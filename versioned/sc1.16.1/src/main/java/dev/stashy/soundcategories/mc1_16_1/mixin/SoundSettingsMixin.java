package dev.stashy.soundcategories.mc1_16_1.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.screen.VersionedSoundGroupOptionsScreenWrapper;
import dev.stashy.soundcategories.shared.gui.widget.VersionedButtonWrapper;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import dev.stashy.soundcategories.shared.runtime.VersionedText;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.GameOptionsScreen;
import net.minecraft.client.gui.screen.options.SoundOptionsScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
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
        this.list = Objects.requireNonNull(VersionedElementListWrapper.newInstance(this.client, this.width, this.height, 32, this.height - 32, 25));

        this.list.addCategory(SoundCategory.MASTER);
        this.list.addAllCategory(SoundCategories.filterVanillaCategory());

        this.list.addDrawable(Option.SUBTITLES, Option.SUBTITLES.createButton(this.gameOptions, this.width / 2 - 75, 0, 150));

        for (SoundCategory category : SoundCategories.filterCustomizedMasterCategory()) {
            this.list.addGroup(category, button -> this.client.openScreen((Screen) VersionedSoundGroupOptionsScreenWrapper.newInstance(this, this.gameOptions, category)));
        }
        this.children.add(this.list);

        this.addButton(
                (ButtonWidget) VersionedButtonWrapper.newInstance(
                        this.width / 2 - 100, this.height - 27, 200, 20,
                        VersionedText.INSTANCE.getDoneText(), (button) -> {
                            this.client.options.write();
                            this.client.openScreen(this.parent);
                        }
                )
        );
    }

    @WrapOperation(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/options/SoundOptionsScreen;addButton(Lnet/minecraft/client/gui/widget/AbstractButtonWidget;)Lnet/minecraft/client/gui/widget/AbstractButtonWidget;"))
    private AbstractButtonWidget soundcategories$noopDrawableChild(SoundOptionsScreen instance, AbstractButtonWidget widget, Operation<AbstractButtonWidget> original) {
        return widget;
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/options/SoundOptionsScreen;renderBackground(Lnet/minecraft/client/util/math/MatrixStack;)V", shift = At.Shift.AFTER))
    private void soundcategories$drawList(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        this.list.render(matrices, mouseX, mouseY, delta);
    }
}
