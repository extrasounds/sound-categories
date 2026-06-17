package dev.stashy.soundcategories.mc26_2.gui.widget;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.*;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class SoundList extends ContainerObjectSelectionList<OptionsList.AbstractEntry> implements VersionedElementListWrapper {
    public SoundList(Minecraft minecraftClient, int i, int j, int k, int l) {
        super(minecraftClient, i, j, k, l);
        this.centerListVertically = false;
    }

    public static SoundList init(Minecraft client, int width, int height, int top, int bottom, int itemHeight) {
        return new SoundList(client, width, bottom - top, top, itemHeight);
    }

    @Override
    public int getRowWidth() {
        return 310;
    }

    @Override
    public void setDimensionsImpl(int width, int height) {
        super.setSize(width, height);
    }

    @Override
    public boolean mouseScrolledImpl(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return this.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public void addDrawable(Object option, AbstractWidget button) {
        this.addEntry(Objects.requireNonNull(VersionedSoundEntry.newInstance(Map.of(option, button))));
    }

    private OptionInstance<?> createCustomizedOption(SoundSource category) {
        final OptionInstance<Double> option = this.minecraft.options.getSoundSourceOptionInstance(category);
        if (SoundCategories.TOGGLEABLE_CATS.getOrDefault(category, false)) {
            final Component tooltip = SoundCategories.TOOLTIPS.getOrDefault(category, Component.empty());
            return OptionInstance.createBoolean(option.toString(),
                    new OptionInstance.TooltipSupplier<>() {
                        @Override
                        public @org.jspecify.annotations.Nullable Tooltip apply(Boolean value) {
                            return (tooltip.equals(Component.empty())) ? null : Tooltip.create(tooltip);
                        }
                    },
                    option.get() > 0,
                    new OptionInstance.ValueUpdateListener<>() {
                        @Override
                        public void valueChanged(Boolean newValue) {
                            option.set(newValue ? 1.0 : 0.0);
                        }
                    }
            );
        }
        return option;
    }

    @Override
    public int addSingleOptionEntry(Object option, boolean editable) {
        var entry = VersionedSoundEntry.create(this.minecraft.options, this.width, option);
        if (!editable) {
            entry.getWidgets().forEach(widget -> widget.active = false);
        }
        return this.addEntry((OptionsList.AbstractEntry) entry);
    }

    @Override
    public int addOptionEntry(Object firstOption, @Nullable Object secondOption) {
        return this.addEntry(VersionedSoundEntry.createDouble(this.minecraft.options, this.width, firstOption, secondOption));
    }

    @Override
    public int addCategory(SoundSource cat) {
        return this.addSingleOptionEntry(this.createCustomizedOption(cat));
    }

    @Override
    public int addReadOnlyCategory(SoundSource cat) {
        return this.addSingleOptionEntry(this.createCustomizedOption(cat), false);
    }

    @Override
    public void addAllCategory(SoundSource[] categories) {
        this.addAll(Arrays.stream(categories).map(this::createCustomizedOption).toArray());
    }

    @Override
    public int addGroup(SoundSource group, Button.OnPress pressAction) {
        return super.addEntry(VersionedSoundEntry.createGroup(this.minecraft.options, this.createCustomizedOption(group), this.width, pressAction));
    }
}
