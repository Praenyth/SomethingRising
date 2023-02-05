package inc.silly.saucers.plugins.somethingrising;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.arguments.StaticArgument;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class RisingUtils {

    public static Command.Builder<CommandSender> generateCommand(CommandManager<CommandSender> manager, String argument) {
        return Command.<CommandSender>newBuilder(
                "rising",
                manager.createDefaultCommandMeta()
        ).argument(StaticArgument.of(argument));
    }

    public static Player chooseRandomPlayer() {
        ArrayList<String> allPlayers = new ArrayList<>();
        for(Player pl : Bukkit.getOnlinePlayers()) {
            allPlayers.add(pl.getName());
            System.out.println(pl.getName());
        }
        int random = new Random().nextInt(allPlayers.size());

        return Bukkit.getPlayer(allPlayers.get(random));
    }

}
