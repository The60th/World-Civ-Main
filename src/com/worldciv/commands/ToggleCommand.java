package com.worldciv.commands;

import com.worldciv.the60th.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.worldciv.utility.utilityArrays.*;
import static com.worldciv.utility.utilityStrings.mainbot;
import static com.worldciv.utility.utilityStrings.maintop;
import static com.worldciv.utility.utilityStrings.worldciv;

public class ToggleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if (cmd.getName().equalsIgnoreCase("toggle") ||cmd.getName().equalsIgnoreCase("t" )) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "You must be a player to access this command!");

                return true;
            }

            Player p = (Player) sender; //declare player

            if (args.length == 0) {
                p.sendMessage(worldciv + ChatColor.RED + " Invalid arguments! Use " + ChatColor.YELLOW + "/toggle help" + ChatColor.RED + ". Example: " + ChatColor.YELLOW + "/toggle sb");
                return true;
            } else if (args[0].equalsIgnoreCase("help")) {

                p.sendMessage(maintop);
                p.sendMessage(ChatColor.GRAY + " The toggle commands are:" + ChatColor.AQUA + " scoreboard (sb), sbanimation (anim), visionmessages (vm/vms)");

                if (p.hasPermission("worldciv.togglevision")) {
                    p.sendMessage(ChatColor.GRAY + " The staff toggle commands are (only staff can see this):" + ChatColor.AQUA + " vision (v)");
                }
                p.sendMessage(" " + mainbot);
            } else if (args[0].equalsIgnoreCase("sb") || args[0].equalsIgnoreCase("scoreboard")) {
                if (dummytoggleboard.contains(p)) {
                    dummytoggleboard.remove(p);
                    p.sendMessage(worldciv + ChatColor.GRAY + " The scoreboard has been enabled!");
                    Main.getScoreboardManager().setScoreboard(p);
                    return true;

                } else if (!dummytoggleboard.contains(p)) {
                    dummytoggleboard.add(p);
                    p.sendMessage(worldciv + ChatColor.GRAY + " The scoreboard has been disabled!");
                    p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("vision") || args[0].equalsIgnoreCase("v")) {
                if (!p.hasPermission("worldciv.togglevision")) {
                    p.sendMessage(worldciv + ChatColor.GRAY + " This command is only allowed for staff. If you believe this is an error, ask staff to provide you the" + ChatColor.AQUA + " worldciv.togglevision" + ChatColor.GRAY + " permission.");
                    return true;
                }
                if (!togglevision.contains(p)) {
                    togglevision.add(p);
                    p.sendMessage(worldciv + ChatColor.GRAY + " You have enabled " + ChatColor.YELLOW + "vision bypass.");

                    return true;
                }
                if (togglevision.contains(p)) {
                    togglevision.remove(p);
                    p.sendMessage(worldciv + ChatColor.GRAY + " You have disabled " + ChatColor.YELLOW + "vision bypass.");
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("sbanimation") || args[0].equalsIgnoreCase("anim")) {
                if (toggledisplay.contains(p)) {
                    toggledisplay.remove(p);
                    p.sendMessage(worldciv + ChatColor.GRAY + " The display title's animation has been enabled!");
                    Main.getScoreboardManager().setScoreboard(p);
                    return true;

                } else if (!toggledisplay.contains(p)) {
                    toggledisplay.add(p);
                    p.sendMessage(worldciv + ChatColor.GRAY + " The display title's animation has been disabled!");

                    return true;
                }
            } else if (args[0].equalsIgnoreCase("visionmessages") || args[0].equalsIgnoreCase("vm") || args[0].equalsIgnoreCase("vms")) {
                if (togglevisionmessage.contains(p)) {
                    togglevisionmessage.remove(p);
                    p.sendMessage(worldciv + ChatColor.GRAY + " The vision message notifications have been enabled!");
                    return true;

                } else if (!togglevisionmessage.contains(p)) {
                    togglevisionmessage.add(p);
                    p.sendMessage(worldciv + ChatColor.GRAY + " The vision message notifications have been disabled!");

                    return true;
                }
            } else {
                p.sendMessage(worldciv + ChatColor.RED + " Not a valid argument! Use" + ChatColor.YELLOW + " /toggle help" + ChatColor.RED + ". Example: " + ChatColor.YELLOW + "/toggle sb");
                return true;
            }
        }

        return true;

    }



}
