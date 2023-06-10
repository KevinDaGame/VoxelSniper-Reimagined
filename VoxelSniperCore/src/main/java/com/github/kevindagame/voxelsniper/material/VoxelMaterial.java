package com.github.kevindagame.voxelsniper.material;

import com.github.kevindagame.VoxelSniper;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public abstract class VoxelMaterial implements IMaterial {

    public VoxelMaterial(String namespace, String key) {
        this.namespace = namespace;
        this.key = key;
    }

    //these statics are used for the default materials. Please don't add any more statics here.
    public static VoxelMaterial AIR() {
        return getMaterial("minecraft", "air");
    }

    public static VoxelMaterial BEDROCK() {
        return getMaterial("minecraft", "bedrock");
    }

    public static VoxelMaterial WATER() {
        return getMaterial("minecraft", "water");
    }

    public static VoxelMaterial LAVA() {
        return getMaterial("minecraft", "lava");
    }

    public static VoxelMaterial STONE() {
        return getMaterial("minecraft", "stone");
    }

    public static VoxelMaterial OAK_WOOD() {
        return getMaterial("minecraft", "oak_wood");
    }

    public static VoxelMaterial OAK_LEAVES() {
        return getMaterial("minecraft", "oak_leaves");
    }

    public static List<VoxelMaterial> getOverridableMaterials() {

        return Arrays.asList(
                VoxelMaterial.STONE(),
                getMaterial("andesite"),
                getMaterial("diorite"),
                getMaterial("granite"),
                getMaterial("grass_block"),
                getMaterial("dirt"),
                getMaterial("coarse_dirt"),
                getMaterial("podzol"),
                getMaterial("sand"),
                getMaterial("red_sand"),
                getMaterial("gravel"),
                getMaterial("sandstone"),
                getMaterial("mossy_cobblestone"),
                getMaterial("clay"),
                getMaterial("snow"),
                getMaterial("obsidian")
        );
    }

    public static VoxelMaterial getMaterial(String key) {
        if (key.contains(":")) {
            String[] components = key.split(":");
            return getMaterial(components[0], components[1]);
        }
        return getMaterial("minecraft", key);
    }

    public static VoxelMaterial getMaterial(String namespace, String key) {
        return VoxelSniper.voxelsniper.getMaterial(namespace, key);
    }

    private final String key;
    private final String namespace;


    public VoxelMaterial(String key) {
        this("minecraft", key);
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
        if (!(o instanceof VoxelMaterial that)) return false;

        if (!key.equals(that.key)) return false;
        return namespace.equals(that.namespace);
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + namespace.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return namespace + ":" + key;
    }
}
