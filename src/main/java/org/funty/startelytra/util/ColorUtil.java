package org.funty.startelytra.util;

import org.bukkit.ChatColor;

import java.util.List;

public class ColorUtil {
    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> color(List<String> input) {
        input.replaceAll(ColorUtil::color);
        return input;
    }
}
