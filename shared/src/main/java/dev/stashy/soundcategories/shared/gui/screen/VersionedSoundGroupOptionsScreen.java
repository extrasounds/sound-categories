package dev.stashy.soundcategories.shared.gui.screen;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Objects;

public abstract class VersionedSoundGroupOptionsScreen extends OptionsSubScreen {
    private static final String METHOD_KEY_CTOR = VersionedSoundGroupOptionsScreen.class.getCanonicalName() + "#<init>";

    protected VersionedElementListWrapper list;

    static {
        try {
            Class<VersionedSoundGroupOptionsScreen> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "gui.screen.SoundGroupOptionsScreen");
            Constructor<VersionedSoundGroupOptionsScreen> constructor = clazz.getConstructor(Screen.class, Options.class, SoundSource.class);
            SoundCategories.CACHED_INIT_MAP.put(METHOD_KEY_CTOR, Objects.requireNonNull(constructor));
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Failed to find 'SoundGroupOptionsScreen' class.", ex);
        }
    }

    public VersionedSoundGroupOptionsScreen(Screen lastScreen, Options options, Component title) {
        super(lastScreen, options, title);
    }

    @SuppressWarnings("unchecked")
    public static VersionedSoundGroupOptionsScreen newInstance(Screen parent, Options settings, SoundSource category) {
        try {
            Constructor<VersionedSoundGroupOptionsScreen> constructor = (Constructor<VersionedSoundGroupOptionsScreen>) SoundCategories.CACHED_INIT_MAP.get(METHOD_KEY_CTOR);
            return constructor.newInstance(parent, settings, category);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'SoundGroupOptionsScreen'", ex);
        }
        return null;
    }

    @Override
    protected void init() {
        this.list = VersionedElementListWrapper.newInstance(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
        super.init();

        this.addRenderableWidget(this.list);
    }

    protected SoundSource[] filterByParentCategory(SoundSource parentCategory) {
        return Arrays.stream(SoundSource.values()).filter(it -> {
            return SoundCategories.PARENTS.containsKey(it) && SoundCategories.PARENTS.get(it) == parentCategory;
        }).toArray(SoundSource[]::new);
    }
}
