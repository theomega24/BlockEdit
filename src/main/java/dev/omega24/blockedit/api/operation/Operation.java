package dev.omega24.blockedit.api.operation;

import dev.omega24.blockedit.user.Selection;
import dev.omega24.blockedit.util.location.Position;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;

import java.util.Collection;

public abstract class Operation<D> {
    protected final D data;
    protected final Selection selection;
    private final OperationStats stats = new OperationStats();

    public Operation(D data, Selection selection) {
        this.data = data;
        this.selection = selection;
    }

    public D getData() {
        return data;
    }

    public Selection getSelection() {
        return selection;
    }

    public OperationStats getStats() {
        return stats;
    }

    public abstract BlockData change(Position position);

    public abstract Collection<Position> filterPositions();
}
