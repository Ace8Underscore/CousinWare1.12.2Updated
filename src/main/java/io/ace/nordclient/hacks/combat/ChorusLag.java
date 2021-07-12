package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.LinkedList;
import java.util.Queue;

public class ChorusLag extends Hack {

    Setting sDelay;

    public ChorusLag() {
        super("ChorusLag", Category.COMBAT, 2956934);
        CousinWare.INSTANCE.settingsManager.rSetting(sDelay = new Setting("TickDelay", this, 18, 0, 50, true, "ChorusLagDelay", true));
    }

    int delay = 0;
    int packetSent = 0;
    boolean teleportPacket = false;
    boolean hackPacket = false;

    Queue<CPacketPlayer> packets = new LinkedList<>();
    Queue<CPacketConfirmTeleport> packetsTeleport = new LinkedList<>();

    public void onEnable() {
    teleportPacket = false;
    hackPacket = false;
    }

    public void onUpdate() {
        hackPacket = false;
        if (teleportPacket) {
            delay++;
            if (delay >= sDelay.getValInt()) {
                while (!packets.isEmpty()) {
                    mc.player.connection.sendPacket(packets.poll());
                    packets.clear();
                    delay = 0;
                    teleportPacket = false;
                }
                while (!packetsTeleport.isEmpty()) {
                    hackPacket = true;
                    mc.player.connection.sendPacket(packetsTeleport.poll());
                    hackPacket = false;
                    packetsTeleport.clear();
                    delay = 0;
                    teleportPacket = false;
                }
            }
        } else {
            delay = 0;
        }
    }

    @Listener
    public void onUpdate(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer && teleportPacket) {
            //if (delay <= 20) {
            event.setCanceled(true);
            packets.add((CPacketPlayer) event.getPacket());
            //}
        }
        if (event.getPacket() instanceof CPacketConfirmTeleport) {
            if (!hackPacket) {
                if (((CPacketConfirmTeleport) event.getPacket()).getTeleportId() == 1) return;
                packetSent++;
                teleportPacket = true;
                event.setCanceled(true);
                packetsTeleport.add((CPacketConfirmTeleport) event.getPacket());
            }
        }
    }

}
