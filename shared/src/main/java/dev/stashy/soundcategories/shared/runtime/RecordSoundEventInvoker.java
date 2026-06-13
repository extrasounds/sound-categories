package dev.stashy.soundcategories.shared.runtime;

import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public abstract class RecordSoundEventInvoker {
    @Nullable
    public static final RecordSoundEventInvoker INSTANCE;

    static {
        RecordSoundEventInvoker instance = null;
        try {
            Class<RecordSoundEventInvoker> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "runtime.RecordSoundEventImpl");
            instance = clazz.getConstructor().newInstance();
        } catch (Exception ignore) {
        }
        INSTANCE = instance;
    }

    public abstract Object invokeSoundEvent$of(Identifier id) throws Exception;
}
