package dev.stashy.soundcategories.mc1_15_2.gui.widget;

import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;

import java.util.List;
import java.util.Map;

public class SoundEntry extends VersionedElementListWrapper.DefaultedSoundEntry {
    public SoundEntry(Map<Object, AbstractButtonWidget> widgets) {
        super(widgets.values());
    }

    @Override
    public void render(int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        if (this.widgets.isEmpty()) {
            return;
        }
        final MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) {
            return;
        }
        final Screen screen = client.currentScreen;
        if (screen == null) {
            return;
        }

        int i = 0;
        int j = ((AbstractButtonWidget) this.widgets.get(0)).x;

        for (Element element : this.widgets) {
            AbstractButtonWidget widget = (AbstractButtonWidget) element;
            widget.x = j + i;
            widget.y = y;
            widget.render(mouseX, mouseY, tickDelta);
            i += widget.getWidth() + 10;
        }
    }

    @Override
    public List<? extends Element> children() {
        return this.widgets;
    }
}
