package dev.omega24.blockedit.command;

import cloud.commandframework.Command.Builder;
import cloud.commandframework.bukkit.parsers.MaterialArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.paper.PaperCommandManager;
import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.config.Lang;
import dev.omega24.blockedit.operation.SetOperation;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

public class SetCommand extends Command {

    public SetCommand(BlockEdit plugin, PaperCommandManager<CommandSender> manager) {
        super(plugin, manager);
    }

    @Override
    protected Builder<CommandSender> register(Builder<CommandSender> builder) {
        return builder.argument(MaterialArgument.of("block")); // todo: figure out a real block system, likely from NMS
    }

    @Override
    protected void execute(CommandContext<CommandSender> context) {
        Material material = context.get("block");
        SetOperation operation = new SetOperation(getUser(context), new SetOperation.Data(material));
        plugin.getOperationManager().submit(operation);
    }

    @Override
    protected CommandData data() {
        return new CommandData(
                "set",
                Lang.SET_COMMAND_DESCRIPTION
        );
    }
}
