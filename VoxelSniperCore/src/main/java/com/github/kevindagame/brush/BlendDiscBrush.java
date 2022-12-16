package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#blend-disc-brush">...</a>
 */
public class BlendDiscBrush extends BlendBrushBase {

    /**
     *
     */
    public BlendDiscBrush() {
        this.setName("Blend Disc");
    }

    protected final void blend(final SnipeData v) {
        this.positions = Shapes.disc(this.getTargetBlock().getLocation(), v.getBrushSize(), false);
    }

    @Override
    protected boolean actPerform(SnipeData v) {
        var brushSize = v.getBrushSize();
        final Undo undo = new Undo();
        var newMaterials = this.blend2D(brushSize);

        for(var position : this.positions) {
            var material = newMaterials[position.getBlockX() - this.getTargetBlock().getX() + brushSize][position.getBlockZ() - this.getTargetBlock().getZ() + brushSize];
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
            v.sendMessage(Messages.BLEND_DISC_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        super.parseParameters(triggerHandle, params, v);
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.blenddisc";
    }
}
