package com.github.kevindagame.brush;

import com.google.common.collect.Lists;
import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Voxel
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#fill-down-brush">...</a>
 */
public class FillDownBrush extends PerformerBrush {

    private static final double SMOOTH_CIRCLE_VALUE = 0.5;
    private static final double VOXEL_CIRCLE_VALUE = 0.0;

    private boolean smoothCircle = false;

    private boolean fillLiquid = true;
    private boolean fromExisting = false;

    /**
     *
     */
    public FillDownBrush() {
        this.setName("Fill Down");
    }

    private void fillDown(final SnipeData v, final IBlock b) {
        final int brushSize = v.getBrushSize();
        final double brushSizeSquared = Math.pow(brushSize + (smoothCircle ? SMOOTH_CIRCLE_VALUE : VOXEL_CIRCLE_VALUE), 2);
        final IBlock targetBlock = this.getTargetBlock();
        for (int x = -brushSize; x <= brushSize; x++) {
            final double currentXSquared = Math.pow(x, 2);

            for (int z = -brushSize; z <= brushSize; z++) {
                if (currentXSquared + Math.pow(z, 2) <= brushSizeSquared) {
                    int y = 0;
                    boolean found = false;
                    if (this.fromExisting) {
                        for (y = -v.getVoxelHeight(); y < v.getVoxelHeight(); y++) {
                            final IBlock currentBlock = this.getWorld().getBlock(
                                    targetBlock.getX() + x,
                                    targetBlock.getY() + y,
                                    targetBlock.getZ() + z);
                            if (!currentBlock.isEmpty()) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            continue;
                        }
                        y--;
                    }
                    for (; y >= -targetBlock.getY(); --y) {
                        final IBlock currentBlock = this.getWorld().getBlock(
                                targetBlock.getX() + x,
                                targetBlock.getY() + y,
                                targetBlock.getZ() + z);
                        if (currentBlock.isEmpty() || (fillLiquid && currentBlock.isLiquid())) {
                            this.currentPerformer.perform(currentBlock);
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.fillDown(v, this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.fillDown(v, this.getLastBlock());
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.FILL_DOWN_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        if (params[0].equalsIgnoreCase("liquid")) {
            this.fillLiquid = !this.fillLiquid;
            String mode = (this.fillLiquid) ? "liquid and air" : "air only";
            v.sendMessage(Messages.FILL_DOWN_MODE.replace("%mode%", mode));
            return;
        }

        if (params[0].equalsIgnoreCase("existing")) {
            this.fromExisting = !this.fromExisting;
            String mode = (this.fromExisting) ? "existing" : "all";
            v.sendMessage(Messages.FILL_DOWN_FROM.replace("%mode%", mode));
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
        arguments.addAll(Lists.newArrayList("smooth", "liquid", "existing"));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.filldown";
    }
}
