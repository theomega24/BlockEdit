package dev.omega24.blockedit.util;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// todo: verify this works
public class RollingAverage {
    private final Map<Long, Double> values = Maps.newHashMap();
    private final long olderThan;

    public RollingAverage(TimeUnit unit, long duration) {
        this.olderThan = unit.toMillis(duration);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this::prune, duration, duration, unit);
    }

    public void add(double value) {
        values.put(System.currentTimeMillis(), value);
    }

    public double average() {
        int amount = 0;
        double total = 0;

        for (double value : values.values()) {
            total += value;
            amount++;
        }

        return total / amount;
    }

    private void prune() {
        long current = System.currentTimeMillis();
        values.forEach((time, value) -> {
            if (current - time > olderThan) {
                values.remove(time, value);
            }
        });
    }
}
