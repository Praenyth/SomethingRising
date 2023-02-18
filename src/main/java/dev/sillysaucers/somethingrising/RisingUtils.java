package dev.sillysaucers.somethingrising;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.arguments.StaticArgument;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RisingUtils {

    public static Command.Builder<CommandSender> generateCommand(CommandManager<CommandSender> manager, String argument) {
        return Command.<CommandSender>newBuilder(
                        "rising",
                        manager.createDefaultCommandMeta()
                ).argument(StaticArgument.of(argument))
                .permission("something.rising.admin");
    }

    public static Player chooseRandomPlayer() {
        ArrayList<String> allPlayers = new ArrayList<>();
        for (Player pl : Bukkit.getOnlinePlayers()) {
            allPlayers.add(pl.getName());
            System.out.println(pl.getName());
        }
        int random = new Random().nextInt(allPlayers.size());

        return Bukkit.getPlayer(allPlayers.get(random));
    }

    public static String displayTimer(int sec) {
        int hours = (int) TimeUnit.SECONDS.toHours(sec);
        int minutes = (int) (TimeUnit.SECONDS.toMinutes(sec) - TimeUnit.HOURS.toMinutes(hours));
        int seconds = (int) (TimeUnit.SECONDS.toSeconds(sec) - TimeUnit.MINUTES.toSeconds(minutes));
        if (seconds < 0) {
            seconds = 0;
        }
        if (hours == 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }

}
