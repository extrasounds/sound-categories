package dev.stashy.soundcategories.mc1_21.gui.screen;

import dev.stashy.soundcategories.shared.gui.screen.VersionedSoundOptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.sound.SoundCategory;

public class CustomSoundOptionsScreen extends VersionedSoundOptionsScreen {
    public CustomSoundOptionsScreen(Screen parent, GameOptions gameOptions) {
        super(parent, gameOptions);
    }

    @Override
    protected void initList() {
        this.list.addCategory(SoundCategory.MASTER);
        this.list.addAllCategory(this.filterVanillaCategory());
        this.list.addSingleOptionEntry(this.gameOptions.getSoundDevice());
        this.list.addAll(new SimpleOption<?>[]{this.gameOptions.getShowSubtitles(), this.gameOptions.getDirectionalAudio()});

        for (SoundCategory category : this.filterCustomizedMasterCategory()) {
            this.list.addGroup(category, button -> this.client.setScreen(new SoundGroupOptionsScreen(this, this.gameOptions, category)));
        }
    }

    @Override
    protected void addOptions() {
    }

    @Override
    protected void initBody() {
    }

    @Override
    protected void refreshWidgetPositions() {
        super.refreshWidgetPositions();
        this.list.setDimensionsImpl(this.width, this.layout.getContentHeight());
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return this.list.mouseScrolledImpl(mouseX, mouseY, horizontalAmount, verticalAmount);
    }
}
