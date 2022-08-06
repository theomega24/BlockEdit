package dev.omega24.blockedit.util.location;

public record ChunkPosition(int x, int z) {

    public boolean contains(Position position) {
        return position.x() >> 4 == x &&
                position.z() >> 4 == z;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof ChunkPosition position)) {
            return false;
        }

        return x == position.x && z == position.z;
    }
}
