package com.worldciv.events.player;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import static com.worldciv.utility.utilityStrings.worldciv;

public class TreeCutterEvent implements Listener{

    @EventHandler
    public void breakingBlock(final BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) {
            return;
        }
        if (e.getBlock().getType() != Material.LOG && e.getBlock().getType() != Material.LOG_2) {
            return;
        }
        if (!this.isAxe(p.getInventory().getItemInMainHand())){

            return;
        }

        if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) {
            return;
        }
        this.breakBlock(e.getBlock(), e.getPlayer());
    }

    public void breakBlock(final Block b, final Player p) {
        b.breakNaturally();
        final Location above = new Location(b.getWorld(), (double)b.getLocation().getBlockX(), (double)(b.getLocation().getBlockY() + 1), (double)b.getLocation().getBlockZ());
        final Block blockAbove = above.getBlock();
        if (blockAbove.getType() == Material.LOG || blockAbove.getType() == Material.LOG_2) {
            this.breakBlock(blockAbove, p);
            p.getItemInHand().setDurability((short)(p.getItemInHand().getDurability() + 1));
            if (p.getItemInHand().getDurability() > p.getItemInHand().getType().getMaxDurability()) {
                p.getInventory().remove(p.getItemInHand());
                p.playSound(p.getLocation(), Sound.BLOCK_WOOD_HIT, 1.0f, 1.0f);
            }
        }
    }

    public boolean isAxe(final ItemStack item) {
        return item.getType().equals((Object)Material.WOOD_AXE) || item.getType().equals((Object)Material.STONE_AXE) || item.getType().equals((Object)Material.IRON_AXE) || item.getType().equals((Object)Material.GOLD_AXE) || item.getType().equals((Object)Material.DIAMOND_AXE);
    }
}