package org.funty.startelytra.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.funty.startelytra.Main;
import org.funty.startelytra.util.ConfigManager;

public class ElytraCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.getNotEnoughArgsMessage()));
        } else {
            String arg = args[0];
            if (arg.equals("reload")) {
                if (sender.hasPermission("Elytra.reload")) {
                    Main.getPlugin().reloadConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.getReloadedMessage()));
                }else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.getNoPermissionMessage()));
                }
            }

        }



        return false;
    }
}
