package com.worldciv.filesystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class Gear {
    //This should be done better? Feeder function to register recipes rather then by hand?
    //Swords need 3
    //Helms 2
    //Boots 2
    //Legs 1
    //Chests 1
    //Axes 2
    public static ShapedRecipe customTierOneSword;
    public static ShapedRecipe customTierOneSword2;
    public static ShapedRecipe customTierOneSword3;


    public static ShapedRecipe customTierOneHelm;
    public static ShapedRecipe customTierOneHelm2;

    public static ShapedRecipe customTierOneChest;

    public static ShapedRecipe customTierOneLeg;

    public static ShapedRecipe customTierOneBoots;
    public static ShapedRecipe customTierOneBoots2;

    public static ShapedRecipe customTierOneShield;

    public static ShapedRecipe customTierOneBow;

    public static ShapedRecipe customTierOneArrow;

    public static ShapedRecipe customTierOnePike;

    public  static ShapedRecipe customTierOneLance;

    public static void registerRecipes(){
        customTierOneSword();
        customTierOneHelm();
        customTierOneChest();
        customTierOneLegs();
        customTierOneBoots();
        CustomTierOneShield();
        CustomTierOneBow();
        CustomTierOneArrow();
        CustomTierOnePike();
        CustomTierOneLance();
    }
    private static void customTierOneHelm(){
        //ItemStack item = CustomItem.getItemFromCustomItem(MainCombat.fileSystem.createItem((new ItemStack(Material.WOOD_SWORD,1)),Tier.five,ItemType.SWORD ));
        ItemStack item = new ItemStack(Material.IRON_HELMET, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier One Helm");
        item.setItemMeta(meta);
        customTierOneHelm = new ShapedRecipe(item);
        customTierOneHelm.shape(
                "@@@",
                "@ @",
                "   ");
        customTierOneHelm.setIngredient('@',Material.GLASS);
        Bukkit.getServer().addRecipe(customTierOneHelm);

        customTierOneHelm2 = new ShapedRecipe(item);
        customTierOneHelm2.shape(
                "   ",
                "@@@",
                "@ @");
        customTierOneHelm2.setIngredient('@',Material.GLASS);
        Bukkit.getServer().addRecipe(customTierOneHelm2);
    }
    private static void customTierOneChest(){
        //ItemStack item = CustomItem.getItemFromCustomItem(MainCombat.fileSystem.createItem((new ItemStack(Material.WOOD_SWORD,1)),Tier.five,ItemType.SWORD ));
        ItemStack item = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier One Chest plate");
        item.setItemMeta(meta);
        customTierOneChest = new ShapedRecipe(item);
        customTierOneChest.shape(
                "@ @",
                "@@@",
                "@@@");
        customTierOneChest.setIngredient('@',Material.GLASS);

        Bukkit.getServer().addRecipe(customTierOneChest);
    }
    private static void customTierOneLegs(){
        //ItemStack item = CustomItem.getItemFromCustomItem(MainCombat.fileSystem.createItem((new ItemStack(Material.WOOD_SWORD,1)),Tier.five,ItemType.SWORD ));
        ItemStack item = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier One Leggings");
        item.setItemMeta(meta);
        customTierOneLeg = new ShapedRecipe(item);
        customTierOneLeg.shape(
                "@@@",
                "@ @" ,
                "@ @");
        customTierOneLeg.setIngredient('@',Material.GLASS);

        Bukkit.getServer().addRecipe(customTierOneLeg);
    }
    private static void customTierOneBoots(){
        //ItemStack item = CustomItem.getItemFromCustomItem(MainCombat.fileSystem.createItem((new ItemStack(Material.WOOD_SWORD,1)),Tier.five,ItemType.SWORD ));
        ItemStack item = new ItemStack(Material.IRON_BOOTS, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier One Boots");
        item.setItemMeta(meta);

        customTierOneBoots = new ShapedRecipe(item);
        customTierOneBoots.shape(
                "   ",
                "@ @",
                "@ @");
        customTierOneBoots.setIngredient('@',Material.GLASS);
        Bukkit.getServer().addRecipe(customTierOneBoots);

        customTierOneBoots2 = new ShapedRecipe(item);
        customTierOneBoots2.shape(
                "@ @",
                "@ @",
                "   "
        );
        customTierOneBoots2.setIngredient('@',Material.GLASS);
        Bukkit.getServer().addRecipe(customTierOneBoots2);
    }
    private static void customTierOneSword(){
        //ItemStack item = CustomItem.getItemFromCustomItem(MainCombat.fileSystem.createItem((new ItemStack(Material.WOOD_SWORD,1)),Tier.five,ItemType.SWORD ));
        ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier One Sword");
        item.setItemMeta(meta);

        customTierOneSword = new ShapedRecipe(item);
        customTierOneSword.shape(
                "@  ",
                "@  ",
                "#  ");
        customTierOneSword.setIngredient('@',Material.GLASS);
        customTierOneSword.setIngredient('#',Material.STICK);
        Bukkit.getServer().addRecipe(customTierOneSword);

        customTierOneSword2 = new ShapedRecipe(item);
        customTierOneSword2.shape(
                " @",
                " @",
                " #");
        customTierOneSword2.setIngredient('@',Material.GLASS);
        customTierOneSword2.setIngredient('#',Material.STICK);
        Bukkit.getServer().addRecipe(customTierOneSword2);

        customTierOneSword3 = new ShapedRecipe(item);
        customTierOneSword3.shape(
                " @ ",
                " @ ",
                " # ");
        customTierOneSword3.setIngredient('@',Material.GLASS);
        customTierOneSword3.setIngredient('#',Material.STICK);
        Bukkit.getServer().addRecipe(customTierOneSword3);
    }

    private static void CustomTierOneShield(){

        //ItemStack item = CustomItem.getItemFromCustomItem(MainCombat.fileSystem.createItem((new ItemStack(Material.WOOD_SWORD,1)),Tier.five,ItemType.SWORD ));
        ItemStack item = new ItemStack(Material.SHIELD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier One Shield");
        item.setItemMeta(meta);

        customTierOneShield = new ShapedRecipe(item);
        customTierOneShield.shape(
                "@ @",
                "@#@",
                " @ "
        );
        customTierOneShield.setIngredient('@',Material.GLASS);
        customTierOneShield.setIngredient('#',Material.IRON_FENCE);

        Bukkit.getServer().addRecipe(customTierOneShield);
    }
    private static void CustomTierOneArrow(){

        //ItemStack item = CustomItem.getItemFromCustomItem(MainCombat.fileSystem.createItem((new ItemStack(Material.WOOD_SWORD,1)),Tier.five,ItemType.SWORD ));
        ItemStack item = new ItemStack(Material.ARROW, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier One Arrow");
        item.setItemMeta(meta);

        customTierOneArrow = new ShapedRecipe(item);
        customTierOneArrow.shape(
                "  @",
                "  #",
                "   "
        );
        customTierOneArrow.setIngredient('@',Material.GLASS);
        customTierOneArrow.setIngredient('#',Material.STICK);

        Bukkit.getServer().addRecipe(customTierOneArrow);
    }
    private static void CustomTierOneBow(){

        //ItemStack item = CustomItem.getItemFromCustomItem(MainCombat.fileSystem.createItem((new ItemStack(Material.WOOD_SWORD,1)),Tier.five,ItemType.SWORD ));
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier One Bow");
        item.setItemMeta(meta);

        customTierOneBow = new ShapedRecipe(item);
        customTierOneBow.shape(
                " @#",
                "@ #",
                " @#"
        );
        customTierOneBow.setIngredient('@',Material.GLASS);
        customTierOneBow.setIngredient('#',Material.STRING);

        Bukkit.getServer().addRecipe(customTierOneBow);
    }
    private static void  CustomTierOnePike(){
        ItemStack item = new ItemStack(Material.IRON_SPADE,1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier One Pike");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Deals bonus damage to mounted foes."));
        item.setItemMeta(meta);

        customTierOnePike = new ShapedRecipe(item);
        customTierOnePike.shape(
                "  @",
                " @ ",
                "#  "
        );
        customTierOnePike.shape(
                "@  ",
                " @ ",
                "  #"
        );
        customTierOnePike.setIngredient('@',Material.GLASS);
        customTierOnePike.setIngredient('#',Material.STICK);
        Bukkit.getServer().addRecipe(customTierOnePike);
    }
    private static void  CustomTierOneLance(){
        ItemStack item = new ItemStack(Material.IRON_SPADE,1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier One Lance");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Deals bonus damage when used from horseback."));
        item.setItemMeta(meta);

        customTierOneLance = new ShapedRecipe(item);
        customTierOneLance.shape(
                "  @",
                " # ",
                "#  "
        );
        customTierOneLance.shape(
                "@  ",
                " # ",
                "  #"
        );
        customTierOneLance.setIngredient('@',Material.GLASS);
        customTierOneLance.setIngredient('#',Material.STICK);
        Bukkit.getServer().addRecipe(customTierOneLance);
    }

}


