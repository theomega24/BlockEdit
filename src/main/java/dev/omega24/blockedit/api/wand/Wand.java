package dev.omega24.blockedit.api.wand;

import org.bukkit.NamespacedKey;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public abstract class Wand {
    private final NamespacedKey key;
    private final Data data;

    public Wand(NamespacedKey key, Data data) {
        this.key = key;
        this.data = data;

        ItemMeta meta = data.item.getItemMeta();
        meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
        data.item.setItemMeta(meta);
    }

    public abstract void use(PlayerInteractEvent event);

    public NamespacedKey getKey() {
        return this.key;
    }

    public Data getData() {
        return this.data;
    }

    public record Data(
            String id,
            ItemStack item,
            List<Action> actions
    ) {}
}
