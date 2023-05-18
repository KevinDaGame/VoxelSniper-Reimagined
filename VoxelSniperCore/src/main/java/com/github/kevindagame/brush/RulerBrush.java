package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.CustomOperation;
import com.github.kevindagame.util.brushOperation.CustomOperationContext;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#ruler-brush">...</a>
 *
 * @author Gavjenks
 */
public class RulerBrush extends CustomBrush {

    private final int xOff = 0;
    private final int yOff = 0;
    private final int zOff = 0;
    private boolean first = true;
    private VoxelVector coords = new VoxelVector();


    @Override
    protected final void arrow(final SnipeData v) {
        addOperation(new CustomOperation(this.getTargetBlock().getLocation(), this, v, CustomOperationContext.TARGETLOCATION));
    }


    @Override
    protected final void powder(final SnipeData v) {
        if (this.coords == null || this.coords.lengthSquared() == 0) {
            v.sendMessage(Messages.FIRST_COORDINATE_NOT_SET);
            return;
        }
        addOperation(new CustomOperation(this.getTargetBlock().getLocation(), this, v, CustomOperationContext.TARGETLOCATION));
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.voxel();
    }

    @Override
    public final void parseParameters(@NotNull final String triggerHandle, final String[] params, @NotNull final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.RULER_BRUSH_USAGE);
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public boolean perform(List<CustomOperation> operations, @NotNull SnipeData snipeData, @NotNull Undo undo) {
        if (operations.size() != 1) {
            return false;
        }
        switch (Objects.requireNonNull(getSnipeAction())) {
            case ARROW -> {
                if (operations.size() == 1) {
                    this.coords = operations.stream().findFirst().get().getLocation().toVector();
                    snipeData.sendMessage(Messages.FIRST_POINT_SELECTED);
                    this.first = !this.first;
                }
            }
            case GUNPOWDER -> {
                var target = operations.stream().findFirst().get().getLocation();
                double x = target.getX() - this.coords.getX();
                double y = target.getY() - this.coords.getY();
                double z = target.getZ() - this.coords.getZ();
                final double distance = (double) (Math.round(target.toVector().subtract(this.coords).length() * 100) / 100);
                final double blockDistance = (double) (Math.round((Math.abs(Math.max(Math.max(Math.abs(x), Math.abs(y)), Math.abs(z))) + 1) * 100) / 100);

                snipeData.sendMessage(Messages.RULER_BRUSH_POWDER.replace("%x%", String.valueOf(x)).replace("%y%", String.valueOf(y)).replace("%z%", String.valueOf(z)).replace("%distance%", String.valueOf(distance)).replace("%blockDistance%", String.valueOf(blockDistance)));
            }
        }
        return true;
    }
}
