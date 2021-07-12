package io.ace.nordclient.utilz;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import io.ace.nordclient.CousinWare;
import net.minecraft.client.Minecraft;

public class DiscordRP
{
    private final boolean running;
    private final long created;
    
    public DiscordRP() {
        this.running = true;
        this.created = 0L;
    }
    
    public void start() {
        final Minecraft mc = Minecraft.getMinecraft();
        final DiscordRPC lib = DiscordRPC.INSTANCE;
        final String applicationId = "858518457405145089";
        final String steamId = "";
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user -> System.out.println("Ready!"));
        lib.Discord_Initialize(applicationId, handlers, true, steamId);
        final DiscordRichPresence presence = new DiscordRichPresence();
        final DiscordRPC discordRPC = DiscordRPC.INSTANCE;
        presence.startTimestamp = System.currentTimeMillis() / 1000L;
        presence.details = "CousinWare on Top";
        presence.state = "Gank on kids";
        presence.largeImageKey = "1babbb28276a8b99a2c40f778d63f95b";
        discordRPC.Discord_UpdatePresence(presence);
        final DiscordRichPresence discordRichPresence = new DiscordRichPresence();
        final Minecraft minecraft;
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                discordRPC.Discord_ClearPresence();
                try {
                    discordRichPresence.largeImageKey = "1babbb28276a8b99a2c40f778d63f95b";
                    discordRichPresence.largeImageText = CousinWare.NAME + " " + CousinWare.VERSION;
                    if (mc.isIntegratedServerRunning()) {
                        discordRichPresence.details = "Singleplayer";
                        discordRichPresence.state = "In Game";
                    }
                    else if (mc.getCurrentServerData() != null) {
                        if (!mc.getCurrentServerData().serverIP.equals(discordRichPresence.state)) {
                            discordRichPresence.details = "Playing a server";
                            discordRichPresence.state = mc.getCurrentServerData().serverIP;
                        }
                    }
                    else {
                        discordRichPresence.details = "Menu";
                        discordRichPresence.state = "Hanging Out With Cousin";
                    }
                    discordRPC.Discord_UpdatePresence(discordRichPresence);
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException ex) {}
            }
        }, "RPC-Callback-Handler").start();
    }
    
    public void shutdown() {
        final DiscordRPC lib = DiscordRPC.INSTANCE;
        lib.Discord_Shutdown();
    }
}