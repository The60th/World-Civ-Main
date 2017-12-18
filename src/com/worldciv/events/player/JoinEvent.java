package com.worldciv.events.player;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.worldciv.the60th.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.xml.bind.Marshaller;

import static com.worldciv.the60th.Main.getWorldGuard;
import static com.worldciv.the60th.Main.vision_bypass;
import static com.worldciv.utility.utilityArrays.visionregion;

public class JoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.setMaxHealth(40);
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
