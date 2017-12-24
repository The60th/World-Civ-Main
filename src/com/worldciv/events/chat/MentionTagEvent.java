package com.worldciv.events.chat;

import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.StringUtil;

import java.util.*;

import static com.worldciv.the60th.Main.plugin;

public class MentionTagEvent implements Listener {



    @EventHandler
    public void onNameCall(AsyncPlayerChatEvent e) {

        HashMap<String, Integer> players = new HashMap<String, Integer>();

        Player official_sender =  e.getPlayer();

        String[] args = e.getMessage().split(" "); //create args

        int iteration = 0; //iteration

        if (!e.getMessage().contains("@")) { //lets prevent the checking process, reduce lag
            e.setCancelled(false);
            return;
        }



        for (String argument : args) { //one fragment of args
            if (argument.startsWith("@")) { //if its starts with @
                String possible_player_name = argument.replaceAll("[^A-Za-z0-9]", ""); //createds an alphanumerical name && make it a playername

                if ((Bukkit.getPlayer(possible_player_name) != null) || possible_player_name.equalsIgnoreCase("all")){ //check its a valid name

                    players.put(possible_player_name, iteration);

                } else {
                    //its null
                }
                //starts with @
            }

            iteration++;
            //for arg
        }

        if (players.isEmpty()) { //no player found
            return;
        }

    //KEEP IN MIND PLAYERS HAS PLAYERS IN IT

        for (Player onlineplayer : Bukkit.getOnlinePlayers()) { //iterate all players. send appropiate alert.


            if(players.containsKey("all")){

                Integer index = players.get("all");

                String msg = e.getMessage();

                String not_alphanumeral = args[index].replaceAll("[A-Za-z0-9@]", ""); //creates a non- alphanumerical name. IE: !.#$.. The punctuation in the end.

                String official_not_alphanumerical = ChatColor.GRAY + not_alphanumeral;


                String alphanumerical = args[index].replaceAll("[^A-Za-z0-9@]", ""); //We are filtering OUT the non-alphanumerical. ABC 0-9 stays whitelisted.

                //Alphanumerical should now be: @PLAYER
                //official_not_alphanumerical should now be: the punctuation at the end of the @PLAYER. Like: @PLAYER. or @PLAYER!

                String playername = ChatColor.GOLD + "" + ChatColor.BOLD + alphanumerical + official_not_alphanumerical +  ChatColor.GRAY;


                String official_msg = msg.replace(args[index], playername);

                String finalmessage = String.format(e.getFormat(), official_sender.getDisplayName(), official_msg);

                onlineplayer.playSound(onlineplayer.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5 , 1);

                executeMessage(onlineplayer, official_sender, finalmessage, e);


            }


            else if (!players.containsKey(onlineplayer.getName())){

                String finalmessage = String.format(e.getFormat(), official_sender.getDisplayName(), e.getMessage());

                executeMessage(onlineplayer, official_sender, finalmessage, e);

            }


            else { //it contains player in the hashmap


                Integer index = players.get(onlineplayer.getName()); //get index

                String msg = e.getMessage();

                String not_alphanumeral = args[index].replaceAll("[A-Za-z0-9@]", ""); //creates a non- alphanumerical name. IE: !.#$.. The punctuation in the end.

                String official_not_alphanumerical = ChatColor.GRAY + not_alphanumeral;

                String alphanumerical = args[index].replaceAll("[^A-Za-z0-9@]", ""); //We are filtering OUT the non-alphanumerical. ABC 0-9 stays whitelisted.

                //Alphanumerical should now be: @PLAYER
                //official_not_alphanumerical should now be: the punctuation at the end of the @PLAYER. Like: @PLAYER. or @PLAYER!

                String playername = ChatColor.GOLD + "" + ChatColor.BOLD + alphanumerical + official_not_alphanumerical +  ChatColor.GRAY;


                String official_msg = msg.replace(args[index], playername);

                String finalmessage = String.format(e.getFormat(), official_sender.getDisplayName(), official_msg);

                onlineplayer.playSound(onlineplayer.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5 , 1);

                executeMessage(onlineplayer, official_sender, finalmessage, e);

            }
        }
    }




    @EventHandler
    public void onTabComplete(TabCompleteEvent e) {
        //e.setCompletions(tabCompleteChat((Player) e.getSender(), e.getBuffer()));
    }

    public void executeMessage (Player player, Player sender, String message, AsyncPlayerChatEvent e){


        String strippedcolor = ChatColor.stripColor(message);

        if(strippedcolor.startsWith("[TC]")){
            try {
                Resident resident = TownyUniverse.getDataSource().getResident(sender.getName());
                Town town = resident.getTown();
                List<Resident> residents = town.getResidents();

                for (Resident resident_each : residents ){

                    Player resident_as_player = Bukkit.getPlayer(resident_each.getName());
                    resident_as_player.sendMessage(message);

                }
            } catch (Exception exception){
                exception.printStackTrace();
            }
        }

        else if (strippedcolor.startsWith("[NC]")){
            try {
                Resident resident = TownyUniverse.getDataSource().getResident(sender.getName());
                Nation nation = resident.getTown().getNation();
                List<Resident> residents = nation.getResidents();

                for (Resident resident_each : residents ){

                    Player resident_as_player = Bukkit.getPlayer(resident_each.getName());
                    resident_as_player.sendMessage(message);

                }

            }catch(Exception exception){
                exception.printStackTrace();
            }
        }

        else if(strippedcolor.startsWith("[M]")){
            if(player.hasPermission("towny.chat.mod")){
                player.sendMessage(message);
            }
        }

        else if(strippedcolor.startsWith("[ADMIN]")){
            if(player.hasPermission("towny.chat.admin")){
                player.sendMessage(message);
            }
        }

        else if(strippedcolor.startsWith("[RP]") || strippedcolor.startsWith("[OOC]")){
            long radius = Math.round(sender.getLocation().distance(player.getLocation()));

            if(radius <= 70){
                player.sendMessage(message);
            }
        }

        else if (strippedcolor.startsWith("[G]")){

            player.sendMessage(message);

        } else if (strippedcolor.startsWith("[me ->")){
            return;
        } else {
            player.sendMessage(message);
        }

        e.setCancelled(true);


    }

    public List<String> tabCompleteChat(Player player, String message) {
        List<String> completions = new ArrayList();
        boolean charadd = false;
        PlayerChatTabCompleteEvent event = new PlayerChatTabCompleteEvent(player, message, completions);

        String token = event.getLastToken();

        if (token.startsWith("@")) {
            token = token.substring(1);
            completions.add("@all");
            charadd = true;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if ((player.canSee(p)) && (StringUtil.startsWithIgnoreCase(p.getName(), token))) {

                if (charadd) {
                    completions.add("@" + p.getName());
                } else {
                    completions.add(p.getName());
                }
            }
        }

        PluginManager pm = plugin.getServer().getPluginManager();

        pm.callEvent(event);

        Iterator<?> it = completions.iterator();
        while (it.hasNext()) {
            Object current = it.next();
            if (!(current instanceof String)) {
                it.remove();
            }
        }
        Collections.sort(completions, String.CASE_INSENSITIVE_ORDER);
        return completions;
    }

}
