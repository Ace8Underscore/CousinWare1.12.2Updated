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
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class AutoTrapRewrite extends Hack {

    Setting range;
    Setting delay;
    public Setting multiThread;
    Setting threadSleepTime;
    Setting debug;
    Setting trappedCheck;
    Setting toggleTicks;
    Setting extraHead;
    Setting headFixMode;

    public AutoTrapRewrite() {
        super("AutoTrapRewrite", Category.COMBAT, 679631);
        CousinWare.INSTANCE.settingsManager.rSetting(range = new Setting("Range", this, 5.5, 1, 7, false, "AutoTrapRewriteRange"));
        CousinWare.INSTANCE.settingsManager.rSetting(delay = new Setting("Delay", this, 1, 0, 6, true, "AutoTrapRewriteDelay"));
        CousinWare.INSTANCE.settingsManager.rSetting(trappedCheck = new Setting("TrappedCheck", this, true, "AutoTrapRewriteTrapCheck"));
        CousinWare.INSTANCE.settingsManager.rSetting(extraHead = new Setting("ExtraHead", this, true, "AutoTrapRewriteExtraHead"));
        CousinWare.INSTANCE.settingsManager.rSetting(toggleTicks = new Setting("ToggleTicks", this, 8, 0, 20, true, "AutoTrapRewriteToggleTicks"));
        ArrayList<String> headFixModes = new ArrayList<>();
        headFixModes.add("Off");
        headFixModes.add("On");
        headFixModes.add("Auto");
        CousinWare.INSTANCE.settingsManager.rSetting(headFixMode = new Setting("HeadFix", this, "Auto", headFixModes, "AutoTrapHeadFixModes"));
        CousinWare.INSTANCE.settingsManager.rSetting(multiThread = new Setting("MultiThread", this, true, "AutoTrapRewriteMultithread"));
        CousinWare.INSTANCE.settingsManager.rSetting(threadSleepTime = new Setting("ThreadMsSleep", this, 50, 0, 1000, true, "AutoTrapThreadSleepTime"));
        CousinWare.INSTANCE.settingsManager.rSetting(debug = new Setting("Debug", this, false, "AutoTrapRewriteDebug"));
    }

    static Thread thread;
    static ArrayList<Vec3d> placeNoDirection = new ArrayList<>();
    static private EntityPlayer closestTarget;
    static int t = 0;
    static int toggleTick = 0;
    int startingHand;
    int obsidianSlot = 0;

    public void onUpdate() {
        if (!multiThread.getValBoolean()) {
            toggleTick++;
            t++;
            for (Vec3d vec : placeNoDirection) {
                if (t >= delay.getValInt()) {
                    if (BlockInteractionHelper.canBlockBePlaced(closestTarget.getPositionVector().add(vec))) {
                        BlockPos place = new BlockPos(closestTarget.getPositionVector().add(vec).x, closestTarget.getPositionVector().add(vec).y, closestTarget.getPositionVector().add(vec).z);
                        if (mc.world.mayPlace(Blocks.OBSIDIAN, place, false, EnumFacing.UP, mc.player)) {
                            mc.player.inventory.currentItem = obsidianSlot;
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
                            BlockInteractionHelper.placeBlockScaffold(place);
                            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                            mc.player.inventory.currentItem = startingHand;

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
            startingHand = mc.player.inventory.currentItem;
            int obsidianHand = InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN);
            if (obsidianHand == -1) {
                Command.sendClientSideMessage("No Obsidian Toggling!");
                this.disable();
            } else {
                obsidianSlot = obsidianHand;
            }
            getPlaceArea();
            if (multiThread.getValBoolean()) {
                thread = new Thread(AutoTrapRewrite.TAutoTrapRewrite.getInstance(this));
                doMultiThreading();
            }

        }

        public void postTick() {
            if (multiThread.getValBoolean()) {
                doMultiThreading();
            }
        }

        public void doMultiThreading() {
            if (thread != null && (thread.isInterrupted() || !thread.isAlive())) {
                thread = new Thread(TAutoTrapRewrite.getInstance(this));
            }

            if (thread != null && thread.getState() == Thread.State.NEW) {
                try {
                    thread.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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

    private static class TAutoTrapRewrite
            implements Runnable {

        private static AutoTrapRewrite.TAutoTrapRewrite instance;
        private AutoTrapRewrite autoTrapRewrite;

        public static AutoTrapRewrite.TAutoTrapRewrite getInstance(AutoTrapRewrite autoTrap) {
            if (instance == null) {
                instance = new AutoTrapRewrite.TAutoTrapRewrite();
                TAutoTrapRewrite.instance.autoTrapRewrite = autoTrap;
            }
            return instance;
        }


        @Override
        public void run() {
            if (autoTrapRewrite.multiThread.getValBoolean()) {
                t++;
                toggleTick++;
                for (Vec3d vec : placeNoDirection) {
                    if (t >= autoTrapRewrite.delay.getValInt()) {
                        if (BlockInteractionHelper.canBlockBePlaced(closestTarget.getPositionVector().add(vec))) {
                            BlockPos place = new BlockPos(closestTarget.getPositionVector().add(vec).x, closestTarget.getPositionVector().add(vec).y, closestTarget.getPositionVector().add(vec).z);
                            BlockInteractionHelper.placeBlockScaffoldNoRotate(place);
                            t = 0;
                        }
                    }
                }
                if (autoTrapRewrite.debug.getValBoolean()) Command.sendClientSideMessage("Multi");
                if (toggleTick >= autoTrapRewrite.toggleTicks.getValInt()) {
                    toggleTick = 0;
                    autoTrapRewrite.disable();
                }
                try {
                    Thread.sleep(autoTrapRewrite.threadSleepTime.getValInt());
                } catch (InterruptedException e) {
                    thread.interrupt();
                    e.printStackTrace();
                }
            }

        }
    }

}
