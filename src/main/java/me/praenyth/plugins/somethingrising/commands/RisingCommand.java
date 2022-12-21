package me.praenyth.plugins.somethingrising.commands;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.arguments.standard.EnumArgument;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.bukkit.BukkitCommandManager;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import me.praenyth.plugins.somethingrising.GameStatus;
import me.praenyth.plugins.somethingrising.SomethingRising;
import me.praenyth.plugins.somethingrising.commands.runnable.LavaRising;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class RisingCommand {

    public static void init(SomethingRising plugin) {
        try {

            // command manager
            CommandManager<CommandSender> manager = new BukkitCommandManager<>(
                    plugin,
                    CommandExecutionCoordinator.simpleCoordinator(),
                    Function.identity(),
                    Function.identity()
            );

            // something rising command
            manager.command(
                    Command.<CommandSender>newBuilder(
                            "rising",
                            manager.createDefaultCommandMeta(),
                            "sr")
                            .argument(StringArgument.builder( "start"))
                            .handler(context -> {
                                if (context.getSender() instanceof Player) {
                                    Player sender = ((Player) context.getSender()).getPlayer();
                                    sender.getServer().getScheduler().runTaskTimer(plugin, new LavaRising(sender.getWorld()), 80, 0);
                                }
                            })
                            .build()
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
