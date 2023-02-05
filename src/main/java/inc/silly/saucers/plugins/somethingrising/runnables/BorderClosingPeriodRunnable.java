package inc.silly.saucers.plugins.somethingrising.runnables;

import inc.silly.saucers.plugins.somethingrising.GamePeriod;
import inc.silly.saucers.plugins.somethingrising.SomethingRising;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

public class BorderClosingPeriodRunnable extends BukkitRunnable {

    public BorderClosingPeriodRunnable(World world) {
        this.world = world;
    }

    private World world;
    private boolean startedBorderClosePhase = false;

    private int borderClosingSeconds = 1200;

    public void startBorderClose(Plugin plugin) {
        this.runTaskTimer(plugin, 0, 20);
    }
    @Override
    public void run() {
        if (!startedBorderClosePhase) {
            SomethingRising.CURRENT_STATUS = GamePeriod.BORDER;
            world.getWorldBorder().setSize(100, TimeUnit.SECONDS, borderClosingSeconds);
        }
    }
}
