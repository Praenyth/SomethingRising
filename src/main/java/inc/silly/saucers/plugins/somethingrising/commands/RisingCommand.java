package inc.silly.saucers.plugins.somethingrising.commands;

import cloud.commandframework.CommandManager;
import cloud.commandframework.arguments.standard.IntegerArgument;
import cloud.commandframework.bukkit.BukkitCommandManager;
import cloud.commandframework.bukkit.parsers.PlayerArgument;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import inc.silly.saucers.plugins.somethingrising.GamePeriod;
import inc.silly.saucers.plugins.somethingrising.RisingUtils;
import inc.silly.saucers.plugins.somethingrising.SomethingRising;
import org.bukkit.*;
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
                    RisingUtils.generateCommand(manager, "start")
                            .permission("something.rising.admin")
                            .handler(context -> {
                                switch (SomethingRising.CURRENT_STATUS) {
                                    case ACTIVE:
                                    case BORDER:
                                    case STARTER:
                                        context.getSender().sendMessage(ChatColor.RED+"There is a game currently going on.");
                                        break;
                                    case ENDED:
                                        context.getSender().sendMessage(ChatColor.RED+"Please restart your server before starting another game.");
                                        break;
                                    case LOBBY:
                                        SomethingRising.CURRENT_STATUS = GamePeriod.STARTER;
                                        SomethingRising.GAME.startLavaRise();
                                        for (Player pl : Bukkit.getOnlinePlayers()) {
                                            if (context.getSender() instanceof Player) {
                                                if (pl.hasPermission("something.rising.admin") && !pl.getName().equals(context.getSender().getName())) {
                                                    pl.sendMessage(ChatColor.GRAY+"["+ context.getSender().getName()+": Started the lava rise.]");
                                                }
                                            }
                                            SomethingRising.alivePlayers.add(pl.getUniqueId());
                                            pl.sendMessage(ChatColor.GREEN + "The lava will now be rising! Good luck!");
                                        }
                                        break;
                                }

                            }
                    )
            );

            manager.command(
                    RisingUtils.generateCommand(manager, "setticksperrise")
                            .permission("something.rising.admin")
                            .argument(IntegerArgument.builder("time"))
                            .handler(context -> {
                                if (!(((int)context.get("time")) < 1 && ((int)context.get("time")) > 1200)) {
                                    for (Player pl: Bukkit.getOnlinePlayers()) {
                                        if (context.getSender() instanceof Player) {
                                            if (pl.hasPermission("something.rising.admin") && !pl.getName().equals(context.getSender().getName())) {
                                                pl.sendMessage(ChatColor.GRAY+"["+ context.getSender().getName()+": Set lava rise ticks to "+ context.get("time") +".]");
                                            }
                                        }
                                    }
                                    SomethingRising.GAME.setTicksPerRise(context.get("time"));
                                    context.getSender().sendMessage(ChatColor.GREEN+"The ticks per lava rise has been set to " + context.get("time") + "!");
                                } else {
                                context.getSender().sendMessage(ChatColor.RED+"That value is way too high! Are you trying to make your games last a million years?");
                                }
                            }
                    )
            );

            manager.command(
                    RisingUtils.generateCommand(manager, "revive")
                            .permission("something.rising.admin")
                            .argument(PlayerArgument.builder("revivedPlayer"))
                            .handler(
                                    context -> {

                                        Player revivedPlayer = context.get("revivedPlayer");

                                        switch (SomethingRising.CURRENT_STATUS) {
                                            case LOBBY:
                                            case ENDED:
                                            case STARTER:
                                            case BORDER:
                                                context.getSender().sendMessage(ChatColor.RED+"This is not a period where you can revive people!");
                                                break;
                                            case ACTIVE:
                                                if (SomethingRising.alivePlayers.contains(revivedPlayer.getUniqueId())) {
                                                    for (Player pl: Bukkit.getOnlinePlayers()) {
                                                        if (context.getSender() instanceof Player) {
                                                            if (pl.hasPermission("something.rising.admin") && !pl.getName().equals(context.getSender().getName())) {
                                                                pl.sendMessage(ChatColor.GRAY+"["+ context.getSender().getName()+": Revived "+revivedPlayer.getName()+".]");
                                                            }
                                                        }
                                                    }

                                                    Location newLocation = revivedPlayer.getLastDeathLocation().add(0, 10, 0);

                                                    revivedPlayer.getWorld().getBlockAt(newLocation.clone().add(0, -1,0)).setType(Material.GLASS, true);
                                                    revivedPlayer.teleport(newLocation);
                                                    revivedPlayer.setGameMode(GameMode.SURVIVAL);

                                                    SomethingRising.alivePlayers.add(revivedPlayer.getUniqueId());

                                                    for (Player pl:
                                                            Bukkit.getOnlinePlayers()) {
                                                        pl.sendMessage(ChatColor.YELLOW+revivedPlayer.getDisplayName()+ChatColor.GREEN+" has been revived!");
                                                    }
                                                } else {
                                                    context.getSender().sendMessage(ChatColor.RED+"That player is still alive!");
                                                }
                                                break;
                                        }


                                    }
                    )
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
