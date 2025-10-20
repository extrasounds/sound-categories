package dev.stashy.soundcategories.mc1_19.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.stashy.soundcategories.shared.SoundCategories;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @Shadow
    @Final
    private Object2FloatMap<SoundCategory> soundVolumeLevels;

    @Unique
    private static SoundCategory currentCategory = null;

    @Inject(method = "method_33667", at = @At("HEAD"))
    private static void soundcategories$storeCategory(GameOptions.Visitor instance, SoundCategory soundCategory, Float value, CallbackInfoReturnable<Float> cir) {
        currentCategory = soundCategory;
    }

    @ModifyExpressionValue(method = "method_33667", at = @At(value = "CONSTANT", args = "floatValue=1.0F"))
    private static float soundcategories$changeDefault(float original) {
        return SoundCategories.DEFAULT_LEVELS.getOrDefault(currentCategory, original);
    }

    @Inject(at = @At(value = "HEAD"), method = "load")
    private void soundcategories$putAllCustomVolumes(CallbackInfo ci) {
        this.soundVolumeLevels.putAll(SoundCategories.DEFAULT_LEVELS);
    }
}
