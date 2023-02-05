package inc.silly.saucers.plugins.somethingrising.runnables;

import inc.silly.saucers.plugins.somethingrising.RisingUtils;
import inc.silly.saucers.plugins.somethingrising.SomethingRising;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

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

    public void setTimeLeft(int seconds) {
        timeLeft = seconds;
    }

    @Override
    public void run() {
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
