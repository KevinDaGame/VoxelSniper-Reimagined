package com.thevoxelbox.voxelsniper.snipe;

import com.google.common.collect.Sets;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockstate.IBlockState;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.IVector;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Furnace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.NoteBlock;

/**
 * Holds {@link BlockState}s that can be later on used to reset those block locations back to the recorded states.
 */
public class Undo {

    private static final List<VoxelMaterial> FALLING_MATERIALS = Arrays.asList(
            VoxelMaterial.WATER,
            VoxelMaterial.LAVA);
    private final Set<IVector> containing = Sets.newHashSet();
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
        IVector pos = block.getLocation().toVector();
        if (this.containing.contains(pos)) {
            return;
        }
        this.containing.add(pos);
        if (Undo.FALLING_MATERIALS.contains(block.getMaterial())) {
            dropdown.add(block.getState());
        } else if (block.getMaterial().fallsOff()) {
            falloff.add(block.getState());
        } else {
            all.add(block.getState());
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
