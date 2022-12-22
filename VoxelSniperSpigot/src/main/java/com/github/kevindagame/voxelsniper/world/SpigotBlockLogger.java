package com.github.kevindagame.voxelsniper.world;

import com.github.kevindagame.util.BrushOperation.BlockOperation;
import com.github.kevindagame.voxelsniper.blockdata.SpigotBlockData;
import com.github.kevindagame.voxelsniper.location.BaseLocation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.BlockChangeDelegate;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class SpigotBlockLogger implements BlockChangeDelegate {

    private final IWorld world;
    private final World targetWorld;
    final List<BlockOperation> operations = new ArrayList<>();

    public SpigotBlockLogger(SpigotWorld targetWorld) {
        this.world = targetWorld;
        this.targetWorld = targetWorld.world();
    }

    @Override
    public boolean setBlockData(int x, int y, int z, @NotNull BlockData blockData) {
        var oldData = SpigotBlockData.fromSpigotData(getBlockData(x, y, z));
        operations.add(new BlockOperation(new BaseLocation(this.world, x, y, z), oldData, SpigotBlockData.fromSpigotData(blockData)));
//        this.currentUndo.put(SpigotBlockState.fromSpigotState(targetWorld.getBlockAt(x, y, z).getState()));
//        this.targetWorld.getBlockAt(x, y, z).setBlockData(blockData, false);
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
