package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.MotionUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.*;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class InventoryMove extends Hack {

    Setting sprint;
    Setting motion;
    Setting useItem;
    Setting sneak;
    Setting time;

    public InventoryMove() {
        super("InventoryMove", Category.MOVEMENT, -1);
        CousinWare.INSTANCE.settingsManager.rSetting(sprint = new Setting("StopSprint", this, true, "InventoryModeStopSprint", true));
        CousinWare.INSTANCE.settingsManager.rSetting(motion = new Setting("StopMotion", this, true, "InventoryModeStopMotion", true));
        CousinWare.INSTANCE.settingsManager.rSetting(useItem = new Setting("StopUseItem", this, false, "InventoryModeStopUseItem", true));
        CousinWare.INSTANCE.settingsManager.rSetting(sneak = new Setting("Sneak", this, false, "InventoryModeSneak", true));
        CousinWare.INSTANCE.settingsManager.rSetting(time = new Setting("ActionTime", this, 4, 0, 20, true, "InventoryMoveTime", true));
    }

    boolean cancelEat = false;
    boolean cancelSprint = false;
    boolean cancelMotion = false;
    int cancelEatTime = 0;
    int cancelSprintTime = 0;
    int cancelMotionTime = 0;
    @Override
    public void onUpdate() {
        if (mc.currentScreen instanceof GuiInventory) {
            mc.currentScreen.allowUserInput = true;
            if (Keyboard.isKeyDown(Keyboard.KEY_W)) mc.player.movementInput.moveForward = 1;

        }
        if (cancelSprint) {
            cancelSprintTime++;
            if (cancelSprintTime < time.getValInt()) {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            } else {
                cancelSprint = false;
                cancelSprintTime = 0;
            }
        }

        if (cancelMotion) {
            cancelMotionTime++;
            if (cancelMotionTime < time.getValInt()) {
                mc.player.motionZ = 0;
                mc.player.motionX = 0;
                mc.player.moveStrafing = 0;
                mc.player.moveForward = 0;
            } else {
                cancelMotion = false;
                cancelMotionTime = 0;
            }
        }

    }

    @Listener
    public void onUpdate(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketClickWindow) {
            if (sprint.getValBoolean()) if (mc.player.isSprinting()) {
                cancelSprint = true;
            }
            if (motion.getValBoolean()) if (MotionUtil.isMoving()) {
                cancelMotion = true;
            }
            if (sneak.getValBoolean()) if (!mc.player.isSneaking()) mc.player.setSneaking(true);

            if (mc.player.itemStackMainHand.getItem() instanceof ItemFood || mc.player.itemStackMainHand.getItem() instanceof ItemBow) {
                if (mc.gameSettings.keyBindUseItem.isKeyDown() && useItem.getValBoolean()) cancelEat = true;
            }
        }
        if (cancelEat) {
            if (event.getPacket() instanceof CPacketPlayerTryUseItem || event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock || event.getPacket() instanceof CPacketPlayerDigging) {
                if (mc.player.itemStackMainHand.getItem() instanceof ItemFood || mc.player.itemStackMainHand.getItem() instanceof ItemBow) {
                    if (mc.gameSettings.keyBindUseItem.isKeyDown() && useItem.getValBoolean()) event.setCanceled(true);
                }
            }
        }

    }
    @SubscribeEvent
    public void keyboardEvent(GuiScreenEvent.KeyboardInputEvent event) {
        event.setResult(Event.Result.ALLOW);
        event.getGui().allowUserInput = true;
    }
//
    @SubscribeEvent
    public void eatEvent(LivingEntityUseItemEvent event) {
        if (cancelEat) {
            cancelEatTime++;
            if (cancelEatTime < time.getValInt()) {
                event.setCanceled(true);
            }
            if (cancelEatTime >= time.getValInt()) {
                cancelEat = false;
                cancelEatTime = 0;
            }
        }
    }

    public void onEnable() {
        cancelEat = false;
        cancelSprint = false;
        cancelMotion = false;
        cancelEatTime = 0;
        cancelSprintTime = 0;
        cancelMotionTime = 0;
    }
}
