package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.operation.CustomOperation;
import com.github.kevindagame.util.brushOperation.operation.CustomOperationContext;

import java.util.List;

import org.jetbrains.annotations.NotNull;

/**
 * @author Gavjenks
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#lightning-brush">...</a>
 */
public class LightningBrush extends CustomBrush {


    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.brushMessage(Messages.LIGHTNING_BRUSH_MESSAGE);
    }

    @Override
    protected final void arrow(final SnipeData v) {
        addOperation(new CustomOperation(this.getTargetBlock().getLocation(), this, v, CustomOperationContext.TARGETLOCATION));
    }

    @Override
    protected final void powder(final SnipeData v) {
        addOperation(new CustomOperation(this.getTargetBlock().getLocation(), this, v, CustomOperationContext.TARGETLOCATION));
    }

    @Override
    public boolean perform(@NotNull List<CustomOperation> operations, @NotNull SnipeData snipeData, @NotNull Undo undo) {
        operations.forEach(operation -> operation.getLocation().getWorld().strikeLightning(operation.getLocation()));
        return true;
    }
}
