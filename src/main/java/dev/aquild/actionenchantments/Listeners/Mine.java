package dev.aquild.actionenchantments.Listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class Mine implements Listener {
    private final JavaPlugin plugin;
    private final int enchantCount;
    private final HashMap<UUID, Integer> players;

    public Mine(JavaPlugin plugin, int enchantCount) {
        this.plugin = plugin;
        this.enchantCount = enchantCount;
        this.players = new HashMap<UUID, Integer>();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (EnchantmentTarget.TOOL.includes(item)) {
            this.players.put(player.getUniqueId(), this.players.getOrDefault(player.getUniqueId(), 0) + 1);

            if (this.players.get(player.getUniqueId()) == this.enchantCount) {
                this.players.put(player.getUniqueId(), 0);
                item.addUnsafeEnchantment(Enchantment.DIG_SPEED, item.getEnchantmentLevel(Enchantment.DIG_SPEED) + 1);
                if (new Random().nextInt(5) == 0) {
                    item.addUnsafeEnchantment(Enchantment.DURABILITY, item.getEnchantmentLevel(Enchantment.DURABILITY) + 1);
                }
            }
        }
    }
}
