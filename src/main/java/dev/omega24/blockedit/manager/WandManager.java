package dev.omega24.blockedit.manager;

import com.google.common.collect.ImmutableList;
import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.wand.SelectionWand;
import dev.omega24.blockedit.wand.AbstractWand;
import org.jetbrains.annotations.Nullable;

public class WandManager {
    private final ImmutableList<AbstractWand> wands;

    public WandManager(BlockEdit plugin) {
        wands = ImmutableList.of(
                new SelectionWand(plugin)
        );
    }

    public ImmutableList<AbstractWand> getAll() {
        return this.wands;
    }

    @Nullable
    public AbstractWand getById(String id) {
        for (AbstractWand wand : wands) {
            if (wand.getKey().getKey().equals(id)) {
                return wand;
            }
        }

        return null;
    }
}
