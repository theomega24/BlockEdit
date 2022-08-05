package dev.omega24.blockedit.impl;

import com.google.common.collect.Lists;
import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.config.Config;
import dev.omega24.blockedit.operation.Operation;
import dev.omega24.blockedit.util.TickUtil;
import dev.omega24.blockedit.util.operation.OperationRunner;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class OperationManager {
    private final BlockEdit plugin;
    private final Collection<OperationRunner> runners = Lists.newArrayList();
    private final Executor executor = Executors.newCachedThreadPool();

    public OperationManager(BlockEdit plugin) {
        this.plugin = plugin;
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::tick, 5, 1);
    }

    public void submit(Operation<?> operation) {
        if (!operation.getUser().getSelection().isValid()) {
            operation.getUser().send(""); // todo: lang this
            return;
        }

        CompletableFuture.supplyAsync(() -> new OperationRunner(plugin, operation), executor).thenAccept((runner) -> plugin.getServer().getScheduler().runTask(plugin, () -> {
            runners.add(runner);
            runner.run(1);
        }));
    }

    private void tick() {
        if (runners.isEmpty()) {
            return;
        }

        double time = Config.MAX_MSPT - TickUtil.MSPT_5S.average();
        double timePer = time / runners.size();

        runners.removeIf(OperationRunner::isDone);
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
