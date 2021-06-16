package io.ace.nordclient.event;

import net.minecraft.entity.player.EntityPlayer;

public class EventTotemPop extends EventCancellable {
    EntityPlayer player;
    
    public EventTotemPop(final EntityPlayer entityPlayerIn) {
        this.player = entityPlayerIn;
    }
    
    public EntityPlayer getPlayer() {
        return this.player;
    }
}