package dev.stashy.soundcategories.mc1_21_9.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.stashy.soundcategories.shared.SoundCategories;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundPreviewer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SoundPreviewer.class)
public abstract class SoundPreviewerMixin {
    @Unique
    private static final Identifier[] SE_EMPTIES = new Identifier[]{SoundEvents.INTENTIONALLY_EMPTY.id()};

    @Shadow
    @Nullable
    private static SoundCategory category;

    @ModifyVariable(method = "preview", at = @At("STORE"), ordinal = 0)
    private static SoundEvent soundcategories$applyPreviewSound(SoundEvent original) {
        if (original != SoundEvents.INTENTIONALLY_EMPTY || category == null) {
            return original;
        }

        var ids = SoundCategories.PREVIEW_SOUNDS.getOrDefault(category, SE_EMPTIES);
        return SoundEvent.of(ids[(int) (Math.random() * ids.length)]);
    }

    @ModifyExpressionValue(method = "preview", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/PositionedSoundInstance;master(Lnet/minecraft/sound/SoundEvent;FF)Lnet/minecraft/client/sound/PositionedSoundInstance;"))
    private static PositionedSoundInstance soundcategories$changeVolume(PositionedSoundInstance original) {
        if (category == null || !SoundCategories.PREVIEW_SOUNDS.containsKey(category)) {
            return original;
        }

        var masterCat = SoundCategories.PARENTS.getOrDefault(category, null);
        float volume = (masterCat == null) ? 1.0f : MinecraftClient.getInstance().options.getCategorySoundVolume(masterCat);
        return new PositionedSoundInstance(original.getId(), category, volume, 1f, SoundInstance.createRandom(), false, 0, SoundInstance.AttenuationType.NONE, 0, 0, 0, true);
    }
}
