package dev.omega24.blockedit.api.wand;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Collection;

public interface WandManager {

    void register(Wand wand);

    void unregister(Wand wand);

    Collection<Wand> getAll();

    @Nullable
    default Wand getByKey(NamespacedKey key) {
        for (Wand wand : getAll()) {
            if (wand.getKey().equals(key)) {
                return wand;
            }
        }

        return null;
    }

    boolean give(Player player, Wand wand);
}
