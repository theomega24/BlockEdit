package dev.omega24.blockedit.wand;

import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.api.wand.Wand;
import dev.omega24.blockedit.config.Config;
import dev.omega24.blockedit.config.Lang;
import dev.omega24.blockedit.user.User;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class SelectionWand extends Wand {

    public SelectionWand(BlockEdit plugin) {
        super(new NamespacedKey(plugin, data().id()), data());
    }

    @Override
    public void use(PlayerInteractEvent event) {
        User user = User.get(event.getPlayer());
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }

        Location location = block.getLocation();
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            user.getSelection().setPos1(location);
        } else {
            user.getSelection().setPos2(location);
        }

        user.send(
                Lang.SELECTION_WAND_SET_POSITION,
                Placeholder.unparsed("pos", event.getAction() == Action.LEFT_CLICK_BLOCK ? Lang.NUMBER_ONE : Lang.NUMBER_TWO),
                Placeholder.unparsed("x", String.valueOf(location.getBlockX())),
                Placeholder.unparsed("y", String.valueOf(location.getBlockY())),
                Placeholder.unparsed("z", String.valueOf(location.getBlockZ()))
        );
    }

    private static Data data() {
        ItemStack item = new ItemStack(Config.SELECTION_WAND_MATERIAL);

        ItemMeta meta = item.getItemMeta();
        meta.displayName(MiniMessage.miniMessage().deserialize(Lang.SELECTION_WAND_NAME));
        meta.lore(Lang.SELECTION_WAND_LORE.stream().map(MiniMessage.miniMessage()::deserialize).collect(Collectors.toList()));
        meta.setCustomModelData(Config.SELECTION_WAND_CMD == 0 ? null : Config.SELECTION_WAND_CMD);
        item.setItemMeta(meta);

        return new Data(
                "selection",
                item,
                List.of(Action.LEFT_CLICK_BLOCK, Action.RIGHT_CLICK_BLOCK)
        );
    }
}
