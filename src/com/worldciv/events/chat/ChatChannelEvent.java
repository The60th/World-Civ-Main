package com.worldciv.events.chat;

import com.earth2me.essentials.User;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;

import java.util.*;

import static com.worldciv.the60th.Main.getEssentials;
import static com.worldciv.the60th.Main.getPermissionsEx;
import static com.worldciv.utility.utilityMultimaps.chatchannels;
import static com.worldciv.utility.utilityStrings.worldciv;

public class ChatChannelEvent implements Listener {

    @EventHandler
    public void onChatChannel(AsyncPlayerChatEvent e) {

        String raw_message = e.getMessage();
        Player official_sender = e.getPlayer();


        if (chatchannels.containsValue(official_sender.getName())) { //it should 99.999999% of time. else theres bug

            Multimap<String, String> inversecc = Multimaps.invertFrom(chatchannels, ArrayListMultimap.<String, String>create()); //Inverse to find the channelname
            Collection<String> chat_channel = inversecc.get(official_sender.getName()); //Find the channelname

            String official_chat_channel = "";


            for (String channel_name : chat_channel) {
                official_chat_channel = channel_name;
            }

            switch (official_chat_channel.toLowerCase()) {

                case "local":
                    pushLocalMessage(official_sender, raw_message, e);
                    return;
                case "global":
                    pushGlobalMessage(official_sender, raw_message, e);
                    return;
                case "ooc":
                    pushOOCMessage(official_sender, raw_message, e);
                    return;
                case "mod":
                    pushModMessage(official_sender, raw_message, e);
                    return;
                case "admin":
                    pushAdminMessage(official_sender, raw_message, e);
                    return;
                case "anc":
                    pushANCMessage(official_sender, raw_message, e);
                    return;
                case "nc":
                    pushNCMessage(official_sender, raw_message, e);
                    return;
                case "tc":
                    pushTCMessage(official_sender, raw_message, e);
                    return;

                default:
                    official_sender.sendMessage(worldciv + ChatColor.RED + " Error: We could not find a channel you're in.");
                    return;
            }


        } else {
            official_sender.sendMessage(worldciv + ChatColor.RED + " Error: There was a conflict with chat channels. Please inform staff about this error.");
            e.setCancelled(true);
        }


    }

    public static void pushLocalMessage(Player sender, String rawmessage, AsyncPlayerChatEvent e) {
        /**
         * This is the prefix_local formatting
         */

        String prefix_local = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "RP" + ChatColor.DARK_GRAY + "]"; //PREFIX

        /**
         * This is getting essentials nickname
         */

        String nick = getEssentialsNick(sender); //GET ESSENTIALS NICKNAME

        Collection<? extends Player> online_players = Bukkit.getOnlinePlayers(); //ALL online players

        for (Player online_player : online_players) { //FOR ALL PLAYERS ONLINE
            long radius = Math.round(sender.getLocation().distance(online_player.getLocation())); //GET RANGE BETWEEN SENDER AND RECIPIENT
            if (radius > 70) { //IF NOT NEAR 70
                e.getRecipients().remove(online_player);
            }
        }

        e.setFormat(prefix_local + " " + nick + ChatColor.GRAY + ": " + rawmessage); //send the final msg


        return;
    }

    public static void pushGlobalMessage(Player sender, String rawmessage, AsyncPlayerChatEvent e) { //todo add a custom mute for this channel

        /**
         * Custom mute for GLOBAL ONLY
         */


        /**
         * This is prefix_global formatted.
         */

        String prefix_global = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "G" + ChatColor.DARK_GRAY + "]"; //PREFIX [G]

        /**
         * Get priority group
         */
        PermissionGroup priority_group = getPriorityGroup(sender);


        /**
         * This is official group: formatted_group_prefix
         */

        String formatted_group_prefix = priority_group.getPrefix() + " ";

        if (priority_group.getPrefix().isEmpty()) {
            formatted_group_prefix = "";
        }

        /*
        Time to start building string together
         */

        e.setFormat(prefix_global + "" + formatted_group_prefix + ChatColor.GRAY + sender.getName() + ChatColor.GRAY + ": " + rawmessage);


        return;
    }

