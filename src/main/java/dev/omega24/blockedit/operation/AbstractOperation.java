package dev.omega24.blockedit.operation;

import com.google.common.collect.Lists;
import dev.omega24.blockedit.user.Selection;
import dev.omega24.blockedit.user.User;
import dev.omega24.blockedit.util.location.ChunkPosition;
import dev.omega24.blockedit.util.location.ChunkWork;
import dev.omega24.blockedit.util.location.LocationUtil;
import dev.omega24.blockedit.util.location.Position;

import java.util.Collection;

public abstract class AbstractOperation {
    protected final Selection selection;

    public AbstractOperation(User user) {
        this.selection = user.getSelection().clone();
    }

    protected abstract Collection<Position> filterPositions();

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
