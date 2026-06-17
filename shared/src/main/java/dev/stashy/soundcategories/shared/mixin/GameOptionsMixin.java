package dev.stashy.soundcategories.shared.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.stashy.soundcategories.shared.SoundCategories;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Options.class)
public abstract class GameOptionsMixin {
    @Unique
    private SoundSource currentCategory = null;

    /**
     * Stores the current SoundCategory in loop.
     */
    @Inject(method = "createSoundSliderOptionInstance", at = @At("HEAD"))
    private void soundcategories$storeCategory(String key, SoundSource soundCategory, CallbackInfoReturnable<OptionInstance<?>> cir) {
        this.currentCategory = soundCategory;
    }

    /**
     * Modifies a Constant of the default sound volume that exists in {@link SoundCategories#DEFAULT_LEVELS} and matches {@link GameOptionsMixin#currentCategory}.<br>
     * default value is 1.0.
     *
     * @see Options#createSoundSliderOptionInstance
     * @see Options#Options
     */
    @ModifyExpressionValue(method = "createSoundSliderOptionInstance", at = @At(value = "CONSTANT", args = "doubleValue=1.0"))
    private double soundcategories$changeDefault(double value) {
        return SoundCategories.DEFAULT_LEVELS.getOrDefault(this.currentCategory, (float) value);
    }

    @ModifyExpressionValue(method = "createSoundSliderOptionInstance", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/OptionInstance;noTooltip()Lnet/minecraft/client/OptionInstance$TooltipSupplier;"))
    private OptionInstance.TooltipSupplier<?> soundcategories$modifyTooltip(OptionInstance.TooltipSupplier<?> original) {
        if (SoundCategories.TOOLTIPS.containsKey(this.currentCategory)) {
            final var tooltip = Tooltip.create(SoundCategories.TOOLTIPS.get(this.currentCategory));
            return value -> tooltip;
        }
        return original;
    }
}
