package dev.omega24.blockedit.config;

import dev.omega24.blockedit.config.manager.Key;

public class Config {

    @Key("settings.lang-file")
    public static String LANG_FILE = "en-US.yml";

    @Key("commands.prefix")
    public static String COMMAND_PREFIX = "#";
}
