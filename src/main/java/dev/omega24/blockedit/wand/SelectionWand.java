package dev.omega24.blockedit.wand;

import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.config.Config;
import dev.omega24.blockedit.config.Lang;
import dev.omega24.blockedit.player.BEPlayer;
import dev.omega24.blockedit.util.location.Position;
import dev.omega24.blockedit.wand.manager.Wand;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class SelectionWand extends Wand {

    public SelectionWand(BlockEdit plugin) {
        super(plugin);
    }

    @Override
    protected void use(BEPlayer player, PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }

        Position position = Position.from(block.getLocation());
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            player.getSelection().pos1(position);
        } else {
            player.getSelection().pos2(position);
        }

        player.send(
                Lang.SELECTION_WAND_SET_POSITION,
                Placeholder.unparsed("pos", event.getAction() == Action.LEFT_CLICK_BLOCK ? Lang.NUMBER_ONE : Lang.NUMBER_TWO),
                Placeholder.unparsed("x", String.valueOf(position.x())),
                Placeholder.unparsed("y", String.valueOf(position.y())),
                Placeholder.unparsed("z", String.valueOf(position.z()))
        );
    }

    @Override
    protected WandData data() {
        ItemStack item = new ItemStack(Config.SELECTION_WAND_MATERIAL);

        ItemMeta meta = item.getItemMeta();
        meta.displayName(MiniMessage.miniMessage().deserialize(Lang.SELECTION_WAND_NAME));
        meta.lore(Lang.SELECTION_WAND_LORE.stream().map(MiniMessage.miniMessage()::deserialize).collect(Collectors.toList()));
        meta.setCustomModelData(Config.SELECTION_WAND_CMD == 0 ? null : Config.SELECTION_WAND_CMD);
        item.setItemMeta(meta);

        return new WandData(
                "selection",
                item,
                List.of(Action.LEFT_CLICK_BLOCK, Action.RIGHT_CLICK_BLOCK)
        );
    }
}
