package dev.stashy.soundcategories.mc1_19_4.option;

import dev.stashy.soundcategories.shared.option.VersionedSimpleOptionProvider;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;

public class SimpleOptionImpl extends VersionedSimpleOptionProvider {
    @Override
    public ClickableWidget createWidget(Object instance, GameOptions options, int x, int y, int width) {
        return ((SimpleOption<Double>) instance).createWidget(options, x, y, width);
    }

    @Override
    public Object ofBoolean(String key) {
        return SimpleOption.ofBoolean(key, true, bool -> {
        });
    }
}
