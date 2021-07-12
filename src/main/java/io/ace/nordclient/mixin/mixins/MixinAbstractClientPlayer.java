package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.hacks.client.Core;
import io.ace.nordclient.hwid.UID;
import io.ace.nordclient.managers.FriendManager;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(AbstractClientPlayer.class)
public abstract class MixinAbstractClientPlayer {
    @Shadow @Nullable protected abstract NetworkPlayerInfo getPlayerInfo();

    @Inject(method = "getLocationCape", at = @At("HEAD"), cancellable = true)
    public void getLocationCape(CallbackInfoReturnable<ResourceLocation> cir){
        if (Core.capes.getValBoolean()) {
            NetworkPlayerInfo info = this.getPlayerInfo();
            assert info != null;
            if (!UID.isCapeUser(info.getGameProfile().getName()) && !FriendManager.isFriend(info.getGameProfile().getName())) {
                return;
            }
            ResourceLocation r;

                r = new ResourceLocation("custom/cape.png");

            cir.setReturnValue(r);
        }


    }
}