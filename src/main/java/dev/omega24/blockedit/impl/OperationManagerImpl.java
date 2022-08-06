package dev.omega24.blockedit.impl;

import com.google.common.collect.Lists;
import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.api.operation.Operation;
import dev.omega24.blockedit.api.operation.OperationManager;
import dev.omega24.blockedit.config.Config;
import dev.omega24.blockedit.config.Lang;
import dev.omega24.blockedit.util.TickUtil;
import dev.omega24.blockedit.util.operation.OperationRunner;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class OperationManagerImpl implements OperationManager {
    private final BlockEdit plugin;
    private final Collection<OperationRunner> runners = Lists.newArrayList();
    private final Executor executor = Executors.newCachedThreadPool();

    public OperationManagerImpl(BlockEdit plugin) {
        this.plugin = plugin;
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::tick, 5, 1);
    }

    @Override
    public void submit(Player player, Operation<?> operation) {
        // todo: only send when its from this plugin
        if (!operation.getSelection().isValid()) {
            Lang.send(player, Lang.OPERATION_NO_SELECTION);
            return;
        }

        CompletableFuture.supplyAsync(() -> new OperationRunner(plugin, operation, player), executor).thenAccept((runner) -> {
            runners.add(runner);
            runner.start();
        });
    }

    private void tick() {
        if (runners.isEmpty()) {
            return;
        }

        double time = Config.MAX_MSPT - TickUtil.MSPT_5S.average();
        double timePer = time / runners.size();

        runners.removeIf(runner -> {
            boolean done = runner.tryDone();
            if (done) {
                long seconds = runner.getOperation().getStats().getTimeInMillis() / 1000;
                long minutes = seconds / 60;
                long leftoverSeconds = seconds % 60;

                // todo: only send when its from this plugin
                Lang.send(
                        runner.getPlayer(),
                        Lang.OPERATION_FINISHED,
                        Placeholder.unparsed("blocks", String.format("%,d", runner.getOperation().getStats().getBlocksChanged())),
                        Placeholder.unparsed("minutes", String.valueOf(minutes)),
                        Placeholder.unparsed("seconds", String.valueOf(leftoverSeconds))
                );
            }
            return done;
        });

        runners.forEach(runner -> {
            long average = runner.averageTime();
            if (average > timePer) {
                runner.resetAverageTime();
                runner.run(1);
            } else {
                runner.run(Math.abs((int) (timePer / average)));
            }
        });
    }
}
