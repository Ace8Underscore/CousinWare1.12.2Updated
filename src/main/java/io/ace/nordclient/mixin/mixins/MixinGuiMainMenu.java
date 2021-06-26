package io.ace.nordclient.mixin.mixins;


import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.hwid.UID;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu extends GuiScreen {
    //String online = UID.pastebin.equalsIgnoreCase("https://pastebin.com/raw/0PKUJaf5") ? ChatFormatting.GREEN + "Online" : ChatFormatting.DARK_RED + "Offline";
    String s = ChatFormatting.WHITE + "CousinWare Authentication" + ": " + ChatFormatting.DARK_GREEN + "Online" ;

    @Inject(method = "drawScreen", at = @At("TAIL"), cancellable = true)
    public void drawText(int mouseX, int mouseY, float partialTicks, CallbackInfo ci){
        this.drawString(this.fontRenderer, s, -(this.fontRenderer.getStringWidth(s) / 2) + 84, 2, 65280);
        this.drawString(this.fontRenderer, ChatFormatting.WHITE + "Welcome " + ChatFormatting.LIGHT_PURPLE + UID.getUID(), -(this.fontRenderer.getStringWidth(s) / 2) + 84, 12, 65280);
    }
}