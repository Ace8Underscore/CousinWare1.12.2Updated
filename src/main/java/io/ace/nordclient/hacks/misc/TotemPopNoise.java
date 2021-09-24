package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.sound.Neverlose;
import net.minecraft.client.audio.ISound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class TotemPopNoise extends Hack {

    public TotemPopNoise() {
        super("TotemPopNoise", Category.MISC, 3963319);
    }

    private final ISound sound = Neverlose.sound;

    @Listener
    public void onUpdate(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketEntityStatus) {
            final SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
            if (packet.getOpCode() == 35 && packet.getEntity(mc.world) instanceof EntityPlayer) {
                mc.world.playSound(mc.player, packet.getEntity(mc.world).posX, packet.getEntity(mc.world).posY, packet.getEntity(mc.world).posZ, new SoundEvent(new ResourceLocation("custom/neverlose.ogg")), SoundCategory.PLAYERS, 1, 1 );
                //SoundPlayer.playSound(new SoundEvent(new ResourceLocation("custom/neverlose.ogg")));
                }
            }
    }

    public void onEnable() {
        if (mc.world == null) return;
        mc.world.playSound(mc.player, mc.player.posX, mc.player.posY, mc.player.posZ, new SoundEvent(new ResourceLocation("custom/totem.ogg")), SoundCategory.PLAYERS, 100, 100 );
        mc.soundHandler.playSound(sound);
    }
}
