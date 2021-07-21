package io.ace.nordclient.hacks.render;

import com.mojang.authlib.GameProfile;
import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PopRender extends Hack {

    public ConcurrentHashMap<EntityPlayer, Integer> popMap;

    public static Setting colorSync;
    public static Setting colored;
    public static Setting textured;
    public static Setting rainbow;
    public static Setting saturation;
    public static Setting brightness;
    public static Setting speed;
    public static Setting xqz;
    public static Setting red;
    public static Setting green;
    public static Setting blue;
    public static Setting alpha;

    public PopRender() {
        super("PopRender", Category.RENDER, 1123108);
        popMap = new ConcurrentHashMap<EntityPlayer, Integer>();
        CousinWare.INSTANCE.settingsManager.rSetting(colorSync = new Setting("Sync", this, false, "PopRenderSync", true));
        CousinWare.INSTANCE.settingsManager.rSetting(colored = new Setting("Colored", this, false, "PopRenderColored", true));
        CousinWare.INSTANCE.settingsManager.rSetting(textured = new Setting("Textured", this, false, "PopRenderTextured", true));
        CousinWare.INSTANCE.settingsManager.rSetting(colorSync = new Setting("Sync", this, false, "PopRenderSync", true));
        CousinWare.INSTANCE.settingsManager.rSetting(saturation = new Setting("Saturation", this, 50, 0, 100, true, "PopRenderSaturation", true));
        CousinWare.INSTANCE.settingsManager.rSetting(brightness = new Setting("Brightness", this, 100, 0, 100, true, "PopRenderBrightness", true));
        CousinWare.INSTANCE.settingsManager.rSetting(speed = new Setting("Speed", this, 40, 1, 100, true, "PopRenderSpeed", true));
        CousinWare.INSTANCE.settingsManager.rSetting(xqz = new Setting("XQZ", this, false, "PopRenderXQZ", true));
        CousinWare.INSTANCE.settingsManager.rSetting(red = new Setting("Red", this, 255, 0, 255, true, "PopRenderRed", true));
        CousinWare.INSTANCE.settingsManager.rSetting(green = new Setting("Green", this, 26, 0, 255, true, "PopRenderGreen", true));
        CousinWare.INSTANCE.settingsManager.rSetting(blue = new Setting("Blue", this, 42, 0, 255, true, "PopRenderBlue", true));
        CousinWare.INSTANCE.settingsManager.rSetting(alpha = new Setting("Alpha", this, 255, 0, 255, true, "PopRenderAlpha", true));

    }

    EntityPlayer entity;

    @Override
    public void onUpdate() {
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        RenderPlayer re = new RenderPlayer(mc.getRenderManager(), false);
        if (entity != null) {

            ////////
        }


    }

    public void onTotemPop(EntityPlayer eventTotemPop) {
            int pops = this.popMap.getOrDefault(eventTotemPop, 0) + 1;
            this.popMap.put(eventTotemPop, pops);
            java.util.Random r = new Random();
            int id = r.nextInt(10000000);
            entity = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("d8d5a923-7b20-43d8-883b-1150148d6955"), "Test"));
            entity.copyLocationAndAnglesFrom(eventTotemPop);
            entity.rotationYaw = eventTotemPop.rotationYaw;
            entity.rotationYawHead = eventTotemPop.rotationYawHead;
            mc.world.addEntityToWorld(id, entity);
            //RenderLivingBase.renderOffsetAABB(new AxisAlignedBB(entity.getPosition()), entity.getPosition().getX(), entity.getPosition().getY(), entity.getPosition().getZ());
            mc.entityRenderer.loadEntityShader(entity);
//            RenderPlayer.renderOffsetAABB(new AxisAlignedBB(entity.getPosition()), entity.getPosition().getX(), entity.getPosition().getY(), entity.getPosition().getZ());
       // RenderPlayer re = new RenderPlayer(mc.renderManager, false);
          //  re.doRenderShadowAndFire(entity, entity.getPosition().getX(), entity.getPosition().getY(), entity.getPosition().getZ(), entity.rotationYaw, mc.getRenderPartialTicks());
    }



    @Listener
    public void onUpdate(PacketEvent.Receive event) {

            if (event.getPacket() instanceof SPacketEntityStatus) {
                final SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
                if (packet.getOpCode() == 35 && packet.getEntity(mc.world) instanceof EntityPlayer) {
                    EntityPlayer entity = (EntityPlayer) packet.getEntity(mc.world);
                    onTotemPop(entity);


            }
        }
    }
}
