package dev.omega24.blockedit.util.location;

import org.bukkit.Location;

public record Position(int x, int y, int z) {

    public static Position from(Location location) {
        return new Position(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}
