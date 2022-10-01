package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
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
 *
 * @author GavJenks
 */
public class BlockResetSurfaceBrush extends Brush {

    private static final ArrayList<VoxelMaterial> DENIED_UPDATES = new ArrayList<>();

    static {
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.ACACIA_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.BIRCH_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.DARK_OAK_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.JUNGLE_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.OAK_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.ACACIA_WALL_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.BIRCH_WALL_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.DARK_OAK_WALL_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.JUNGLE_WALL_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.OAK_WALL_SIGN);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.CHEST);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.FURNACE);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.REDSTONE_TORCH);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.REDSTONE_WIRE);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.REPEATER);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.ACACIA_DOOR);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.BIRCH_DOOR);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.DARK_OAK_DOOR);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.JUNGLE_DOOR);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.OAK_DOOR);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.IRON_DOOR);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.ACACIA_FENCE_GATE);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.BIRCH_FENCE_GATE);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.DARK_OAK_FENCE_GATE);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.JUNGLE_FENCE_GATE);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.OAK_FENCE_GATE);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.AIR);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.VOID_AIR);
        BlockResetSurfaceBrush.DENIED_UPDATES.add(VoxelMaterial.CAVE_AIR);
    }

    /**
     *
     */
    public BlockResetSurfaceBrush() {
        this.setName("Block Reset Brush Surface Only");
    }

    @SuppressWarnings("deprecation")
    private void applyBrush(final SnipeData v) {
        final IWorld world = this.getWorld();

        for (int z = -v.getBrushSize(); z <= v.getBrushSize(); z++) {
            for (int x = -v.getBrushSize(); x <= v.getBrushSize(); x++) {
                for (int y = -v.getBrushSize(); y <= v.getBrushSize(); y++) {
                    // TODO clampY
                    IBlock block = world.getBlock(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z);
                    if (BlockResetSurfaceBrush.DENIED_UPDATES.contains(block.getMaterial())) {
                        continue;
                    }

                    boolean airFound = false;

                    if (world.getBlock(this.getTargetBlock().getX() + x + 1, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z).getMaterial().isAir()) {
                        block = world.getBlock(this.getTargetBlock().getX() + x + 1, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z);
                        resetBlock(block);
                        airFound = true;
                    }

                    if (world.getBlock(this.getTargetBlock().getX() + x - 1, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z).getMaterial().isAir()) {
                        block = world.getBlock(this.getTargetBlock().getX() + x - 1, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z);
                        resetBlock(block);
                        airFound = true;
                    }

                    if (world.getBlock(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y + 1, this.getTargetBlock().getZ() + z).getMaterial().isAir()) {
                        block = world.getBlock(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y + 1, this.getTargetBlock().getZ() + z);
                        resetBlock(block);
                        airFound = true;
                    }

                    if (world.getBlock(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y - 1, this.getTargetBlock().getZ() + z).getMaterial().isAir()) {
                        block = world.getBlock(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y - 1, this.getTargetBlock().getZ() + z);
                        resetBlock(block);
                        airFound = true;
                    }

                    if (world.getBlock(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z + 1).getMaterial().isAir()) {
                        block = world.getBlock(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z + 1);
                        resetBlock(block);
                        airFound = true;
                    }

                    if (world.getBlock(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z - 1).getMaterial().isAir()) {
                        block = world.getBlock(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z - 1);
                        resetBlock(block);
                        airFound = true;
                    }

                    if (airFound) {
                        block = world.getBlock(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z);
                        resetBlock(block);
                    }
                }
            }
        }
    }

    private void resetBlock(IBlock block) {
        // Resets the block state to initial state by creating a new BlockData with default values.
        block.setBlockData(block.getBlockData().getMaterial().createBlockData(), true);
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

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.blockresetsurface";
    }
}
