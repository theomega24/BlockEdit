package dev.omega24.blockedit.util.operation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.api.operation.Operation;
import dev.omega24.blockedit.util.LocationUtil;
import dev.omega24.blockedit.util.location.ChunkPosition;
import dev.omega24.blockedit.util.location.Position;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.block.data.CraftBlockData;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class OperationRunner {
    private final BlockEdit plugin;
    private final Operation<?> operation;
    private final Collection<Work> chunks = Lists.newArrayList();
    private final Collection<Work> doneChunks = Lists.newArrayList();
    private final Collection<Long> times = Lists.newArrayList();
    private final ServerLevel level;
    private final Player player;

    public OperationRunner(BlockEdit plugin, Operation<?> operation, Player player) {
        this.plugin = plugin;
        this.operation = operation;
        this.player = player;

        World world = plugin.getServer().getWorld(operation.getSelection().getWorldUUID());
        if (world == null) {
            throw new IllegalArgumentException("World not found");
        }
        this.level = ((CraftWorld) world).getHandle();
    }

    public Operation<?> getOperation() {
        return this.operation;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void start() {
        this.operation.getStats().start();

        Collection<Position> positions = this.operation.filterPositions();
        Collection<ChunkPosition> chunks = LocationUtil.getChunksFromBB(this.operation.getSelection().createBoundingBox());
        Map<ChunkPosition, Collection<Position>> chunksPositions = Maps.newHashMap();

        positions.forEach(position -> chunks.forEach(chunk -> {
            if (chunk.contains(position)) {
                chunksPositions.computeIfAbsent(chunk, (key) -> Lists.newArrayList()).add(position);
            }
        }));

        Collection<Work> work = Lists.newArrayList();
        chunksPositions.forEach((chunk, chunkPositions) -> work.add(new Work(chunkPositions)));
        this.chunks.addAll(work);

        this.operation.getStats().setBlocksChanged(positions.size());
        this.run(1);
    }

    public boolean tryDone() {
        if (!chunks.isEmpty()) {
            return false;
        }

        doneChunks.forEach(chunk -> chunk.positions().forEach(position -> {
            // todo: update all neighbors, heightmaps & lightmaps, and send to players
        }));

        this.operation.getStats().end();
        return true;
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
        Iterator<Work> iterator = chunks.iterator();
        while (iterator.hasNext() && amount > 0) {
            Work chunk = iterator.next();
            if (!Bukkit.isPrimaryThread()) {
                Bukkit.getScheduler().runTask(plugin, () -> this.handleChunk(chunk));
            } else {
                this.handleChunk(chunk);
            }

            doneChunks.add(chunk);
            iterator.remove();
            amount--;
        }
    }

    private void handleChunk(Work work) {
        work.positions().forEach(position -> level.setBlock(position.toBlockPos(), ((CraftBlockData) operation.change(position)).getState(), 16));
    }
}
