package dev.stashy.soundcategories.shared.gui.widget;

import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.minecraft.client.gui.components.Button;

import java.lang.reflect.Method;
import java.util.Objects;

public interface VersionedTexturedButtonWrapper {
    String METHOD_KEY_INIT = VersionedTexturedButtonWrapper.class.getCanonicalName() + "#init";

    static VersionedTexturedButtonWrapper newInstance(int x, int y, int width, int height, int u, int v, int hoveredVOffset, int textureWidth, int textureHeight, Button.OnPress pressAction) {
        Method init = SoundCategories.CACHED_METHOD_MAP.getOrDefault(METHOD_KEY_INIT, null);

        if (init == null) {
            try {
                Class<?> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "gui.widget.TexturedButtonWidgetImpl");
                init = Objects.requireNonNull(clazz).getMethod("init", int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, Button.OnPress.class);
                SoundCategories.CACHED_METHOD_MAP.put(METHOD_KEY_INIT, Objects.requireNonNull(init));
            } catch (Exception ex) {
                SoundCategories.LOGGER.error("Failed to find 'TexturedButtonWidget' class.", ex);
            }
        }

        try {
            return (VersionedTexturedButtonWrapper) Objects.requireNonNull(init).invoke(null, x, y, width, height, u, v, hoveredVOffset, textureWidth, textureHeight, pressAction);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'TexturedButtonWidget'", ex);
        }

        return null;
    }
}
