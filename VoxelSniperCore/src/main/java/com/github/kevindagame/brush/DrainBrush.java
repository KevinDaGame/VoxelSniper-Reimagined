package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.*;
import com.github.kevindagame.util.brushOperation.operation.BlockOperation;
import com.github.kevindagame.voxelsniper.blockdata.waterlogged.IWaterlogged;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#drain-brush">...</a>
 *
 * @author Gavjenks
 * @author psanker
 */
public class DrainBrush extends AbstractBrush {

    private final boolean smooth = true;
    private boolean disc = false;


    private void drain(final SnipeData v) {
        var positions = this.disc ? Shapes.disc(getTargetBlock().getLocation(), RotationAxis.Y, v.getBrushSize(), smooth) : Shapes.ball(getTargetBlock().getLocation(), v.getBrushSize(), smooth);
        for (var pos : positions) {
            var block = pos.getBlock();
            var blockData = block.getBlockData();
            if (blockData.getMaterial().isFluid()) {
                addOperation(new BlockOperation(pos, blockData, VoxelMaterial.AIR().createBlockData()));
            } else if (blockData instanceof IWaterlogged waterlogged && waterlogged.isWaterlogged()) {
                var newData = (IWaterlogged) waterlogged.getCopy();
                newData.setWaterlogged(false);
                addOperation(new BlockOperation(pos, blockData, newData));
            }
        }
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
    public final void parseParameters(@NotNull final String triggerHandle, final String[] params, @NotNull final SnipeData v) {
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

    @NotNull
    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Utils.newArrayList("shape"));
    }
}
