package dev.stashy.soundcategories.mc1_15.gui.widget;

import dev.stashy.soundcategories.shared.gui.widget.VersionedButtonWrapper;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ButtonWidgetImpl extends ButtonWidget implements VersionedButtonWrapper {
    public ButtonWidgetImpl(int x, int y, int width, int height, String message, PressAction onPress) {
        super(x, y, width, height, message, onPress);
    }

    public static ButtonWidgetImpl init(int x, int y, int width, int height, Text message, PressAction callback, Text tooltipContent) {
        return new ButtonWidgetImpl(x, y, width, height, message.asFormattedString(), callback);
    }
}
