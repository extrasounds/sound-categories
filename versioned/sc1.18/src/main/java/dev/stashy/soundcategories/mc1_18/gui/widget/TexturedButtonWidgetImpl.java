package dev.stashy.soundcategories.mc1_18.gui.widget;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.widget.VersionedTexturedButtonWrapper;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.util.Identifier;

public class TexturedButtonWidgetImpl extends TexturedButtonWidget implements VersionedTexturedButtonWrapper {
    public TexturedButtonWidgetImpl(int x, int y, int width, int height, int u, int v, int hoveredVOffset, Identifier texture, int textureWidth, int textureHeight, PressAction pressAction) {
        super(x, y, width, height, u, v, hoveredVOffset, texture, textureWidth, textureHeight, pressAction);
    }

    public static TexturedButtonWidgetImpl init(int x, int y, int width, int height, int u, int v, int hoveredVOffset, int textureWidth, int textureHeight, PressAction pressAction) {
        return new TexturedButtonWidgetImpl(x, y, width, height, u, v, hoveredVOffset, Identifier.tryParse(SoundCategories.MOD_ID + ":textures/gui/settings.png"), textureWidth, textureHeight, pressAction);
    }
}
