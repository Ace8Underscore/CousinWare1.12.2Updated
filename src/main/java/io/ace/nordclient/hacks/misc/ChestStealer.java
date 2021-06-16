package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.hacks.Hack;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;

public class ChestStealer extends Hack {

    public ChestStealer() {
        super("ChestStealer", Category.MISC, 13278553);
    }

    public void onUpdate() {
        if (mc.currentScreen instanceof GuiChest) {
            for (int i = 0; i < ((GuiChest) mc.currentScreen).inventorySlots.getInventory().size() - 36; i++) {
                //Command.sendClientSideMessage(String.valueOf(i));
                if (mc.player.openContainer.getSlot(i).getStack().getItem() == Items.GOLDEN_APPLE && mc.player.openContainer.getSlot(i).getStack().getItemDamage() == 1) {
                    mc.playerController.windowClick(((GuiChest) mc.currentScreen).inventorySlots.windowId, i, 0, ClickType.QUICK_MOVE, mc.player);
                }
            }
        }
    }
}
