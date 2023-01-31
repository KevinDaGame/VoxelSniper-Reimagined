package com.github.kevindagame.util;

import com.github.kevindagame.util.brushOperation.BlockOperation;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.world.IWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Actions {

    /**
     * Returns the locations that are on a checker grid, according to their sum
     *
     * @param locations A list of locations
     * @param even      whether the sum of x + y + z should be even or odd
     * @return The locations of which the sum of x + y + z is even or odd
     */
    public static List<BaseLocation> checker(List<BaseLocation> locations, boolean even) {
        return locations.stream().filter(location -> (location.getBlockX() + location.getBlockY() + location.getBlockZ()) % 2 != (even ? 0 : 1)).collect(Collectors.toList());

    }

    public static List<BlockOperation> rotate(final IBlock target, final int bSize, final double angle, final RotationAxis axis) {
        List<BlockOperation> operations = new ArrayList<>();

        final int brushSize = (bSize * 2) + 1;
        final double brushSizeSquared = Math.pow(bSize + 0.5, 2);
        // We need to translate the other way around, since we look up the old block for every new block instead of the other way around
        final double se = Math.toRadians(angle * -1);
        final double cos = Math.cos(se);
        final double sin = Math.sin(se);
        // This describes a cylinder of sorts with the radius of bSize and the height of (bSize * 2) + 1
        // Put the height in the inside loop, since it doesn't have any power functions, should be faster.
        final IWorld world = target.getWorld();

        for (int w = -bSize; w <= bSize; w++) {
            final double wSquared = Math.pow(w, 2);
            for (int d = -bSize; d <= bSize; d++) {
                if (wSquared + Math.pow(d, 2) <= brushSizeSquared) {
                    final int oldW = (int) Math.round((w * cos) - (d * sin));
                    final int oldD = (int) Math.round((w * sin) + (d * cos));

                    for (int h = -bSize; h <= bSize; h++) {
                        var oldLoc = axis.getLocationTranslator().invoke(world, oldW, h, oldD);
                        oldLoc.add(target.getLocation());
                        var newLoc = axis.getLocationTranslator().invoke(world, w, h, d);
                        newLoc.add(target.getLocation());
                        if (newLoc.isInWorldHeight()) {
                            final IBlockData oldBlockData = oldLoc.isInWorldHeight() ? world.getBlock(oldLoc).getBlockData() : VoxelMaterial.AIR.createBlockData();
                            final IBlock newBlock = world.getBlock(newLoc);
                            operations.add(new BlockOperation(newBlock.getLocation(), newBlock.getBlockData(), oldBlockData));
                        }
                    }
                }
            }
        }
        return operations;
    }
}
