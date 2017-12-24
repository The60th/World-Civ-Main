package com.worldciv.events.chat;

import com.earth2me.essentials.User;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import com.worldciv.commands.ChatCommand;
import com.worldciv.utility.Fanciful.mkremins.fanciful.FancyMessage;
import jdk.nashorn.internal.parser.JSONParser;
import net.md_5.bungee.api.chat.TextComponent;
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
import static com.worldciv.utility.utilityArrays.*;
import static com.worldciv.utility.utilityMultimaps.chatchannels;
import static com.worldciv.utility.utilityStrings.worldciv;

@SuppressWarnings("all")
public class ChatChannelEvent implements Listener {

    @EventHandler
    public void onChatChannel(AsyncPlayerChatEvent e) {

        String raw_message = e.getMessage();
        Player official_sender = e.getPlayer();

        if (globalMute.contains(official_sender)) {
            official_sender.sendMessage(worldciv + ChatColor.RED + " You are server muted.");
            e.setCancelled(true);
        }

        String[] args = e.getMessage().split(" ");
        raw_message = ChatCommand.getMessage(args);

        if (isStaffOrColorChat(official_sender)) {
            raw_message = ChatColor.translateAlternateColorCodes("&".charAt(0), raw_message);
        }


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

        String prefix_local = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "RP" + ChatColor.DARK_GRAY + "]"; //The prefix that is already formatted

        /**
         * This is getting essentials nickname
         */

        String rawnick = getEssentialsNick(sender); //Get raw essentials nick.
        String nick = ChatColor.translateAlternateColorCodes("&".charAt(0), rawnick); //Get essentials with chat color codes translated

        Collection<? extends Player> online_players = Bukkit.getOnlinePlayers(); //ALL online players

        for (Player online_player : online_players) { //FOR ALL PLAYERS ONLINE
            long radius = Math.round(sender.getLocation().distance(online_player.getLocation())); //GET RANGE BETWEEN SENDER AND RECIPIENT
            if (radius > 50) { //IF NOT NEAR 50
                e.getRecipients().remove(online_player);
            }
        }

        for (Player receiver : e.getRecipients()) {

        }

        String Fprefix = getFancyChannelPrefix(prefix_local, sender);


        e.setFormat( Fprefix + " " + nick + ChatColor.GRAY + ": " + rawmessage); //send the final msg


        return;
    }

    public static void pushGlobalMessage(Player sender, String rawmessage, AsyncPlayerChatEvent e) { //todo add a custom mute for this channel

        /**
         * Custom mute for GLOBAL ONLY
         */


        /**
         * This is prefix_global formatted.
         */

        String prefix_global = ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "G" + ChatColor.DARK_GRAY + "]";
        //[G]

        /**
         * Get priority group
         */
        PermissionGroup priority_group = getPriorityGroup(sender);
        //Get best rank from PEX

        /**
         * This is official group: formatted_group_prefix
         */

        String raw_group_prefix = priority_group.getPrefix() + " ";
        String formatted_group_prefix = ChatColor.translateAlternateColorCodes("&".charAt(0), raw_group_prefix);
        //Translate GroupPrefix to a ChatColor.

        if (priority_group.getPrefix().isEmpty()) {
            formatted_group_prefix = " ";
        }

        /**
         * Check if in town and nation
         */

        String nation_name;
        if (getNation(sender) == null) {
            nation_name = "";

        } else {
            nation_name = getNationName(sender);
            nation_name = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + nation_name + ChatColor.DARK_GRAY + "]"; //Make it colored
        }


        /**
         * Build string together
         */

        e.setFormat(prefix_global + nation_name + formatted_group_prefix + ChatColor.GRAY + sender.getName() + ChatColor.GRAY + ": " + ChatColor.GRAY + rawmessage);


        return;
    }

