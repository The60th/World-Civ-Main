package com.worldciv.filesystem;

import com.worldciv.commands.PollCommand;
import com.worldciv.dungeons.DungeonMob;
import com.worldciv.the60th.Main;
import com.worldciv.utility.ItemType;
import com.worldciv.utility.Tier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import static com.worldciv.dungeons.DungeonManager.activedungeons;
import static com.worldciv.the60th.Main.logger;
import static com.worldciv.the60th.Main.plugin;
import static com.worldciv.utility.utilityStrings.worldciv;

public class FileSystem {
    File dungeons_folder;
    File dungeons_file;

    File polls_folder;
    File pollsdata_folder;
    File polls_banlist;
    File polls_reportlist;

    YamlConfiguration dungeons_yml;

    public FileSystem(){
        File custom_items_folder = new File(Bukkit.getPluginManager().getPlugin("WorldCivMaster").getDataFolder()+"/Custom_Items");

        dungeons_folder = new File(Bukkit.getPluginManager().getPlugin("WorldCivMaster").getDataFolder()+"/dungeons");
        dungeons_file = new File(dungeons_folder, "dungeons.yml");

        polls_folder = new File(Bukkit.getPluginManager().getPlugin("WorldCivMaster").getDataFolder() + "/polls");
        pollsdata_folder = new File(Bukkit.getPluginManager().getPlugin("WorldCivMaster").getDataFolder() + "/polls/pollsdata");
        polls_banlist = new File(polls_folder, "banlist.yml");
        polls_reportlist = new File(polls_folder, "reportlist.yml");

        if(!custom_items_folder.exists()) {
            custom_items_folder.mkdir();
        }

        if (!polls_folder.exists()) {
            polls_folder.mkdir();
        }

        if(!pollsdata_folder.exists()){
            pollsdata_folder.mkdir();
        }

        if(!dungeons_folder.exists()){
            dungeons_folder.mkdir();
        }

        if(!polls_banlist.exists()){
            try {
                polls_banlist.createNewFile();
            } catch(Exception e){

            }
        }

        if(!polls_reportlist.exists()){
            try {
                polls_reportlist.createNewFile();
            } catch(Exception e){

            }
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

    public void createPoll(Player p, String msg) {
        if (!polls_folder.exists() && !pollsdata_folder.exists()) {
            return;
        }
        int id;

        for (id = 0; id < 200; id++) {

            File file = new File(pollsdata_folder, "ID" + String.valueOf(id) + "-" + p.getUniqueId() + "-" + p.getName() + ".yml");




            if (allFileNames(pollsdata_folder) == null || !file.exists()) { //no list

                YamlConfiguration file_yml = YamlConfiguration.loadConfiguration(file);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();

                file_yml.createSection(String.valueOf(id));
                file_yml.createSection(String.valueOf(id) + ".Upload Date");
                file_yml.set(String.valueOf(id) + ".Upload Date", dateFormat.format(date));
                file_yml.createSection(String.valueOf(id) + ".Author");
                file_yml.set(String.valueOf(id) + ".Author", p.getName());
                file_yml.createSection(String.valueOf(id) + ".Subject");
                file_yml.set(String.valueOf(id) + ".Subject", msg);
                file_yml.createSection(String.valueOf(id) + ".Votes");

                try{
                    file_yml.save(file);
                }catch(Exception e){

                }
                break;
            }
        }

    }

    public List<String> allFileNames(File folder) {
        File[] listOfFiles = folder.listFiles();
        List<String> list = new ArrayList<>();

        for (File file : listOfFiles) {
            String file_name = file.getName();
            list.add(file_name);
        }

        if (list.isEmpty() || list == null) {

            return null;
        }
        return list;
    }

    public File[] allFiles(File folder) {
        File[] listOfFiles = folder.listFiles();

        return listOfFiles;
    }

    public void createDungeon(String dungeon_id){
        if(!dungeons_folder.exists() || !dungeons_file.exists()){
            return;
        }
        List<String> easy = Arrays.asList("easy");
        List<String> medium = Arrays.asList("medium");
        List<String> hard = Arrays.asList("hard");

        dungeons_yml.createSection(dungeon_id);
        dungeons_yml.createSection(dungeon_id + ".Player-Spawn-Location");
        dungeons_yml.set(dungeon_id + ".Player-Spawn-Location", "null");
        dungeons_yml.createSection(dungeon_id + ".Player-End-Spawn-Location");
        dungeons_yml.set(dungeon_id + ".Player-End-Spawn-Location", "null");
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations");
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations.EASY");
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations.MEDIUM");
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations.HARD");

        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations.EASY.ENCOUNTERS");
        dungeons_yml.set(dungeon_id + ".Mob-Spawn-Locations.EASY.ENCOUNTERS",easy);
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations.MEDIUM.ENCOUNTERS");
        dungeons_yml.set(dungeon_id + ".Mob-Spawn-Locations.MEDIUM.ENCOUNTERS",medium);
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations.HARD.ENCOUNTERS");
        dungeons_yml.set(dungeon_id + ".Mob-Spawn-Locations.HARD.ENCOUNTERS",hard);
        try {
            dungeons_yml.save(dungeons_file);
        } catch (IOException e){
            logger.info(worldciv + ChatColor.DARK_RED + " Failed to save dungeons file.");
            e.printStackTrace();
        }

    }


    public void removethislater(String dungeon_id){

        if(!dungeons_folder.exists() || !dungeons_file.exists()){
            return;
        }

        Bukkit.broadcastMessage(dungeons_yml.getStringList(dungeon_id).toString());
        Bukkit.broadcastMessage(dungeons_yml.getList(dungeon_id).toString());
        //Bukkit.broadcastMessage(dungeons_yml.);



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

    /*public void saveEntity(String dungeon_id){
        if(!dungeons_folder.exists() || !dungeons_file.exists()){
            return;
        }
        List<String> blank = Arrays.asList("sup1", "sup2", "sup3");

        dungeons_yml.createSection(dungeon_id);
        dungeons_yml.createSection(dungeon_id + ".Player-Spawn-Location");
        dungeons_yml.set(dungeon_id + ".Player-Spawn-Location", "null");
        dungeons_yml.createSection(dungeon_id + ".Player-End-Spawn-Location");
        dungeons_yml.set(dungeon_id + ".Player-End-Spawn-Location", "null");
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations");
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations.EASY");
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations.EASY.ENCOUNTERS");
        dungeons_yml.set(dungeon_id + ".Mob-Spawn-Locations.EASY.ENCOUNTERS",blank);
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations.MEDIUM");
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations.MEDIUM.ENCOUNTERS");
        dungeons_yml.set(dungeon_id + ".Mob-Spawn-Locations.MEDIUM.ENCOUNTERS",blank);
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations.HARD");
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations.HARD.ENCOUNTERS");
        dungeons_yml.set(dungeon_id + ".Mob-Spawn-Locations.HARD.ENCOUNTERS",blank);

        try {
            dungeons_yml.save(dungeons_file);
        } catch (IOException e){
            logger.info(worldciv + ChatColor.DARK_RED + " Failed to save dungeons file.");
            e.printStackTrace();
        }

    }*/
    public void saveMob(String dungeon_id, String mythicMobID,String difficulty, int amount, Location location,String encounterName){
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations."+difficulty+"."+encounterName);
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations."+difficulty+"."+encounterName+".Mobs");
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations."+difficulty+"."+encounterName+".Mobs.Name");
        dungeons_yml.createSection(dungeon_id + ".Mob-Spawn-Locations."+difficulty+"."+encounterName+".Mobs.amount");
        dungeons_yml.set(dungeon_id + ".Mob-Spawn-Locations."+difficulty+"."+encounterName+".Mobs.amount",amount);
        dungeons_yml.set(dungeon_id + ".Mob-Spawn-Locations."+difficulty+"."+encounterName+".Mobs.Name",mythicMobID);
        dungeons_yml.set(dungeon_id + ".Mob-Spawn-Locations."+difficulty+"."+encounterName+".Location", location);

        List<String> encounters = dungeons_yml.getStringList(dungeon_id+".Mob-Spawn-Locations."+difficulty+".ENCOUNTERS");

        if (encounters.get(0).equals("easy") ||encounters.get(0).equals("medium") ||encounters.get(0).equals("hard")) {
            encounters.remove(0);
            encounters.add(encounterName);
        }else{
            encounters.add(encounterName);
        }
        dungeons_yml.set(dungeon_id+".Mob-Spawn-Locations."+difficulty+".ENCOUNTERS",encounters);

        try {
            dungeons_yml.save(dungeons_file);
        } catch (IOException e){
            logger.info(worldciv + ChatColor.DARK_RED + " Failed to save dungeons file.");
            e.printStackTrace();
        }
    }
    public DungeonMob[] loadMobs(String dungeon_id, String difficulty){
        List<String> encounters = dungeons_yml.getStringList(dungeon_id+".Mob-Spawn-Locations."+difficulty+".ENCOUNTERS");
        Bukkit.broadcastMessage(encounters.toString());
        DungeonMob[] mobs = new DungeonMob[encounters.size()];
        //YamlConfiguration yaml = dungeons_yml.get(dungeon_id + ".Mob-Spawn-Locations."+difficulty);
        for(int i = 0; i <encounters.size();i++){
            Location location = (Location) dungeons_yml.get(dungeon_id + ".Mob-Spawn-Locations." +difficulty+"."+ encounters.get(i)+".Location");
            Bukkit.broadcastMessage(location.toString());
            mobs[i] = new DungeonMob(location,
                    dungeons_yml.getInt(dungeon_id + ".Mob-Spawn-Locations."+difficulty+"."+encounters.get(i)+".Mobs.amount"),
                    dungeons_yml.getString(dungeon_id + ".Mob-Spawn-Locations."+difficulty+"."+encounters.get(i)+".Mobs.Name"));
        }
        return mobs;
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
