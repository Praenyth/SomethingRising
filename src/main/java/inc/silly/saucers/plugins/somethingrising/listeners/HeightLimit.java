package inc.silly.saucers.plugins.somethingrising.listeners;

import inc.silly.saucers.plugins.somethingrising.SomethingRising;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class HeightLimit implements Listener {

    @EventHandler
    public void onBlockOnBuildHeight(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getLocation().getY() > SomethingRising.GAME.lavaHeight+1) {

            event.setCancelled(true);

        }
    }

}
