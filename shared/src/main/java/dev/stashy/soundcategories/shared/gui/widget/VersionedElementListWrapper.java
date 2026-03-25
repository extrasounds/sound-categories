package dev.stashy.soundcategories.shared.gui.widget;

import com.google.common.collect.ImmutableMap;
import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.option.VersionedOptionLikeProvider;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface VersionedElementListWrapper extends GuiEventListener, Renderable, NarratableEntry {
    String METHOD_KEY_INIT = VersionedElementListWrapper.class.getCanonicalName() + "#init";

    static VersionedElementListWrapper newInstance(Minecraft client, int width, int height, int top, int bottom, int itemHeight) {
        Method init = SoundCategories.CACHED_METHOD_MAP.getOrDefault(METHOD_KEY_INIT, null);

        if (init == null) {
            try {
                Class<?> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "gui.widget.SoundList");
                init = Objects.requireNonNull(clazz).getMethod("init", Minecraft.class, int.class, int.class, int.class, int.class, int.class);
                SoundCategories.CACHED_METHOD_MAP.put(METHOD_KEY_INIT, Objects.requireNonNull(init));
            } catch (Exception ex) {
                SoundCategories.LOGGER.error("Failed to init 'SoundList' class.", ex);
            }
        }

        try {
            return (VersionedElementListWrapper) Objects.requireNonNull(init).invoke(null, client, width, height, top, bottom, itemHeight);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'SoundList'", ex);
        }

        return null;
    }

    default int addSingleOptionEntry(Object option) {
        return this.addSingleOptionEntry(option, true);
    }

    int addSingleOptionEntry(Object option, boolean editable);

    int addOptionEntry(Object firstOption, @Nullable Object secondOption);

    default void addAll(Object[] options) {
        for (int i = 0; i < options.length; i += 2) {
            this.addOptionEntry(options[i], i < options.length - 1 ? options[i + 1] : null);
        }
    }

    int addCategory(SoundSource cat);

    int addReadOnlyCategory(SoundSource cat);

    void addAllCategory(SoundSource[] categories);

    int addGroup(SoundSource group, Button.OnPress pressAction);

    void setDimensionsImpl(int width, int height);

    boolean mouseScrolledImpl(double mouseX, double mouseY, double horizontalAmount, double verticalAmount);

    void addDrawable(Object option, AbstractWidget button);

    @Environment(EnvType.CLIENT)
    interface VersionedSoundEntry {
        String METHOD_KEY_CTOR = VersionedSoundEntry.class.getCanonicalName() + "#<init>";

        @SuppressWarnings("unchecked")
        static <T extends VersionedSoundEntry> T newInstance(Map<Object, AbstractWidget> widgets) {
            Constructor<?> init = SoundCategories.CACHED_INIT_MAP.getOrDefault(METHOD_KEY_CTOR, null);

            if (init == null) {
                try {
                    Class<?> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "gui.widget.SoundEntry");
                    init = Objects.requireNonNull(clazz).getConstructor(Map.class);
                    SoundCategories.CACHED_INIT_MAP.put(METHOD_KEY_CTOR, Objects.requireNonNull(init));
                } catch (Exception ex) {
                    SoundCategories.LOGGER.error("Failed to init 'SoundEntry' class.", ex);
                }
            }

            try {
                return (T) Objects.requireNonNull(init).newInstance(widgets);
            } catch (Exception ex) {
                SoundCategories.LOGGER.error("Cannot instantiate 'SoundEntry'", ex);
            }
            return null;
        }

        static <T extends VersionedSoundEntry> T create(Options options, int width, Object option) {
            return VersionedSoundEntry.newInstance(
                    ImmutableMap.of(option, Objects.requireNonNull(
                            VersionedOptionLikeProvider.INSTANCE.createWidget(option, options, width / 2 - 155, 0, 310)
                    ))
            );
        }

        static <T extends VersionedSoundEntry> T createDouble(Options options, int width, Object first, @Nullable Object second) {
            Map<Object, AbstractWidget> widgets;
            AbstractWidget firstWidget = VersionedOptionLikeProvider.INSTANCE.createWidget(first, options, width / 2 - 155, 0, 150);
            if (second == null) {
                widgets = ImmutableMap.of(first, firstWidget);
            } else {
                widgets = ImmutableMap.of(
                        first, firstWidget,
                        second, VersionedOptionLikeProvider.INSTANCE.createWidget(second, options, width / 2 + 5, 0, 150)
                );
            }
            return VersionedSoundEntry.newInstance(widgets);
        }

        static <T extends VersionedSoundEntry> T createGroup(Options options, Object option, int width, Button.OnPress pressAction) {
            Map<Object, AbstractWidget> widgets = ImmutableMap.of(
                    option, Objects.requireNonNull(VersionedOptionLikeProvider.INSTANCE.createWidget(option, options, width / 2 - 155, 0, 280)),
                    VersionedOptionLikeProvider.INSTANCE.ofBoolean(option.toString()), (ImageButton) Objects.requireNonNull(
                            VersionedTexturedButtonWrapper.newInstance(width / 2 + 135, 0, 20, 20, 0, 0, 20,
                                    20, 40, pressAction)
                    ));
            return VersionedSoundEntry.newInstance(widgets);
        }

        List<AbstractWidget> getWidgets();
    }
}
