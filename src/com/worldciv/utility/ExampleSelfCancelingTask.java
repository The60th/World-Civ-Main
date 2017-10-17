package com.worldciv.utility;

import com.worldciv.events.player.AttackEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ExampleSelfCancelingTask extends BukkitRunnable {

    private final JavaPlugin plugin;

    private int counter;

    private Player player;

    public ExampleSelfCancelingTask(JavaPlugin plugin, int counter, Player player) {
        this.player = player;
        this.plugin = plugin;
        if (counter < 1) {
            throw new IllegalArgumentException("counter must be greater than 1");
        } else {
            this.counter = counter;
        }
    }

    @Override
    public void run() {
        if(AttackEvent.defenderArmorTracker.containsKey(player)){
            if(counter >= 30){
                AttackEvent.defenderArmorTracker.remove(player);
            }else{

            }
        }
        else if(!(AttackEvent.defenderArmorTracker.containsKey(player))){
            this.cancel();
        }
    }

}
