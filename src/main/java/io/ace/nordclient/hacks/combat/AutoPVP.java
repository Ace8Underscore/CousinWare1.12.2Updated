package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.HackManager;
import io.ace.nordclient.utilz.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class AutoPVP extends Hack {

    public AutoPVP() {
        super("AutoPVP", Category.COMBAT, -1);
    }

    boolean chorus;
    boolean eattingGap;
    int delay;

    public void onUpdate() {
        if (!HoleUtil.isBedrockHole(PlayerUtil.getPlayerPos()) && !BaritoneUtil.isActive()) {
            List<BlockPos> blocks = (BlockInteractionHelper.getSphere(PlayerUtil.getPlayerPos(), (float) 10, 10, false, true, 0));
            for (BlockPos block : blocks) {
                if (block == null) return;
                if (HoleUtil.isBedrockHole(block)) {
                    BaritoneUtil.setGoal(block);
                    break;
                }
            }
        }
        if (HoleUtil.isBedrockHole(PlayerUtil.getPlayerPos()) && mc.player.getHealth() + mc.player.getAbsorptionAmount() > 10 && !chorus) {
            int sword = InventoryUtil.findItemInHotbar(Items.DIAMOND_SWORD);
            if (sword != -1) {
                mc.player.inventory.currentItem = sword;
            }
        }
        if (mc.world.getBlockState(PlayerUtil.getPlayerPos().up(2)).getBlock().equals(Blocks.OBSIDIAN)) {
            if (!chorus) {
                leaveHole();
            } else {
                delay++;
            }
        }
        if (delay > 40) {
            chorus = false;
            delay = 0;
        }

    }

    public void leaveHole() {
        HackManager.getHackByName("AutoTrap").toggle();
        chorus = true;
        int chor = InventoryUtil.findItemInHotbar(Items.CHORUS_FRUIT);
        if (chor != -1) {
            mc.player.inventory.currentItem = chor;
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));

        } //
    }

    @SubscribeEvent
    public void finishEating(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() == mc.player) {
                eattingGap = false;
                chorus = false;

        }
    }

    @SubscribeEvent
    public void finishEating(LivingEntityUseItemEvent.Start event) {
        if (event.getEntity() == mc.player) {
            eattingGap = true;

        }
    }

    @SubscribeEvent
    public void finishEating(LivingEntityUseItemEvent.Stop event) {
        if (event.getEntity() == mc.player) {
            eattingGap = false;
            chorus = false;

        }
    }



}

