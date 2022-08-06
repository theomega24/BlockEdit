package dev.omega24.blockedit.api;

import dev.omega24.blockedit.api.operation.OperationManager;
import dev.omega24.blockedit.api.wand.WandManager;

public interface BlockEditAPI {

    WandManager getWandManager();

    OperationManager getOperationManager();
}
