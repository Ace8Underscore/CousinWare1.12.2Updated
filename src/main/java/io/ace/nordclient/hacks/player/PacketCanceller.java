package io.ace.nordclient.hacks.player;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class PacketCanceller extends Hack {

    Setting CPacketInput;
    Setting CPacketPlayer;
    Setting CPacketPlayerAbilities;
    Setting CPacketPlayerDigging;
    Setting CPacketTryUseItem;
    Setting CPacketTryUseItemOnBlock;
    Setting CPacketEntityRotation;
    Setting CPacketUseEntity;
    Setting CPacketVehicleMove;


    public PacketCanceller() {
        super("PacketCanceller", Category.PLAYER, 1125279);
        CousinWare.INSTANCE.settingsManager.rSetting(CPacketInput = new Setting("CPacketInput", this, false, "CPacketInput", true));
        CousinWare.INSTANCE.settingsManager.rSetting(CPacketPlayer = new Setting("CPacketPlayer", this, false, "CPacketPlayer", true));
        CousinWare.INSTANCE.settingsManager.rSetting(CPacketPlayerAbilities = new Setting("CPacketPlayerAbilities", this, false, "CPacketPlayerAbilities", true));
        CousinWare.INSTANCE.settingsManager.rSetting(CPacketPlayerDigging = new Setting("CPacketPlayerDigging", this, false, "CPacketPlayerDigging", true));
        CousinWare.INSTANCE.settingsManager.rSetting(CPacketTryUseItem = new Setting("CPacketTryUseItem", this, false, "CPacketTryUseItem", true));
        CousinWare.INSTANCE.settingsManager.rSetting(CPacketTryUseItemOnBlock = new Setting("CPacketTryUseItemOnBlock", this, false, "CPacketTryUseItemOnBlock", true));
        CousinWare.INSTANCE.settingsManager.rSetting(CPacketEntityRotation = new Setting("CPacketEntityRotation", this, false, "CPacketEntityRotation", true));
        CousinWare.INSTANCE.settingsManager.rSetting(CPacketUseEntity = new Setting("CPacketUseEntity", this, false, "CPacketUseEntity", true));
        CousinWare.INSTANCE.settingsManager.rSetting(CPacketVehicleMove = new Setting("CPacketVehicleMove", this, false, "CPacketVehicleMove", true));

    }
    @Listener
    public void onUpdate(PacketEvent.Send event) {
        Packet packet = event.getPacket();
        if (packet instanceof net.minecraft.network.play.client.CPacketInput && CPacketPlayer.getValBoolean()) {
            event.setCanceled(true);
        }

        if (packet instanceof net.minecraft.network.play.client.CPacketPlayer && CPacketPlayer.getValBoolean()) {
            event.setCanceled(true);
        }

        if (packet instanceof net.minecraft.network.play.client.CPacketPlayerAbilities && CPacketPlayerAbilities.getValBoolean()) {
            event.setCanceled(true);
        }

        if (packet instanceof net.minecraft.network.play.client.CPacketPlayerDigging && CPacketPlayerDigging.getValBoolean()) {
            event.setCanceled(true);
        }

        if (packet instanceof CPacketPlayerTryUseItem && CPacketTryUseItem.getValBoolean()) {
            event.setCanceled(true);
        }

        if (packet instanceof CPacketPlayerTryUseItemOnBlock && CPacketTryUseItemOnBlock.getValBoolean()) {
            event.setCanceled(true);
        }

        if (packet instanceof CPacketUseEntity && CPacketUseEntity.getValBoolean()) {
            event.setCanceled(true);
        }

        if (packet instanceof CPacketVehicleMove && CPacketVehicleMove.getValBoolean()) {
            event.setCanceled(true);
        }
    }
}
