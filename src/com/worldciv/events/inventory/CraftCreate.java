package com.worldciv.events.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class CraftCreate implements Listener {

    @EventHandler
    public void onCraftingPrepare(PrepareItemCraftEvent e) {

        CraftingInventory craftingInv = e.getInventory();

        ItemStack[] itemsInTable = craftingInv.getContents();

        //ItemCrafting functions go here!
        dummyItemCraft(itemsInTable, e);

    }

    private void dummyItemCraft(ItemStack[] itemsInTable, PrepareItemCraftEvent e){
        if (itemsInTable[2].getType() == Material.EGG) {
            //Item type detected! To reduce less iteration and check if item is correct.

            if (itemsInTable[2].getItemMeta().getLore() == null) { //Item lore not found, this is not the item we are looking for. Change recipe to AIR
                e.getInventory().setResult(new ItemStack(Material.AIR));
                return;
            }

            if (itemsInTable[2].getItemMeta().getLore().get(0) == "imisspetra") { //Lore found for item!
                //Found the recipe. No needed code here! :D The CraftingRecipes class handles this!
            }
        }
    }


}
