package com.github.kevindagame.brush;

import com.google.common.collect.Lists;
import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.BlockFace;
import com.github.kevindagame.voxelsniper.block.IBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#disc-face-brush">...</a>
 *
 * @author Voxel
 */
public class DiscFaceBrush extends PerformerBrush {

    private static final double SMOOTH_CIRCLE_VALUE = 0.5;
    private static final double VOXEL_CIRCLE_VALUE = 0.0;

    private boolean smoothCircle = false;

    /**
     *
     */
    public DiscFaceBrush() {
        this.setName("Disc Face");
    }

    private void discUD(final SnipeData v, IBlock targetBlock) {
        final int brushSize = v.getBrushSize();
        final double brushSizeSquared = Math.pow(brushSize + (smoothCircle ? SMOOTH_CIRCLE_VALUE : VOXEL_CIRCLE_VALUE), 2);

        for (int x = brushSize; x >= 0; x--) {
            final double xSquared = Math.pow(x, 2);

            for (int z = brushSize; z >= 0; z--) {
                if ((xSquared + Math.pow(z, 2)) <= brushSizeSquared) {
                    currentPerformer.perform(targetBlock.getRelative(x, 0, z));
                    currentPerformer.perform(targetBlock.getRelative(x, 0, -z));
                    currentPerformer.perform(targetBlock.getRelative(-x, 0, z));
                    currentPerformer.perform(targetBlock.getRelative(-x, 0, -z));
                }
            }
        }

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    private void discNS(final SnipeData v, IBlock targetBlock) {
        final int brushSize = v.getBrushSize();
        final double brushSizeSquared = Math.pow(brushSize + (smoothCircle ? SMOOTH_CIRCLE_VALUE : VOXEL_CIRCLE_VALUE), 2);

        for (int x = brushSize; x >= 0; x--) {
            final double xSquared = Math.pow(x, 2);
            for (int y = brushSize; y >= 0; y--) {
                if ((xSquared + Math.pow(y, 2)) <= brushSizeSquared) {
                    currentPerformer.perform(targetBlock.getRelative(x, y, 0));
                    currentPerformer.perform(targetBlock.getRelative(x, -y, 0));
                    currentPerformer.perform(targetBlock.getRelative(-x, y, 0));
                    currentPerformer.perform(targetBlock.getRelative(-x, -y, 0));
                }
            }
        }

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    private void discEW(final SnipeData v, IBlock targetBlock) {
        final int brushSize = v.getBrushSize();
        final double brushSizeSquared = Math.pow(brushSize + (smoothCircle ? SMOOTH_CIRCLE_VALUE : VOXEL_CIRCLE_VALUE), 2);

        for (int x = brushSize; x >= 0; x--) {
            final double xSquared = Math.pow(x, 2);
            for (int y = brushSize; y >= 0; y--) {
                if ((xSquared + Math.pow(y, 2)) <= brushSizeSquared) {
                    currentPerformer.perform(targetBlock.getRelative(0, x, y));
                    currentPerformer.perform(targetBlock.getRelative(0, x, -y));
                    currentPerformer.perform(targetBlock.getRelative(0, -x, y));
                    currentPerformer.perform(targetBlock.getRelative(0, -x, -y));
                }
            }
        }

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    private void pre(final SnipeData v, IBlock targetBlock) {
        BlockFace blockFace = getTargetBlock().getFace(this.getLastBlock());
        if (blockFace == null) {
            return;
        }
        switch (blockFace) {
            case NORTH:
            case SOUTH:
                this.discNS(v, targetBlock);
                break;

            case EAST:
            case WEST:
                this.discEW(v, targetBlock);
                break;

            case UP:
            case DOWN:
                this.discUD(v, targetBlock);
                break;

            default:
                break;
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.pre(v, this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.pre(v, this.getLastBlock());
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.DISC_FACE_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
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
        return "voxelsniper.brush.discface";
    }
}
