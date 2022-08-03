package dev.omega24.blockedit.util.location;

import com.google.common.collect.Lists;
import org.bukkit.util.BoundingBox;

import java.util.Collection;

public class ChunkLocation {
    private final int x;
    private final int z;

    public ChunkLocation(int x, int z) {
        this.x = x >> 4;
        this.z = z >> 4;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof ChunkLocation chunk)) {
            return false;
        }

        return x == chunk.x && z == chunk.z;
    }

    public static Collection<ChunkLocation> getChunksFromBB(BoundingBox box) {
        Collection<ChunkLocation> chunks = Lists.newArrayList();
        for (double x = box.getMinX(); x <= box.getMaxX(); x++) {
            for (double z = box.getMinZ(); z <= box.getMaxZ(); z++) {
                ChunkLocation chunk = new ChunkLocation((int) x, (int) z);
                if (!chunks.contains(chunk)) {
                    chunks.add(chunk);
                }
            }
        }
        return chunks;
    }
}
