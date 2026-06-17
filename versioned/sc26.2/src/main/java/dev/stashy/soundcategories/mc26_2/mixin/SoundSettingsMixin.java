package dev.stashy.soundcategories.mc26_2.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.screen.VersionedSoundGroupOptionsScreen;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.SoundOptionsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(SoundOptionsScreen.class)
public abstract class SoundSettingsMixin extends OptionsSubScreen {
    public SoundSettingsMixin(Screen parent, Options gameOptions, Component title) {
        super(parent, gameOptions, title);
    }

    @WrapOperation(method = "addOptions", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/options/SoundOptionsScreen;getAllSoundOptionsExceptMaster()[Lnet/minecraft/client/OptionInstance;"))
    private OptionInstance<?>[] soundcategories$filterVanillaSoundOptions(SoundOptionsScreen instance, Operation<OptionInstance<?>[]> original) {
        return Arrays.stream(SoundCategories.filterVanillaCategory()).map(this.minecraft.options::getSoundSourceOptionInstance).toArray(OptionInstance[]::new);
    }

    @Inject(method = "addOptions", at = @At("TAIL"))
    private void soundcategories$addCustomSoundWidgets(CallbackInfo ci) {
        if (this.list == null) {
            return;
        }

        for (var master : SoundCategories.filterCustomizedMasterCategory()) {
            OptionsList.AbstractEntry widget = VersionedElementListWrapper.VersionedSoundEntry.createGroup(this.options, this.minecraft.options.getSoundSourceOptionInstance(master), this.width, button -> this.minecraft.gui.setScreen(VersionedSoundGroupOptionsScreen.newInstance(this, this.options, master)));
            this.list.addEntry(widget);
        }
    }
}
