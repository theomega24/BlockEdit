package dev.omega24.blockedit.util;

import com.google.common.collect.Lists;
import dev.omega24.blockedit.util.location.ChunkPosition;
import org.bukkit.util.BoundingBox;

import java.util.Collection;
import java.util.UUID;

public class LocationUtil {

    public static Collection<ChunkPosition> getChunksFromBB(BoundingBox box, UUID worldUUID) {
        Collection<ChunkPosition> positions = Lists.newArrayList();

        // iterate over all blocks in the bounding box x y z
        for (double x = box.getMinX(); x <= box.getMaxX(); x++) {
            for (double y = box.getMinY(); y <= box.getMaxY(); y++) {
                for (double z = box.getMinZ(); z <= box.getMaxZ(); z++) {
                    ChunkPosition position = new ChunkPosition((int) x >> 4, (int) y >> 4, (int) z >> 4, worldUUID);
                    if (!positions.contains(position)) {
                        positions.add(position);
                    }
                }
            }
        }

        return positions;
    }
}
