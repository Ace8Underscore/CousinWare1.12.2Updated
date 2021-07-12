package io.ace.nordclient.cousingui;

import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.ClickGuiHack;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class CousinWareGui extends GuiScreen {

    public static ArrayList<io.ace.nordclient.cousingui.Frame> frames;
    public static int color;

    public CousinWareGui() {
        CousinWareGui.frames = new ArrayList<io.ace.nordclient.cousingui.Frame>();
        int frameX = 5;
        for (final Hack.Category category : Hack.Category.values()) {
            final io.ace.nordclient.cousingui.Frame frame = new io.ace.nordclient.cousingui.Frame(category);
            frame.setX(frameX);
            CousinWareGui.frames.add(frame);
            frameX += frame.getWidth() + 10;
        }

    }

    public void initGui() {
    }
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        CousinWareGui.color = new Color(ClickGuiHack.red.getValInt(), ClickGuiHack.green.getValInt(),ClickGuiHack.blue.getValInt(), ClickGuiHack.alpha.getValInt()).getRGB();
        ScaledResolution sr = new ScaledResolution(mc);
        for (final io.ace.nordclient.cousingui.Frame frame : CousinWareGui.frames) {
            frame.renderFrame(this.fontRenderer, frame);
            frame.updatePosition(mouseX, mouseY);
            for (final io.ace.nordclient.cousingui.Component comp : frame.getComponents()) {
                comp.updateComponent(mouseX, mouseY);
                if (frame.category.equals(Hack.Category.CLIENT)) {
                    frame.setDrag(false);
                    frame.setX(100);
                    frame.setY(50);
                }
                if (frame.category.equals(Hack.Category.COMBAT)) {
                    frame.setDrag(false);
                    frame.setX(100);
                    frame.setY(95);
                }
                if (frame.category.equals(Hack.Category.PLAYER)) {
                    frame.setDrag(false);
                    frame.setX(100);
                    frame.setY(140);
                }
                if (frame.category.equals(Hack.Category.MOVEMENT)) {
                    frame.setDrag(false);
                    frame.setX(100);
                    frame.setY(185);
                }
                if (frame.category.equals(Hack.Category.MISC)) {
                    frame.setDrag(false);
                    frame.setX(100);
                    frame.setY(230);
                }
                if (frame.category.equals(Hack.Category.EXPLOIT)) {
                    frame.setDrag(false);
                    frame.setX(100);
                    frame.setY(275);
                }
                if (frame.category.equals(Hack.Category.RENDER)) {
                    frame.setDrag(false);
                    frame.setX(100);
                    frame.setY(320);
                }


            }
        }

    }


    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        for (final io.ace.nordclient.cousingui.Frame frame : CousinWareGui.frames) {
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                frame.setOpen(true);
                setFramesClosed(frame);
                frame.dragX = mouseX - frame.getX();
                frame.dragY = mouseY - frame.getY();
            }
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                if (!frame.isOpen()) {
                    frame.setOpen(true);
                    setFramesClosed(frame);
                } else {
                    frame.setOpen(false);
                }
            }
            if (frame.isOpen() && !frame.getComponents().isEmpty()) {
                for (final io.ace.nordclient.cousingui.Component component : frame.getComponents()) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    private void setFramesClosed(Frame excludeFrame) {
        for (final io.ace.nordclient.cousingui.Frame frame : CousinWareGui.frames) {
            if (!frame.equals(excludeFrame)) {
                frame.setOpen(false);
            }
        }
    }

    protected void keyTyped(final char typedChar, final int keyCode) {
        for (final io.ace.nordclient.cousingui.Frame frame : CousinWareGui.frames) {
            if (frame.isOpen() && keyCode != 1 && !frame.getComponents().isEmpty()) {
                for (final io.ace.nordclient.cousingui.Component component : frame.getComponents()) {
                    component.keyTyped(typedChar, keyCode);
                }
            }
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
    }

    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        for (final io.ace.nordclient.cousingui.Frame frame : CousinWareGui.frames) {
            frame.setDrag(false);
        }
        for (final io.ace.nordclient.cousingui.Frame frame : CousinWareGui.frames) {
            if (frame.isOpen() && !frame.getComponents().isEmpty()) {
                for (final Component component : frame.getComponents()) {
                    component.mouseReleased(mouseX, mouseY, state);
                }
            }
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    static {
        CousinWareGui.color = -1;
    }
}



