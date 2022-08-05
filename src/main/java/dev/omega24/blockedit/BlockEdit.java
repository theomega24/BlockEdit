package dev.omega24.blockedit;

import dev.omega24.blockedit.api.BlockEditAPI;
import dev.omega24.blockedit.api.wand.WandManager;
import dev.omega24.blockedit.config.Config;
import dev.omega24.blockedit.config.Lang;
import dev.omega24.blockedit.listener.PlayerListeners;
import dev.omega24.blockedit.listener.TickListeners;
import dev.omega24.blockedit.listener.WandListeners;
import dev.omega24.blockedit.util.manager.CommandManager;
import dev.omega24.blockedit.util.manager.ConfigManager;
import dev.omega24.blockedit.impl.OperationManager;
import dev.omega24.blockedit.impl.WandManagerImpl;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockEdit extends JavaPlugin implements BlockEditAPI {
    private WandManager wandManager;
    private OperationManager operationManager;

    @Override
    public void onEnable() {
        try {
            ConfigManager.load(this.getDataFolder().toPath().resolve("config.yml"), Config.class);
            ConfigManager.load(this.getDataFolder().toPath().resolve("lang").resolve(Config.LANG_FILE), Lang.class);
            CommandManager.load(this);
        } catch (Exception e) {
            getLogger().severe("Failed to load, disabling.");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }

        getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
        getServer().getPluginManager().registerEvents(new TickListeners(), this);
        getServer().getPluginManager().registerEvents(new WandListeners(this), this);

        this.wandManager = new WandManagerImpl(this);
        this.operationManager = new OperationManager(this);
    }

    public OperationManager getOperationManager() {
        return this.operationManager;
    }

    @Override
    public dev.omega24.blockedit.api.wand.WandManager getWandManager() {
        return this.wandManager;
    }
}
