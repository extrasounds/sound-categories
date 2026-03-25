package dev.stashy.soundcategories.mc26_1.gui.widget;

import com.google.common.collect.ImmutableList;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;

import java.util.List;
import java.util.Map;

public class SoundEntry extends OptionsList.AbstractEntry implements VersionedElementListWrapper.VersionedSoundEntry {
    private final List<AbstractWidget> widgets;

    public SoundEntry(Map<Object, AbstractWidget> widgets) {
        super();
        this.widgets = ImmutableList.copyOf(widgets.values());
    }

    @Override
    public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float deltaTicks) {
        if (this.widgets.isEmpty()) {
            return;
        }
        final var client = Minecraft.getInstance();
        final var screen = client.screen;
        if (screen == null) {
            return;
        }

        int i = 0;
        int j = screen.width / 2 - 155;

        for (var widget : this.widgets) {
            widget.setPosition(j + i, this.getContentY());
            widget.extractRenderState(graphics, mouseX, mouseY, deltaTicks);
            i += widget.getWidth() + 10;
        }
    }

    @Override
    public List<? extends NarratableEntry> narratables() {
        return this.widgets;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return this.widgets;
    }

    @Override
    public List<AbstractWidget> getWidgets() {
        return this.widgets;
    }
}
