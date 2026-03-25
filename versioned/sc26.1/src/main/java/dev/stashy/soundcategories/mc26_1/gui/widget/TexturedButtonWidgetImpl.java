package dev.stashy.soundcategories.mc26_1.gui.widget;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.widget.VersionedTexturedButtonWrapper;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.Identifier;

public class TexturedButtonWidgetImpl extends ImageButton implements VersionedTexturedButtonWrapper {
    public TexturedButtonWidgetImpl(int x, int y, int width, int height, WidgetSprites textures, OnPress pressAction) {
        super(x, y, width, height, textures, pressAction);
    }

    public static TexturedButtonWidgetImpl init(int x, int y, int width, int height, int u, int v, int hoveredVOffset, int textureWidth, int textureHeight, OnPress pressAction) {
        WidgetSprites textures = new WidgetSprites(
                Identifier.fromNamespaceAndPath(SoundCategories.MOD_ID, "settings/button"),
                Identifier.fromNamespaceAndPath(SoundCategories.MOD_ID, "settings/disabled"),
                Identifier.fromNamespaceAndPath(SoundCategories.MOD_ID, "settings/hover"),
                Identifier.fromNamespaceAndPath(SoundCategories.MOD_ID, "settings/disabled")
        );
        return new TexturedButtonWidgetImpl(x, y, width, height, textures, pressAction);
    }
}
