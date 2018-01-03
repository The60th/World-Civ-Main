package com.worldciv.events.player;

import com.worldciv.utility.CraftingItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class SteelAnvilEvent implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {

        Block b = e.getBlockPlaced();

        if (b.getType().equals(Material.ANVIL)) {
            Player p = e.getPlayer();
            ItemStack is = e.getItemInHand();
            ItemMeta im = is.getItemMeta();
            AnvilInventory inv = (AnvilInventory) b;
            if (im == null || im.getLore() == null || im.getLore().get(0).isEmpty()) {
                return;
            }

            if (im.getLore().get(0).equals(CraftingItemStack.getSteelAnvil().getItemMeta().getLore().get(0))) {

                //add to database
            }

        }
    }

    @EventHandler
    public void onSteelAnvilRemoval(SteelAnvilBreakEvent e) {

        Block b = e.getBlock();
    }


    /**
     * On removal of block
     */

    @EventHandler
    public void onPhysics(BlockPhysicsEvent e){
        Block b = e.getBlock();
        if(b.getType().equals(Material.ANVIL)){
            SteelAnvilBreakEvent event = new SteelAnvilBreakEvent(b);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block b = e.getBlock();
        Player p = e.getPlayer();

        if (b.getType().equals(Material.ANVIL)) {
            SteelAnvilBreakEvent event = new SteelAnvilBreakEvent(p, b);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    @EventHandler
    public void onExplosion(BlockExplodeEvent e) {
        List<Block> list = e.blockList();

        for (Block b : list) {
            if (b.getType().equals(Material.ANVIL)) {
                SteelAnvilBreakEvent event = new SteelAnvilBreakEvent(e.getBlock());
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
        }
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent e) {
        List<Block> list = e.getBlocks();
        for (Block b : list) {
            if (b.getType().equals(Material.ANVIL)) {
                SteelAnvilBreakEvent event = new SteelAnvilBreakEvent(e.getBlock());
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
        }
    }

    @EventHandler
    public void onPistonPush(BlockPistonExtendEvent e) {
        List<Block> list = e.getBlocks();

        for (Block b : list) {
            if (b.getType().equals(Material.ANVIL)) {
                SteelAnvilBreakEvent event = new SteelAnvilBreakEvent(e.getBlock());
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
        }
    }
}
