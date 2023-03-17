package com.github.kevindagame.brush.shell;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.BlockWrapper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.brushOperation.BlockOperation;
import com.github.kevindagame.voxelsniper.block.IBlock;

import java.util.ArrayList;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#shell-set-brush">...</a>
 *
 * @author Piotr
 */
public class ShellSetBrush extends ShellBrushBase {

    private static final int MAX_SIZE = 5000000;
    private final ArrayList<BlockWrapper> operations = new ArrayList<>();
    private IBlock block = null;


    private boolean set(final IBlock bl, final SnipeData v) {
        operations.clear();
        if (this.block == null) {
            this.block = bl;
            return true;
        } else {
            if (!this.block.getWorld().getName().equals(bl.getWorld().getName())) {
                v.sendMessage(Messages.SELECTED_POINTS_DIFFERENT_WORLD);
                this.block = null;
                return true;
            }

            final int lowX = Math.min(this.block.getX(), bl.getX());
            final int lowY = Math.min(this.block.getY(), bl.getY());
            final int lowZ = Math.min(this.block.getZ(), bl.getZ());
            final int highX = Math.max(this.block.getX(), bl.getX());
            final int highY = Math.max(this.block.getY(), bl.getY());
            final int highZ = Math.max(this.block.getZ(), bl.getZ());

            int size = Math.abs(highX - lowX) * Math.abs(highZ - lowZ) * Math.abs(highY - lowY);
            if (size > MAX_SIZE) {
                v.sendMessage(Messages.SELECTION_SIZE_LIMIT);
            } else {
                final ArrayList<IBlock> blocks = new ArrayList<>((size / 2));
                for (int y = lowY; y <= highY; y++) {
                    for (int x = lowX; x <= highX; x++) {
                        for (int z = lowZ; z <= highZ; z++) {
                            if (this.getWorld().getBlock(x, y, z).getMaterial() == v.getReplaceMaterial()) {
                            } else if (this.getWorld().getBlock(x + 1, y, z).getMaterial() == v.getReplaceMaterial()) {
                            } else if (this.getWorld().getBlock(x - 1, y, z).getMaterial() == v.getReplaceMaterial()) {
                            } else if (this.getWorld().getBlock(x, y, z + 1).getMaterial() == v.getReplaceMaterial()) {
                            } else if (this.getWorld().getBlock(x, y, z - 1).getMaterial() == v.getReplaceMaterial()) {
                            } else if (this.getWorld().getBlock(x, y + 1, z).getMaterial() == v.getReplaceMaterial()) {
                            } else if (this.getWorld().getBlock(x, y - 1, z).getMaterial() == v.getReplaceMaterial()) {
                            } else {
                                blocks.add(this.getWorld().getBlock(x, y, z));
                            }
                        }
                    }
                }
                for (final IBlock currentBlock : blocks) {
                    if (currentBlock.getMaterial() != v.getVoxelMaterial()) {
                        addOperation(new BlockOperation(currentBlock.getLocation(), currentBlock.getBlockData(), v.getVoxelMaterial().createBlockData()));
                    }
                }
                v.sendMessage(Messages.SHELL_BRUSH_COMPLETE);

            }

            this.block = null;
            return false;
        }
    }

    @Override
    protected void shell(SnipeData v) {
        //empty because method is not needed right now
    }

    @Override
    protected final void arrow(final SnipeData v) {
        if (this.set(this.getTargetBlock(), v)) {
            v.sendMessage(Messages.FIRST_POINT_SELECTED);
        }
    }

    @Override
    protected final void powder(final SnipeData v) {
        if (this.set(this.getLastBlock(), v)) {
            v.sendMessage(Messages.FIRST_POINT_SELECTED);
        }
    }

}
