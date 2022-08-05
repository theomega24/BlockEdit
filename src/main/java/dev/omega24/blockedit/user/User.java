package dev.omega24.blockedit.user;

import com.google.common.collect.Maps;
import dev.omega24.blockedit.config.Lang;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class User {
    private static final Map<UUID, User> users = Maps.newHashMap();

    public static User get(Player player) {
        return users.get(player.getUniqueId());
    }

    public static void create(Player player) {
        users.put(player.getUniqueId(), new User(player.getUniqueId()));
    }

    public static void destroy(Player player) {
        users.remove(player.getUniqueId());
    }

    private final UUID uuid;
    private final Selection selection = new Selection();

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public Selection getSelection() {
        return this.selection;
    }

    public void send(String message, TagResolver.Single... placeholders) {
        Lang.send(getPlayer(), message, placeholders);
    }
}
