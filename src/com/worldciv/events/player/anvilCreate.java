package com.worldciv.events.player;

import com.google.common.primitives.Bytes;
import com.mysql.fabric.xmlrpc.base.Array;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static com.worldciv.utility.utilityStrings.worldciv;

public class anvilCreate implements Listener {

    public static boolean isAllFull(HumanEntity p, ItemStack item) {

        for (int i = 0; i < 35; i++) { //iterate through all inv slots

            if ((p.getInventory().getItem(i) == null)) { //empty slot counts as null
                return false;
            }

            if (p.getInventory().getItem(i).getAmount() + item.getAmount() <= item.getMaxStackSize()) {
                if (p.getInventory().getItem(i).getType().equals(item.getType())) {

                    //add item we found space !
                    return false;
                }
            }
            if (i == 34) {
                // no slots available
                return true;

            }
        }
        return true;

    }

    public void anvilBreakShiftLeft(Block anvil, HumanEntity p){
        Random r = new Random();
        int x = r.nextInt(100);

        if (anvil.getData() < Byte.valueOf("6")) {
            if (x <= 15) {
                anvil.setData(Byte.valueOf("6"));
                anvil.getState().update();
                p.sendMessage(worldciv + ChatColor.RED + " The anvil is starting to tear apart...");
            }
        } else if (anvil.getData() < Byte.valueOf("10")) {

            if (x <= 25) {
                anvil.setData(Byte.valueOf("10"));
                anvil.getState().update();
                p.sendMessage(worldciv + ChatColor.RED + " The anvil is in critical health!");
            }
        } else if (anvil.getData() == Byte.valueOf("10")) {

            if (x <= 50) {
                anvil.setType(Material.AIR);
                ((Player) p).playSound(anvil.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 3L, 0L);
                p.sendMessage(worldciv + ChatColor.RED + " The anvil broke from heavy forging!");
            }
        }
    }

    public void anvilBreakSingleClick(Block anvil, HumanEntity p){
        Random r = new Random();
        int x = r.nextInt(100);

        if (anvil.getData() < Byte.valueOf("6")) {
            if (x <= 3) {
                anvil.setData(Byte.valueOf("6"));
                anvil.getState().update();
                p.sendMessage(worldciv + ChatColor.RED + " The anvil is starting to tear apart...");
            }
        } else if (anvil.getData() < Byte.valueOf("10")) {

            if (x <= 5) {
                anvil.setData(Byte.valueOf("10"));
                anvil.getState().update();
                p.sendMessage(worldciv + ChatColor.RED + " The anvil is in critical health!");
            }
        } else if (anvil.getData() == Byte.valueOf("10")) {

            if (x <= 10) {
                anvil.setType(Material.AIR);
                ((Player) p).playSound(anvil.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 3L, 0L);
                p.sendMessage(worldciv + ChatColor.RED + " The anvil broke from heavy forging!");
            }
        }
    }

    @EventHandler
    public void onAnvilPrepare(PrepareAnvilEvent e) {
        AnvilInventory anvilInv = e.getInventory();
        ItemStack[] itemsInAnvil = anvilInv.getContents();

        if ((itemsInAnvil[0] == null) || (itemsInAnvil[1] == null)) { //if null
            return;
        }

        //This is where all the functions for anvil recipes go. Not actual click event!
        dummyRecipeItem(itemsInAnvil, e);
        dummyLoreRecipeItem(itemsInAnvil, e);

    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        if (p instanceof Player) { //clicker is player

            if (e.getView().getType() == InventoryType.ANVIL) { // detects if its an anvil

                AnvilInventory anvilInv = (AnvilInventory) e.getInventory(); //get anviilinv
                int slot = e.getRawSlot(); //slots of anvil

                if (slot == 2) { //clicking on 2nd slot
                    ItemStack[] itemsInAnvil = anvilInv.getContents(); //get anvil slots

                    if ((itemsInAnvil[0] == null) || (itemsInAnvil[1] == null)) { //if null
                        return;
                    }

                    //This is where all the functions for item clicking goes. Not actual recipe display!
                    dummyClickItem(p, itemsInAnvil, e); //Example Item To Click And Receive
                    dummyLoreClickItem(p, itemsInAnvil, e);

                }
            }
        }
    }

