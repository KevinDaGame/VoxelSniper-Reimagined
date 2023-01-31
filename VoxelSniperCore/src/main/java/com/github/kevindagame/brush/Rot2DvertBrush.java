package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Actions;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.RotationAxis;
import com.github.kevindagame.util.VoxelMessage;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

// TODO: Dissect this

/**
 * @author Gavjenks, hack job from the other 2d rotation brush blockPositionY piotr
 */
// The X Y and Z variable names in this file do NOT MAKE ANY SENSE. Do not attempt to actually figure out what on earth is going on here. Just go to the
// original 2d horizontal brush if you wish to make anything similar to this, and start there. I didn't bother renaming everything.
public class Rot2DvertBrush extends AbstractBrush {
    private double angle = 90;

    /**
     *
     */
    public Rot2DvertBrush() {
        this.setName("2D Rotation");
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.addOperations(Actions.rotate(getTargetBlock(), v.getBrushSize(), this.angle, RotationAxis.X));

    }

    @Override
    protected final void powder(final SnipeData v) {
        this.addOperations(Actions.rotate(getTargetBlock(), v.getBrushSize(), -1 * this.angle, RotationAxis.X));
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
    }

    @Override
    public final void parseParameters(@NotNull final String triggerHandle, final String[] params, @NotNull final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.ROTATE_2D_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        try {
            double degrees = Double.parseDouble(params[0]);
            this.angle = degrees;
            v.sendMessage(Messages.ANGLE_SET.replace("%se%", String.valueOf(degrees)));
        } catch (NumberFormatException temp) {
            v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        }
    }

    @NotNull
    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("[number]"));
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.rot2dvert";
    }
}
