package io.ace.nordclient.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.Core;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.mixin.accessor.IMinecraft;
import io.ace.nordclient.mixin.accessor.IRenderManager;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

/**
 * @author Ace________/Ace_#1233
 */

public class NameTags extends Hack {

    final FontRenderer fontRenderer = mc.fontRenderer;

    public ConcurrentHashMap<EntityPlayer, Integer> popMap;

    int spacingArmor = 0;

    Setting gamemode;
    Setting range;
    Setting friendColor;
    Setting scale;
    Setting reversed;
    Setting armor;
    Setting hands;
    Setting ping;
    Setting health;
    Setting totemPop;
    Setting burrowed;

    public NameTags() {
        super("NameTags", Category.RENDER, 10955851);
        popMap = new ConcurrentHashMap<EntityPlayer, Integer>();
        CousinWare.INSTANCE.settingsManager.rSetting(range = new Setting("Range", this, 100, 0, 1000, false, "NameTagsRange", true));
        CousinWare.INSTANCE.settingsManager.rSetting(reversed = new Setting("Reversed", this, false, "NametagsReversed", true));
        CousinWare.INSTANCE.settingsManager.rSetting(armor = new Setting("Armor", this, true, "NametagsArmor", true));
        CousinWare.INSTANCE.settingsManager.rSetting(hands = new Setting("HeldItems", this, true, "NametagsHeldItems", true));
        CousinWare.INSTANCE.settingsManager.rSetting(gamemode = new Setting("Gamemode", this, true, "NametagsGamemode", true));
        CousinWare.INSTANCE.settingsManager.rSetting(ping = new Setting("Ping", this, true, "NametagsPing", true));
        CousinWare.INSTANCE.settingsManager.rSetting(health = new Setting("Health", this, true, "NametagsHealth", true));
        java.util.ArrayList<String> friendColors = new ArrayList<>();
        friendColors.add("BLACK");
        friendColors.add("RED");
        friendColors.add("AQUA");
        friendColors.add("BLUE");
        friendColors.add("GOLD");
        friendColors.add("GRAY");
        friendColors.add("WHITE");
        friendColors.add("GREEN");
        friendColors.add("YELLOW");
        friendColors.add("DARK_RED");
        friendColors.add("DARK_AQUA");
        friendColors.add("DARK_BLUE");
        friendColors.add("DARK_GRAY");
        friendColors.add("DARK_GREEN");
        friendColors.add("DARK_PURPLE");
        friendColors.add("LIGHT_PURPLE");
        CousinWare.INSTANCE.settingsManager.rSetting(friendColor = new Setting("FriendColor", this, "BLUE", friendColors, "NameTagsFriendColor", true));
        CousinWare.INSTANCE.settingsManager.rSetting(scale = new Setting("Scale", this, 0.05, 0.01, 0.09, false, "NameTagsScale", true));
        CousinWare.INSTANCE.settingsManager.rSetting(burrowed = new Setting("Burrow", this, true,  "NameTagsBurrowed", true));
        CousinWare.INSTANCE.settingsManager.rSetting(totemPop = new Setting("TotemPops", this, true, "", true));
    }

