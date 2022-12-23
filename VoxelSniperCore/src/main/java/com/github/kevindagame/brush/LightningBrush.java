package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.BrushOperation.CustomOperation;
import com.github.kevindagame.util.BrushOperation.CustomOperationContext;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Gavjenks
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#lightning-brush">...</a>
 */
public class LightningBrush extends CustomBrush {

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
        getOperations().add(new CustomOperation(this.getTargetBlock().getLocation(), this, v, CustomOperationContext.TARGETLOCATION));
    }

    @Override
    protected final void powder(final SnipeData v) {
        getOperations().add(new CustomOperation(this.getTargetBlock().getLocation(), this, v, CustomOperationContext.TARGETLOCATION));
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.lightning";
    }

    @Override
    public boolean perform(@NotNull List<CustomOperation> operations, @NotNull SnipeData snipeData, @NotNull Undo undo) {
        operations.forEach(operation -> operation.getLocation().getWorld().strikeLightning(operation.getLocation()));
        return true;
    }
}
