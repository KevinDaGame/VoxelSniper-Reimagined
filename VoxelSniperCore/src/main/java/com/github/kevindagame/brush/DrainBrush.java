package com.github.kevindagame.brush;

import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.google.common.collect.Lists;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_Drain_Brush
 *
 * @author Gavjenks
 * @author psanker
 */
public class DrainBrush extends AbstractBrush {

    private final boolean smooth = true;
    private boolean disc = false;

    /**
     *
     */
    public DrainBrush() {
        this.setName("Drain");
    }

    private void drain(final SnipeData v) {
        positions = this.disc ? Shapes.disc(getTargetBlock().getLocation(), v.getBrushSize(), smooth) : Shapes.ball(getTargetBlock().getLocation(), v.getBrushSize(), smooth);
    }

    @Override
    protected boolean actPerform(SnipeData v) {
        Undo undo = new Undo();
        for (var pos : positions) {
            var block = pos.getBlock();
            if (block.getMaterial().isFluid()) {
                undo.put(block);
                block.setMaterial(VoxelMaterial.AIR);
            }
        }
        return true;
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.drain(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.drain(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();

        vm.custom(Messages.DRAIN_TRUE_CIRCLE_MODE.replace("%state%", this.smooth ? "ON" : "OFF"));
        vm.custom(Messages.DISC_DRAIN_MODE.replace("%state%", (this.disc) ? "ON" : "OFF"));
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.DRAIN_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }
        if (params[0].startsWith("shape")) {
            this.disc = !this.disc;
            v.sendMessage(Messages.DRAIN_BRUSH_SHAPE.replace("%shape%", this.disc ? "Disc" : "Ball"));
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("shape"));
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.drain";
    }
}
