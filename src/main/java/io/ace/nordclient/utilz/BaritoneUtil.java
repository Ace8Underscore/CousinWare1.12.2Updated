package io.ace.nordclient.utilz;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.GoalBlock;
import net.minecraft.util.math.BlockPos;

public class BaritoneUtil {

    public static boolean isPathFinding() {
       return BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing();
    }

    public static boolean isActive() {
        return BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().isActive();
    }

    public static void doCommand(String command) {
        BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute(command);
    }

    public static void goToPos(BlockPos pos) {
        doCommand("goto " + pos.getX() + " " + pos.getY() + " " + pos.getZ());
    }

    public static void setGoal(BlockPos pos) {BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(new GoalBlock(pos));}

    public static void stopAll() {BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();}
}
