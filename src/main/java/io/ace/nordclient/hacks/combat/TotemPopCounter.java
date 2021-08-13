package io.ace.nordclient.hacks.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.event.TotemPopEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;
import java.util.HashMap;

public class TotemPopCounter extends Hack {

    private final Setting msg;
    private final Setting friend;
    private final Setting mode;
    private final Setting msgDelaySlider;

public TotemPopCounter() {
    super("PopCounter", Category.COMBAT, 471851);


    CousinWare.INSTANCE.settingsManager.rSetting(msg = new Setting("Msg", this, false, "PopCounterMsgInGame", true));
    CousinWare.INSTANCE.settingsManager.rSetting(friend = new Setting("FriendPops", this, false, "PopCounterFriendPops", true));
    CousinWare.INSTANCE.settingsManager.rSetting(msgDelaySlider = new Setting("MsgDelay", this, 4.0, 1, 20, false, "PopCounterMsgDelay", true));


    ArrayList<String> modes = new ArrayList<>();
    modes.add("BLACK");
    modes.add("RED");
    modes.add("AQUA");
    modes.add("BLUE");
    modes.add("GOLD");
    modes.add("GRAY");
    modes.add("WHITE");
    modes.add("GREEN");
    modes.add("YELLOW");
    modes.add("DARK_RED");
    modes.add("DARK_AQUA");
    modes.add("DARK_BLUE");
    modes.add("DARK_GRAY");
    modes.add("DARK_GREEN");
    modes.add("DARK_PURPLE");
    modes.add("LIGHT_PURPLE");
    CousinWare.INSTANCE.settingsManager.rSetting(mode = new Setting("Mode", this, "DARK_RED", modes, "PopCounterColorModes", true));


}
    private HashMap<String, Integer> popList = new HashMap();
    private int msgDelay;

    @Listener
    public void onUpdate(TotemPopEvent event) {
        if (mc.player == null || mc.world == null) {
            return;
        }
        if(popList == null) {
            popList = new HashMap<>();
        }
        if (!friend.getValBoolean()) {
            if (!FriendManager.isFriend(event.getEntity().getName())) {
                if (popList.get(event.getEntity().getName()) == null) {
                    popList.put(event.getEntity().getName(), 1);
                    Command.sendClientSideMessage(colorchoice() + event.getEntity().getName() + " popped " + 1 + " totem!");
                    if (msg.getValBoolean()) {
                        if (msgDelay > msgDelaySlider.getValDouble() * 20) {
                            if (!event.getEntity().getName().equals(mc.player.getName())) {
                                mc.player.sendChatMessage("/msg " + event.getEntity().getName() + " You're Poppin!");
                                msgDelay = 0;
                            }
                        }
                    }

                } else if (!(popList.get(event.getEntity().getName()) == null)) {
                    int popCounter = popList.get(event.getEntity().getName());
                    int newPopCounter = popCounter += 1;
                    popList.put(event.getEntity().getName(), newPopCounter);
                    Command.sendClientSideMessage(colorchoice() + event.getEntity().getName() + " popped " + newPopCounter + " totems!");
                    if (msg.getValBoolean()) {
                        if (msgDelay > msgDelaySlider.getValDouble() * 20) {
                            if (!event.getEntity().getName().equals(mc.player.getName())) {
                                mc.player.sendChatMessage("/msg " + event.getEntity().getName() + " You're Poppin!");
                                msgDelay = 0;
                            }
                        }
                    }
                }
            }
        }
        if (friend.getValBoolean())
            if(popList.get(event.getEntity().getName()) == null) {
                popList.put(event.getEntity().getName(), 1);
                Command.sendClientSideMessage(colorchoice() + event.getEntity().getName() + " popped " + 1 + " totem!");
                if (msg.getValBoolean()) {
                    if (msgDelay > msgDelaySlider.getValDouble() * 20) {
                        if (!event.getEntity().getName().equals(mc.player.getName())) {
                            if (FriendManager.isFriend(event.getEntity().getName())) {
                                mc.player.sendChatMessage("/msg " + event.getEntity().getName() + " You Popped Stay Safe Friend");
                                msgDelay = 0;
                            }
                            if (!FriendManager.isFriend(event.getEntity().getName())) {
                                mc.player.sendChatMessage("/msg " + event.getEntity().getName() + " You're Poppin!");
                                msgDelay = 0;
                            }
                        }
                    }

                }
            } else if(!(popList.get(event.getEntity().getName()) == null)) {
                int popCounter = popList.get(event.getEntity().getName());
                int newPopCounter = popCounter += 1;
                popList.put(event.getEntity().getName(), newPopCounter);
                Command.sendClientSideMessage(colorchoice() + event.getEntity().getName() + " popped " + newPopCounter + " totems!");
                if (msg.getValBoolean()) {
                    if (msgDelay > msgDelaySlider.getValDouble() * 20) {
                        if (!event.getEntity().getName().equals(mc.player.getName())) {
                            if (FriendManager.isFriend(event.getEntity().getName())) {
                                mc.player.sendChatMessage("/msg " + event.getEntity().getName() + " You Popped Stay Safe Friend");
                                msgDelay = 0;
                            }
                            if (!FriendManager.isFriend(event.getEntity().getName())) {
                                mc.player.sendChatMessage("/msg " + event.getEntity().getName() + " You're Poppin!");
                                msgDelay = 0;
                            }
                        }

                    }
                }
            }

    }

