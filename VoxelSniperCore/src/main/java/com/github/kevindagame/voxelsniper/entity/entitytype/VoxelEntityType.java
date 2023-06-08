package com.github.kevindagame.voxelsniper.entity.entitytype;

import com.github.kevindagame.IKeyed;
import com.github.kevindagame.VoxelSniper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public record VoxelEntityType(String namespace, String key) implements IKeyed {

    public static VoxelEntityType FIREBALL() {
        return getEntityType("minecraft", "fireball");
    }

    public static VoxelEntityType SMALL_FIREBALL() {
        return getEntityType("minecraft", "small_fireball");
    }

    public static VoxelEntityType ZOMBIE() {
        return getEntityType("minecraft", "zombie");
    }

    public static VoxelEntityType getEntityType(String key) {
        return getEntityType("minecraft", key.toLowerCase(Locale.ROOT));
    }

    public static VoxelEntityType getEntityType(String namespace, String key) {
        return VoxelSniper.voxelsniper.getEntityType(namespace, key);

    }

    public static List<VoxelEntityType> getEntityTypes() {
        return VoxelSniper.voxelsniper.getEntityTypes();
    }

    @NotNull
    @Override
    public String getNameSpace() {
        return namespace;
    }

    @NotNull
    @Override
    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoxelEntityType that)) return false;

        if (!namespace.equals(that.namespace)) return false;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        int result = namespace.hashCode();
        result = 31 * result + key.hashCode();
        return result;
    }
}
