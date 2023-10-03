package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.BlockHelper;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.operation.CustomOperation;
import com.github.kevindagame.util.brushOperation.operation.CustomOperationContext;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

/**
 * Painting scrolling Brush. <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#painting-brush">...</a>
 *
 * @author Voxel
 */
public class PaintingBrush extends CustomBrush {


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
    public boolean perform(@NotNull List<CustomOperation> operations, @NotNull SnipeData snipeData, @NotNull Undo undo) {
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
