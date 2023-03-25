package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.BlockOperation;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType;
import com.github.kevindagame.voxelsniper.world.IWorld;

import java.util.ArrayList;

/**
 * This brush only looks for solid blocks, and then changes those plus any air blocks touching them. If it works, this brush should be faster than the original
 * blockPositionY an amount proportional to the volume of a snipe selection area / the number of blocks touching air in the selection. This is because every
 * solid block surrounded blockPositionY others should take equally long to check and not change as it would take MC to change them and then check and find no
 * lighting to update. For air blocks surrounded blockPositionY other air blocks, this brush saves about 80-100 checks blockPositionY not updating them or their
 * lighting. And for air blocks touching solids, this brush is slower, because it replaces the air once per solid block it is touching. I assume on average this
 * is about 2 blocks. So every air block touching a solid negates one air block floating in air. Thus, for selections that have more air blocks surrounded
 * blockPositionY air than air blocks touching solids, this brush will be faster, which is almost always the case, especially for undeveloped terrain and for
 * larger brush sizes (unlike the original brush, this should only slow down blockPositionY the square of the brush size, not the cube of the brush size). For
 * typical terrain, blockPositionY my calculations, overall speed increase is about a factor of 5-6 for a size 20 brush. For a complicated city or ship, etc.,
 * this may be only a factor of about 2. In a hypothetical worst case scenario of a 3d checkerboard of stone and air every other block, this brush should only
 * be about 1.5x slower than the original brush. Savings increase for larger brushes.
 * <br>
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#block-reset-brush-surface-only-brush">...</a>
 *
 * @author GavJenks
 */
public class BlockResetSurfaceBrush extends AbstractBrush {

    private static final ArrayList<VoxelMaterialType> DENIED_UPDATES = new ArrayList<>();

    static {
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.ACACIA_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.BIRCH_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.DARK_OAK_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.JUNGLE_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.OAK_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.ACACIA_WALL_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.BIRCH_WALL_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.DARK_OAK_WALL_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.JUNGLE_WALL_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.OAK_WALL_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.CHEST);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.FURNACE);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.REDSTONE_TORCH);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.REDSTONE_WIRE);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.REPEATER);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.ACACIA_DOOR);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.BIRCH_DOOR);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.DARK_OAK_DOOR);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.JUNGLE_DOOR);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.OAK_DOOR);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.IRON_DOOR);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.ACACIA_FENCE_GATE);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.BIRCH_FENCE_GATE);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.DARK_OAK_FENCE_GATE);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.JUNGLE_FENCE_GATE);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.OAK_FENCE_GATE);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.AIR);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.VOID_AIR);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterialType.CAVE_AIR);
    }


    private void applyBrush(final SnipeData v) {
        final IWorld world = this.getWorld();

        for (int z = -v.getBrushSize(); z <= v.getBrushSize(); z++) {
            for (int x = -v.getBrushSize(); x <= v.getBrushSize(); x++) {
                for (int y = -v.getBrushSize(); y <= v.getBrushSize(); y++) {
                    IBlock block = world.getBlock(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z);
                    if (BlockResetSurfaceBrush.DENIED_UPDATES.contains(block.getMaterial())) {
                        continue;
                    }

                    boolean airFound;

                    airFound = checkBlock(world, x + 1, y, z);
                    airFound = checkBlock(world, x - 1, y, z) || airFound;
                    airFound = checkBlock(world, x, y + 1, z) || airFound;
                    airFound = checkBlock(world, x, y - 1, z) || airFound;
                    airFound = checkBlock(world, x, y, z + 1) || airFound;
                    airFound = checkBlock(world, x, y, z - 1) || airFound;

                    if (airFound) {
                        var location = new BaseLocation(getWorld(), this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z);

                        resetBlock(location);
                    }
                }
            }
        }
    }

    private boolean checkBlock(IWorld world, int x, int y, int z) {
        IBlock block = world.getBlock(this.getTargetBlock().getX() + x + 1, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z);
        if (block.getMaterial().isAir()) {
            resetBlock(block.getLocation());
            return true;
        }
        return false;
    }

    private void resetBlock(BaseLocation location) {
        // create an operation to reset the block
        var block = location.getBlock();
        addOperation(new BlockOperation(location, block.getBlockData(), block.getMaterial().createBlockData()));
    }

    @Override
    protected final void arrow(final SnipeData v) {
        applyBrush(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        applyBrush(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
    }
}
