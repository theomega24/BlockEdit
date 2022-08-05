package dev.omega24.blockedit.config;

import dev.omega24.blockedit.config.annotation.Comment;
import dev.omega24.blockedit.config.annotation.Key;
import org.bukkit.Material;

public class Config {

    @Key("settings.lang-file")
    @Comment("The language file to from the 'lang' folder.")
    public static String LANG_FILE = "en-US.yml";
    @Key("settings.max-mspt")
    @Comment("""
            The max MSPT that the executor will continue to run operations at.
            If this is set to '50', the executor won't allow any operations to cause the server to go over 50 mspt.
            If the server is already over 50 mspt, then the server will run one chunk per tick.
            """)
    public static double MAX_MSPT = 50D;

    @Key("commands.prefix")
    @Comment("""
            The prefix before all commands.
            Ex: If this is set to '#' then to get a wand you would run '/#wand'
            """)
    public static String COMMAND_PREFIX = "#";

    @Key("wands.selection.material")
    public static Material SELECTION_WAND_MATERIAL = Material.STONE_AXE;
    @Key("wands.selection.custom-model-data")
    @Comment("""
            The Custom Model Data NBT value to set for this wand.
            Setting this to zero disables this feature.
            """)
    public static int SELECTION_WAND_CMD = 0;
}
