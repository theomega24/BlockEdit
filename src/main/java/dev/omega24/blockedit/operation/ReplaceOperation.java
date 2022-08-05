package dev.omega24.blockedit.operation;

import dev.omega24.blockedit.user.User;
import dev.omega24.blockedit.util.location.Position;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Collection;

// todo: this doesnt seem to work
public class ReplaceOperation extends Operation<ReplaceOperation.Data> {

    public ReplaceOperation(User user, Data data) {
        super(user, data);
    }

    @Override
    public void change(Block block) {
        if (block.getBlockData().getMaterial().equals(data().from())) {
            block.setBlockData(data().to().createBlockData());
        }
    }

    @Override
    protected Collection<Position> filterPositions() {
        return this.user.getSelection().getAllPositions();
    }

    public record Data(
            Material from,
            Material to
    ) {}
}
