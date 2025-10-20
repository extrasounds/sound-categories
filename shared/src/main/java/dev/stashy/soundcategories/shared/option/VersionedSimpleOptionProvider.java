package dev.stashy.soundcategories.shared.option;

import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;

import java.util.Objects;

public abstract class VersionedSimpleOptionProvider {
    public static final VersionedSimpleOptionProvider INSTANCE;

    static {
        VersionedSimpleOptionProvider instance = null;
        try {
            Class<VersionedSimpleOptionProvider> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "option.SimpleOptionImpl");
            instance = clazz.getConstructor().newInstance();
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Failed to init 'OptionImpl' class.", ex);
        }
        INSTANCE = Objects.requireNonNull(instance);
    }

    public abstract ClickableWidget createWidget(Object instance, GameOptions options, int x, int y, int width);

    public abstract Object ofBoolean(String key);
}
