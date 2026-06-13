package dev.stashy.soundcategories.shared.runtime;

import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public abstract class RecordSoundEventProvider {
    @Nullable
    public static final RecordSoundEventProvider INSTANCE;

    static {
        RecordSoundEventProvider instance = null;
        try {
            Class<RecordSoundEventProvider> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "runtime.RecordSoundEventImpl");
            instance = clazz.getConstructor().newInstance();
        } catch (Exception ignore) {
        }
        INSTANCE = instance;
    }

    public abstract Object invokeSoundEvent$of(Identifier id) throws Exception;
}
