package com.worldciv.events.player;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static com.worldciv.the60th.Main.getWorldGuard;
import static com.worldciv.the60th.Main.vision_bypass;
import static com.worldciv.utility.utilityArrays.visionregion;

public class MoveEvent implements Listener {
    @EventHandler
    public void onMoveEvent(PlayerMoveEvent e) {
        Player p = e.getPlayer();

    }
}