    public static void pushNCMessage(Player sender, String rawmessage, AsyncPlayerChatEvent e) { //todo add a custom mute for this channel


        if (townyMute.contains(sender)) {
            sender.sendMessage(worldciv + ChatColor.RED + " You are towny-chat muted.");
            e.setCancelled(true);
            return;
        }

        // [NC] townName userName

        /**
         * This is Nation prefix formatted.
         */

        String prefix_NC = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "NC" + ChatColor.DARK_GRAY + "]"; //PREFIX [NC]

        /**
         * Check if in town and nation
         */


        if (getTown(sender) == null) {
            sender.sendMessage(worldciv + ChatColor.GRAY + " You are not in a town.");
            e.setCancelled(true);
        }

        if (getNation(sender) == null) {
            sender.sendMessage(worldciv + ChatColor.GRAY + " You are not in a nation.");
            e.setCancelled(true);
        }

        /**
         * Check if king.
         */

        String raw_king_title = getKingTitle(sender); //Will get a string "King" if king. If not king, ""

        String town_name = getTownName(sender); //Get town id.

        TextComponent msg = new TextComponent(town_name);
        String json = "{text:\"Visit our website: \",extra:[{text:\"*click*\",color:orange,clickEvent:{action:open_url,value:\"www.bukkit.org\"}}]}";

        sender.sendMessage(JSONParser.quote(json));


        String formatted_town_name = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + town_name + ChatColor.DARK_GRAY + "]"; //Make it colored


        if (!raw_king_title.isEmpty() && raw_king_title != null) {
            raw_king_title = ChatColor.GRAY + raw_king_title + " ";
        }

        /**
         * Get all nation members:
         */

        List<Resident> residents = getNationResidents(sender);

        for (Player player : Bukkit.getOnlinePlayers()) { //For all online players
            if (!residents.contains(getResident(player)) && !togglesocialspy.contains(player)) { //If you're not part of nation residents
                e.getRecipients().remove(player); //Remove player from being a part of it!
            }
        }

        /**
         * String Builder
         */

        e.setFormat(prefix_NC + formatted_town_name + " " + raw_king_title + ChatColor.GRAY + sender.getName() + ChatColor.GRAY + ": " + ChatColor.GOLD + rawmessage);

        return;
    }

