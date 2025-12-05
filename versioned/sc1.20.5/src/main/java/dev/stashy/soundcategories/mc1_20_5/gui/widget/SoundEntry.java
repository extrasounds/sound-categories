package dev.stashy.soundcategories.mc1_20_5.gui.widget;

import com.google.common.collect.ImmutableList;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.OptionListWidget;

import java.util.List;
import java.util.Map;

public class SoundEntry extends OptionListWidget.WidgetEntry implements VersionedElementListWrapper.VersionedSoundEntry {
    private final List<ClickableWidget> widgets;

    public SoundEntry(Map<Object, ClickableWidget> widgets) {
        super(ImmutableList.copyOf(widgets.values()), MinecraftClient.getInstance().currentScreen);
        this.widgets = ImmutableList.copyOf(widgets.values());
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
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
            widget.setPosition(j + i, y);
            widget.render(context, mouseX, mouseY, tickDelta);
            i += widget.getWidth() + 10;
        }
    }

    @Override
    public List<net.minecraft.client.gui.widget.ClickableWidget> getWidgets() {
        return this.widgets;
    }
}
