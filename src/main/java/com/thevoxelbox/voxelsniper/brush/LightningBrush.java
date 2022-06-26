package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;

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
        vm.brushMessage("Lightning Brush!  Please use in moderation.");
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
