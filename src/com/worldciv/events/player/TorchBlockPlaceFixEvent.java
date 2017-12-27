package com.worldciv.events.player;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import static com.worldciv.utility.utilityStrings.worldciv;

public class TorchBlockPlaceFixEvent implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        Block blockplaced = e.getBlock();

        if (blockplaced.getType() == Material.TORCH) { //torch is being placed
            if (p.getInventory().getItemInMainHand().getType().isBlock()){ //item in main hand is a block
                e.setCancelled(true);
                return;
            }
        }
    }

}
