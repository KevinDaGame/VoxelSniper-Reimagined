package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.BlockHelper;
import com.github.kevindagame.util.VoxelMessage;

/**
 * Painting scrolling Brush. <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#painting-brush">...</a>
 *
 * @author Voxel
 */
public class PaintingBrush extends AbstractBrush {

    /**
     *
     */
    public PaintingBrush() {
        this.setName("Painting");
    }

    /**
     * Scroll painting forward.
     *
     * @param v Sniper caller
     */
    @Override
    protected final void arrow(final SnipeData v) {
        BlockHelper.painting(v.owner().getPlayer(), true, false, 0);
    }

    /**
     * Scroll painting backwards.
     *
     * @param v Sniper caller
     */
    @Override
    protected final void powder(final SnipeData v) {
        BlockHelper.painting(v.owner().getPlayer(), true, true, 0);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.painting";
    }
}
