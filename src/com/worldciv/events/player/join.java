package com.worldciv.events.player;

import com.worldciv.the60th.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class join implements Listener{
    @SuppressWarnings("deprecation")
    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer(); //player triggering event


        // SCOREBOARD CREATION //
        Main.getScoreboardManager().setScoreboard(player);

    }
}
