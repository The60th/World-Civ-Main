package com.worldciv.events.player;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Furnace;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static com.worldciv.utility.utilityStrings.worldciv;

public class furnaceCreate implements Listener {

    @EventHandler
    public void onSmeltCompletion(FurnaceSmeltEvent e) {

        Furnace furnace = (Furnace) e.getBlock().getState();
        FurnaceInventory furnaceInv = (FurnaceInventory) furnace.getInventory();

        ItemStack[] itemsInFurnace = furnaceInv.getContents();

        Material source = Material.IRON_INGOT;
        Material fuel = Material.COAL;

        if (itemsInFurnace[0] == null) {
            return;
        }

        if (itemsInFurnace[1] == null) {
            List<Entity> near = furnace.getWorld().getEntities();
            for (Entity players : near) {
                if (players instanceof Player) {
                    if (players.getLocation().distance(furnace.getLocation()) <= 10) {
                        players.sendMessage(worldciv + ChatColor.GRAY + " There is no fuel on the furnace. Add more!");
                    }
                }
            }
            e.setCancelled(true);
            furnaceInv.getHolder().setBurnTime((short) 20);
            return;
        }

        if (itemsInFurnace[0].getType() == source && itemsInFurnace[1].getType() == fuel) {


                List<Entity> near = furnace.getWorld().getEntities();

                for (Entity players : near) {
                    if (players instanceof Player) {
                        if (players.getLocation().distance(furnace.getLocation()) <= 10) {

                            if (itemsInFurnace[0].getItemMeta().getLore() == null || itemsInFurnace[1].getItemMeta().getLore() == null) {

                                ((Player) players).updateInventory(); //it glitches with non-vanilla items, thts why we update
                                players.sendMessage(worldciv + ChatColor.GRAY + " The ingot(s) didn't smelt!!");
                                PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.VILLAGER_ANGRY, true, furnace.getLocation().getBlockX(), furnace.getLocation().getBlockY(), furnace.getLocation().getBlockZ(), 1, 1, 1, 10, 50, null);
                                ((CraftPlayer) players).getHandle().playerConnection.sendPacket(packet);
                                ((Player) players).playSound(furnace.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 5L, 0);
                                e.setCancelled(true);

                            } else if (itemsInFurnace[0].getItemMeta().getLore().get(0).contains("refined iron ingot") && itemsInFurnace[1].getItemMeta().getLore().get(0).contains("Coke is a fuel")) {
                                if (itemsInFurnace[1].getDurability() != 1) {
                                    ((Player) players).updateInventory(); //it glitches with non-vanilla items, thts why we update
                                    players.sendMessage(worldciv + ChatColor.GRAY + " The refined iron ingot(s) smelted into steel!");
                                    PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.VILLAGER_HAPPY, true, furnace.getLocation().getBlockX(), furnace.getLocation().getBlockY(), furnace.getLocation().getBlockZ(), 1, 1, 1, 10, 50, null);
                                    ((CraftPlayer) players).getHandle().playerConnection.sendPacket(packet);
                                    ((Player) players).playSound(furnace.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5L, 0);

                                    if (itemsInFurnace[0].getAmount() > 1) {
                                        furnaceInv.getHolder().setCookTime((short) 400);
                                    }

                                }

                            } else if (itemsInFurnace[0].getItemMeta().getLore().get(0).contains("refined iron ingot") && itemsInFurnace[1].getItemMeta().getLore().get(0).contains("Activated Carbon is a fuel")) {
                                if (itemsInFurnace[1].getDurability() == 1) { //charcoal
                                    ((Player) players).updateInventory(); //it glitches with non-vanilla items, thts why we update
                                    players.sendMessage(worldciv + ChatColor.GRAY + " The refined iron ingot(s) smelted into steel!");
                                    PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.VILLAGER_HAPPY, true, furnace.getLocation().getBlockX(), furnace.getLocation().getBlockY(), furnace.getLocation().getBlockZ(), 1, 1, 1, 10, 50, null);
                                    ((CraftPlayer) players).getHandle().playerConnection.sendPacket(packet);
                                    ((Player) players).playSound(furnace.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5L, 0);

                                    if (itemsInFurnace[0].getAmount() > 1) {
                                        furnaceInv.getHolder().setCookTime((short) 400);
                                    }

                                }
                            }

                        }
                }
            }
        }
    }

    @EventHandler
    public void onBurnEvent(FurnaceBurnEvent e){
        Furnace furnace = (Furnace) e.getBlock().getState();
        FurnaceInventory furnaceInv = (FurnaceInventory) furnace.getInventory();

        ItemStack[] itemsInFurnace = furnaceInv.getContents();

        Material source = Material.IRON_INGOT;
        Material fuel = Material.COAL;

        if (itemsInFurnace[0] == null || itemsInFurnace[1] == null) {
            return;
        }

        if(itemsInFurnace[0].getItemMeta().getLore() == null | itemsInFurnace[1].getItemMeta().getLore() == null){
            return;
        }

        if(itemsInFurnace[1].getType() == Material.COAL && itemsInFurnace[1].getDurability() != 1 && itemsInFurnace[1].getItemMeta().getLore().get(0).contains("Coke is a fuel")){
            for(Entity p : furnaceInv.getViewers()){
                p.sendMessage(worldciv + ChatColor.GRAY + " You added coke (fuel) to the furnace!");
            }
            e.setBurnTime(200); //10s
        }  else if(itemsInFurnace[1].getType() == Material.COAL && itemsInFurnace[1].getDurability() == 1 && itemsInFurnace[1].getItemMeta().getLore().get(0).contains("Activated Carbon")){
            for(Entity p : furnaceInv.getViewers()){
                p.sendMessage(worldciv + ChatColor.GRAY + " You added activated carbon to the furnace!");
            }
            e.setBurnTime(100); //5s
        }

        if(itemsInFurnace[0].getType() == Material.IRON_INGOT && itemsInFurnace[0].getItemMeta().getLore().get(0).contains("refined iron ingot")){
            furnaceInv.getHolder().setCookTime((short) 400); //20s
        }


    }

    @EventHandler
    public void onFurnaceClickEvent(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        if (e.getView().getType() == InventoryType.FURNACE) {
            FurnaceInventory furnaceInv = (FurnaceInventory) e.getInventory();
            ItemStack[] itemsInFurnace = furnaceInv.getContents();
            int slot = e.getRawSlot();

            if (furnaceInv.getHolder().getBurnTime() > 0) {
                if(slot == 0  || slot == 1) {

                    if (itemsInFurnace[0] == null || itemsInFurnace[1] == null){
                        return;
                    }

                    if(itemsInFurnace[0].getType() == Material.IRON_INGOT && itemsInFurnace[1].getType() == Material.COAL)
                    p.sendMessage(worldciv + ChatColor.GRAY + " It is too dangerous to pick this item while smelting!");
                    e.setCancelled(true);
                }

            }
        }
    }
}

