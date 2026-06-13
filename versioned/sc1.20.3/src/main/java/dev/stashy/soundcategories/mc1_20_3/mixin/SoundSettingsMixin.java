package dev.stashy.soundcategories.mc1_20_3.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.screen.VersionedSoundGroupOptionsScreen;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.SoundOptionsScreen;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(SoundOptionsScreen.class)
public abstract class SoundSettingsMixin extends GameOptionsScreen {
    @Shadow
    private OptionListWidget optionButtons;

    public SoundSettingsMixin(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    @WrapOperation(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/option/SoundOptionsScreen;getVolumeOptions()[Lnet/minecraft/client/option/SimpleOption;"))
    private SimpleOption<?>[] soundcategories$filterVanillaSoundOptions(SoundOptionsScreen instance, Operation<SimpleOption<?>[]> original) {
        return Arrays.stream(SoundCategories.filterVanillaCategory()).map(this.client.options::getSoundVolumeOption).toArray(SimpleOption[]::new);
    }

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/OptionListWidget;addAll([Lnet/minecraft/client/option/SimpleOption;)V", ordinal = 1, shift = At.Shift.AFTER))
    private void soundcategories$addCustomSoundWidgets(CallbackInfo ci) {
        for (SoundCategory master : SoundCategories.filterCustomizedMasterCategory()) {
            OptionListWidget.WidgetEntry widget = VersionedElementListWrapper.VersionedSoundEntry.createGroup(this.gameOptions, this.client.options.getSoundVolumeOption(master), this.width, button -> this.client.setScreen(VersionedSoundGroupOptionsScreen.newInstance(this, this.gameOptions, master)));
            this.optionButtons.addEntry(widget);
        }
    }
}
