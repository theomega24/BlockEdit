package dev.omega24.blockedit.manager;

import com.google.common.collect.Lists;
import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.config.Config;
import dev.omega24.blockedit.operation.Operation;
import dev.omega24.blockedit.util.TickUtil;
import dev.omega24.blockedit.util.operation.OperationRunner;

import java.util.Collection;

public class OperationManager {
    private final BlockEdit plugin;
    private final Collection<OperationRunner> runners = Lists.newArrayList();

    public OperationManager(BlockEdit plugin) {
        this.plugin = plugin;
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::tick, 100, 20);
    }

    public void submit(Operation<?> operation) {
        runners.add(new OperationRunner(plugin, operation));
    }

    private void tick() {
        if (runners.isEmpty()) {
            return;
        }

        double time = Config.MAX_MSPT - TickUtil.MSPT_5S.average();
        double timePer = time / runners.size();
        if (time < 0) {
            // todo: figure out a better way to handle this (maybe send a message to the player?)
        }

        runners.forEach(runner -> runner.run(Math.abs((int) Math.floor(timePer / runner.averageTime()))));
        runners.removeIf(OperationRunner::isDone);
    }
}
