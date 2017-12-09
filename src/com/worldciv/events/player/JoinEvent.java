package com.worldciv.events.player;

import com.worldciv.the60th.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.xml.bind.Marshaller;

public class JoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.setMaxHealth(40);
        // SCOREBOARD CREATION //
        Main.getScoreboardManager().setScoreboard(player);
    }
}
