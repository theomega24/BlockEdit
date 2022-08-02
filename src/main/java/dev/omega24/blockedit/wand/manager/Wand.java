package dev.omega24.blockedit.wand.manager;

import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.config.Lang;
import dev.omega24.blockedit.player.BEPlayer;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public abstract class Wand implements Listener {
    protected final BlockEdit plugin;
    private final WandData data;
    private final NamespacedKey key;

    public Wand(BlockEdit plugin) {
        this.plugin = plugin;
        this.data = data();
        this.key = new NamespacedKey(plugin, data.id());

        ItemMeta meta = data.item.getItemMeta();
        meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
        data.item.setItemMeta(meta);

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public NamespacedKey getKey() {
        return this.key;
    }

    public void give(BEPlayer player) {
        PlayerInventory inventory = player.getPlayer().getInventory();
        for (int slot = 0; slot <= 8; slot++) {
            if (inventory.getStorageContents()[slot] == null) {
                inventory.addItem(data.item.clone());
                return;
            }
        }

        player.send(Lang.WAND_COMMAND_HOTBAR_FULL);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null || item.getItemMeta().getPersistentDataContainer().getOrDefault(key, PersistentDataType.BYTE, (byte) 0) != 1) {
            return;
        }

        if (data.actions().contains(event.getAction())) {
            this.use(plugin.getPlayerManager().get(event.getPlayer()), event);
        }
    }

    protected abstract void use(BEPlayer player, PlayerInteractEvent event);

    protected abstract WandData data();

    public record WandData(
            String id,
            ItemStack item,
            List<Action> actions
    ) {}
}
