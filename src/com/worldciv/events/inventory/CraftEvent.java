package com.worldciv.events.inventory;

import com.worldciv.filesystem.*;
import com.worldciv.the60th.Main;
import com.worldciv.utility.ItemType;
import com.worldciv.utility.Tier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class CraftEvent implements Listener {
    //TODO
    //Current bugs
    //Shift clicking items creates non custom items.
    //This should be done some other way? Config to register event and check em all?
    @EventHandler
    public void onItemCrafted(CraftItemEvent event){
        Player clicked = (Player)event.getWhoClicked();
        if(clicked == null) return;
        String result = event.getRecipe().getResult().toString();
        if(result.equals(Gear.customTierOneSword.getResult().toString()) ||
                result.equals(Gear.customTierOneSword2.getResult().toString()) ||
                result.equals(Gear.customTierOneSword3.getResult().toString())){
            event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_SWORD,1)), Tier.I, ItemType.SWORD)));
        }
        else if(result.equals(Gear.customTierOneHelm.getResult().toString()) || result.equals(Gear.customTierOneHelm2.getResult().toString())){
            event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_HELMET,1)), Tier.I, ItemType.HELM)));
        }
        else if(result.equals(Gear.customTierOneChest.getResult().toString())){
            event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_CHESTPLATE,1)), Tier.I, ItemType.CHESTPLATE)));
        }
        else if(result.equals(Gear.customTierOneLeg.getResult().toString())){
            event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_LEGGINGS,1)), Tier.I, ItemType.LEGGINGS)));
        }
        else if(result.equals(Gear.customTierOneBoots.getResult().toString()) || result.equals(Gear.customTierOneBoots2.getResult().toString())){
            event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_BOOTS,1)), Tier.I, ItemType.BOOTS)));
        }
        else if(result.equals(Gear.customTierOneShield.getResult().toString())){
            event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem(new ItemStack(Material.SHIELD,1),Tier.I, ItemType.SHIELD)));
        }
        else if(result.equals(Gear.customTierOneBow.getResult().toString())){
            event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem(new ItemStack(Material.BOW,1),Tier.I, ItemType.BOW)));
        }
        else if(result.equals(Gear.customTierOneArrow.getResult().toString())){
            event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem(new ItemStack(Material.ARROW,1),Tier.I, ItemType.ARROW)));
        }else if(result.equals(Gear.customTierOnePike.getResult().toString())){
            ItemStack itemStack = CustomItem.getItemFromCustomItem
                            (Main.fileSystem.createItem(new ItemStack(Material.IRON_SPADE,1),Tier.I, ItemType.PIKE));
            ItemMeta meta = itemStack.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.DURABILITY,1,true);
            itemStack.setItemMeta(meta);
            event.setCurrentItem(itemStack);
        }
        else if(result.equals(Gear.customTierOneLance.getResult().toString())){
            ItemStack itemStack = CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem(new ItemStack(Material.IRON_SPADE,1),Tier.I, ItemType.LANCE));
            ItemMeta meta = itemStack.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.DURABILITY,1,true);
            itemStack.setItemMeta(meta);
            event.setCurrentItem(itemStack);
        }


        if(event.getCurrentItem().toString().equals(Gear.tierTwoSword.toString())){
            event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_SWORD,1)), Tier.II, ItemType.SWORD)));
        }


    }




}
