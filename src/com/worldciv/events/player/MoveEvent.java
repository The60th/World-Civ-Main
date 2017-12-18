package com.worldciv.events.player;

import com.worldciv.dungeons.Dungeon;
import com.worldciv.parties.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.worldciv.the60th.Main.getDungeonManager;
import static com.worldciv.utility.utilityArrays.lighttutorial;

public class MoveEvent implements Listener {
    @EventHandler
    public void onMoveEvent(PlayerMoveEvent e) {
        Player p = e.getPlayer();


    }
}
