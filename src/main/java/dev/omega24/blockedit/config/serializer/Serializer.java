package dev.omega24.blockedit.config.serializer;

public interface Serializer {

    Object serialize(Object type);

    Object deserialize(Object value);

    Class<?> getSerializerClass();
}
