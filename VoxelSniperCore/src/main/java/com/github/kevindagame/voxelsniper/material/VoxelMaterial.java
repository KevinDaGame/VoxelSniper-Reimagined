package com.github.kevindagame.voxelsniper.material;

import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.voxelsniper.Version;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;

import java.util.*;

import static com.github.kevindagame.voxelsniper.Version.*;

public class VoxelMaterial implements IMaterial {
    public static final Map<String, VoxelMaterial> MATERIALS = new HashMap<>();

    //these statics are used for the default materials. Please don't add any more statics here.
    public static final VoxelMaterial AIR = register("minecraft", "air", Tags.AIR);
    public static final VoxelMaterial BEDROCK = register("minecraft", "bedrock");
    public static final VoxelMaterial WATER = register("minecraft", "water", Tags.FLUIDS);
    public static final VoxelMaterial LAVA = register("minecraft", "lava", Tags.FLUIDS);
    public static final VoxelMaterial STONE = register("minecraft", "stone");
    public static final VoxelMaterial OAK_WOOD = register("minecraft", "oak_wood");
    public static final VoxelMaterial OAK_LEAVES = register("minecraft", "oak_leaves");
    public static List<VoxelMaterial> OVERRIDABLE_MATERIALS = Arrays.asList(VoxelMaterial.STONE,
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
    private final Version version;
    private final String key;
    private final String namespace;
    private final List<Tags> tags;
    private IMaterial material = null;

    public VoxelMaterial(String namespace, String key, Version version, Tags... tags) {
        this.namespace = namespace;
        this.key = key;
        this.version = version;
        this.tags = Arrays.stream(tags).toList();
    }

    public VoxelMaterial(String namespace, String key) {
        this(namespace, key, V1_16);
    }

    public VoxelMaterial(String key) {
        this("minecraft", key);
    }

    private static VoxelMaterial register(String namespace, String key, Version version, Tags... tags) {

        var material = new VoxelMaterial(namespace, key, version, tags);
        var mapKey = namespace + ":" + key;
        MATERIALS.put(mapKey, material);
        return material;
    }

    private static VoxelMaterial register(String namespace, String key, Tags... tags) {
        return register(namespace, key, V1_16, tags);
    }

    public static VoxelMaterial getMaterial(String key) {
        if (key.contains(":")) {
            String[] components = key.split(":");
            return getMaterial(components[0], components[1]);
        }
        return getMaterial("minecraft", key);
    }

    public static VoxelMaterial getMaterial(String namespace, String key) {
        if (namespace.isEmpty() || key.isEmpty()) return null;
        var block = MATERIALS.get(namespace + ":" + key);
        if (block == null) {
            block = new VoxelMaterial(namespace, key);
            if (block.getIMaterial() != null) {  // block exists
                String mapKey = namespace + ":" + key;
                MATERIALS.put(mapKey, block);
                return block;
            } else {
                return null;
            }
        }
        if (VoxelSniper.voxelsniper.getVersion().isSupported(block.getVersion())) {
            return block;
        }
        return null;
    }

    public static Collection<VoxelMaterial> getMaterials() {
        return MATERIALS.values().stream().filter((m) -> VoxelSniper.voxelsniper.getVersion().isSupported(m.getVersion())).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoxelMaterial that = (VoxelMaterial) o;
        return version == that.version && key.equals(that.key) && namespace.equals(that.namespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, key, namespace);
    }

    @Override
    public String toString() {
        return namespace + ":" + key;
    }

    public boolean isBlock() {
        return this.getIMaterial().isBlock();
    }

    @Override
    public boolean hasGravity() {
        return this.getIMaterial().hasGravity();
    }

    @Override
    public IBlockData createBlockData(String s) {
        return this.getIMaterial().createBlockData(s);
    }

    @Override
    public IBlockData createBlockData() {
        return this.getIMaterial().createBlockData();
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public boolean isSolid() {
        return this.getIMaterial().isSolid();
    }

    public boolean fallsOff() {
        return this.tags.contains(Tags.FALLSOFF);
    }

    public boolean isFluid() {
        return this.tags.contains(Tags.FLUIDS);
    }

    public boolean isAir() {
        return this.tags.contains(Tags.AIR);
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(String key) {
        return this.getIMaterial().equals(key);
    }

    @Override
    public String getName() {
        return this.getKey();
    }

    @Override
    public boolean equals(VoxelMaterial material) {
        return this.getIMaterial().equals(material);
    }

    @Override
    public boolean isTransparent() {
        return this.getIMaterial().isTransparent();
    }

    public String getNamespace() {
        return namespace;
    }

    public IMaterial getIMaterial() {
        if (material == null) {
            this.material = VoxelSniper.voxelsniper.getMaterial(this);
        }
        return material;
    }

    private enum Tags {
        FALLSOFF,
        FLUIDS,
        AIR
    }
}
