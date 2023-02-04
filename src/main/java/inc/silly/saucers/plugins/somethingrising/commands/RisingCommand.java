package inc.silly.saucers.plugins.somethingrising.commands;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.StaticArgument;
import cloud.commandframework.arguments.standard.IntegerArgument;
import cloud.commandframework.bukkit.BukkitCommandManager;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.keys.CloudKey;
import inc.silly.saucers.plugins.somethingrising.SomethingRising;
import org.bukkit.ChatColor;
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
                    generateCommand(manager, "start").handler(context -> {
                        if (context.getSender() instanceof Player) {
                            Player sender = ((Player) context.getSender()).getPlayer();
                            sender.getServer().getScheduler().runTaskTimer(plugin, SomethingRising.CURRENT_GAME, 80, 0);
                        }
                    })
            );

            manager.command(
                    generateCommand(manager, "setticksperrise").argument(IntegerArgument.builder("time")).handler(
                            context -> {
                                SomethingRising.CURRENT_GAME.setTicksPerRise(context.get("time"));
                                context.getSender().sendMessage(ChatColor.GREEN+"The ticks per lava rise has been set to " + context.get("time") + "!");
                            }
                    )
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Command.Builder<CommandSender> generateCommand(CommandManager<CommandSender> manager, String argument) {
        return Command.<CommandSender>newBuilder(
                "rising",
                manager.createDefaultCommandMeta()
        ).argument(StaticArgument.of(argument));
    }

}
