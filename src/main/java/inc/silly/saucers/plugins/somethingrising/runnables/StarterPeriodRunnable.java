package inc.silly.saucers.plugins.somethingrising.runnables;

import inc.silly.saucers.plugins.somethingrising.SomethingRising;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

public class StarterPeriodRunnable extends BukkitRunnable {

    Plugin plugin;

    public StarterPeriodRunnable(Plugin plugin, int time) {
        this.plugin = plugin;
        this.timeLeft = time;
    }

    private int timeLeft;
    public void startFromStarter(Plugin plugin) {
        this.runTaskTimer(plugin, 0, 20);
    }

    @Override
    public void run() {
        for (Player pl:
                Bukkit.getOnlinePlayers()) {
            pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN+displayTimer()));
        }
        if (timeLeft <= 0) {
            SomethingRising.BORDER_PRE_EVENT.startBorderClose(plugin);
            cancel();
        }
    }

    private String displayTimer() {
        int hours = (int) TimeUnit.SECONDS.toHours(timeLeft);
        int minutes = (int) (TimeUnit.SECONDS.toMinutes(timeLeft) - TimeUnit.HOURS.toMinutes(hours));
        int seconds = (int) (TimeUnit.SECONDS.toSeconds(timeLeft) - TimeUnit.MINUTES.toSeconds(minutes));
        if (seconds < 0) {
            seconds = 0;
        }
        if (hours == 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }
}
