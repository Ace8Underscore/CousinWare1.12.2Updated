package io.ace.nordclient.hacks.render;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.HoleUtil;
import io.ace.nordclient.utilz.NordTessellator;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class HoleESP extends Hack {

    static Setting doubleHole;
    static Setting radius;
    static Setting rObi;
    static Setting gObi;
    static Setting bObi;
    static Setting rRock;
    static Setting gRock;
    static Setting bRock;
    static Setting alpha;
    static Setting outlineAlpha;
    static Setting width;
    static Setting fruitRender;
    Thread thread;

    private static HoleESP instance;


    public HoleESP() {
        super("HoleESP", Category.RENDER, 3639503);
        CousinWare.INSTANCE.settingsManager.rSetting(fruitRender = new Setting("FruitRender", this, false, "HoleESPFruitRender"));
        CousinWare.INSTANCE.settingsManager.rSetting(doubleHole = new Setting("DoubleHole", this, true, "HoleESPDoubleHole"));
        CousinWare.INSTANCE.settingsManager.rSetting(radius = new Setting("Radius", this, 5, 1, 20, false, "HoleESPRadius"));
        CousinWare.INSTANCE.settingsManager.rSetting(rObi = new Setting("RedObi", this, 255, 0, 255, true, "HoleESPRedObi"));
        CousinWare.INSTANCE.settingsManager.rSetting(gObi = new Setting("GreenObi", this, 26, 0, 255, true, "HoleESPGreenObi"));
        CousinWare.INSTANCE.settingsManager.rSetting(bObi = new Setting("BlueObi", this, 255, 0, 255, true, "HoleESPBlueObi"));
        CousinWare.INSTANCE.settingsManager.rSetting(rRock = new Setting("RedRock", this, 0, 0, 255, true, "HoleESPRedRock"));
        CousinWare.INSTANCE.settingsManager.rSetting(gRock = new Setting("GreenRock", this, 255, 0, 255, true, "HoleESPGreenRock"));
        CousinWare.INSTANCE.settingsManager.rSetting(bRock = new Setting("BlueRock", this, 0, 0, 255, true, "HoleESPBlueRock"));
        CousinWare.INSTANCE.settingsManager.rSetting(alpha = new Setting("Alpha", this, 60, 0, 255, true, "HoleESPAlpha"));
        CousinWare.INSTANCE.settingsManager.rSetting(outlineAlpha = new Setting("OutlineAlpha", this, 200, 0, 255, true, "HoleESPOutlineAlpha"));
        CousinWare.INSTANCE.settingsManager.rSetting(width = new Setting("Width", this, 2, 0, 10, true, "HoleESPWidth"));
        instance = this;
    }

    public static HoleESP getInstance() {
        if (instance == null) {
            instance = new HoleESP();
        }
        return instance;
    }



    @Override
    public void onWorldRender(RenderEvent event) {
        if (mc.world == null || mc.player == null) return;
        double x = mc.player.posX;
        double y = mc.player.posY;
        double z = mc.player.posZ;
        BlockPos playerPos = new BlockPos(x, y, z);
        List<BlockPos> blocks = (BlockInteractionHelper.getSphere(playerPos, (float) radius.getValDouble(), (int) radius.getValDouble(), false, true, 0));
        for (BlockPos block : blocks) {
            if (block == null)
                return;
            if (HoleUtil.isBedrockHole(block)) {
                NordTessellator.prepare(7);
                NordTessellator.drawBoxBottom(block, rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), alpha.getValInt());
                NordTessellator.release();
                NordTessellator.drawBoundingBoxBottomBlockPos(block, width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                if (fruitRender.getValBoolean()) {
                    NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle(block, width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                    NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle2(block, width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());

                }
            }
            if (HoleUtil.isObiHole(block)) {
                NordTessellator.prepare(7);
                NordTessellator.drawBoxBottom(block, rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), alpha.getValInt());
                NordTessellator.release();
                NordTessellator.drawBoundingBoxBottomBlockPos(block, width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle(block, width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle2(block, width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());

            }
            if (doubleHole.getValBoolean()) {
                if (HoleUtil.isBedrockEastHole(block) && HoleUtil.isBedrockWestHole(block.west())) {
                    NordTessellator.prepare(7);
                    NordTessellator.drawBoxBottom(block.west(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), alpha.getValInt());
                    NordTessellator.drawBoxBottom(block, rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), alpha.getValInt());
                    NordTessellator.release();
                    NordTessellator.drawBoundingBoxBottomBlockPosEast(block, width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                    NordTessellator.drawBoundingBoxBottomBlockPosWest(block.west(), width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                    if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle(block, width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                    if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle(block.west(), width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                    if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle2(block.west(), width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                    if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle2(block, width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());

                }
                if (HoleUtil.isBedrockNorthHole(block) && HoleUtil.isBedrockSouthHole(block.south())) {
                    NordTessellator.prepare(7);
                    NordTessellator.drawBoxBottom(block.south(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), alpha.getValInt());
                    NordTessellator.drawBoxBottom(block, rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), alpha.getValInt());
                    NordTessellator.release();
                    NordTessellator.drawBoundingBoxBottomBlockPosNorth(block, width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                    NordTessellator.drawBoundingBoxBottomBlockPosSouth(block.south(), width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                    if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle(block, width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                    if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle2(block.south(), width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                    if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle(block.south(), width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                    if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle2(block, width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());


                }
                if (HoleUtil.isObiEastHole(block) && HoleUtil.isObiWestHole(block.west())) {
                    NordTessellator.prepare(7);
                    NordTessellator.drawBoxBottom(block.west(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), alpha.getValInt());
                    NordTessellator.drawBoxBottom(block, rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), alpha.getValInt());
                    NordTessellator.release();
                    NordTessellator.drawBoundingBoxBottomBlockPosEast(block, width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                    NordTessellator.drawBoundingBoxBottomBlockPosWest(block.west(), width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());if (fruitRender.getValBoolean()) if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle2(block, width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                    if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle(block.west(), width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());                    if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle(block, width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                    if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle2(block.west(), width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                }

                if (HoleUtil.isObiNorthHole(block) && HoleUtil.isObiSouthHole(block.south())) {
                    NordTessellator.prepare(7);
                    NordTessellator.drawBoxBottom(block.south(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), alpha.getValInt());
                    NordTessellator.drawBoxBottom(block, rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), alpha.getValInt());
                    NordTessellator.release();
                    NordTessellator.drawBoundingBoxBottomBlockPosNorth(block, width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                    NordTessellator.drawBoundingBoxBottomBlockPosSouth(block.south(), width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                    if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle(block, width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                    if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle(block.south(), width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                    if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle2(block, width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                    if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle2(block.south(), width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                }
                if (!(HoleUtil.isObiNorthHole(block) && HoleUtil.isObiSouthHole(block.south())) && !(HoleUtil.isObiEastHole(block) && HoleUtil.isObiWestHole(block.west())) && !(HoleUtil.isBedrockNorthHole(block) && HoleUtil.isBedrockSouthHole(block.south())) && !(HoleUtil.isBedrockEastHole(block) && HoleUtil.isBedrockWestHole(block.west()))) {

                    if ((HoleUtil.isObiNorthHole(block) || HoleUtil.isBedrockNorthHole(block)) && (HoleUtil.isObiSouthHole(block.south()) || HoleUtil.isBedrockSouthHole(block.south()))) {
                        NordTessellator.prepare(7);
                        NordTessellator.drawBoxBottom(block.south(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), alpha.getValInt());
                        NordTessellator.drawBoxBottom(block, rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), alpha.getValInt());
                        NordTessellator.release();
                        NordTessellator.drawBoundingBoxBottomBlockPosNorth(block, width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                        NordTessellator.drawBoundingBoxBottomBlockPosSouth(block.south(), width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                        if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle(block, width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                        if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle2(block.south(), width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                        if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle2(block, width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                        if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle(block.south(), width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                    }

                    if ((HoleUtil.isObiEastHole(block) || HoleUtil.isBedrockEastHole(block)) && (HoleUtil.isObiWestHole(block.west()) || HoleUtil.isBedrockWestHole(block.west()))) {
                        NordTessellator.prepare(7);
                        NordTessellator.drawBoxBottom(block.west(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), alpha.getValInt());
                        NordTessellator.drawBoxBottom(block, rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), alpha.getValInt());
                        NordTessellator.release();
                        NordTessellator.drawBoundingBoxBottomBlockPosEast(block, width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                        NordTessellator.drawBoundingBoxBottomBlockPosWest(block.west(), width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                        if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle(block, width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                        if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle2(block.west(), width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                        if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle(block.west(), width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                        if (fruitRender.getValBoolean()) NordTessellator.drawBoundingBoxBottomBlockPosXInMiddle2(block, width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                    }
                }
            }
        }
    }



    private static class RHoleESP
            implements Runnable {

        private static RHoleESP instance;
        private HoleESP autoCrystal;

        public static RHoleESP getInstance(HoleESP holeESP) {
            if (instance == null) {
                instance = new RHoleESP();
                RHoleESP.instance.autoCrystal = holeESP;
            }
            return instance;
        }


        @Override
        public void run() {
            double x = mc.player.posX;
            double y = mc.player.posY;
            double z = mc.player.posZ;
            BlockPos playerPos = new BlockPos(x, y, z);
            List<BlockPos> blocks = (BlockInteractionHelper.getSphere(playerPos, (float) radius.getValDouble(), (int) radius.getValDouble(), false, true, 0));
            for (BlockPos block : blocks) {
                if (block == null)
                    return;
                if (HoleUtil.isBedrockHole(block)) {
                    NordTessellator.prepare(7);
                    NordTessellator.drawBoxBottom(block, rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), alpha.getValInt());
                    NordTessellator.release();
                    NordTessellator.drawBoundingBoxBottomBlockPos(block, width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                }
                if (HoleUtil.isObiHole(block)) {
                    NordTessellator.prepare(7);
                    NordTessellator.drawBoxBottom(block, rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), alpha.getValInt());
                    NordTessellator.release();
                    NordTessellator.drawBoundingBoxBottomBlockPos(block, width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                }
                if (doubleHole.getValBoolean()) {
                    if (HoleUtil.isBedrockEastHole(block) && HoleUtil.isBedrockWestHole(block.west())) {
                        NordTessellator.prepare(7);
                        NordTessellator.drawBoxBottom(block.west(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), alpha.getValInt());
                        NordTessellator.drawBoxBottom(block, rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), alpha.getValInt());
                        NordTessellator.release();
                        NordTessellator.drawBoundingBoxBottomBlockPosEast(block, width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                        NordTessellator.drawBoundingBoxBottomBlockPosWest(block.west(), width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());

                    }
                    if (HoleUtil.isBedrockNorthHole(block) && HoleUtil.isBedrockSouthHole(block.south())) {
                        NordTessellator.prepare(7);
                        NordTessellator.drawBoxBottom(block.south(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), alpha.getValInt());
                        NordTessellator.drawBoxBottom(block, rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), alpha.getValInt());
                        NordTessellator.release();
                        NordTessellator.drawBoundingBoxBottomBlockPosNorth(block, width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
                        NordTessellator.drawBoundingBoxBottomBlockPosSouth(block.south(), width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());

                    }
                    if (HoleUtil.isObiEastHole(block) && HoleUtil.isObiWestHole(block.west())) {
                        NordTessellator.prepare(7);
                        NordTessellator.drawBoxBottom(block.west(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), alpha.getValInt());
                        NordTessellator.drawBoxBottom(block, rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), alpha.getValInt());
                        NordTessellator.release();
                        NordTessellator.drawBoundingBoxBottomBlockPosEast(block, width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                        NordTessellator.drawBoundingBoxBottomBlockPosWest(block.west(), width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
//
                    }

                    if (HoleUtil.isObiNorthHole(block) && HoleUtil.isObiSouthHole(block.south())) {
                        NordTessellator.prepare(7);
                        NordTessellator.drawBoxBottom(block.south(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), alpha.getValInt());
                        NordTessellator.drawBoxBottom(block, rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), alpha.getValInt());
                        NordTessellator.release();
                        NordTessellator.drawBoundingBoxBottomBlockPosNorth(block, width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                        NordTessellator.drawBoundingBoxBottomBlockPosSouth(block.south(), width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());


                    }
                    if (!(HoleUtil.isObiNorthHole(block) && HoleUtil.isObiSouthHole(block.south())) && !(HoleUtil.isObiEastHole(block) && HoleUtil.isObiWestHole(block.west())) && !(HoleUtil.isBedrockNorthHole(block) && HoleUtil.isBedrockSouthHole(block.south())) && !(HoleUtil.isBedrockEastHole(block) && HoleUtil.isBedrockWestHole(block.west()))) {

                        if ((HoleUtil.isObiNorthHole(block) || HoleUtil.isBedrockNorthHole(block)) && (HoleUtil.isObiSouthHole(block.south()) || HoleUtil.isBedrockSouthHole(block.south()))) {
                            NordTessellator.prepare(7);
                            NordTessellator.drawBoxBottom(block.south(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), alpha.getValInt());
                            NordTessellator.drawBoxBottom(block, rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), alpha.getValInt());
                            NordTessellator.release();
                            NordTessellator.drawBoundingBoxBottomBlockPosNorth(block, width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                            NordTessellator.drawBoundingBoxBottomBlockPosSouth(block.south(), width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());

                        }

                        if ((HoleUtil.isObiEastHole(block) || HoleUtil.isBedrockEastHole(block)) && (HoleUtil.isObiWestHole(block.west()) || HoleUtil.isBedrockWestHole(block.west()))) {
                            NordTessellator.prepare(7);
                            NordTessellator.drawBoxBottom(block.west(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), alpha.getValInt());
                            NordTessellator.drawBoxBottom(block, rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), alpha.getValInt());
                            NordTessellator.release();
                            NordTessellator.drawBoundingBoxBottomBlockPosEast(block, width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
                            NordTessellator.drawBoundingBoxBottomBlockPosWest(block.west(), width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());

                        }
                    }
                }
            }
        }

    }





    public void onEnable() {
        this.thread = new Thread(RHoleESP.getInstance(this));
        this.thread.start();


        //this.thread.start();
    }

    public void onDisable() {
        //tthis.thread.setName("holeESP");
        this.thread.stop();
    }
}
