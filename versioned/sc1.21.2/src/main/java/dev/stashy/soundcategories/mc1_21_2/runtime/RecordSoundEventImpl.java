package dev.stashy.soundcategories.mc1_21_2.runtime;

import dev.stashy.soundcategories.shared.runtime.RecordSoundEventProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.lang.reflect.Method;

public class RecordSoundEventImpl extends RecordSoundEventProvider {
    private static final Class<SoundEvent> SOUND_EVENT_CLASS = SoundEvent.class;

    @Override
    public Object invokeSoundEvent$of(Identifier id) throws Exception {
        final Method $of;
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            $of = SOUND_EVENT_CLASS.getMethod("of", Identifier.class);
        } else {
            $of = SOUND_EVENT_CLASS.getMethod("method_47908", Identifier.class);
        }
        return $of.invoke(SOUND_EVENT_CLASS, id);
    }
}
