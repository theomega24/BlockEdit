package dev.omega24.blockedit.util.operation;

import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.operation.Operation;
import dev.omega24.blockedit.util.location.ChunkWork;
import org.bukkit.World;

import java.util.Collection;

public class OperationRunner {
    private final BlockEdit plugin;
    private final Operation operation;
    private final Collection<ChunkWork> chunks;

    public OperationRunner(BlockEdit plugin, Operation operation) {
        this.plugin = plugin;
        this.operation = operation;
        this.chunks = operation.splitChunkWork();
    }

    public void start() {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            // todo: dynamically determine chunk amount based off of the current server mspt
            chunks.stream().limit(10).forEach(chunk -> {
                chunks.remove(chunk);
                this.handleChunkWork(chunk);
            });
        }, 0L, 20L);
    }

    private void handleChunkWork(ChunkWork work) {
        World world = plugin.getServer().getWorld(work.chunk().worldUUID());
        if (world == null) {
            throw new IllegalStateException("Could not find world with UUID " + work.chunk().worldUUID());
        }

        // todo: figure out if this system can cause light/block update lag
        world.getChunkAtAsync(work.chunk().x(), work.chunk().z()).thenAccept((chunk) -> {
            chunk.addPluginChunkTicket(plugin);

            work.positions().forEach(position -> {
                operation.change(chunk.getBlock(position.x(), position.y(), position.z()));
            });

            chunk.removePluginChunkTicket(plugin);
        });
    }
}