    public void onUpdate() {
        for (final EntityPlayer player : mc.world.playerEntities) {
            if (player.getHealth() == 0 || player.isDead) {
                this.popMap.remove(player);
            }
        }
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        for (EntityPlayer e : mc.world.playerEntities) {
            if (mc.player.getDistance(e.posX, e.posY, e.posZ) <= range.getValDouble()) {
                if (!(mc.player.getName() == e.getName())) {
                    final double eX = e.lastTickPosX + (e.posX - e.lastTickPosX) * ((IMinecraft) mc).getTimer().renderPartialTicks - ((IRenderManager)mc.getRenderManager()).getRenderPosX();
                    final double eY = e.lastTickPosY + (e.posY - e.lastTickPosY) * ((IMinecraft) mc).getTimer().renderPartialTicks - ((IRenderManager)mc.getRenderManager()).getRenderPosY();
                    final double eZ = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * ((IMinecraft) mc).getTimer().renderPartialTicks - ((IRenderManager)mc.getRenderManager()).getRenderPosZ();
                    if (!e.getName().startsWith("Body #")) {
                        renderName(e, eX, eY, eZ);
                    }
                }
                if (mc.gameSettings.thirdPersonView == 2 || mc.gameSettings.thirdPersonView == 1) {
                    final double eX = e.lastTickPosX + (e.posX - e.lastTickPosX) * ((IMinecraft) mc).getTimer().renderPartialTicks - ((IRenderManager)mc.getRenderManager()).getRenderPosX();
                    final double eY = e.lastTickPosY + (e.posY - e.lastTickPosY) * ((IMinecraft) mc).getTimer().renderPartialTicks - ((IRenderManager)mc.getRenderManager()).getRenderPosY();
                    final double eZ = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * ((IMinecraft) mc).getTimer().renderPartialTicks - ((IRenderManager)mc.getRenderManager()).getRenderPosZ();
                    if (!e.getName().startsWith("Body #")) {
                        renderName(e, eX, eY, eZ);
                    }
                }
            }
        }
    }

    public String getGamemode(EntityPlayer entityPlayer) {
        if (entityPlayer.isAllowEdit() && !entityPlayer.isCreative()) return "[S]";
        if (entityPlayer.isCreative()) return "[C]";
        if (entityPlayer.isSpectator()) return "[SP]";
        return "[A]";
    }

