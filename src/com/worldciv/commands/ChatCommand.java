package com.worldciv.commands;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.worldciv.events.chat.ChatChannelEvent.pushLocalMessage;
import static com.worldciv.utility.utilityMultimaps.chatchannels;
import static com.worldciv.utility.utilityStrings.*;

public class ChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {


        if (cmd.getName().equalsIgnoreCase("channels") || cmd.getName().equalsIgnoreCase("ch")) {

            if (args.length == 0) {
                sender.sendMessage(maintop);

                sender.sendMessage(ChatColor.GRAY + "Global:");
                sender.sendMessage(ChatColor.YELLOW + "/g" + ChatColor.GRAY + ": [OOC] Join global chat.");
                sender.sendMessage(ChatColor.YELLOW + "/tc" + ChatColor.GRAY + ": [TC] Join town chat.");
                sender.sendMessage(ChatColor.YELLOW + "/nc" + ChatColor.GRAY + ": [NC] Join nation chat.");
                sender.sendMessage(ChatColor.YELLOW + "/anc" + ChatColor.GRAY + ": [ANC] Join ally-nation chat. A   Q2`1");
                sender.sendMessage(ChatColor.GRAY + "Must be 70 blocks or closer:");
                sender.sendMessage(ChatColor.YELLOW + "/l" + ChatColor.GRAY + ": [RP] Join local chat.");
                sender.sendMessage(ChatColor.YELLOW + "/ooc" + ChatColor.GRAY + ": [OOC] Join OOC chat.");


                if (sender.hasPermission("worldciv.mod") || sender.hasPermission("worldciv.admin")) {
                    sender.sendMessage(ChatColor.RED + "Only moderators+ can see the following:");
                    sender.sendMessage(ChatColor.YELLOW + "/mod" + ChatColor.GRAY + ": [MOD] Join moderator chat.");
                }

                if (sender.hasPermission("worldciv.admin")) {
                    sender.sendMessage(ChatColor.RED + "Only admins can see the following:");
                    sender.sendMessage(ChatColor.YELLOW + "/admin" + ChatColor.GRAY + ": [ADMIN] Join admin chat.");

                }
                sender.sendMessage(mainbot);
                return true;
            }
        } else if (cmd.getName().equalsIgnoreCase("l") || cmd.getName().equalsIgnoreCase("local")) {

            if (args.length == 0) {

                executeChatTransfer(sender, "local");
                return true;
            }

           //ush pushLocalMessage(sender, getMessage(args), )
            return true;

        } else if (cmd.getName().equalsIgnoreCase("ooc")) {

            if (args.length == 0) {
                executeChatTransfer(sender, "ooc");
                return true;
            }

            sender.sendMessage(worldciv + ChatColor.GRAY + " Did you mean to use " + ChatColor.YELLOW + "/ooc" + ChatColor.GRAY + " to join the out-of-character chat?");
            return true;

        } else if (cmd.getName().equalsIgnoreCase("g") || cmd.getName().equalsIgnoreCase("global")) {

            if (args.length == 0) {
                executeChatTransfer(sender, "global");
                return true;
            }

            sender.sendMessage(worldciv + ChatColor.GRAY + " Did you mean to use " + ChatColor.YELLOW + "/global" + ChatColor.GRAY + " to join the global chat?");
            return true;

        } else if (cmd.getName().equalsIgnoreCase("tc")) {

            if (args.length == 0) {
                executeChatTransfer(sender, "tc");
                return true;
            }

            sender.sendMessage(worldciv + ChatColor.GRAY + " Did you mean to use " + ChatColor.YELLOW + "/tc" + ChatColor.GRAY + " to join the town chat?");
            return true;

        } else if (cmd.getName().equalsIgnoreCase("nc")) {

            if (args.length == 0) {
                executeChatTransfer(sender, "nc");
                return true;
            }
            sender.sendMessage(worldciv + ChatColor.GRAY + " Did you mean to use " + ChatColor.YELLOW + "/nc" + ChatColor.GRAY + " to join the nation chat?");
            return true;

        } else if (cmd.getName().equalsIgnoreCase("anc")) {

            if (args.length == 0) {
                executeChatTransfer(sender, "anc");
                return true;
            }
            sender.sendMessage(worldciv + ChatColor.GRAY + " Did you mean to use " + ChatColor.YELLOW + "/anc" + ChatColor.GRAY + " to join the ally-nation chat?");
            return true;

        } else if (cmd.getName().equalsIgnoreCase("mod")) {

            if (!sender.hasPermission("worldciv.mod") && !sender.hasPermission("worldciv.admin")) { //not mod
                sender.sendMessage(worldciv + ChatColor.RED + " You have no permission to access this chat channel. Permission node: worldciv.mod OR worldciv.admin");
                return true;
            }

            if (args.length == 0) {
                executeChatTransfer(sender, "mod");
                return true;
            }
            sender.sendMessage(worldciv + ChatColor.GRAY + " Did you mean to use " + ChatColor.YELLOW + "/mod" + ChatColor.GRAY + " to join the moderator chat?");
            return true;
        } else if (cmd.getName().equalsIgnoreCase("admin")) {
            if (!sender.hasPermission("worldciv.admin")) {
                sender.sendMessage(worldciv + ChatColor.RED + " You have no permission to access this chat channel. Permission node: worldciv.admin");
                return true;
            }

            if (args.length == 0) {
                executeChatTransfer(sender, "admin");
                return true;
            }

            sender.sendMessage(worldciv + ChatColor.GRAY + " Did you mean to use " + ChatColor.YELLOW + "/admin" + ChatColor.GRAY + " to join the admin chat?");
            return true;

        }

