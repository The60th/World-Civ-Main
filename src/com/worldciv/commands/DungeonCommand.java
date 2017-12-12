package com.worldciv.commands;

import com.google.common.collect.Multimap;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.worldciv.dungeons.Dungeon;
import com.worldciv.mythicmobs.CustomMobs;
import com.worldciv.parties.Party;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.worldciv.the60th.Main.*;
import static com.worldciv.utility.utilityArrays.notreadylist;
import static com.worldciv.utility.utilityStrings.*;

public class DungeonCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender robotsender, Command cmd, String alias, String[] args) {

        Party party = new Party();
        String dungeonregionname;
        if (!(robotsender instanceof Player)) {
            robotsender.sendMessage(ChatColor.RED + "Silly console! You can't join dungeons, that's unfair!");
            return true;
        }

        Player sender = (Player) robotsender;


        if (cmd.getName().equalsIgnoreCase("dungeon") || cmd.getName().equalsIgnoreCase("dg")) {

            if (args.length == 0) {

                if (getDungeonManager.getDungeon(sender) == null) {
                    sender.sendMessage(maintop);
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon join <dungeon-id> <#difficulty>" + ChatColor.GRAY + ": Join a dungeon.");
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon list" + ChatColor.GRAY + ": Displays all dungeon's status.");
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon leave" + ChatColor.GRAY + ": Leave a dungeon as group.");
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon cancel" + ChatColor.GRAY + ": Cancel pending dungeon joining status.");
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon stats" + ChatColor.GRAY + ": Statistics for current dungeon.");

                    if (isDungeonAdmin(sender)) {
                        sender.sendMessage(ChatColor.RED + "/dungeon admin" + ChatColor.RED + ": Admins can only see this.");
                    }


                    sender.sendMessage(mainbot);
                    return true;
                } else {
                    //if in dungeon show wot current dungeon ur in
                    return true;
                }
            }

            switch (args[0].toLowerCase()) {
                /*
                //The "create" argument is for staff use only and creates a dungeon when a //wand selection is used.
                 */

                case "c":
                case "create":

                    if (!isDungeonAdmin(sender)) {
                        return true;
                    }

                    if (args.length == 2) { //If /dg create <id>

                        Selection sel = getWorldEdit().getSelection(sender);

                        if (sel == null) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " You must make a selection before creating a dungeon. Use " + ChatColor.YELLOW + "//wand.");
                            return true;
                        }

                        for (String possible_dungeon : getDungeonManager.getAllDungeons()) {

                            if (possible_dungeon.equalsIgnoreCase(args[1])) {
                                sender.sendMessage(worldciv + ChatColor.RED + " Error: This region name is already in use!");
                                return true;
                            }
                        }

                        //Region is created with FLAG(S): dungeon-region

                        ProtectedCuboidRegion region = new ProtectedCuboidRegion(
                                args[1],
                                new BlockVector(sel.getNativeMinimumPoint()),
                                new BlockVector(sel.getNativeMaximumPoint())
                        );

                        getWorldGuard().getRegionManager(sender.getWorld()).addRegion(region); //create the region
                        getWorldGuard().getRegionManager(sender.getWorld()).getRegion(region.getId()).setFlag(dungeon_region, StateFlag.State.ALLOW); //add dungeon_region flag
                        fileSystem.createDungeon(region.getId()); //write it on autogeneration
                        sender.sendMessage(worldciv + ChatColor.GRAY + " The dungeon region " + ChatColor.YELLOW + "'" + region.getId() + "'" + ChatColor.GRAY + " has been created! Be sure to be inside this region when editing!");


                        return true;

                    }
                    sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments! Use " + ChatColor.YELLOW + "/dg create <id>");
                    return true;
                /*
                The delete command is for staff use only. It will remove dungeons from their id.
                */
                case "d":
                case "delete":
                case "remove":
                    if (!isDungeonAdmin(sender)) {
                        return true;
                    }

