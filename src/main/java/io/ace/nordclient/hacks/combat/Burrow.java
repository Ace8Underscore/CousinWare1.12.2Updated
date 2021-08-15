package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.mixin.accessor.ISPacketPlayerPosLook;
import io.ace.nordclient.utilz.*;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Ace________/Ace_#1233
 */

public class Burrow extends Hack {


    int delayPlace;
    boolean blockPlaced;
    boolean noForce;
    int startingHand;
    double startingY;
    int useItem;

    Setting delay;
    Setting lagBackPower;
    Setting noForceRotate;
    Setting blockMode;
    Setting lagMode;
    Setting noJump;
    Setting rotate;
    Setting silentSwitch;
    Setting motionCheck;
    Setting rat;



    public Burrow() {
        super("Burrow", Category.COMBAT, 5254300);
        CousinWare.INSTANCE.settingsManager.rSetting(delay = new Setting("Delay", this, 4, 0, 8, true, "BurrowDelay", true));
        CousinWare.INSTANCE.settingsManager.rSetting(lagBackPower = new Setting("LagBackPower", this,1, .5,10, true, "BurrowLagBack", true));
        CousinWare.INSTANCE.settingsManager.rSetting(noForceRotate = new Setting("NoForceRotate", this, true, "BurrowNoForceRotate", true));
        CousinWare.INSTANCE.settingsManager.rSetting(rotate = new Setting("Rotate", this, false, "BurrowRotate", true));
        CousinWare.INSTANCE.settingsManager.rSetting(motionCheck = new Setting("MotionCheck", this, false, "BurrowMotionCheck", true));
        ArrayList<String> blockModes = new ArrayList<>();
        blockModes.add("Obi");
        blockModes.add("EChest");
        blockModes.add("All");
        CousinWare.INSTANCE.settingsManager.rSetting(blockMode = new Setting("BlockMode", this, "Obi", blockModes, "BurrowBlockMode", true));
        ArrayList<String> lagModes = new ArrayList<>();
        lagModes.add("Packet");
        lagModes.add("Fly");
        lagModes.add("Smart");
        lagModes.add("NeverFail");
        CousinWare.INSTANCE.settingsManager.rSetting(lagMode = new Setting("LagMode", this, "Packet", lagModes, "BurrowLagMode", true));
        CousinWare.INSTANCE.settingsManager.rSetting(noJump = new Setting("NoJump", this, true, "BurrowNoJump", true));
        CousinWare.INSTANCE.settingsManager.rSetting(silentSwitch = new Setting("SilentSwitch", this, false, "BurrowSilentSwitch", true));
        CousinWare.INSTANCE.settingsManager.rSetting(rat = new Setting("RatAutoBurrow", this, false, "BurrowRatAutoBurrow", true));
    }

