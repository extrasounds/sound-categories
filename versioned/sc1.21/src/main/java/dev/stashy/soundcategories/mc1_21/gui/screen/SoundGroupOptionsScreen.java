package dev.stashy.soundcategories.mc1_21.gui.screen;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.screen.AbstractSoundListedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

public class SoundGroupOptionsScreen extends AbstractSoundListedScreen {
    private final SoundCategory parentCategory;

    public SoundGroupOptionsScreen(Screen parent, GameOptions gameOptions, SoundCategory category) {
        super(parent, gameOptions, Text.translatable(SoundCategories.getOptionsTranslationKey(category)));
        this.parentCategory = category;
    }

    @Override
    protected void addOptions() {
        this.list.addReadOnlyCategory(this.parentCategory);
        this.list.addAllCategory(this.filterByParentCategory(this.parentCategory));
    }

    @Override
    protected void refreshWidgetPositions() {
        super.refreshWidgetPositions();
        this.list.setDimensionsImpl(this.width, this.layout.getContentHeight());
    }

    @Override
    protected void initBody() {
        this.addOptions();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return this.list.mouseScrolledImpl(mouseX, mouseY, horizontalAmount, verticalAmount);
    }
}
