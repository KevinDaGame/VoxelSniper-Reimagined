package com.github.kevindagame.util.BrushOperation;

import com.github.kevindagame.voxelsniper.location.VoxelLocation;

/**
 * Represents a brush operation that changes the biome of a location.
 */
public class BiomeOperation extends AbstractOperation {
    private final VoxelLocation location;
    private final String oldBiome;
    private String newBiome;

    public BiomeOperation(VoxelLocation location, String oldBiome, String newBiome) {
        this.location = location;
        this.oldBiome = oldBiome;
        this.newBiome = newBiome;
    }

    public VoxelLocation getLocation() {
        return location;
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
