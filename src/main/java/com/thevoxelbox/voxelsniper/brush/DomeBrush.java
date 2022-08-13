package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.IVector;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.VectorFactory;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.util.NumberConversions;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Dome_Brush
 *
 * @author Gavjenks
 * @author MikeMatrix
 */
public class DomeBrush extends Brush {

    /**
     *
     */
    public DomeBrush() {
        this.setName("Dome");
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        vm.voxel();
        vm.height();
    }

    /**
     * @param v
     * @param targetBlock
     */
    @SuppressWarnings("deprecation")
    private void generateDome(final SnipeData v, final IBlock targetBlock) {

        if (v.getVoxelHeight() == 0) {
            v.sendMessage(Messages.VOXEL_HEIGHT_MUST_NOT_BE_0);
            return;
        }

        final int absoluteHeight = Math.abs(v.getVoxelHeight());
        final boolean negative = v.getVoxelHeight() < 0;

        final Set<IVector> changeablePositions = new HashSet<>();

        final Undo undo = new Undo();

        final int brushSizeTimesVoxelHeight = v.getBrushSize() * absoluteHeight;
        final double stepScale = ((v.getBrushSize() * v.getBrushSize()) + brushSizeTimesVoxelHeight + brushSizeTimesVoxelHeight) / 5;

        final double stepSize = 1 / stepScale;

        for (double u = 0; u <= Math.PI / 2; u += stepSize) {
            final double y = absoluteHeight * Math.sin(u);
            for (double stepV = -Math.PI; stepV <= -(Math.PI / 2); stepV += stepSize) {
                final double x = v.getBrushSize() * Math.cos(u) * Math.cos(stepV);
                final double z = v.getBrushSize() * Math.cos(u) * Math.sin(stepV);

                final double targetBlockX = targetBlock.getX() + 0.5;
                final double targetBlockZ = targetBlock.getZ() + 0.5;
                final int targetY = NumberConversions.floor(targetBlock.getY() + (negative ? -y : y));
                final int currentBlockXAdd = NumberConversions.floor(targetBlockX + x);
                final int currentBlockZAdd = NumberConversions.floor(targetBlockZ + z);
                final int currentBlockXSubtract = NumberConversions.floor(targetBlockX - x);
                final int currentBlockZSubtract = NumberConversions.floor(targetBlockZ - z);
                changeablePositions.add(VectorFactory.getVector(currentBlockXAdd, targetY, currentBlockZAdd));
                changeablePositions.add(VectorFactory.getVector(currentBlockXSubtract, targetY, currentBlockZAdd));
                changeablePositions.add(VectorFactory.getVector(currentBlockXAdd, targetY, currentBlockZSubtract));
                changeablePositions.add(VectorFactory.getVector(currentBlockXSubtract, targetY, currentBlockZSubtract));

            }
        }

        for (final IVector vector : changeablePositions) {
            final IBlock currentTargetBlock = vector.getLocation(this.getTargetBlock().getWorld()).getBlock();
            // TODO: Check whether BlockData omission affects this or not.
            // if (currentTargetBlock.getMaterial() != v.getVoxelMaterial() || currentTargetBlock.getBlockData().matches(v.getVoxelSubstance())) {
            if (currentTargetBlock.getMaterial() != v.getVoxelMaterial()) {
                undo.put(currentTargetBlock);
                currentTargetBlock.setBlockData(v.getVoxelMaterial().createBlockData(), true);
            }
        }

        v.owner().storeUndo(undo);
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.generateDome(v, this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.generateDome(v, this.getLastBlock());
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.dome";
    }
}
