package io.ace.nordclient.utilz.configz;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.render.Xray;
import io.ace.nordclient.hud.Hud;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.managers.HackManager;
import io.ace.nordclient.managers.HudManager;
import io.ace.nordclient.utilz.FriendUtil;
import io.ace.nordclient.utilz.Setting;
import io.ace.nordclient.utilz.font.CFontRenderer;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.*;
import java.util.Iterator;

public class ConfigUtils {
    final static Minecraft mc = Minecraft.getMinecraft();
    public static File Nord;
    public static File Settings;
    public static String publicname;
    public static String configFolder = "default";

    /**
     * @author Finz0
     *
     **/

    public ConfigUtils() {


    }

    public static void startUp() {
        loadConfigFolder();
        Nord = new File(mc.gameDir + File.separator + "CousinWare" + File.separator + configFolder);
        if (!Nord.exists()) {
            Nord.mkdirs();
        }

        Settings = new File(mc.gameDir + File.separator + "CousinWare" + File.separator + configFolder + File.separator + "Settings");
        if (!Settings.exists()) {
            Settings.mkdirs();
        }

        loadMods();
        loadDrawn();
        loadBinds();
        loadPrefix();
        loadFriends();
        loadSettingsList();
        loadFont();
        loadHuds();
        loadHudPos();
        //loadXray();
    }

    public static void loadAll() {
        Nord = new File(mc.gameDir + File.separator + "CousinWare" + File.separator + configFolder);
        //
        if (!Nord.exists()) {
            Nord.mkdirs();
        }

        Settings = new File(mc.gameDir + File.separator + "CousinWare" + File.separator + configFolder + File.separator + "Settings");
        if (!Settings.exists()) {
            Settings.mkdirs();
        }

        loadMods();
        loadDrawn();
        loadBinds();
        loadPrefix();
        loadFriends();
        loadSettingsList();
        loadFont();
        loadHuds();
        loadHudPos();
        //loadXray();
    }

    public static void saveConfigFolder(String s) {
        try {
            File file = new File(mc.gameDir + File.separator + "CousinWare" + File.separator, "ConfigFolder");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(s);

            out.close();
        } catch (Exception var5) {
        }
    }

