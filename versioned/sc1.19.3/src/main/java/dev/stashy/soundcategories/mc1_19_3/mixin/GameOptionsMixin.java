package dev.stashy.soundcategories.mc1_19_3.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.stashy.soundcategories.shared.SoundCategories;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @Unique
    private SoundCategory currentCategory = null;

    /**
     * Stores the current SoundCategory in loop.
     */
    @Inject(method = "createSoundVolumeOption", at = @At("HEAD"))
    private void soundcategories$storeCategory(String key, SoundCategory soundCategory, CallbackInfoReturnable<SimpleOption<?>> cir) {
        this.currentCategory = soundCategory;
    }

    /**
     * Modifies a Constant of the default sound volume that exists in {@link SoundCategories#DEFAULT_LEVELS} and matches {@link GameOptionsMixin#currentCategory}.<br>
     * default value is 1.0.
     *
     * @see GameOptions#createSoundVolumeOption
     * @see GameOptions#GameOptions
     */
    @ModifyExpressionValue(method = "createSoundVolumeOption", at = @At(value = "CONSTANT", args = "doubleValue=1.0"))
    private double soundcategories$changeDefault(double value) {
        return SoundCategories.DEFAULT_LEVELS.getOrDefault(this.currentCategory, (float) value);
    }

    @ModifyExpressionValue(method = "createSoundVolumeOption", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/SimpleOption;emptyTooltip()Lnet/minecraft/client/option/SimpleOption$TooltipFactory;"))
    private SimpleOption.TooltipFactory<?> soundcategories$modifyTooltip(SimpleOption.TooltipFactory<?> original) {
        if (SoundCategories.TOOLTIPS.containsKey(this.currentCategory)) {
            final Tooltip tooltip = Tooltip.of(SoundCategories.TOOLTIPS.get(this.currentCategory));
            return value -> tooltip;
        }
        return original;
    }
}
