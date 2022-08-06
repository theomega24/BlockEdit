package dev.omega24.blockedit.util.location;

import net.minecraft.core.BlockPos;
import org.bukkit.Location;

import java.util.UUID;

public record Position(int x, int y, int z) {

    public BlockPos toBlockPos() {
        return new BlockPos(x, y, z);
    }

    public static Position from(Location location) {
        return new Position(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}
