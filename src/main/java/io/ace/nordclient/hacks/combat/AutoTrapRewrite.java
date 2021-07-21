package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.HoleUtil;
import io.ace.nordclient.utilz.InventoryUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class AutoTrapRewrite extends Hack {

    Setting range;
    Setting delay;
    Setting debug;
    Setting trappedCheck;
    Setting toggleTicks;
    Setting extraHead;
    Setting headFixMode;
    Setting rotate;
    Setting silentSwitch;

    public AutoTrapRewrite() {
        super("AutoTrapRewrite", Category.COMBAT, 679631);
        CousinWare.INSTANCE.settingsManager.rSetting(range = new Setting("Range", this, 5.5, 1, 7, false, "AutoTrapRewriteRange", true));
        CousinWare.INSTANCE.settingsManager.rSetting(delay = new Setting("Delay", this, 1, 0, 6, true, "AutoTrapRewriteDelay", true));
        CousinWare.INSTANCE.settingsManager.rSetting(rotate = new Setting("Rotate", this, false, "AutoTrapRewriteRotate", true));
        CousinWare.INSTANCE.settingsManager.rSetting(trappedCheck = new Setting("TrappedCheck", this, true, "AutoTrapRewriteTrapCheck", true));
        CousinWare.INSTANCE.settingsManager.rSetting(extraHead = new Setting("ExtraHead", this, true, "AutoTrapRewriteExtraHead", true));
        CousinWare.INSTANCE.settingsManager.rSetting(toggleTicks = new Setting("ToggleTicks", this, 8, 0, 20, true, "AutoTrapRewriteToggleTicks", true));
        ArrayList<String> headFixModes = new ArrayList<>();
        headFixModes.add("Off");
        headFixModes.add("On");
        headFixModes.add("Auto");
        CousinWare.INSTANCE.settingsManager.rSetting(headFixMode = new Setting("HeadFix", this, "Auto", headFixModes, "AutoTrapRewriteHeadFixModes", true));
        CousinWare.INSTANCE.settingsManager.rSetting(debug = new Setting("Debug", this, false, "AutoTrapRewriteDebug", true));
        CousinWare.INSTANCE.settingsManager.rSetting(silentSwitch = new Setting("SilentSwitch", this, false, "AutoTrapRewriteSilentSwitch", true));

    }

    static Thread thread;
    static ArrayList<Vec3d> placeNoDirection = new ArrayList<>();
    static private EntityPlayer closestTarget;
    static int t = 0;
    static int toggleTick = 0;
    int startingHand;
    int obsidianSlot = 0;

    public void onUpdate() {
            toggleTick++;
            t++;
            for (Vec3d vec : placeNoDirection) {
                if (t >= delay.getValInt()) {
                    if (BlockInteractionHelper.canBlockBePlaced(closestTarget.getPositionVector().add(vec))) {
                        BlockPos place = new BlockPos(closestTarget.getPositionVector().add(vec).x, closestTarget.getPositionVector().add(vec).y, closestTarget.getPositionVector().add(vec).z);
                        if (mc.world.mayPlace(Blocks.OBSIDIAN, place, false, EnumFacing.UP, mc.player)) {
                            if (silentSwitch.getValBoolean()) mc.player.connection.sendPacket(new CPacketHeldItemChange(obsidianSlot));
                            else mc.player.inventory.currentItem = obsidianSlot;
                            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                            if (headFixMode.getValString().equalsIgnoreCase("on")) {
                                if (vec.equals(new Vec3d(-1, 2, 0)) || vec.equals(new Vec3d(1, 2, 0)) || vec.equals(new Vec3d(0, 2, 1)) || vec.equals(new Vec3d(0, 2, -1))) {
                                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698D, mc.player.posZ, true));
                                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7531999805211997D, mc.player.posZ, true));

                                }
                            }
                            if (headFixMode.getValString().equalsIgnoreCase("auto")) {
                                if (mc.player.posY <= (closestTarget.posY - .6)) {
                                    if (vec.equals(new Vec3d(-1, 2, 0)) || vec.equals(new Vec3d(1, 2, 0)) || vec.equals(new Vec3d(0, 2, 1)) || vec.equals(new Vec3d(0, 2, -1))) {
                                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698D, mc.player.posZ, true));
                                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7531999805211997D, mc.player.posZ, true));
                                    }
                                }
                            }
                            if (rotate.getValBoolean()) BlockInteractionHelper.placeBlockScaffold(place);
                            else BlockInteractionHelper.placeBlockScaffoldNoRotate(place);
                            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                            if (silentSwitch.getValBoolean()) mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
                            else mc.player.inventory.currentItem = startingHand;

                            t = 0;
                            if (debug.getValBoolean()) Command.sendClientSideMessage("Single");
                        }
                    }
                }
            }
                if (toggleTick >= toggleTicks.getValInt()) {
                    toggleTick = 0;
                    this.disable();
                }

            }


        public void onDisable() {
            placeNoDirection.clear();
            toggleTick = 0;
            t = 0;

            if (thread != null) {
                thread.interrupt();
                if (debug.getValBoolean())  Command.sendClientSideMessage("Stopped Thread");

            }
        }

        public void onEnable() {
        if (mc.world == null || mc.player == null) return;
            startingHand = mc.player.inventory.currentItem;
            int obsidianHand = InventoryUtil.findBlockInHotbarObiEchestRandom();
            if (obsidianHand == -1) {
                Command.sendClientSideMessage("No Obsidian Toggling!");
                this.disable();
            } else {
                obsidianSlot = obsidianHand;
            }
            getPlaceArea();


        }



        public void getPlaceArea() {
            findClosestTarget();
            if (closestTarget == null) {
                this.disable();
                return;
            }
            if (mc.player.getDistance(closestTarget) <= range.getValDouble()) {
                if (BlockInteractionHelper.canBlockBePlaced(closestTarget.getPositionVector().add(1, 0, 0))) {
                    placeNoDirection.add(new Vec3d(1, 0, 0));
                }
                if (BlockInteractionHelper.canBlockBePlaced(closestTarget.getPositionVector().add(0, 0, 1))) {
                    placeNoDirection.add(new Vec3d(0, 0, 1));
                }
                if (BlockInteractionHelper.canBlockBePlaced(closestTarget.getPositionVector().add(-1, 0, 0))) {
                    placeNoDirection.add(new Vec3d(-1, 0, 0));
                }

                if (BlockInteractionHelper.canBlockBePlaced(closestTarget.getPositionVector().add(0, 0, -1))) {
                    placeNoDirection.add(new Vec3d(0, 0, -1));
                }
                if (BlockInteractionHelper.canBlockBePlaced(closestTarget.getPositionVector().add(1, 1, 0))) {
                    placeNoDirection.add(new Vec3d(1, 1, 0));
                }
                if (BlockInteractionHelper.canBlockBePlaced(closestTarget.getPositionVector().add(0, 1, 1))) {
                    placeNoDirection.add(new Vec3d(0, 1, 1));
                }
                if (BlockInteractionHelper.canBlockBePlaced(closestTarget.getPositionVector().add(-1, 1, 0))) {
                    placeNoDirection.add(new Vec3d(-1, 1, 0));
                }
                if (BlockInteractionHelper.canBlockBePlaced(closestTarget.getPositionVector().add(0, 1, -1))) {
                    placeNoDirection.add(new Vec3d(0, 1, -1));
                }
                if (BlockInteractionHelper.canBlockBePlaced(closestTarget.getPositionVector().add(0, 2, -1)) && mc.player.getDistance(closestTarget.getPositionVector().add(0, 2, -1).x, closestTarget.getPositionVector().add(0, 2, -1).y, closestTarget.getPositionVector().add(0, 2, -1).z) <= range.getValDouble()) {
                    placeNoDirection.add(new Vec3d(0, 2, -1));
                } else if (BlockInteractionHelper.canBlockBePlaced(closestTarget.getPositionVector().add(0, 2, 1)) && mc.player.getDistance(closestTarget.getPositionVector().add(0, 2, 1).x, closestTarget.getPositionVector().add(0, 2, 1).y, closestTarget.getPositionVector().add(0, 2, 1).z) <= range.getValDouble()) {
                    placeNoDirection.add(new Vec3d(0, 2, 1));
                } else if (BlockInteractionHelper.canBlockBePlaced(closestTarget.getPositionVector().add(1, 2, 0)) && mc.player.getDistance(closestTarget.getPositionVector().add(1, 2, 0).x, closestTarget.getPositionVector().add(1, 2, 0).y, closestTarget.getPositionVector().add(1, 2, 0).z) <= range.getValDouble()) {
                    placeNoDirection.add(new Vec3d(1, 2, 0));
                } else {
                    placeNoDirection.add(new Vec3d(-1, 2, 0));
                }
                placeNoDirection.add(new Vec3d(0, 2, 0));
                if (extraHead.getValBoolean()) placeNoDirection.add(new Vec3d(0, 3, 0));
            }
        }


    private void findClosestTarget() {
        final List<EntityPlayer> playerList = mc.world.playerEntities;
        closestTarget = null;
        for (final EntityPlayer target : playerList) {
            if (target == mc.player) {
                continue;
            }
            if (FriendManager.isFriend(target.getName())) {
                continue;
            }
            if (target.getHealth() <= 0.0f) {
                continue;
            }
            if (trappedCheck.getValBoolean()) {
                if (HoleUtil.isTrapped(new BlockPos(target.posX, target.posY, target.posZ))) {
                    continue;
                }
            }
            if (closestTarget == null) {
                closestTarget = target;
            }
            else {
                if (mc.player.getDistance(target) >= mc.player.getDistance(closestTarget)) {
                    continue;
                }
                closestTarget = target;
            }
        }
    }

}