    public static void loadConfigFolder() {
        try {
            File file = new File(mc.gameDir + File.separator + "CousinWare" + File.separator, "ConfigFolder");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null) {
                configFolder = line;
            }

            } catch(Exception e){
                configFolder = "default";

            }
        }






    public static void saveBinds() {
        try {
            File file = new File(Nord.getAbsolutePath(), "Binds.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = HackManager.getHacks().iterator();

            while (var3.hasNext()) {
                Hack hack = (Hack) var3.next();
                out.write(hack.getName() + ":" + Keyboard.getKeyName(hack.getBind()));
                out.write("\r\n");
            }

            out.close();
        } catch (Exception var5) {
        }

    }

    public static void loadBinds() {
        try {
            File file = new File(Nord.getAbsolutePath(), "Binds.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                String bind = curLine.split(":")[1];
                for (Hack h : HackManager.getHacks()) {
                    if (h != null && h.getName().equalsIgnoreCase(name)) {
                        h.setBind(Keyboard.getKeyIndex(bind));
                    }
                }
            }

            br.close();
        } catch (Exception var11) {
            var11.printStackTrace();
            //saveBinds();
        }

    }


    public static void saveMods() {
        try {
            File file = new File(Nord.getAbsolutePath(), "EnabledHacks.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = HackManager.getHacks().iterator();

            while (var3.hasNext()) {
                Hack hack = (Hack) var3.next();
                if (hack.isEnabled() && !hack.getName().equalsIgnoreCase("crystalaura") && !hack.getName().equalsIgnoreCase("AutoEGapFinder") && !hack.getName().equalsIgnoreCase("fly") && !hack.getName().equalsIgnoreCase("FakePlayer") && !hack.getName().equalsIgnoreCase("ChunkScanner") && !hack.getName().equalsIgnoreCase("HoleFiller")) {
                    out.write(hack.getName());
                    out.write("\r\n");
                }
            }

            out.close();
        } catch (Exception var5) {
        }

    }

    public static void saveFriends() {
        try {
            File file = new File(Nord.getAbsolutePath(), "Friends.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = FriendManager.getFriends().iterator();

            while(var3.hasNext()) {
                FriendUtil f = (FriendUtil)var3.next();
                out.write(f.getName());
                out.write("\r\n");
            }

            out.close();
        } catch (Exception var5) {
        }

    }

    public static void loadFriends() {
        try {
            File file = new File(Nord.getAbsolutePath(), "Friends.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            FriendManager.friends.clear();
            String line;
            while((line = br.readLine()) != null) {
                FriendManager.addFriend(line);
            }

            br.close();
        } catch (Exception var6) {
            var6.printStackTrace();


        }

    }

    public static void saveXray() {
        try {
            File file = new File(Nord.getAbsolutePath(), "Xray.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));


            while (Xray.xrayBlocks.iterator().hasNext()) {
                for (int i = 0; i < Xray.xrayBlocks.size(); i++) {
                    Xray x = new Xray();
                    out.write(Xray.xrayBlocks.get(i));
                    out.write("\r\n");
                }
            }

            out.close();
        } catch (Exception var5) {
        }

    }

    public static void loadXray() {
        try {
            File file = new File(Nord.getAbsolutePath(), "Xray.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            FriendManager.friends.clear();
            String line;
            while((line = br.readLine()) != null) {
                Xray.xrayBlocks.add(line);
            }

            br.close();
        } catch (Exception var6) {
            var6.printStackTrace();

        }

    }

  /*  public static void saveEnemies() {
        try {
            File file = new File(Nord.getAbsolutePath(), "Enemies.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = Enemies.getEnemies().iterator();

            while (var3.hasNext()) {
                Enemy e = (Enemy) var3.next();
                out.write(e.getName());
                out.write("\r\n");
            }

            out.close();
        } catch (Exception var5) {
        }
    }

   public static void loadEnemies() {
        try {
            File file = new File(Nord.getAbsolutePath(), "Enemies.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            Enemies.enemies.clear();
            String line;
            while((line = br.readLine()) != null) {
                Enemies.addEnemy(line);
            }

            br.close();
        } catch (Exception var6) {
            var6.printStackTrace();
            //saveEnemies();
        }
    } */

 /*   public static void saveGui() {
        try {
            File file = new File(Nord.getAbsolutePath(), "Gui.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = ClickGUI.panels.iterator();

            while(var3.hasNext()) {
                Panel p = (Panel)var3.next();
                out.write(p.title + ":" + p.x + ":" + p.y + ":" + p.extended);
                out.write("\r\n");
            }

            out.close();
        } catch (Exception var5) {
        }

    }

    public static void loadGui() {
        try {
            File file = new File(Nord.getAbsolutePath(), "Gui.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                String x = curLine.split(":")[1];
                String y = curLine.split(":")[2];
                String e = curLine.split(":")[3];
                double x1 = Double.parseDouble(x);
                double y1 = Double.parseDouble(y);
                boolean ext = Boolean.parseBoolean(e);
                Panel p = ClickGUI.frames.get(name);
                if (p != null) {
                    p.x = x1;
                    p.y = y1;
                    p.extended = ext;
                }
            }

            br.close();
        } catch (Exception var17) {
            var17.printStackTrace();
            //saveGui();
        }

    }

    public static void saveHudComponents() {
        try {
            File file = new File(Nord.getAbsolutePath(), "HudComponents.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = HudComponentManager.hudComponents.iterator();

            while(var3.hasNext()) {
                Panel p = (Panel)var3.next();
                out.write(p.title + ":" + p.x + ":" + p.y + ":" + p.extended + ":" + p.isHudComponentPinned);
                out.write("\r\n");
            }

            out.close();
        } catch (Exception var5) {
        }

    }

    public static void loadHudComponents() {
        try {
            File file = new File(Nord.getAbsolutePath(), "HudComponents.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                String x = curLine.split(":")[1];
                String y = curLine.split(":")[2];
                String e = curLine.split(":")[3];
                String pin = curLine.split(":")[4];
                double x1 = Double.parseDouble(x);
                double y1 = Double.parseDouble(y);
                boolean ex = Boolean.parseBoolean(e);
                boolean pinned = Boolean.parseBoolean(pin);
                Panel p = HudComponentManager.getHudComponentByName(name);
                if (p != null) {
                    p.x = x1;
                    p.y = y1;
                    p.extended = ex;
                    p.isHudComponentPinned = pinned;
                }
            }

            br.close();
        } catch (Exception var17) {
            var17.printStackTrace();
            //saveHudComponents();
        }

    } */

    public static void savePrefix() {
        try {
            File file = new File(Nord.getAbsolutePath(), "CommandPrefix.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(Command.getClientPrefix());
            out.write("\r\n");
            out.close();
        } catch (Exception var3) {
        }

    }

    public static void loadPrefix() {
        try {
            File file = new File(Nord.getAbsolutePath(), "CommandPrefix.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null) {
                Command.setClientPrefix(line);
            }

            br.close();
        } catch (Exception var6) {
            var6.printStackTrace();
            //savePrefix();
        }

    }

    public static void saveFont() {
        try {
            File file = new File(Nord.getAbsolutePath(), "Font.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(CousinWare.INSTANCE.fontRenderer.getFontName() + ":" + CousinWare.INSTANCE.fontRenderer.getFontSize());
            out.write("\r\n");
            out.close();
        } catch (Exception var3) {
        }

    }

    public static void loadFont() {
        try {
            File file = new File(Nord.getAbsolutePath(), "Font.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while((line = br.readLine()) != null) {
                publicname = line.split(":")[0];
                String size = line.split(":")[1];
                int sizeInt = Integer.parseInt(size);
                CousinWare.INSTANCE.fontRenderer = new CFontRenderer(new Font(publicname, Font.PLAIN, sizeInt), true, false);
                CousinWare.INSTANCE.fontRenderer.setFont(new Font(publicname, Font.PLAIN, sizeInt));
                CousinWare.INSTANCE.fontRenderer.setAntiAlias(true);
                CousinWare.INSTANCE.fontRenderer.setFractionalMetrics(false);
                CousinWare.INSTANCE.fontRenderer.setFontName(publicname);
                CousinWare.INSTANCE.fontRenderer.setFontSize(sizeInt);
                //CousinWare.INSTANCE.fontRenderer = new CFontRendererGui(new Font(name, Font.PLAIN, sizeInt), true, false);
                CousinWare.INSTANCE.fontRenderer.setFont(new Font(publicname, Font.PLAIN, sizeInt));
                CousinWare.INSTANCE.fontRenderer.setAntiAlias(true);
                CousinWare.INSTANCE.fontRenderer.setFractionalMetrics(false);
                CousinWare.INSTANCE.fontRenderer.setFontName(publicname);

            }

            br.close();
        } catch (Exception var6) {
            var6.printStackTrace();
            //saveFont();
        }

    }


    public static void saveDrawn() {
        try {
            File file = new File(Nord.getAbsolutePath(), "Drawn.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = HackManager.getHacks().iterator();

            while (var3.hasNext()) {
                Hack hack = (Hack) var3.next();
                out.write(hack.getName() + ":" + hack.isDrawn());
                out.write("\r\n");
            }

            out.close();
        } catch (Exception var5) {
        }

    }

    public static void loadDrawn() {
        try {
            File file = new File(Nord.getAbsolutePath(), "Drawn.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                String isOn = curLine.split(":")[1];
                boolean drawn = Boolean.parseBoolean(isOn);
                for (Hack h : HackManager.getHacks()) {
                    if (h.getName().equalsIgnoreCase(name)) {
                        h.drawn = drawn;
                    }
                }
            }

            br.close();
        } catch (Exception var11) {
            var11.printStackTrace();
            //saveDrawn();
        }

    }


    public static void loadMods() {
        try {
            File file = new File(Nord.getAbsolutePath(), "EnabledHacks.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null) {
                Iterator var6 = HackManager.getHacks().iterator();

                while (var6.hasNext()) {
                    Hack h = (Hack) var6.next();
                    if (h.getName().equals(line)) {
                        h.enable();
                    }
                }
            }

            br.close();
        } catch (Exception var8) {
            var8.printStackTrace();
            //saveHacks();
        }

    }

    public static void saveHuds() {
        try {
            File file = new File(Nord.getAbsolutePath(), "EnabledHuds.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = HudManager.getHuds().iterator();

            while (var3.hasNext()) {
                Hud hud = (Hud) var3.next();
                if (hud.isEnabled()) {
                    out.write(hud.getName());
                    out.write("\r\n");
                }
            }

            out.close();
        } catch (Exception var5) {
        }

    }

    public static void loadHuds() {
        try {
            File file = new File(Nord.getAbsolutePath(), "EnabledHuds.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null) {
                Iterator var6 = HudManager.getHuds().iterator();

                while (var6.hasNext()) {
                    Hud h = (Hud) var6.next();
                    if (h.getName().equals(line)) {
                        h.enable();
                    }
                }
            }

            br.close();
        } catch (Exception var8) {
            var8.printStackTrace();
            //saveHacks();
        }

    }


    public static void saveSettingsList() {
        File file;
        BufferedWriter out;
        Iterator var3;
        Setting i;
        try {
            file = new File(Settings.getAbsolutePath(), "Number.txt");
            out = new BufferedWriter(new FileWriter(file));
            var3 = CousinWare.INSTANCE.settingsManager.getSettings().iterator();

            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.isSlider()) {
                    out.write(i.getId() + ":" + i.getValDouble() + ":" + i.getParentMod().getName() + "\r\n");
                }
            }

            out.close();
        } catch (Exception var7) {
        }

        try {
            file = new File(Settings.getAbsolutePath(), "Boolean.txt");
            out = new BufferedWriter(new FileWriter(file));
            var3 = CousinWare.INSTANCE.settingsManager.getSettings().iterator();

            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.isCheck()) {
                    out.write(i.getId() + ":" + i.getValBoolean() + ":" + i.getParentMod().getName() + "\r\n");
                }
            }

            out.close();
        } catch (Exception var6) {
        }

        try {
            file = new File(Settings.getAbsolutePath(), "String.txt");
            out = new BufferedWriter(new FileWriter(file));
            var3 = CousinWare.INSTANCE.settingsManager.getSettings().iterator();

            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.isCombo()) {
                    out.write(i.getId() + ":" + i.getValString() + ":" + i.getParentMod().getName() + "\r\n");
                }
            }

            out.close();
        } catch (Exception var5) {
        }

        try {
            file = new File(Settings.getAbsolutePath(), "Color.txt");
            out = new BufferedWriter(new FileWriter(file));
            var3 = CousinWare.INSTANCE.settingsManager.getSettings().iterator();

            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.isColorPicker()) {
                    out.write(i.getId() + ":" + i.getValColor().getRGB() + ":" + i.getParentMod().getName() + "\r\n");
                }
            }

            out.close();
        } catch (Exception var7) {
        }

    }

    public static void loadSettingsList() {
        File file;
        FileInputStream fstream;
        DataInputStream in;
        BufferedReader br;
        String line;
        String curLine;
        String name;
        String isOn;
        String m;
        Setting mod;
        int color;
        try {
            file = new File(Settings.getAbsolutePath(), "Number.txt");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            while((line = br.readLine()) != null) {
                curLine = line.trim();
                name = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Hack h : HackManager.getHacks()) {
                    if (h != null && h.getName().equalsIgnoreCase(m)) {
                        mod = CousinWare.INSTANCE.settingsManager.getSettingByID(name);
                        mod.setValDouble(Double.parseDouble(isOn));
                    }
                }
            }

            br.close();
        } catch (Exception var13) {
            var13.printStackTrace();
            //saveSettingsList();
        }

        try {
            file = new File(Settings.getAbsolutePath(), "Color.txt");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            while((line = br.readLine()) != null) {
                curLine = line.trim();
                name = curLine.split(":")[0];
                color = Integer.parseInt(curLine.split(":")[1]);
                m = curLine.split(":")[2];
                for(Hack h : HackManager.getHacks()) {
                    if (h != null && h.getName().equalsIgnoreCase(m)) {
                        mod = CousinWare.INSTANCE.settingsManager.getSettingByID(name);
                        mod.setValColor(new Color(color));
                    }
                }
            }

            br.close();
        } catch (Exception var13) {
            var13.printStackTrace();
            //saveSettingsList();
        }

        try {
            file = new File(Settings.getAbsolutePath(), "Boolean.txt");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            while((line = br.readLine()) != null) {
                curLine = line.trim();
                name = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Hack h : HackManager.getHacks()) {
                    if (h != null && h.getName().equalsIgnoreCase(m)) {
                        mod = CousinWare.INSTANCE.settingsManager.getSettingByID(name);
                        mod.setValBoolean(Boolean.parseBoolean(isOn));
                    }
                }
            }

            br.close();
        } catch (Exception var12) {
            var12.printStackTrace();
            //saveSettingsList();
        }

        try {
            file = new File(Settings.getAbsolutePath(), "String.txt");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            while((line = br.readLine()) != null) {
                curLine = line.trim();
                name = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Hack h : HackManager.getHacks()) {
                    if (h != null && h.getName().equalsIgnoreCase(m)) {
                        mod = CousinWare.INSTANCE.settingsManager.getSettingByID(name);
                        mod.setValString(isOn);
                    }
                }
            }

            br.close();
        } catch (Exception var11) {
            var11.printStackTrace();
            //aveSettingsList();
        }

    }
    public static void saveHudPos() {
        try {
            File file = new File(Nord.getAbsolutePath(), "HudPos.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = HudManager.getHuds().iterator();

            while (var3.hasNext()) {
                Hud hud = (Hud) var3.next();
                out.write(hud.getName() + ":" + hud.x + ":" + hud.y);
                out.write("\r\n");
            }

            out.close();
        } catch (Exception var5) {
        }

    }

    public static void loadHudPos() {
        try {
            File file = new File(Nord.getAbsolutePath(), "HudPos.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                String x = curLine.split(":")[1];
                String y = curLine.split(":")[2];
                for (Hud h : HudManager.getHuds()) {
                    if (h.getName().equalsIgnoreCase(name)) {
                        h.x = Integer.parseInt(x);
                        h.y = Integer.parseInt(y);
                    }
                }
            }

            br.close();
        } catch (Exception var11) {
            var11.printStackTrace();
            //saveDrawn();
        }

    }
}