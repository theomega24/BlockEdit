package dev.omega24.blockedit.player;

import com.google.common.collect.Maps;
import dev.omega24.blockedit.BlockEdit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class PlayerManager {
    private final BlockEdit plugin;
    private final Map<UUID, BEPlayer> bePlayerMap = Maps.newHashMap();

    public PlayerManager(BlockEdit plugin) {
        this.plugin = plugin;
    }

    public BEPlayer get(Player player) {
        return bePlayerMap.get(player.getUniqueId());
    }

    public void create(Player player) {
        bePlayerMap.put(player.getUniqueId(), new BEPlayer(player.getUniqueId()));
    }

    public BEPlayer getOrCreate(Player player) {
        if (!bePlayerMap.containsKey(player.getUniqueId())) {
            create(player);
        }

        return get(player);
    }
}
