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

import java.util.Collections;
import java.util.Set;

import static com.worldciv.the60th.Main.*;
import static com.worldciv.utility.utilityArrays.dungeonregionlist;
import static com.worldciv.utility.utilityStrings.*;

public class DungeonCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender robotsender, Command cmd, String alias, String[] args) {

        Party party = new Party();

        if (!(robotsender instanceof Player)) {
            robotsender.sendMessage(ChatColor.RED + "Silly console! You can't join dungeons, that's unfair!");
            return true;
        }

        Player sender = (Player) robotsender;


        if (cmd.getName().equalsIgnoreCase("dungeon") || cmd.getName().equalsIgnoreCase("dg")) {
            if (args.length == 0) {

                if (true) { //displays available dungeons?

                    return true;
                } else { //if youre in a dungeon atm. show stats

                    return true;
                }
            }

            switch (args[0]) {
                case "create":

                    if (!isStaff(sender)) {
                        return true;
                    }

                    if (args.length == 2) { //If /dg create <id>

                        Selection sel = getWorldEdit().getSelection(sender);

                        if (sel == null) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " You must make a selection before creating a dungeon. Use " + ChatColor.YELLOW + "//wand.");
                            return true;
                        }

                        for (Object regionname : getWorldGuard().getRegionManager(sender.getWorld()).getRegions().keySet().toArray()) {

                            if (regionname.toString().equalsIgnoreCase(args[1])) {
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

                        getWorldGuard().getRegionManager(sender.getWorld()).addRegion(region);
                        getWorldGuard().getRegionManager(sender.getWorld()).getRegion(region.getId()).setFlag(dungeon_region, StateFlag.State.ALLOW);

                        sender.sendMessage(worldciv + ChatColor.GRAY + " The dungeon region " + ChatColor.YELLOW + "'" + args[1] + "'" + ChatColor.GRAY + " has been created! Be sure to be inside this region when editing!");


                        return true;

                    }
                    sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments! Use " + ChatColor.YELLOW + "/dg create <id>");
                    return true;
                case "list": //LISTS ALL DUNGEON REGIONS

                    if (!isStaff(sender)) {
                        return true;
                    }

                    if (args.length == 1) {


                        for (Object regionname : getWorldGuard().getRegionManager(sender.getWorld()).getRegions().keySet().toArray()) { //CHANGE REGION WORLDS

                            if (getWorldGuard().getRegionManager(sender.getWorld()).getRegion(regionname.toString()).getFlag(dungeon_region) == StateFlag.State.ALLOW) {

                                if (!dungeonregionlist.contains(regionname.toString())) {
                                    dungeonregionlist.add(regionname.toString());

                                }
                            }
                        }

                        Collections.sort(dungeonregionlist);

                        sender.sendMessage(worldciv + ChatColor.GRAY + " Dungeon Regions: " + ChatColor.YELLOW + dungeonregionlist);

                    }
                    sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments! Use " + ChatColor.YELLOW + "/dg list");
                    return true;
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

                        if (getWorldGuard().getRegionManager(sender.getWorld()).getRegion(args[1]).getFlag(dungeon_region) != StateFlag.State.ALLOW) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " You must provide a valid dungeon region id.");
                            return true;
                        }

                        if(!args[2].matches(".*\\d.*")){
                            sender.sendMessage(worldciv + ChatColor.GRAY + " You must provide a numerical value as a difficulty.");
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


                            Dungeon dungeon = new Dungeon(party, args[1], Integer.parseInt(args[2]));

                            sender.sendMessage(mainbot);
                            return true;
                        }

                        Bukkit.broadcastMessage("all players are currently grouped up!");
                        return true;
                    }
                    sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments! Use " + ChatColor.YELLOW + "/dg join <dungeon-id> <difficulty>");
                    return true;


                case "mobspawn":

                    if (!isStaff(sender)) {
                        return true;
                    }

                    String dungeonregionname = "";

                    for (ProtectedRegion r : getWorldGuard().getRegionManager(sender.getWorld()).getApplicableRegions(sender.getLocation()).getRegions()) {
                        for (Object regionname : getWorldGuard().getRegionManager(sender.getWorld()).getRegions().keySet().toArray()) { //CHANGE REGION WORLDS

                            if (getWorldGuard().getRegionManager(sender.getWorld()).getRegion(regionname.toString()).getFlag(dungeon_region) == StateFlag.State.ALLOW)

                                if (regionname.toString() == r.getId()) {
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

                    sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments. Use" + ChatColor.YELLOW + " /dg mobspawn <mob-id>" + ChatColor.GRAY + ".");
                    return true;

                case "admin": //HELP PAGE FOR ADMINS

                    if (!isStaff(sender)) {
                        return true;
                    }

                    sender.sendMessage(maintop);
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon create <id>" + ChatColor.GRAY + ": Create a dungeon.");
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon list" + ChatColor.GRAY + ": Displays all dungeons.");
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon remove <id>" + ChatColor.GRAY + ": Remove a dungeon.");
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon setspawn" + ChatColor.GRAY + ": Player spawnpoint.");
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon mobspawn <mob-id>" + ChatColor.GRAY + ": Hostile mobspawnpoints.");
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon cp <cp-id>" + ChatColor.GRAY + ": Set dungeon checkpoints.");
                    /*sender.sendMessage(ChatColor.YELLOW + "/party accept <player>" + ChatColor.GRAY + ": Join a dungeon-raiding party.");
                    sender.sendMessage(ChatColor.YELLOW + "/party deny <player>" + ChatColor.GRAY + ": Refuse to join a feeble party.");
                    sender.sendMessage(ChatColor.YELLOW + "/party block <player>" + ChatColor.GRAY + ": Block/Unblock invitations from players.");
                    sender.sendMessage(ChatColor.YELLOW + "/party help" + ChatColor.GRAY + ": Displays this help page."); */
                    sender.sendMessage(mainbot);

                    return true;

            }


            sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments. Use" + ChatColor.YELLOW + " /dungeon" + ChatColor.GRAY + ".");
            return true;

        }

        return false; //end of command boolean
    }

    public boolean isStaff(Player sender) {
        if (!sender.hasPermission("worldciv.dungeonadmin")) {
            sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments. Use" + ChatColor.YELLOW + " /dungeon" + ChatColor.GRAY + ".");
            return false;
        } else {
            return true;
        }
    }
}

