package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.exploit.SecretMine;
import io.ace.nordclient.managers.HackManager;
import io.ace.nordclient.managers.RotationManager;
import io.ace.nordclient.mixin.accessor.ICPacketPlayer;
import io.ace.nordclient.utilz.InventoryUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.EnumHand;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class AntiRegear extends Hack {

    Setting range;
    Setting autoSwitch;
    Setting rotate;

    public AntiRegear() {
        super("AntiRegear", Category.MISC, 9681786);
        CousinWare.INSTANCE.settingsManager.rSetting(range = new Setting("Range", this, 5.5, 0, 8, false, "AntiRegearRange", true));
        CousinWare.INSTANCE.settingsManager.rSetting(autoSwitch = new Setting("AutoSwitch", this, true, "AntiRegearAutoSwitch", true));
        CousinWare.INSTANCE.settingsManager.rSetting(rotate = new Setting("Rotate", this, false, "AntiReagearRotate", true));
    }

    float yaw;
    float pitch;
    boolean isSpoofing;

    @Override
    public void onUpdate() {
        if (getTargetBlock() != null) {
            if (autoSwitch.getValBoolean() && InventoryUtil.findItemInHotbar(Items.DIAMOND_PICKAXE) != -1) {
            }
            if (rotate.getValBoolean()) {
                isSpoofing = true;
                lookAtPacket(getTargetBlock().getPos().getX() + .5, getTargetBlock().getPos().getY() - 1, getTargetBlock().getPos().getZ() + .5, mc.player);
            }
            mc.player.swingArm(HackManager.getHackByName("Swing").isEnabled() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            SecretMine.secretSwap(getTargetBlock().getPos());

        }
        if (rotate.getValBoolean()) {
        if (getTargetBlock() == null) {
            resetRotations();
        }
        }

    }

    public TileEntity getTargetBlock() {
        TileEntity target = null;
        for (TileEntity shulker : mc.world.loadedTileEntityList) {
            if (shulker instanceof TileEntityShulkerBox) {
                if (shulker.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) <= (range.getValDouble() * range.getValDouble())) {
                    target = shulker;
                }

            }


        }


        return target;
    }

    public void resetRotations() {
        yaw = mc.player.rotationYaw;
        pitch = mc.player.rotationPitch;
    }

    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = RotationManager.calculateLookAt(px, py, pz, me);
        setYawAndPitch((float) v[0], (float) v[1]);
    }

    private void setYawAndPitch(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Listener
    public void onUpdate(PacketEvent.Send event) {
        if (rotate.getValBoolean()) {
            Packet packet = event.getPacket();
            if (packet instanceof CPacketPlayer) {
                ((ICPacketPlayer) packet).setYaw(yaw);
                ((ICPacketPlayer) packet).setPitch(pitch);
            }
        }
    }

    @Override
    public void onDisable() {
        if (rotate.getValBoolean()) {
            resetRotations();
        }
    }


}
