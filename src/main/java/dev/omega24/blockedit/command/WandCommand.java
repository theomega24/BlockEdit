package dev.omega24.blockedit.command;

import cloud.commandframework.Command.Builder;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.paper.PaperCommandManager;
import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.command.argument.WandArgument;
import dev.omega24.blockedit.config.Lang;
import dev.omega24.blockedit.wand.AbstractWand;
import org.bukkit.command.CommandSender;

public class WandCommand extends AbstractCommand {

    public WandCommand(BlockEdit plugin, PaperCommandManager<CommandSender> manager) {
        super(plugin, manager);
    }

    @Override
    protected Builder<CommandSender> register(Builder<CommandSender> builder) {
        return builder.argument(WandArgument.optional("wand", "selection"));
    }

    @Override
    protected void execute(CommandContext<CommandSender> context) {
        AbstractWand wand = context.get("wand");
        wand.give(getPlayer(context));
    }

    @Override
    protected CommandData data() {
        return new CommandData(
                "wand",
                Lang.WAND_COMMAND_DESCRIPTION
        );
    }
}
