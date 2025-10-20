package dev.stashy.soundcategories.mc1_20.gui.widget;

import com.google.common.collect.ImmutableList;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.OptionListWidget;

import java.util.List;
import java.util.Map;

public class SoundEntry extends OptionListWidget.WidgetEntry implements VersionedElementListWrapper.VersionedSoundEntry {
    private final List<ClickableWidget> widgets;

    @SuppressWarnings("unchecked")
    public SoundEntry(Map<Object, ClickableWidget> widgets) {
        super((Map) widgets);
        this.widgets = ImmutableList.copyOf(widgets.values());
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        if (this.widgets.isEmpty()) {
            return;
        }

        int i = 0;
        int j = this.widgets.get(0).getX();

        for (ClickableWidget s : this.widgets) {
            s.setPosition(j + i, y);
            s.render(context, mouseX, mouseY, tickDelta);
            i += s.getWidth() + 10;
        }
    }

    @Override
    public List<ClickableWidget> getWidgets() {
        return this.widgets;
    }
}
