package dev.omega24.blockedit;

import dev.omega24.blockedit.config.Config;
import dev.omega24.blockedit.config.Lang;
import dev.omega24.blockedit.listener.PlayerListeners;
import dev.omega24.blockedit.listener.TickListeners;
import dev.omega24.blockedit.manager.CommandManager;
import dev.omega24.blockedit.manager.ConfigManager;
import dev.omega24.blockedit.manager.OperationManager;
import dev.omega24.blockedit.manager.WandManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockEdit extends JavaPlugin {
    private OperationManager operationManager;

    @Override
    public void onEnable() {
        try {
            ConfigManager.load(this.getDataFolder().toPath().resolve("config.yml"), Config.class);
            ConfigManager.load(this.getDataFolder().toPath().resolve("lang").resolve(Config.LANG_FILE), Lang.class);

            CommandManager.load(this);
            WandManager.load(this);

            getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
            getServer().getPluginManager().registerEvents(new TickListeners(), this);

            this.operationManager = new OperationManager(this);
        } catch (Exception e) {
            getLogger().severe("Failed to load, disabling.");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    public OperationManager getOperationManager() {
        return this.operationManager;
    }
}
