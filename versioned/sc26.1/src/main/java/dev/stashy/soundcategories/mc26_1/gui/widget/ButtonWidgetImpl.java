package dev.stashy.soundcategories.mc26_1.gui.widget;

import dev.stashy.soundcategories.shared.gui.widget.VersionedButtonWrapper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

public class ButtonWidgetImpl extends Button implements VersionedButtonWrapper {
    protected ButtonWidgetImpl(int x, int y, int width, int height, Component message, OnPress onPress, CreateNarration narrationSupplier) {
        super(x, y, width, height, message, onPress, narrationSupplier);
    }

    public static ButtonWidgetImpl init(int x, int y, int width, int height, Component message, Button.OnPress callback, Component tooltipContent) {
        var result = new ButtonWidgetImpl(x, y, width, height, message, callback, Button.DEFAULT_NARRATION);
        result.setTooltip(Tooltip.create(tooltipContent));
        return result;
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

    }
}
