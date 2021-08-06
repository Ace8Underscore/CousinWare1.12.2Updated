package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.event.EventPlayerTravel;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.LinkedList;
import java.util.Queue;

public class ChorusLag extends Hack {

    Setting sDelay;

    public ChorusLag() {
        super("ChorusLag", Category.COMBAT, 2956934);
        CousinWare.INSTANCE.settingsManager.rSetting(sDelay = new Setting("TickDelay", this, 18, 0, 500, true, "ChorusLagDelay", true));
    }
    int delay = 0;
    int delay2 = 0;
    boolean ateChorus = false;
    boolean hackPacket = false;
    boolean posTp = false;
    double posX;
    double posY;
    double posZ;

    Queue<CPacketPlayer> packets = new LinkedList<>();
    Queue<CPacketConfirmTeleport> packetss = new LinkedList<>();


    public void onEnable() {
    ateChorus = false;
    hackPacket = false;
    posTp = false;
    }

    public void onUpdate() {
        if (ateChorus) {
            delay++;
            delay2++;
            if (!mc.player.getPosition().equals(new BlockPos(posX, posY, posZ)) && !posTp) {
                if (mc.player.getDistance(posX, posY, posZ) > 1) {
                    mc.player.setPosition(posX, posY, posZ);
                    posTp = true;
                }
            }
        }
        if (ateChorus && delay2 > sDelay.getValInt()) {
            ateChorus = false;
            delay = 0;
            hackPacket = true;
            delay2 = 0;
            sendPackets();
        }

        if (delay2 == sDelay.getValInt() - 40) {
            Command.sendClientSideMessage("Chorusing In 2 seconds");
        }



    }

    public void sendPackets() {
        while (!packets.isEmpty()) {
            mc.player.connection.sendPacket(packets.poll());
        }
        while (!packetss.isEmpty()) {
            mc.player.connection.sendPacket(packetss.poll());
        }
        hackPacket = false;
        delay2 = 0;
        ateChorus = false;
    }

    @Listener
    public void Event(EventPlayerTravel eventPlayerTravel) {

    }

    @SubscribeEvent
    public void finishEating(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() == mc.player) {
            if (event.getResultStack().getItem().equals(Items.CHORUS_FRUIT)) {
                posX = mc.player.posX;
                posY = mc.player.posY;
                posZ = mc.player.posZ;
                posTp = false;
                ateChorus = true;
            }
        }
    }

    @SubscribeEvent
    public void finishEating(LivingEntityUseItemEvent.Start event) {

    }



    @Listener
    public void onUpdate(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketConfirmTeleport) {
            if (ateChorus && delay2 < sDelay.getValInt()) {
                packetss.add((CPacketConfirmTeleport) event.getPacket());
                event.setCanceled(true);
            }

        }
        if (event.getPacket() instanceof CPacketPlayer) {
            if (ateChorus && delay2 < sDelay.getValInt()) {
                    packets.add((CPacketPlayer) event.getPacket());
                    event.setCanceled(true);
                }
            }
        }

    @Override
    public String getHudInfo() {
        return "\u00A77[\u00A7f" + (int)(((double) delay2 / (double) sDelay.getValInt()) * 100) + "%" + "\u00A77]";
    }
    }

