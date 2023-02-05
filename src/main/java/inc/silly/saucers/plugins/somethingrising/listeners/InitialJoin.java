package inc.silly.saucers.plugins.somethingrising.listeners;

import inc.silly.saucers.plugins.somethingrising.GamePeriod;
import inc.silly.saucers.plugins.somethingrising.SomethingRising;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class InitialJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (SomethingRising.CURRENT_STATUS == GamePeriod.LOBBY) {

            event.getPlayer().setGameMode(GameMode.ADVENTURE);

        } else {

            event.getPlayer().setGameMode(GameMode.SPECTATOR);

        }
    }

}
