package com.github.kevindagame.voxelsniper.world;

import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.voxelsniper.blockstate.SpigotBlockState;
import org.bukkit.BlockChangeDelegate;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class SpigotUndoDelegate implements BlockChangeDelegate {

    private final World targetWorld;
    private final Undo currentUndo;

    public SpigotUndoDelegate(World targetWorld, Undo undo) {
        this.targetWorld = targetWorld;
        this.currentUndo = undo;
    }

    @Override
    public boolean setBlockData(int x, int y, int z, @NotNull BlockData blockData) {
        this.currentUndo.put(SpigotBlockState.fromSpigotState(targetWorld.getBlockAt(x, y, z).getState()));
        this.targetWorld.getBlockAt(x, y, z).setBlockData(blockData, false);
        return true;
    }

    @NotNull
    @Override
    public BlockData getBlockData(int x, int y, int z) {
        return this.targetWorld.getBlockAt(x, y, z).getBlockData();
    }

    @Override
    public int getHeight() {
        return this.targetWorld.getMaxHeight();
    }

    @Override
    public boolean isEmpty(int x, int y, int z) {
        return this.targetWorld.getBlockAt(x, y, z).isEmpty();
    }
}
