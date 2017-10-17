package com.worldciv.events.inventory;

import com.worldciv.filesystem.*;
import com.worldciv.the60th.Main;
import com.worldciv.utility.ArmorType;
import com.worldciv.utility.Tier;
import com.worldciv.utility.WeaponType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

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
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_SWORD,1)), Tier.I, WeaponType.SWORD)));
        }
        else if(result.equals(Gear.customTierOneHelm.getResult().toString()) || result.equals(Gear.customTierOneHelm2.getResult().toString())){
            event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_HELMET,1)), Tier.I, ArmorType.HELM)));
        }
        else if(result.equals(Gear.customTierOneChest.getResult().toString())){
            event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_CHESTPLATE,1)), Tier.I, ArmorType.CHESTPLATE)));
        }
        else if(result.equals(Gear.customTierOneLeg.getResult().toString())){
            event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_LEGGINGS,1)), Tier.I, ArmorType.LEGGINGS)));
        }
        else if(result.equals(Gear.customTierOneBoots.getResult().toString()) || result.equals(Gear.customTierOneBoots2.getResult().toString())){
            event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_BOOTS,1)), Tier.I, ArmorType.BOOTS)));
        }
        else if(result.equals(Gear.customTierOneShield.getResult().toString())){
            event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem(new ItemStack(Material.SHIELD,1),Tier.I,WeaponType.SHIELD)));
        }
        else if(result.equals(Gear.customTierOneBow.getResult().toString())){
            event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem(new ItemStack(Material.BOW,1),Tier.I, WeaponType.SWORD)));
        }
        else if(result.equals(Gear.customTierOneArrow.getResult().toString())){
            event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem(new ItemStack(Material.ARROW,1),Tier.I, WeaponType.SWORD)));
        }


    }




}
