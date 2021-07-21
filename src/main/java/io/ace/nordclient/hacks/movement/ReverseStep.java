package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.network.Packet;

import java.util.ArrayList;

public class ReverseStep extends Hack {

    Setting fallMode;
    private Double y;
    int delay = 0;
    Packet lastPacket;

    public ReverseStep() {
        super("ReverseStep", Category.MOVEMENT, 10820258);
        ArrayList<String> fallModes = new ArrayList<>();
        fallModes.add("Fast");
        fallModes.add("Medium");
        fallModes.add("Slow");
        fallModes.add("2b");

        CousinWare.INSTANCE.settingsManager.rSetting(fallMode = new Setting("FallModes", this, "Slow", fallModes, "ReverseStepFallModes", true));

    }

    @Override
    public void onUpdate() {
        delay++;
        if (fallMode.getValString().equalsIgnoreCase("fast")) y = -4D;
        if (fallMode.getValString().equalsIgnoreCase("medium")) y = -2D;
        if (fallMode.getValString().equalsIgnoreCase("slow")) y = -1D;

        if (mc.player.onGround && !mc.player.isInWater() && !mc.player.isInLava() && !fallMode.getValString().equalsIgnoreCase("2b")) {
            mc.player.motionY = y;
        }

        if (!mc.player.isInWater() && !mc.player.isInLava() && fallMode.getValString().equalsIgnoreCase("2b") && mc.player.onGround) {
            mc.player.motionY *= .125;

        }

    }

}


