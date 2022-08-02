package dev.omega24.blockedit.config;

import dev.omega24.blockedit.config.manager.Key;
import org.bukkit.Material;

public class Config {

    @Key("settings.lang-file")
    public static String LANG_FILE = "en-US.yml";

    @Key("commands.prefix")
    public static String COMMAND_PREFIX = "#";

    @Key("wands.selection.material")
    public static Material SELECTION_WAND_MATERIAL = Material.STONE_AXE;
    @Key("wands.selection.custom-model-data")
    public static int SELECTION_WAND_CMD = 0;
}
