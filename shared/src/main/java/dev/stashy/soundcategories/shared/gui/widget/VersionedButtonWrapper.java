package dev.stashy.soundcategories.shared.gui.widget;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.text.VersionedText;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;

import java.lang.reflect.Method;
import java.util.Objects;

public interface VersionedButtonWrapper extends GuiEventListener, Renderable, NarratableEntry {
    String METHOD_KEY_INIT = VersionedButtonWrapper.class.getCanonicalName() + "#init";

    static VersionedButtonWrapper newInstance(int x, int y, int width, int height, Component message, Button.OnPress callback) {
        return newInstance(x, y, width, height, message, callback, VersionedText.INSTANCE.empty());
    }

    static VersionedButtonWrapper newInstance(int x, int y, int width, int height, Component message, Button.OnPress callback, Component tooltipContent) {
        Method init = SoundCategories.CACHED_METHOD_MAP.getOrDefault(METHOD_KEY_INIT, null);

        if (init == null) {
            try {
                Class<VersionedButtonWrapper> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "gui.widget.ButtonWidgetImpl");
                init = Objects.requireNonNull(clazz).getMethod("init", int.class, int.class, int.class, int.class, Component.class, Button.OnPress.class, Component.class);
                SoundCategories.CACHED_METHOD_MAP.put(METHOD_KEY_INIT, Objects.requireNonNull(init));
            } catch (Exception ex) {
                SoundCategories.LOGGER.error("Failed to init 'ButtonWidget' class.", ex);
            }
        }

        try {
            return (VersionedButtonWrapper) Objects.requireNonNull(init).invoke(null, x, y, width, height, message, callback, tooltipContent);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'ButtonWidget'", ex);
        }

        return null;
    }
}
