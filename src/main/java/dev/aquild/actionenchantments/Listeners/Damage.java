package dev.aquild.actionenchantments.Listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Damage implements Listener {
    private final JavaPlugin plugin;
    private int enchantCount;
    private final HashMap<UUID, Integer> players;

    public Damage(JavaPlugin plugin, int enchantCount) {
        this.plugin = plugin;
        this.enchantCount = enchantCount;
        this.players = new HashMap<UUID, Integer>();
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();

        if (Player.class.isInstance(entity)) {
            Player player = (Player) entity;
            this.players.put(player.getUniqueId(), this.players.getOrDefault(player.getUniqueId(), 0) + 1);
            if (this.players.get(player.getUniqueId()) == this.enchantCount) {
                this.players.put(player.getUniqueId(), 0);
                for (ItemStack item : player.getInventory().getArmorContents()) {
                    if (item == null) break;
                    if (EnchantmentTarget.ARMOR.includes(item)) {
                        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) + 1);
                    }
                }
            }
        }
    }
}
