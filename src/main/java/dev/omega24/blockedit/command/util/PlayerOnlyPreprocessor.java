package dev.omega24.blockedit.command.util;

import cloud.commandframework.execution.preprocessor.CommandPreprocessingContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessor;
import cloud.commandframework.services.types.ConsumerService;
import dev.omega24.blockedit.config.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerOnlyPreprocessor implements CommandPreprocessor<CommandSender> {

    @Override
    public void accept(@NotNull CommandPreprocessingContext<CommandSender> context) {
        // todo: figure out a way for commands to bypass this
        if (!(context.getCommandContext().getSender() instanceof Player)) {
            Lang.send(context.getCommandContext().getSender(), Lang.PLAYER_COMMAND_ONLY);
            ConsumerService.interrupt();
        }
    }
}
