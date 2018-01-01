package com.worldciv.events.player;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.worldciv.the60th.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static com.worldciv.the60th.Main.*;
import static com.worldciv.utility.utilityArrays.visionregion;
import static com.worldciv.utility.utilityMultimaps.chatchannels;

public class JoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){

        //FIXIMG


        String name = getEssentials().getUser(event.getPlayer().getUniqueId()).getNick(true);
        String stripped = ChatColor.stripColor(name);
        getEssentials().getUser(event.getPlayer().getUniqueId()).setNickname(stripped);


        Player player = event.getPlayer();
        player.setMaxHealth(40);

        // CHAT CHANNEL CHECKER //
        if(!chatchannels.containsValue(player.getName())){
            chatchannels.put("global", player.getName());
        }

        // SCOREBOARD CREATION //
        Main.getScoreboardManager().setScoreboard(player);


        ApplicableRegionSet set = getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());

        if (!visionregion.contains(player)) {

            for (ProtectedRegion region : set) {
                if (region.getFlag(vision_bypass) == StateFlag.State.ALLOW) {
                    visionregion.add(player);
                }
            }

        }
    }
}
