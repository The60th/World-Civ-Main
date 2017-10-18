package com.worldciv.utility;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class FurnaceRecipes {

    public static void registerFurnaceRecipes(){
        SmeltToSteel();
    }

    private static void SmeltToSteel(){

        ItemStack resultitem = new ItemStack(Material.IRON_INGOT, 1);
        ItemMeta resultitemmeta = resultitem.getItemMeta();
        resultitemmeta.setLore(Arrays.asList(ChatColor.GRAY + "The refined iron ingot is the", ChatColor.GRAY + "core ingredient to create equipment.", ChatColor.GRAY + "It is also the base material to create steel.", "", ChatColor.YELLOW + "Follow the guide on the /website"));
        resultitemmeta.setDisplayName(ChatColor.GRAY + "" +  ChatColor.BOLD + "Steel");
        resultitem.setItemMeta(resultitemmeta);

        Material sourceitem =  Material.IRON_INGOT;

        FurnaceRecipe recipe = new FurnaceRecipe(resultitem, sourceitem);

        Bukkit.getServer().addRecipe(recipe);

    }
}
