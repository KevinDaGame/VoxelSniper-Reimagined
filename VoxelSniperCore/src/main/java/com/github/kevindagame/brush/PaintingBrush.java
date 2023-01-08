package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.BlockHelper;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.CustomOperation;
import com.github.kevindagame.util.brushOperation.CustomOperationContext;
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Painting scrolling Brush. <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#painting-brush">...</a>
 *
 * @author Voxel
 */
public class PaintingBrush extends CustomBrush {

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
        addOperation(new CustomOperation(getTargetBlock().getLocation(), this, v, CustomOperationContext.TARGETLOCATION));
    }

    /**
     * Scroll painting backwards.
     *
     * @param v Sniper caller
     */
    @Override
    protected final void powder(final SnipeData v) {
        addOperation(new CustomOperation(getTargetBlock().getLocation(), this, v, CustomOperationContext.TARGETLOCATION));
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.painting";
    }

    @Override
    public boolean perform(@NotNull ImmutableList<CustomOperation> operations, @NotNull SnipeData snipeData, @NotNull Undo undo) {
        switch (Objects.requireNonNull(getSnipeAction())) {
            case ARROW:
                BlockHelper.painting(snipeData.owner().getPlayer(), true, false, 0);
                return true;
            case GUNPOWDER:
                BlockHelper.painting(snipeData.owner().getPlayer(), true, true, 0);
                return true;
            default:
                return false;
        }
    }
}
