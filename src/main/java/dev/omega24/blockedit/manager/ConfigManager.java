package dev.omega24.blockedit.manager;

import dev.omega24.blockedit.config.annotation.Comment;
import dev.omega24.blockedit.config.annotation.Key;
import dev.omega24.blockedit.config.serializer.Serializer;
import dev.omega24.blockedit.config.serializer.Serializers;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

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

            if (field.isAnnotationPresent(Comment.class)) {
                config.setComments(key, Arrays.asList(field.getDeclaredAnnotation(Comment.class).value().split("\n")));
            }


            try {
                Object value = field.get(null);
                Serializer serializer = Serializers.get(value.getClass());
                if (serializer != null) {
                    value = serializer.serialize(value);
                }

                if (config.get(key) == null) {
                    config.set(key, value);
                }

                value = config.get(key);
                if (serializer != null) {
                    value = serializer.deserialize(value);
                }

                field.set(null, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        config.save(path.toFile());
    }
}
