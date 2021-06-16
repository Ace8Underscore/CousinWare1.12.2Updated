package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.hacks.Hack;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ChestSwap extends Hack {

    public ChestSwap() {
        super("ChestSwap", Category.MISC, -1);
    }

    boolean switched = false;

    public void onUpdate() {
        if (mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA) {
            for (int i = 0; i < 45; i++) {
                if (!switched) {
                    if (mc.player.openContainer.getSlot(i).getStack().getItem() == Items.DIAMOND_CHESTPLATE) {
                        mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(0, i, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, mc.player);
                        //mc.playerController.windowClick(0, i, 0, ClickType.PICKUP, mc.player);
                        switched = true;
                        this.disable();
                        break;
                    }
                }
            }
        }
        else if (mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.DIAMOND_CHESTPLATE) {
            for (int j = 0; j < 45; j++) {
                if (!switched) {
                if (mc.player.openContainer.getSlot(j).getStack().getItem() == Items.ELYTRA) {
                    mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, j, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, mc.player);
                    switched = true;
                    this.disable();
                    break;

                }
                }
            }
        }
    }

    public void onDisable() {
        switched = false;
    }
}
