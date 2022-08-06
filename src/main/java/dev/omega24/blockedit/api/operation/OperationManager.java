package dev.omega24.blockedit.api.operation;

import org.bukkit.entity.Player;

public interface OperationManager {

    void submit(Player player, Operation<?> operation);
}