                    if (args.length == 2) { //If /dg create <id>

                        String region = "";

                        for (String possible_dungeon : getDungeonManager.getAllDungeons()) {
                            if (possible_dungeon.toString().equalsIgnoreCase(args[1])) {

                                region += args[1];

                            }
                        }

                        if (region.equalsIgnoreCase("")) {
                            sender.sendMessage(worldciv + ChatColor.RED + " We could not find the specified dungeon.");
                            return true;
                        }

                        getWorldGuard().getRegionManager(sender.getWorld()).removeRegion(region);
                        fileSystem.removeDungeon(region); //REMOVE ALL DATA FOR THIS DUNGEON
                        sender.sendMessage(worldciv + ChatColor.GRAY + " The dungeon region " + ChatColor.YELLOW + "'" + args[1] + "'" + ChatColor.GRAY + " has been removed permanently! All data has been cleared!");

                        return true;

                    }
                    sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments! Use " + ChatColor.YELLOW + "/dg delete <id>");
                    return true;
                 /*
                 The list command will list available dungeons and information about them. This is not a staff command.

                  */
                case "l":
                case "list":
                    if (args.length == 1) {

                        if (getDungeonManager.getAllDungeons().size() == 0) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " There are no established dungeons on this server.");
                            return true;
                        }

                        sender.sendMessage(worldciv + ChatColor.GRAY + " Dungeons: ");

                        for (String dungeonid : getDungeonManager.getAllDungeons()) {

                            if (getDungeonManager.getAllActiveDungeons() == null) {
                                sender.sendMessage(ChatColor.GOLD + dungeonid + ": " + ChatColor.GREEN + "Available");
                            } else if (!getDungeonManager.getAllActiveDungeons().containsKey(dungeonid)) {
                                sender.sendMessage(ChatColor.GOLD + dungeonid + ": " + ChatColor.GREEN + "Available");
                            } else if (getDungeonManager.getAllActiveDungeons().containsKey(dungeonid)) {

                                if (!party.hasParty(sender)) {
                                    sender.sendMessage(ChatColor.GOLD + dungeonid + ": " + ChatColor.RED + "Occupied");
                                    return true;
                                }

                                String party_id = party.getPartyID(sender);
                                Dungeon dungeon = getDungeonManager.getDungeon(party_id);

                                if (dungeon == null) {
                                    sender.sendMessage(ChatColor.GOLD + dungeonid + ": " + ChatColor.RED + "Occupied");
                                    return true;
                                }

                                List<String> playersindungeon = dungeon.getPlayers(party_id);


                                if (getDungeonManager.getAllActiveDungeons().get(dungeonid).equalsIgnoreCase(playersindungeon.toString())) {
                                    String players = getDungeonManager.getAllActiveDungeons().get(dungeonid);
                                    players = players.replace("[", "");
                                    players = players.replace("]", "");

                                    sender.sendMessage(ChatColor.GOLD + dungeonid + ": " + ChatColor.AQUA + players);
                                } else { //in dungeon but not with same players
                                    sender.sendMessage(ChatColor.GOLD + dungeonid + ": " + ChatColor.RED + "Occupied");

                                }
                            }
                        }

                        return true;
                    }
                    sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments! Use " + ChatColor.YELLOW + "/dg list");
                    return true;
                case "j":
                case "join": //public use to join dungeons'

                    if (args.length == 3) {

                        if (!party.hasParty(sender)) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " You must be in a party to join dungeons.");
                            return true;
                        }

                        if (!party.isLeader(sender)) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " You must be the party leader to join dungeons.");
                            return true;
                        }

                        if (getDungeonManager.getDungeon(sender) != null) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " You're already in a dungeon.");

                            return true;
                        }

                        if (getWorldGuard().getRegionManager(sender.getWorld()).getRegion(args[1]) == null) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " You must provide a valid dungeon name.");
                            return true;
                        }

                        if (getWorldGuard().getRegionManager(sender.getWorld()).getRegion(args[1]).getFlag(dungeon_region) != StateFlag.State.ALLOW) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " You must provide a valid dungeon name.");
                            return true;
                        }

                        if (!args[2].matches(".*\\d.*")) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " You must provide a numerical value as a difficulty.");
                            return true;
                        } else {
                            if (!(1 <= Integer.parseInt(args[2]) && 3 >= Integer.parseInt(args[2]))) {
                                sender.sendMessage(worldciv + ChatColor.GRAY + " You must provide a numerical value between 1-3.");
                                return true;
                            }
                        }

                        if (getDungeonManager.isDungeon(args[1])) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " This dungeon is occupied.");
                            return true;
                        }


                        if (!party.isAllNear(sender, 6)) { //you must be with all your team together
                            Multimap<String, String> partymembersnotnear = party.getWhoNotNear(sender, 6);
                            Set<String> listofkeys = partymembersnotnear.keySet();

                            sender.sendMessage(maintop);
                            sender.sendMessage(worldciv + ChatColor.GRAY + " You must be near these players to enter the dungeon:");

                            for (String player_string : listofkeys) {
                                if (partymembersnotnear.get(player_string).toString().matches(".*\\d.*")) {
                                    sender.sendMessage(ChatColor.AQUA + player_string + ChatColor.GRAY + ": Player is " + ChatColor.YELLOW + partymembersnotnear.get(player_string) + ChatColor.GRAY + " blocks away.");
                                } else {
                                    sender.sendMessage(ChatColor.AQUA + player_string + ChatColor.GRAY + ": " + ChatColor.GREEN + partymembersnotnear.get(player_string));

                                }
                            }


                            sender.sendMessage(mainbot);
                            return true;
                        }

                        String officialdungeonid = getWorldGuard().getRegionManager(Bukkit.getWorld("world")).getRegion(args[1]).getId();
                        String messagetoparty = worldciv + ChatColor.GOLD + " You are about to enter dungeon: " + ChatColor.YELLOW + "'" + officialdungeonid + "'" + ChatColor.GOLD
                                + ", difficulty " + ChatColor.YELLOW + "'" + args[2] + "'" + ChatColor.GOLD + ", and party total size " + ChatColor.YELLOW + "'" +
                                party.size(sender) + "'" + ChatColor.GOLD + " in " + ChatColor.YELLOW + "10" + ChatColor.GOLD + " seconds.";

                        String messagehowtojoin = ChatColor.RED + "All players in the party MUST be ready with " + ChatColor.YELLOW + "/dg ready " +
                                "in order to join the dungeon.";

                        party.sendToParty(sender, messagetoparty);
                        party.sendToParty(sender, messagehowtojoin);

                        for(String players_in_string : party.getPlayers(sender)){
                            Player player = Bukkit.getPlayer(players_in_string);
                            notreadylist.add(player);
                        }


                        new BukkitRunnable() {
                            int x = 0;

                            public void run() {

                               if(getDungeonManager.allReady(sender)){
                                   cancel();
                                   return;
                               }




                                if (x == 10) {
                                    if(!getDungeonManager.allReady(sender)) {

                                        String msgclean = getDungeonManager.getPlayersNotReady(sender).toString().replace("]", "").replace("[", "");

                                        String msg = worldciv + ChatColor.GRAY+ " The following players did not ready up: " +ChatColor.AQUA + msgclean;
                                        party.sendToParty(sender, msg);

                                        for(String players_in_string : getDungeonManager.getPlayersNotReady(sender)){
                                            Player players = Bukkit.getPlayer(players_in_string);
                                            notreadylist.remove(players);
                                        }
                                        cancel();
                                        return;
                                    }
                                }
                                x++;
                            }
                        }.runTaskTimer(plugin, 0, 20);
                        return true;
                    }
                    sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments! Use " + ChatColor.YELLOW + "/dg join <dungeon-id> <difficulty>");
                    return true;
                case "r":
                case "ready":

                    if (args.length == 1) {


                        if (!notreadylist.contains(sender)) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " You must be in pending status to ready up!");
                            return true;
                        }

                        if (getDungeonManager.getDungeon(sender) != null) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " You already joined the dungeon. The leader can use" + ChatColor.YELLOW + " /dg leave" + ChatColor.GRAY +
                                    " to leave the party as a group.");
                            return true;

                        }



                        String msg = worldciv + ChatColor.AQUA + sender.getName() + ChatColor.GRAY + " is ready to join the dungeon!";

                        party.sendToParty(sender, msg);
                        notreadylist.remove(sender);

                        if(getDungeonManager.allReady(sender)){
                            Dungeon dungeon = new Dungeon(party.getPartyID(sender), args[1], Integer.parseInt(args[2]));

                            String allrdy = worldciv + ChatColor.GREEN + "All players are ready to join the dungeon!";

                            party.sendToParty(sender, allrdy);

                            getDungeonManager.addDungeon(dungeon);
                        }
                        return true;
                    }
                    sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments! Use " + ChatColor.YELLOW + "/dg ready" +ChatColor.GRAY +
                    "to ready up!");
                    return true;

                case "ms":
                case "mobspawn":

                    if (!isDungeonAdmin(sender)) {
                        return true;
                    }

                    dungeonregionname = "";

                    for (ProtectedRegion r : getDungeonManager.getCurrentRegion(sender)) {
                        for (String regionname : getDungeonManager.getAllDungeons()) {

                            if (regionname.equalsIgnoreCase(r.getId())) {
                                dungeonregionname += regionname.toString();

                            }
                        }
                        }

                        if (dungeonregionname.equals("")) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " There is no dungeon region in your current location.");
                            return true;
                        }


                        if (args.length == 2) {

                            for (String mob_id : getMythicMobs().getMobManager().getMobNames()) {
                                if (mob_id.equalsIgnoreCase(args[1])) {

                                    Location loc = sender.getLocation();

                                    CustomMobs.spawn(loc);
                                    sender.sendMessage(worldciv + ChatColor.GRAY + " You have spawned a " + ChatColor.YELLOW + "'" + mob_id + "'" + ChatColor.GRAY + " in region " + ChatColor.YELLOW + "'" + dungeonregionname + "'" + ChatColor.GRAY + ".");
                                    return true;
                                }
                            }

                            sender.sendMessage(worldciv + ChatColor.GRAY + " There was no mob-id with this name. Use " + ChatColor.YELLOW + "/mm mobs list" + ChatColor.GRAY + ".");

                            return true;
                        }

                    sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments. Use" + ChatColor.YELLOW + " /dg mobspawn <mob-id>" + ChatColor.GRAY + "inside a dungeon region.");
                        return true;
                case "inspect":
                case "i":
                    if (!isDungeonAdmin(sender)) {
                        return true;
                    }

                    if (args.length == 1) {

                        ArrayList<String> dungeon_on = new ArrayList<String>();

                        for (ProtectedRegion player_on_region : getDungeonManager.getCurrentRegion(sender)) {
                            for (String dungeon_id : getDungeonManager.getAllDungeons()) {

                                if (dungeon_id.equalsIgnoreCase(player_on_region.getId())) {
                                    dungeon_on.add(dungeon_id);

                                }
                            }
                        }

                        if (dungeon_on.isEmpty()) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " There is no dungeon region in your current location.");
                            return true;
                        } else {
                            sender.sendMessage(maintop);
                            sender.sendMessage(worldciv + ChatColor.GRAY + " You are currently standing on:");
                            for (String possible_dungeons : dungeon_on) {
                                sender.sendMessage(ChatColor.GOLD + possible_dungeons);
                            }
                            sender.sendMessage(mainbot);
                            return true;

                        }

                       //TODO Add players currently in this dungeon.
                    }
                    sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments. Use" + ChatColor.YELLOW + " /dg inspect" + ChatColor.GRAY + "inside a dungeon region.");
                    return true;

                case "ss":
                case "setspawn":
                    if (!isDungeonAdmin(sender)) {
                        return true;
                    }

                    if (args.length == 1) {

                        dungeonregionname = "";

                        for (ProtectedRegion player_on_region : getDungeonManager.getCurrentRegion(sender)) {
                            for (String dungeon_id : getDungeonManager.getAllDungeons()) {

                                if (dungeon_id.equalsIgnoreCase(player_on_region.getId())) {
                                    dungeonregionname += dungeon_id;

                                }
                            }
                        }

                        if (dungeonregionname.equals("")) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " There is no dungeon region in your current location.");
                            return true;
                        }

                        if (!getDungeonManager.getAllDungeons().contains(dungeonregionname)) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " There's two dungeons complicating each other! Check the dungeons you're standing inside with " + ChatColor.YELLOW +
                                    "/dg inspect " + ChatColor.GRAY + ".");
                            return true;
                        }


                        Location loc = sender.getLocation();

                        if(loc == null){
                            sender.sendMessage(worldciv + ChatColor.GRAY + " Somehow your location is null. Try again.");
                            return true;
                        }

                        fileSystem.setPlayerSpawn(dungeonregionname, loc);
                        sender.sendMessage(worldciv + ChatColor.GRAY + " You have set the player spawn coordinates to your current location in " + ChatColor.YELLOW + "'" + dungeonregionname + "'" + ChatColor.GRAY + ".");
                        return true;


                    }
                    sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments. Use" + ChatColor.YELLOW + " /dg setspawn" + ChatColor.GRAY + "inside a dungeon region.");
                    return true;
                case "a":
                case "admin": //HELP PAGE FOR ADMINS

                    if (!isDungeonAdmin(sender)) {
                        return true;
                    }

                    sender.sendMessage(maintop);
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon create <id>" + ChatColor.GRAY + ": Create a dungeon.");
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon delete <id>" + ChatColor.GRAY + ": Remove a dungeon.");
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon inspect" + ChatColor.GRAY + ": Inspect the region you're standing on.");
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon setspawn" + ChatColor.GRAY + ": Set the player-spawn in a dungeon.");
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon mobspawn <mob-id>" + ChatColor.GRAY + ": Set the mob-spawn(s) in a dungeon.");
                    //  sender.sendMessage(ChatColor.YELLOW + "/dungeon cp <cp-id>" + ChatColor.GRAY + ": Set dungeon checkpoints.");
                    sender.sendMessage(mainbot);

                    return true;
                default:
                    sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments. Did you mean" + ChatColor.YELLOW + " /dungeon" + ChatColor.GRAY + "?");


            }

            return true;

            }

            return false; //end of command boolean
        }


    public static boolean isDungeonAdmin(Player sender) {
        if (!sender.hasPermission("worldciv.dungeonadmin")) {
            sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments. Use" + ChatColor.YELLOW + " /dungeon" + ChatColor.GRAY + ".");
            return false;
        } else {
            return true;
        }
    }
}

