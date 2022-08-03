package dev.omega24.blockedit.wand.manager;

import com.google.common.collect.ImmutableList;
import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.wand.SelectionWand;
import org.jetbrains.annotations.Nullable;

public class Wands {
    private final ImmutableList<Wand> wands;

    public Wands(BlockEdit plugin) {
        wands = ImmutableList.of(
                new SelectionWand(plugin)
        );
    }

    public ImmutableList<Wand> getAll() {
        return this.wands;
    }

    @Nullable
    public Wand getById(String id) {
        for (Wand wand : wands) {
            if (wand.getKey().getKey().equals(id)) {
                return wand;
            }
        }

        return null;
    }
}
