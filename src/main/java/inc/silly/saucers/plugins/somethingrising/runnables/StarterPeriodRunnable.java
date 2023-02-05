package inc.silly.saucers.plugins.somethingrising.runnables;

import inc.silly.saucers.plugins.somethingrising.RisingUtils;
import inc.silly.saucers.plugins.somethingrising.SomethingRising;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class StarterPeriodRunnable extends BukkitRunnable {

    Plugin plugin;

    private boolean runOnce = false;

    public StarterPeriodRunnable(Plugin plugin) {
        this.plugin = plugin;
    }

    private int timeLeft = 1200;
    private World world;
    private double worldBorderRadius = 500;
    public void startFromStarter(Plugin plugin, World world) {
        this.world = world;
        this.runTaskTimer(plugin, 0, 20);
    }

    public void setTimeLeft(int seconds) {
        timeLeft = seconds;
    }

    public void setWorldBorderRadius(double radius) {
        worldBorderRadius = radius;
    }

    @Override
    public void run() {

        if (!runOnce) {

            world.getWorldBorder().setSize(worldBorderRadius);
            runOnce = true;

        }

        timeLeft--;
        for (Player pl:
                Bukkit.getOnlinePlayers()) {
            pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN+ RisingUtils.displayTimer(timeLeft)));
        }
        if (timeLeft <= 0) {
            SomethingRising.BORDER_PRE_EVENT.startBorderClose(plugin);
            cancel();
        }
    }

}
