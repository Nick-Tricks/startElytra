package org.funty.startelytra.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.geysermc.geyser.api.GeyserApi;

public class GeyserUtil {
    public static boolean isBedrockPlayer(Player player) {
        boolean geyserEnabled = Bukkit.getPluginManager().getPlugin("Geyser-Spigot") != null;
        return player.getName().startsWith(ConfigManager.getGeyserMCPrefix()) ||
                (geyserEnabled && GeyserApi.api().isBedrockPlayer(player.getUniqueId()));
    }
}
