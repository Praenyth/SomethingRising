package dev.sillysaucers.somethingrising.commands;

import cloud.commandframework.CommandManager;
import cloud.commandframework.arguments.standard.DoubleArgument;
import cloud.commandframework.arguments.standard.IntegerArgument;
import cloud.commandframework.bukkit.BukkitCommandManager;
import cloud.commandframework.bukkit.parsers.MaterialArgument;
import cloud.commandframework.bukkit.parsers.PlayerArgument;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import dev.sillysaucers.somethingrising.GamePeriod;
import dev.sillysaucers.somethingrising.RisingUtils;
import dev.sillysaucers.somethingrising.SomethingRising;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

import static dev.sillysaucers.somethingrising.SomethingRising.config;

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
                            .handler(context -> {
                                        if (context.getSender() instanceof Player) {
                                            switch (SomethingRising.CURRENT_STATUS) {
                                                case ACTIVE:
                                                case BORDER:
                                                case STARTER:
                                                    context.getSender().sendMessage(ChatColor.RED + "There is a game currently going on.");
                                                    break;
                                                case ENDED:
                                                    context.getSender().sendMessage(ChatColor.RED + "Please delete the world and restart your server before starting another game.");
                                                    break;
                                                case LOBBY:
                                                    SomethingRising.CURRENT_STATUS = GamePeriod.STARTER;
                                                    SomethingRising.STARTER_PRE_EVENT.startFromStarter(plugin, ((Player) context.getSender()).getWorld());
                                                    SomethingRising.damage.runTaskTimer(plugin, 1, 0);
                                                    for (Player pl : Bukkit.getOnlinePlayers()) {
                                                        if (context.getSender() instanceof Player) {
                                                            if (pl.hasPermission("something.rising.admin") && !pl.getName().equals(context.getSender().getName())) {
                                                                pl.sendMessage(ChatColor.ITALIC + "" + ChatColor.GRAY + "[" + context.getSender().getName() + ": Started the minigame.]");
                                                            }
                                                        }
                                                        SomethingRising.alivePlayers.add(pl.getUniqueId());
                                                        pl.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
                                                        pl.setGameMode(GameMode.SURVIVAL);
                                                        pl.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "The starter period has begun!");
                                                        pl.playSound(pl.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1, 1);
                                                    }
                                                    break;
                                            }
                                        }

                                    }
                            )
            );

            manager.command(
                    RisingUtils.generateCommand(manager, "setfinalbordertime")
                            .argument(IntegerArgument.builder("finalbordertime"))
                            .handler(
                                    context -> {
                                        switch (SomethingRising.CURRENT_STATUS) {
                                            case LOBBY:
                                                for (Player pl : Bukkit.getOnlinePlayers()) {
                                                    if (context.getSender() instanceof Player) {
                                                        if (pl.hasPermission("something.rising.admin") && !pl.getName().equals(context.getSender().getName())) {
                                                            pl.sendMessage(ChatColor.ITALIC + "" + ChatColor.GRAY + "[" + context.getSender().getName() + ": Set final border time to: " + context.get("finalbordertime") + ".]");
                                                        }
                                                    }
                                                }
                                                config.set("final-border", context.get("finalbordertime"));
                                                config.write();
                                                config.forceReload();
                                                SomethingRising.GAME.setFinalBorderTime(config.getInt("final-border"));
                                                context.getSender().sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "The final border time is now: " + context.get("finalbordertime") + " seconds!");
                                                break;
                                            case ENDED:
                                            case ACTIVE:
                                            case BORDER:
                                            case STARTER:
                                                context.getSender().sendMessage(ChatColor.RED + "You can't change that now!");
                                                break;
                                        }
                                    }
                            )
            );

            manager.command(
                    RisingUtils.generateCommand(manager, "setstarterborderradius")
                            .argument(DoubleArgument.builder("borderradius"))
                            .handler(
                                    context -> {
                                        switch (SomethingRising.CURRENT_STATUS) {
                                            case LOBBY:
                                                for (Player pl : Bukkit.getOnlinePlayers()) {
                                                    if (context.getSender() instanceof Player) {
                                                        if (pl.hasPermission("something.rising.admin") && !pl.getName().equals(context.getSender().getName())) {
                                                            pl.sendMessage(ChatColor.ITALIC + "" + ChatColor.GRAY + "[" + context.getSender().getName() + ": Set starter border to: " + context.get("borderradius") + ".]");
                                                        }
                                                    }
                                                }
                                                config.set("starter-border", context.get("borderradius"));
                                                config.write();
                                                config.forceReload();
                                                SomethingRising.STARTER_PRE_EVENT.setWorldBorderRadius(config.getInt("starter-border"));
                                                context.getSender().sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "The starter border is now: " + context.get("borderradius") + "!");
                                                break;
                                            case ENDED:
                                            case ACTIVE:
                                            case BORDER:
                                            case STARTER:
                                                context.getSender().sendMessage(ChatColor.RED + "You can't change that now!");
                                                break;
                                        }
                                    }
                            )
            );

            manager.command(
                    RisingUtils.generateCommand(manager, "setblock")
                            .argument(MaterialArgument.builder("block"))
                            .handler(context -> {
                                for (Player pl : Bukkit.getOnlinePlayers()) {
                                    if (context.getSender() instanceof Player) {
                                        if (pl.hasPermission("something.rising.admin") && !pl.getName().equals(context.getSender().getName())) {
                                            pl.sendMessage(ChatColor.ITALIC + "" + ChatColor.GRAY + "[" + context.getSender().getName() + ": Set block to " + context.get("block") + ".]");
                                        }
                                    }
                                }
                                config.set("block", context.get("block"));
                                config.write();
                                config.forceReload();
                                SomethingRising.GAME.setBlock(Material.valueOf((String) config.get("block")));
                                context.getSender().sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD +  "The block used in the block rising is now: " + context.get("block") + "!");
                            })
            );

            manager.command(
                    RisingUtils.generateCommand(manager, "setticksperrise")
                            .argument(IntegerArgument.builder("time"))
                            .handler(context -> {
                                        if (!(((int) context.get("time")) < 1 && ((int) context.get("time")) > 1200)) {
                                            for (Player pl : Bukkit.getOnlinePlayers()) {
                                                if (context.getSender() instanceof Player) {
                                                    if (pl.hasPermission("something.rising.admin") && !pl.getName().equals(context.getSender().getName())) {
                                                        pl.sendMessage(ChatColor.ITALIC + "" + ChatColor.GRAY + "[" + context.getSender().getName() + ": Set lava rise ticks to " + context.get("time") + ".]");
                                                    }
                                                }
                                            }
                                            config.set("ticks-per-rise", context.get("time"));
                                            config.write();
                                            config.forceReload();
                                            SomethingRising.GAME.setTicksPerRise(config.getInt("ticks-per-rise"));
                                            context.getSender().sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "The ticks per lava rise has been set to " + context.get("time") + "!");
                                        } else {
                                            context.getSender().sendMessage(ChatColor.RED + "That value is way too high! Are you trying to make your games last a million years?");
                                        }
                                    }
                            )
            );

            manager.command(
                    RisingUtils.generateCommand(manager, "setbordercloseseconds")
                            .argument(IntegerArgument.builder("closeSeconds"))
                            .handler(context -> {
                                switch (SomethingRising.CURRENT_STATUS) {
                                    case LOBBY:
                                        for (Player pl : Bukkit.getOnlinePlayers()) {
                                            if (context.getSender() instanceof Player) {
                                                if (pl.hasPermission("something.rising.admin") && !pl.getName().equals(context.getSender().getName())) {
                                                    pl.sendMessage(ChatColor.ITALIC + "" + ChatColor.GRAY + "[" + context.getSender().getName() + ": Set border close seconds to " + context.get("closeSeconds") + ".]");
                                                }
                                            }
                                        }
                                        config.set("border-close-seconds", context.get("closeSeconds"));
                                        config.write();
                                        config.forceReload();
                                        SomethingRising.BORDER_PRE_EVENT.setBorderClosingSeconds(config.getInt("border-close-seconds"));
                                        context.getSender().sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "The seconds it takes for the border to close is now " + context.get("closeSeconds") + " seconds!");
                                        break;
                                    case ENDED:
                                    case ACTIVE:
                                    case BORDER:
                                    case STARTER:
                                        context.getSender().sendMessage(ChatColor.RED + "You can't change that now!");
                                        break;
                                }
                            })

            );

            manager.command(
                    RisingUtils.generateCommand(manager, "starterperiodtime")
                            .argument(IntegerArgument.builder("starterseconds"))
                            .handler(context -> {
                                switch (SomethingRising.CURRENT_STATUS) {
                                    case LOBBY:
                                        for (Player pl : Bukkit.getOnlinePlayers()) {
                                            if (context.getSender() instanceof Player) {
                                                if (pl.hasPermission("something.rising.admin") && !pl.getName().equals(context.getSender().getName())) {
                                                    pl.sendMessage(ChatColor.ITALIC + "" + ChatColor.GRAY + "[" + context.getSender().getName() + ": Set the amount of time in the starter period to " + context.get("starterseconds") + ".]");
                                                }
                                            }
                                        }
                                        config.set("starter-period-length", context.get("starterseconds"));
                                        config.write();
                                        config.forceReload();
                                        SomethingRising.STARTER_PRE_EVENT.setTimeLeft(config.getInt("starter-period-length"));
                                        context.getSender().sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "The starter period will now last " + context.get("starterseconds") + " seconds!");
                                        break;
                                    case ENDED:
                                    case ACTIVE:
                                    case BORDER:
                                    case STARTER:
                                        context.getSender().sendMessage(ChatColor.RED + "You can't change that now!");
                                        break;
                                }
                            })

            );

            manager.command(
                    RisingUtils.generateCommand(manager, "setblockheight")
                            .argument(IntegerArgument.builder("blockheight"))
                            .handler(
                                    context -> {
                                        switch (SomethingRising.CURRENT_STATUS) {
                                            case LOBBY:
                                                for (Player pl : Bukkit.getOnlinePlayers()) {
                                                    if (context.getSender() instanceof Player) {
                                                        if (pl.hasPermission("something.rising.admin") && !pl.getName().equals(context.getSender().getName())) {
                                                            pl.sendMessage(ChatColor.ITALIC + "" + ChatColor.GRAY + "[" + context.getSender().getName() + ": Set block height to: " + context.get("blockheight") + ".]");
                                                        }
                                                    }
                                                }
                                                config.set("block-height", context.get("blockheight"));
                                                config.write();
                                                config.forceReload();
                                                SomethingRising.GAME.setBlockHeightLimit(config.getInt("block-height"));
                                                context.getSender().sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "The block height can now reach: " + context.get("blockheight") + "!");
                                                break;
                                            case ENDED:
                                            case ACTIVE:
                                            case BORDER:
                                            case STARTER:
                                                context.getSender().sendMessage(ChatColor.RED + "You can't change that now!");
                                                break;
                                        }

                                    }
                            )
            );

            manager.command(
                    RisingUtils.generateCommand(manager, "setbuildheight")
                            .argument(IntegerArgument.builder("buildheight"))
                            .handler(
                                    context -> {
                                        switch (SomethingRising.CURRENT_STATUS) {
                                            case LOBBY:
                                                for (Player pl : Bukkit.getOnlinePlayers()) {
                                                    if (context.getSender() instanceof Player) {
                                                        if (pl.hasPermission("something.rising.admin") && !pl.getName().equals(context.getSender().getName())) {
                                                            pl.sendMessage(ChatColor.GRAY +""+ChatColor.ITALIC + "[" + context.getSender().getName() + ": Set build height to: " + context.get("buildheight") + ".]");
                                                        }
                                                    }
                                                }
                                                config.set("build-height", context.get("buildheight"));
                                                config.write();
                                                config.forceReload();
                                                SomethingRising.GAME.setBuildHeight(config.getInt("build-height"));
                                                context.getSender().sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "The build height is now: " + context.get("buildheight") + "!");
                                                break;
                                            case ENDED:
                                            case ACTIVE:
                                            case BORDER:
                                            case STARTER:
                                                context.getSender().sendMessage(ChatColor.RED + "You can't change that now!");
                                                break;
                                        }

                                    }
                            )
            );

            manager.command(
                    RisingUtils.generateCommand(manager, "revive")
                            .argument(PlayerArgument.builder("revivedPlayer"))
                            .handler(
                                    context -> {

                                        Player revivedPlayer = context.get("revivedPlayer");

                                        switch (SomethingRising.CURRENT_STATUS) {
                                            case LOBBY:
                                            case ENDED:
                                                context.getSender().sendMessage(ChatColor.RED + "This is not a period where you can revive people!");
                                                break;
                                            case STARTER:
                                            case BORDER:
                                            case ACTIVE:
                                                if (!SomethingRising.alivePlayers.contains(revivedPlayer.getUniqueId())) {
                                                    for (Player pl : Bukkit.getOnlinePlayers()) {
                                                        if (context.getSender() instanceof Player) {
                                                            if (pl.hasPermission("something.rising.admin") && !pl.getName().equals(context.getSender().getName())) {
                                                                pl.sendMessage(ChatColor.ITALIC + "" + ChatColor.GRAY + "[" + context.getSender().getName() + ": Revived " + revivedPlayer.getName() + ".]");
                                                            }
                                                        }
                                                    }

                                                    if (context.getSender() instanceof Player) {

                                                        Player teleportTo = (Player) context.getSender();
                                                        revivedPlayer.teleport(teleportTo);

                                                    }

                                                    revivedPlayer.setGameMode(GameMode.SURVIVAL);

                                                    SomethingRising.alivePlayers.add(revivedPlayer.getUniqueId());

                                                    for (Player pl :
                                                            Bukkit.getOnlinePlayers()) {
                                                        pl.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + revivedPlayer.getDisplayName() + ChatColor.GREEN + " has been revived!");
                                                    }
                                                } else {
                                                    context.getSender().sendMessage(ChatColor.RED + "That player is still alive!");
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
