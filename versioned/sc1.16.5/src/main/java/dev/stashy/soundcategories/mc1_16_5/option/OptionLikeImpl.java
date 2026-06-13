package dev.stashy.soundcategories.mc1_16_5.option;

import dev.stashy.soundcategories.shared.option.VersionedOptionLikeProvider;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;

public class OptionLikeImpl extends VersionedOptionLikeProvider {
    @Override
    public ClickableWidget createWidget(Object instance, GameOptions options, int x, int y, int width) {
        return ((Option) instance).createButton(options, x, y, width);
    }

    @Override
    public Object ofBoolean(String key) {
        return new BooleanOption(key, gameOptions -> true, (gameOptions, value) -> {
        });
    }
}
