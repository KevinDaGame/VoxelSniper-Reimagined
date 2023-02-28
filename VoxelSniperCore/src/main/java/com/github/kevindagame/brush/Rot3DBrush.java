package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Actions;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.util.VoxelMessage;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#3d-rotation-brush">...</a>
 */
public class Rot3DBrush extends AbstractBrush {
    private double yaw;
    private double pitch;
    private double roll;

    /**
     *
     */
    public Rot3DBrush() {
        this.setName("3D Rotation");
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.brushMessage(Messages.ROT3D_BRUSH_MESSAGE);
    }

    // after all rotations, compare snapshot to new state of world, and store changed blocks to undo?
    // --> agreed. Do what erode does and store one snapshot with Block pointers and int id of what the block started with, afterwards simply go thru that
    // matrix and compare Block.getId with 'id' if different undo.add( new BlockWrapper ( Block, oldId ) )
    @Override
    public final void parseParameters(@NotNull final String triggerHandle, final String[] params, @NotNull final SnipeData v) {
        // which way is clockwise is less obvious for roll and pitch... should probably fix that / make it clear
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.ROTATION_3D_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }
        try {
            if (params[0].equalsIgnoreCase("pitch")) {
                this.pitch = Double.parseDouble(params[1]);
                v.sendMessage(Messages.ANGLE_AROUND_AXIS_SET.replace("%axis%", "Z").replace("%angle%", String.valueOf(this.pitch)));
                if (this.pitch < 0 || this.pitch > 359) {
                    v.sendMessage(Messages.INVALID_BRUSH_PARAM_ANGLE);
                }
                return;
            }

            if (params[0].equalsIgnoreCase("roll")) {
                this.roll = Double.parseDouble(params[1]);
                v.sendMessage(Messages.ANGLE_AROUND_AXIS_SET.replace("%axis%", "X").replace("%angle%", String.valueOf(this.roll)));
                if (this.roll < 0 || this.roll > 359) {
                    v.sendMessage(Messages.INVALID_BRUSH_PARAM_ANGLE);
                }
                return;
            }

            if (params[0].equalsIgnoreCase("yaw")) {
                this.yaw = Double.parseDouble(params[1]);
                v.sendMessage(Messages.ANGLE_AROUND_AXIS_SET.replace("%axis%", "Y").replace("%angle%", String.valueOf(this.yaw)));
                if (this.yaw < 0 || this.yaw > 359) {
                    v.sendMessage(Messages.INVALID_BRUSH_PARAM_ANGLE);
                }
                return;
            }
        } catch (NumberFormatException ignored) { }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @NotNull
    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("pitch", "roll", "yaw"));
    }

    @NotNull
    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        // Number variables
        HashMap<String, List<String>> argumentValues = new HashMap<>();

        argumentValues.put("pitch", Lists.newArrayList("[1-359]"));
        argumentValues.put("roll", Lists.newArrayList("[1-359]"));
        argumentValues.put("yaw", Lists.newArrayList("[1-359]"));

        return argumentValues;
    }

    @Override
    protected final void arrow(final SnipeData v) {
        int bSize = v.getBrushSize();

        var ball = Shapes.ball(getTargetBlock().getLocation(), bSize, true);
        this.addOperations(Actions.rotate3D(getTargetBlock(), ball, this.roll, this.yaw, this.pitch));

    }

    @Override
    protected final void powder(final SnipeData v) {
        this.arrow(v);
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.rot3d";
    }
}
