package com.worldciv.events.player;

import com.worldciv.parties.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class quit implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        Party party = new Party();



        if(party.isLeader(p)){
            party.removeLeader(p); //TIHS ALSO REMOVES PARTY DW!
        }

        if(party.hasParty(p)){
            party.remove(p);
        }

        //check if he was in a party && waas setLeader

    }
}
