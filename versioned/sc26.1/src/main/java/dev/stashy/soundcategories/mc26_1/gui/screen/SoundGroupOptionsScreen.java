package dev.stashy.soundcategories.mc26_1.gui.screen;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.screen.VersionedSoundGroupOptionsScreen;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;

public class SoundGroupOptionsScreen extends VersionedSoundGroupOptionsScreen {
    private final SoundSource parentCategory;

    public SoundGroupOptionsScreen(Screen parent, Options gameOptions, SoundSource category) {
        super(parent, gameOptions, Component.translatable(SoundCategories.getOptionsTranslationKey(category)));
        this.parentCategory = category;
    }

    @Override
    protected void addOptions() {
        this.list.addReadOnlyCategory(this.parentCategory);
        this.list.addAllCategory(this.filterByParentCategory(this.parentCategory));
    }

    @Override
    protected void repositionElements() {
        super.repositionElements();
        this.list.setDimensionsImpl(this.width, this.layout.getContentHeight());
    }

    @Override
    protected void addContents() {
        this.addOptions();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return this.list.mouseScrolledImpl(mouseX, mouseY, horizontalAmount, verticalAmount);
    }
}
