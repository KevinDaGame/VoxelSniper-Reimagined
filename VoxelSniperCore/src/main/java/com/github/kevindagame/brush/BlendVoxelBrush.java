package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BlendVoxelBrush extends BlendBrushBase {

    /**
     *
     */
    public BlendVoxelBrush() {
        this.setName("Blend Voxel");
    }

    @Override
    protected final void blend(final SnipeData v) {
        this.positions = Shapes.voxel(this.getTargetBlock().getLocation(), v.getBrushSize());
    }

    @Override
    protected boolean actPerform(SnipeData v) {
        final int brushSize = v.getBrushSize();
        var newMaterials = this.blend3D(brushSize);
        final Undo undo = new Undo();
        for(var position : this.positions) {
            var material = newMaterials[position.getBlockX() - this.getTargetBlock().getX() + brushSize][position.getBlockY() - this.getTargetBlock().getY() + brushSize][position.getBlockZ() - this.getTargetBlock().getZ() + brushSize];
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
            v.sendMessage(Messages.BLEND_VOXEL_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        super.parseParameters(triggerHandle, params, v);
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.blendvoxel";
    }
}
