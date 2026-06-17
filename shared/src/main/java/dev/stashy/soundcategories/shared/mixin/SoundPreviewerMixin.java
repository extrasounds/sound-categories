package dev.stashy.soundcategories.shared.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.stashy.soundcategories.shared.SoundCategories;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundPreviewHandler;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SoundPreviewHandler.class)
public abstract class SoundPreviewerMixin {
    @Unique
    private static final Identifier[] SE_EMPTIES = new Identifier[]{SoundEvents.EMPTY.location()};

    @Shadow
    @Nullable
    private static SoundSource previousCategory;

    @ModifyVariable(method = "preview", at = @At("STORE"), ordinal = 0)
    private static SoundEvent soundcategories$applyPreviewSound(SoundEvent original) {
        if (original != SoundEvents.EMPTY || previousCategory == null) {
            return original;
        }

        var ids = SoundCategories.PREVIEW_SOUNDS.getOrDefault(previousCategory, SE_EMPTIES);
        return SoundEvent.createVariableRangeEvent(ids[(int) (ids.length * Math.random())]);
    }

    @ModifyExpressionValue(method = "preview", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/sounds/SimpleSoundInstance;forUI(Lnet/minecraft/sounds/SoundEvent;FF)Lnet/minecraft/client/resources/sounds/SimpleSoundInstance;"))
    private static SimpleSoundInstance soundcategories$changeVolume(SimpleSoundInstance original) {
        if (previousCategory == null || !SoundCategories.PREVIEW_SOUNDS.containsKey(previousCategory)) {
            return original;
        }

        var masterCat = SoundCategories.PARENTS.getOrDefault(previousCategory, null);
        float volume = (masterCat == null) ? 1.0f : Minecraft.getInstance().options.getSoundSourceVolume(masterCat);
        return new SimpleSoundInstance(original.getIdentifier(), previousCategory, volume, 1f, SoundInstance.createUnseededRandom(), false, 0, SoundInstance.Attenuation.NONE, 0, 0, 0, true);
    }
}
