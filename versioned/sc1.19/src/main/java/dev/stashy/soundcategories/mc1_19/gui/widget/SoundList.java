package dev.stashy.soundcategories.mc1_19.gui.widget;

import com.google.common.collect.ImmutableMap;
import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import me.lonefelidae16.groominglib.Util;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Objects;

public class SoundList extends ElementListWidget<VersionedElementListWrapper.DefaultedSoundEntry> implements VersionedElementListWrapper, Selectable {
    private static final EnumMap<SoundCategory, SimpleOption<Double>> VOLUME_OPTS = Util.make(new EnumMap<>(SoundCategory.class), map -> {
        for (SoundCategory cat : SoundCategory.values()) {
            final SimpleOption.TooltipFactoryGetter<Double> getter;
            if (SoundCategories.TOOLTIPS.containsKey(cat)) {
                getter = SimpleOption.constantTooltip(SoundCategories.TOOLTIPS.get(cat));
            } else {
                getter = SimpleOption.emptyTooltip();
            }
            final SimpleOption<Double> option = new SimpleOption<>(SoundCategories.getOptionsTranslationKey(cat), getter, (prefix, value) -> {
                return value == 0.0 ? GameOptions.getGenericValueText(prefix, ScreenTexts.OFF) : GameOptions.getPercentValueText(prefix, value);
            }, SimpleOption.DoubleSliderCallbacks.INSTANCE, (double) MinecraftClient.getInstance().options.getSoundVolume(cat), (value) -> {
                MinecraftClient client = MinecraftClient.getInstance();
                client.getSoundManager().updateSoundVolume(cat, value.floatValue());
                client.options.setSoundVolume(cat, value.floatValue());
            });
            map.put(cat, option);
        }
    });

    public SoundList(MinecraftClient minecraftClient, int i, int j, int k, int l, int m) {
        super(minecraftClient, i, j, k, l, m);
        this.centerListVertically = false;
    }

    public static SoundList init(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
        return new SoundList(client, width, height, top, bottom, itemHeight);
    }

    @Override
    protected int getScrollbarPositionX() {
        return super.getScrollbarPositionX() + 32;
    }

    @Override
    public int getRowWidth() {
        return 400;
    }

    @Override
    public int addSingleOptionEntry(Object option, boolean editable) {
        DefaultedSoundEntry entry = VersionedSoundEntry.create(this.client.options, this.width, option);
        if (!editable) {
            entry.getWidgets().stream().map(ClickableWidget.class::cast).forEach(widget -> widget.active = false);
        }
        return this.addEntry(entry);
    }

    @Override
    public int addOptionEntry(Object firstOption, @Nullable Object secondOption) {
        return this.addEntry(VersionedSoundEntry.createDouble(this.client.options, this.width, firstOption, secondOption));
    }

    @Override
    public int addCategory(SoundCategory cat) {
        return this.addSingleOptionEntry(this.createCustomizedOption(cat));
    }

    @Override
    public int addReadOnlyCategory(SoundCategory cat) {
        return this.addSingleOptionEntry(this.createCustomizedOption(cat), false);
    }

    @Override
    public void addAllCategory(SoundCategory[] categories) {
        this.addAll(Arrays.stream(categories).map(this::createCustomizedOption).toArray());
    }

    @Override
    public int addGroup(SoundCategory group, ButtonWidget.PressAction pressAction) {
        return super.addEntry(VersionedSoundEntry.createGroup(this.client.options, this.createCustomizedOption(group), this.width, pressAction));
    }

    @Override
    public void setDimensionsImpl(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean mouseScrolledImpl(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return this.mouseScrolled(mouseX, mouseY, verticalAmount);
    }

    @Override
    public void addDrawable(Object option, Object button) {
        this.addEntry(VersionedSoundEntry.newInstance(ImmutableMap.of(option, button)));
    }

    private SimpleOption<?> createCustomizedOption(SoundCategory category) {
        final SimpleOption<Double> option = Objects.requireNonNull(VOLUME_OPTS.get(category));
        if (SoundCategories.TOGGLEABLE_CATS.getOrDefault(category, false)) {
            final Text tooltip = SoundCategories.TOOLTIPS.getOrDefault(category, Text.of(""));
            return SimpleOption.ofBoolean(option.toString(),
                    tooltip.equals(Text.empty()) ? SimpleOption.emptyTooltip() : SimpleOption.constantTooltip(tooltip),
                    option.getValue() > 0, value -> option.setValue(value ? 1.0 : 0.0)
            );
        }
        return option;
    }
}
