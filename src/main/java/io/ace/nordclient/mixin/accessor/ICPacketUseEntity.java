package io.ace.nordclient.mixin.accessor;

import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;

public interface ICPacketUseEntity {

    void setEntityId(int entityId1);

    void setEntityAction(CPacketUseEntity.Action action1);

    void setHand(EnumHand hand1);

    void setHitVec(Vec3d hitVec1);
}
