package dev.stashy.soundcategories.shared.gui.screen;

import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.sound.SoundCategory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Objects;

public interface VersionedSoundGroupOptionsScreenWrapper {
    String METHOD_KEY_CTOR = VersionedSoundGroupOptionsScreenWrapper.class.getCanonicalName() + "#<init>";

    @SuppressWarnings("unchecked")
    static VersionedSoundGroupOptionsScreenWrapper newInstance(Screen parent, Object settings, SoundCategory category) {
        Constructor<VersionedSoundGroupOptionsScreenWrapper> constructor = (Constructor<VersionedSoundGroupOptionsScreenWrapper>) SoundCategories.CACHED_INIT_MAP.get(METHOD_KEY_CTOR);

        if (constructor == null) {
            try {
                Class<VersionedSoundGroupOptionsScreenWrapper> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "gui.screen.SoundGroupOptionsScreen");
                constructor = clazz.getConstructor(Screen.class, Object.class, SoundCategory.class);
                SoundCategories.CACHED_INIT_MAP.put(METHOD_KEY_CTOR, Objects.requireNonNull(constructor));
            } catch (Exception ex) {
                SoundCategories.LOGGER.error("Failed to find 'SoundGroupOptionsScreen' class.", ex);
            }
        }

        try {
            return Objects.requireNonNull(constructor).newInstance(parent, settings, category);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'SoundGroupOptionsScreen'", ex);
        }
        return null;
    }

    default SoundCategory[] filterByParentCategory(SoundCategory parentCategory) {
        return Arrays.stream(SoundCategory.values()).filter(it -> {
            return SoundCategories.PARENTS.containsKey(it) && SoundCategories.PARENTS.get(it) == parentCategory;
        }).toArray(SoundCategory[]::new);
    }
}
