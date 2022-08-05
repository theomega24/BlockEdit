package dev.omega24.blockedit.command;

import cloud.commandframework.Command.Builder;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.paper.PaperCommandManager;
import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.api.wand.Wand;
import dev.omega24.blockedit.command.argument.WandArgument;
import dev.omega24.blockedit.config.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WandCommand extends Command {

    public WandCommand(BlockEdit plugin, PaperCommandManager<CommandSender> manager) {
        super(plugin, manager);
    }

    @Override
    protected Builder<CommandSender> register(Builder<CommandSender> builder) {
        return builder.argument(WandArgument.optional("wand", "selection")); // todo: no default value?
    }

    @Override
    protected void execute(CommandContext<CommandSender> context) {
        Wand wand = context.get("wand");
        if (!plugin.getWandManager().give((Player) context.getSender(), wand)) {
            Lang.send(context.getSender(), Lang.WAND_COMMAND_HOTBAR_FULL);
        }
    }

    @Override
    protected CommandData data() {
        return new CommandData(
                "wand",
                Lang.WAND_COMMAND_DESCRIPTION
        );
    }
}
