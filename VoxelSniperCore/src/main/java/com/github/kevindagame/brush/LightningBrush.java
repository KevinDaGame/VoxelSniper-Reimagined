package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;

/**
 * @author Gavjenks
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#lightning-brush">...</a>
 */
public class LightningBrush extends AbstractBrush {

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
