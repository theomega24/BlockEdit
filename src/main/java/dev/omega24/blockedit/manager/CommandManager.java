package dev.omega24.blockedit.manager;

import cloud.commandframework.brigadier.CloudBrigadierManager;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.common.collect.ImmutableList;
import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.command.Command;
import dev.omega24.blockedit.command.WandCommand;
import dev.omega24.blockedit.command.processor.PlayerOnlyPreprocessor;
import org.bukkit.command.CommandSender;

import java.util.function.Function;

public class CommandManager {

    public static void load(BlockEdit plugin) throws Exception {
        PaperCommandManager<CommandSender> manager = new PaperCommandManager<>(plugin, AsynchronousCommandExecutionCoordinator.simpleCoordinator(), Function.identity(), Function.identity());

        if (manager.hasCapability(CloudBukkitCapabilities.NATIVE_BRIGADIER)) {
            manager.registerBrigadier();

            CloudBrigadierManager<?, ?> brigadier = manager.brigadierManager();
            if (brigadier != null) {
                brigadier.setNativeNumberSuggestions(false);
            }
        }

        if (manager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            manager.registerAsynchronousCompletions();
        }

        manager.registerCommandPreProcessor(new PlayerOnlyPreprocessor());

        ImmutableList.of(
                new WandCommand(plugin, manager)
        ).forEach(Command::register);
    }
}
