package com.github.kevindagame.brush;

import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.BlockFace;
import com.github.kevindagame.voxelsniper.block.IBlock;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#voxel-disc-face-brush">...</a>
 *
 * @author Voxel
 */
public class VoxelDiscFaceBrush extends PerformerBrush {

    /**
     *
     */
    public VoxelDiscFaceBrush() {
        this.setName("Voxel Disc Face");
    }

    private void disc(final SnipeData v, IBlock targetBlock) {
        for (int x = v.getBrushSize(); x >= -v.getBrushSize(); x--) {
            for (int y = v.getBrushSize(); y >= -v.getBrushSize(); y--) {
                this.currentPerformer.perform(this.clampY(targetBlock.getX() + x, targetBlock.getY(), targetBlock.getZ() + y));
            }
        }

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    private void discNS(final SnipeData v, IBlock targetBlock) {
        for (int x = v.getBrushSize(); x >= -v.getBrushSize(); x--) {
            for (int y = v.getBrushSize(); y >= -v.getBrushSize(); y--) {
                this.currentPerformer.perform(this.clampY(targetBlock.getX() + x, targetBlock.getY() + y, targetBlock.getZ()));
            }
        }

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    private void discEW(final SnipeData v, IBlock targetBlock) {
        for (int x = v.getBrushSize(); x >= -v.getBrushSize(); x--) {
            for (int y = v.getBrushSize(); y >= -v.getBrushSize(); y--) {
                this.currentPerformer.perform(this.clampY(targetBlock.getX(), targetBlock.getY() + x, targetBlock.getZ() + y));
            }
        }

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    private void pre(final SnipeData v, final BlockFace bf, IBlock targetBlock) {
        if (bf == null) {
            return;
        }
        switch (bf) {
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
                this.disc(v, targetBlock);
                break;

            default:
                break;
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.pre(v, this.getTargetBlock().getFace(this.getLastBlock()), this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.pre(v, this.getTargetBlock().getFace(this.getLastBlock()), this.getLastBlock());
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.voxeldiscface";
    }
}
