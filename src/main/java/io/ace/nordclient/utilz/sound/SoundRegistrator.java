package io.ace.nordclient.utilz.sound;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundRegistrator {
    public static final SoundEvent NEVERLOSE;
    //public static final SoundEvent SOUND_2;

    static {
        NEVERLOSE = addSoundsToRegistry("neverlose");
        //SOUND_2 = addSoundsToRegistry("sound2");
    }

    private static SoundEvent addSoundsToRegistry(String soundId) {
        ResourceLocation shotSoundLocation = new ResourceLocation(soundId);
        SoundEvent soundEvent = new SoundEvent(shotSoundLocation);
        soundEvent.setRegistryName(shotSoundLocation);
        return soundEvent;
    }


}