package com.worldciv.commands;

import com.worldciv.the60th.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.worldciv.the60th.Main.fileSystem;
import static com.worldciv.utility.utilityArrays.*;
import static com.worldciv.utility.utilityStrings.*;

public class ToggleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if (cmd.getName().equalsIgnoreCase("toggle") ||cmd.getName().equalsIgnoreCase("tg" )) {
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
                p.sendMessage(ChatColor.GRAY + " The toggle commands are:" + ChatColor.AQUA + " scoreboard (sb), sbanimation (anim), visionmessages (vm/vms), censorship (c), timber (t), timbermessages (tm/tms)");

                if (p.hasPermission("worldciv.togglevision")) {
                    p.sendMessage(ChatColor.GRAY + " The staff toggle commands are (only staff can see this):" + ChatColor.AQUA + " vision (v), socialspy (ss)");
                }
                p.sendMessage(" " + mainbot);
            } else if (args[0].equalsIgnoreCase("sb") || args[0].equalsIgnoreCase("scoreboard")) {
                if (fileSystem.getToggleList("scoreboard").contains(p.getName())) {
                    fileSystem.removeToggle(p, "scoreboard");
                    p.sendMessage(worldciv + ChatColor.GRAY + " The scoreboard has been enabled!");
                    Main.getScoreboardManager().setScoreboard(p);
                    return true;

                } else if (!fileSystem.getToggleList("scoreboard").contains(p.getName())) {
                    fileSystem.addToggle(p, "scoreboard");
                    p.sendMessage(worldciv + ChatColor.GRAY + " The scoreboard has been disabled!");
                    p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("vision") || args[0].equalsIgnoreCase("v")) {
                if (!p.hasPermission("worldciv.togglevision")) {
                    p.sendMessage(worldciv + ChatColor.GRAY + " This command is only allowed for staff. If you believe this is an error, ask staff to provide you the" + ChatColor.AQUA + " worldciv.togglevision" + ChatColor.GRAY + " permission.");
                    return true;
                }
                if (!fileSystem.getToggleList("vision").contains(p.getName())) {
                    fileSystem.addToggle(p, "vision");
                    p.sendMessage(worldciv + ChatColor.GRAY + " You have enabled " + ChatColor.YELLOW + "vision bypass.");
                    return true;
                } else if (fileSystem.getToggleList("vision").contains(p.getName())) {
                    fileSystem.removeToggle(p, "vision");
                    p.sendMessage(worldciv + ChatColor.GRAY + " You have disabled " + ChatColor.YELLOW + "vision bypass.");
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("censor") || args[0].equalsIgnoreCase("c")) {

                if (!fileSystem.getToggleList("c").contains(p.getName())) {
                    fileSystem.addToggle(p, "c");
                    p.sendMessage(worldciv + ChatColor.GRAY + " You have disabled " + ChatColor.YELLOW + "censorship.");

                    return true;
                }
                if (fileSystem.getToggleList("c").contains(p.getName())) {
                    fileSystem.removeToggle(p, "c");
                    p.sendMessage(worldciv + ChatColor.GRAY + " You have enabled " + ChatColor.YELLOW + "censorship");
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("socialspy") || args[0].equalsIgnoreCase("ss")) {
                if (!p.hasPermission("worldciv.socialspy")) {
                    p.sendMessage(worldciv + ChatColor.GRAY + " This command is only allowed for staff. If you believe this is an error, ask staff to provide you the" + ChatColor.AQUA + " worldciv.socialspy" + ChatColor.GRAY + " permission.");
                    return true;
                }
                if(!fileSystem.getToggleList("ss").contains(p.getName())){
                    fileSystem.addToggle(p, "ss");
                    p.sendMessage(worldciv + ChatColor.GRAY + " You have enabled " + ChatColor.YELLOW + "social spy.");

                    return true;
                }
                else if(fileSystem.getToggleList("ss").contains(p.getName())){
                    fileSystem.removeToggle(p, "ss");
                    p.sendMessage(worldciv + ChatColor.GRAY + " You have disabled " + ChatColor.YELLOW + "social spy");
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("timbermessages") || args[0].equalsIgnoreCase("tm") || args[0].equalsIgnoreCase("tms")) {
                if(fileSystem.getToggleList("tms").contains(p.getName())){
                    fileSystem.removeToggle(p, "tms");
                    p.sendMessage(worldciv + ChatColor.GRAY + " You have enabled " + ChatColor.YELLOW + "timber messages" + ChatColor.GRAY + ".");
                    return true;

                } else if(!fileSystem.getToggleList("tms").contains(p.getName())){
                    fileSystem.addToggle(p, "tms");
                    p.sendMessage(worldciv + ChatColor.GRAY + " You have disabled " + ChatColor.YELLOW + "timber messages" + ChatColor.GRAY + ".");
                    return true;
                }
            }else if (args[0].equalsIgnoreCase("timber") || args[0].equalsIgnoreCase("t")) {
                if(fileSystem.getToggleList("timber").contains(p.getName())){
                    fileSystem.removeToggle(p, "timber");
                    p.sendMessage(worldciv + ChatColor.GRAY + " You have enabled " + ChatColor.YELLOW + "timber" + ChatColor.GRAY + ".");
                    return true;

                } else if(!fileSystem.getToggleList("timber").contains(p.getName())){
                    fileSystem.addToggle(p, "timber");
                    p.sendMessage(worldciv + ChatColor.GRAY + " You have disabled " + ChatColor.YELLOW + "timber" + ChatColor.GRAY + ".");

                    return true;
                }
            } else if (args[0].equalsIgnoreCase("sbanimation") || args[0].equalsIgnoreCase("anim")) {
                if(fileSystem.getToggleList("sbanimation").contains(p.getName())){
                    fileSystem.removeToggle(p, "sbanimation");
                    p.sendMessage(worldciv + ChatColor.GRAY + " The display title's animation has been enabled!");
                    Main.getScoreboardManager().setScoreboard(p);
                    return true;

                } else  if(!fileSystem.getToggleList("sbanimation").contains(p.getName())){
                    fileSystem.addToggle(p, "sbanimation");
                    p.sendMessage(worldciv + ChatColor.GRAY + " The display title's animation has been disabled!");

                    return true;
                }
            } else if (args[0].equalsIgnoreCase("visionmessages") || args[0].equalsIgnoreCase("vm") || args[0].equalsIgnoreCase("vms")) {
                if(fileSystem.getToggleList("vms").contains(p.getName())){
                    fileSystem.removeToggle(p, "vms");
                    p.sendMessage(worldciv + ChatColor.GRAY + " The vision message notifications have been enabled!");
                    return true;

                } else  if(!fileSystem.getToggleList("vms").contains(p.getName())){
                    fileSystem.addToggle(p, "vms");
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
