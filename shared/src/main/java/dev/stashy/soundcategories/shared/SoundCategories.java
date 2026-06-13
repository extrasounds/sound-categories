package dev.stashy.soundcategories.shared;

import dev.stashy.soundcategories.CategoryLoader;
import dev.stashy.soundcategories.shared.text.VersionedText;
import me.lonefelidae16.groominglib.api.PrefixableMessageFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.AnnotationFormatError;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public final class SoundCategories {
    public static final Logger LOGGER = LogManager.getLogger(
            SoundCategories.class,
            new PrefixableMessageFactory(SoundCategories.class.getSimpleName())
    );
    public static final String MOD_ID = "soundcategories";
    public static final String BASE_PACKAGE = "dev.stashy.soundcategories";
    public static final Map<String, Method> CACHED_METHOD_MAP = new HashMap<>();
    public static final Map<String, Constructor<?>> CACHED_INIT_MAP = new HashMap<>();
    private static final String OPTION_PREFIX_SOUND_CAT = "soundCategory.";
    private static final Set<String> SUPPRESSED_NAMES = new HashSet<>();

    /**
     * The Map of {@link SoundCategory} including to which group the category belongs.<br>
     * {@code Unique category} -> {@code Group category}
     */
    public static final Map<SoundCategory, SoundCategory> PARENTS = new EnumMap<>(SoundCategory.class);
    /**
     * The Map of {@link String} -> {@link SoundCategory} showing which a master category the class has.<br>
     * {@code Class name} -> {@code Master category}
     */
    public static final Map<String, SoundCategory> MASTERS = new HashMap<>();
    public static final List<String> MASTER_CLASSES = new ArrayList<>();
    public static final Map<SoundCategory, Float> DEFAULT_LEVELS = new EnumMap<>(SoundCategory.class);
    public static final Map<SoundCategory, Boolean> TOGGLEABLE_CATS = new EnumMap<>(SoundCategory.class);
    public static final Map<SoundCategory, Text> TOOLTIPS = new EnumMap<>(SoundCategory.class);
    public static final Map<SoundCategory, Identifier[]> PREVIEW_SOUNDS = new EnumMap<>(SoundCategory.class);

    public static String getOptionsTranslationKey(SoundCategory target) {
        return OPTION_PREFIX_SOUND_CAT + target.getName();
    }

    /**
     * Retrieves all {@link EntrypointContainer} from the key {@code "sound-categories"} and their annotation fields.
     */
    public static Map<EntrypointContainer<CategoryLoader>, List<Field>> getCategories() {
        return FabricLoader.getInstance().getEntrypointContainers("sound-categories", CategoryLoader.class).stream()
                .collect(Collectors.toMap(it -> it, it -> getRegistrations(it.getEntrypoint())));
    }

    /**
     * Retrieves all annotated fields from specified {@link CategoryLoader}.
     *
     * @param loader The target instance to use {@link java.lang.reflect}.
     */
    private static List<Field> getRegistrations(CategoryLoader loader) {
        return Arrays.stream(loader.getClass().getDeclaredFields()).filter(it -> {
            return it.isAnnotationPresent(CategoryLoader.Register.class);
        }).collect(Collectors.toList());
    }

    private static String generateFieldClassName(Class<?> clazz, Field field) {
        return String.format("%s#%s", clazz.getCanonicalName(), field.getName());
    }

    public static void setup() {
        Class<SoundCategory> soundCategoryClass = SoundCategory.class;
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            SoundCategories.LOGGER.info("Loaded SoundCategory: {}", EnumSet.allOf(soundCategoryClass).stream().map(Enum::toString).collect(Collectors.joining(", ")));
        }

        try {
            final Map<EntrypointContainer<CategoryLoader>, List<Field>> allAnnotations = getCategories();

            // First fetch for the MASTER categories.
            for (EntrypointContainer<CategoryLoader> container : allAnnotations.keySet()) {
                final CategoryLoader categoryLoader = container.getEntrypoint();
                final String className = categoryLoader.getClass().getCanonicalName();

                for (Field field : allAnnotations.get(container)) {
                    final CategoryLoader.Register annotation = field.getAnnotation(CategoryLoader.Register.class);
                    final Object fieldInstance = field.get(categoryLoader);
                    final SoundCategory category;
                    if (!(fieldInstance instanceof SoundCategory)) {
                        final String fieldClassName = generateFieldClassName(categoryLoader.getClass(), field);
                        if (!SUPPRESSED_NAMES.contains(fieldClassName)) {
                            LOGGER.error(
                                    "Cast check failed for the member '{}'.", fieldClassName,
                                    new ClassCastException(String.format("Can not cast %s to SoundCategory", fieldInstance.getClass().getCanonicalName())));
                            SUPPRESSED_NAMES.add(fieldClassName);
                        }
                        continue;
                    } else {
                        category = (SoundCategory) fieldInstance;
                    }

                    if (!annotation.master()) {
                        continue;
                    }

                    if (MASTERS.containsKey(className)) {
                        // The MASTER already registered.
                        if (!SUPPRESSED_NAMES.contains(className)) {
                            LOGGER.warn(
                                    "Unexpected annotation was found.",
                                    new AnnotationFormatError(String.format("Class '%s' has a duplicate member with annotation value 'master'!", className)));
                            SUPPRESSED_NAMES.add(className);
                        }
                        PARENTS.put(category, MASTERS.get(className));
                    }
                    MASTERS.putIfAbsent(className, category);
                }
            }

            MASTER_CLASSES.addAll(MASTERS.keySet().stream().sorted().collect(Collectors.toList()));

            // Put all the customized SoundCategories.
            for (EntrypointContainer<CategoryLoader> container : allAnnotations.keySet()) {
                final CategoryLoader categoryLoader = container.getEntrypoint();
                final String className = categoryLoader.getClass().getCanonicalName();

                for (Field field : allAnnotations.get(container)) {
                    final CategoryLoader.Register annotation = field.getAnnotation(CategoryLoader.Register.class);
                    final Object fieldInstance = field.get(categoryLoader);
                    final SoundCategory category;
                    if (!(fieldInstance instanceof SoundCategory)) {
                        continue;
                    } else {
                        category = (SoundCategory) fieldInstance;
                    }

                    if (!annotation.master()) {
                        if (MASTERS.containsKey(className)) {
                            PARENTS.put(category, MASTERS.get(className));
                        } else {
                            // The 'orphan' category was found, will be grouped together with Vanilla volume options.
                            // This is deprecated as it causes confusion for users.
                            if (!SUPPRESSED_NAMES.contains(className)) {
                                LOGGER.warn("Missing annotation value 'master' in class '{}'. This is deprecated.", className);
                                LOGGER.warn("To avoid this message, please specify \"master = true\" in one of the @Register annotation in your class.");
                                SUPPRESSED_NAMES.add(className);
                            }
                        }
                    }

                    if (annotation.defaultLevel() != 1f) {
                        DEFAULT_LEVELS.put(category, annotation.defaultLevel());
                    }

                    if (annotation.toggle()) {
                        TOGGLEABLE_CATS.put(category, true);
                    }

                    if (!annotation.tooltip().isEmpty()) {
                        TOOLTIPS.put(category, VersionedText.INSTANCE.translatable(annotation.tooltip()));
                    }

                    if (annotation.preview().length > 0) {
                        try {
                            PREVIEW_SOUNDS.put(category, Objects.requireNonNull(Arrays.stream(annotation.preview()).map(Identifier::tryParse).toArray(Identifier[]::new)));
                        } catch (Exception ex) {
                            LOGGER.error("Parsing Identifier of preview sound failed: {}", String.join(", ", annotation.preview()), ex);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Unexpected error has caught", ex);
        }

        // Cleanup.
        SUPPRESSED_NAMES.clear();
    }

    public static SoundCategory[] filterVanillaCategory() {
        return Arrays.stream(SoundCategory.values()).filter(it -> {
            return !SoundCategories.PARENTS.containsKey(it) &&
                    !SoundCategories.PARENTS.containsValue(it) &&
                    it != SoundCategory.MASTER;
        }).toArray(SoundCategory[]::new);
    }

    public static SoundCategory[] filterCustomizedMasterCategory() {
        return SoundCategories.MASTER_CLASSES.stream().map(SoundCategories.MASTERS::get).toArray(SoundCategory[]::new);
    }
}
