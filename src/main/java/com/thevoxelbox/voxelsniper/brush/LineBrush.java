package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.brush.perform.PerformerBrush;
import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.voxelsniper.block.BukkitBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.BukkitVector;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.IVector;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.VectorFactory;
import com.thevoxelbox.voxelsniper.voxelsniper.world.BukkitWorld;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

import org.bukkit.util.BlockIterator;
import org.bukkit.util.NumberConversions;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Line_Brush
 *
 * @author Gavjenks
 * @author giltwist
 * @author MikeMatrix
 */
public class LineBrush extends PerformerBrush {

    private static final IVector HALF_BLOCK_OFFSET = VectorFactory.getVector(0.5, 0.5, 0.5);
    private IVector originCoords = null;
    private IVector targetCoords = VectorFactory.getVector();
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
        final IVector originClone = this.originCoords.clone().add(LineBrush.HALF_BLOCK_OFFSET);
        final IVector targetClone = this.targetCoords.clone().add(LineBrush.HALF_BLOCK_OFFSET);

        final IVector direction = targetClone.clone().subtract(originClone);
        final double length = this.targetCoords.distance(this.originCoords);

        if (length == 0) {
            this.currentPerformer.perform(this.targetCoords.getLocation(this.targetWorld).getBlock());
        } else {
            //TODO replace BlockIterator
            for (final BlockIterator blockIterator = new BlockIterator(((BukkitWorld)this.targetWorld).world(), ((BukkitVector)originClone).vector(), ((BukkitVector)direction).vector(), 0, NumberConversions.round(length)); blockIterator.hasNext();) {
                final IBlock currentBlock = new BukkitBlock(blockIterator.next());
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
