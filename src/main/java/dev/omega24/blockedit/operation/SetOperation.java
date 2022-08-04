package dev.omega24.blockedit.operation;

import dev.omega24.blockedit.user.User;
import dev.omega24.blockedit.util.location.Position;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.Collection;

public class SetOperation extends Operation {
    private final BlockData data;

    public SetOperation(User user, BlockData data) {
        super(user);
        this.data = data;
    }

    @Override
    public void change(Block block) {
        block.setBlockData(this.data);
    }

    @Override
    protected Collection<Position> filterPositions() {
        return this.selection.getAllPositions();
    }
}
