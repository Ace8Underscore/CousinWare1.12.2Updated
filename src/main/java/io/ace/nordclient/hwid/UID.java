package io.ace.nordclient.hwid;

import net.minecraft.client.Minecraft;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public class UID {

    static List<String> hwids = new ArrayList<>();

    public static String pastebin = "https://pastebin.com/raw/0PKUJaf5";

    public static String getUID() {
        long currentHWID = Runtime.getRuntime().availableProcessors() + ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
        if (currentHWID == 17123528716L) return "UID 2";
        else if (currentHWID == 17018974220L) return "UID 8";
        else if (currentHWID == 17102532620L) return "UID 10";
        else if (currentHWID == 17104789508L) return "UID 9";
        else if (currentHWID == 34306510860L) return "UID 7";
        else if (currentHWID == 16990093318L) return "UID 12";
        else if (currentHWID == 8552308740L) return "UID 11";
        else if (currentHWID == 34290864134L) return "UID 1";
        else if (currentHWID == 25730654216L) return "UID 14";
        else if (currentHWID == 8535744516L) return "UID 15";
        else if (currentHWID == 8589934600L) return "UID 19";
        else if (currentHWID == 17101369350L) return "UID 21";
        else if (currentHWID == 16990093312L) return "UID 22";
        else if (currentHWID == 17126617096L) return "UID 16";
        else if (currentHWID == 17109893128L) return "UID 4";
        else if (currentHWID == 16972029960L) return "UID 28";
        else if (currentHWID == 25717571596L) return "UID 26";
        else if (currentHWID == 34291027976L) return "UID 29";
        else return "Missing";

    }

    public static boolean isCapeUser(String name) {
            if (name.equalsIgnoreCase("Ace________")) return true;
            else if (name.equalsIgnoreCase("DiikSuk")) return true;
            else if (name.equalsIgnoreCase("Shanks_Ace")) return true;
            else if (name.equalsIgnoreCase("RatSmasher")) return true;
            else if (name.equalsIgnoreCase("69hr")) return true;
            else if (name.equalsIgnoreCase("Lence")) return true;
            else if (name.equalsIgnoreCase("Kildrio")) return true;
            else if (name.equalsIgnoreCase("Chae")) return true;
            else if (name.equalsIgnoreCase("InputMc")) return true;
            else if (name.equalsIgnoreCase("Nicky")) return true;
            else if (name.equalsIgnoreCase("Takeouts")) return true;
            else if (name.equalsIgnoreCase("Waveshift")) return true;
            else if (name.equalsIgnoreCase("Reckinq")) return true;
            else if (name.equalsIgnoreCase("L1GH7ER")) return true;
            else if (name.equalsIgnoreCase("Fruit54")) return true;
            else if (name.equalsIgnoreCase("Balake_")) return true;
            else if (name.equalsIgnoreCase("XHitsFromTheBong")) return true;
            else return name.equalsIgnoreCase(Minecraft.getMinecraft().player.getName());

    }
}
