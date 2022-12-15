package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;

import java.util.HashSet;
import java.util.Set;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#dome-brush">...</a>
 *
 * @author Gavjenks
 * @author MikeMatrix
 */
public class DomeBrush extends AbstractBrush {

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

        final Set<VoxelVector> changeablePositions = new HashSet<>();

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
                final int targetY = ((int) Math.floor(targetBlock.getY() + (negative ? -y : y)));
                final int currentBlockXAdd = ((int) Math.floor(targetBlockX + x));
                final int currentBlockZAdd = ((int) Math.floor(targetBlockZ + z));
                final int currentBlockXSubtract = ((int) Math.floor(targetBlockX - x));
                final int currentBlockZSubtract = ((int) Math.floor(targetBlockZ - z));
                changeablePositions.add(new VoxelVector(currentBlockXAdd, targetY, currentBlockZAdd));
                changeablePositions.add(new VoxelVector(currentBlockXSubtract, targetY, currentBlockZAdd));
                changeablePositions.add(new VoxelVector(currentBlockXAdd, targetY, currentBlockZSubtract));
                changeablePositions.add(new VoxelVector(currentBlockXSubtract, targetY, currentBlockZSubtract));

            }
        }

        for (final VoxelVector vector : changeablePositions) {
            final IBlock currentTargetBlock = vector.getLocation(this.getTargetBlock().getWorld()).getBlock();
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
