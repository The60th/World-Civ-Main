package com.worldciv.commands;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.worldciv.events.inventory.AnvilCreate;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

import static com.worldciv.the60th.Main.getWorldGuard;
import static com.worldciv.the60th.Main.plugin;
import static com.worldciv.utility.utilityArrays.lighttutorial;
import static com.worldciv.utility.utilityArrays.visionregion;
import static com.worldciv.utility.utilityStrings.*;

public class WorldCivCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

        if (cmd.getName().equalsIgnoreCase("wc") || cmd.getName().equalsIgnoreCase("worldciv")) {


            if (args.length == 0) {
                helppage1((Player) sender);
                return true;
            }


            switch (args[0].toLowerCase()) {

                case "help":
                case "h":

                    if (args.length == 1) {
                        helppage1((Player) sender);
                        return true;
                    }

                    if (args.length == 2) {

                        if (!args[1].matches("-?\\d+(\\.\\d+)?")) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " You must provide a numerical value.");
return true;
                        }

                        switch (args[1].toLowerCase()) {

                            case "1":
                                helppage1((Player) sender);
                                return true;
                            case "2":
                                helppage2((Player) sender);
                                return true;
                            default:
                                sender.sendMessage(worldciv + ChatColor.GRAY + " The help page you requested for was not found.");
                                return true;

                        }

                    }

                    sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments. Use" + ChatColor.YELLOW + " /wc help [page]" + ChatColor.GRAY + ".");
                    return true;
                case "t":
                case "tutorial":

                    if(!sender.hasPermission("worldciv.admin")){
                        sender.sendMessage(worldciv + ChatColor.GRAY + "No permission for this command.");
                        return true;
                    }

                    if (args.length != 3) {
                        sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments. Use " + ChatColor.YELLOW + "/wc tutorial <tutorial> <player>");
                        return true;
                    }


                    switch (args[1].toLowerCase()) {
                        case "light":
                        case "l":

                            if (Bukkit.getPlayer(args[2]) == null) {
                                sender.sendMessage(worldciv + ChatColor.GRAY + " The player you requested is not a player in this server.");
                                return true;
                            }

                            Player tutorialplayer = Bukkit.getPlayer(args[2]);

                            if(tutorialplayer.getInventory().getContents().length == 36){ //needs to b checked
                                sender.sendMessage(worldciv + ChatColor.GRAY + " There is no inventory space for you to enter the light tutorial.");
                                return true;
                        }
                            addLightTutorial(tutorialplayer);

                            return true;
                        default:
                            sender.sendMessage(worldciv + ChatColor.GRAY + " There is no tutorial with that name.");
                            return true;
                    }

                case "quit":
                case "q":

                    removeLightTutorial((Player) sender);
                    return true;


                default:
                    sender.sendMessage(worldciv + ChatColor.GRAY + " Did you mean to use" + ChatColor.YELLOW + " /wc" + ChatColor.GRAY + "?");
                    return true;
            }
        }
        return true;
    }


    public static void addLightTutorial(Player player) {

        ItemStack is = new ItemStack(Material.TORCH, 1);
        ItemMeta im = is.getItemMeta();
        List<String> templore = Arrays.asList(ChatColor.GRAY + "This torch is designed for:", ChatColor.AQUA + player.getName());
        im.setLore(templore);

        is.setItemMeta(im);

        Location location = new Location(Bukkit.getWorld("world"), 8125, 81, 6370, 180, 0 );
        Location torchlocation = new Location(Bukkit.getWorld("world"), 8125, 90, 6370, 180, 0 );


        player.teleport(location);

        visionregion.remove(player);

        lighttutorial.add(player); //add to an array of  ppl inside lighttutorial.

        String introduction = worldciv + ChatColor.GRAY + " Welcome! You have entered the light tutorial. [Duration: 53 seconds]";
        String optout = ChatColor.YELLOW + " If you ever want to leave the tutorial: " + ChatColor.YELLOW + "/wc quit" +ChatColor.GRAY + ".";
        String droptorch = ChatColor.GRAY + " We aren't sure if you have a torch with you, so for now, we will drop one in the center.";
        String cantfindit = ChatColor.YELLOW + " If you moved out of the center, good luck finding it!";
        String nostealing = ChatColor.GRAY + " Now don't take more than one. If there are other players taking the tutorial, it wouldn't be nice.";
        String justincasestealing = ChatColor.YELLOW + " Just in case, we won't let you steal. We made it steal-proof.";
        String holdinglight = ChatColor.GRAY + " Can you feel the energy of the light run through you? This is the power light has. Without it, you're vulnerable.";
        String playersholdinglight = ChatColor.YELLOW + " If there are other players nearby holding a torch, you are also illuminated. This will be crucial for dungeons!";
        String placinglight = ChatColor.GRAY + " Place that torch! You will also be given light. Any sort of lighting on the block you're standing on will provide you vision.";
        String lightingen = ChatColor.YELLOW + " With that said: the sun, lava, glowstone, and anything that provides light will provide you with vision.";
        String weather = ChatColor.GRAY + " In conclusion, be careful of the weather, if you're caught outside in a storm, your torches will cease to provide light.";
        String thx = ChatColor.YELLOW + " If you have any other questions or comments, feel free to ask staff!";
        String torch = ChatColor.GRAY + "Oh, that's right! We're taking back our torch!";

        List<String> messages = Arrays.asList(introduction, optout, droptorch, cantfindit, nostealing, justincasestealing, holdinglight, playersholdinglight, placinglight
        , lightingen, weather, thx, torch);


        new BukkitRunnable() {
            int x = 0;


            public void run() {

                if (!lighttutorial.contains(player)) {

                    removeLightTutorial(player);
                    cancel();
                    return;
                }

                if(messages.size() == x){

                    player.getInventory().remove(is);
                    removeLightTutorial(player);
                    cancel();
                    return;
                }


                ApplicableRegionSet set = getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());

                boolean tutorialcheck = false;

                for(ProtectedRegion region : set){

                    if("tutorial".equalsIgnoreCase(region.getId())){

                        tutorialcheck = true;
                    }
                }

                if(!tutorialcheck){

                    removeLightTutorial(player);
                    cancel();
                    return;
                }

                if(x == 2){

                    Bukkit.getWorld("world").dropItemNaturally(torchlocation, is);
                    player.playSound(location, Sound.ENTITY_SNOWBALL_THROW, 5, 1);
                }

                player.sendMessage(messages.get(x));

                x++;
            }
        }.runTaskTimer(plugin, 20, 80);



    }

    public static void removeLightTutorial(Player player){

        ItemStack is = new ItemStack(Material.TORCH, 1);
        ItemMeta im = is.getItemMeta();
        List<String> templore = Arrays.asList(ChatColor.GRAY + "This torch is designed for:", ChatColor.AQUA + player.getName());
        im.setLore(templore);
        is.setItemMeta(im);

        if(!lighttutorial.contains(player)){
            player.sendMessage(worldciv + ChatColor.GRAY + " You are not in a tutorial.");
            return;
        }


        player.getInventory().remove(is);

        Location location = new Location(Bukkit.getWorld("world"), 8125, 153, 6374, (-90), 0);
        player.teleport(location);
        lighttutorial.remove(player);
        visionregion.add(player);
        player.sendMessage(worldciv + ChatColor.GRAY + " You have exited the tutorial.");
        return;

    }

    public void helppage1(Player sender) {
        sender.sendMessage(maintop);
        sender.sendMessage(ChatColor.GRAY + " These are some of the commands available for you: ");
        sender.sendMessage(ChatColor.YELLOW + "/party" + ChatColor.GRAY + ": Displays the party commands.");
        sender.sendMessage(ChatColor.YELLOW + "/dungeon" + ChatColor.GRAY + ": Displays the dungeon commands.");
        sender.sendMessage(ChatColor.YELLOW + "/news" + ChatColor.GRAY + ": Displays the current news.");
        sender.sendMessage(ChatColor.YELLOW + "/wc links" + ChatColor.GRAY + ": Displays media (discord, website, vote, etc).");
        sender.sendMessage(ChatColor.YELLOW + "/polls" + ChatColor.GRAY + ": Displays the polls.");


        if (sender.hasPermission("worldciv.admin")) {
            sender.sendMessage(ChatColor.RED + "Only admins can see the following:");
            sender.sendMessage(ChatColor.YELLOW + "/wc tutorial light <player>" + ChatColor.GRAY + ": Send a player to the light level tutorial.");
        }

        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.YELLOW + "/wc help [page]" + ChatColor.GRAY + ": Select your help page. " +ChatColor.ITALIC + "[Currently on page:1]");
        sender.sendMessage(mainbot);
        return;
    }

    public void helppage2(Player sender) {
        sender.sendMessage(maintop);

        sender.sendMessage(ChatColor.GRAY + " These are some of the commands available for you: ");
        sender.sendMessage(ChatColor.YELLOW + "/rules" + ChatColor.GRAY + ": Displays the rules.");
        sender.sendMessage(ChatColor.YELLOW + "/toggle help" + ChatColor.GRAY + ": Displays the toggle commands.");
        sender.sendMessage(ChatColor.YELLOW + "/towny" + ChatColor.GRAY + ": Displays the towny commands.");
        sender.sendMessage(ChatColor.YELLOW + "/brewery" + ChatColor.GRAY + ": Displays the brewery commands.");
        sender.sendMessage(ChatColor.YELLOW + "/channels" + ChatColor.GRAY + ": Displays chat channel commands.");


        if (sender.hasPermission("worldciv.admin")) {
            sender.sendMessage(ChatColor.RED + "Only admins can see the following:");
            // sender.sendMessage(ChatColor.YELLOW + "/wc tutorial light <player>" + ChatColor.GRAY + ": Send a player to the light level tutorial.");
        }

        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.YELLOW + "/wc help [page]" + ChatColor.GRAY + ": Select your help page. " +ChatColor.ITALIC + "[Currently on page:2]");
        sender.sendMessage(mainbot);
        return;
    }


}