        return true;
    }

    public String getMessage(String[] args) {

        List<String> listargs = Arrays.asList(args);
        int index = 0;

        String finalmessage = "";

        for (String argument : listargs) {

            if (index == 0) {
                finalmessage += argument;
            } else {
                finalmessage += " " + argument;
            }
            index++;
        }

        return finalmessage;

    }

    public void executeChatTransfer(CommandSender sender, String channelname) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(worldciv + ChatColor.RED + " You can't join chat channels as console. It is HIGHLY planned to use logs to record messages in the future.");
            return;
        }


        boolean found_player = false; //Found the player in the same channel!

        for (String all_players_in_chat : chatchannels.get(channelname)) { //For all players in the parameter's channel

            if (all_players_in_chat.equalsIgnoreCase(sender.getName())) { //If the player matches the sender.
                found_player = true; //We found the player.
            }

        }

        if (found_player) { //If player is found!
            sender.sendMessage(worldciv + ChatColor.GRAY + " You are already in the " + ChatColor.YELLOW + channelname + ChatColor.GRAY + " chat.");
            return; //Don't do anything! He's already in this parameter's chat.
        }

        //Condition already met: Not already in the chat mode.

        if (chatchannels.containsValue(sender.getName())) { //If chat channel finds a player in a chat.
            Multimap<String, String> inversecc = Multimaps.invertFrom(chatchannels, ArrayListMultimap.<String, String>create()); //Inverse to find the channelname
            Collection<String> chat_channel = inversecc.get(sender.getName()); //Find the channelname

            for (String channel : chat_channel) { //Filtering! :)
                chatchannels.remove(channel, sender.getName()); //Remove player from previous channel name
                Bukkit.broadcastMessage("I have removed " + sender.getName() + "from " + channel); //TODO remove this when debug is complete! :D
            }
        }

        //Conditions already met: Not already in chat mode && removed from (if any) chat channel (which should be removed.. always going to be in one.)

        chatchannels.put(channelname, sender.getName());
        sender.sendMessage(worldciv + ChatColor.GRAY + " You have joined the " + ChatColor.YELLOW + channelname + ChatColor.GRAY + " chat.");
        return;
    }

}