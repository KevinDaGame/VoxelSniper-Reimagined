package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.BrushOperation.BlockOperation;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#blend-ball-brush">...</a>
 */
public class BlendBallBrush extends BlendBrushBase {
    public BlendBallBrush() {
        this.setName("Blend Ball");
    }

    @Override
    protected final void blend(final SnipeData v) {
        var brushSize = v.getBrushSize();
        var positions = Shapes.ball(this.getTargetBlock().getLocation(), v.getBrushSize(), false);
        var newMaterials = this.blend3D(brushSize);
        for (var position : positions) {
            var material = newMaterials[position.getBlockX() - this.getTargetBlock().getX() + brushSize][position.getBlockY() - this.getTargetBlock().getY() + brushSize][position.getBlockZ() - this.getTargetBlock().getZ() + brushSize];
            if (!(this.excludeAir && material.isAir()) && !(this.excludeWater && (material == VoxelMaterial.WATER))) {
                getOperations().add(new BlockOperation(position, position.getBlock().getBlockData(), material.createBlockData()));
            }
        }
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.BLEND_BALL_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        super.parseParameters(triggerHandle, params, v);
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.blendball";
    }
}
