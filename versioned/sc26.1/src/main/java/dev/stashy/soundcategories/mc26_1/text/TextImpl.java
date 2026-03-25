package dev.stashy.soundcategories.mc26_1.text;

import dev.stashy.soundcategories.shared.text.VersionedText;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class TextImpl extends VersionedText {
    @Override
    public Component empty() {
        return Component.empty();
    }

    public Component getDoneText() {
        return CommonComponents.GUI_DONE;
    }

    public Component getCancelText() {
        return CommonComponents.GUI_CANCEL;
    }

    public Component translatable(String key) {
        return Component.translatable(key);
    }
}
