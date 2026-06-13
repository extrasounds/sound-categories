package dev.stashy.soundcategories.mc1_19.option;

import dev.stashy.soundcategories.shared.option.VersionedOptionLikeProvider;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;

public class OptionLikeImpl extends VersionedOptionLikeProvider {
    @Override
    public Object createWidget(Object instance, Object options, int x, int y, int width) {
        return ((SimpleOption<?>) instance).createButton((GameOptions) options, x, y, width);
    }

    @Override
    public Object ofBoolean(String key) {
        return SimpleOption.ofBoolean(key, true, bool -> {
        });
    }
}
