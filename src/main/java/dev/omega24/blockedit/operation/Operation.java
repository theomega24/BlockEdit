package dev.omega24.blockedit.operation;

import com.google.common.collect.Lists;
import dev.omega24.blockedit.user.Selection;
import dev.omega24.blockedit.user.User;
import dev.omega24.blockedit.util.location.ChunkPosition;
import dev.omega24.blockedit.util.operation.ChunkWork;
import dev.omega24.blockedit.util.LocationUtil;
import dev.omega24.blockedit.util.location.Position;
import org.bukkit.block.Block;

import java.util.Collection;

public abstract class Operation<D> {
    protected final Selection selection;
    private final D data;

    public Operation(User user, D data) {
        this.selection = user.getSelection().clone();
        this.data = data;
    }

    public abstract void change(Block block);

    protected abstract Collection<Position> filterPositions();

    protected D data() {
        return this.data;
    }

    public Collection<ChunkWork> splitChunkWork() {
        Collection<Position> positions = this.filterPositions();
        Collection<ChunkPosition> chunks = LocationUtil.getChunksFromBB(this.selection.createBoundingBox());
        Collection<ChunkWork> work = Lists.newArrayList();

        chunks.forEach(chunk -> {
            Collection<Position> chunkPositions = Lists.newArrayList();
            positions.forEach(position -> {
                if (chunk.contains(position)) {
                    chunkPositions.add(position);
                    positions.remove(position);
                }
            });

            work.add(new ChunkWork(chunk, chunkPositions));
        });

        return work;
    }
}
