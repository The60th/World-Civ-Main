package com.worldciv.commands;

import com.worldciv.utility.Fanciful.mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.worldciv.the60th.Main.fileSystem;
import static com.worldciv.utility.utilityMultimaps.playerandsubject;
import static com.worldciv.utility.utilityStrings.*;

public class PollCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + " You can't vote as console.");
            return true;
        }

        Player sender = (Player) commandSender;

        if (cmd.getName().equalsIgnoreCase("polls")) {

            File folder = new File(Bukkit.getPluginManager().getPlugin("WorldCivMaster").getDataFolder() + "/polls/pollsdata"); //folder
            File in_folder = new File(Bukkit.getPluginManager().getPlugin("WorldCivMaster").getDataFolder() + "/polls"); //folder
            File polls_banlist = new File(in_folder, "banlist.yml");
            File polls_reportlist = new File(in_folder, "reportlist.yml");
            YamlConfiguration polls_banlist_yml = YamlConfiguration.loadConfiguration(polls_banlist);
            YamlConfiguration polls_reportlist_yml = YamlConfiguration.loadConfiguration(polls_reportlist);

            if (args.length == 0) {

                sender.sendMessage(maintop);
                sender.sendMessage(ChatColor.YELLOW + "/polls create <subject>" + ChatColor.GRAY + ": Create a poll to express yourself.");
                sender.sendMessage(ChatColor.YELLOW + "/polls list" + ChatColor.GRAY + ": Show poll ids, votes, and more!");
                sender.sendMessage(ChatColor.YELLOW + "/polls vote <id> <Y/N> [optional-msg]" + ChatColor.GRAY + ": Vote a subject by their ID.");
                sender.sendMessage(ChatColor.YELLOW + "/polls show <id> " + ChatColor.GRAY + ": Vote a subject by their ID.");

                sender.sendMessage(mainbot);

                return true;
            }


            switch (args[0].toLowerCase()) {
                case "accept":
                    if (args.length == 1) {
                        if (!playerandsubject.containsKey(sender)) { //not in array
                            sender.sendMessage(worldciv + ChatColor.GRAY + " You must create a poll subject before you can accept the terms and conditions.");
                            return true;
                        }

                        String subject = playerandsubject.get(sender); //subject
                        int iteration = 0; //self-explanatory


                        if (fileSystem.allFiles(folder) != null) {
                            for (File file : fileSystem.allFiles(folder)) { //all files in folder

                                String file_id = getPollId(file);
                                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file); //load file

                                String file_subject = yaml.get(file_id + ".Subject").toString();

                                if (file_subject.equalsIgnoreCase(subject)) {
                                    sender.sendMessage(worldciv + ChatColor.GRAY + " A player has already published this subject.");
                                    return true;
                                }
                            }
                        }

                      /*  if (banned) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " You were banned before you could accept.");

                        } */

                        playerandsubject.remove(sender);
                        fileSystem.createPoll(sender, subject);

                        sender.sendMessage(worldciv + ChatColor.GREEN + " You accepted the terms and conditions. Use " + ChatColor.YELLOW + "/polls list " + ChatColor.GREEN + "to view your poll.");

                    } else {
                        sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid arguments. Use " + ChatColor.YELLOW + "/polls accept" + ChatColor.GREEN + "to accept the terms and conditions.");
                    }
                    return true;
                case "create":
                    if (args.length == 1) {
                        sender.sendMessage(worldciv + ChatColor.GRAY + " To create a subject to vote in: " + ChatColor.YELLOW + "/polls create <subject>" + ChatColor.GRAY + ".");
                        return true;
                    }

                    String subject = getMessage(args);

                    if (playerandsubject.containsKey(sender)) {
                        sender.sendMessage(worldciv + ChatColor.BLUE + " Your previous poll subject was abandoned.");
                        playerandsubject.remove(sender);
                    }

                    if (true) {
                        //check if this is already a name
                    }

                    if (true) {
                        //check if youre banned
                    }

                    playerandsubject.put(sender, subject);

                    new FancyMessage(worldciv + ChatColor.GRAY + " Before your poll subject, " + ChatColor.BLUE + subject + ChatColor.GRAY + ", is published, please read the ").then(ChatColor.YELLOW + "terms and conditions (hover me).").
                            tooltip(ChatColor.YELLOW + " Click me to redirect to terms and conditions.").link("https://discord.gg/r2NqfJ6").send(sender);
                    new FancyMessage(" ").send(sender);
                    new FancyMessage(ChatColor.GREEN + " Click me to accept the terms and conditions or use " + ChatColor.YELLOW + "/polls accept" + ChatColor.GRAY + ".").tooltip(ChatColor.GREEN + "Click me to accept the terms and conditions.").command("/polls accept").send(sender);
                    new FancyMessage(" ").send(sender);
                    return true;
                case "report":

                    if (args.length == 2) {
                        String possible_id = args[1];

                        if (!getPollIds(folder).contains(possible_id)){
                            sender.sendMessage(worldciv + ChatColor.GRAY + " The ID you provided was not valid.");
                            return true;
                        }

                        if (polls_banlist_yml.contains(sender.getName())) {
                            sender.sendMessage(worldciv + ChatColor.GRAY + " You are banned from using this poll feature.");
                            return true;
                        }

                        if(polls_reportlist_yml.get(possible_id) == null){ //it hasnt been added to a reported list, make player first one
                            List<String> list = new ArrayList<>();
                            list.add(sender.getName());
                            polls_reportlist_yml.createSection(possible_id);
                            polls_reportlist_yml.set(possible_id, list);

                        } else { //its already a list and need to check
                            List<String> list = polls_reportlist_yml.getStringList(possible_id);
                            if(list.contains(sender.getName())){
                                sender.sendMessage(worldciv + ChatColor.GRAY + " You have already reported this ID.");
                                //todo remove this from teh commadn remove
                                return true;
                            }
                            list.add(sender.getName());
                            polls_reportlist_yml.set(possible_id, list);
                        }

                        try {
                            polls_reportlist_yml.save(polls_reportlist);
                        } catch(Exception e){

                        }

                        sender.sendMessage(worldciv + ChatColor.GRAY + " Your report was successfully sent.");
                        return true;
                    }

                    sender.sendMessage(worldciv + ChatColor.GRAY + " To report a subject: " + ChatColor.YELLOW + "/polls report <subject>" + ChatColor.GRAY + ".");
                    return true;
                case "list":

                    File[] files = fileSystem.allFiles(folder);

                    sender.sendMessage(worldciv + ChatColor.GRAY + " Polls (Hovering subject reveals ID): ");
                    for (File file : files) {
                        String id = getPollId(file);
                        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
                        String subj = yaml.get(id + ".Subject").toString();

                        new FancyMessage(ChatColor.GRAY + "Subject: " + ChatColor.BLUE + subj).tooltip(ChatColor.GRAY + "ID: " + ChatColor.YELLOW + id + ChatColor.GRAY + ". Click for more information.").send(sender);
                    }
                    return true;
                case "vote":
                    sender.sendMessage(worldciv + ChatColor.GRAY + " To vote a subject: " + ChatColor.YELLOW + "/polls vote <subject> <yes/no> [optional-msg]" + ChatColor.GRAY + ".");
                    //todo
                    return true;
                case "refresh":
                case "reload":
                    sender.sendMessage(worldciv + ChatColor.GREEN + " The polls were refreshed.");
                    //todo refresh config
                    return true;
                case "show":
                    sender.sendMessage(worldciv + ChatColor.GRAY + " Display more information of certain vote: " + ChatColor.YELLOW + "/polls show <subject>" + ChatColor.GRAY + ".");
                    return true;
                default:
                    sender.sendMessage(worldciv + ChatColor.GRAY + " Invalid parameter. Use " + ChatColor.YELLOW + "/polls" + ChatColor.GRAY + " for more help");
                    return true;
            }
        }
        return true;
    }

    public String getMessage(String[] args) {
        List<String> oldlist = Arrays.asList(args); //make to list
        List<String> list = new ArrayList<>();
        list.addAll(oldlist);

        list.remove(0); //remove first index which should be [create [returns-all-these-args]
        int size = list.size(); //size
        int index = 0; //index
        String finalmessage = "";

        for (String arg : list) {

            if (index == 0) {
                finalmessage += arg;
            } else {
                finalmessage += " " + arg;
            }
            index++;

        }
        return finalmessage;

    }

    public static String getPollId(File file) {
        String file_name = file.getName(); //returns file name
        String[] broken_file_name = file_name.split("-"); //split it into the first category
        String file_id = broken_file_name[0].substring(2); //returns id.
        return file_id;
    }

    public static List<String> getPollIds(File folder) {

        List<String> list = new ArrayList<>();

        for (File file : fileSystem.allFiles(folder)) {
            String id = getPollId(file);
            list.add(id);
        }
        return list;

    }


}
