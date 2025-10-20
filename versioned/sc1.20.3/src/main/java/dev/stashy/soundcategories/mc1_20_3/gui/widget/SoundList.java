package dev.stashy.soundcategories.mc1_20_3.gui.widget;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;

public class SoundList extends EntryListWidget<OptionListWidget.WidgetEntry> implements VersionedElementListWrapper {
    public SoundList(MinecraftClient minecraftClient, int i, int j, int k, int l) {
        super(minecraftClient, i, j, k, l);
        this.centerListVertically = false;
    }

    public static SoundList init(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
        return new SoundList(client, width, bottom - top, top, itemHeight);
    }

    @Override
    public void setDimensionsImpl(int width, int height) {
        super.setDimensions(width, height);
    }

    @Override
    public boolean mouseScrolledImpl(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return this.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public void addDrawable(Object option, ClickableWidget button) {
        this.addEntry(VersionedSoundEntry.newInstance(Map.of(option, button)));
    }

    private SimpleOption<?> createCustomizedOption(SoundCategory category) {
        final SimpleOption<Double> option = this.client.options.getSoundVolumeOption(category);
        if (SoundCategories.TOGGLEABLE_CATS.getOrDefault(category, false)) {
            final Text tooltip = SoundCategories.TOOLTIPS.getOrDefault(category, Text.empty());
            return SimpleOption.ofBoolean(option.toString(),
                    b -> (tooltip.equals(Text.empty())) ? null : Tooltip.of(tooltip),
                    option.getValue() > 0,
                    value -> option.setValue(value ? 1.0 : 0.0)
            );
        }
        return option;
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
        var entry = VersionedSoundEntry.create(this.client.options, this.width, option);
        if (!editable) {
            entry.getWidgets().forEach(widget -> widget.active = false);
        }
        return this.addEntry((OptionListWidget.WidgetEntry) entry);
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
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
