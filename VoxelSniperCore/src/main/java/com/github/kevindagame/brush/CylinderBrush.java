package com.github.kevindagame.brush;

import com.github.kevindagame.util.Shapes;
import com.google.common.collect.Lists;
import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Kavutop
 */
public class CylinderBrush extends PerformerBrush {

    private static final double SMOOTH_CIRCLE_VALUE = 0.5;
    private static final double VOXEL_CIRCLE_VALUE = 0.0;

    private boolean smoothCircle = false;

    /**
     *
     */
    public CylinderBrush() {
        this.setName("Cylinder");
    }

    private void cylinder(final SnipeData v) {
        this.positions = Shapes.cylinder(this.getTargetBlock().getLocation(), v.getBrushSize(), v.getVoxelHeight(), v.getcCen(), this.smoothCircle);
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.cylinder(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.cylinder(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        vm.height();
        vm.center();
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.CYLINDER_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        if (params[0].startsWith("smooth")) {
            this.smoothCircle = !this.smoothCircle;
            v.sendMessage(Messages.BRUSH_SMOOTH_CIRCLE.replace("%smoothCircle%", String.valueOf(this.smoothCircle)));
            return;
        }

        if (params[0].startsWith("height")) {
            try {
                v.setVoxelHeight(Integer.parseInt(params[1]));
                v.getVoxelMessage().height();
                return;
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException temp) {
                temp.printStackTrace();
            }
        }

        if (params[0].startsWith("shift")) {
            try {
                v.setcCen(Integer.parseInt(params[1]));
                v.sendMessage(Messages.CYLINDER_SHIFT_Y.replace("%count%", String.valueOf(v.getcCen())));
                return;
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException temp) {
                temp.printStackTrace();
            }
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        sendPerformerMessage(triggerHandle, v);
    }

    @Override
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.addAll(Lists.newArrayList("shift", "height", "smooth"));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();


        argumentValues.put("shift", Lists.newArrayList("[number]"));

        argumentValues.put("height", Lists.newArrayList("[number]"));

        argumentValues.putAll(super.registerArgumentValues());
        return argumentValues;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.cylinder";
    }
}
