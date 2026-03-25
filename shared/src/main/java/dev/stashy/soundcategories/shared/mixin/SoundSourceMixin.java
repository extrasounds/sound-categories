package dev.stashy.soundcategories.shared.mixin;

import dev.stashy.soundcategories.CategoryLoader;
import dev.stashy.soundcategories.shared.SoundCategories;
import net.minecraft.sounds.SoundSource;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.util.*;

@Mixin(SoundSource.class)
public abstract class SoundSourceMixin {
    @SuppressWarnings("InvokerTarget")
    @Invoker("<init>")
    private static SoundSource soundcategories$newSoundSource(String internalName, int order, String name) {
        throw new AssertionError();
    }

    @SuppressWarnings("ShadowTarget")
    @Shadow
    @Final
    @Mutable
    private static SoundSource[] $VALUES;

    @Unique
    private static final String INVALID_VAR_NAME_REGEX = "[^a-zA-Z0-9_$]";
    @Unique
    private static Set<String> SUPPRESSED_NAMES;
    @Unique
    private static List<SoundSource> EDITING_CATS;
    @Unique
    private static Map<String, SoundSource> REGISTERED_VARIANTS;

    /**
     * Tries to make the custom enum. The created variable can be accessed from specified field.<br>
     * When the name already exists, the reference is created to match it.
     *
     * @param field    The referer.
     * @param instance The instance of an Object that has {@code field}.
     * @param name     The name trying to register.
     * @throws IllegalAccessException Thrown when cannot access to the {@code field}.
     */
    @Unique
    private static void soundcategories$tryMakeVariant(Field field, Object instance, String name) throws IllegalAccessException {
        final String varName = name.toUpperCase(Locale.ROOT);
        final String displayName = name.toLowerCase(Locale.ROOT);
        final SoundSource newCategory;

        // Check duplicated name.
        if (REGISTERED_VARIANTS.containsKey(displayName)) {
            if (!SUPPRESSED_NAMES.contains(displayName)) {
                SoundCategories.LOGGER.error(
                        "Duplicate enum name was found: '{}'.", displayName,
                        new RuntimeException("%s is already registered".formatted(displayName))
                );
                SUPPRESSED_NAMES.add(displayName);
            }
            newCategory = REGISTERED_VARIANTS.get(displayName);
        } else {
            newCategory = soundcategories$newSoundSource(varName, EDITING_CATS.getLast().ordinal() + 1, displayName);
        }

        field.set(instance, newCategory);
        if (!EDITING_CATS.contains(newCategory)) {
            EDITING_CATS.add(newCategory);
            REGISTERED_VARIANTS.put(displayName, newCategory);
        }
    }

    /**
     * Adds customized enum variant of {@link SoundSource}.
     */
    @Inject(method = "<clinit>", at = @At(value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/minecraft/sounds/SoundSource;$VALUES:[Lnet/minecraft/sounds/SoundSource;",
            shift = At.Shift.AFTER))
    private static void soundcategories$addCustomVariants(CallbackInfo ci) {
        REGISTERED_VARIANTS = new HashMap<>();
        SUPPRESSED_NAMES = new HashSet<>();
        EDITING_CATS = new ArrayList<>(Arrays.asList($VALUES));
        for (SoundSource category : EDITING_CATS) {
            REGISTERED_VARIANTS.put(category.getName(), category);
        }

        SoundCategories.getCategories().forEach((container, fields) -> {
            final String modId = container.getProvider().getMetadata().getId();
            final CategoryLoader categoryLoader = container.getEntrypoint();
            fields.forEach(field -> {
                if (!field.getType().equals(SoundSource.class)) {
                    return;
                }

                final CategoryLoader.Register annotation = field.getAnnotation(CategoryLoader.Register.class);
                final String id = annotation.id().isEmpty() ? field.getName() : annotation.id();
                final String varName = "%s$%s".formatted(modId, id).replaceAll(INVALID_VAR_NAME_REGEX, "_");
                try {
                    soundcategories$tryMakeVariant(field, categoryLoader, varName);
                } catch (Exception ex) {
                    SoundCategories.LOGGER.error("Failed to register SoundCategory with ID '{}'", varName, ex);
                }
            });
        });

        // Set the new enums.
        $VALUES = EDITING_CATS.toArray(SoundSource[]::new);

        // Cleanup.
        EDITING_CATS.clear();
        SUPPRESSED_NAMES.clear();
        REGISTERED_VARIANTS.clear();
    }
}
