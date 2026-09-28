package dev.stashy.soundcategories.mc26_4.mixin;

import dev.stashy.soundcategories.shared.SoundCategories;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.SoundPreviewHandler;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SoundPreviewHandler.class)
public abstract class SoundPreviewerMixin {
    @Shadow
    @Nullable
    private static SoundSource previousCategory;

    @ModifyVariable(method = "preview", at = @At("STORE"), ordinal = 0)
    private static SoundInstance soundcategories$applyPreviewSound(@Nullable SoundInstance original, SoundManager soundManager, SoundSource category, float volume) {
        if (original != null || previousCategory == null) {
            return original;
        }

        var ids = SoundCategories.PREVIEW_SOUNDS.getOrDefault(previousCategory, null);
        if (ids == null) {
            return null;
        }

        var event = SoundEvent.createVariableRangeEvent(ids[(int) (ids.length * Math.random())]);
        return SimpleSoundInstance.forUI(event, 1f, volume);
    }
}
