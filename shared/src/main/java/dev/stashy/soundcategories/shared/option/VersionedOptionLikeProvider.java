package dev.stashy.soundcategories.shared.option;

import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.api.McVersionInterchange;

import java.util.Objects;

public abstract class VersionedOptionLikeProvider {
    public static final VersionedOptionLikeProvider INSTANCE;

    static {
        VersionedOptionLikeProvider instance = null;
        try {
            Class<VersionedOptionLikeProvider> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "option.OptionLikeImpl");
            instance = clazz.getConstructor().newInstance();
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Failed to init 'OptionLikeImpl' class.", ex);
        }
        INSTANCE = Objects.requireNonNull(instance);
    }

    public abstract Object createWidget(Object instance, Object options, int x, int y, int width);

    public abstract Object ofBoolean(String key);
}
