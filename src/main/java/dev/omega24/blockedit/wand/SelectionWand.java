package dev.omega24.blockedit.wand;

import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.config.Lang;
import dev.omega24.blockedit.player.BEPlayer;
import dev.omega24.blockedit.util.location.Position;
import dev.omega24.blockedit.wand.manager.Wand;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

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
        return new WandData(
                "selection",
                Lang.SELECTION_WAND_NAME,
                Material.STONE_AXE,
                List.of(Action.LEFT_CLICK_BLOCK, Action.RIGHT_CLICK_BLOCK)
        );
    }
}
