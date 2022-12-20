package com.github.kevindagame.util.BrushOperation;

import com.github.kevindagame.voxelsniper.biome.VoxelBiome;
import com.github.kevindagame.voxelsniper.location.BaseLocation;

/**
 * Represents a brush operation that changes the biome of a location.
 */
public class BiomeOperation extends BrushOperation {
    private final VoxelBiome oldBiome;
    private VoxelBiome newBiome;

    public BiomeOperation(BaseLocation location, VoxelBiome oldBiome, VoxelBiome newBiome) {
        super(location);
        this.oldBiome = oldBiome;
        this.newBiome = newBiome;
    }

    public VoxelBiome getOldBiome() {
        return oldBiome;
    }

    public VoxelBiome getNewBiome() {
        return newBiome;
    }

    public void setNewBiome(VoxelBiome newBiome) {
        this.newBiome = newBiome;
    }
}
