package inc.silly.saucers.plugins.somethingrising.runnables;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockRisingRunnable extends BukkitRunnable {

    public BlockRisingRunnable(Plugin plugin, World world, int ticks) {
        this.plugin = plugin;
        this.world = world;
        this.ticksPerRise = ticks;
        originalTicks = ticks;
    }

    private Plugin plugin;

    private static int Y_LEVEL = -64;
    private World world;
    private int ticksPerRise;
    public static int originalTicks;


    public static void resetyLevel() {
        Y_LEVEL = -64;
    }

    public static void setyLevel(int yLevel) {
        Y_LEVEL = yLevel;
    }

    public void setTicksPerRise(int ticks) {
        originalTicks = ticks;
    }

    public void startLavaRise() {
        this.runTaskTimer(plugin, 0, 1);
    }

    @Override
    public void run() {

        ticksPerRise--;
        if (ticksPerRise <= 0) {
            ticksPerRise = originalTicks;
            if (Y_LEVEL > 318) {
                cancel();
            } else {
                for (int x = -50; x < 50; x++) {
                    for (int z = -50; z < 50; z++) {
                        world.getBlockAt(x, Y_LEVEL, z).setType(Material.LAVA, false);
                    }
                }
                Y_LEVEL++;
            }
        }

    }

}
