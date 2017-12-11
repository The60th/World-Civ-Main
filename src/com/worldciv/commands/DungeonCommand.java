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

import java.util.List;
import java.util.Set;

import static com.worldciv.the60th.Main.*;
import static com.worldciv.utility.utilityArrays.joiningdungeon;
import static com.worldciv.utility.utilityArrays.setnewsmessage;
import static com.worldciv.utility.utilityMultimaps.partyid;
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

                if (getDungeonManager.getDungeon(sender) == null) {
                    sender.sendMessage(maintop);
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon join <dungeon-id> <#difficulty>" + ChatColor.GRAY + ": Join a dungeon.");
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon list" + ChatColor.GRAY + ": Displays all dungeon's status.");
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon leave" + ChatColor.GRAY + ": Leave a dungeon as group.");
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon cancel" + ChatColor.GRAY + ": Cancel pending dungeon joining status.");
                    sender.sendMessage(ChatColor.YELLOW + "/dungeon stats" + ChatColor.GRAY + ": Statistics for current dungeon.");

                    sender.sendMessage(mainbot);
                } else {
                    //if in dungeon show wot current dungeon ur in
                }
            }

                switch (args[0]) {
                    case "create": //This command is for staff use only and creates a dungeon when a //wand selection is used.

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
                    case "list": //This command will list available dungeons and information about them.

                        if (!isStaff(sender)) {
                            return true;
                        }

                        if (args.length == 1) {

                            sender.sendMessage(worldciv + ChatColor.GRAY + " Dungeons: ");

                            if (getDungeonManager.getAllDungeons() == null) {
                                sender.sendMessage(worldciv + ChatColor.GRAY + " There are no established dungeons on this server.");
                                return true;
                            }

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
                                    party.size(sender) + "'" + ChatColor.GOLD + " in " + ChatColor.RED + "15" + ChatColor.GOLD + " seconds.";

                            String messagehowtocancel = ChatColor.RED + "To cancel, leave your party with " + ChatColor.YELLOW + "/party leave" +
                                    ChatColor.RED + " or the leader can use command" + ChatColor.YELLOW + " /dg cancel" +
                                    ChatColor.RED + " or " + ChatColor.YELLOW + "/dg c " + ChatColor.RED + "to stop the dungeon joining process.";

                            party.sendToParty(sender, messagetoparty);
                            party.sendToParty(sender, messagehowtocancel);
                            joiningdungeon.add(sender);

                            new BukkitRunnable() {
                                int x = 0;

                                public void run() {

                                    if (!joiningdungeon.contains(sender)) {
                                        cancel();
                                        return;
                                    }


                                    if (x == 15) {
                                        Dungeon dungeon = new Dungeon(party.getPartyID(sender), args[1], Integer.parseInt(args[2]));
                                        getDungeonManager.addDungeon(dungeon);
                                        cancel();
                                        return;
                                    }
                                    x++;
                                }
                            }.runTaskTimer(plugin, 0, 20);
                            return true;
                        }
                        sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments! Use " + ChatColor.YELLOW + "/dg join <dungeon-id> <difficulty>");
                        return true;
                    case "c":
                    case "cancel":

                        if (args.length == 1) {

                            //you cant cancel a processed dungeon. you can leave as group with /dg leave

                            if (!party.hasParty(sender)) {
                                sender.sendMessage(worldciv + ChatColor.GRAY + " You must be in a party to cancel.");
                                return true;
                            }

                            if (!party.isLeader(sender)) {
                                sender.sendMessage(worldciv + ChatColor.GRAY + " You must be the leader of the party to cancel.");

                            }

                            if (getDungeonManager.getDungeon(sender) != null) {
                                sender.sendMessage(worldciv + ChatColor.GRAY + " You already joined the dungeon. Use" + ChatColor.YELLOW + " /dg leave" + ChatColor.GRAY +
                                        " to leave the party as a group.");
                                return true;

                            }

                            if (!joiningdungeon.contains(sender)) {
                                sender.sendMessage(worldciv + ChatColor.GRAY + " You must be in pending status to cancel a processing dungeon.");
                                return true;
                            }

                            String msg = worldciv + ChatColor.GRAY + " The leader has cancelled joining the dungeon.";

                            party.sendToParty(sender, msg);
                            joiningdungeon.remove(sender);
                            return true;
                        }
                        sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments! Use " + ChatColor.YELLOW + "/dg cancel");
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


                //  sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments. Use" + ChatColor.YELLOW + " /dungeon" + ChatColor.GRAY + ".");
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

