package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Blend_Brushes
 */
public class BlendBallBrush extends BlendBrushBase {

    /**
     * TODO: NEEDS TO CHECK AFTER REWRITE
     */
    public BlendBallBrush() {
        this.setName("Blend Ball");
    }

    @Override
    protected final void blend(final SnipeData v) {
        this.positions = Shapes.ball(this.getTargetBlock().getLocation(), v.getBrushSize(), false);
    }

    @Override
    protected boolean actPerform(SnipeData v) {
        final int brushSize = v.getBrushSize();
        var newMaterials = this.blend3D(brushSize);
        final Undo undo = new Undo();
        for(var position : this.positions) {
                var material = newMaterials[this.getTargetBlock().getX() - position.getBlockX() + brushSize][this.getTargetBlock().getY() - position.getBlockY() + brushSize][this.getTargetBlock().getZ() - position.getBlockZ() + brushSize];
            if (!(this.excludeAir && material.isAir()) && !(this.excludeWater && (material == VoxelMaterial.WATER))) {

                undo.put(position.getBlock());
                position.getBlock().setMaterial(material);
            }
        }
        v.owner().storeUndo(undo);
        return true;
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
