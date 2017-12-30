package com.worldciv.events.inventory;

import com.worldciv.filesystem.*;
import com.worldciv.the60th.Main;
import com.worldciv.utility.ItemType;
import com.worldciv.utility.Tier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static com.worldciv.utility.utilityStrings.worldciv;

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
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }
            if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            }
            event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_SWORD,1)), Tier.tempHalfTier, ItemType.SWORD)));
        }
        else if(result.equals(Gear.customTierOneHelm.getResult().toString()) || result.equals(Gear.customTierOneHelm2.getResult().toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            } event.setCurrentItem((CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_HELMET,1)), Tier.tempHalfTier, ItemType.HELM))));
        }
        else if(result.equals(Gear.customTierOneChest.getResult().toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            }event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_CHESTPLATE,1)), Tier.tempHalfTier, ItemType.CHESTPLATE)));
        }
        else if(result.equals(Gear.customTierOneLeg.getResult().toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            }event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_LEGGINGS,1)), Tier.tempHalfTier, ItemType.LEGGINGS)));
        }
        else if(result.equals(Gear.customTierOneBoots.getResult().toString()) || result.equals(Gear.customTierOneBoots2.getResult().toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            }event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_BOOTS,1)), Tier.tempHalfTier, ItemType.BOOTS)));
        }
        else if(result.equals(Gear.customTierOneShield.getResult().toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            }event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem(new ItemStack(Material.SHIELD,1),Tier.tempHalfTier, ItemType.SHIELD)));
        }
        else if(result.equals(Gear.customTierOneBow.getResult().toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            }event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem(new ItemStack(Material.BOW,1),Tier.tempHalfTier, ItemType.BOW)));
        }
        else if(result.equals(Gear.customTierOneArrow.getResult().toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            }event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem(new ItemStack(Material.ARROW,1),Tier.tempHalfTier, ItemType.ARROW)));
            //Bukkit.broadcastMessage("sadsdas222s");
        }else if(result.equals(Gear.customTierOnePike.getResult().toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            }
            ItemStack itemStack = CustomItem.getItemFromCustomItem
                            (Main.fileSystem.createItem(new ItemStack(Material.IRON_SPADE,1),Tier.tempHalfTier, ItemType.PIKE));
            ItemMeta meta = itemStack.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.DURABILITY,1,true);
            itemStack.setItemMeta(meta);
            event.setCurrentItem(itemStack);
        }
        else if(result.equals(Gear.customTierOneLance.getResult().toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            }
            ItemStack itemStack = CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem(new ItemStack(Material.IRON_SPADE,1),Tier.tempHalfTier, ItemType.LANCE));
            ItemMeta meta = itemStack.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.DURABILITY,1,true);
            itemStack.setItemMeta(meta);
            event.setCurrentItem(itemStack);
        }


        if(event.getCurrentItem().toString().equals(Gear.tierOneSword.toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            } event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_SWORD,1)), Tier.I, ItemType.SWORD)));
        }
        else if(event.getCurrentItem().toString().equals(Gear.tierTwoSword.toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            }event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_SWORD,1)), Tier.II, ItemType.SWORD)));

        }else if(event.getCurrentItem().toString().equals(Gear.tierOneHelm.toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            }event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_HELMET,1)), Tier.I, ItemType.HELM)));

        }else if(event.getCurrentItem().toString().equals(Gear.tierOneChest.toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            }event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_CHESTPLATE,1)), Tier.I, ItemType.CHESTPLATE)));

        }else if(event.getCurrentItem().toString().equals(Gear.tierOneLegs.toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            } event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_LEGGINGS,1)), Tier.I, ItemType.LEGGINGS)));

        }else if(event.getCurrentItem().toString().equals(Gear.tierOneBoots.toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            } event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_BOOTS,1)), Tier.I, ItemType.BOOTS)));
        }

    else if(event.getCurrentItem().toString().equals(Gear.tierTwoHelm.toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            }  event.setCurrentItem(CustomItem.getItemFromCustomItem
                (Main.fileSystem.createItem((new ItemStack(Material.IRON_HELMET,1)), Tier.II, ItemType.HELM)));

    }else if(event.getCurrentItem().toString().equals(Gear.tierTwoChest.toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            }event.setCurrentItem(CustomItem.getItemFromCustomItem
                (Main.fileSystem.createItem((new ItemStack(Material.IRON_CHESTPLATE,1)), Tier.II, ItemType.CHESTPLATE)));

    }else if(event.getCurrentItem().toString().equals(Gear.tierTwoLegs.toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            } event.setCurrentItem(CustomItem.getItemFromCustomItem
                (Main.fileSystem.createItem((new ItemStack(Material.IRON_LEGGINGS,1)), Tier.II, ItemType.LEGGINGS)));

    }else if(event.getCurrentItem().toString().equals(Gear.tierTwoBoots.toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            } event.setCurrentItem(CustomItem.getItemFromCustomItem
                (Main.fileSystem.createItem((new ItemStack(Material.IRON_BOOTS,1)), Tier.II, ItemType.BOOTS)));

    }else if(event.getCurrentItem().toString().equals(Gear.tierOneShield.toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            } event.setCurrentItem(CustomItem.getItemFromCustomItem
                (Main.fileSystem.createItem((new ItemStack(Material.SHIELD)),Tier.I, ItemType.SHIELD)));
    }else if(event.getCurrentItem().toString().equals(Gear.tierTwoShield.toString())) {
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            } event.setCurrentItem(CustomItem.getItemFromCustomItem
                (Main.fileSystem.createItem((new ItemStack(Material.SHIELD)), Tier.II, ItemType.SHIELD)));
    }

    else if(event.getCurrentItem().toString().equals(Gear.tierOneBow.toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            }event.setCurrentItem(CustomItem.getItemFromCustomItem
                (Main.fileSystem.createItem((new ItemStack(Material.BOW)),Tier.I, ItemType.BOW)));
    }else if(event.getCurrentItem().toString().equals(Gear.tierTwoBow.toString())) {
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            } event.setCurrentItem(CustomItem.getItemFromCustomItem
                (Main.fileSystem.createItem((new ItemStack(Material.BOW)), Tier.II, ItemType.BOW)));
    }

        else if(event.getCurrentItem().toString().equals(Gear.tierOnePike.toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            }  event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_SPADE)),Tier.I, ItemType.PIKE)));
        }else if(event.getCurrentItem().toString().equals(Gear.tierTwoPike.toString())) {
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            } event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_SPADE)), Tier.II, ItemType.PIKE)));
        }

        else if(event.getCurrentItem().toString().equals(Gear.tierOneLance.toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            }event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_SPADE)),Tier.I, ItemType.LANCE)));
        }else if(event.getCurrentItem().toString().equals(Gear.tierTwoLance.toString())) {
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            } event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_SPADE)), Tier.II, ItemType.LANCE)));
        }

        else if(event.getCurrentItem().toString().equals(Gear.tierOneLance.toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            } event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_SPADE)),Tier.I, ItemType.LANCE)));
        }else if(event.getCurrentItem().toString().equals(Gear.tierTwoLance.toString())) {
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            } event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.IRON_SPADE)), Tier.II, ItemType.LANCE)));
        }

        else if(event.getCurrentItem().toString().equals(Gear.tierOneArrow.toString())){
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            } event.setCurrentItem(CustomItem.getItemFromCustomItem
            (Main.fileSystem.createItem((new ItemStack(Material.ARROW)),Tier.I, ItemType.ARROW)));

            //Bukkit.broadcastMessage("sadsdass");
        }else if(event.getCurrentItem().toString().equals(Gear.tierTwoArrow.toString())) {
           // Bukkit.broadcastMessage("sads");
            if(event.isShiftClick()){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please click normally to craft, don't shift click.");
                event.setCancelled(true);
                return;
            }        if(clicked.getInventory().firstEmpty() < 0){
                clicked.sendMessage(worldciv + ChatColor.GRAY + "Please make room in your inventory before crafting.");
                event.setCancelled(true);
                return;
            } event.setCurrentItem(CustomItem.getItemFromCustomItem
                    (Main.fileSystem.createItem((new ItemStack(Material.ARROW)), Tier.II, ItemType.ARROW)));
        }
    }




}
