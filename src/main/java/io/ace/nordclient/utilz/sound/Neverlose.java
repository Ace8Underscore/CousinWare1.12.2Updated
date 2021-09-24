package io.ace.nordclient.utilz.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

import javax.annotation.Nullable;

public class Neverlose {

    public static final ISound sound;
    private static final String song = "totem";
    private static final ResourceLocation loc = new ResourceLocation("custom/sounds/" + song + ".ogg");

    static {
        sound = new ISound() {

            private final Minecraft mc = Minecraft.getMinecraft();

            private final int pitch = 1;
            private final int volume = 1000000;

            @Override
            public ResourceLocation getSoundLocation() {
                return loc;
            }

            @Nullable
            public SoundEventAccessor createAccessor(SoundHandler soundHandler) {
                return new SoundEventAccessor(loc, "totem");
            }

            @Override
            public Sound getSound() {
                return new Sound(song, volume, pitch, 1, Sound.Type.SOUND_EVENT, false);
            }

            @Override
            public SoundCategory getCategory() {
                return SoundCategory.VOICE;
            }

            @Override
            public boolean canRepeat() {
                return true;
            }

            @Override
            public int getRepeatDelay() {
                return 2;
            }

            @Override
            public float getVolume() {
                return volume;
            }

            @Override
            public float getPitch() {
                return pitch;
            }

            @Override
            public float getXPosF() {
                return mc.player != null ? (float) mc.player.posX : 0;
            }

            @Override
            public float getYPosF() {
                return mc.player != null ? (float) mc.player.posY : 0;
            }

            @Override
            public float getZPosF() {
                return mc.player != null ? (float) mc.player.posZ : 0;
            }

            @Override
            public AttenuationType getAttenuationType() {
                return AttenuationType.LINEAR;
            }
        };
    }
}