    public static void pushNCMessage(Player sender, String rawmessage, AsyncPlayerChatEvent e) { //todo add a custom mute for this channel

        /**
         * This is prefix_global formatted.
         */

        String prefix_NC = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "NC" + ChatColor.DARK_GRAY + "]"; //PREFIX [G]

        /**
         * Get priority group
         */


        if (getTown(sender) == null) {
            sender.sendMessage(worldciv + ChatColor.GRAY + " You are not in a town.");
            e.setCancelled(true);
        }

        if (getNation(sender) == null) {
            sender.sendMessage(worldciv + ChatColor.GRAY + " You are not in a nation.");
            e.setCancelled(true);
        }

        String king_title = getKingTitle(sender); //Will get TITLE first. if no title: Mayor Assistant Resident
        String town_name = getTownName(sender); //get town name

        /**
         * This is official group: formatted_group_prefix
         */

        if (!king_title.isEmpty() && king_title != null) {
            king_title = ChatColor.GOLD + king_title.toUpperCase() + " ";
        }

        /**
         * Get all nation members:
         */

        List<Resident> residents = getNationResidents(sender);

        e.getRecipients().retainAll(residents);


        /*
        Time to start building string together
         */


        e.setFormat(prefix_NC + " " + king_title + town_name + " " + ChatColor.GRAY + sender.getName() + ChatColor.GRAY + ": " + rawmessage);


        return;
    }

    public static void pushANCMessage(Player sender, String rawmessage, AsyncPlayerChatEvent e) { //todo add a custom mute for this channel

        /**
         * This is prefix_global formatted.
         */

        String prefix_NC = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "ANC" + ChatColor.DARK_GRAY + "]";

        /**
         * Get priority group
         */


        if (getTown(sender) == null) {
            sender.sendMessage(worldciv + ChatColor.GRAY + " You are not in a town.");
            e.setCancelled(true);
        }

        if (getNation(sender) == null) {
            sender.sendMessage(worldciv + ChatColor.GRAY + " You are not in a nation.");
            e.setCancelled(true);
        }

        String king_title = getKingTitle(sender); //Will get TITLE first. if no title: Mayor Assistant Resident
        String town_name = getTownName(sender); //get town name

        /**
         * This is official group: formatted_group_prefix
         */

        if (!king_title.isEmpty() && king_title != null) {
            king_title = ChatColor.GOLD + king_title.toUpperCase() + " ";
        }

        /**
         * Get all residents:
         */

        List<Resident> ally_nation_residents = getAllyNationResidents(sender);
        List<Resident> residents = getNationResidents(sender);

        if (ally_nation_residents == residents) {
            sender.sendMessage(worldciv + ChatColor.GRAY + " There are no allies to hear your voice. Are they offline?");
            e.setCancelled(true);
        }

        e.getRecipients().retainAll(ally_nation_residents);


        /*
        Time to start building string together
         */


        e.setFormat(prefix_NC + " " + king_title + town_name + " " + ChatColor.GRAY + sender.getName() + ChatColor.GRAY + ": " + rawmessage);


        return;
    }


    public static void pushTCMessage(Player sender, String rawmessage, AsyncPlayerChatEvent e) { //todo add a custom mute for this channel

        /**
         * This is prefix_global formatted.
         */

        String prefix_global = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "TC" + ChatColor.DARK_GRAY + "]"; //PREFIX [G]

        /**
         * Get priority group
         */


        if (getTown(sender) == null) {
            sender.sendMessage(worldciv + ChatColor.GRAY + " You are not in a town.");
            e.setCancelled(true);
        }

        if (getNation(sender) == null) {
            sender.sendMessage(worldciv + ChatColor.GRAY + " You are not in a nation.");
            e.setCancelled(true);
        }

        String towntitle = getTownTitle(sender); //Will get TITLE first. if no title: Mayor Assistant Resident

        /**
         * This is official group: formatted_group_prefix
         */

        String formatted_town_title = ChatColor.GRAY + towntitle;


        /**
         * Filter
         */

        List<Resident> residents = getTownResidents(sender);
        e.getRecipients().retainAll(residents);

        /*
        Time to start building string together
         */

        e.setFormat(prefix_global + " " + formatted_town_title + " " + ChatColor.GRAY + sender.getName() + ChatColor.GRAY + ": " + rawmessage);


        return;
    }

