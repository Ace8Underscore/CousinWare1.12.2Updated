package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.RainbowUtil;
import io.ace.nordclient.utilz.Setting;
import io.ace.nordclient.utilz.render.NordTessellator;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class ChorusFinder extends Hack {

    BlockPos tpPos;

    Setting render;
    Setting r;
    Setting g;
    Setting b;
    Setting rainbow;

    int delay = 0;

    public ChorusFinder() {
        super("ChorusFinder", Category.COMBAT, 14525233);
        CousinWare.INSTANCE.settingsManager.rSetting(render = new Setting("Render", this, true, "ChorusFinderRender", true));
        CousinWare.INSTANCE.settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "ChorusLagRainbow", true));
        CousinWare.INSTANCE.settingsManager.rSetting(r = new Setting("Red", this, 255, 0, 255, true, "ChorusLagRed", !rainbow.getValBoolean()));
        CousinWare.INSTANCE.settingsManager.rSetting(g = new Setting("Green", this, 26, 0, 255, true, "ChorusLagGreen", !rainbow.getValBoolean()));
        CousinWare.INSTANCE.settingsManager.rSetting(b = new Setting("Blue", this, 42, 0, 255, true, "ChorusLagBlue", !rainbow.getValBoolean()));

    }

    public void onUpdate() {
        if (tpPos == null) return;
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity.getDistance(tpPos.getX(), tpPos.getY(), tpPos.getZ()) < 1) {
                tpPos = null;
                break;
            }
        }
        if (rainbow.getValBoolean()) RainbowUtil.settingRainbow(r, g, b);

    }

    public void onWorldRender(RenderEvent event) {
        if (tpPos != null && render.getValBoolean()) {
            NordTessellator.drawBoundingBoxBlockPos(tpPos, 1, 243, 0, 127, 255);
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
