package com.github.kevindagame.voxelsniper.biome;

import com.github.kevindagame.IKeyed;
import com.github.kevindagame.VoxelSniper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record VoxelBiome(String namespace, String key) implements IKeyed {

    public static VoxelBiome PLAINS() {
        return getBiome("minecraft", "plains");
    }

    public static VoxelBiome getBiome(String key) {
        if(key.contains(":")) {
            String[] split = key.split(":");
            return getBiome(split[0], split[1]);
        }
        return getBiome("minecraft", key);
    }

    public static VoxelBiome getBiome(String namespace, String key) {
        return VoxelSniper.voxelsniper.getBiome(namespace, key);
    }

    public static List<VoxelBiome> getBiomes() {
        return VoxelSniper.voxelsniper.getBiomes();
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
        if (o == null || getClass() != o.getClass()) return false;

        VoxelBiome that = (VoxelBiome) o;

        if (!namespace.equals(that.namespace)) return false;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        int result = namespace.hashCode();
        result = 31 * result + key.hashCode();
        return result;
    }

    String getName() {
        return namespace + ":" + key;
    }

    @Override
    public String toString() {
        return namespace + ":" + key;
    }
}
