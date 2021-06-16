package io.ace.nordclient.hacks.render;

import io.ace.nordclient.hacks.Hack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author Ace________/Ace_#1233
 */

public class SelfParticle extends Hack {

    public SelfParticle() {
        super("SelfParticle", Category.RENDER, 14147855);
    }

    Thread thread;

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
        if (mc.world == null || mc.player == null) return;
        if (this.thread != null) {
            try {
                this.thread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onEnable() {
        this.thread = new Thread(SelfParticle.TSelfParticle.getInstance(this));
        this.thread.start();
    }

    public void onDisable() {
        this.thread.stop();
    }


    private static class TSelfParticle
            implements Runnable {

        private static SelfParticle.TSelfParticle instance;
        private SelfParticle selfParticle;

        public static SelfParticle.TSelfParticle getInstance(SelfParticle selfParticle) {
            if (instance == null) {
                instance = new SelfParticle.TSelfParticle();
                TSelfParticle.instance.selfParticle = selfParticle;
            }
            return instance;
        }


        @Override
        public void run() {
            int x = (int) ((Math.random() * ((2 - -2) + 1))+0);
            int y = (int) ((Math.random() * ((2 - 0) + 1))+1);
            int z = (int) ((Math.random() * ((2 - -2) + 1))+-1);

            int particleId = (int) ((Math.random() * ((49 - 3) + 1))+1);

            if (!(particleId == 1) && !(particleId == 2) && !(particleId == 41))
                mc.effectRenderer.spawnEffectParticle(particleId, mc.player.posX + 1.5 +- x , mc.player.posY + y, mc.player.posZ + 1.5 +- z, 0, .5, 0, 10);

        }

    }
}
