package com.github.kevindagame.voxelsniper.world;

import com.github.kevindagame.util.brushOperation.BlockOperation;
import com.github.kevindagame.util.brushOperation.BlockStateOperation;
import com.github.kevindagame.util.brushOperation.BrushOperation;
import com.github.kevindagame.voxelsniper.blockdata.SpigotBlockData;
import com.github.kevindagame.voxelsniper.blockstate.SpigotBlockState;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import org.bukkit.BlockChangeDelegate;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 */
public class SpigotBlockLogger implements BlockChangeDelegate, Predicate<BlockState> {

    final List<BrushOperation> operations = new ArrayList<>();
    final boolean updateBlocks;
    private final IWorld world;
    private final World targetWorld;

    public SpigotBlockLogger(SpigotWorld targetWorld, boolean updateBlocks) {
        this.world = targetWorld;
        this.targetWorld = targetWorld.world();
        this.updateBlocks = updateBlocks;
    }

    @Override
    public boolean setBlockData(int x, int y, int z, @NotNull BlockData blockData) {
        var oldData = SpigotBlockData.fromSpigotData(getBlockData(x, y, z));
        operations.add(new BlockOperation(new BaseLocation(this.world, x, y, z), oldData, SpigotBlockData.fromSpigotData(blockData)));
        if (updateBlocks)
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

    @Override
    public boolean test(BlockState blockState) {
        var oldState = SpigotBlockState.fromSpigotState(blockState.getBlock().getState());
        var newState = SpigotBlockState.fromSpigotState(blockState);
        operations.add(new BlockStateOperation(new BaseLocation(this.world, blockState.getX(), blockState.getY(), blockState.getZ()), oldState, newState, true, true));
        return this.updateBlocks;
    }
}
