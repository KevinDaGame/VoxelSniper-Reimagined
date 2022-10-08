package com.github.kevindagame.snipe;

import com.google.common.collect.Sets;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockstate.IBlockState;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Holds BlockStates that can be later on used to reset those block locations back to the recorded states.
 */
public class Undo {

    private static final List<VoxelMaterial> FALLING_MATERIALS = Arrays.asList(
            VoxelMaterial.WATER,
            VoxelMaterial.LAVA);
    private final Set<VoxelVector> containing = Sets.newHashSet();
    private final List<IBlockState> all;
    private final List<IBlockState> falloff;
    private final List<IBlockState> dropdown;

    /**
     * Default constructor of a Undo container.
     */
    public Undo() {
        all = new LinkedList<>();
        falloff = new LinkedList<>();
        dropdown = new LinkedList<>();
    }

    /**
     * Get the number of blocks in the collection.
     *
     * @return size of the Undo collection
     */
    public int getSize() {
        return containing.size();
    }

    /**
     * Adds a Block to the collection.
     *
     * @param block Block to be added
     */
    public void put(IBlock block) {
        this.put(block.getState());
    }

    /**
     * Adds a Block to the collection.
     *
     * @param state BlockState to be added
     */
    public void put(IBlockState state) {
        VoxelVector pos = state.getLocation().toVector();
        if (this.containing.contains(pos)) {
            return;
        }
        this.containing.add(pos);
        if (Undo.FALLING_MATERIALS.contains(state.getMaterial())) {
            dropdown.add(state);
        } else if (state.getMaterial().fallsOff()) {
            falloff.add(state);
        } else {
            all.add(state);
        }
    }

    /**
     * Set the blockstates of all recorded blocks back to the state when they were inserted.
     */
    public void undo() {

        for (IBlockState blockState : all) {
            blockState.update(true, false);
        }

        for (IBlockState blockState : falloff) {
            blockState.update(true, false);
        }

        for (IBlockState blockState : dropdown) {
            blockState.update(true, false);
        }
    }
}
