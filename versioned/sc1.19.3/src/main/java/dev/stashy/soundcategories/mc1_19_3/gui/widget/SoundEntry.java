package dev.stashy.soundcategories.mc1_19_3.gui.widget;

import com.google.common.collect.ImmutableList;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;
import java.util.Map;

public class SoundEntry extends ButtonListWidget.ButtonEntry implements VersionedElementListWrapper.VersionedSoundEntry {
    private final List<ClickableWidget> widgets;

    @SuppressWarnings("unchecked")
    public SoundEntry(Map<Object, ClickableWidget> widgets) {
        super((Map) widgets);
        this.widgets = ImmutableList.copyOf(widgets.values());
    }

    @Override
    public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        if (this.widgets.isEmpty()) {
            return;
        }

        int i = 0;
        int j = this.widgets.get(0).getX();

        for (ClickableWidget s : this.widgets) {
            s.setPos(j + i, y);
            s.render(matrices, mouseX, mouseY, tickDelta);
            i += s.getWidth() + 10;
        }
    }

    @Override
    public List<ClickableWidget> getWidgets() {
        return this.widgets;
    }
}