    public void renderName(EntityPlayer entityPlayer, double x, double y, double z) {
            GlStateManager.pushMatrix();
            int ping = this.ping.getValBoolean() ? ((mc.getConnection() != null && mc.player != null && mc.getConnection().getPlayerInfo(entityPlayer.getUniqueID()) != null) ? Integer.valueOf(mc.getConnection().getPlayerInfo(entityPlayer.getGameProfile().getId()).getResponseTime()) : -1) : 0;
            int health = this.health.getValBoolean() ? getHealth(entityPlayer) : 0;
            String nameTag = (burrowed.getValBoolean() ? (isBurrowed(entityPlayer) ? ChatFormatting.GREEN + "B " : ChatFormatting.RED + "B ") : "") + getFriendColor(entityPlayer.getName()) + (gamemode.getValBoolean() ? getGamemode(entityPlayer) + " " : "") + entityPlayer.getName() + " " + (this.ping.getValBoolean() ? (getPingColor(ping) + "" + ping + "ms ") : "") + (this.health.getValBoolean() ? getHealthColor(health) + "" + health : "") + ((popMap.containsKey(entityPlayer) && totemPop.getValBoolean()) ? (" " + ChatFormatting.DARK_RED + "-" + popMap.get(entityPlayer)) : "");
            final float distance = mc.player.getDistance(entityPlayer);
            final float var14 = (float) (((distance / 5.0f <= 2.0f) ? 2.0f : (distance / 5.0f * (this.scale.getValDouble() * 10.0f + 1.0f))) * 2.5f * (this.scale.getValDouble() / 10.0f));
            final float var15 = (float) (this.scale.getValDouble() * this.getNametagSize(entityPlayer));
            GL11.glTranslated((float)x, (float)y +
                    // height
                    2.5
                    - (entityPlayer.isSneaking() ? 0.4 : 0.0) + ((distance / 5.0f > 2.0f) ? (distance / 12.0f - 0.7) : 0.0), (float)z);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(mc.getRenderManager().playerViewX, (mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
            GL11.glScalef(-var14, -var14, var14);
             GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
             GL11.glDisable(2929);
            int width;
            if (Core.customFont.getValBoolean()) {
                width = CousinWare.INSTANCE.fontRenderer.getStringWidth(nameTag) / 2 + 1;
            }
            else {
                width = fontRenderer.getStringWidth(nameTag) / 2;
            }

        if (Core.customFont.getValBoolean()) {
            CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(nameTag, -width, 10.0, -1);
        }
        else {
            fontRenderer.drawStringWithShadow(nameTag, (float)(-width), 11.0f, -1);
        }
        if (armor.getValBoolean()) {
            if (reversed.getValBoolean()) {
                for (ItemStack inventoryPlayer : entityPlayer.inventory.armorInventory) {
                    renderItem(inventoryPlayer, 50 - 80 + spacingArmor, 6);
                    spacingArmor += 15;
                    if (spacingArmor == 60) spacingArmor = 0;


                }
            } else {
                for (int i = entityPlayer.inventory.armorInventory.size() - 1; i >= 0; i--) {
                    renderItem(entityPlayer.inventory.armorInventory.get(i), 50 - 80 + spacingArmor, 6);
                    spacingArmor += 15;
                    if (spacingArmor == 60) spacingArmor = 0;
                }
            }
        }
        if (hands.getValBoolean()) {
            renderItem(entityPlayer.getHeldItemMainhand(), 32, 6);
            renderItem(entityPlayer.getHeldItemOffhand(), -45, 6);
        }
       // NordTessellator.drawBorderedRect(-width - 3, 8, width + 2.5, 21, .6, 0x4D827f7e, 0xb3afad);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);

            GlStateManager.resetColor();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
    }

    public float getNametagSize(final EntityLivingBase player) {
        final ScaledResolution scaledRes = new ScaledResolution(mc);
        final double twoDscale = scaledRes.getScaleFactor() / Math.pow(scaledRes.getScaleFactor(), 2.0);
        return (float)twoDscale + mc.player.getDistance(player) / 7.0f;
    }

    public int getHealth(EntityPlayer entityPlayer) {
        return (int) (entityPlayer.getAbsorptionAmount() + entityPlayer.getHealth());
    }

    public ChatFormatting getHealthColor(int health) {
        if (health > 18) return ChatFormatting.GREEN;
        if (health > 10) return ChatFormatting.YELLOW;
        if (health > 6) return ChatFormatting.RED;
        return ChatFormatting.DARK_RED;
    }

    public ChatFormatting getPingColor(float ping) {
        if (ping > 225) return ChatFormatting.DARK_RED;
        if (ping > 175) return ChatFormatting.RED;
        if (ping > 100) return ChatFormatting.YELLOW;
        if (ping > 50) return ChatFormatting.DARK_GREEN;
        return ChatFormatting.GREEN;

    }

    private void renderItem(ItemStack stack, int x, int y) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
        mc.getRenderItem().zLevel = -100.0F;
        GlStateManager.scale(1, 1, 0.01f);
        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, (y / 2) - 12);
        mc.getRenderItem().renderItemOverlays(mc.fontRenderer, stack, x, (y / 2) - 12);
        mc.getRenderItem().zLevel = 0.0F;
        GlStateManager.scale(1, 1, 1);
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        GlStateManager.disableDepth();
        if (isMaxEnchants(stack))  renderEnchantTextMax(stack, x, y - 18);
        else renderEnchantText(stack, x, y - 18);

        GlStateManager.enableDepth();
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GL11.glPopMatrix();
    }

    private void renderEnchantText(ItemStack stack, int x, int y) {
        int encY = y - 4;
        int yCount = encY - 5;
        if (stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemElytra) {
            float green = ((float) stack.getMaxDamage() - (float) stack.getItemDamage()) / (float) stack.getMaxDamage();
            float red = 1 - green;
            int dmg = 100 - (int) (red * 100);
            if (Core.customFont.getValBoolean()) CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(dmg + "%", x * 2 + 6, y + 22, new Color((int) (red * 255), (int) (green * 255), 0).getRGB());
            else fontRenderer.drawStringWithShadow(dmg + "%", x * 2 + 6, y + 22, new Color((int) (red * 255), (int) (green * 255), 0).getRGB());
        }

        NBTTagList enchants = stack.getEnchantmentTagList();
        for (int index = 0; index < enchants.tagCount(); ++index) {
            short id = enchants.getCompoundTagAt(index).getShort("id");
            short level = enchants.getCompoundTagAt(index).getShort("lvl");
            Enchantment enc = Enchantment.getEnchantmentByID(id);

            if (enc != null) {
                String encName = "";

                    encName = enc.isCurse()
                            ? TextFormatting.WHITE
                            + enc.getTranslatedName(level).substring(11).substring(0, 1).toLowerCase()
                            : enc.getTranslatedName(level).substring(0, 1).toLowerCase();
                    encName = encName + level;

                GL11.glPushMatrix();
                GL11.glScalef(0.8f, 0.8f, 0);
                if (Core.customFont.getValBoolean()) CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(encName, x * 2 + 13, yCount + 35, -1);
                else fontRenderer.drawStringWithShadow(encName, x * 2 + 13, yCount + 35, -1);
                GL11.glScalef(2f, 2f, 2);
                GL11.glPopMatrix();
                encY += 8;
                yCount -= 10;
            }
        }
    }

