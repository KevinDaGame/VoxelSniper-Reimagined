package com.github.kevindagame.brush;

import com.github.kevindagame.util.Actions;
import com.github.kevindagame.util.Shapes;
import com.google.common.collect.Lists;
import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MikeMatrix
 */
public class CheckerVoxelDiscBrush extends PerformerBrush {

    private boolean useWorldCoordinates = true;

    /**
     * Default constructor.
     */
    public CheckerVoxelDiscBrush() {
        this.setName("Checker Voxel Disc");
    }

    /**
     * @param v
     */
    private void applyBrush(final SnipeData v) {
        this.positions = Actions.checker(Shapes.voxelDisc(this.getTargetBlock().getLocation(), v.getBrushSize()), this.useWorldCoordinates);
        v.owner().storeUndo(this.currentPerformer.getAndClearUndo());
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.applyBrush(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.applyBrush(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {

        if (params[0].equals("info")) {
            v.sendMessage(Messages.CHECKER_DISC_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        if (params[0].startsWith("worldcoords")) {
            this.useWorldCoordinates = !this.useWorldCoordinates;
            v.sendMessage(Messages.CHECKER_USING_WORLD_CORDS.replace("%useWorldCoordinates%", String.valueOf(this.useWorldCoordinates)));
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        sendPerformerMessage(triggerHandle, v);
    }

    @Override
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.addAll(Lists.newArrayList("worldcoords"));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.checkervoxeldisc";
    }
}
