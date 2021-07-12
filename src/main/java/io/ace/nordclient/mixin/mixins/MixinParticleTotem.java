package io.ace.nordclient.mixin.mixins;


import io.ace.nordclient.hacks.render.NoRender;
import net.minecraft.client.particle.ParticleSimpleAnimated;
import net.minecraft.client.particle.ParticleTotem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleTotem.class)
public abstract class MixinParticleTotem extends ParticleSimpleAnimated{


    public MixinParticleTotem(World worldIn, double x, double y, double z, int textureIdxIn, int numFrames, float yAccelIn) {
        super(worldIn, x, y, z, textureIdxIn, numFrames, yAccelIn);
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/Particle;particleScale:F"))
    public void renderTotem(World p_i47220_1_, double p_i47220_2_, double p_i47220_4_, double p_i47220_6_, double p_i47220_8_, double p_i47220_10_, double p_i47220_12_, CallbackInfo ci) {
        this.multipleParticleScaleBy((float) NoRender.totemSize.getValDouble());
        this.particleScale = (float) NoRender.totemSize.getValDouble();
    }

}
