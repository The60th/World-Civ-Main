package com.worldciv.events.player;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SteelAnvilBreakEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Block block;
    private Player p;

    public SteelAnvilBreakEvent(Player p, Block b){
        this.p = p;
        block = b;
    }

    public SteelAnvilBreakEvent(Block b){
        block = b;
    }

    public Player getPlayer(){
        return this.p;
    }

    public Block getBlock(){
        return block;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public HandlerList getHandlerList(){
        return handlers;
    }


}
