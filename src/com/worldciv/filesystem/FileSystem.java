package com.worldciv.filesystem;

import com.worldciv.the60th.Main;
import com.worldciv.utility.ArmorType;
import com.worldciv.utility.Tier;
import com.worldciv.utility.WeaponType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class FileSystem {
    public static boolean exists = false;

    public FileSystem(){
        File dir = new File(Bukkit.getPluginManager().getPlugin("WorldCivMaster").getDataFolder()+"/Custom_Items");
        if(!dir.exists()) {
            dir.mkdir();
            Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Warning!");
            Main.logger.info(("Creating new UID System, please check all plugins if this is not wanted!"));
            File file = new File(dir, "Custom_Items_UID_System.yml");
            if (!(file.exists())) {
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Warning!");
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Creating new UID System, please check all plugins if this is not wanted!");
                YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(file);
                yamlFile.createSection("UID");

                yamlFile.createSection("UID.customArmor");
                yamlFile.createSection("UID.customWeapon");

                yamlFile.createSection("UID.customArmor.Helm");
                yamlFile.createSection("UID.customArmor.ChestPlate");
                yamlFile.createSection("UID.customArmor.Leggings");
                yamlFile.createSection("UID.customArmor.Boots");
                yamlFile.createSection("UID.customArmor.Other");

                yamlFile.createSection("UID.customWeapon.Sword");
                yamlFile.createSection("UID.customWeapon.Shield");
                yamlFile.createSection("UID.customWeapon.Axe");
                yamlFile.createSection("UID.customWeapon.Bow");
                yamlFile.createSection("UID.customWeapon.Other");

                yamlFile.set("UID.customArmor.Helm", 1);
                yamlFile.set("UID.customArmor.ChestPlate", 1);
                yamlFile.set("UID.customArmor.Leggings", 1);
                yamlFile.set("UID.customArmor.Boots", 1);
                yamlFile.set("UID.customArmor.Other", 1);

                yamlFile.set("UID.customWeapon.Sword", 1);
                yamlFile.set("UID.customWeapon.Shield", 1);
                yamlFile.set("UID.customWeapon.Axe", 1);
                yamlFile.set("UID.customWeapon.Bow", 1);
                yamlFile.set("UID.customWeapon.Other", 1);
                try {
                    yamlFile.save(file);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        else{
            this.exists = true;
        }
        //MainCombat.logger.info("Loading UID system.");

    }
    public boolean saveItem(CustomItem item){
        File dir = new File(Bukkit.getPluginManager().getPlugin("WorldCivMaster").getDataFolder()+"/Custom_Items");
        if(dir.exists()) {
            File file = new File(dir,CustomItem.unhideItemUUID(item.getId())+".yml");
            if(file.exists()){
                Main.logger.info("Failed error has occurred when saving an item. Item UUID: [" + item.getId() + "}");
                return false;
            }else{
                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
                yaml = createFileSectionsFromFile(yaml);
                yaml = writeDataToFile(yaml,item);

                try {
                    yaml.save(file);


                    return true;
                } catch (IOException e) {
                    Main.logger.info("Failed error has occurred when saving an item. Item UUID: [" + item.getId() + "}");
                    e.printStackTrace();
                    return false;
                }

            }
        }else{
            Main.logger.info("Failed error has occurred when saving an item. Item UUID: [" + item.getId() + "}");
            return false;
        }

    }
    public CustomItem createItem(ItemStack itemType, Tier tier, WeaponType weaponType){
        CustomItem customItem = ItemGenerator.generateItem(itemType,tier,weaponType);
        saveItem(customItem);
        return customItem;
    }
    public CustomItem createItem(ItemStack itemType, Tier tier, ArmorType armorType){
        CustomItem customItem = ItemGenerator.generateItem(itemType,tier,armorType);
        saveItem(customItem);
        return customItem;
    }
    private YamlConfiguration createFileSectionsFromFile(YamlConfiguration yamlConfiguration){
        yamlConfiguration.createSection("Item-Data");
        yamlConfiguration.createSection("Item-Data.UUID");
        yamlConfiguration.createSection("Item-Data.Name");
        yamlConfiguration.createSection("Item-Data.Damage");
        yamlConfiguration.createSection("Item-Data.Armor");
        yamlConfiguration.createSection("Item-Data.Rarity");
        yamlConfiguration.createSection("Item-Data.Tier");
        yamlConfiguration.createSection("Item-Data.ItemType");
        yamlConfiguration.createSection("Item-Data.Lore");
        yamlConfiguration.createSection("Item-Data.Other");
        yamlConfiguration.createSection("Item-Data.ItemStack");
        return yamlConfiguration;
    }
    private YamlConfiguration writeDataToFile(YamlConfiguration yamlConfiguration, CustomItem item){
        if(item.getId()!=null)yamlConfiguration.set("Item-Data.UUID",CustomItem.unhideItemUUID(item.getId()));
        if(item.getName()!=null)yamlConfiguration.set("Item-Data.Name",item.getName());
        if(item.getDamage()!=-1)yamlConfiguration.set("Item-Data.Damage",item.getDamage());
        if(item.getArmor()!=-1)yamlConfiguration.set("Item-Data.Armor",item.getArmor());
        if(item.getRarity()!=null)yamlConfiguration.set("Item-Data.Rarity",item.getRarity().toString());
        if(item.getTier()!=null)yamlConfiguration.set("Item-Data.Tier",item.getTier().toString());
        if(item.getId()!=null)yamlConfiguration.set("Item-Data.ItemType",-1);
        if(item.getOther()!=-1)yamlConfiguration.set("Item-Data.Lore",item.getOther());
        if(item.getOther()!=-1)yamlConfiguration.set("Item-Data.Other",item.getOther());
        if(item.getItemStack()!=null)yamlConfiguration.set("Item-Data.ItemStack",item.getItemStack());
        return yamlConfiguration;
    }
}
