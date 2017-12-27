package com.worldciv.filesystem;

import com.sun.corba.se.spi.ior.IORTemplate;
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

    public static ItemStack tierTwoHelm;
    public static ItemStack tierTwoChest;
    public static ItemStack tierTwoLegs;
    public static ItemStack tierTwoBoots;
    public static ItemStack tierTwoSword;
    public static ItemStack tierTwoBow;
    public static ItemStack tierTwoShield;
    public static ItemStack tierTwoPike;
    public static ItemStack tierTwoLance;
    public static ItemStack tierTwoArrow;


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

        registerTierTwo();
    }
    private static void customTierOneHelm(){
        //ItemStack item = CustomItem.getItemFromCustomItem(MainCombat.fileSystem.createItem((new ItemStack(Material.WOOD_SWORD,1)),Tier.five,ItemType.SWORD ));
        ItemStack item = new ItemStack(Material.IRON_HELMET, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier One Helm");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Tier one gear."));
        item.setItemMeta(meta);
        customTierOneHelm = new ShapedRecipe(item);
        customTierOneHelm.shape(
                "@@@",
                "@ @",
                "   ");
        customTierOneHelm.setIngredient('@',Material.IRON_BLOCK);
        Bukkit.getServer().addRecipe(customTierOneHelm);

        customTierOneHelm2 = new ShapedRecipe(item);
        customTierOneHelm2.shape(
                "   ",
                "@@@",
                "@ @");
        customTierOneHelm2.setIngredient('@',Material.IRON_BLOCK);
        Bukkit.getServer().addRecipe(customTierOneHelm2);
    }
    private static void customTierOneChest(){
        //ItemStack item = CustomItem.getItemFromCustomItem(MainCombat.fileSystem.createItem((new ItemStack(Material.WOOD_SWORD,1)),Tier.five,ItemType.SWORD ));
        ItemStack item = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier One Chest plate");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Tier one gear."));
        item.setItemMeta(meta);
        customTierOneChest = new ShapedRecipe(item);
        customTierOneChest.shape(
                "@ @",
                "@@@",
                "@@@");
        customTierOneChest.setIngredient('@',Material.IRON_BLOCK);

        Bukkit.getServer().addRecipe(customTierOneChest);
    }
    private static void customTierOneLegs(){
        //ItemStack item = CustomItem.getItemFromCustomItem(MainCombat.fileSystem.createItem((new ItemStack(Material.WOOD_SWORD,1)),Tier.five,ItemType.SWORD ));
        ItemStack item = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier One Leggings");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Tier one gear."));
        item.setItemMeta(meta);
        customTierOneLeg = new ShapedRecipe(item);
        customTierOneLeg.shape(
                "@@@",
                "@ @" ,
                "@ @");
        customTierOneLeg.setIngredient('@',Material.IRON_BLOCK);

        Bukkit.getServer().addRecipe(customTierOneLeg);
    }
    private static void customTierOneBoots(){
        //ItemStack item = CustomItem.getItemFromCustomItem(MainCombat.fileSystem.createItem((new ItemStack(Material.WOOD_SWORD,1)),Tier.five,ItemType.SWORD ));
        ItemStack item = new ItemStack(Material.IRON_BOOTS, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier One Boots");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Tier one gear."));
        item.setItemMeta(meta);

        customTierOneBoots = new ShapedRecipe(item);
        customTierOneBoots.shape(
                "   ",
                "@ @",
                "@ @");
        customTierOneBoots.setIngredient('@',Material.IRON_BLOCK);
        Bukkit.getServer().addRecipe(customTierOneBoots);

        customTierOneBoots2 = new ShapedRecipe(item);
        customTierOneBoots2.shape(
                "@ @",
                "@ @",
                "   "
        );
        customTierOneBoots2.setIngredient('@',Material.IRON_BLOCK);
        Bukkit.getServer().addRecipe(customTierOneBoots2);
    }
    private static void customTierOneSword(){
        //ItemStack item = CustomItem.getItemFromCustomItem(MainCombat.fileSystem.createItem((new ItemStack(Material.WOOD_SWORD,1)),Tier.five,ItemType.SWORD ));
        ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier One Sword");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Tier one gear."));
        item.setItemMeta(meta);

        customTierOneSword = new ShapedRecipe(item);
        customTierOneSword.shape(
                "@  ",
                "@  ",
                "#  ");
        customTierOneSword.setIngredient('@',Material.IRON_BLOCK);
        customTierOneSword.setIngredient('#',Material.STICK);
        Bukkit.getServer().addRecipe(customTierOneSword);

        customTierOneSword2 = new ShapedRecipe(item);
        customTierOneSword2.shape(
                " @",
                " @",
                " #");
        customTierOneSword2.setIngredient('@',Material.IRON_BLOCK);
        customTierOneSword2.setIngredient('#',Material.STICK);
        Bukkit.getServer().addRecipe(customTierOneSword2);

        customTierOneSword3 = new ShapedRecipe(item);
        customTierOneSword3.shape(
                " @ ",
                " @ ",
                " # ");
        customTierOneSword3.setIngredient('@',Material.IRON_BLOCK);
        customTierOneSword3.setIngredient('#',Material.STICK);
        Bukkit.getServer().addRecipe(customTierOneSword3);
    }

    private static void CustomTierOneShield(){

        //ItemStack item = CustomItem.getItemFromCustomItem(MainCombat.fileSystem.createItem((new ItemStack(Material.WOOD_SWORD,1)),Tier.five,ItemType.SWORD ));
        ItemStack item = new ItemStack(Material.SHIELD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier One Shield");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Tier one gear."));
        item.setItemMeta(meta);

        customTierOneShield = new ShapedRecipe(item);
        customTierOneShield.shape(
                "@ @",
                "@#@",
                " @ "
        );
        customTierOneShield.setIngredient('@',Material.IRON_INGOT);
        customTierOneShield.setIngredient('#',Material.IRON_FENCE);

        Bukkit.getServer().addRecipe(customTierOneShield);
    }
    private static void CustomTierOneArrow(){

        //ItemStack item = CustomItem.getItemFromCustomItem(MainCombat.fileSystem.createItem((new ItemStack(Material.WOOD_SWORD,1)),Tier.five,ItemType.SWORD ));
        ItemStack item = new ItemStack(Material.ARROW, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier One Arrow");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Tier one gear."));
        item.setItemMeta(meta);

        customTierOneArrow = new ShapedRecipe(item);
        customTierOneArrow.shape(
                "  @",
                "  #",
                "   "
        );
        customTierOneArrow.setIngredient('@',Material.IRON_INGOT);
        customTierOneArrow.setIngredient('#',Material.STICK);

        Bukkit.getServer().addRecipe(customTierOneArrow);
    }
    private static void CustomTierOneBow(){

        //ItemStack item = CustomItem.getItemFromCustomItem(MainCombat.fileSystem.createItem((new ItemStack(Material.WOOD_SWORD,1)),Tier.five,ItemType.SWORD ));
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier One Bow");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Tier one gear."));
        item.setItemMeta(meta);

        customTierOneBow = new ShapedRecipe(item);
        customTierOneBow.shape(
                " @#",
                "@ #",
                " @#"
        );
        customTierOneBow.setIngredient('@',Material.IRON_INGOT);
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
        customTierOnePike.setIngredient('@',Material.IRON_BLOCK);
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
        customTierOneLance.setIngredient('@',Material.IRON_BLOCK);
        customTierOneLance.setIngredient('#',Material.STICK);
        Bukkit.getServer().addRecipe(customTierOneLance);
    }




    public static void registerTierTwo(){
        ItemMeta meta;

        tierTwoHelm = new ItemStack(Material.IRON_HELMET, 1);
        meta = tierTwoHelm.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier Two Helm");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Tier Two gear."));
        tierTwoHelm.setItemMeta(meta);

        tierTwoChest = new ItemStack(Material.IRON_CHESTPLATE, 1);
        meta = tierTwoChest.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier Two Chestplate");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Tier Two gear."));
        tierTwoChest.setItemMeta(meta);

        tierTwoLegs = new ItemStack(Material.IRON_LEGGINGS, 1);
        meta = tierTwoLegs.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier Two Leggings");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Tier Two gear."));
        tierTwoLegs.setItemMeta(meta);

        tierTwoBoots = new ItemStack(Material.IRON_HELMET, 1);
        meta = tierTwoBoots.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier Two Boots");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Tier Two gear."));
        tierTwoBoots.setItemMeta(meta);

        tierTwoSword = new ItemStack(Material.IRON_SWORD, 1);
        meta = tierTwoSword.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier Two Sword");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Tier Two gear."));
        tierTwoSword.setItemMeta(meta);

        tierTwoHelm = new ItemStack(Material.IRON_HELMET, 1);
        meta = tierTwoHelm.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier Two Helm");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Tier Two gear."));
        tierTwoHelm.setItemMeta(meta);

        tierTwoHelm = new ItemStack(Material.IRON_HELMET, 1);
        meta = tierTwoHelm.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier Two Helm");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Tier Two gear."));
        tierTwoHelm.setItemMeta(meta);

        tierTwoHelm = new ItemStack(Material.IRON_HELMET, 1);
        meta = tierTwoHelm.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier Two Helm");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Tier Two gear."));
        tierTwoHelm.setItemMeta(meta);

        tierTwoHelm = new ItemStack(Material.IRON_HELMET, 1);
        meta = tierTwoHelm.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier Two Helm");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Tier Two gear."));
        tierTwoHelm.setItemMeta(meta);

        tierTwoHelm = new ItemStack(Material.IRON_HELMET, 1);
        meta = tierTwoHelm.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Tier Two Helm");
        meta.setLore(Arrays.asList(ChatColor.WHITE + "Tier Two gear."));
        tierTwoHelm.setItemMeta(meta);

    }

}


