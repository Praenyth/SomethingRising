package dev.sillysaucers.somethingrising.runnables;

import dev.sillysaucers.somethingrising.GamePeriod;
import dev.sillysaucers.somethingrising.RisingUtils;
import dev.sillysaucers.somethingrising.SomethingRising;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
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

    public void setBorderClosingSeconds(int seconds) {
        borderClosingSeconds = seconds;
    }
    @Override
    public void run() {
        if (!startedBorderClosePhase) {
            SomethingRising.CURRENT_STATUS = GamePeriod.BORDER;
            world.getWorldBorder().setSize(100, TimeUnit.SECONDS, borderClosingSeconds);
            for (Player pl:
                    Bukkit.getOnlinePlayers()) {
                pl.sendMessage(ChatColor.YELLOW+"The border will now close in! PVP is now enabled.");
            }
            startedBorderClosePhase = true;
        }
        borderClosingSeconds--;
        for (Player pl:
                Bukkit.getOnlinePlayers()) {
            pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED+ RisingUtils.displayTimer(borderClosingSeconds)));
        }
        if (borderClosingSeconds <= 0) {
            SomethingRising.GAME.startLavaRise();
            cancel();
        }

    }
}
