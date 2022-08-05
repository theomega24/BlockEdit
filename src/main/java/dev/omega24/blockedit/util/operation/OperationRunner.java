package dev.omega24.blockedit.util.operation;

import com.google.common.collect.Lists;
import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.operation.Operation;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Collection;
import java.util.Iterator;

public class OperationRunner {
    private final BlockEdit plugin;
    private final Operation<?> operation;
    private final Collection<ChunkWork> chunks;
    private final Collection<Long> times = Lists.newArrayList();

    public OperationRunner(BlockEdit plugin, Operation<?> operation) {
        this.plugin = plugin;
        this.operation = operation;
        this.chunks = operation.splitChunkWork();

        this.run(1);
    }

    public boolean isDone() {
        return chunks.isEmpty();
    }

    public long averageTime() {
        if (times.isEmpty()) {
            return 1;
        }

        long total = 0;
        for (long time : times) {
            total += time;
        }

        return total / times.size();
    }

    public void run(int amount) {
        Collection<ChunkWork> toRemove = Lists.newArrayList();
        chunks.stream().limit(amount).forEach(chunk -> {
            this.handleChunkWork(chunk);
            toRemove.add(chunk);
        });
        chunks.removeAll(toRemove);
    }

    private void handleChunkWork(ChunkWork work) {
        World world = plugin.getServer().getWorld(work.chunk().worldUUID());
        if (world == null) {
            throw new IllegalStateException("Could not find world with UUID " + work.chunk().worldUUID());
        }

        // todo: figure out if this system can cause light/block update lag
        world.getChunkAtAsync(work.chunk().x(), work.chunk().z(), true).thenAccept((chunk) -> {
            long startTime = System.currentTimeMillis();

            work.positions().forEach(position -> {
                Block block = chunk.getBlock(position.x(), position.y(), position.z()); // todo: everything gets held up on this one line (probably because of the async)
                operation.change(block);
            });

            long endTime = System.currentTimeMillis();
            times.add(endTime - startTime);
        });
    }
}
