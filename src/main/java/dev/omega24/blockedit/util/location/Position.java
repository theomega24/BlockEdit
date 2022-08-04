package dev.omega24.blockedit.util.location;

import org.bukkit.Location;

import java.util.UUID;

public record Position(int x, int y, int z, UUID worldUUID) {

    public static Position from(Location location) {
        return new Position(location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getWorld().getUID());
    }
}
