package com.worldciv.utility;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

public class FurnaceRecipes {

    public static void registerFurnaceRecipes(){
        dummySmelt();
    }

    private static void dummySmelt(){

        ItemStack resultitem = new ItemStack(Material.GRILLED_PORK, 1);
        Material sourceitem =  Material.EGG;

        FurnaceRecipe recipe = new FurnaceRecipe(resultitem, sourceitem);

        Bukkit.getServer().addRecipe(recipe);

    }
}
