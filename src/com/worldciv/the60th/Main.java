package com.worldciv.the60th;


import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.worldciv.commands.DungeonCommand;
import com.worldciv.commands.NewsCommand;
import com.worldciv.commands.PartyCommand;
import com.worldciv.commands.ToggleCommand;
import com.worldciv.dungeons.DungeonManager;
import com.worldciv.events.inventory.AnvilCreate;
import com.worldciv.events.inventory.CraftCreate;
import com.worldciv.events.inventory.FurnaceCreate;
import com.worldciv.events.player.*;
import com.worldciv.scoreboard.scoreboardManager;
import com.worldciv.utility.CraftingRecipes;
import com.worldciv.utility.FurnaceRecipes;
import io.lumine.xikage.mythicmobs.MythicMobs;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.worldciv.events.inventory.CraftEvent;
import com.worldciv.events.player.ArrowEvents;
import com.worldciv.events.player.JoinEvent;
import com.worldciv.events.player.AttackEvent;
import com.worldciv.filesystem.FileSystem;
import com.worldciv.filesystem.Gear;

import java.util.Random;
import java.util.logging.Logger;

import static com.worldciv.utility.utilityStrings.worldciv;

public class Main extends JavaPlugin {


    protected static scoreboardManager scoreboardManager;
    public static Plugin plugin;
    public static JavaPlugin javaPlugin;
    public static FileSystem fileSystem;
    public static DungeonManager getDungeonManager;
    public static Logger logger;


    public static final Flag vision_bypass = new StateFlag("vision-bypass", true);
    public static final Flag dungeon_region = new StateFlag("dungeon-region", true);

    public void onLoad(){

        FlagRegistry registry = getWorldGuard().getFlagRegistry();
        try {
            registry.register(vision_bypass);
            registry.register(dungeon_region);
        } catch (FlagConflictException e) {
            e.printStackTrace();
        }


    }

