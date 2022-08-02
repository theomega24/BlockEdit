package dev.omega24.blockedit;

import dev.omega24.blockedit.command.manager.CommandManager;
import dev.omega24.blockedit.config.Config;
import dev.omega24.blockedit.config.Lang;
import dev.omega24.blockedit.config.manager.ConfigManager;
import dev.omega24.blockedit.player.PlayerManager;
import dev.omega24.blockedit.wand.manager.WandManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockEdit extends JavaPlugin {
    private PlayerManager playerManager;
    private WandManager wandManager;

    @Override
    public void onEnable() {
        try {
            ConfigManager.load(this.getDataFolder().toPath().resolve("config.yml"), Config.class);
            ConfigManager.load(this.getDataFolder().toPath().resolve("lang").resolve("en-US.yml"), Lang.class);
            ConfigManager.load(this.getDataFolder().toPath().resolve("lang").resolve(Config.LANG_FILE), Lang.class);

            CommandManager.load(this);

            playerManager = new PlayerManager(this);
            wandManager = new WandManager(this);
        } catch (Exception e) {
            getLogger().severe("Failed to load, disabling.");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public WandManager getWandManager() {
        return this.wandManager;
    }
}
