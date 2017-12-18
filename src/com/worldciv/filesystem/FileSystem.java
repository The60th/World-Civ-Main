package com.worldciv.filesystem;

import com.sk89q.worldedit.util.YAMLConfiguration;
import com.worldciv.dungeons.Dungeon;
import com.worldciv.the60th.Main;
import com.worldciv.utility.ItemType;
import com.worldciv.utility.Tier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.List;
import java.util.logging.Level;

import static com.worldciv.dungeons.DungeonManager.activedungeons;
import static com.worldciv.the60th.Main.logger;
import static com.worldciv.the60th.Main.plugin;
import static com.worldciv.utility.utilityStrings.worldciv;

public class FileSystem {
    File dungeons_folder;
    File dungeons_file;
    YamlConfiguration dungeons_yml;

    public FileSystem(){
        File custom_items_folder = new File(Bukkit.getPluginManager().getPlugin("WorldCivMaster").getDataFolder()+"/Custom_Items");

        dungeons_folder = new File(Bukkit.getPluginManager().getPlugin("WorldCivMaster").getDataFolder()+"/dungeons");
        dungeons_file = new File(dungeons_folder, "dungeons.yml");

        if(!custom_items_folder.exists()) {
            custom_items_folder.mkdir();
        }

        if(!dungeons_folder.exists()){
            dungeons_folder.mkdir();
        }

        if(!dungeons_file.exists()){
            saveResource("dungeons.yml", dungeons_folder, false);
        }

        dungeons_yml = YamlConfiguration.loadConfiguration(dungeons_file);
    }

    public void saveResource(String resourcePath, File out_to_folder, boolean replace) {
        if (resourcePath != null && !resourcePath.equals("")) {
            resourcePath = resourcePath.replace('\\', '/');
            InputStream in = plugin.getResource(resourcePath);
            if (in == null) {
                throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + plugin.getName());
            } else {
                File outFile = new File(out_to_folder, resourcePath);
                int lastIndex = resourcePath.lastIndexOf(47);
                File outDir = new File(out_to_folder, resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }

                try {
                    if (outFile.exists() && !replace) {
                        plugin.getLogger().log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
                    } else {
                        OutputStream out = new FileOutputStream(outFile);
                        byte[] buf = new byte[4096];

                        int len;
                        while((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }

                        out.close();
                        in.close();
                    }
                } catch (IOException var10) {
                    plugin.getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, var10);
                }

            }
        } else {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }
    }

    public void createDungeon(String dungeon_id){
        if(!dungeons_folder.exists() || !dungeons_file.exists()){
            return;
        }

        dungeons_yml.createSection(dungeon_id);
        dungeons_yml.createSection(dungeon_id + ".Player-Spawn-Location");
        dungeons_yml.set(dungeon_id + ".Player-Spawn-Location", "null");
        dungeons_yml.createSection(dungeon_id + ".Player-End-Spawn-Location");
        dungeons_yml.set(dungeon_id + ".Player-End-Spawn-Location", "null");
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations");
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations.EASY");
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations.MEDIUM");
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations.HARD");

        try {
            dungeons_yml.save(dungeons_file);
        } catch (IOException e){
            logger.info(worldciv + ChatColor.DARK_RED + " Failed to save dungeons file.");
            e.printStackTrace();
        }

    }
    public void removeDungeon(String dungeon_id){
        if(!dungeons_folder.exists() || !dungeons_file.exists()){
            return;
        }

        dungeons_yml.set(dungeon_id, null);
        activedungeons.remove(dungeon_id);

        try {
            dungeons_yml.save(dungeons_file);
        } catch (IOException e){
            logger.info(worldciv + ChatColor.DARK_RED + " Failed to save dungeons file.");
            e.printStackTrace();
        }
    }

    public void saveEntity(String dungeon_id){
        if(!dungeons_folder.exists() || !dungeons_file.exists()){
            return;
        }

        dungeons_yml.createSection(dungeon_id);
        dungeons_yml.createSection(dungeon_id + ".Player-Spawn-Location");
        dungeons_yml.set(dungeon_id + ".Player-Spawn-Location", "null");
        dungeons_yml.createSection(dungeon_id + ".Player-End-Spawn-Location");
        dungeons_yml.set(dungeon_id + ".Player-End-Spawn-Location", "null");
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations");
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations.EASY");
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations.MEDIUM");
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations.HARD");

        try {
            dungeons_yml.save(dungeons_file);
        } catch (IOException e){
            logger.info(worldciv + ChatColor.DARK_RED + " Failed to save dungeons file.");
            e.printStackTrace();
        }

    }

    public void setPlayerSpawn(String dungeon_id, Location location ){
        if(!dungeons_folder.exists() || !dungeons_file.exists()){
            return;
        }

        //String concat_coords = String.valueOf(x) + ", " + String.valueOf(y) + ", " + String.valueOf(z);

        dungeons_yml.set(dungeon_id + ".Player-Spawn-Location", location);

        try {
            dungeons_yml.save(dungeons_file);
        } catch (IOException e){
            logger.info(worldciv + ChatColor.DARK_RED + " Failed to save dungeons file.");
            e.printStackTrace();
        }
    }
    public Location getPlayerSpawn(String dungeon_id){
        if(!dungeons_folder.exists() || !dungeons_file.exists()){
            return null;
        }

        Object value = dungeons_yml.get(dungeon_id + ".Player-Spawn-Location");

        if(value.toString() == "null"){
            logger.info(worldciv + ChatColor.DARK_RED + " No intro spawn location found for " + ChatColor.YELLOW + dungeon_id);
            return null;
        }

        Location location = (Location) dungeons_yml.get(dungeon_id + ".Player-Spawn-Location");
        return location;
    }

    public void setPlayerEndSpawn(String dungeon_id, Location location ){
        if(!dungeons_folder.exists() || !dungeons_file.exists()){
            return;
        }

        //String concat_coords = String.valueOf(x) + ", " + String.valueOf(y) + ", " + String.valueOf(z);

        dungeons_yml.set(dungeon_id + ".Player-End-Spawn-Location", location);

        try {
            dungeons_yml.save(dungeons_file);
        } catch (IOException e){
            logger.info(worldciv + ChatColor.DARK_RED + " Failed to save dungeons file.");
            e.printStackTrace();
        }
    }
    public Location getPlayerEndSpawn(String dungeon_id){
        if(!dungeons_folder.exists() || !dungeons_file.exists()){
            return null;
        }

        Object value = dungeons_yml.get(dungeon_id + ".Player-End-Spawn-Location");


        if(value.toString() == "null"){
            logger.info(worldciv + ChatColor.DARK_RED + " No outro spawn location found for " + ChatColor.YELLOW + dungeon_id);

            return null;
        }

        Location location = (Location) dungeons_yml.get(dungeon_id + ".Player-End-Spawn-Location");
        return location;

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
