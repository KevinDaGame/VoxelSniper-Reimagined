package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.EnumSet;
import java.util.Set;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Eraser_Brush
 *
 * @author Voxel
 */
public class EraserBrush extends Brush {

    private static final Set<Material> EXCLUSIVE_MATERIALS = EnumSet.of(
            Material.AIR, Material.STONE, Material.GRASS_BLOCK, Material.DIRT, Material.SAND, Material.GRAVEL, Material.SANDSTONE);
    private static final Set<Material> EXCLUSIVE_LIQUIDS = EnumSet.of(
            Material.WATER, Material.LAVA);

    static {
        try {
            // 1.17+
            EXCLUSIVE_MATERIALS.add(Material.DEEPSLATE);
        } catch(Throwable ignore) {
            // Don't add for older versions
        }
    }

    /**
     *
     */
    public EraserBrush() {
        this.setName("Eraser");
    }

    private void doErase(final SnipeData v, final boolean keepWater) {
        final int brushSize = v.getBrushSize();
        final int brushSizeDoubled = 2 * brushSize;
        World world = this.getTargetBlock().getWorld();
        final Undo undo = new Undo();

        for (int x = brushSizeDoubled; x >= 0; x--) {
            int currentX = this.getTargetBlock().getX() - brushSize + x;
            for (int y = 0; y <= brushSizeDoubled; y++) {
                int currentY = this.getTargetBlock().getY() - brushSize + y;
                for (int z = brushSizeDoubled; z >= 0; z--) {
                    int currentZ = this.getTargetBlock().getZ() - brushSize + z;
                    Block currentBlock = world.getBlockAt(currentX, currentY, currentZ);
                    if (EXCLUSIVE_MATERIALS.contains(currentBlock.getMaterial())
                            || (keepWater && EXCLUSIVE_LIQUIDS.contains(currentBlock.getMaterial()))) {
                        continue;
                    }
                    undo.put(currentBlock);
                    currentBlock.setType(Material.AIR);
                }
            }
        }
        v.owner().storeUndo(undo);
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.doErase(v, false);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.doErase(v, true);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.eraser";
    }
}
