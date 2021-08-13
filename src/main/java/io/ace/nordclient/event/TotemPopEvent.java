package io.ace.nordclient.event;

import net.minecraft.entity.Entity;

public class TotemPopEvent extends EventCancellable {

    private final Entity entity;

    public TotemPopEvent(Entity entity) {
        super();
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

}
