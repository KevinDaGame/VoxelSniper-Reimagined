package com.thevoxelbox.voxelsniper.util;

import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.BukkitBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

import org.bukkit.BlockChangeDelegate;
import org.bukkit.block.data.BlockData;

/**
 *
 */
public class UndoDelegate implements BlockChangeDelegate {

    private final IWorld targetWorld;
    private Undo currentUndo;

    public Undo getUndo() {
        final Undo pastUndo = currentUndo;
        currentUndo = new Undo();
        return pastUndo;
    }

    public UndoDelegate(IWorld targetWorld) {
        this.targetWorld = targetWorld;
        this.currentUndo = new Undo();
    }

    @Override
    public boolean setBlockData(int x, int y, int z, BlockData blockData) {
        this.currentUndo.put(targetWorld.getBlock(x, y, z));
        this.targetWorld.getBlock(x, y, z).setBlockData(BukkitBlockData.fromBukkitData(blockData), false);
        return true;
    }

    @Override
    public BlockData getBlockData(int x, int y, int z) {
        return ((BukkitBlockData)this.targetWorld.getBlock(x, y, z)).getBlockData();
    }

    public boolean setBlock(IBlock b) {
        this.currentUndo.put(this.targetWorld.getBlock(b.getLocation()));
        this.targetWorld.getBlock(b.getLocation()).setBlockData(b.getBlockData(), true);
        return true;
    }

    @Override
    public int getHeight() {
        return this.targetWorld.getMaxWorldHeight();
    }

    @Override
    public boolean isEmpty(int x, int y, int z) {
        return this.targetWorld.getBlock(x, y, z).isEmpty();
    }
}
