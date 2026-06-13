package dev.stashy.soundcategories.mc1_16_2.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.stashy.soundcategories.shared.SoundCategories;
import net.minecraft.client.options.GameOptions;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @ModifyExpressionValue(method = "getSoundVolume", at = @At(value = "CONSTANT", args = "floatValue=1.0F"))
    private float soundcategories$putCustomVolumes(float original, SoundCategory soundCategory) {
        return SoundCategories.DEFAULT_LEVELS.getOrDefault(soundCategory, original);
    }
}
