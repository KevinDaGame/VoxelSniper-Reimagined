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
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#splatter-voxel-brush">...</a>
 *
 * @author Voxel
 */
public class SplatterVoxelBrush extends SplatterBrushBase {

    public SplatterVoxelBrush() {
        this.setName("Splatter Voxel");
    }

    private void vSplatterBall(final SnipeData v, IBlock targetBlock) {
        final int brushSize = v.getBrushSize();
        var positions = Shapes.voxel(this.getTargetBlock().getLocation(), brushSize);
        var splat = this.splatter3D(v);
        for (var position : positions) {
            var val = splat[position.getBlockX() - this.getTargetBlock().getX() + brushSize][position.getBlockY() - this.getTargetBlock().getY() + brushSize][position.getBlockZ() - this.getTargetBlock().getZ() + brushSize];
            if (val == 1) {
                this.positions.add(position);
            }
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.vSplatterBall(v, this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.vSplatterBall(v, this.getLastBlock());
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if(super.parseParams(triggerHandle, params, v)) return;
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.SPLATTER_FOXEL_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        sendPerformerMessage(triggerHandle, v);
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.splattervoxel";
    }
}
