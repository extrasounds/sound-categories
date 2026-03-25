package dev.stashy.soundcategories.shared.text;

import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.minecraft.network.chat.Component;

import java.lang.reflect.Constructor;
import java.util.Objects;

public abstract class VersionedText {
    public static final VersionedText INSTANCE;

    static {
        VersionedText instance = null;
        try {
            Class<VersionedText> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "text.TextImpl");
            Constructor<VersionedText> constructor = clazz.getConstructor();
            instance = constructor.newInstance();
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'TextImpl'", ex);
        }
        INSTANCE = Objects.requireNonNull(instance);
    }

    public abstract Component empty();

    public abstract Component getDoneText();

    public abstract Component getCancelText();

    public abstract Component translatable(String key);
}
