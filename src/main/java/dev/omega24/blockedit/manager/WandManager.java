package dev.omega24.blockedit.manager;

import com.google.common.collect.Lists;
import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.wand.SelectionWand;
import dev.omega24.blockedit.wand.Wand;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class WandManager {
    private static final Collection<Wand> WANDS = Lists.newArrayList();

    public static void load(BlockEdit plugin) {
        WANDS.addAll(List.of(
                new SelectionWand(plugin)
        ));
    }

    public static Collection<Wand> getAll() {
        return WANDS;
    }

    @Nullable
    public static Wand getById(String id) {
        for (Wand wand : WANDS) {
            if (wand.getKey().getKey().equals(id)) {
                return wand;
            }
        }

        return null;
    }
}
