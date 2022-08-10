package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.material.BukkitMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

import java.util.ArrayList;
import java.util.Objects;

import org.bukkit.Material;

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

    private static final ArrayList<IMaterial> DENIED_UPDATES = new ArrayList<>();

    static {
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.ACACIA_SIGN));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.BIRCH_SIGN));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.DARK_OAK_SIGN));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.JUNGLE_SIGN));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.OAK_SIGN));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.ACACIA_WALL_SIGN));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.BIRCH_WALL_SIGN));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.DARK_OAK_WALL_SIGN));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.JUNGLE_WALL_SIGN));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.OAK_WALL_SIGN));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.CHEST));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.FURNACE));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.REDSTONE_TORCH));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.REDSTONE_WIRE));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.REPEATER));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.ACACIA_DOOR));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.BIRCH_DOOR));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.DARK_OAK_DOOR));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.JUNGLE_DOOR));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.OAK_DOOR));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.IRON_DOOR));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.ACACIA_FENCE_GATE));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.BIRCH_FENCE_GATE));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.DARK_OAK_FENCE_GATE));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.JUNGLE_FENCE_GATE));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.OAK_FENCE_GATE));
        BlockResetSurfaceBrush.DENIED_UPDATES.add(new BukkitMaterial(Material.AIR));
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

                    if (Objects.equals(world.getBlock(this.getTargetBlock().getX() + x + 1, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z).getMaterial(), new BukkitMaterial(Material.AIR))) {
                        block = world.getBlock(this.getTargetBlock().getX() + x + 1, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z);
                        resetBlock(block);
                        airFound = true;
                    }

                    if (Objects.equals(world.getBlock(this.getTargetBlock().getX() + x - 1, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z).getMaterial(), new BukkitMaterial(Material.AIR))) {
                        block = world.getBlock(this.getTargetBlock().getX() + x - 1, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z);
                        resetBlock(block);
                        airFound = true;
                    }

                    if (Objects.equals(world.getBlock(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y + 1, this.getTargetBlock().getZ() + z).getMaterial(), new BukkitMaterial(Material.AIR))) {
                        block = world.getBlock(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y + 1, this.getTargetBlock().getZ() + z);
                        resetBlock(block);
                        airFound = true;
                    }

                    if (Objects.equals(world.getBlock(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y - 1, this.getTargetBlock().getZ() + z).getMaterial(), new BukkitMaterial(Material.AIR))) {
                        block = world.getBlock(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y - 1, this.getTargetBlock().getZ() + z);
                        resetBlock(block);
                        airFound = true;
                    }

                    if (Objects.equals(world.getBlock(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z + 1).getMaterial(), new BukkitMaterial(Material.AIR))) {
                        block = world.getBlock(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z + 1);
                        resetBlock(block);
                        airFound = true;
                    }

                    if (Objects.equals(world.getBlock(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z - 1).getMaterial(), new BukkitMaterial(Material.AIR))) {
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
