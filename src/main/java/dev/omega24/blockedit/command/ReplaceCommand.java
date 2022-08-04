package dev.omega24.blockedit.command;

import cloud.commandframework.Command.Builder;
import cloud.commandframework.bukkit.parsers.MaterialArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.paper.PaperCommandManager;
import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.config.Lang;
import dev.omega24.blockedit.operation.ReplaceOperation;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

public class ReplaceCommand extends Command {

    public ReplaceCommand(BlockEdit plugin, PaperCommandManager<CommandSender> manager) {
        super(plugin, manager);
    }

    @Override
    protected Builder<CommandSender> register(Builder<CommandSender> builder) {
        // todo: figure out a real block system, likely from NMS
        return builder.argument(MaterialArgument.of("from")).argument(MaterialArgument.of("to"));
    }

    @Override
    protected void execute(CommandContext<CommandSender> context) {
        Material from = context.get("from");
        Material to = context.get("to");

        ReplaceOperation operation = new ReplaceOperation(getUser(context), new ReplaceOperation.Data(from, to));
        plugin.getOperationManager().submit(operation);
    }

    @Override
    protected CommandData data() {
        return new CommandData(
                "replace",
                Lang.REPLACE_COMMAND_DESCRIPTION
        );
    }
}
