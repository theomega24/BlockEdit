package dev.omega24.blockedit.user;

import com.google.common.collect.Lists;
import dev.omega24.blockedit.util.location.Position;
import org.bukkit.Location;
import org.bukkit.util.BoundingBox;

import java.util.Collection;
import java.util.UUID;

public class Selection {
    private Location pos1;
    private Location pos2;
    private UUID worldUUID;

    public BoundingBox createBoundingBox() {
        return BoundingBox.of(pos1, pos2).expand(0.5, 0.5, 0.5).shift(0.5, 0.5, 0.5);
    }

    public Collection<Position> getAllPositions() {
        Collection<Position> positions = Lists.newArrayList();
        BoundingBox box = createBoundingBox();

        for (double x = box.getMinX(); x <= box.getMaxX(); x++) {
            for (double y = box.getMinY(); y <= box.getMaxY(); y++) {
                for (double z = box.getMinZ(); z <= box.getMaxZ(); z++) {
                    positions.add(new Position((int) x, (int) y, (int) z, worldUUID));
                }
            }
        }

        return positions;
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

    @Override
    public Selection clone() {
        Selection selection = new Selection();
        selection.setPos1(this.pos1.clone());
        selection.setPos2(this.pos2.clone());
        selection.setWorldUUID(this.worldUUID);
        return selection;
    }
}
