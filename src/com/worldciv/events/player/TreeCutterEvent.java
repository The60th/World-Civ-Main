package com.worldciv.events.player;

import com.worldciv.the60th.Main;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockBreakAnimation;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftMagicNumbers;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;

import static com.worldciv.utility.utilityStrings.worldciv;

public class TreeCutterEvent implements Listener{

    double duration;

    @EventHandler
    public void onDamagedLog(final BlockDamageEvent e) {

        Block block = e.getBlock();
        BlockPosition blockpos = new BlockPosition(block.getX(), block.getY(), block.getZ());
        Player p = e.getPlayer();

        if (e.isCancelled()) {
            return;
        }

        if (block.getType() != Material.LOG && block.getType() != Material.LOG_2) {
            return;
        }

        if (!this.isAxe(p.getInventory().getItemInMainHand())) {
            return;
        }

        if (p.getGameMode() != GameMode.SURVIVAL) {
            return;
        }

        //holding axe, is survival, cutting a log

        double animation_per_second = (getDuration() / 9);


        PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(0, blockpos, 5);

        for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
            double radius = block.getLocation().distance(onlineplayer.getLocation());
            if (radius <= 16) {
                ((CraftPlayer) onlineplayer).getHandle().playerConnection.sendPacket(packet);
            }

            e.setCancelled(true);

        }
    }


    @EventHandler
    public void onHitLog(final PlayerInteractEvent e) {

        Block block = e.getClickedBlock();
        Player p = e.getPlayer();

        if (e.isCancelled()) {
            return;
        }

        if (block.getType() != Material.LOG && block.getType() != Material.LOG_2) {
            return;
        }

        if (!this.isAxe(p.getInventory().getItemInMainHand())) {
            return;
        }

        if (p.getGameMode() != GameMode.SURVIVAL) {
            return;
        }

        if (e.getAction() != Action.LEFT_CLICK_BLOCK) {
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
            //Don't do anything, it's default.
        } else {
            double efflevel = getEffEnchLevel(axe); //returns int of efficiency
            double eff_calc = Math.pow(efflevel, 2) + 1;
            multiplier = eff_calc + multiplier;
        }

        setDuration(base * (1 / multiplier) * amount_of_logs);


        setHardness(block, amount_of_logs);

        p.sendMessage(worldciv + ChatColor.GRAY + " Logs Found: " + ChatColor.YELLOW + amount_of_logs + ChatColor.GRAY + ". Duration: " + ChatColor.YELLOW + String.valueOf(getDuration()) + ChatColor.GRAY + ".");


    }

    @EventHandler
    public void onBreakingBlock(final BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) {
            return;
        }
        if (e.getBlock().getType() != Material.LOG && e.getBlock().getType() != Material.LOG_2) {
            return;
        }
        if (!this.isAxe(p.getInventory().getItemInMainHand())){
            return;
        }

        if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) {
            return;
        }

        this.breakBlock(e.getBlock(), e.getPlayer());
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

    public double getDuration() {
        return this.duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getEffEnchLevel(ItemStack axe) {
        for (Enchantment ench : axe.getEnchantments().keySet()) {
            if (ench == Enchantment.DIG_SPEED) {
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
            p.getItemInHand().setDurability((short)(p.getItemInHand().getDurability() + 1));
            if (p.getItemInHand().getDurability() > p.getItemInHand().getType().getMaxDurability()) {
                p.getInventory().remove(p.getItemInHand());
                p.playSound(p.getLocation(), Sound.BLOCK_WOOD_HIT, 1.0f, 1.0f);
            }
        }
    }

    public boolean isAxe(final ItemStack item) {
        return item.getType().equals((Object)Material.WOOD_AXE) || item.getType().equals((Object)Material.STONE_AXE) || item.getType().equals((Object)Material.IRON_AXE) || item.getType().equals((Object)Material.GOLD_AXE) || item.getType().equals((Object)Material.DIAMOND_AXE);
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

            BlockPosition blockpos = new BlockPosition(block.getX(), block.getY(), block.getZ());

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