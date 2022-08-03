package dev.omega24.blockedit.command;

import cloud.commandframework.Command.Builder;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.config.Config;
import dev.omega24.blockedit.user.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractCommand {
    protected final BlockEdit plugin;
    private final PaperCommandManager<CommandSender> manager;

    public AbstractCommand(BlockEdit plugin, PaperCommandManager<CommandSender> manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    public void register() {
        this.manager.command(this.register(
                this.manager.commandBuilder(Config.COMMAND_PREFIX + this.data().name())
                        .meta(CommandMeta.DESCRIPTION, this.data().description())
                        .permission(this.permission())
        ).handler(this::execute));
    }

    protected User getPlayer(CommandContext<CommandSender> context) {
        if (!(context.getSender() instanceof Player player)) {
            throw new IllegalCallerException();
        }

        return User.get(player);
    }

    protected abstract Builder<CommandSender> register(Builder<CommandSender> builder);

    protected abstract void execute(CommandContext<CommandSender> context);

    protected abstract CommandData data();

    protected String permission() {
        return "blockedit.command." + data().name();
    }

    public record CommandData(
            String name,
            String description
    ) {}
}
