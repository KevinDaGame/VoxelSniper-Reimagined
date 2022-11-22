package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.BlockHelper;
import com.github.kevindagame.util.VoxelMessage;

/**
 * Painting scrolling Brush. http://www.voxelwiki.com/minecraft/Voxelsniper#The_Painting_Picker_Brush
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
        switch(this.snipeAction) {
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
