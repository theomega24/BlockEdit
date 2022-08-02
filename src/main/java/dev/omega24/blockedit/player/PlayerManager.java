package dev.omega24.blockedit.player;

import com.google.common.collect.Maps;
import dev.omega24.blockedit.BlockEdit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;

public class PlayerManager implements Listener {
    private final Map<UUID, BEPlayer> bePlayerMap = Maps.newHashMap();

    public PlayerManager(BlockEdit plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public BEPlayer get(Player player) {
        return bePlayerMap.get(player.getUniqueId());
    }

    public void create(Player player) {
        bePlayerMap.put(player.getUniqueId(), new BEPlayer(player.getUniqueId()));
    }

    public void destroy(Player player) {
        bePlayerMap.remove(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.create(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.destroy(event.getPlayer());
    }
}
