package dev.omega24.blockedit.util;

import com.google.common.collect.Lists;
import dev.omega24.blockedit.util.location.ChunkPosition;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class LocationUtil {

    public static Collection<ChunkPosition> getChunksFromBB(BoundingBox box) {
        Collection<ChunkPosition> positions = Lists.newArrayList();

        for (double x = box.getMinX(); x <= box.getMaxX(); x++) {
            for (double z = box.getMinZ(); z <= box.getMaxZ(); z++) {
                ChunkPosition position = new ChunkPosition((int) x >> 4,  (int) z >> 4);
                if (!positions.contains(position)) {
                    positions.add(position);
                }
            }
        }

        return positions;
    }

    public static CompletableFuture<@Nullable LevelChunk> getChunkAsync(ServerLevel level, int x, int z) {
        ServerChunkCache cache = level.getChunkSource();
        LevelChunk ifLoaded = cache.getChunkAtIfLoadedImmediately(x, z);
        if (ifLoaded != null) {
            return CompletableFuture.completedFuture(ifLoaded);
        }

        return cache.getChunkAtAsynchronously(x, z, true, true).thenApplyAsync((chunk) -> (LevelChunk) chunk.left().orElse(null));
    }
}
