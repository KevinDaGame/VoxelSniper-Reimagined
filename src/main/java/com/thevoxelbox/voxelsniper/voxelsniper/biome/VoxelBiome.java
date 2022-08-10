package com.thevoxelbox.voxelsniper.voxelsniper.biome;

import com.thevoxelbox.voxelsniper.VoxelSniper;
import com.thevoxelbox.voxelsniper.voxelsniper.IVoxelsniper;
import com.thevoxelbox.voxelsniper.voxelsniper.Version;

import java.util.HashMap;
import java.util.Map;
//todo: backwards compatibility
@SuppressWarnings("unused")
public record VoxelBiome(String namespace, String key, Version version) {
    public static final String DEFAULT_NAMESPACE = "minecraft";
    public static Map<String, VoxelBiome> BIOMES;
    public static VoxelBiome OCEAN = register("ocean");
    public static VoxelBiome PLAINS = register("plains");
    public static VoxelBiome DESERT = register("desert");
    public static VoxelBiome WINDSWEPT_HILLS = register("windswept_hills");
    public static VoxelBiome FOREST = register("forest");
    public static VoxelBiome TAIGA = register("taiga");
    public static VoxelBiome SWAMP = register("swamp");
    public static VoxelBiome MANGROVE_SWAMP = register("mangrove_swamp", Version.V1_19);
    public static VoxelBiome RIVER = register("river");
    public static VoxelBiome NETHER_WASTES = register("nether_wastes");
    public static VoxelBiome THE_END = register("the_end");
    public static VoxelBiome FROZEN_OCEAN = register("frozen_ocean");
    public static VoxelBiome FROZEN_RIVER = register("frozen_river");
    public static VoxelBiome SNOWY_PLAINS = register("snowy_plains");
    public static VoxelBiome MUSHROOM_FIELDS = register("mushroom_fields");
    public static VoxelBiome BEACH = register("beach");
    public static VoxelBiome JUNGLE = register("jungle");
    public static VoxelBiome SPARSE_JUNGLE = register("sparse_jungle");
    public static VoxelBiome DEEP_OCEAN = register("deep_ocean");
    public static VoxelBiome STONY_SHORE = register("stony_shore");
    public static VoxelBiome SNOWY_BEACH = register("snowy_beach");
    public static VoxelBiome BIRCH_FOREST = register("birch_forest");
    public static VoxelBiome DARK_FOREST = register("dark_forest");
    public static VoxelBiome SNOWY_TAIGA = register("snowy_taiga");
    public static VoxelBiome OLD_GROWTH_PINE_TAIGA = register("old_growth_pine_taiga");
    public static VoxelBiome WINDSWEPT_FOREST = register("windswept_forest");
    public static VoxelBiome SAVANNA = register("savanna");
    public static VoxelBiome SAVANNA_PLATEAU = register("savanna_plateau");
    public static VoxelBiome BADLANDS = register("badlands");
    public static VoxelBiome WOODED_BADLANDS = register("wooded_badlands");
    public static VoxelBiome SMALL_END_ISLANDS = register("small_end_islands");
    public static VoxelBiome END_MIDLANDS = register("end_midlands");
    public static VoxelBiome END_HIGHLANDS = register("end_highlands");
    public static VoxelBiome END_BARRENS = register("end_barrens");
    public static VoxelBiome WARM_OCEAN = register("warm_ocean");
    public static VoxelBiome LUKEWARM_OCEAN = register("lukewarm_ocean");
    public static VoxelBiome COLD_OCEAN = register("cold_ocean");
    public static VoxelBiome DEEP_LUKEWARM_OCEAN = register("deep_lukewarm_ocean");
    public static VoxelBiome DEEP_COLD_OCEAN = register("deep_cold_ocean");
    public static VoxelBiome DEEP_FROZEN_OCEAN = register("deep_frozen_ocean");
    public static VoxelBiome THE_VOID = register("the_void");
    public static VoxelBiome SUNFLOWER_PLAINS = register("sunflower_plains");
    public static VoxelBiome WINDSWEPT_GRAVELLY_HILLS = register("windswept_gravelly_hills");
    public static VoxelBiome FLOWER_FOREST = register("flower_forest");
    public static VoxelBiome ICE_SPIKES = register("ice_spikes");
    public static VoxelBiome OLD_GROWTH_BIRCH_FOREST = register("old_growth_birch_forest");
    public static VoxelBiome OLD_GROWTH_SPRUCE_TAIGA = register("old_growth_spruce_taiga");
    public static VoxelBiome WINDSWEPT_SAVANNA = register("windswept_savanna");
    public static VoxelBiome ERODED_BADLANDS = register("eroded_badlands");
    public static VoxelBiome BAMBOO_JUNGLE = register("bamboo_jungle");
    public static VoxelBiome SOUL_SAND_VALLEY = register("soul_sand_valley");
    public static VoxelBiome CRIMSON_FOREST = register("crimson_forest");
    public static VoxelBiome WARPED_FOREST = register("warped_forest");
    public static VoxelBiome BASALT_DELTAS = register("basalt_deltas");
    public static VoxelBiome DRIPSTONE_CAVES = register("dripstone_caves", Version.V1_17);
    public static VoxelBiome LUSH_CAVES = register("lush_caves", Version.V1_17);
    public static VoxelBiome DEEP_DARK = register("deep_dark", Version.V1_19);
    public static VoxelBiome MEADOW = register("meadow", Version.V1_18);
    public static VoxelBiome GROVE = register("grove", Version.V1_18);
    public static VoxelBiome SNOWY_SLOPES = register("snowy_slopes", Version.V1_18);
    public static VoxelBiome FROZEN_PEAKS = register("frozen_peaks", Version.V1_18);
    public static VoxelBiome JAGGED_PEAKS = register("jagged_peaks", Version.V1_18);
    public static VoxelBiome STONY_PEAKS = register("stony_peaks", Version.V1_18);

    private static VoxelBiome register(String key) {
        return register(key, Version.V1_16);
    }
    private static VoxelBiome register(String key, Version version) {
        var biome = new VoxelBiome("minecraft", key, version);
        if (BIOMES == null) {
            BIOMES = new HashMap<>();
        }
        BIOMES.put(biome.getName(), biome);
        return biome;
    }

    public VoxelBiome(String key) {
        this(DEFAULT_NAMESPACE, key, Version.V1_16);
    }
    String getNamespace() {
        return namespace;
    }
    String getKey() {
        return key;
    }
    String getName() {
        return namespace + ":" + key;
    }
    public static VoxelBiome getBiome(String key) {
        return getBiome("minecraft", key);
    }
    public static VoxelBiome getBiome(String namespace, String key) {
        var biome = BIOMES.get(namespace + ":" + key);
        if(VoxelSniper.voxelsniper.getVersion().isSupported(biome.getVersion())){
            return biome;
        }
        return null;
    }

    private Version getVersion() {
        return version;
    }
}
