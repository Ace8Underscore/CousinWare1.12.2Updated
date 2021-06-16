package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.hacks.render.NoRender;
import io.ace.nordclient.managers.HackManager;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.world.storage.MapData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MapItemRenderer.class)
public abstract class MixinMapItemRenderer {

    @Inject(method = "renderMap", at = @At("HEAD"), cancellable = true)
    public void render(MapData mapdataIn, boolean noOverlayRendering, CallbackInfo ci) {
        if (HackManager.getHackByName("NoRender").isEnabled() && NoRender.map.getValBoolean()) ci.cancel();
    }
}
