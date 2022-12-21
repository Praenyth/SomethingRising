package me.praenyth.plugins.somethingrising.commands.runnable;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class LavaRising implements Runnable {

    public LavaRising(World world) {
        this.world = world;
    }

    private static int Y_LEVEL = -64;
    private World world;

    public static void resetyLevel() {
        Y_LEVEL = -64;
    }

    public static void setyLevel(int yLevel) {
        Y_LEVEL = yLevel;
    }

    @Override
    public void run() {

        Y_LEVEL++;
        for (int x = -50; x < 50; x++) {
            for (int z = -50; z < 50; z++) {
                world.getBlockAt(x, Y_LEVEL, z).setType(Material.LAVA, false);
            }
        }

    }

}
