package dev.omega24.blockedit.config;

import dev.omega24.blockedit.config.manager.Key;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.ConsoleCommandSender;

import java.util.List;

public class Lang {

    @Key("prefix.console")
    public static String CONSOLE_PREFIX = "[BlockEdit] ";
    @Key("prefix.player")
    public static String PLAYER_PREFIX = "<color:#006aff><b>BlockEdit »</b></color> ";

    @Key("command.wand.description")
    public static String WAND_COMMAND_DESCRIPTION = "Gives the player a wand";
    @Key("command.wand.inventory-full")
    public static String WAND_COMMAND_HOTBAR_FULL = "<red>Your hotbar must have at least one available space";
    @Key("command.wand.wand-not-found")
    public static String WAND_COMMAND_NOT_FOUND = "<red>The wand <gray><wand></gray> could not be found";

    @Key("command.error.player-only")
    public static String PLAYER_COMMAND_ONLY = "<red>You can only run this command as a player";

    @Key("wand.selection.name")
    public static String SELECTION_WAND_NAME = "Selection Wand";
    @Key("wand.selection.lore")
    public static List<String> SELECTION_WAND_LORE = List.of("Right click to set position one", "Left click to set position two");
    @Key("wand.selection.set-position")
    public static String SELECTION_WAND_SET_POSITION = "Set position <pos> to <x>, <y>, <z>";

    @Key("numbers.one")
    public static String NUMBER_ONE = "one";
    @Key("numbers.two")
    public static String NUMBER_TWO = "two";

    public static void send(Audience recipient, String message, TagResolver.Single... placeholders) {
        recipient.sendMessage(parse((recipient instanceof ConsoleCommandSender ? Lang.CONSOLE_PREFIX : Lang.PLAYER_PREFIX) + message, placeholders));
    }

    public static Component parse(String message, TagResolver.Single... placeholders) {
        return MiniMessage.miniMessage().deserialize(message, placeholders);
    }
}
