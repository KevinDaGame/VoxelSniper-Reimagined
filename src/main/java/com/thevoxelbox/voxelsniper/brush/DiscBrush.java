package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.brush.perform.PerformerBrush;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.util.Vector;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_Disc_Brush
 *
 * @author Voxel
 */
public class DiscBrush extends PerformerBrush {

    private static final double SMOOTH_CIRCLE_VALUE = 0.5;
    private static final double VOXEL_CIRCLE_VALUE = 0.0;

    private boolean smoothCircle = false;

    /**
     * Default Constructor.
     */
    public DiscBrush() {
        this.setName("Disc");
    }

    /**
     * Disc executor.
     *
     * @param v
     */
    private void disc(final SnipeData v, final Block targetBlock) {
        final double radiusSquared = (v.getBrushSize() + (smoothCircle ? SMOOTH_CIRCLE_VALUE : VOXEL_CIRCLE_VALUE)) * (v.getBrushSize() + (smoothCircle ? SMOOTH_CIRCLE_VALUE : VOXEL_CIRCLE_VALUE));
        final Vector centerPoint = targetBlock.getLocation().toVector();
        final Vector currentPoint = centerPoint.clone();

        for (int x = -v.getBrushSize(); x <= v.getBrushSize(); x++) {
            currentPoint.setX(centerPoint.getX() + x);
            for (int z = -v.getBrushSize(); z <= v.getBrushSize(); z++) {
                currentPoint.setZ(centerPoint.getZ() + z);
                if (centerPoint.distanceSquared(currentPoint) <= radiusSquared) {
                    this.currentPerformer.perform(this.clampY(currentPoint.getBlockX(), currentPoint.getBlockY(), currentPoint.getBlockZ()));
                }
            }
        }
        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.disc(v, this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.disc(v, this.getLastBlock());
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.DISC_BRUSH_USAGE.replace("%triggerHandle%",triggerHandle));
            return;
        }

        if (params[0].startsWith("smooth")) {
            this.smoothCircle = !this.smoothCircle;
            v.sendMessage(Messages.BRUSH_SMOOTH_CIRCLE.replace("%smoothCircle%", String.valueOf(this.smoothCircle)));
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        sendPerformerMessage(triggerHandle, v);
    }

    @Override
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.addAll(Lists.newArrayList("smooth"));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.disc";
    }
}
