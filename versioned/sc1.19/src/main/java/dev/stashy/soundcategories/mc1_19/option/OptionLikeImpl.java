package dev.stashy.soundcategories.mc1_19.option;

import dev.stashy.soundcategories.shared.option.VersionedOptionLikeProvider;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;

public class OptionLikeImpl extends VersionedOptionLikeProvider {
    @Override
    public ClickableWidget createWidget(Object instance, GameOptions options, int x, int y, int width) {
        return ((SimpleOption<?>) instance).createButton(options, x, y, width);
    }

    @Override
    public Object ofBoolean(String key) {
        return SimpleOption.ofBoolean(key, true, bool -> {
        });
    }
}
