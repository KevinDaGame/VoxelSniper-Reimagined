package com.github.kevindagame.snipe;

import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockstate.IBlockState;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Holds BlockStates that can be later on used to reset those block locations back to the recorded states.
 */
public class Undo {
    private final Set<VoxelVector> containing = new HashSet<>();
    private final List<IBlockState> undoList;

    /**
     * Default constructor of a Undo container.
     */
    public Undo() {
        undoList = new LinkedList<>();
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
            undoList.add(state);
    }

    /**
     * Set the blockstates of all recorded blocks back to the state when they were inserted.
     */
    public void undo() {
        for (IBlockState blockState : undoList) {
            blockState.update(true, false);
        }
    }
}
