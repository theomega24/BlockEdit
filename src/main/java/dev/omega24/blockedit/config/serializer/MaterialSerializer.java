package dev.omega24.blockedit.config.serializer;

import org.bukkit.Material;

public class MaterialSerializer implements Serializer {

    @Override
    public Object serialize(Object type) {
        if (!(type instanceof Material material)) {
            throw new IllegalStateException();
        }

        return material.getKey().asString();
    }

    @Override
    public Object deserialize(Object value) {
        return Material.matchMaterial(value.toString());
    }

    @Override
    public Class<?> getSerializerClass() {
        return Material.class;
    }
}