    public void onEnable() {
        logger = Logger.getLogger("Minecraft");
        plugin = this;
        javaPlugin = this;


        getConfig().options().copyDefaults(true); //creates data folder for pl
        fileSystem = new FileSystem();
        getDungeonManager = new DungeonManager();


        if (getConfig().getString("newsmessage") == null) {
            getConfig().set("newsmessage", "          " + ChatColor.GRAY + "This must be a new server. Set a news message with /news set <message>");
        }
        saveConfig();

        scoreboardManager = new scoreboardManager();
        PluginDescriptionFile pdfFile = this.getDescription();

        logger.info(pdfFile.getName()
                + "has successfully enabled. The current version is: "
                + pdfFile.getVersion());

        registerEvents();
        registerCommands();

        //Check time of day!
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run(){
                Server server = getServer();
                long time = server.getWorld("world").getTime();

                if(time >= 13200 && time <=13239 ){
                    Bukkit.broadcastMessage(worldciv + ChatColor.GRAY + " It's getting dark...");
                } else if (time >= 22390 && time <= 22429){ //2399 is last tick or 2400? use 2399 for safety
                    Bukkit.broadcastMessage(worldciv + ChatColor.GRAY + " It appears morning is arising.");

                }

                for (Player players : Bukkit.getOnlinePlayers()) {
                    Location loc = players.getLocation();
                    World world = players.getWorld();
                    Biome biome = world.getBiome(loc.getBlockX(), loc.getBlockZ());

                    if (world.hasStorm()) {

                        if (!(biome == Biome.DESERT || biome == Biome.DESERT_HILLS | biome == Biome.MUTATED_DESERT || biome == Biome.MESA || biome == Biome.MESA_CLEAR_ROCK
                                || biome == Biome.MESA_ROCK || biome == Biome.MUTATED_MESA || biome == Biome.MUTATED_MESA_CLEAR_ROCK || biome == Biome.MUTATED_MESA_ROCK
                                || biome == Biome.SAVANNA || biome == Biome.SAVANNA_ROCK || biome == Biome.MUTATED_SAVANNA || biome == Biome.MUTATED_SAVANNA_ROCK)) {



                            if (world.getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ()) < players.getLocation().getBlockY() + 1) {


                                ItemStack currentItem = players.getInventory().getItemInMainHand();
                                ItemStack offHandItem = players.getInventory().getItemInOffHand();

                                if (currentItem.getType() == Material.TORCH) {
                                    Random r = new Random();
                                    int chance = r.nextInt(1200000);

                                    if (chance < 150000) {
                                        if (currentItem.getAmount() > 1) {
                                            currentItem.setAmount(currentItem.getAmount() - 1);
                                            players.sendMessage(worldciv + ChatColor.GRAY + " The storm has made one of your torches useless!");
                                        } else {
                                            currentItem.setAmount(-1);
                                            players.sendMessage(worldciv + ChatColor.GRAY + " Your last torch  in your main hand was used!");
                                        }

                                    }
                                } else if (offHandItem.getType() == Material.TORCH) {
                                    Random r = new Random();
                                    int chance = r.nextInt(1200000);

                                    if (chance < 150000) {
                                        if (offHandItem.getAmount() > 1) {
                                            offHandItem.setAmount(offHandItem.getAmount() - 1);
                                            players.sendMessage(worldciv + ChatColor.GRAY + " The storm has made one of your torches useless!");
                                        } else {
                                            offHandItem.setAmount(-1);
                                            players.sendMessage(worldciv + ChatColor.GRAY + " Your last torch in your offhand was used!");
                                        }

                                    }
                                }
                            }
                        }
                    }
                }

            }
        }, 0, 40); //every 2s, try not to jam the server!

        for (Player p : Bukkit.getOnlinePlayers()){
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard()); //REMOVES CURRENT SB if at all any.
            scoreboardManager.setScoreboard(p);
        }

        CraftingRecipes.registerRecipes();
        FurnaceRecipes.registerFurnaceRecipes();
        Gear.registerRecipes();

        Bukkit.broadcastMessage(worldciv + ChatColor.GRAY + " Refreshing plugin data.");
    }

    public void onDisable() {
        plugin = null;
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = Logger.getLogger("Minecraft");
        logger.info(pdfFile.getName() + "has successfully disabled.");
    }

    public void registerCommands(){
        getCommand("toggle").setExecutor(new ToggleCommand());
        getCommand("dungeon").setExecutor(new DungeonCommand());
        getCommand("dg").setExecutor(new DungeonCommand());
        getCommand("news").setExecutor(new NewsCommand());
        getCommand("party").setExecutor(new PartyCommand());
        getCommand("p").setExecutor(new PartyCommand());
        getCommand("t").setExecutor(new ToggleCommand());
    }

    public void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new QuitEvent(), this);
        pm.registerEvents(new CommandPreprocess(), this);
        pm.registerEvents(new WeatherChangingEvent(), this);
        pm.registerEvents(new AnvilCreate(), this);
        pm.registerEvents(new CraftCreate(), this);
        pm.registerEvents(new FurnaceCreate(), this);
        pm.registerEvents(new JoinEvent(), this);
        pm.registerEvents(new AttackEvent(), this);
        pm.registerEvents(new CraftEvent(), this);
        pm.registerEvents(new RegionEvent(), this);
        pm.registerEvents(new ArrowEvents(), this);
    }



    public static Plugin getPlugin() {
        return plugin;
    }

    public static scoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }


    public static WorldGuardPlugin getWorldGuard() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }

    public static WorldEditPlugin getWorldEdit() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldEditPlugin)) {
            return null;
        }

        return (WorldEditPlugin) plugin;
    }

    public static MythicMobs getMythicMobs() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("MythicMobs");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof MythicMobs)) {
            return null;
        }

        return (MythicMobs) plugin;
    }

}