    private void dummyClickItem(HumanEntity p, ItemStack[] itemsInAnvil, InventoryClickEvent e) {  // Dummy Item Click Event: Material + Material = ItemStack

        Material firstitem = Material.DIRT;
        Material seconditem = Material.SAND;

        ItemStack resultitem = new ItemStack(Material.EGG, 1); //dummy item
        ItemMeta resultitemmeta = resultitem.getItemMeta();
        resultitemmeta.setLore(Arrays.asList("imisspetra"));
        resultitemmeta.setUnbreakable(true);
        resultitem.setItemMeta(resultitemmeta);

        ItemStack resultitem5 = new ItemStack(Material.EGG, 5); //dummy item as 5, don't change 5, some stuff with 5 is hardcoded. i'd keep as 5 since multiples of 5 are really nice.
        ItemMeta resultitem5meta = resultitem5.getItemMeta();
        resultitem5meta.setLore(Arrays.asList("imisspetra"));
        resultitem5meta.setUnbreakable(true);
        resultitem5.setItemMeta(resultitem5meta);

        String resultname = "enchanted egg(s)!"; //add exclamation mark and plural cases, this is what it prints: You got "x" resultname


        if (itemsInAnvil[0].getType() == firstitem && itemsInAnvil[1].getType() == seconditem) //If first slot is that and if second slot is that
        {
            Block anvil = e.getInventory().getLocation().getBlock();


            if (e.isShiftClick() && ((p.getInventory().firstEmpty() != -1) || !isAllFull(p, resultitem5))) {  //checking if shift click and is not full for 5!

                if (itemsInAnvil[0].getAmount() < 5 || itemsInAnvil[1].getAmount() < 5) { //When you dont have 5 of the required items! in either slot!
                    p.sendMessage(worldciv + ChatColor.GRAY + " Not enough material to buy " + ChatColor.YELLOW + "5 " + resultname);
                    return;
                }

                p.sendMessage(worldciv + ChatColor.GRAY + " You have added " + ChatColor.YELLOW + "5 " + ChatColor.GRAY + resultname);

                if (itemsInAnvil[0].getAmount() == 5) { //check for first slot has 5 items
                    itemsInAnvil[0].setAmount(-1); //remove them to emptiness
                }

                if (itemsInAnvil[1].getAmount() == 5) { //same as above
                    itemsInAnvil[1].setAmount(-1);
                }

                anvilBreakShiftLeft(anvil, p);

                itemsInAnvil[0].setAmount(itemsInAnvil[0].getAmount() - 5); //Removes 5 every time! hype! Once you have emptiness these lines dont matter. the more negative the more emptiness!
                itemsInAnvil[1].setAmount(itemsInAnvil[1].getAmount() - 5);

                p.getInventory().addItem(resultitem5); //Add that item! ohayo~!

                ((Player) p).updateInventory(); //update inventory cos glitchy sometimes!

            } else if (!isAllFull(p, resultitem) || (p.getInventory().firstEmpty() != -1)) { //same as the check before this. just single clicks

                p.sendMessage(worldciv + ChatColor.GRAY + " You have added " + ChatColor.YELLOW + "1 " + ChatColor.GRAY + resultname);

                if (itemsInAnvil[0].getAmount() == 1) { //same as stuff above with 5, no need to spam here now..
                    itemsInAnvil[0].setAmount(-1);
                }

                if (itemsInAnvil[1].getAmount() == 1) {
                    itemsInAnvil[1].setAmount(-1);
                }


                anvilBreakSingleClick(anvil, p);
                itemsInAnvil[0].setAmount(itemsInAnvil[0].getAmount() - 1);
                itemsInAnvil[1].setAmount(itemsInAnvil[1].getAmount() - 1);


                p.getInventory().addItem(resultitem);

                ((Player) p).updateInventory();


            } else {

                p.sendMessage(worldciv + ChatColor.GRAY + " Inventory is full! You can't forge this!"); //no inv space :)

            }
        }

    }

    private void dummyRecipeItem(ItemStack[] itemsInAnvil, PrepareAnvilEvent e) { //Dummy Recipe: Material + Material = Item Stack (DISPLAYONLY)

        Material firstitem = Material.DIRT;
        Material seconditem = Material.SAND;

        ItemStack resultitem = new ItemStack(Material.EGG, 1); //dummy item
        ItemMeta resultitemmeta = resultitem.getItemMeta();
        resultitemmeta.setLore(Arrays.asList("imisspetra"));
        resultitemmeta.setUnbreakable(true);
        resultitem.setItemMeta(resultitemmeta);


        if (itemsInAnvil[0].getType() == firstitem && itemsInAnvil[1].getType() == seconditem) {  //if only stuff was as this easy..

            e.setResult(resultitem); //display the item
        }

    }

