package dev.omega24.blockedit.util.operation;

import com.google.common.collect.Lists;
import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.operation.Operation;
import net.minecraft.world.level.Level;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;

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

    public void resetAverageTime() {
        times.clear();
    }

    public void run(int amount) {
        Iterator<ChunkWork> iterator = chunks.iterator();
        while (iterator.hasNext() && amount > 0) {
            ChunkWork chunk = iterator.next();
            this.handleChunkWork(chunk);
            iterator.remove();
            amount--;
        }
    }

    private void handleChunkWork(ChunkWork work) {
        World world = plugin.getServer().getWorld(work.chunk().worldUUID());
        if (world == null) {
            throw new IllegalStateException("World not found");
        }

        // todo: use nms here
        // todo: figure out if this system can cause light/block update lag
        world.getChunkAtAsync(work.chunk().x(), work.chunk().z(), true).thenAccept((chunk) -> {
            long startTime = System.currentTimeMillis();

            work.positions().forEach(position -> operation.change(world.getBlockAt(position.x(), position.y(), position.z())));

            long endTime = System.currentTimeMillis();
            times.add(endTime - startTime);
        });
    }
}
