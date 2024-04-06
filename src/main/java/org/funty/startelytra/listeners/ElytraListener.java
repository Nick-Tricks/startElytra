package org.funty.startelytra.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import org.funty.startelytra.util.ColorUtil;
import org.funty.startelytra.util.ConfigManager;
import org.funty.startelytra.util.GeyserUtil;

import java.util.ArrayList;
import java.util.UUID;

public class ElytraListener implements Listener {

    ItemStack elytra = new ItemStack(Material.ELYTRA);
    ItemMeta elytraMeta = this.elytra.getItemMeta();

    {
        this.elytraMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', ConfigManager.getElytraDisplayName()));
        this.elytraMeta.setLore(ColorUtil.color(ConfigManager.getElytraLore()));
        this.elytraMeta.setUnbreakable(true);
        this.elytraMeta.addEnchant(Enchantment.DURABILITY, 1000, true);
        this.elytraMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        this.elytraMeta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
        this.elytraMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.elytra.setItemMeta(this.elytraMeta);
    }

    private static final ArrayList<UUID> glider = new ArrayList<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Location location = ConfigManager.getCenterLocation(player.getWorld());
        if (event.getPlayer().getGameMode() != GameMode.SURVIVAL) return;

        if (!player.getLocation().add(0, -1, 0).getBlock().getType().isAir() &&
                !(player.getLocation().getY() >= player.getWorld().getMaxHeight())) {
            glider.remove(uuid);
            if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) player.setAllowFlight(false);
            if (!GeyserUtil.isBedrockPlayer(player)) return;
            if (player.getInventory().getChestplate() == null) return;
            if (!player.getInventory().getChestplate().equals(this.elytra)) return;
            player.getInventory().setChestplate(null);
            return;
        }

        int radiusInBlocks = ConfigManager.getRadius();
        int radiusSquared = radiusInBlocks * radiusInBlocks;
        if (glider.contains(player.getUniqueId()) ||
                location.distanceSquared(event.getPlayer().getLocation()) > radiusSquared ||
                !player.getLocation().add(0, -1, 0).getBlock().getType().isAir()) return;

        glider.add(uuid);
        setBedrockElytra(player);
        player.setGliding(true);
        player.setAllowFlight(true);
    }

    public void setBedrockElytra(Player player) {
        if (!GeyserUtil.isBedrockPlayer(player)) return;
        if (player.getInventory().getChestplate() == null) {
            player.getInventory().setChestplate(this.elytra);
            return;
        }
        player.sendMessage(ConfigManager.getChestOccupiedMessage());
    }

    @EventHandler
    public void onSwapHandItems(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!glider.contains(uuid)) return;

        event.setCancelled(true);
        Vector velocity;
        if (ConfigManager.isNewBoost()) velocity = player.getLocation().getDirection().multiply(ConfigManager.getBoost());
        else velocity = player.getLocation().getDirection().multiply(2).add(new Vector(0, ConfigManager.getBoost(), 0));

        player.setVelocity(velocity);
    }

    @EventHandler
    public void onSneakItems(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!glider.contains(uuid)) return;
        if (!GeyserUtil.isBedrockPlayer(player)) return;
        event.setCancelled(true);
        Vector velocity = player.getLocation().getDirection().multiply(2).add(new Vector(0, ConfigManager.getBoost(), 0));
        player.setVelocity(velocity);
    }

    @EventHandler
    public void onFlightToggle(PlayerToggleFlightEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (event.getPlayer().getGameMode() == GameMode.SURVIVAL && glider.contains(uuid)) event.setCancelled(true);
    }

    @EventHandler
    public void onGlideToggle(EntityToggleGlideEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) return;
        Player player = (Player) event.getEntity();
        UUID uuid = player.getUniqueId();
        if (glider.contains(uuid)) event.setCancelled(true);
    }
}