    private void dummyLoreClickItem(HumanEntity p, ItemStack[] itemsInAnvil, InventoryClickEvent e) {  // Dummy Item Click Event: ItemStack + ItemStack = ItemStack

        Material firstitem = Material.EGG;
        Material seconditem = Material.EGG;

        ItemStack item = new ItemStack(Material.EGG, 1); //dummy item
        ItemStack item5 = new ItemStack(Material.EGG, 5); //dummy item as 5, don't change 5, some stuff with 5 is hardcoded. i'd keep as 5 since multiples of 5 are really nice.

        String resultname = "egg(s)!"; //add exclamation mark and plural cases, this is what it prints: You got "x" resultname


        if (itemsInAnvil[0].getType() == firstitem && itemsInAnvil[1].getType() == seconditem) //If first slot is that and if second slot is that
        {
            Block anvil = e.getInventory().getLocation().getBlock();

            if (itemsInAnvil[0].getItemMeta().getLore().get(0) == "imisspetra" && itemsInAnvil[0].getItemMeta().getLore().get(0) == "imisspetra") {

                if (e.isShiftClick() && ((p.getInventory().firstEmpty() != -1) || !isAllFull(p, item5))) {  //checking if shift click and is not full for 5!

                    if (itemsInAnvil[0].getAmount() < 5 || itemsInAnvil[1].getAmount() < 5) { //When you dont have 5 of the required items! in either slot!
                        p.sendMessage(worldciv + ChatColor.GRAY + " Not enough material to buy " + ChatColor.YELLOW + "5 " + resultname);
                        return;
                    }

                    p.sendMessage(worldciv + ChatColor.GRAY + " You have added " + ChatColor.YELLOW + "5 " + ChatColor.GRAY + resultname);

                    if (itemsInAnvil[0].getAmount() == 5) { //check for first slot has 5 items
                        itemsInAnvil[0].setAmount(-1); //remove them to emptiness
                    }

                    if (itemsInAnvil[1].getAmount() == 5) { //same as above
                        itemsInAnvil[1].setAmount(-1);
                    }

                    anvilBreakShiftLeft(anvil, p);

                    itemsInAnvil[0].setAmount(itemsInAnvil[0].getAmount() - 5); //Removes 5 every time! hype! Once you have emptiness these lines dont matter. the more negative the more emptiness!
                    itemsInAnvil[1].setAmount(itemsInAnvil[1].getAmount() - 5);


                    p.getInventory().addItem(item5); //Add that item! ohayo~!

                    ((Player) p).updateInventory(); //update inventory cos glitchy sometimes!

                } else if (!isAllFull(p, item) || (p.getInventory().firstEmpty() != -1)) { //same as the check before this. just single clicks

                    p.sendMessage(worldciv + ChatColor.GRAY + " You have added " + ChatColor.YELLOW + "1 " + ChatColor.GRAY + resultname);

                    if (itemsInAnvil[0].getAmount() == 1) { //same as stuff above with 5, no need to spam here now..
                        itemsInAnvil[0].setAmount(-1);
                    }

                    if (itemsInAnvil[1].getAmount() == 1) {
                        itemsInAnvil[1].setAmount(-1);
                    }

                    itemsInAnvil[0].setAmount(itemsInAnvil[0].getAmount() - 1);
                    itemsInAnvil[1].setAmount(itemsInAnvil[1].getAmount() - 1);


                    anvilBreakSingleClick(anvil, p);

                    p.getInventory().addItem(item);

                    ((Player) p).updateInventory();


                } else {

                    p.sendMessage(worldciv + ChatColor.GRAY + " Inventory is full! You can't forge this!"); //no inv space :)

                }
            }
        }

    }

    private void dummyLoreRecipeItem(ItemStack[] itemsInAnvil, PrepareAnvilEvent e) { //Dummy Recipe: ItemStack + ItemStack = Item Stack (DISPLAYONLY)

        Material firstitem = Material.EGG;
        Material seconditem = Material.EGG;

        ItemStack resultitem = new ItemStack(Material.EGG, 1); //dummy item

        if (itemsInAnvil[0].getType() == firstitem && itemsInAnvil[1].getType() == seconditem) {  //if only stuff was as this easy..
            if (itemsInAnvil[0].getItemMeta().getLore().get(0) == "imisspetra" && itemsInAnvil[0].getItemMeta().getLore().get(0) == "imisspetra") {

                e.setResult(resultitem); //display the item
            }
        }

    }
}