    @Override
    public void doTick() {
        if (mc.player == null || mc.world == null) this.disable();
        if (mc.player.isDead) this.disable();
        if (rat.getValBoolean()) {
            if (mc.world.getBlockState(PlayerUtil.getPlayerPos()).getBlock().equals(Blocks.AIR) && !mc.world.getBlockState(PlayerUtil.getPlayerPos().down()).getBlock().equals(Blocks.AIR)) {
                if (mc.player.onGround && !mc.gameSettings.keyBindForward.isKeyDown() && !mc.gameSettings.keyBindBack.isKeyDown() && !mc.gameSettings.keyBindLeft.isKeyDown() && !mc.gameSettings.keyBindRight.isKeyDown()) {
                    if ((mc.world.getBlockState(PlayerUtil.getPlayerPos().up(lagBackPower.getValInt() + 1)).getBlock().equals(Blocks.AIR)) && mc.world.getBlockState(PlayerUtil.getPlayerPos().up(2)).getBlock().equals(Blocks.AIR)) {
                        if (mc.world.getEntitiesInAABBexcluding(mc.player, new AxisAlignedBB(PlayerUtil.getPlayerPos()), Entity::isEntityAlive).isEmpty()) {
                            if (blockMode.getValString().equalsIgnoreCase("Obi")) {
                                if (InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN) == -1) {
                                    Command.sendClientSideMessage("No Obsidian Found");
                                    this.disable();
                                } else {
                                    useItem = InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN);
                                    blockPlaced = false;
                                    noForce = false;
                                    if (!noJump.getValBoolean()) mc.player.jump();
                                    delayPlace = 0;
                                }
                            }

                            if (blockMode.getValString().equalsIgnoreCase("EChest")) {
                                if (InventoryUtil.findBlockInHotbar(Blocks.ENDER_CHEST) == -1) {
                                    Command.sendClientSideMessage("No EChest Found");
                                    this.disable();
                                } else {
                                    useItem = InventoryUtil.findBlockInHotbar(Blocks.ENDER_CHEST);
                                    blockPlaced = false;
                                    noForce = false;
                                    if (!noJump.getValBoolean()) mc.player.jump();
                                    delayPlace = 0;
                                }
                            }
                            if (blockMode.getValString().equalsIgnoreCase("all")) {
                                if (InventoryUtil.findBlockInHotbarObiEchestRandom() == -1) {
                                    Command.sendClientSideMessage("No Blocks Found");
                                    this.disable();
                                } else {
                                    useItem = InventoryUtil.findBlockInHotbarObiEchestRandom();
                                    blockPlaced = false;
                                    noForce = false;
                                    if (!noJump.getValBoolean()) mc.player.jump();
                                    delayPlace = 0;
                                }
                            }
                            startingHand = mc.player.inventory.currentItem;
                            if (motionCheck.getValBoolean() && !MotionUtil.isMoving()) burrow();
                            else burrow();
                        }
                    }
                }
            }
        } else {
            if (mc.world.getBlockState(PlayerUtil.getPlayerPos()).getBlock().equals(Blocks.AIR) || mc.world.getBlockState(PlayerUtil.getPlayerPos()).getBlock().equals(Blocks.ENDER_CHEST) && !mc.world.getBlockState(PlayerUtil.getPlayerPos().down()).getBlock().equals(Blocks.AIR)) {
                if (mc.player.onGround && !mc.gameSettings.keyBindForward.isKeyDown() && !mc.gameSettings.keyBindBack.isKeyDown() && !mc.gameSettings.keyBindLeft.isKeyDown() && !mc.gameSettings.keyBindRight.isKeyDown()) {
                        if (mc.world.getEntitiesInAABBexcluding(mc.player, new AxisAlignedBB(PlayerUtil.getPlayerPos()), Entity::isEntityAlive).isEmpty()) {
                            if (blockMode.getValString().equalsIgnoreCase("Obi")) {
                                if (InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN) == -1) {
                                    Command.sendClientSideMessage("No Obsidian Found");
                                    this.disable();
                                } else {
                                    useItem = InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN);
                                    blockPlaced = false;
                                    noForce = false;
                                    if (!noJump.getValBoolean()) mc.player.jump();
                                    delayPlace = 0;
                                }
                            }

                            if (blockMode.getValString().equalsIgnoreCase("EChest")) {
                                if (InventoryUtil.findBlockInHotbar(Blocks.ENDER_CHEST) == -1) {
                                    Command.sendClientSideMessage("No EChest Found");
                                    this.disable();
                                } else {
                                    useItem = InventoryUtil.findBlockInHotbar(Blocks.ENDER_CHEST);
                                    blockPlaced = false;
                                    noForce = false;
                                    if (!noJump.getValBoolean()) mc.player.jump();
                                    delayPlace = 0;
                                }
                            }
                            if (blockMode.getValString().equalsIgnoreCase("all")) {
                                if (InventoryUtil.findBlockInHotbarObiEchestRandom() == -1) {
                                    Command.sendClientSideMessage("No Blocks Found");
                                    this.disable();
                                } else {
                                    useItem = InventoryUtil.findBlockInHotbarObiEchestRandom();
                                    blockPlaced = false;
                                    noForce = false;
                                    if (!noJump.getValBoolean()) mc.player.jump();
                                    delayPlace = 0;
                                }
                            }
                            startingHand = mc.player.inventory.currentItem;
                            if (motionCheck.getValBoolean() && !MotionUtil.isMoving()) burrow();
                            else burrow();
                        }
                    }
                }
            }

        if (mc.gameSettings.keyBindJump.isKeyDown()) this.disable();

    }

    public void onEnable() {
        startingHand = mc.player.inventory.currentItem;
        if (blockMode.getValString().equalsIgnoreCase("Obi")) {
            if (InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN) == -1) {
                Command.sendClientSideMessage("No Obsidian Found");
                this.disable();
            } else {
                useItem = InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN);
                blockPlaced = false;
                noForce = false;
                if (!noJump.getValBoolean()) mc.player.jump();
                delayPlace = 0;
            }
        }

        if (blockMode.getValString().equalsIgnoreCase("EChest")) {
            if (InventoryUtil.findBlockInHotbar(Blocks.ENDER_CHEST) == -1) {
                Command.sendClientSideMessage("No EChest Found");
                this.disable();
            } else {
                useItem = InventoryUtil.findBlockInHotbar(Blocks.ENDER_CHEST);
                blockPlaced = false;
                noForce = false;
                if (!noJump.getValBoolean()) mc.player.jump();
                delayPlace = 0;
            }
        }
        if (blockMode.getValString().equalsIgnoreCase("all")) {
            if (InventoryUtil.findBlockInHotbarObiEchestRandom() == -1) {
                Command.sendClientSideMessage("No Blocks Found");
                this.disable();
            } else {
                useItem = InventoryUtil.findBlockInHotbarObiEchestRandom();
                blockPlaced = false;
                noForce = false;
                if (!noJump.getValBoolean()) mc.player.jump();
                delayPlace = 0;
            }
        }
        }
        //


    public void burrow() {
        if (mc.player.isDead) this.disable();
        delayPlace++;
        if (delayPlace >= delay.getValInt() && !noForce) {
            BlockPos pos = new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ);
            if (noJump.getValBoolean()) {
                if (silentSwitch.getValBoolean()) mc.player.connection.sendPacket(new CPacketHeldItemChange(useItem));
                else mc.player.inventory.currentItem = useItem;
                if (mc.world.getBlockState(PlayerUtil.getPlayerPos()).getBlock().equals(Blocks.ENDER_CHEST)) mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                if (mc.world.getBlockState(PlayerUtil.getPlayerPos()).getBlock().equals(Blocks.ENDER_CHEST)) {
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698D, mc.player.posZ, true));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7531999805211997D, mc.player.posZ, true));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.00133597911214D, mc.player.posZ, true));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.16610926093821D, mc.player.posZ, true));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.2710926093821D, mc.player.posZ, true));
                } else {
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698D, mc.player.posZ, true));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7531999805211997D, mc.player.posZ, true));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.00133597911214D, mc.player.posZ, true));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.16610926093821D, mc.player.posZ, true));

                }
                BlockInteractionHelper.placeBlockScaffold(pos.up());
                if (!mc.world.getBlockState(PlayerUtil.getPlayerPos()).getBlock().equals(Blocks.ENDER_CHEST)) mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));
                else mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos.up(), EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));
                mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                if (mc.world.getBlockState(PlayerUtil.getPlayerPos()).getBlock().equals(Blocks.ENDER_CHEST)) mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));

                if (silentSwitch.getValBoolean()) mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
                else mc.player.inventory.currentItem = startingHand;
            } else  {
                if (silentSwitch.getValBoolean()) mc.player.connection.sendPacket(new CPacketHeldItemChange(useItem));
                else mc.player.inventory.currentItem = useItem;
                if (rotate.getValBoolean()) BlockInteractionHelper.placeBlockScaffold(pos);
                else BlockInteractionHelper.placeBlockScaffoldNoRotate(pos);

                if (silentSwitch.getValBoolean()) mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
                else mc.player.inventory.currentItem = startingHand;
            }
            if (lagMode.getValString().equalsIgnoreCase("Fly")) {
                mc.player.motionY = lagBackPower.getValDouble();
            } else if (lagMode.getValString().equalsIgnoreCase("Packet")) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + lagBackPower.getValDouble(), mc.player.posZ, false));
            } else if (lagMode.getValString().equalsIgnoreCase("Smart")){
                if (mc.player.posY >= 118) mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 10, mc.player.posZ, false));
                else mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, findTpBlocks().getY(), mc.player.posY + 3 , false));
            } else if (lagMode.getValString().equalsIgnoreCase("NeverFail")) {
                if (playerPosMod() != null) {
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(getCenterModX(Objects.requireNonNull(playerPosMod())), Objects.requireNonNull(playerPosMod()).getY(), getCenterModZ(Objects.requireNonNull(playerPosMod())), false));
                } else {
                    for (BlockPos e : CrystalAura.getSphere(PlayerUtil.getPlayerPos().up(), 1, lagBackPower.getValInt(), false, false, 0)) {
                        if (e.getY() > mc.player.posY + 2) {
                            if (canTpBlock(e)) {
                                mc.player.connection.sendPacket(new CPacketPlayer.Position(getCenterModX(e), e.getY(), getCenterModZ(e), false));
                                //mc.player.setPosition(getCenterModX(e), e.getY() + 1, getCenterModZ(e));
                                //Command.sendClientSideMessage(getCenterModX(e) + " " + e.up().getY() + " " + getCenterModZ(e));
                                break;
                            }
                        }
                    }
                }
            }
            if (!noForceRotate.getValBoolean() && !rat.getValBoolean()) this.disable();

