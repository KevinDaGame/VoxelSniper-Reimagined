package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Shapes;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#splatter-disc-brush">...</a>
 *
 * @author Voxel
 */
public class SplatterDiscBrush extends SplatterBrushBase {




    private void splatterDisc(final SnipeData v) {
        var positions = Shapes.disc(this.getTargetBlock().getLocation(), v.getBrushSize(), false);
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
    protected final void doArrow(final SnipeData v) {
        this.splatterDisc(v);
    }

    @Override
    protected final void doPowder(final SnipeData v) {
        this.splatterDisc(v);
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (super.parseParams(triggerHandle, params, v)) return;
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.SPLATTER_DISC_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }
        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        sendPerformerMessage(triggerHandle, v);
    }
}
