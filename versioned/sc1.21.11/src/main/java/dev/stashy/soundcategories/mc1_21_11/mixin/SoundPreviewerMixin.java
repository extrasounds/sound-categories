package dev.stashy.soundcategories.mc1_21_11.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.runtime.RecordSoundEventInvoker;
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

import java.util.Objects;

@Mixin(SoundPreviewer.class)
public abstract class SoundPreviewerMixin {
    @Unique
    private static final Identifier[] SE_EMPTIES = new Identifier[]{Identifier.ofVanilla("intentionally_empty")};

    @Shadow
    @Nullable
    private static SoundCategory category;

    @ModifyVariable(method = "preview", at = @At("STORE"), ordinal = 0)
    private static SoundEvent soundcategories$applyPreviewSound(SoundEvent original) {
        if (original != SoundEvents.INTENTIONALLY_EMPTY || category == null) {
            return original;
        }

        Identifier[] ids = SoundCategories.PREVIEW_SOUNDS.getOrDefault(category, SE_EMPTIES);
        try {
            RecordSoundEventInvoker invoker = Objects.requireNonNull(RecordSoundEventInvoker.INSTANCE);
            return (SoundEvent) invoker.invokeSoundEvent$of(ids[(int) (Math.random() * ids.length)]);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("can not invoke in record class 'SoundEvent'", ex);
        }
        return original;
    }

    @ModifyExpressionValue(method = "preview", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/PositionedSoundInstance;ui(Lnet/minecraft/sound/SoundEvent;FF)Lnet/minecraft/client/sound/PositionedSoundInstance;"))
    private static PositionedSoundInstance soundcategories$changeVolume(PositionedSoundInstance original) {
        if (category == null || !SoundCategories.PREVIEW_SOUNDS.containsKey(category)) {
            return original;
        }

        SoundCategory masterCat = SoundCategories.PARENTS.getOrDefault(category, null);
        float volume = (masterCat == null) ? 1.0f : MinecraftClient.getInstance().options.getCategorySoundVolume(masterCat);
        return new PositionedSoundInstance(original.getId(), category, volume, 1f, SoundInstance.createRandom(), false, 0, SoundInstance.AttenuationType.NONE, 0, 0, 0, true);
    }
}
