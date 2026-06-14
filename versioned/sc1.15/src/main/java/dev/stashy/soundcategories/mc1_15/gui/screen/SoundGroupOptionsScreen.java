package dev.stashy.soundcategories.mc1_15.gui.screen;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.screen.VersionedSoundGroupOptionsScreenWrapper;
import dev.stashy.soundcategories.shared.gui.widget.VersionedButtonWrapper;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import dev.stashy.soundcategories.shared.runtime.VersionedText;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.GameOptionsScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.TranslatableText;

import java.util.Objects;

public class SoundGroupOptionsScreen extends GameOptionsScreen implements VersionedSoundGroupOptionsScreenWrapper {
    private final SoundCategory parentCategory;
    private VersionedElementListWrapper list;

    public SoundGroupOptionsScreen(Screen parent, Object gameOptions, SoundCategory category) {
        super(parent, (GameOptions) gameOptions, new TranslatableText(SoundCategories.getOptionsTranslationKey(category)));
        this.parentCategory = category;
    }

    @Override
    protected void init() {
        this.list = Objects.requireNonNull(VersionedElementListWrapper.newInstance(this.minecraft, this.width, this.height, 32, this.height - 32, 25));
        this.list.addCategory(this.parentCategory);
        this.list.addAllCategory(this.filterByParentCategory(this.parentCategory));
        this.children.add(this.list);

        this.addButton(
                (AbstractButtonWidget) VersionedButtonWrapper.newInstance(
                        this.width / 2 - 100, this.height - 27, 200, 20,
                        VersionedText.INSTANCE.getDoneText(), (button) -> {
                            this.minecraft.options.write();
                            this.minecraft.openScreen(this.parent);
                        }
                )
        );
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        super.renderBackground();
        this.list.render(mouseX, mouseY, delta);
        drawCenteredString(this.font, this.title.asFormattedString(), this.width / 2, 20, 0xffffff);
        super.render(mouseX, mouseY, delta);
    }
}
