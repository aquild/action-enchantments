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

public class Attack implements Listener {
    private final JavaPlugin plugin;
    private final int enchantCount;
    private final HashMap<UUID, Integer> players;

    public Attack(JavaPlugin plugin, int enchantCount) {
        this.plugin = plugin;
        this.enchantCount = enchantCount;
        this.players = new HashMap<UUID, Integer>();
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getDamager();
        if (Player.class.isInstance(entity)) {
            Player player = (Player) entity;
            ItemStack item = player.getInventory().getItemInMainHand();
            if (EnchantmentTarget.WEAPON.includes(item)) {
                this.players.put(player.getUniqueId(), this.players.getOrDefault(player.getUniqueId(), 0) + 1);

                if (this.players.get(player.getUniqueId()) == this.enchantCount) {
                    this.players.put(player.getUniqueId(), 0);
                    item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) + 1);
                }
            }
        }
    }
}
