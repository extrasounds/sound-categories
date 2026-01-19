package dev.stashy.soundcategories.mc1_21_11.gui.widget;

import com.google.common.collect.ImmutableList;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.OptionListWidget;

import java.util.List;
import java.util.Map;

public class SoundEntry extends OptionListWidget.Component implements VersionedElementListWrapper.VersionedSoundEntry {
    private final List<ClickableWidget> widgets;

    public SoundEntry(Map<Object, ClickableWidget> widgets) {
        super();
        this.widgets = ImmutableList.copyOf(widgets.values());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, boolean hovered, float deltaTicks) {
        if (this.widgets.isEmpty()) {
            return;
        }
        final var client = MinecraftClient.getInstance();
        if (client == null) {
            return;
        }
        final var screen = client.currentScreen;
        if (screen == null) {
            return;
        }

        int i = 0;
        int j = screen.width / 2 - 155;

        for (var widget : this.widgets) {
            widget.setPosition(j + i, this.getContentY());
            widget.render(context, mouseX, mouseY, deltaTicks);
            i += widget.getWidth() + 10;
        }
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return this.widgets;
    }

    @Override
    public List<? extends Element> children() {
        return this.widgets;
    }

    @Override
    public List<ClickableWidget> getWidgets() {
        return this.widgets;
    }
}
