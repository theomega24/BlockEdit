package dev.omega24.blockedit.player;

import dev.omega24.blockedit.config.Lang;
import dev.omega24.blockedit.util.location.Position;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BEPlayer {
    private final UUID uuid;
    private Position pos1, pos2;

    public BEPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public void send(String message, TagResolver.Single... placeholders) {
        Lang.send(getPlayer(), message, placeholders);
    }

    public Position getPos1() {
        return pos1;
    }

    public void setPos1(Position pos1) {
        this.pos1 = pos1;
    }

    public Position getPos2() {
        return pos2;
    }

    public void setPos2(Position pos2) {
        this.pos2 = pos2;
    }
}
