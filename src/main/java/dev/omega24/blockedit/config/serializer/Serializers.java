package dev.omega24.blockedit.config.serializer;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class Serializers {
    private static final Collection<Serializer> SERIALIZERS = ImmutableList.of(
            new MaterialSerializer()
    );

    @Nullable
    public static Serializer get(Class<?> clazz) {
        for (Serializer serializer : SERIALIZERS) {
            if (serializer.getSerializerClass().equals(clazz)) {
                return serializer;
            }
        }

        return null;
    }
}
