package com.github.kevindagame.util;

import com.github.kevindagame.util.brushOperation.BlockOperation;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType;
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

    public static List<BlockOperation> rotate3D(final IBlock target, List<BaseLocation> locations, final double roll, final double yaw, final double pitch) {
        // basically rotates the blocks at all the positions in the List around the target block, doing three rotations in a row, one in each dimension

        List<BlockOperation> operations = new ArrayList<>();
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

        for (BaseLocation newPos : locations) {
            if (newPos.isInWorldHeight()) {
                final int dx = newPos.getBlockX() - target.getX();
                final int dy = newPos.getBlockY() - target.getY();
                final int dz = newPos.getBlockZ() - target.getZ();

                // calculate where we are rotating from, this is more reliable than calculating where to rotate to
                final double oldxzX = (dx * cosYaw) - (dz * sinYaw);
                final double oldxzZ = (dx * sinYaw) + (dz * cosYaw);
                final double oldxyY = (oldxzX * sinPitch) + (dy * cosPitch);
                final int oldX = target.getX() + (int) Math.round((oldxzX * cosPitch) - (dy * sinPitch));
                final int oldY = target.getY() + (int) Math.round((oldxyY * cosRoll) - (oldxzZ * sinRoll));
                final int oldZ = target.getZ() + (int) Math.round((oldxyY * sinRoll) + (oldxzZ * cosRoll));

                final IBlockData oldPosBlockData = world.isInWorldHeight(oldY) ? world.getBlock(oldX, oldY, oldZ).getBlockData() : VoxelMaterialType.AIR.createBlockData();
                final IBlockData newPosBlockData = newPos.getBlock().getBlockData();

                if (!newPosBlockData.matches(oldPosBlockData))
                    operations.add(new BlockOperation(newPos, newPosBlockData, oldPosBlockData));
            }
        }

        return operations;
    }
}
