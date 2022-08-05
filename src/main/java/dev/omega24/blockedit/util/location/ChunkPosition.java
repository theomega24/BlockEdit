package dev.omega24.blockedit.util.location;

import java.util.UUID;

public record ChunkPosition(int x, int y, int z, UUID worldUUID) {

    public boolean contains(Position position) {
        return position.x() >> 4 == x &&
                position.y() >> 4 == y &&
                position.z() >> 4 == z &&
                position.worldUUID().equals(worldUUID);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof ChunkPosition position)) {
            return false;
        }

        return x == position.x && y == position.y && z == position.z;
    }
}
