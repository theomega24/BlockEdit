package dev.omega24.blockedit.util;

import com.google.common.collect.Lists;
import dev.omega24.blockedit.util.location.ChunkPosition;
import org.bukkit.util.BoundingBox;

import java.util.Collection;

public class LocationUtil {

    public static Collection<ChunkPosition> getChunksFromBB(BoundingBox box) {
        Collection<ChunkPosition> positions = Lists.newArrayList();
        for (double x = box.getMinX(); x <= box.getMaxX(); x++) {
            for (double z = box.getMinZ(); z <= box.getMaxZ(); z++) {
                ChunkPosition position = new ChunkPosition((int) x >> 4, (int) z >> 4);
                if (!positions.contains(position)) {
                    positions.add(position);
                }
            }
        }
        return positions;
    }
}