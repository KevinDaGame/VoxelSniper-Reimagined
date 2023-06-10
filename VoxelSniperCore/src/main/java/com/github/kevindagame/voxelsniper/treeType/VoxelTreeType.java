package com.github.kevindagame.voxelsniper.treeType;

import com.github.kevindagame.IKeyed;
import com.github.kevindagame.VoxelSniper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record VoxelTreeType(String namespace, String key) implements IKeyed {

    @Nullable
    public static VoxelTreeType getTreeType(String key) {
        return getTreeType("minecraft", key);
    }

    @Nullable
    public static VoxelTreeType getTreeType(String namespace, String key) {
        return VoxelSniper.voxelsniper.getTreeType(namespace, key);
    }

    public static List<VoxelTreeType> getTreeTypes() {
        return VoxelSniper.voxelsniper.getTreeTypes();
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
}
