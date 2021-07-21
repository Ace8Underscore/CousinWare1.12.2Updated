package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.InventoryUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.Vec3d;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class Surround extends Hack {

    Setting noGhostBlocks;
    Setting placeDelay;

    int delay = 0;
    int startingSlot;
    int obiSlot;

    public Surround() {
        super("Surround", Category.COMBAT, 14379045);
        CousinWare.INSTANCE.settingsManager.rSetting(noGhostBlocks = new Setting("NoGhostBlocks", this, true, "SurroundNoGhostBlocks", true));
        CousinWare.INSTANCE.settingsManager.rSetting(placeDelay = new Setting("PlaceDelay", this, 2, 0, 20, true, "SurroundPlaceDelay", true));

    }


    private final Vec3d[] placeLocation = new Vec3d[]{new Vec3d(1, 0, 0), new Vec3d(0, 0, 1), new Vec3d(-1, 0, 0), new Vec3d(0, 0, -1), new Vec3d(1, -1, 0), new Vec3d(0, -1, 1), new Vec3d(-1, -1, 0), new Vec3d(0, -1, -1)};

    @Listener
    public void doTick() {
        delay++;
        Command.sendClientSideMessage(String.valueOf(delay));

    }

    @Override
    public void onEnable() {
         obiSlot = -1;
         startingSlot = mc.player.inventory.currentItem;
        if (InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN) == -1) this.disable();
        else obiSlot = InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN);
    }
}