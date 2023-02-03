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

    public static List<BlockOperation> rotate3D(final IBlock target, final int bSize, final double roll, final double yaw, final double pitch) {
        // basically rotates in a sphere around the target block, doing three rotations in a row, one in each dimension, unless some dimensions are set to zero or udnefined or whatever, then skip those.

        List<BlockOperation> operations = new ArrayList<>();
        final double brushSizeSquared = Math.pow(bSize + 0.5, 2);
        final IWorld world = target.getWorld();

        final double seYaw = Math.toRadians(yaw * -1);
        final double sePitch = Math.toRadians(pitch * -1);
        final double seRoll = Math.toRadians(roll * -1);
        final double cosYaw = Math.cos(seYaw);
        final double sinYaw = Math.sin(seYaw);
        final double cosPitch = Math.cos(sePitch);
        final double sinPitch = Math.sin(sePitch);
        final double cosRoll = Math.cos(seRoll);
        final double sinRoll = Math.sin(seRoll);

        for (int x = -bSize; x <= bSize; x++) {
            final double xSquared = Math.pow(x, 2);

            for (int z = -bSize; z <= bSize; z++) {
                final double zSquared = Math.pow(z, 2);

                final double oldxzX = (x * cosYaw) - (z * sinYaw);
                final double oldxzZ = (x * sinYaw) + (z * cosYaw);

                for (int y = -bSize; y <= bSize; y++) {
                    if (xSquared + zSquared + Math.pow(y, 2) <= brushSizeSquared) {
                        final int oldX = (int) Math.round((oldxzX * cosPitch) - (y * sinPitch));
                        final double oldxyY = (oldxzX * sinPitch) + (y * cosPitch); // calculates all three in succession in precise math space
                        final int oldY = (int) Math.round((oldxyY * cosRoll) - (oldxzZ * sinRoll));
                        final int oldZ = (int) Math.round((oldxyY * sinRoll) + (oldxzZ * cosRoll));

                        if (!world.isInWorldHeight(target.getY() + y)) continue;
                        final IBlockData oldBlockData = world.isInWorldHeight(target.getY() + oldY) ? world.getBlock(target.getX() + oldX, target.getY() + oldY, target.getZ() + oldZ).getBlockData() : VoxelMaterial.AIR.createBlockData();
                        final IBlock newBlock = world.getBlock(target.getX() + x, target.getY() + y, target.getZ() + z);

                        operations.add(new BlockOperation(newBlock.getLocation(), newBlock.getBlockData(), oldBlockData));
                    }
                }
            }
        }

        return operations;
    }
}
