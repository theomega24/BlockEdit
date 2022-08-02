package dev.omega24.blockedit.player;

import dev.omega24.blockedit.config.Lang;
import dev.omega24.blockedit.util.location.Selection;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BEPlayer {
    private final UUID uuid;
    private Selection selection;

    public BEPlayer(UUID uuid) {
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
