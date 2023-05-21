package com.github.kevindagame.voxelsniper.material;

import com.github.kevindagame.VoxelSniper;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public static List<VoxelMaterial> getOVERRIDABLE_MATERIALS() {

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoxelMaterial that = (VoxelMaterial) o;
        return key.equals(that.key) && namespace.equals(that.namespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, namespace);
    }

    @Override
    public String toString() {
        return namespace + ":" + key;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override

    public String getNamespace() {
        return namespace;
    }

    @Override
    public boolean equals(String namespace, String key) {
        return this.namespace.equals(namespace) && this.key.equals(key);
    }

    @Override
    public boolean equals(VoxelMaterial material) {
        return this.namespace.equals(material.namespace) && this.key.equals(material.key);
    }
}
