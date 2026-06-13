package dev.stashy.soundcategories.mc1_17.option;

import dev.stashy.soundcategories.shared.option.VersionedOptionLikeProvider;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;

public class OptionLikeImpl extends VersionedOptionLikeProvider {
    @Override
    public Object createWidget(Object instance, Object options, int x, int y, int width) {
        return ((Option) instance).createButton((GameOptions) options, x, y, width);
    }

    @Override
    public Object ofBoolean(String key) {
        return CyclingOption.create(key, gameOptions -> true, (gameOptions, option, value) -> {
        });
    }
}
