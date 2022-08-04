package dev.omega24.blockedit.listener;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import dev.omega24.blockedit.util.TickUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TickListeners implements Listener {

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        TickUtil.MSPT_5S.add(event.getTickDuration());
    }
}
