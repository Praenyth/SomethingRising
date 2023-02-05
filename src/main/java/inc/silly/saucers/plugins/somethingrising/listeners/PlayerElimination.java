package inc.silly.saucers.plugins.somethingrising.listeners;

import inc.silly.saucers.plugins.somethingrising.SomethingRising;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerElimination implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        switch (SomethingRising.CURRENT_STATUS) {
            case LOBBY:
            case STARTER:
            case BORDER:
            case ENDED:
                break;
            case ACTIVE:
                if (SomethingRising.alivePlayers.contains(player.getUniqueId())) {

                    event.setKeepInventory(true);
                    player.setGameMode(GameMode.SPECTATOR);
                    SomethingRising.alivePlayers.remove(player.getUniqueId());
                    if (SomethingRising.alivePlayers.size() == 1) {

                        Player winner = Bukkit.getPlayer(SomethingRising.alivePlayers.get(0));
                        for (Player pl:
                             Bukkit.getOnlinePlayers()) {
                            pl.sendTitle(ChatColor.GREEN+winner.getName()+" has won!","",10,70,20);
                        }

                    } else {
                        event.setDeathMessage(
                                ChatColor.RED+player.getName()+
                                        " has been eliminated. (" +
                                        SomethingRising.alivePlayers.size()+
                                        "/"+
                                        Bukkit.getOnlinePlayers().size()+")"
                        );
                    }

                } else {
                    event.setDeathMessage("");
                }
                break;
        }

    }


    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {

            switch (SomethingRising.CURRENT_STATUS) {

                case STARTER:
                case ENDED:
                case LOBBY:
                    event.setCancelled(true);

            }

        }
    }

}
