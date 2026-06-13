package dev.stashy.soundcategories.mc1_21_2.runtime;

import dev.stashy.soundcategories.shared.runtime.RecordSoundEventInvoker;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RecordSoundEventImpl extends RecordSoundEventInvoker {
    private static final Class<SoundEvent> SOUND_EVENT_CLASS = SoundEvent.class;

    @Override
    public Object invokeSoundEvent$of(Identifier id) throws Exception {
        Method $of = SOUND_EVENT_CLASS.getMethod("of", Identifier.class);
        return $of.invoke(SOUND_EVENT_CLASS, id);
    }
}
