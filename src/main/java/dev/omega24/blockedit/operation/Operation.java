package dev.omega24.blockedit.operation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.omega24.blockedit.user.Selection;
import dev.omega24.blockedit.user.User;
import dev.omega24.blockedit.util.location.ChunkPosition;
import dev.omega24.blockedit.util.operation.ChunkWork;
import dev.omega24.blockedit.util.LocationUtil;
import dev.omega24.blockedit.util.location.Position;
import org.bukkit.block.Block;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public abstract class Operation<D> {
    protected final User user;
    private final D data;

    public Operation(User user, D data) {
        this.user = user;
        this.data = data;
    }

    public User getUser() {
        return this.user;
    }

    public abstract void change(Block block);

    protected abstract Collection<Position> filterPositions();

    protected D data() {
        return this.data;
    }

    public Collection<ChunkWork> splitChunkWork() {
        Collection<Position> positions = this.filterPositions();
        Collection<ChunkPosition> chunks = LocationUtil.getChunksFromBB(this.user.getSelection().createBoundingBox(), this.user.getSelection().getWorldUUID());
        Collection<ChunkWork> work = Lists.newArrayList();

        Map<ChunkPosition, Collection<Position>> positionsMap = Maps.newHashMap();
        positions.forEach(position -> chunks.forEach(chunk -> {
            if (chunk.contains(position)) {
                positionsMap.computeIfAbsent(chunk, k -> Lists.newArrayList()).add(position);
            }
        }));

        positionsMap.forEach((chunk, chunkPositions) -> work.add(new ChunkWork(chunk, chunkPositions)));

        return work;
    }
}
