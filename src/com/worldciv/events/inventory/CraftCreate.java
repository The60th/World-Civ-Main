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

    private void dummyItemCraft(ItemStack[] itemsInTable, PrepareItemCraftEvent e) {
        //Disable crafting of new diamond tools.
        if (e.getInventory().getResult() == null) return;
        Material mat = e.getInventory().getResult().getType();
        if (mat == null) return;
        if (mat == Material.DIAMOND_SWORD || mat == Material.DIAMOND_HELMET || mat == Material.DIAMOND_BOOTS || mat == Material.DIAMOND_CHESTPLATE
                || mat == Material.DIAMOND_LEGGINGS) {
            e.getInventory().setResult(new ItemStack(Material.AIR));
        } else if (mat == Material.IRON_SWORD || mat == Material.IRON_HELMET || mat == Material.IRON_BOOTS || mat == Material.IRON_CHESTPLATE
                || mat == Material.IRON_LEGGINGS) {
            // Bukkit.broadcastMessage("dem2");
            // Bukkit.broadcastMessage("dem2");
            for (int i = 0; i < itemsInTable.length; i++) {
                //Bukkit.broadcastMessage("dem " + i);
                //Handle replacement of default crafting recipes with iron block style recipes.
                //Bukkit.broadcastMessage("dem3");
                if (itemsInTable[i].getType() == Material.IRON_BLOCK) return;
                //Bukkit.broadcastMessage("dem4");

            }
            // Bukkit.broadcastMessage("dem5");
            e.getInventory().setResult(new ItemStack(Material.AIR));

            //Now do lore checks for refined iron items.
            final String RII = "Refined Iron Ingot";
            final String Steel = "Steel";
            //Bukkit.broadcastMessage("dem6");
            //Check each crafting spot for all patterns.
            //Sword check one:
            //Refined Iron Ingot Sword.
            if (itemsInTable[1].getType() != Material.AIR && itemsInTable[4].getType() != Material.AIR) {
                if (itemsInTable[1].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[4].getItemMeta().getLore().get(0).contains(RII) && itemsInTable[7].getType() == Material.STICK) {
                    e.getInventory().setResult(Gear.tierOneSword);
                    return;
                }
            } else if (itemsInTable[2].getType() != Material.AIR && itemsInTable[5].getType() != Material.AIR) {
                if (itemsInTable[2].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[5].getItemMeta().getLore().get(0).contains(RII) && itemsInTable[8].getType() == Material.STICK) {
                    e.getInventory().setResult(Gear.tierOneSword);
                    return;
                }
            } else if (itemsInTable[3].getType() != Material.AIR && itemsInTable[6].getType() != Material.AIR) {
                if (itemsInTable[3].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[6].getItemMeta().getLore().get(0).contains(RII) && itemsInTable[9].getType() == Material.STICK) {
                    e.getInventory().setResult(Gear.tierOneSword);
                    return;
                }
            }
            //Chestplate
            if (itemsInTable[1].getType() != Material.AIR && itemsInTable[3].getType() != Material.AIR
                    && itemsInTable[4].getType() != Material.AIR
                    && itemsInTable[5].getType() != Material.AIR
                    && itemsInTable[6].getType() != Material.AIR
                    && itemsInTable[7].getType() != Material.AIR
                    && itemsInTable[8].getType() != Material.AIR
                    && itemsInTable[9].getType() != Material.AIR) {
                if (itemsInTable[1].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[3].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[4].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[5].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[6].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[7].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[8].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[9].getItemMeta().getLore().get(0).contains(RII)) {
                    e.getInventory().setResult(Gear.tierOneChest);
                    return;
                }
            }
            //Leggings
            if (itemsInTable[1].getType() != Material.AIR && itemsInTable[2].getType() != Material.AIR
                    && itemsInTable[3].getType() != Material.AIR
                    && itemsInTable[4].getType() != Material.AIR
                    && itemsInTable[6].getType() != Material.AIR
                    && itemsInTable[7].getType() != Material.AIR
                    && itemsInTable[9].getType() != Material.AIR) {
                if (itemsInTable[1].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[2].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[3].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[4].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[6].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[7].getItemMeta().getLore().get(0).contains(RII)) {
                    e.getInventory().setResult(Gear.tierOneLegs);
                    return;
                }
            }
            //Helm
            if (itemsInTable[1].getType() != Material.AIR && itemsInTable[2].getType() != Material.AIR
                    && itemsInTable[3].getType() != Material.AIR
                    && itemsInTable[4].getType() != Material.AIR
                    && itemsInTable[6].getType() != Material.AIR){
                if (itemsInTable[1].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[2].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[3].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[4].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[6].getItemMeta().getLore().get(0).contains(RII)){
                    e.getInventory().setResult(Gear.tierOneHelm);
                    return;
                }
            }
            //Helm bottom
            if (itemsInTable[4].getType() != Material.AIR && itemsInTable[5].getType() != Material.AIR
                    && itemsInTable[6].getType() != Material.AIR
                    && itemsInTable[7].getType() != Material.AIR
                    && itemsInTable[9].getType() != Material.AIR){
                if (itemsInTable[4].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[5].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[6].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[7].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[9].getItemMeta().getLore().get(0).contains(RII)){
                    e.getInventory().setResult(Gear.tierOneHelm);
                    return;
                }
            }
            //boots top
            if (itemsInTable[1].getType() != Material.AIR && itemsInTable[3].getType() != Material.AIR
                    && itemsInTable[4].getType() != Material.AIR
                    && itemsInTable[6].getType() != Material.AIR){
                if (itemsInTable[1].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[3].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[4].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[6].getItemMeta().getLore().get(0).contains(RII)){
                    e.getInventory().setResult(Gear.tierOneBoots);
                    return;
                }
            }
            //boots bottom
            if (itemsInTable[4].getType() != Material.AIR && itemsInTable[6].getType() != Material.AIR
                    && itemsInTable[7].getType() != Material.AIR
                    && itemsInTable[9].getType() != Material.AIR){
                if (itemsInTable[4].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[6].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[7].getItemMeta().getLore().get(0).contains(RII)
                        && itemsInTable[9].getItemMeta().getLore().get(0).contains(RII)){
                    e.getInventory().setResult(Gear.tierOneBoots);
                    return;
                }
            }


            //Steel Sword.
            if (itemsInTable[1].getType() != Material.AIR && itemsInTable[4].getType() != Material.AIR) {
                if (itemsInTable[1].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[4].getItemMeta().getLore().get(0).contains(Steel) && itemsInTable[7].getType() == Material.STICK) {
                    e.getInventory().setResult(Gear.tierTwoSword);
                    return;
                }
            } else if (itemsInTable[2].getType() != Material.AIR && itemsInTable[5].getType() != Material.AIR) {
                if (itemsInTable[2].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[5].getItemMeta().getLore().get(0).contains(Steel) && itemsInTable[8].getType() == Material.STICK) {
                    e.getInventory().setResult(Gear.tierTwoSword);
                    return;
                }
            } else if (itemsInTable[3].getType() != Material.AIR && itemsInTable[6].getType() != Material.AIR) {
                if (itemsInTable[3].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[6].getItemMeta().getLore().get(0).contains(Steel) && itemsInTable[9].getType() == Material.STICK) {
                    e.getInventory().setResult(Gear.tierTwoSword);
                    return;
                }
            }
            //Steel chest
            if (itemsInTable[1].getType() != Material.AIR && itemsInTable[3].getType() != Material.AIR
                    && itemsInTable[4].getType() != Material.AIR
                    && itemsInTable[5].getType() != Material.AIR
                    && itemsInTable[6].getType() != Material.AIR
                    && itemsInTable[7].getType() != Material.AIR
                    && itemsInTable[8].getType() != Material.AIR
                    && itemsInTable[9].getType() != Material.AIR) {
                if (itemsInTable[1].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[3].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[4].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[5].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[6].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[7].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[8].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[9].getItemMeta().getLore().get(0).contains(Steel)) {
                    e.getInventory().setResult(Gear.tierTwoChest);
                    return;
                }
            }
            //steel legs
            if (itemsInTable[1].getType() != Material.AIR && itemsInTable[2].getType() != Material.AIR
                    && itemsInTable[3].getType() != Material.AIR
                    && itemsInTable[4].getType() != Material.AIR
                    && itemsInTable[6].getType() != Material.AIR
                    && itemsInTable[7].getType() != Material.AIR
                    && itemsInTable[9].getType() != Material.AIR) {
                if (itemsInTable[1].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[2].getItemMeta().getLore().get(0).contains(Steel )
                        && itemsInTable[3].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[4].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[6].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[7].getItemMeta().getLore().get(0).contains(Steel)) {
                    e.getInventory().setResult(Gear.tierTwoLegs);
                    return;
                }
            }
            //Helm Top
            if (itemsInTable[1].getType() != Material.AIR && itemsInTable[2].getType() != Material.AIR
                    && itemsInTable[3].getType() != Material.AIR
                    && itemsInTable[4].getType() != Material.AIR
                    && itemsInTable[6].getType() != Material.AIR){
                if (itemsInTable[1].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[2].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[3].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[4].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[6].getItemMeta().getLore().get(0).contains(Steel)){
                    e.getInventory().setResult(Gear.tierTwoHelm);
                    return;
                }
            }
            //Helm bottom
            if (itemsInTable[4].getType() != Material.AIR && itemsInTable[5].getType() != Material.AIR
                    && itemsInTable[6].getType() != Material.AIR
                    && itemsInTable[7].getType() != Material.AIR
                    && itemsInTable[9].getType() != Material.AIR){
                if (itemsInTable[4].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[5].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[6].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[7].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[9].getItemMeta().getLore().get(0).contains(Steel)){
                    e.getInventory().setResult(Gear.tierTwoHelm);
                    return;
                }
            }


            //boots top
            if (itemsInTable[1].getType() != Material.AIR && itemsInTable[3].getType() != Material.AIR
                    && itemsInTable[4].getType() != Material.AIR
                    && itemsInTable[6].getType() != Material.AIR){
                if (itemsInTable[1].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[3].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[4].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[6].getItemMeta().getLore().get(0).contains(Steel)){
                    e.getInventory().setResult(Gear.tierTwoBoots);
                    return;
                }
            }
            //boots bottom
            if (itemsInTable[4].getType() != Material.AIR && itemsInTable[6].getType() != Material.AIR
                    && itemsInTable[7].getType() != Material.AIR
                    && itemsInTable[9].getType() != Material.AIR){
                if (itemsInTable[4].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[6].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[7].getItemMeta().getLore().get(0).contains(Steel)
                        && itemsInTable[9].getItemMeta().getLore().get(0).contains(Steel)){
                    e.getInventory().setResult(Gear.tierTwoBoots);
                    return;
                }
            }

            /*if(itemsInTable[2].getItemMeta().getLore().get(0).contains("Refined Iron Ingot")
                    && itemsInTable[5].getItemMeta().getLore().get(0).contains("Refined Iron Ingot") || (
            itemsInTable[1].getItemMeta().getLore().get(0).contains("Refined Iron Ingot")
                    && itemsInTable[4].getItemMeta().getLore().get(0).contains("Refined Iron Ingot")) || (
                    itemsInTable[3].getItemMeta().getLore().get(0).contains("Refined Iron Ingot")
                            && itemsInTable[6].getItemMeta().getLore().get(0).contains("Refined Iron Ingot"))){
                    e.getInventory().setResult(Gear.tierTwoSword);
                    return;
                }
            //else if()
        }*/
        }
    }

}
