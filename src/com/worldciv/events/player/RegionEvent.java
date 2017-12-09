package com.worldciv.events.player;

import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import com.mewin.WGRegionEvents.events.RegionLeaveEvent;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.worldciv.parties.Party;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static com.worldciv.the60th.Main.dungeon_region;
import static com.worldciv.the60th.Main.getWorldGuard;
import static com.worldciv.the60th.Main.vision_bypass;
import static com.worldciv.utility.utilityArrays.visionregion;
import static com.worldciv.utility.utilityStrings.worldciv;

public class RegionEvent implements Listener {



    @EventHandler
    public void PlayerEnterRegion(RegionEnterEvent e) {

        Player player = e.getPlayer();
        ApplicableRegionSet set = getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());

        if (e.getRegion().getFlag(vision_bypass) == StateFlag.State.ALLOW && e.isCancellable()) {

            visionregion.add(player);
            player.sendMessage(worldciv + ChatColor.GRAY + " You are protected in this region.");
        }

        if(e.getRegion().getFlag(dungeon_region) == StateFlag.State.ALLOW && e.isCancellable()){

            Party party = new Party();

            if(!party.hasParty(player)){
                player.sendMessage(worldciv + ChatColor.RED + " You can't enter a dungeon without being in a party.");
                return;
            }

            player.sendMessage(worldciv + ChatColor.GOLD + " You have entered dungeon " + ChatColor.YELLOW + "'" + e.getRegion().getId() + "'" + ChatColor.GOLD
                    + ", difficulty " + ChatColor.YELLOW + "'" + "INSERT_DIFFICULTY" + "'" +ChatColor.GOLD + ", and party total size " + ChatColor.YELLOW + "'" + party.size(player) + "'" +ChatColor.GOLD + ".");
        }



     /*   if (e.getRegion().getFlag(vision_bypass) == StateFlag.State.DENY && e.getRegion().getFlag(vision_bypass) == null && e.isCancellable()) {

            visionregion.remove(player);

        } */

    }
        @EventHandler
        public void PlayerLeaveRegion (RegionLeaveEvent e){

        Player player = e.getPlayer();

            if (e.getRegion().getFlag(vision_bypass) == StateFlag.State.ALLOW && e.isCancellable()) {

                visionregion.remove(player);

                player.sendMessage(worldciv + ChatColor.GRAY + " You have abandoned the protected region.");


            }

            if(e.getRegion().getFlag(dungeon_region) == StateFlag.State.ALLOW && e.isCancellable()) {
                player.sendMessage(worldciv + ChatColor.GOLD + " You have exited the dungeon.");

            }


        }


    }

