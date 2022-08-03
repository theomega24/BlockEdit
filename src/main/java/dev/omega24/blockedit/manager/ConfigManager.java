package dev.omega24.blockedit.manager;

import dev.omega24.blockedit.config.annotation.Key;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {

    public static void load(Path path, Class<?> clazz) throws IOException {
        YamlConfiguration config = new YamlConfiguration();

        config.options().copyDefaults(true);
        config.options().parseComments(true);

        if (Files.notExists(path)) {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        }

        try {
            config.load(path.toFile());
        } catch (InvalidConfigurationException e) {
            throw new IOException("Failed to load " + path.getFileName() + ", please correct your syntax errors", e);
        }

        for (Field field : clazz.getDeclaredFields()) {
            Key keyAnnotation = field.getDeclaredAnnotation(Key.class);
            if (keyAnnotation == null) {
                return;
            }
            String key = keyAnnotation.value();

            try {
                Object value = field.get(null);

                boolean isMaterial = value.getClass().equals(Material.class);
                if (isMaterial) {
                    value = ((Material) value).getKey().asString();
                }

                if (config.get(key) == null) {
                    config.set(key, value);
                }

                value = config.get(key);
                if (isMaterial) {
                    value = Material.matchMaterial((String) value);
                }
                field.set(null, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        config.save(path.toFile());
    }
}
