package dev.omega24.blockedit.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.api.wand.WandManager;
import dev.omega24.blockedit.api.wand.Wand;
import dev.omega24.blockedit.wand.SelectionWand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.Collection;

public class WandManagerImpl implements WandManager {
    private final Collection<Wand> wands = Lists.newArrayList();

    public WandManagerImpl(BlockEdit plugin) {
        ImmutableList.of(
                new SelectionWand(plugin)
        ).forEach(this::register);
    }

    @Override
    public void register(Wand wand) {
        wands.add(wand);
    }

    @Override
    public void unregister(Wand wand) {
        wands.remove(wand);
    }

    @Override
    public Collection<Wand> getAll() {
        return wands;
    }

    @Override
    public boolean give(Player player, Wand wand) {
        PlayerInventory inventory = player.getInventory();
        for (int slot = 0; slot <= 8; slot++) {
            if (inventory.getStorageContents()[slot] == null) {
                inventory.addItem(wand.getData().item().clone());
                return true;
            }
        }

        return false;
    }
}
