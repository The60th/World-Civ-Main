package com.worldciv.events.inventory;

import com.worldciv.filesystem.Gear;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class CraftCreate implements Listener {

    @EventHandler
    public void onCraftingPrepare(PrepareItemCraftEvent e) {

        CraftingInventory craftingInv = e.getInventory(); //is NOT the crafting inv, this is the players full inv.
        ItemStack[] itemsInTable = craftingInv.getContents();
        ItemStack[] itemsInTableNew = itemsInTable;/*{e.getInventory().getItem(1),e.getInventory().getItem(2),e.getInventory().getItem(3)
                ,e.getInventory().getItem(4),e.getInventory().getItem(5),e.getInventory().getItem(6),e.getInventory().getItem(7)
        ,e.getInventory().getItem(8),e.getInventory().getItem(9)};*/

        //ItemCrafting functions go here!
        dummyItemCraft(itemsInTable, e);

    }

    private void dummyItemCraft(ItemStack[] itemsInTable, PrepareItemCraftEvent e){
        //Disable crafting of new diamond tools.
        Material mat = e.getInventory().getResult().getType();
        if(mat == Material.DIAMOND_SWORD || mat == Material.DIAMOND_HELMET || mat == Material.DIAMOND_BOOTS || mat == Material.DIAMOND_CHESTPLATE
                || mat == Material.DIAMOND_LEGGINGS){
            e.getInventory().setResult(new ItemStack(Material.AIR));
        }else if(mat == Material.IRON_SWORD || mat == Material.IRON_HELMET || mat == Material.IRON_BOOTS || mat == Material.IRON_CHESTPLATE
                || mat == Material.IRON_LEGGINGS){
                Bukkit.broadcastMessage("dem2");
                Bukkit.broadcastMessage("dem2");
                for(int i = 0; i < itemsInTable.length; i++){
                    Bukkit.broadcastMessage("dem " + i);
                    //Handle replacement of default crafting recipes with iron block style recipes.
                    Bukkit.broadcastMessage("dem3");
                    if(itemsInTable[i].getType() == Material.IRON_BLOCK) return;
                    Bukkit.broadcastMessage("dem4");

                }
            Bukkit.broadcastMessage("dem5");

                //Now do lore checks for refined iron items.
                final String value = "Refined Iron Ingot";
            Bukkit.broadcastMessage("dem6");
            //Check each crafting spot for all patterns.
            if(itemsInTable[2].getItemMeta().getDisplayName().contains("Refined Iron Ingot")
                    && itemsInTable[5].getItemMeta().getDisplayName().contains("Refined Iron Ingot")) {
                Bukkit.broadcastMessage("dem");
                    //e.getInventory().setResult(Gear.tierTwoSword);
                    //return;
                }
            Bukkit.broadcastMessage("dem101");

                e.getInventory().setResult(new ItemStack(Material.AIR));
            Bukkit.broadcastMessage("dem100");

        }
    }


}
