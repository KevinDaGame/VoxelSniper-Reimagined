package com.github.kevindagame.util;

import com.github.kevindagame.voxelsniper.block.BlockFace;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;

import java.util.HashSet;
import java.util.Set;

public class Shapes {
    public static Set<VoxelLocation> ball(VoxelLocation location, double radius, boolean smooth) {
        Set<VoxelLocation> positions = new HashSet<>();
        double SMOOTH_SPHERE_VALUE = 0.5;
        int VOXEL_SPHERE_VALUE = 0;

        int bx = location.getBlockX();
        int by = location.getBlockY();
        int bz = location.getBlockZ();
        var radiusSquared = Math.pow(radius + (smooth ? SMOOTH_SPHERE_VALUE : VOXEL_SPHERE_VALUE), 2);
        for (int x = (int) (bx - radius); x <= bx + radius; x++) {
            for (int y = (int) (by - radius); y <= by + radius; y++) {
                for (int z = (int) (bz - radius); z <= bz + radius; z++) {

                    double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y)));
                    if (distance <= radiusSquared) {
                        positions.add(new VoxelLocation(location.getWorld(), x, y, z));
                    }
                }
            }
        }

        return positions;
    }

    public static Set<VoxelLocation> voxel(VoxelLocation location, int brushSize) {
        Set<VoxelLocation> positions = new HashSet<>();
        for (int z = brushSize; z >= -brushSize; z--) {
            for (int x = brushSize; x >= -brushSize; x--) {
                for (int y = brushSize; y >= -brushSize; y--) {
                    positions.add(new VoxelLocation(location.getWorld(), location.getX() + x, location.getY() + z, location.getZ() + y));
                }
            }
        }
        return positions;
    }

    public static Set<VoxelLocation> disc(VoxelLocation location, int brushSize, boolean smooth) {
        Set<VoxelLocation> positions = new HashSet<>();
        double SMOOTH_CIRCLE_VALUE = 0.5;
        double VOXEL_CIRCLE_VALUE = 0.0;
        final double radiusSquared = (brushSize + (smooth ? SMOOTH_CIRCLE_VALUE : VOXEL_CIRCLE_VALUE)) * (brushSize + (smooth ? SMOOTH_CIRCLE_VALUE : VOXEL_CIRCLE_VALUE));
        final VoxelVector centerPoint = location.toVector();
        final VoxelVector currentPoint = centerPoint.clone();

        for (int x = -brushSize; x <= brushSize; x++) {
            currentPoint.setX(centerPoint.getX() + x);
            for (int z = -brushSize; z <= brushSize; z++) {
                currentPoint.setZ(centerPoint.getZ() + z);
                if (centerPoint.distanceSquared(currentPoint) <= radiusSquared) {
                    positions.add(new VoxelLocation(location.getWorld(), currentPoint.getBlockX(), currentPoint.getBlockY(), currentPoint.getBlockZ()));
                }
            }
        }
        return positions;
    }

    public static Set<VoxelLocation> voxelDisc(VoxelLocation location, int brushSize) {
        Set<VoxelLocation> positions = new HashSet<>();
        for (int x = brushSize; x >= -brushSize; x--) {
            for (int z = brushSize; z >= -brushSize; z--) {
                positions.add(new VoxelLocation(location.getWorld(), location.getX() + z, location.getY(), location.getZ() + z));
            }
        }
        return positions;
    }

    public static Set<VoxelLocation> cylinder(VoxelLocation location, int brushSize, int height, boolean smooth) {
        Set<VoxelLocation> positions = new HashSet<>();
        for (int y = height - 1; y < height; y++) {
            positions.addAll(disc(new VoxelLocation(location.getWorld(), location.getX(), location.getY() + y, location.getZ()), brushSize, smooth));
        }
        return positions;
    }

    public static Set<VoxelLocation> discFace(VoxelLocation location, int brushSize, boolean smooth, BlockFace face) {
        Set<VoxelLocation> disc = disc(location, brushSize, smooth);
        Set<VoxelLocation> positions = new HashSet<>();
        if(face == BlockFace.UP || face == BlockFace.DOWN) return disc;
        for (var pos : disc) {
            VoxelVector diff = location.toVector().subtract(pos.toVector());
            switch (face) {
                case NORTH, SOUTH ->
                        positions.add(new VoxelLocation(location.getWorld(), location.getX() + diff.getBlockX(), location.getY() + diff.getBlockZ(), location.getZ()));
                case EAST, WEST ->
                        positions.add(new VoxelLocation(location.getWorld(), location.getX(), location.getY() + diff.getBlockX(), location.getZ() + diff.getBlockZ()));
            }
        }
        return positions;
    }
}