    private void renderEnchantTextMax(ItemStack stack, int x, int y) {
        int encY = y - 4;
        int yCount = encY - 5;
        if (stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemElytra) {
            float green = ((float) stack.getMaxDamage() - (float) stack.getItemDamage()) / (float) stack.getMaxDamage();
            float red = 1 - green;
            int dmg = 100 - (int) (red * 100);
            if (Core.customFont.getValBoolean())
                CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(dmg + "%", x * 2 + 6, y + 23, new Color((int) (red * 255), (int) (green * 255), 0).getRGB());
            else
                fontRenderer.drawStringWithShadow(dmg + "%", x * 2 + 6, y + 23, new Color((int) (red * 255), (int) (green * 255), 0).getRGB());

            GL11.glPushMatrix();
            GL11.glScalef(0.8f, 0.8f, 0);
            if (stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemElytra) {
                if (Core.customFont.getValBoolean())
                    CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "max", x * 2 + (spacingArmor / 2.2) - 5, yCount - 3, -1);
                else
                    fontRenderer.drawStringWithShadow("max", (float) (x * 2 + (spacingArmor / 2.2) - 5), yCount - 3, -1);
            } else {
                if(Core.customFont.getValBoolean()) CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(ChatFormatting.RED + "max", (x * 2 + 23), yCount - 3, -1);
                else fontRenderer.drawStringWithShadow("max", (float) (x * 2 + 23), yCount - 3, -1);

            }
            GL11.glScalef(2f, 2f, 2);
            GL11.glPopMatrix();

        }
    }

    public boolean isBurrowed(Entity entity) {
        return mc.world.getBlockState(new BlockPos(entity.posX, entity.posY, entity.posZ)).getBlock().equals(Blocks.OBSIDIAN) || mc.world.getBlockState(new BlockPos(entity.posX, entity.posY, entity.posZ)).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(new BlockPos(entity.posX, entity.posY + .25, entity.posZ)).getBlock().equals(Blocks.ENDER_CHEST);
    }





    public boolean isMaxEnchants(final ItemStack stack) {
        final NBTTagList enchants = stack.getEnchantmentTagList();
        final ArrayList<String> enchantments = new ArrayList();
        int count = 0;
        if (enchants == null) {
            return false;
        }
        for (int index = 0; index < enchants.tagCount(); ++index) {
            final short id = enchants.getCompoundTagAt(index).getShort("id");
            final short level = enchants.getCompoundTagAt(index).getShort("lvl");
            final Enchantment enc = Enchantment.getEnchantmentByID(id);
            if (enc != null) {
                enchantments.add(enc.getTranslatedName(level));
            }
        }
        if (stack.getItem() == Items.DIAMOND_HELMET) {
            final int maxnum = 5;
            for (final String s : enchantments) {
                if (s.equalsIgnoreCase("Protection IV")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Respiration III")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Aqua Affinity")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Unbreaking III")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Mending")) {
                    ++count;
                }
            }
            return count >= maxnum;
        }
        if (stack.getItem() == Items.DIAMOND_CHESTPLATE) {
            final int maxnum = 3;
            for (final String s : enchantments) {
                if (s.equalsIgnoreCase("Protection IV")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Unbreaking III")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Mending")) {
                    ++count;
                }
            }
            return count >= maxnum;
        }
        if (stack.getItem() == Items.DIAMOND_LEGGINGS) {
            final int maxnum = 3;
            for (final String s : enchantments) {
                if (s.equalsIgnoreCase("Blast Protection IV")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Unbreaking III")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Mending")) {
                    ++count;
                }
            }
            return count >= maxnum;
        }
        if (stack.getItem() == Items.DIAMOND_BOOTS) {
            final int maxnum = 5;
            for (final String s : enchantments) {
                if (s.equalsIgnoreCase("Protection IV")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Feather Falling IV")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Depth Strider III")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Unbreaking III")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Mending")) {
                    ++count;
                }
            }
            return count >= maxnum;
        }
        if (stack.getItem() instanceof ItemPickaxe) {
            final int maxnum = 4;
            for (final String s : enchantments) {
                if (s.equalsIgnoreCase("Efficiency V")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Fortune III")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Unbreaking III")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Mending")) {
                    ++count;
                }
            }
            return count >= maxnum;
        }
        if (stack.getItem() instanceof ItemSword) {
            final int maxnum = 6;
            for (final String s : enchantments) {
                if (s.equalsIgnoreCase("Sharpness V")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Fire Aspect II")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Looting III")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Sweeping Edge III")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Unbreaking III")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Mending")) {
                    ++count;
                }
            }
            return count >= maxnum;
        }
        if (stack.getItem() == Items.ELYTRA) {
            final int maxnum = 2;
            for (final String s : enchantments) {
                if (s.equalsIgnoreCase("Unbreaking III")) {
                    ++count;
                }
                if (s.equalsIgnoreCase("Mending")) {
                    ++count;
                }

            }
            return count >= maxnum;
        }
        return false;

    }

    private ChatFormatting getFriendColor(String playerName){
        if (FriendManager.isFriend(playerName)) {
        switch (friendColor.getValString()) {
            case "BLACK":
                return ChatFormatting.BLACK;
            case "RED":
                return ChatFormatting.RED;
            case "AQUA":
                return ChatFormatting.AQUA;
            case "BLUE":
                return ChatFormatting.BLUE;
            case "GOLD":
                return ChatFormatting.GOLD;
            case "GRAY":
                return ChatFormatting.GRAY;
            case "WHITE":
                return ChatFormatting.WHITE;
            case "GREEN":
                return ChatFormatting.GREEN;
            case "YELLOW":
                return ChatFormatting.YELLOW;
            case "DARK_RED":
                return ChatFormatting.DARK_RED;
            case "DARK_AQUA":
                return ChatFormatting.DARK_AQUA;
            case "DARK_BLUE":
                return ChatFormatting.DARK_BLUE;
            case "DARK_GRAY":
                return ChatFormatting.DARK_GRAY;
            case "DARK_GREEN":
                return ChatFormatting.DARK_GREEN;
            case "DARK_PURPLE":
                return ChatFormatting.LIGHT_PURPLE;
            case "LIGHT_PURPLE":
                return ChatFormatting.DARK_PURPLE;
            default:
                return ChatFormatting.WHITE;
        }

        }
        return ChatFormatting.WHITE;


    }

    public void onTotemPop(EntityPlayer eventTotemPop) {
        if (totemPop.getValBoolean()) {
            int pops = this.popMap.getOrDefault(eventTotemPop, 0) + 1;
            this.popMap.put(eventTotemPop, pops);
        }
    }

    @Listener
    public void onUpdate(PacketEvent.Receive event) {
        if (totemPop.getValBoolean()) {
        if (event.getPacket() instanceof SPacketEntityStatus) {
            final SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
            if (packet.getOpCode() == 35 && packet.getEntity(mc.world) instanceof EntityPlayer) {
                EntityPlayer entity = (EntityPlayer) packet.getEntity(mc.world);
                onTotemPop(entity);

            }
        }
        }
    }
}



