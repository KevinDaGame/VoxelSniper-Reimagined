package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.util.brushOperation.BlockOperation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#blend-voxel-disc-brush">...</a>
 */
public class BlendVoxelDiscBrush extends BlendBrushBase {

    /**
     *
     */
    public BlendVoxelDiscBrush() {
        this.setName("Blend Voxel Disc");
    }

    @Override
    protected final void blend(final SnipeData v) {
        var positions = Shapes.voxelDisc(this.getTargetBlock().getLocation(), v.getBrushSize());
        var brushSize = v.getBrushSize();
        var newMaterials = this.blend2D(brushSize);

        for (var position : positions) {
            var material = newMaterials[position.getBlockX() - this.getTargetBlock().getX() + brushSize][position.getBlockZ() - this.getTargetBlock().getZ() + brushSize];
            if (!(this.excludeAir && material.isAir()) && !(this.excludeWater && (material == VoxelMaterial.WATER))) {
                addOperation(new BlockOperation(position, position.getBlock().getBlockData(), material.createBlockData()));
            }
        }
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.BALL_BLEND_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        super.parseParameters(triggerHandle, params, v);
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.blendvoxeldisc";
    }
}
