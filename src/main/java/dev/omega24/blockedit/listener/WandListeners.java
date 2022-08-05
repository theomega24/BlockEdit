package dev.omega24.blockedit.listener;

import dev.omega24.blockedit.BlockEdit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public record WandListeners(BlockEdit plugin) implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null) {
            return;
        }

        plugin.getWandManager().getAll().forEach(wand -> {
            if (item.getItemMeta().getPersistentDataContainer().getOrDefault(wand.getKey(), PersistentDataType.BYTE, (byte) 0) != 1) {
                return;
            }

            if (wand.getData().actions().contains(event.getAction())) {
                event.setCancelled(true);
                wand.use(event);
            }
        });
    }
}
