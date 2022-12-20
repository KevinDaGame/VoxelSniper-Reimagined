package com.github.kevindagame.util.BrushOperation;

import com.github.kevindagame.voxelsniper.location.VoxelLocation;

/**
 * Represents a brush operation that changes the biome of a location.
 */
public class BiomeOperation extends BrushOperation {
    private final String oldBiome;
    private String newBiome;

    public BiomeOperation(VoxelLocation location, String oldBiome, String newBiome) {
        super(location);
        this.oldBiome = oldBiome;
        this.newBiome = newBiome;
    }

    public String getOldBiome() {
        return oldBiome;
    }

    public String getNewBiome() {
        return newBiome;
    }

    public void setNewBiome(String newBiome) {
        this.newBiome = newBiome;
    }
}
