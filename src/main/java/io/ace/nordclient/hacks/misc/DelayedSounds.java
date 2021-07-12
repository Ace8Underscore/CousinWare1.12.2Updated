package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import net.minecraft.network.play.server.SPacketSoundEffect;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class DelayedSounds extends Hack {

    public DelayedSounds() {
        super("DelayedSounds", Category.MISC, 13698865);
    }

    @Override
    public void onUpdate() {

    }

    @Listener
    public void onUpdate(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSoundEffect) {
            event.setCanceled(true);
            //mc.getSoundHandler().playDelayedSound(ISound.AttenuationType..Type.getByName(String.valueOf(((SPacketSoundEffect) event.getPacket()).getSound().)), 1);
            //Sound.Type.getByName()
        }
    }
}
