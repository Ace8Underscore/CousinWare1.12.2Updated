package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.NordTessellator;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class ChorusFinder extends Hack {

    BlockPos tpPos;

    Setting render;

    int delay = 0;

    public ChorusFinder() {
        super("ChorusFinder", Category.COMBAT, 14525233);
        CousinWare.INSTANCE.settingsManager.rSetting(render = new Setting("Render", this, true, "ChorusFinderRender", true));
    }

    public void onUpdate() {
        if (tpPos == null) return;
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity.getDistance(tpPos.getX(), tpPos.getY(), tpPos.getZ()) < 1) {
                tpPos = null;
            }
        }
    }

    public void onWorldRender(RenderEvent event) {
        if (tpPos != null && render.getValBoolean()) {
            NordTessellator.drawBoundingBoxBottomBlockPos(tpPos, 1, 243, 0, 127, 255);
        }
    }

    @Listener
    public void onUpdate(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSoundEffect) {
            if (((SPacketSoundEffect) event.getPacket()).getSound() == SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT) {
                tpPos = new BlockPos(((SPacketSoundEffect) event.getPacket()).getX(), ((SPacketSoundEffect) event.getPacket()).getY(), ((SPacketSoundEffect) event.getPacket()).getZ());
            }
        }
    }

}
