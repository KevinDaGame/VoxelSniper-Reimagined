package com.github.kevindagame.brush;

import com.github.kevindagame.util.Shapes;
import com.google.common.collect.Lists;
import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 *
 *
 * @author Voxel
 */
public class SplatterVoxelDiscBrush extends SplatterBrushBase {



    /**
     *
     */
    public SplatterVoxelDiscBrush() {
        this.setName("Splatter Voxel Disc");
    }

    private void vSplatterDisc(final SnipeData v, IBlock targetBlock) {
        var positions = Shapes.voxelDisc(this.getTargetBlock().getLocation(), v.getBrushSize());
        var splat = splatter2D(v);
        final int brushSize = v.getBrushSize();
        for (var position : positions) {
            var val = splat[position.getBlockX() - this.getTargetBlock().getX() + brushSize][position.getBlockZ() - this.getTargetBlock().getZ() + brushSize];
            if (val == 1) {
                this.positions.add(position);
            }
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.vSplatterDisc(v, this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.vSplatterDisc(v, this.getLastBlock());
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if(super.parseParams(triggerHandle, params, v)) return;
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.SPLATTER_VOXEL_DISC_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }
        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        sendPerformerMessage(triggerHandle, v);
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.splattervoxeldisc";
    }
}
