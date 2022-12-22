package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.BlockHelper;
import com.github.kevindagame.util.VoxelMessage;

import java.util.Objects;

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

    @Override
    protected boolean actPerform(SnipeData v) {
        switch(Objects.requireNonNull(getSnipeAction())) {
            case ARROW:
                BlockHelper.painting(v.owner().getPlayer(), true, false, 0);
                return true;
            case GUNPOWDER:
                BlockHelper.painting(v.owner().getPlayer(), true, true, 0);
                return true;
            default:
                return false;
        }
    }

    /**
     * Scroll painting forward.
     *
     * @param v Sniper caller
     */
    @Override
    protected final void arrow(final SnipeData v) {
        positions.add(getTargetBlock().getLocation());
    }

    /**
     * Scroll painting backwards.
     *
     * @param v Sniper caller
     */
    @Override
    protected final void powder(final SnipeData v) {
        positions.add(getTargetBlock().getLocation());
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
