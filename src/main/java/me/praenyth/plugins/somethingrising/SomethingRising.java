package me.praenyth.plugins.somethingrising;

import me.praenyth.plugins.somethingrising.commands.RisingCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class SomethingRising extends JavaPlugin {

    public static GameStatus CURRENT_STATUS = GameStatus.OPEN;

    @Override
    public void onEnable() {

        // register commands
        try {

        } catch (final Exception e) {
            getLogger().warning("Failed to initialize Brigadier support: " + e.getMessage());
        }

        getLogger().info("Hopefully something can rise out of the ashes and kill players!");

    }

    @Override
    public void onDisable() {
        getLogger().info("Thanks for using!");
    }
}