    public static void pushANCMessage(Player sender, String rawmessage, AsyncPlayerChatEvent e) { //todo add a custom mute for this channel

        if (townyMute.contains(sender)) {
            sender.sendMessage(worldciv + ChatColor.RED + " You are towny-chat muted.");
            e.setCancelled(true);
            return;
        }

        /**
         * This is prefix_global formatted.
         */

        String prefix_NC = ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "ANC" + ChatColor.DARK_GRAY + "]";

        /**
         * Null check town and nation
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
        String nation_name = getNationName(sender); //get nation name

        String formatted_nation_name = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + nation_name + ChatColor.DARK_GRAY + "]"; //Make it colored

        String town_rank = "";

        /**
         * THE KING!
         */

        if (!king_title.isEmpty() && king_title != null) { //if king is present
            king_title = ChatColor.GRAY + king_title + " "; //make formatting
        } else {
            String poss_town_rank = getTownRank(sender);
            if (!poss_town_rank.equalsIgnoreCase("Resident") && poss_town_rank != null) {
                town_rank = poss_town_rank + " ";
            }
        }

        /**
         * Get all residents:
         */

        List<Resident> ally_nation_residents = getAllyNationResidents(sender);

      /*  List<Resident> residents = getNationResidents(sender);

        if (ally_nation_residents.toString().equalsIgnoreCase(residents.toString())) {
            sender.sendMessage(worldciv + ChatColor.GRAY + " There are no allies to hear your voice.");
            e.setCancelled(true);
        } */

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!ally_nation_residents.contains(getResident(player)) && !togglesocialspy.contains(player)) {
                e.getRecipients().remove(player);
            }
        }

        /**
         * String builder
         */


        e.setFormat(prefix_NC + formatted_nation_name + " " + king_title + town_rank + ChatColor.GRAY + sender.getName() + ChatColor.GRAY + ": " + ChatColor.YELLOW + rawmessage);


        return;
    }


    public static void pushTCMessage(Player sender, String rawmessage, AsyncPlayerChatEvent e) { //todo add a custom mute for this channel


        if (townyMute.contains(sender)) {
            sender.sendMessage(worldciv + ChatColor.RED + " You are towny-chat muted.");
            e.setCancelled(true);
            return;
        }

        /**
         * This is prefix_global formatted.
         */

        String prefix_global = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "TC" + ChatColor.DARK_GRAY + "]"; //PREFIX [G]

        /**
         * Check if Player is in a town.
         */

        if (getTown(sender) == null) {
            sender.sendMessage(worldciv + ChatColor.GRAY + " You are not in a town.");
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

        List<Resident> residents = getTownResidents(sender); //Get all town residents

        for (Player player : Bukkit.getOnlinePlayers()) { //All online players
            if (!residents.contains(getResident(player)) && !togglesocialspy.contains(player)) { //If online player is not town resident
                e.getRecipients().remove(player); //remove from being a recipient
            }
        }

        /**
         * String Building
         */

        e.setFormat(prefix_global + " " + formatted_town_title + " " + ChatColor.GRAY + sender.getName() + ChatColor.GRAY + ": " + ChatColor.DARK_AQUA + rawmessage);


        return;
    }

    public static void pushOOCMessage(Player sender, String rawmessage, AsyncPlayerChatEvent e) { //todo add a mute

        /**
         * This is prefix_OOC formatted.
         */

        String prefix_ooc = ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "OOC" + ChatColor.DARK_GRAY + "]"; //PREFIX [G]


        /**
         * This is official group: formatted_group_prefix
         */
        PermissionGroup group = getPriorityGroup(sender);

        String raw_formatted_group_prefix = group.getPrefix() + " ";


        if (group.getPrefix().isEmpty()) {
            raw_formatted_group_prefix = "";
        }

        String formatted_group_prefix = ChatColor.translateAlternateColorCodes("&".charAt(0), raw_formatted_group_prefix);

        /*
        Time to start building string together
         */

        Collection<? extends Player> online_players = Bukkit.getOnlinePlayers(); //ALL online players

        for (Player online_player : online_players) { //FOR ALL PLAYERS ONLINE
            long radius = Math.round(sender.getLocation().distance(online_player.getLocation())); //GET RANGE BETWEEN SENDER AND RECIPIENT
            if (radius > 50 && !togglesocialspy.contains(online_player)) { //IF NOT NEAR 50
                e.getRecipients().remove(online_player);
            }
        }

        e.setFormat(prefix_ooc + "" + formatted_group_prefix + ChatColor.GRAY + sender.getName() + ChatColor.GRAY + ": " + rawmessage);

        return;
    }

    public static void pushModMessage(Player sender, String rawmessage, AsyncPlayerChatEvent e) {

        if (!sender.hasPermission("worldciv.mod") && !sender.hasPermission("worldciv.admin")) {
            sender.sendMessage(worldciv + ChatColor.GRAY + " You have no access to chat here.");
            e.setCancelled(true);
        }

        /**
         * This is prefix_global formatted.
         */

        String prefix_mod = ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "MOD" + ChatColor.DARK_GRAY + "]"; //PREFIX [G]


        /**
         * Get priority group
         */
        PermissionGroup priority_group = getPriorityGroup(sender);


        /**
         * This is official group: formatted_group_prefix
         */

        String formatted_group_prefix = ChatColor.translateAlternateColorCodes("&".charAt(0), priority_group.getPrefix() + " ");

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

        e.setFormat(prefix_mod + "" + formatted_group_prefix + ChatColor.GRAY + sender.getName() + ChatColor.GRAY + ": " + ChatColor.BLUE + rawmessage);


        return;
    }

    public static void pushAdminMessage(Player sender, String rawmessage, AsyncPlayerChatEvent e) {

        if (!sender.hasPermission("worldciv.admin")) {
            sender.sendMessage(worldciv + ChatColor.GRAY + " You have no access to chat here.");
            e.setCancelled(true);
        }

        /**
         * This is prefix_global formatted.
         */

        String prefix_admin = ChatColor.DARK_GRAY + "[" + ChatColor.RED + "ADMIN" + ChatColor.DARK_GRAY + "]"; //PREFIX [G]

        /**
         * Get priority group
         */
        PermissionGroup priority_group = getPriorityGroup(sender);

        /**
         * This is official group: formatted_group_prefix
         */

        String formatted_group_prefix = ChatColor.translateAlternateColorCodes("&".charAt(0), priority_group.getPrefix() + " ");

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

        e.setFormat(prefix_admin + "" + formatted_group_prefix + ChatColor.GRAY + sender.getName() + ": " + ChatColor.RED + rawmessage);


        return;
    }

    public static PermissionGroup getPriorityGroup(Player player) {
        PermissionUser pexuser = getPermissionsEx().getPermissionsManager().getUser(player); //Grab PEX USER

        List<String> allgroups = pexuser.getParentIdentifiers(); //GRAB ALL GROUPS FROM THE USER

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
                    String fixed_title = resident.getTitle().substring(0, resident.getTitle().length() - 1);
                    return fixed_title;
                }
                return "Mayor";
            }

            for (Resident assistant : town.getAssistants()) {
                if (assistant == resident) {

                    if (resident.hasTitle()) {
                        String fixed_title = resident.getTitle().substring(0, resident.getTitle().length() - 1);
                        return fixed_title;
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


    public static boolean isTownStaff(Player player) {
        try {
            Resident resident = TownyUniverse.getDataSource().getResident(player.getName());

            Town town = resident.getTown();

            if (resident.isMayor()) {
                return true;
            }

            for (Resident assistant : town.getAssistants()) {
                if (assistant == resident) {
                    return true;
                }
            }

            return false;


        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static String getTownRank(Player player) {
        try {
            Resident resident = TownyUniverse.getDataSource().getResident(player.getName());

            Town town = resident.getTown();

            if (resident.isMayor()) {
                return "Mayor";
            }

            for (Resident assistant : town.getAssistants()) {
                if (assistant == resident) {
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
        }
        return null;
    }

    public static String getTownName(Player player) {
        try {
            Resident resident = TownyUniverse.getDataSource().getResident(player.getName());

            Town town = resident.getTown();

            String town_name = town.getName();
            return town_name;

        } catch (Exception exception) {
        }
        return null;
    }

    public static String getNationName(Player player) {
        try {
            Resident resident = TownyUniverse.getDataSource().getResident(player.getName());

            String nation = resident.getTown().getNation().getName();

            return nation;

        } catch (Exception exception) {
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

            return residents;

        } catch (Exception exception) {
        }
        return null;
    }

    public static Resident getResident(Player player) {
        try {
            Resident resident = TownyUniverse.getDataSource().getResident(player.getName());
            return resident;
        } catch (Exception e) {
            return null;
        }


    }

    public static List<Resident> getAllyNationResidents(Player player) {
        try {
            Resident resident = TownyUniverse.getDataSource().getResident(player.getName());
            Nation nation = resident.getTown().getNation();

            if (nation == null) {
                return null;
            }

            List<Nation> allynations = nation.getAllies();

            List<Resident> ALL_RESIDENTS = new ArrayList<>();
            List<Resident> player_nation_residents = nation.getResidents();
            ALL_RESIDENTS.addAll(player_nation_residents);

            for (Nation ally_nation : allynations) {
                List<Resident> ally_residents = ally_nation.getResidents();
                ALL_RESIDENTS.addAll(ally_residents);
            }

            return ALL_RESIDENTS;

        } catch (Exception exception) {

        }
        return null;
    }

    public boolean isStaffOrColorChat(Player player) {
        if (player.hasPermission("worldciv.mod") || player.hasPermission("worldciv.admin") || player.hasPermission("worldciv.chatcolor")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isStaffOrSocialSpy(Player player) {
        if (player.hasPermission("worldciv.mod") || player.hasPermission("worldciv.admin") || player.hasPermission("worldciv.socialspy")) {
            return true;
        } else {
            return false;
        }

    }

    public static String getChannel(Player player) {
        Multimap<String, String> inversecc = Multimaps.invertFrom(chatchannels, ArrayListMultimap.<String, String>create()); //Inverse to find the channelname
        Collection<String> chat_channel = inversecc.get(player.getName()); //Find the channelname

        String official_chat_channel = "";


        for (String channel_name : chat_channel) {
            official_chat_channel = channel_name;
        }

        if(!chatchannels.containsKey(official_chat_channel)) {
            return null;
        }

        return official_chat_channel;
    }

    public static String getFancyChannelPrefix(String channel_prefix, Player player) {

     String channelname = getChannel(player);

     return new FancyMessage(channel_prefix).link("https://youtu.be/qAgPH1CWiAw").tooltip("I hope this link works.").toJSONString();

    }


}