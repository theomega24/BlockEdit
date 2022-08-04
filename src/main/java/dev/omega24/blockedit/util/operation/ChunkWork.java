package dev.omega24.blockedit.util.operation;

import dev.omega24.blockedit.util.location.ChunkPosition;
import dev.omega24.blockedit.util.location.Position;

import java.util.Collection;

public record ChunkWork(ChunkPosition chunk, Collection<Position> positions) {
}
