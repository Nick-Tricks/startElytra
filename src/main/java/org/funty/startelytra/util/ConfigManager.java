package org.funty.startelytra.util;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.funty.startelytra.Main;

import java.util.List;

public class ConfigManager {

    private static final Main main = Main.getPlugin();

    public static Location getCenterLocation(World world) {
        Location location = world.getSpawnLocation();
        FileConfiguration configuration = org.funty.startelytra.Main.getPlugin().getConfig();
        location.setX(SafeParser.safeParseDouble(configuration.getString("Center.X"), location.getX(), e -> main.getLogger().severe("Center.X in config for startElytra not a number!")));
        location.setY(SafeParser.safeParseDouble(org.funty.startelytra.Main.getPlugin().getConfig().getString("Center.Y"), location.getY(), e -> main.getLogger().severe("Center.Y in config for startElytra not a number!")));
        location.setZ(SafeParser.safeParseDouble(Main.getPlugin().getConfig().getString("Center.Z"), location.getZ(), e -> main.getLogger().severe("Center.Z in config for startElytra not a number!")));
        return location;
    }

    public static String getElytraDisplayName() {
        String disp = Main.getPlugin().getConfig().getString("Geysermc.Elytra.DisplayName");
        if (disp == null) {
            main.getLogger().severe("Elytra DisplayName for startElytra is not set in the config! Setting default value");
            main.getConfig().set("Geysermc.Elytra.DisplayName", "§4Bedrock One-way-Elytra");
            main.saveConfig();
            return "§4Bedrock One-way-Elytra";
        } else {
            return disp;
        }
    }

    @SuppressWarnings("unchecked")
    public static List<String> getElytraLore() {
        Object lore = Main.getPlugin().getConfig().get("Geysermc.Elytra.Lore");
        if (lore instanceof List) {
            return (List<String>) lore;
        }
        if (lore instanceof String) {
            return Lists.newArrayList((String) lore);
        }
        main.getLogger().severe("Elytra Lore for startElytra is not a list or string! Setting default value");
        main.getConfig().set("Geysermc.Elytra.Lore", Lists.newArrayList("§4Removing this item from", "§4the chest-slot will be", "§4punished with a permanent ban"));
        main.saveConfig();
        return Lists.newArrayList("§4Removing this item from", "§4the chest-slot will be", "§4punished with a permanent ban");
    }

    public static String getChestOccupiedMessage() {
        String message = Main.getPlugin().getConfig().getString("Geysermc.Messages.ChestOccupied");
        if (message == null) {
            main.getLogger().severe("Geysermc.Messages.ChestOccupied for startElytra is not set in the config! Setting default value");
            main.getConfig().set("Geysermc.Messages.ChestOccupied", "§4You can't wear a chestplate while using the Bedrock One-way-Elytra");
            main.saveConfig();
            return "§cYour \"chestplate-slot\" must be empty for you to use the starting Elytra.";
        } else {
            return message;
        }
    }

    public static String getNoPermissionMessage() {
        String message = Main.getPlugin().getConfig().getString("Messages.NoPermissions");
        if (message == null) {
            main.getLogger().severe("Messages.NoPermissions for startElytra is not set in the config! Setting default value");
            main.getConfig().set("Messages.NoPermissions", "&4You don´t have Permissions!");
            main.saveConfig();
            return "&4You don´t have Permissions!";
        } else {
            return message;
        }
    }

    public static String getNotEnoughArgsMessage() {
        String message = Main.getPlugin().getConfig().getString("Messages.NotEnoughArgs");
        if (message == null) {
            main.getLogger().severe("Messages.NotEnoughArgs for startElytra is not set in the config! Setting default value");
            main.getConfig().set("Messages.NotEnoughArgs", "&4Not enough arguments!");
            main.saveConfig();
            return "&4Not enough arguments!";
        } else {
            return message;
        }
    }

    public static String getReloadedMessage() {
        String message = Main.getPlugin().getConfig().getString("Messages.Reloaded");
        if (message == null) {
            main.getLogger().severe("Messages.Reloaded for startElytra is not set in the config! Setting default value");
            main.getConfig().set("Messages.Reloaded", "&aThe config.yml was reloaded.");
            main.saveConfig();
            return "&aThe config.yml was reloaded.";
        } else {
            return message;
        }
    }

    public static boolean isNewBoost() {
        return Main.getPlugin().getConfig().getBoolean("NewBoost");
    }

    public static String getGeyserMCPrefix() {
        String prefix = Main.getPlugin().getConfig().getString("Geysermc.Prefix");
        if (prefix == null) {
            main.getLogger().severe("Geysermc.Prefix for startElytra is not set in the config! Setting default value");
            main.getConfig().set("Geysermc.Prefix", "*");
            main.saveConfig();
            return "*";
        } else {
            return prefix;
        }
    }

    public static int getRadius() {
        String radius = Main.getPlugin().getConfig().getString("Radius");
        try {
            return Integer.parseInt(radius);
        } catch (NumberFormatException e) {
            main.getLogger().severe("Radius for startElytra is not a number! Edit config.yml to have a number for Radius! Using default value...");
            return 7;
        } catch (NullPointerException e) {
            main.getLogger().severe("Radius for startElytra is not set in the config! Setting default value");
            main.getConfig().set("Radius", 7);
            main.saveConfig();
            return 7;
        }
    }

    public static double getBoost() {
        String boost = Main.getPlugin().getConfig().getString("Boost");
        try {
            return Double.parseDouble(boost);
        } catch (NumberFormatException e) {
            main.getLogger().severe("Boost for startElytra is not a number! Edit config.yml to have a number for Boost! Using default value...");
            return 1.0;
        } catch (NullPointerException e) {
            main.getLogger().severe("Boost for startElytra is not set in the config! Setting default value");
            main.getConfig().set("Boost", 1.5);
            main.saveConfig();
            return 1.0;
        }
    }

}
