package com.worldciv.events.player;

import com.worldciv.the60th.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftMagicNumbers;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;

import static com.worldciv.utility.utilityArrays.toggletimber;
import static com.worldciv.utility.utilityArrays.toggletimbermessages;
import static com.worldciv.utility.utilityStrings.worldciv;

public class TreeCutterEvent implements Listener{


    HashMap<Player, Block> chopping = new HashMap<>();
    HashMap<Player, Block> chopping_records = new HashMap<>();
    HashMap<Player, Double> chopping_time = new HashMap<>();


    @EventHandler
    public void onAbandonTree(PlayerMoveEvent e){
        Player p = e.getPlayer();

        if(toggletimber.contains(p)){
            return;
        }

        if(!chopping_records.containsKey(p)){
         return;
        } else {
            if(p.getLocation().distance(chopping_records.get(p).getLocation()) > 5 ){
                if (!toggletimbermessages.contains(p))p.sendMessage(worldciv + ChatColor.RED + " You abandoned the tree.");
                chopping.remove(p);
                chopping_records.remove(p);
                return;
            }
        }


    }

    @EventHandler
    public void onHitLog(PlayerInteractEvent e) { //Final event anything passed by e is FINAL

        Block block = e.getClickedBlock(); //block we are trying to interact with



        if (e.getClickedBlock() == null || block.getLocation() == null) {
            return;
        }

        Location loc = block.getLocation();
        Player p = e.getPlayer(); // player

        if(toggletimber.contains(p)){
            return;
        }

        if (e.isCancelled()) { //other plugins can interfere so lets cancel this nentire thing ifother events wish it to be canceled
            return;
        }

        if (block.getType() != Material.LOG && block.getType() != Material.LOG_2) { //if its log
            return;
        }

        if(!isTree(block)){
            return;
        }

        if (!this.isEffectiveAxe(p.getInventory().getItemInMainHand())) { //has an effective axe
            return;
        }

        if (p.getGameMode() != GameMode.SURVIVAL) { //no creative glitchs
            return;
        }

        if (e.getAction() != Action.LEFT_CLICK_BLOCK) { //if ur left clicking
            return;
        }

        ItemStack axe = p.getInventory().getItemInMainHand(); //Item in main hand
        Material axetype = axe.getType(); //Material Type
        double base = 3; //Hand duration is the base
        double multiplier; //the multiplier, we need the reciprocal
        float amount_of_logs = getAllLogs(block, p); //All logs above the block being interacted with

        switch (axetype) {
            case DIAMOND_AXE:
                multiplier = 8;
                break;
            case GOLD_AXE:
                multiplier = 12;
                break;
            case IRON_AXE:
                multiplier = 6;
                break;
            case STONE_AXE:
                multiplier = 4;
                break;
            case WOOD_AXE:
                multiplier = 2;
                break;
            default:
                multiplier = 1;
                break;
        }

        if (getEffEnchLevel(axe) == 0) {
        } else {
            double efflevel = getEffEnchLevel(axe); //returns int of efficiency
            double eff_calc = Math.pow(efflevel, 2) + 1; //wiki calculation for eff calculation
            multiplier += eff_calc; //add it to the multiplier
        }

        DecimalFormat df = new DecimalFormat("##.##");
        double duration = Double.valueOf(df.format(base * (1 / multiplier) * amount_of_logs)); //effectively tells you the duration of cutting down so called tree





        if (!chopping_records.containsKey(p)) { //If you haven't been recorded yet. Add.
            if (!toggletimbermessages.contains(p))p.sendMessage(worldciv + ChatColor.GRAY + " Log(s) Found: " + ChatColor.YELLOW + amount_of_logs + ChatColor.GRAY + ". Duration: " + ChatColor.YELLOW + String.valueOf(duration) + ChatColor.GRAY + ".");
            chopping_records.put(p, block);//Add a record of player cutting down a block. First time cutting down a log. take is easy :C
            chopping_time.put(p, duration);
            chopping.remove(p);
        } else if (!chopping_records.get(p).equals(block)) { //this is a new block. replace it.
            if (!toggletimbermessages.contains(p))  p.sendMessage(worldciv + ChatColor.GRAY + " Log(s) Found: " + ChatColor.YELLOW + amount_of_logs + ChatColor.GRAY + ". Duration: " + ChatColor.YELLOW + String.valueOf(duration) + ChatColor.GRAY + ".");
            chopping_records.replace(p, block);
            chopping_time.replace(p, duration);
            chopping.remove(p);
        } else if(chopping.get(p) == null) {
            if (!toggletimbermessages.contains(p))    p.sendMessage(worldciv + ChatColor.GRAY + " This tree is " + ChatColor.RED + "not ready".toUpperCase() + ChatColor.GRAY + " for you to chop down.");
            return;
        } else if(chopping.get(p).equals(block)) { //It's ready! :D
            if (!toggletimbermessages.contains(p))   p.sendMessage(worldciv + ChatColor.GRAY + " This tree is " + ChatColor.GREEN + "ready".toUpperCase() + ChatColor.GRAY + " for you to chop down.");

            if(duration != chopping_time.get(p)){
                if (!toggletimbermessages.contains(p))   p.sendMessage(worldciv + ChatColor.RED + " This isn't the axe you first used...");

                chopping.remove(p);
                chopping_time.remove(p);
                chopping_records.remove(p);

                return;
            }

            chopping.replace(p, block);
            return;
        } else if (chopping_records.get(p).equals(block)) {
            if (!toggletimbermessages.contains(p)) p.sendMessage(worldciv + ChatColor.GRAY + " This tree is " + ChatColor.RED + "not ready".toUpperCase() + ChatColor.GRAY + " for you to chop down.");
            return;
            //im not sure if anything can be added here
        }

        new BukkitRunnable() {
            int iteration = 0;

            @Override
            public void run() {

                if(chopping_records.get(p) == null){
                    cancel();
                    return;
                }

                if (p.getLocation().distance(block.getLocation()) > 5) {
                    if (!toggletimbermessages.contains(p))  p.sendMessage(worldciv + ChatColor.RED + " You abandoned the tree.");
                    chopping.remove(p);
                    chopping_records.remove(p);
                    chopping_time.remove(p);
                    cancel();
                    return;
                }

                if(!chopping_records.get(p).equals(block)){
                    chopping.remove(p);
                    chopping_records.replace(p, block);
                    chopping_time.replace(p, duration);
                    cancel();
                    return;
                }

                if (Math.floor(duration) <= iteration) {
                    chopping.put(p, block);
                    if (!toggletimbermessages.contains(p))   p.sendMessage(worldciv + ChatColor.GRAY + " This tree is " + ChatColor.GREEN + "ready".toUpperCase() + ChatColor.GRAY + " for you to chop down.");
                    cancel();
                    return;
                }
                iteration++;
            }
        }.runTaskTimer(Main.plugin, 0, 20);


        // setHardness(block, amount_of_logs); This changes all blocks. Can't use this.. :C

    }

