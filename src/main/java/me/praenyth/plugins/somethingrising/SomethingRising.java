package me.praenyth.plugins.somethingrising;

import cloud.commandframework.CommandManager;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.bukkit.BukkitCommandManager;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.meta.SimpleCommandMeta;
import me.praenyth.plugins.somethingrising.commands.RisingCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;

import java.util.function.Function;

public final class SomethingRising extends JavaPlugin {

    public static GameStatus CURRENT_STATUS = GameStatus.OPEN;

    @Override
    public void onEnable() {

        try {
            CommandManager<CommandSender> manager = new BukkitCommandManager<>(
                    /* Owning plugin */ this,
                    CommandExecutionCoordinator.simpleCoordinator(),
                    Function.identity(),
                    Function.identity()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        getLogger().info("Hopefully something can rise out of the ashes and kill players!");

    }

    @Override
    public void onDisable() {
        getLogger().info("Thanks for using!");
    }
}

