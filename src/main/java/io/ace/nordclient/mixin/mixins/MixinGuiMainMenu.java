package io.ace.nordclient.mixin.mixins;


import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.hwid.UID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public abstract class MixinGuiMainMenu extends GuiScreen {
    private final ResourceLocation backgroundTexture = new ResourceLocation("");
    //String online = UID.pastebin.equalsIgnoreCase("https://pastebin.com/raw/0PKUJaf5") ? ChatFormatting.GREEN + "Online" : ChatFormatting.DARK_RED + "Offline";
    String s = ChatFormatting.WHITE + "CousinWare Authentication" + ": " + ChatFormatting.DARK_GREEN + "Online" ;



    @Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V"), cancellable = true)
    public void removeMenuLogoInit(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        float titleX = width / 2 - 100, titleY = 10;
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();

        bindTexture(new ResourceLocation("custom/cousinwarelogo.png"), titleX, titleY, 200, 63, 0F, 0F, 1F, 1F);
        GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

        GlStateManager.enableTexture2D();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

        GlStateManager.popMatrix();
    }

    @Inject(method = "drawScreen", at = @At("TAIL"))
    public void drawText(int mouseX, int mouseY, float partialTicks, CallbackInfo ci){
        this.drawString(this.fontRenderer, s, -(this.fontRenderer.getStringWidth(s) / 2) + 84, 2, 65280);
        this.drawString(this.fontRenderer, ChatFormatting.WHITE + "Welcome " + ChatFormatting.LIGHT_PURPLE + UID.getUID(), -(this.fontRenderer.getStringWidth(s) / 2) + 84, 12, 65280);
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawTexturedModalRect(IIIIII)V"))
    public void removeMenuLogoRendering(GuiMainMenu guiMainMenu, int x, int y, int textureX, int textureY, int width, int height) {

    }

    private static void bindTexture(ResourceLocation textureName, float x, float y, float width, float height, float u, float v, float t, float s) {
        Minecraft.getMinecraft().renderEngine.bindTexture(textureName);
        GlStateManager.enableTexture2D();
        renderMenu(x, y,width, height, u, v, t, s);
    }

    private static void renderMenu(float x, float y, float width, float height, float u, float v, float t, float s) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder renderer = tessellator.getBuffer();
        renderer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(x + width, y, 0F).tex(t, v).endVertex();
        renderer.pos(x, y, 0F).tex(u, v).endVertex();
        renderer.pos(x, y + height, 0F).tex(u, s).endVertex();
        renderer.pos(x, y + height, 0F).tex(u, s).endVertex();
        renderer.pos(x + width, y + height, 0F).tex(t, s).endVertex();
        renderer.pos(x + width, y, 0F).tex(t, v).endVertex();
        tessellator.draw();
    }


}