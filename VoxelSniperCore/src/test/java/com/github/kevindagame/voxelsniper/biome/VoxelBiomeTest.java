package com.github.kevindagame.voxelsniper.biome;

import com.github.kevindagame.voxelsniper.Version;
import junit.framework.TestCase;

public class VoxelBiomeTest extends TestCase {

    public void testTestToString() {
        VoxelBiome namespaced = new VoxelBiome("namespace", "key", Version.V1_16);
        VoxelBiome onlyKey = new VoxelBiome("key");
        assertEquals("namespace:key", namespaced.toString());
        assertEquals("key", onlyKey.toString());
    }
}