package com.worldciv.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftingRecipes {

    public static ShapedRecipe customEgg;

    public static void registerRecipes(){

        registerEgg();
    }

    private static void registerEgg(){
        ItemStack item = new ItemStack(Material.WOOD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Wooden Egg");
        item.setItemMeta(meta);
        customEgg = new ShapedRecipe(item);
        customEgg.shape(
                " @ ",
                "   ",
                "   ");
        customEgg.setIngredient('@',Material.EGG);

        Bukkit.getServer().addRecipe(customEgg);

    }
}
