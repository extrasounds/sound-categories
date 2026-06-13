package dev.stashy.soundcategories.mc1_16_1.option;

import dev.stashy.soundcategories.shared.option.VersionedOptionLikeProvider;
import net.minecraft.client.options.BooleanOption;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;

public class OptionLikeImpl extends VersionedOptionLikeProvider {
    @Override
    public Object createWidget(Object instance, Object options, int x, int y, int width) {
        return ((Option) instance).createButton((GameOptions) options, x, y, width);
    }

    @Override
    public Object ofBoolean(String key) {
        return new BooleanOption(key, gameOptions -> true, (gameOptions, value) -> {
        });
    }
}
