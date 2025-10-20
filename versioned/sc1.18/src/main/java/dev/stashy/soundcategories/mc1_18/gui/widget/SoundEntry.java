package dev.stashy.soundcategories.mc1_18.gui.widget;

import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Map;

public class SoundEntry extends VersionedElementListWrapper.DefaultedSoundEntry {
    public SoundEntry(Map<Object, ClickableWidget> widgets) {
        super(widgets.values());
    }

    @Override
    public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        if (this.widgets.isEmpty()) {
            return;
        }

        int i = 0;
        int j = this.widgets.get(0).x;

        for (ClickableWidget s : this.widgets) {
            s.x = j + i;
            s.y = y;
            s.render(matrices, mouseX, mouseY, tickDelta);
            i += s.getWidth() + 10;
        }
    }
}