//
        }
    }
    @Listener
    public void onUpdate(PacketEvent.Receive event) {
        if (mc.world == null || mc.player == null)
            this.disable();
        if (mc.player.isDead) this.disable();
        if (noForceRotate.getValBoolean()) {
            Packet packet = event.getPacket();

            if (event.getPacket() instanceof SPacketPlayerPosLook) {
                event.setCanceled(true);
                //this.toggle();
                double d0 = ((ISPacketPlayerPosLook) event.getPacket()).getX();
                double d1 = ((ISPacketPlayerPosLook) event.getPacket()).getY();
                double d2 = ((ISPacketPlayerPosLook) event.getPacket()).getZ();


                if (((ISPacketPlayerPosLook) event.getPacket()).getFlags().contains(SPacketPlayerPosLook.EnumFlags.X)) {
                    d0 += mc.player.posX;
                } else {
                    ((ISPacketPlayerPosLook) event.getPacket()).setX(0.0D);
                }

                if (((ISPacketPlayerPosLook) event.getPacket()).getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y)) {
                    d1 += mc.player.posY;
                } else {
                    mc.player.motionY = 0.0D;
                }

                if (((ISPacketPlayerPosLook) event.getPacket()).getFlags().contains(SPacketPlayerPosLook.EnumFlags.Z)) {
                    d2 += mc.player.posZ;
                } else {
                    mc.player.motionZ = 0.0D;
                }

                mc.player.setPosition(d0, d1, d2);
                mc.getConnection().sendPacket(new CPacketConfirmTeleport(((ISPacketPlayerPosLook) event.getPacket()).getTeleportId()));
                mc.getConnection().sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX, mc.player.getEntityBoundingBox().minY, ((ISPacketPlayerPosLook) event.getPacket()).getZ(), ((ISPacketPlayerPosLook) event.getPacket()).getYaw(), ((ISPacketPlayerPosLook) event.getPacket()).getPitch(), false));
                if (!rat.getValBoolean()) this.disable();
            }
        }
    }

    private BlockPos playerPosMod() {
        BlockPos pos = new BlockPos(0, 0,0);
        for (int i = 2; i < 6; i++) {
            if (!(mc.player.posY + i <= 0)) {
                pos = new BlockPos(mc.player.posX, mc.player.posY + i, mc.player.posZ);
                if (canTpBlock(pos)) return new BlockPos(mc.player.posX, mc.player.posY + i, mc.player.posZ);
                Command.sendClientSideMessage(String.valueOf(mc.player.posY + i));

            }


        }
        return null;
    }

    private float getCenterModX(BlockPos pos) {
        float mod = 0;
        if (pos.getX() > 0) mod = (float) (pos.getX() + .5);
        if (pos.getX() < 0) mod = (float) (pos.getX() - .5);
        return mod;
    }
    private float getCenterModZ(BlockPos pos) {
        float mod = 0;
        if (pos.getZ() > 0) mod = (float) (pos.getZ() + .5);
        if (pos.getZ() < 0) mod = (float) (pos.getZ() - .5);
        return mod;
    }

    private boolean canTpBlock(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 0, 0);
        BlockPos boost2 = blockPos.add(0, 1, 0);
        return (mc.world.getBlockState(boost).getBlock() == Blocks.AIR && mc.world.getBlockState(boost2).getBlock() == Blocks.AIR);
    }
    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    private BlockPos findTpBlocks() {
        BlockPos positions = null;
        for (BlockPos pos : BlockInteractionHelper.getSphere(getPlayerPos(), (float) lagBackPower.getValInt() / 2, lagBackPower.getValInt(), false, true, 1))
            if (pos.getY() > mc.player.posY + 1)
                if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) && mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR)) positions = pos;
        return positions;
    }

    //

}