    /**
     * Checks event canceled, if block is a log, if axe, if survival => if chopping then break -> if not cancel event
     *
     * @param e
     */
    @EventHandler
    public void onBreakingBlock(final BlockBreakEvent e) {

        Player p = e.getPlayer();
        Block block = e.getBlock();

        if(toggletimber.contains(p)){
            return;
        }

        if (e.isCancelled()) {
            return;
        }
        if (e.getBlock().getType() != Material.LOG && e.getBlock().getType() != Material.LOG_2) {
            return;
        }

        if(!isTree(block)){
            return;
        }

        if(getAllLogs(block , p) == 1){
            return;
        }

        if (!this.isEffectiveAxe(p.getInventory().getItemInMainHand())) {
            return;
        }

        if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) {
            return;
        }


        if(chopping.get(p) == null){
            e.setCancelled(true);
            if (!toggletimbermessages.contains(p))p.sendMessage(worldciv + ChatColor.GRAY + " This tree is " + ChatColor.RED + "not ready".toUpperCase() + ChatColor.GRAY + " for you to chop down.");
            return;
        }

        if (chopping.get(p).equals(e.getBlock())) {
            this.breakBlock(e.getBlock(), e.getPlayer());
            chopping_records.remove(p);
            chopping.remove(p);
            chopping_time.remove(p);

        } else {
            e.setCancelled(true);
        }
    }

    public int getAllLogs(final Block b, final Player p) {

        int amount_of_logs = 0;

        for (int x = 1; x < 256; x++) {
            final Location above = new Location(b.getWorld(), (double) b.getLocation().getBlockX(), (double) (b.getLocation().getBlockY() + x), (double) b.getLocation().getBlockZ());
            final Block blockAbove = above.getBlock();

            if (blockAbove.getType() != Material.LOG && blockAbove.getType() != Material.LOG_2) {
                amount_of_logs = x;
                break;
            }
        }
        return amount_of_logs;
    }

    public boolean isTree(Block b){

        final Location below = new Location(b.getWorld(), (double) b.getLocation().getBlockX(), (double) (b.getLocation().getBlockY() - 1), (double) b.getLocation().getBlockZ());
        final Block blockBelow = below.getBlock();

        if(blockBelow.getType() != Material.DIRT && blockBelow.getType() != Material.GRASS && blockBelow.getType() != Material.LOG &&  blockBelow.getType() != Material.LOG_2){
            return false;
        }

        for (int x = 1; x < 256; x++) {
            final Location above = new Location(b.getWorld(), (double) b.getLocation().getBlockX(), (double) (b.getLocation().getBlockY() + x), (double) b.getLocation().getBlockZ());
            final Block blockAbove = above.getBlock();

            if (blockAbove.getType() != Material.LOG && blockAbove.getType() != Material.LOG_2) {
                if(blockAbove.getType() ==Material.LEAVES || blockAbove.getType() == Material.LEAVES_2){
                    return true;
                }

            } else {
                //there is more logs
            }
        }
        return false;
    }

    public int getEffEnchLevel(ItemStack axe) {
        for (Enchantment ench : axe.getEnchantments().keySet()) {

            if (ench.equals(Enchantment.DIG_SPEED)) {
                return axe.getEnchantments().get(ench);
            }
        }
        return 0;
    }

    public int getUnbreakingEnchLevel(ItemStack axe) {
        for (Enchantment ench : axe.getEnchantments().keySet()) {

            if (ench.equals(Enchantment.DURABILITY)) {
                return axe.getEnchantments().get(ench);
            }
        }
        return 0;
    }

    public void breakBlock(final Block b, final Player p) {
        b.breakNaturally();
        final Location above = new Location(b.getWorld(), (double)b.getLocation().getBlockX(), (double)(b.getLocation().getBlockY() + 1), (double)b.getLocation().getBlockZ());
        final Block blockAbove = above.getBlock();
        if (blockAbove.getType() == Material.LOG || blockAbove.getType() == Material.LOG_2) {
            this.breakBlock(blockAbove, p);
            short current_durability_number = p.getInventory().getItemInMainHand().getDurability();
            Bukkit.broadcastMessage(Short.toString(current_durability_number));
//todo remove ^^
            if (current_durability_number > 0) {
                if (getUnbreakingEnchLevel(p.getInventory().getItemInMainHand()) > 0) {

                    float durability_chance = 1 / (1 + getUnbreakingEnchLevel(p.getInventory().getItemInMainHand()));
                    Random r = new Random();
                    float random = r.nextFloat(); //returns value 0 < x < 1
                    if(random <= durability_chance ){
                        if(current_durability_number - 1 != 0){

                        }
                    }

                } else { //0 or null?
                    if (current_durability_number - 1 != 0) {
                        current_durability_number = Short.valueOf(String.valueOf(current_durability_number - 1));
                        p.getInventory().getItemInMainHand().setDurability(current_durability_number);
                    } else {
                        p.getInventory().remove(p.getItemInHand());
                        p.playSound(p.getLocation(), Sound.BLOCK_WOOD_BREAK, 3.0f, 4.0f);
                    }

                }
            }

            if (p.getItemInHand().getDurability() > p.getItemInHand().getType().getMaxDurability()) {

            }
        }
    }

    public void destroyDurability(Player p, float durability){

    }

    public boolean isEffectiveAxe(final ItemStack item) {
        return item.getType().equals((Object) Material.IRON_AXE) || item.getType().equals((Object) Material.GOLD_AXE) || item.getType().equals((Object) Material.DIAMOND_AXE);
    }

    /**
     * Packet NMS and Craftbukkit
     */

    public void setHardness(Block block, float amount_of_logs) {

        Material material = block.getType();

        try {
            Field field = net.minecraft.server.v1_12_R1.Block.class.getDeclaredField("strength");
            field.setAccessible(true);
            field.setFloat(CraftMagicNumbers.getBlock(material), 2 * amount_of_logs);

            //add a timer for the duration. once duration runs out send packets to ALL players and players who are abotu to quuit to "2".


        } catch (NoSuchFieldException | IllegalAccessException exception) {
            exception.printStackTrace();
        }


    }


    public void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getCraftClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Class<?> getCraftClass(String name) {
        // org.bukkit.craftbukkit.v1_8_R3
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}