    public static void pushOOCMessage(Player sender, String rawmessage, AsyncPlayerChatEvent e) { //todo add a mute

        /**
         * This is prefix_OOC formatted.
         */

        String prefix_ooc = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "OOC" + ChatColor.DARK_GRAY + "]"; //PREFIX [G]


        /**
         * This is official group: formatted_group_prefix
         */
        PermissionGroup group = getPriorityGroup(sender);
        String formatted_group_prefix = group.getPrefix() + " ";

        if (group.getPrefix().isEmpty()) {
            formatted_group_prefix = "";
        }

        /*
        Time to start building string together
         */

        Collection<? extends Player> online_players = Bukkit.getOnlinePlayers(); //ALL online players

        for (Player online_player : online_players) { //FOR ALL PLAYERS ONLINE
            long radius = Math.round(sender.getLocation().distance(online_player.getLocation())); //GET RANGE BETWEEN SENDER AND RECIPIENT
            if (radius > 70) { //IF NOT NEAR 70
                e.getRecipients().remove(online_player);
            }
        }

        e.setFormat(prefix_ooc + "" + formatted_group_prefix + ChatColor.GRAY + sender.getName() + ChatColor.GRAY + ": " + rawmessage);

        return;
    }

    public static void pushModMessage(Player sender, String rawmessage, AsyncPlayerChatEvent e) {

        /**
         * This is prefix_global formatted.
         */

        String prefix_mod = ChatColor.DARK_AQUA + "[" + ChatColor.BLUE + "MOD" + ChatColor.DARK_AQUA + "]"; //PREFIX [G]


        /**
         * Get priority group
         */
        PermissionGroup priority_group = getPriorityGroup(sender);


        /**
         * This is official group: formatted_group_prefix
         */

        String formatted_group_prefix = priority_group.getPrefix() + " ";

        if (priority_group.getPrefix().isEmpty()) {
            formatted_group_prefix = "";
        }

        /*
        Time to start building string together
         */

        Collection<? extends Player> online_players = Bukkit.getOnlinePlayers(); //ALL online players

        for (Player online_player : online_players) { //FOR ALL PLAYERS ONLINE
            if (!online_player.hasPermission("worldciv.mod") && !online_player.hasPermission("worldciv.admin")) {
                e.getRecipients().remove(online_player);
            }
        }

        e.setFormat(prefix_mod + "" + formatted_group_prefix + ChatColor.BLUE + sender.getName() + ChatColor.BLUE + ": " + rawmessage);


        return;
    }

    public static void pushAdminMessage(Player sender, String rawmessage, AsyncPlayerChatEvent e) {

        /**
         * This is prefix_global formatted.
         */

        String prefix_admin = ChatColor.RED + "[" + ChatColor.RED + "ADMIN" + ChatColor.RED + "]"; //PREFIX [G]

        /**
         * Get priority group
         */
        PermissionGroup priority_group = getPriorityGroup(sender);

        /**
         * This is official group: formatted_group_prefix
         */

        String formatted_group_prefix = priority_group.getPrefix() + " ";

        if (priority_group.getPrefix().isEmpty()) {
            formatted_group_prefix = "";
        }

        /*
        Time to start building string together
         */

        Collection<? extends Player> online_players = Bukkit.getOnlinePlayers(); //ALL online players

        for (Player online_player : online_players) { //FOR ALL PLAYERS ONLINE
            if (!online_player.hasPermission("worldciv.admin")) {
                e.getRecipients().remove(online_player);
            }
        }

        e.setFormat(prefix_admin + "" + formatted_group_prefix + ChatColor.RED + sender.getName() + ChatColor.RED + ": " + rawmessage);


        return;
    }

