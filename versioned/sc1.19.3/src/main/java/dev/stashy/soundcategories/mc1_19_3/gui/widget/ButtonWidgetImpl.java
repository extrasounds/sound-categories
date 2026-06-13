package dev.stashy.soundcategories.mc1_19_3.gui.widget;

import dev.stashy.soundcategories.shared.gui.widget.VersionedButtonWrapper;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ButtonWidgetImpl extends ButtonWidget implements VersionedButtonWrapper {
    protected ButtonWidgetImpl(int x, int y, int width, int height, Text message, PressAction onPress, NarrationSupplier narrationSupplier) {
        super(x, y, width, height, message, onPress, narrationSupplier);
    }

    public static ButtonWidgetImpl init(int x, int y, int width, int height, Text message, ButtonWidget.PressAction callback, Text tooltipContent) {
        ButtonWidgetImpl result = new ButtonWidgetImpl(x, y, width, height, message, callback, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        result.setTooltip(Tooltip.of(tooltipContent));
        return result;
    }
}
