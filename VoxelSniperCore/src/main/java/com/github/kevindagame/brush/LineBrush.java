package com.github.kevindagame.brush;

import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;
import com.github.kevindagame.voxelsniper.world.IWorld;

import java.util.Iterator;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Line_Brush
 *
 * @author Gavjenks
 * @author giltwist
 * @author MikeMatrix
 */
public class LineBrush extends PerformerBrush {

    private static final VoxelVector HALF_BLOCK_OFFSET = new VoxelVector(0.5, 0.5, 0.5);
    private VoxelVector originCoords = null;
    private VoxelVector targetCoords = new VoxelVector();
    private IWorld targetWorld;

    /**
     *
     */
    public LineBrush() {
        this.setName("Line");
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.LINE_BRUSH_INFO);
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        sendPerformerMessage(triggerHandle, v);
    }

    private void linePowder(final SnipeData v) {
        final VoxelVector originClone = this.originCoords.clone().add(LineBrush.HALF_BLOCK_OFFSET);
        final VoxelVector targetClone = this.targetCoords.clone().add(LineBrush.HALF_BLOCK_OFFSET);

        final VoxelVector direction = targetClone.clone().subtract(originClone);
        final double length = this.targetCoords.distance(this.originCoords);

        if (length == 0) {
            this.currentPerformer.perform(this.targetCoords.getLocation(this.targetWorld).getBlock());
        } else {
            for (final Iterator<IBlock> blockIterator = getWorld().getBlockIterator(originClone, direction, 0, (int) Math.round(length)); blockIterator.hasNext(); ) {
                final IBlock currentBlock = blockIterator.next();
                this.currentPerformer.perform(currentBlock);
            }
        }

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.originCoords = this.getTargetBlock().getLocation().toVector();
        this.targetWorld = this.getTargetBlock().getWorld();
        v.sendMessage(Messages.FIRST_POINT_SELECTED);
    }

    @Override
    protected final void powder(final SnipeData v) {
        if (this.originCoords == null || !this.getTargetBlock().getWorld().equals(this.targetWorld)) {
            v.sendMessage(Messages.FIRST_COORDINATE_NOT_SET);
        } else {
            this.targetCoords = this.getTargetBlock().getLocation().toVector();
            this.linePowder(v);
        }
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.line";
    }
}
