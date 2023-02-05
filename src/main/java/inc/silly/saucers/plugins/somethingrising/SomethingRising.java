package inc.silly.saucers.plugins.somethingrising;

import inc.silly.saucers.plugins.somethingrising.commands.RisingCommand;
import inc.silly.saucers.plugins.somethingrising.listeners.PlayerElimination;
import inc.silly.saucers.plugins.somethingrising.runnables.BlockRisingRunnable;
import inc.silly.saucers.plugins.somethingrising.runnables.BorderClosingPeriodRunnable;
import inc.silly.saucers.plugins.somethingrising.runnables.StarterPeriodRunnable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class SomethingRising extends JavaPlugin {

    public static GamePeriod CURRENT_STATUS = GamePeriod.LOBBY;
    public static StarterPeriodRunnable STARTER_PRE_EVENT;
    public static BorderClosingPeriodRunnable BORDER_PRE_EVENT;
    public static BlockRisingRunnable GAME;


    public static List<UUID> alivePlayers = new ArrayList<>();

    @Override
    public void onLoad() {

        super.onLoad();
    }

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new PlayerElimination(), this);
        GAME = new BlockRisingRunnable(this, getServer().getWorlds().get(0), 20);
        STARTER_PRE_EVENT = new StarterPeriodRunnable(this,6000);
        BORDER_PRE_EVENT = new BorderClosingPeriodRunnable(getServer().getWorlds().get(0));
        RisingCommand.init(this);

    }

    @Override
    public void onDisable() {

    }

}

