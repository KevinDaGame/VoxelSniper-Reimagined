package com.github.kevindagame.brush;

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
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#ring-brush">...</a>
 *
 * @author Voxel
 */
public class RingBrush extends PerformerBrush {

    private static final double SMOOTH_CIRCLE_VALUE = 0.5;
    private static final double VOXEL_CIRCLE_VALUE = 0.0;

    private boolean smoothCircle = false;

    private double innerSize = 0;

    /**
     *
     */
    public RingBrush() {
        this.setName("Ring");
    }

    private void ring(final SnipeData v, IBlock targetBlock) {
        final int brushSize = v.getBrushSize();
        final double outerSquared = Math.pow(brushSize + (smoothCircle ? SMOOTH_CIRCLE_VALUE : VOXEL_CIRCLE_VALUE), 2);
        final double innerSquared = Math.pow(this.innerSize, 2);

        for (int x = brushSize; x >= 0; x--) {
            final double xSquared = Math.pow(x, 2);
            for (int z = brushSize; z >= 0; z--) {
                final double ySquared = Math.pow(z, 2);
                if ((xSquared + ySquared) <= outerSquared && (xSquared + ySquared) >= innerSquared) {
                    currentPerformer.perform(targetBlock.getRelative(x, 0, z));
                    currentPerformer.perform(targetBlock.getRelative(x, 0, -z));
                    currentPerformer.perform(targetBlock.getRelative(-x, 0, z));
                    currentPerformer.perform(targetBlock.getRelative(-x, 0, -z));
                }
            }
        }

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.ring(v, this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.ring(v, this.getLastBlock());
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        vm.custom(Messages.INNER_RADIUS_INFO.replace("%innerSize%", String.valueOf(this.innerSize)));
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.RING_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        if (params[0].startsWith("smooth")) {
            this.smoothCircle = !this.smoothCircle;
            v.sendMessage(Messages.BRUSH_SMOOTH_CIRCLE.replace("%smoothCircle%", String.valueOf(this.smoothCircle)));
            return;
        }

        try {
            if (params[0].startsWith("inner")) {
                this.innerSize = Double.parseDouble(params[1]);
                v.sendMessage(Messages.INNER_RADIUS_SET.replace("%innerSize%", String.valueOf(this.innerSize)));
                return;
            }
        } catch (final NumberFormatException e) {
            e.printStackTrace();
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        sendPerformerMessage(triggerHandle, v);
    }

    @Override
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.addAll(Lists.newArrayList("smooth", "inner"));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        // Number variables
        HashMap<String, List<String>> argumentValues = new HashMap<>();

        argumentValues.put("inner", Lists.newArrayList("[decimal]"));

        argumentValues.putAll(super.registerArgumentValues());
        return argumentValues;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.ring";
    }
}
