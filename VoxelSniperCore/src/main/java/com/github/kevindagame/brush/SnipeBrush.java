package com.github.kevindagame.brush;

import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.VoxelMessage;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#snipe-brush">...</a>
 *
 * @author Voxel
 */
public class SnipeBrush extends PerformerBrush {

    /**
     *
     */
    public SnipeBrush() {
        this.setName("Snipe");
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.currentPerformer.perform(this.getTargetBlock());
        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.currentPerformer.perform(this.getLastBlock());
        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.snipe";
    }
}
