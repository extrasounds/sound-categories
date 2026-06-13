package dev.stashy.soundcategories.mc1_21.gui.screen;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.screen.VersionedSoundGroupOptionsScreenWrapper;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.util.Objects;

public class SoundGroupOptionsScreen extends GameOptionsScreen implements VersionedSoundGroupOptionsScreenWrapper {
    private final SoundCategory parentCategory;
    private VersionedElementListWrapper list;

    public SoundGroupOptionsScreen(Screen parent, Object gameOptions, SoundCategory category) {
        super(parent, (GameOptions) gameOptions, Text.translatable(SoundCategories.getOptionsTranslationKey(category)));
        this.parentCategory = category;
    }

    @Override
    protected void addOptions() {
        this.list = Objects.requireNonNull(VersionedElementListWrapper.newInstance(this.client, this.width, this.height, 32, this.height - 32, 25));
        this.list.addReadOnlyCategory(this.parentCategory);
        this.list.addAllCategory(this.filterByParentCategory(this.parentCategory));
        this.addDrawableChild((Drawable & Element & Selectable) this.list);
    }

    @Override
    protected void initTabNavigation() {
        super.initTabNavigation();
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
