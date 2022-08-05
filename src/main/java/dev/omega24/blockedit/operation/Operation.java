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
import java.util.Iterator;

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

        System.out.println("Chunks: " + chunks.size());
        System.out.println("Positions: " + positions.size());

        for (ChunkPosition chunk : chunks) {
            Collection<Position> chunkPositions = Lists.newArrayList();
            for (Position position : positions) {
                if (chunk.contains(position)) {
                    chunkPositions.add(position);
                    positions.remove(position);
                }

            }

            work.add(new ChunkWork(chunk, chunkPositions));
        }

        return work;
    }
}
