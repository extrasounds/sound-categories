package dev.stashy.soundcategories.mc1_17_1.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.screen.VersionedSoundGroupOptionsScreen;
import dev.stashy.soundcategories.shared.gui.widget.VersionedButtonWrapper;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import dev.stashy.soundcategories.shared.runtime.VersionedText;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.SoundOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(SoundOptionsScreen.class)
public abstract class SoundSettingsMixin extends GameOptionsScreen {
    @Unique
    private VersionedElementListWrapper list;

    public SoundSettingsMixin(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    @Inject(method = "init", at = @At("HEAD"))
    private void soundcategories$registerScrollableWidget(CallbackInfo ci) {
        this.list = VersionedElementListWrapper.newInstance(this.client, this.width, this.height, 32, this.height - 32, 25);
        this.addDrawableChild(Objects.requireNonNull(this.list));

        this.list.addCategory(SoundCategory.MASTER);
        this.list.addAllCategory(SoundCategories.filterVanillaCategory());

        this.list.addDrawable(Option.SUBTITLES, Option.SUBTITLES.createButton(this.gameOptions, this.width / 2 - 75, 0, 150));

        for (SoundCategory category : SoundCategories.filterCustomizedMasterCategory()) {
            this.list.addGroup(category, button -> this.client.setScreen(VersionedSoundGroupOptionsScreen.newInstance(this, this.gameOptions, category)));
        }

        this.addDrawableChild(
                VersionedButtonWrapper.newInstance(
                        this.width / 2 - 100, this.height - 27, 200, 20,
                        VersionedText.INSTANCE.getDoneText(), (button) -> {
                            this.client.options.write();
                            this.client.setScreen(this.parent);
                        }
                )
        );
    }

    @WrapOperation(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/option/SoundOptionsScreen;addDrawableChild(Lnet/minecraft/client/gui/Element;)Lnet/minecraft/client/gui/Element;"))
    private Element soundcategories$noopDrawableChild(SoundOptionsScreen instance, Element widget, Operation<Element> original) {
        return widget;
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void soundcategories$drawTitle(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);
    }
}
