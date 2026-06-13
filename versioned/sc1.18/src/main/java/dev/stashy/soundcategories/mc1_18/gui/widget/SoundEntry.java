package dev.stashy.soundcategories.mc1_18.gui.widget;

import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;
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
        final MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) {
            return;
        }
        final Screen screen = client.currentScreen;
        if (screen == null) {
            return;
        }

        int i = 0;
        int j = ((ClickableWidget) this.widgets.get(0)).x;

        for (Element element : this.widgets) {
            ClickableWidget widget = (ClickableWidget) element;
            widget.x = j + i;
            widget.y = y;
            widget.render(matrices, mouseX, mouseY, tickDelta);
            i += widget.getWidth() + 10;
        }
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return (List<? extends Selectable>) this.widgets;
    }
}
