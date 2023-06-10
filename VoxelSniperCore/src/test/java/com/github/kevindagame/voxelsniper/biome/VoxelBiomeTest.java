package com.github.kevindagame.voxelsniper.biome;

import junit.framework.TestCase;

public class VoxelBiomeTest extends TestCase {

    public void testToString() {
        VoxelBiome namespaced = new VoxelBiome("namespace", "key");
        assertEquals("namespace:key", namespaced.toString());
    }
}