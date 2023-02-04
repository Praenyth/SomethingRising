package inc.silly.saucers.plugins.somethingrising;

import inc.silly.saucers.plugins.somethingrising.commands.RisingCommand;
import inc.silly.saucers.plugins.somethingrising.runnables.LavaRising;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class SomethingRising extends JavaPlugin {

    public static GameStatus CURRENT_STATUS = GameStatus.LOBBY;

    public Plugin plugin = this;

    public static LavaRising CURRENT_GAME;

    @Override
    public void onEnable() {

        CURRENT_GAME = new LavaRising(this, getServer().getWorlds().get(0), 20);
        RisingCommand.init(this);

    }

    @Override
    public void onDisable() {

    }
}

