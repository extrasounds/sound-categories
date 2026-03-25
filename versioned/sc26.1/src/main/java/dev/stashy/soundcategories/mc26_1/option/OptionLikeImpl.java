package dev.stashy.soundcategories.mc26_1.option;

import dev.stashy.soundcategories.shared.option.VersionedOptionLikeProvider;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;

public class OptionLikeImpl extends VersionedOptionLikeProvider {
    @Override
    public AbstractWidget createWidget(Object instance, Options options, int x, int y, int width) {
        return ((OptionInstance<?>) instance).createButton(options, x, y, width);
    }

    @Override
    public Object ofBoolean(String key) {
        return OptionInstance.createBoolean(key, true, bool -> {
        });
    }
}
