package dev.stashy.soundcategories.mc1_20_5.gui.screen;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.screen.VersionedSoundGroupOptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

public class SoundGroupOptionsScreen extends VersionedSoundGroupOptionsScreen {
    private final SoundCategory parentCategory;

    public SoundGroupOptionsScreen(Screen parent, GameOptions gameOptions, SoundCategory category) {
        super(parent, gameOptions, Text.translatable(SoundCategories.getOptionsTranslationKey(category)));
        this.parentCategory = category;
    }

    @Override
    protected void init() {
        super.init();

        this.list.addReadOnlyCategory(this.parentCategory);
        this.list.addAllCategory(this.filterByParentCategory(this.parentCategory));
    }

    @Override
    protected void initTabNavigation() {
        super.initTabNavigation();
        this.list.setDimensionsImpl(this.width, this.layout.getContentHeight());
    }
}
