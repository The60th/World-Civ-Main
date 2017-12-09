package com.worldciv.filesystem;

import com.worldciv.the60th.Main;
import com.worldciv.utility.ItemType;
import com.worldciv.utility.Tier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class FileSystem {
    public static boolean itemFilesExists = false;

    public FileSystem(){
        File dir = new File(Bukkit.getPluginManager().getPlugin("WorldCivMaster").getDataFolder()+"/Custom_Items");
        if(!dir.exists()) {
            dir.mkdir();
            Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Warning! Check console for debug data!");
            Main.logger.info(("Creating new UID System, please check all plugins if this is not wanted!"));
        }
        else{
            itemFilesExists = true;
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
    public CustomItem createItem(ItemStack itemStack, Tier tier, ItemType itemType){
        CustomItem customItem = ItemGenerator.generateItem(itemStack,tier,itemType);
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
        if(item.getId()!=null)yamlConfiguration.set("Item-Data.ItemType",item.getItemType().toString());
        if(item.getOther()!=-1)yamlConfiguration.set("Item-Data.Lore",item.getOther());
        if(item.getOther()!=-1)yamlConfiguration.set("Item-Data.Other",item.getOther());
        if(item.getItemStack()!=null)yamlConfiguration.set("Item-Data.ItemStack",item.getItemStack());
        return yamlConfiguration;
    }
}
