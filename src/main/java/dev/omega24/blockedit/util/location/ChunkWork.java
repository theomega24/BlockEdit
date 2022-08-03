package dev.omega24.blockedit.util.location;

import java.util.Collection;

public record ChunkWork(ChunkPosition chunk, Collection<Position> positions) {
}