    @Override
    public void onUpdate() {
        msgDelay++;
        for(EntityPlayer player : mc.world.playerEntities) {
            if(player.getHealth() <= 0) {
                if(popList.containsKey(player.getName())) {
                    Command.sendClientSideMessage(colorchoice() + player.getName() + " died after popping " + popList.get(player.getName()) + " totems!");
                    popList.remove(player.getName(), popList.get(player.getName()));
                    if (msg.getValBoolean()) {
                        if (msgDelay > msgDelaySlider.getValDouble() * 20) {
                            if (player.getName() != mc.player.getName()) {
                                if (!FriendManager.isFriend(player.getName())) {
                                    mc.player.sendChatMessage("/msg " + player.getName() + " You Died!");
                                    msgDelay = 0;
                                }
                                if (FriendManager.isFriend(player.getName())) {
                                    mc.player.sendChatMessage("/msg " + player.getName() + " You Died You're Out Of The Goon Squad! ");
                                    msgDelay = 0;
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    public void onEnable() {

    }
    @Override
    public void onDisable() {

    }

    @Listener
    public void onUpdate(PacketEvent.Receive event) {

        if (mc.world == null || mc.player == null) {
            return;
        }

        if (event.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
            if (packet.getOpCode() == 35) {
                Entity entity = packet.getEntity(mc.world);
                CousinWare.INSTANCE.getEventManager().dispatchEvent(new TotemPopEvent(entity));
            }
        }

    }



    private ChatFormatting colorchoice(){
        switch (mode.getValString()){
            case "BLACK": return ChatFormatting.BLACK;
            case "RED": return ChatFormatting.RED;
            case "AQUA": return ChatFormatting.AQUA;
            case "BLUE": return ChatFormatting.BLUE;
            case "GOLD": return ChatFormatting.GOLD;
            case "GRAY": return ChatFormatting.GRAY;
            case "WHITE": return ChatFormatting.WHITE;
            case "GREEN": return ChatFormatting.GREEN;
            case "YELLOW": return ChatFormatting.YELLOW;
            case "DARK_RED": return ChatFormatting.DARK_RED;
            case "DARK_AQUA": return ChatFormatting.DARK_AQUA;
            case "DARK_BLUE": return ChatFormatting.DARK_BLUE;
            case "DARK_GRAY": return ChatFormatting.DARK_GRAY;
            case "DARK_GREEN": return ChatFormatting.DARK_GREEN;
            case "DARK_PURPLE": return ChatFormatting.DARK_PURPLE;
            case "LIGHT_PURPLE": return ChatFormatting.LIGHT_PURPLE;
            default: return ChatFormatting.WHITE;
        }


    }

    private enum colour{
        BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE
    }

}