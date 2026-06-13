package dev.stashy.soundcategories.mc1_20.gui.screen;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.screen.VersionedSoundGroupOptionsScreenWrapper;
import dev.stashy.soundcategories.shared.gui.widget.VersionedButtonWrapper;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import dev.stashy.soundcategories.shared.runtime.VersionedText;
import net.minecraft.client.gui.DrawContext;
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
    protected void init() {
        this.list = Objects.requireNonNull(VersionedElementListWrapper.newInstance(this.client, this.width, this.height, 32, this.height - 32, 25));
        this.list.addCategory(this.parentCategory);
        this.list.addAllCategory(this.filterByParentCategory(this.parentCategory));
        this.addDrawableChild((Drawable & Element & Selectable) this.list);

        this.addDrawableChild(
                (Drawable & Element & Selectable) VersionedButtonWrapper.newInstance(
                        this.width / 2 - 100, this.height - 27, 200, 20,
                        VersionedText.INSTANCE.getDoneText(), (button) -> {
                            this.client.options.write();
                            this.client.setScreen(this.parent);
                        }
                )
        );
    }

    @Override
    protected void initTabNavigation() {
        super.initTabNavigation();
        this.list.setDimensionsImpl(this.width, this.height - 64);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xffffff);
    }
}
