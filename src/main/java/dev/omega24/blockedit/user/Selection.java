package dev.omega24.blockedit.user;

import org.bukkit.Location;
import org.bukkit.util.BoundingBox;

import java.util.UUID;

public class Selection {
    private Location pos1;
    private Location pos2;
    private UUID worldUUID;

    public BoundingBox createBoundingBox() {
        return BoundingBox.of(pos1, pos2).expand(0.5, 0.5, 0.5).shift(0.5, 0.5, 0.5);
    }

    public Location getPos1() {
        return pos1;
    }

    public void setPos1(Location pos1) {
        if (!pos1.getWorld().getUID().equals(worldUUID)) {
            this.reset();
        }

        this.worldUUID = pos1.getWorld().getUID();
        this.pos1 = pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos2(Location pos2) {
        if (!pos2.getWorld().getUID().equals(worldUUID)) {
            this.reset();
        }

        this.worldUUID = pos2.getWorld().getUID();
        this.pos2 = pos2;
    }

    public UUID getWorldUUID() {
        return worldUUID;
    }

    public void setWorldUUID(UUID worldUUID) {
        this.worldUUID = worldUUID;
    }

    public void reset() {
        this.pos1 = null;
        this.pos2 = null;
        this.worldUUID = null;
    }

    public Selection clone() {
        try {
            return (Selection) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