    public static PermissionGroup getPriorityGroup(Player player) {
        PermissionUser pexuser = getPermissionsEx().getPermissionsManager().getUser(player); //Grab PEX USER
        Bukkit.broadcastMessage(pexuser.getName());
        List<String> allgroups = pexuser.getParentIdentifiers(); //GRAB ALL GROUPS FROM THE USER

        Bukkit.broadcastMessage(allgroups.toString());

        HashMap<Integer, PermissionGroup> groupdata = new HashMap<>(); //CREATE HASHMAP to STORE INFORMATION

        for (String group_string : allgroups) { //iterating all groups and sorting by numbers
            PermissionGroup group = getPermissionsEx().getPermissionsManager().getGroup(group_string); //grab the group
            int group_weight = group.getWeight(); //get group's priority rank. LOWEST = BEST

            groupdata.put(group_weight, group);
        }

        int lowest = Collections.min(groupdata.keySet());

        PermissionGroup priority_group = groupdata.get(lowest);
        return priority_group;
    }

    public static String getEssentialsNick(Player player) {

        User user = getEssentials().getUserMap().getUser(player.getName());

        String nick = user.getNick(false);

        if (nick.isEmpty() || nick == null) { //if no nickname was found
            nick = "";
        }

        return nick;
    }

    public static List<Resident> getTownResidents(Player player) {
        try {
            Resident resident = TownyUniverse.getDataSource().getResident(player.getName());

            Town town = resident.getTown();

            if (town == null) {
                return null;
            }

            List<Resident> residents = town.getResidents();

            return residents;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getTownTitle(Player player) {
        try {
            Resident resident = TownyUniverse.getDataSource().getResident(player.getName());

            Town town = resident.getTown();

            if (resident.isMayor()) {

                if (resident.hasTitle()) {
                    return resident.getTitle();
                }
                return "Mayor";
            }

            for (Resident assistant : town.getAssistants()) {
                if (assistant == resident) {

                    if (resident.hasTitle()) {
                        return resident.getTitle();
                    }

                    return "Assistant";
                }
            }

            return "Resident";


        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getKingTitle(Player player) {
        try {
            Resident resident = TownyUniverse.getDataSource().getResident(player.getName());

            Nation nation = resident.getTown().getNation();

            String king = "";

            if (nation.isKing(resident)) {
                king = "King";
            }


            return king;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getTownName(Player player) {
        try {
            Resident resident = TownyUniverse.getDataSource().getResident(player.getName());

            Town town = resident.getTown();

            String town_name = town.getTag();
            return town_name;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static Nation getNation(Player player) {
        try {
            Resident resident = TownyUniverse.getDataSource().getResident(player.getName());

            Nation nation = resident.getTown().getNation();

            if (nation == null) {
                return null;
            }
            return nation;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static Town getTown(Player player) {
        try {
            Resident resident = TownyUniverse.getDataSource().getResident(player.getName());

            Town town = resident.getTown();

            if (town == null) {
                return null;
            }

            return town;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }


    public static List<Resident> getNationResidents(Player player) {
        try {
            Resident resident = TownyUniverse.getDataSource().getResident(player.getName());
            Nation nation = resident.getTown().getNation();

            if (nation == null) {
                return null;
            }

            List<Resident> residents = nation.getResidents();

            Bukkit.broadcastMessage("size of residents is:" + residents.size()); //todo remove bc

            return residents;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static List<Resident> getAllyNationResidents(Player player) {
        try {
            Resident resident = TownyUniverse.getDataSource().getResident(player.getName());
            Nation nation = resident.getTown().getNation();

            if (nation == null) {
                return null;
            }

            List<Nation> allynations = nation.getAllies();

            if (allynations.isEmpty()) {
                return null;
            }

            List<Resident> ALL_RESIDENTS = new ArrayList<>();
            List<Resident> player_nation_residents = nation.getResidents();
            ALL_RESIDENTS.addAll(player_nation_residents);

            for (Nation ally_nation : allynations) {
                List<Resident> ally_residents = ally_nation.getResidents();
                ALL_RESIDENTS.addAll(ally_residents);
            }

            return ALL_RESIDENTS;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }


}