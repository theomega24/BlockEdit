package dev.omega24.blockedit.util;

import java.util.concurrent.TimeUnit;

public class TickUtil {
    public static final RollingAverage MSPT_5S = new RollingAverage(TimeUnit.SECONDS, 5);
}
