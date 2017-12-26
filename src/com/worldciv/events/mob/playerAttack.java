package com.worldciv.events.mob;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static com.worldciv.events.player.PlayerAttackEvents.*;

public class playerAttack implements Listener {

    @EventHandler
    public void onEntityDamageEvent(EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager(); //Attacker
        Entity defender = event.getEntity(); //Defender

        if(!(attacker instanceof Player) || defender instanceof Player) return;
        //The attacker is now a player
        //and the defender is not a player.

        Player pAttacker = (Player) attacker;
        Entity eDefender = defender;
        pAttacker = (Player) attacker;

        float customDamage =0;
        float damageScaler = 1;
        customDamage = getDamageFromArray(getDamageItems(pAttacker));
        customDamage = (float) (customDamage * getHorseAttackModifer(pAttacker, eDefender));
        damageScaler = (float) getDamageScale(pAttacker, event.getDamage());
        customDamage = customDamage * damageScaler;

        if(eDefender instanceof Monster){
            Monster mob = (Monster) eDefender;
            Bukkit.broadcastMessage("Old health: " + mob.getHealth() + " new health " + (mob.getHealth()-customDamage));
            event.setDamage(customDamage);
            Bukkit.broadcastMessage("did damage to entity");
        }else{
            Bukkit.broadcastMessage("Do something else.");
        }

    }

}