package com.github.kevindagame.util;

import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.voxelsniper.block.BlockFace;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;
import com.github.kevindagame.voxelsniper.world.IWorld;

import java.util.*;

public class Shapes {
    static double SMOOTH_CIRCLE_VALUE = 0.5;
    static double VOXEL_CIRCLE_VALUE = 0.0;

    public static List<BaseLocation> ball(BaseLocation location, double radius, boolean smooth) {
        List<BaseLocation> positions = new ArrayList<>();
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
                        positions.add(new BaseLocation(location.getWorld(), x, y, z));
                    }
                }
            }
        }

        return positions;
    }

    public static List<BaseLocation> voxel(BaseLocation location, int brushSize) {
        List<BaseLocation> positions = new ArrayList<>();
        for (int z = brushSize; z >= -brushSize; z--) {
            for (int x = brushSize; x >= -brushSize; x--) {
                for (int y = brushSize; y >= -brushSize; y--) {
                    positions.add(new BaseLocation(location.getWorld(), location.getX() + x, location.getY() + z, location.getZ() + y));
                }
            }
        }
        return positions;
    }

    public static List<BaseLocation> disc(BaseLocation location, int brushSize, boolean smooth) {
        List<BaseLocation> positions = new ArrayList<>();
        final double radiusSquared = (brushSize + (smooth ? SMOOTH_CIRCLE_VALUE : VOXEL_CIRCLE_VALUE)) * (brushSize + (smooth ? SMOOTH_CIRCLE_VALUE : VOXEL_CIRCLE_VALUE));
        final VoxelVector centerPoint = location.toVector();
        final VoxelVector currentPoint = centerPoint.clone();

        for (int x = -brushSize; x <= brushSize; x++) {
            currentPoint.setX(centerPoint.getX() + x);
            for (int z = -brushSize; z <= brushSize; z++) {
                currentPoint.setZ(centerPoint.getZ() + z);
                if (centerPoint.distanceSquared(currentPoint) <= radiusSquared) {
                    positions.add(new BaseLocation(location.getWorld(), currentPoint.getBlockX(), currentPoint.getBlockY(), currentPoint.getBlockZ()));
                }
            }
        }
        return positions;
    }

    public static List<BaseLocation> voxelDisc(BaseLocation location, int brushSize) {
        List<BaseLocation> positions = new ArrayList<>();
        for (int x = brushSize; x >= -brushSize; x--) {
            for (int z = brushSize; z >= -brushSize; z--) {
                positions.add(new BaseLocation(location.getWorld(), location.getX() + x, location.getY(), location.getZ() + z));
            }
        }
        return positions;
    }

    public static List<BaseLocation> cylinder(BaseLocation location, int brushSize, int height, int shift, boolean smooth) {
        List<BaseLocation> positions = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            positions.addAll(disc(new BaseLocation(location.getWorld(), location.getX(), location.getY() + y - (height / 2.0) + 1 + shift, location.getZ()), brushSize, smooth));
        }
        return positions;
    }

    public static List<BaseLocation> discFace(BaseLocation location, int brushSize, boolean smooth, BlockFace face) {
        List<BaseLocation> disc = disc(location, brushSize, smooth);
        return face(location, disc, face);
    }

    public static List<BaseLocation> voxelDiscFace(BaseLocation location, int brushSize, BlockFace face) {
        List<BaseLocation> voxelDisc = voxelDisc(location, brushSize);
        return face(location, voxelDisc, face);
    }

    public static List<BaseLocation> face(BaseLocation center, List<BaseLocation> locations, BlockFace face) {
        if (face == BlockFace.UP || face == BlockFace.DOWN) return locations;

        List<BaseLocation> positions = new ArrayList<>();
        for (var pos : locations) {
            VoxelVector diff = center.toVector().subtract(pos.toVector());
            switch (face) {
                case NORTH, SOUTH -> positions.add(new BaseLocation(center.getWorld(), center.getX() + diff.getBlockX(), center.getY() + diff.getBlockZ(), center.getZ()));
                case EAST, WEST -> positions.add(new BaseLocation(center.getWorld(), center.getX(), center.getY() + diff.getBlockX(), center.getZ() + diff.getBlockZ()));
            }
        }
        return positions;
    }

    public static List<BaseLocation> dome(BaseLocation location, int brushSize, int height) {

        final List<BaseLocation> positions = new ArrayList<>();
        final int absoluteHeight = Math.abs(height);
        final boolean negative = height < 0;

        final Undo undo = new Undo();

        final int brushSizeTimesVoxelHeight = brushSize * absoluteHeight;
        final double stepScale = ((brushSize * brushSize) + brushSizeTimesVoxelHeight + brushSizeTimesVoxelHeight) / 5.0;

        final double stepSize = 1 / stepScale;

        for (double u = 0; u <= Math.PI / 2; u += stepSize) {
            final double y = absoluteHeight * Math.sin(u);
            for (double stepV = -Math.PI; stepV <= -(Math.PI / 2); stepV += stepSize) {
                final double x = brushSize * Math.cos(u) * Math.cos(stepV);
                final double z = brushSize * Math.cos(u) * Math.sin(stepV);

                final double targetBlockX = location.getX() + 0.5;
                final double targetBlockZ = location.getZ() + 0.5;
                final int targetY = ((int) Math.floor(location.getY() + (negative ? -y : y)));
                final int currentBlockXAdd = ((int) Math.floor(targetBlockX + x));
                final int currentBlockZAdd = ((int) Math.floor(targetBlockZ + z));
                final int currentBlockXSubtract = ((int) Math.floor(targetBlockX - x));
                final int currentBlockZSubtract = ((int) Math.floor(targetBlockZ - z));
                positions.add(new BaseLocation(location.getWorld(), currentBlockXAdd, targetY, currentBlockZAdd));
                positions.add(new BaseLocation(location.getWorld(), currentBlockXSubtract, targetY, currentBlockZAdd));
                positions.add(new BaseLocation(location.getWorld(), currentBlockXAdd, targetY, currentBlockZSubtract));
                positions.add(new BaseLocation(location.getWorld(), currentBlockXSubtract, targetY, currentBlockZSubtract));

            }
        }
        return positions;
    }

    public static List<BaseLocation> line(VoxelVector originCoords, VoxelVector targetCoords, IWorld world) {
        final VoxelVector HALF_BLOCK_OFFList = new VoxelVector(0.5, 0.5, 0.5);
        final VoxelVector originClone = originCoords.clone().add(HALF_BLOCK_OFFList);
        final VoxelVector targetClone = targetCoords.clone().add(HALF_BLOCK_OFFList);

        List<BaseLocation> positions = new ArrayList<>();
        final VoxelVector direction = targetClone.clone().subtract(originClone);
        final double length = targetCoords.distance(originCoords);

        if (length == 0) {
            positions.add(targetCoords.getLocation(world));
        } else {
            for (final Iterator<IBlock> blockIterator = world.getBlockIterator(originClone, direction, 0, (int) Math.round(length)); blockIterator.hasNext(); ) {
                final IBlock currentBlock = blockIterator.next();
                positions.add(currentBlock.getLocation());
            }
        }
        return positions;
    }

    public static List<BaseLocation> ring(BaseLocation location, int radius, double innerSize, boolean smooth) {
        List<BaseLocation> positions = new ArrayList<>();
        final double outerSquared = Math.pow(radius + (smooth ? SMOOTH_CIRCLE_VALUE : VOXEL_CIRCLE_VALUE), 2);
        final double innerSquared = Math.pow(innerSize, 2);

        for (int x = radius; x >= 0; x--) {
            final double xSquared = Math.pow(x, 2);
            for (int z = radius; z >= 0; z--) {
                final double ySquared = Math.pow(z, 2);
                if ((xSquared + ySquared) <= outerSquared && (xSquared + ySquared) >= innerSquared) {
                    positions.add(new BaseLocation(location.getWorld(), location.getX() + x, location.getY(), location.getZ() + z));
                    positions.add(new BaseLocation(location.getWorld(), location.getX() - x, location.getY(), location.getZ() + z));
                    positions.add(new BaseLocation(location.getWorld(), location.getX() + x, location.getY(), location.getZ() - z));
                    positions.add(new BaseLocation(location.getWorld(), location.getX() - x, location.getY(), location.getZ() - z));
                }
            }
        }
        return positions;
    }
}
