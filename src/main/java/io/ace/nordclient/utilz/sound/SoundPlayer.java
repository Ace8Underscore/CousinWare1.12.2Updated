package io.ace.nordclient.utilz.sound;

import io.ace.nordclient.command.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class SoundPlayer {

    public static void playSound(SoundEvent sound, double x, double y, double z) {
        try {

                EntityPlayerSP player = Minecraft.getMinecraft().player;
                Minecraft.getMinecraft().world.playSound(player, new BlockPos(x, y, z), sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
                Command.sendClientSideMessage("Playing Sound At" + x + " " +y + " " +z);

        } catch (Exception ex) {
            //Error happened
        }
    }
}
