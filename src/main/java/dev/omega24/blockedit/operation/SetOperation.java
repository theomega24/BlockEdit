package dev.omega24.blockedit.operation;

import dev.omega24.blockedit.api.operation.Operation;
import dev.omega24.blockedit.user.Selection;
import dev.omega24.blockedit.user.User;
import dev.omega24.blockedit.util.location.Position;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.Collection;

public class SetOperation extends Operation<SetOperation.Data> {

    public SetOperation(Data data, Selection selection) {
        super(data, selection);
    }

    @Override
    public BlockData change(Position position) {
        return data.material().createBlockData();
    }

    @Override
    public Collection<Position> filterPositions() {
        return this.selection.getAllPositions();
    }

    public record Data(
            Material material
    ) {}
}
