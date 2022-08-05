package dev.omega24.blockedit.operation;

import dev.omega24.blockedit.user.User;
import dev.omega24.blockedit.util.location.Position;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Collection;

public class SetOperation extends Operation<SetOperation.Data> {

    public SetOperation(User user, Data data) {
        super(user, data);
    }

    @Override
    public void change(Block block) {
        System.out.println("Changing block " + block.getLocation());
        block.setBlockData(data().material().createBlockData());
    }

    @Override
    protected Collection<Position> filterPositions() {
        return this.selection.getAllPositions();
    }

    public record Data(
            Material material
    ) {}
}
