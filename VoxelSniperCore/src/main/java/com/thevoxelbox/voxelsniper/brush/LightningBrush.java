package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.util.VoxelMessage;

/**
 * @author Gavjenks
 */
public class LightningBrush extends Brush {

    /**
     *
     */
    public LightningBrush() {
        this.setName("Lightning");
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.brushMessage(Messages.LIGHTNING_BRUSH_MESSAGE);
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.getWorld().strikeLightning(this.getTargetBlock().getLocation());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.getWorld().strikeLightning(this.getTargetBlock().getLocation());
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.lightning";
    }
}
