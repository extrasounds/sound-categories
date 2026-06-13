package dev.stashy.soundcategories.mc1_15_2.gui.widget;

import com.google.common.collect.ImmutableMap;
import dev.stashy.soundcategories.mc1_15_2.runtime.TextImpl;
import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import dev.stashy.soundcategories.shared.runtime.VersionedText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.options.BooleanOption;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.options.Option;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class SoundList extends ElementListWidget<VersionedElementListWrapper.DefaultedSoundEntry> implements VersionedElementListWrapper {
    public SoundList(MinecraftClient minecraftClient, int i, int j, int k, int l, int m) {
        super(minecraftClient, i, j, k, l, m);
        this.centerListVertically = false;
    }

    public static SoundList init(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
        return new SoundList(client, width, height, top, bottom, itemHeight);
    }

    @Override
    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 32;
    }

    @Override
    public int getRowWidth() {
        return 400;
    }

    @Override
    public int addSingleOptionEntry(Object option, boolean editable) {
        DefaultedSoundEntry entry = VersionedSoundEntry.create(this.minecraft.options, this.width, option);
        if (!editable) {
            entry.getWidgets().stream().map(AbstractButtonWidget.class::cast).forEach(widget -> widget.active = false);
        }
        return this.addEntry(entry);
    }

    @Override
    public int addOptionEntry(Object firstOption, @Nullable Object secondOption) {
        return this.addEntry(VersionedSoundEntry.createDouble(this.minecraft.options, this.width, firstOption, secondOption));
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
        return super.addEntry(VersionedSoundEntry.createGroup(this.minecraft.options, this.createCustomizedOption(group), this.width, pressAction));
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

    private Option createCustomizedOption(SoundCategory category) {
        if (SoundCategories.TOGGLEABLE_CATS.getOrDefault(category, false)) {
            return new BooleanOption(SoundCategories.getOptionsTranslationKey(category),
                    gameOptions -> gameOptions.getSoundVolume(category) > 0,
                    (gameOptions, v) -> gameOptions.setSoundVolume(category, v ? 1.0f : 0.0f)
            );
        } else {
            return new DoubleOption(SoundCategories.getOptionsTranslationKey(category), 0, 1, 0,
                    gameOptions -> (double) gameOptions.getSoundVolume(category),
                    (gameOptions, value) -> gameOptions.setSoundVolume(category, value.floatValue()),
                    (gameOptions, doubleOption) -> {
                        Text text = new LiteralText(doubleOption.getDisplayPrefix());
                        double value = doubleOption.get(gameOptions);
                        if (value == 0.) {
                            text.append(VersionedText.INSTANCE.getOffText());
                        } else {
                            text.append((int)(value * 100) + "%");
                        }
                        return text.asFormattedString();
                    });
        }
    }
}
