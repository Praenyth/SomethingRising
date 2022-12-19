package me.praenyth.plugins.somethingrising.commands;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.processing.CommandContainer;
import org.bukkit.command.CommandSender;

@CommandContainer
public class RisingCommand {

    @CommandDescription("Oh me oh my, how silly!")
    public static void risingCommandBuild(
        CommandSender sender,
        @Argument("test") int input
    ) {
        sender.sendMessage(String.valueOf(input));
    }

}
