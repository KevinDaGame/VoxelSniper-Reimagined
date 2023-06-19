package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.BlockWrapper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.BlockOperation;
import com.github.kevindagame.voxelsniper.block.IBlock;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

/**
 *
 */
public class StampBrush extends AbstractBrush {

    protected HashSet<BlockWrapper> clone = new HashSet<>();
    protected HashSet<BlockWrapper> toStamp = new HashSet<>();
    protected boolean sorted = false;
    protected StampType stamp = StampType.DEFAULT;


    /**
     *
     */
    public final void reSort() {
        this.sorted = false;
    }

    /**
     * Sets block at location of cb to the block in cb.
     *
     * @param cb
     */
    protected final void setBlock(final BlockWrapper cb) {
        final IBlock block = getWorld().getBlock(this.getTargetBlock().getX() + cb.getX(), this.getTargetBlock().getY() + cb.getY(), this.getTargetBlock().getZ() + cb.getZ());
        addOperation(new BlockOperation(block.getLocation(), block.getBlockData(), cb.getBlockData()));
    }

    /**
     * Sets the block at location of cb to the block in cb, only if the block at the location of cb is air.
     *
     * @param cb
     */
    protected final void setBlockFill(final BlockWrapper cb) {
        final IBlock block = getWorld().getBlock(this.getTargetBlock().getX() + cb.getX(), this.getTargetBlock().getY() + cb.getY(), this.getTargetBlock().getZ() + cb.getZ());
        if (block.getMaterial().isAir()) {
            addOperation(new BlockOperation(block.getLocation(), block.getBlockData(), cb.getBlockData()));
        }
    }

    /**
     * Set the stamp type
     */
    protected final void setStamp(final StampType type) {
        this.stamp = type;
    }

    /**
     * stamps the terrain. This will replace all old blocks in the stamp area
     *
     * @param v
     */
    protected final void stamp(final SnipeData v) {
        if (!this.sorted) {
            this.toStamp.clear();
            this.toStamp.addAll(this.clone);
            this.sorted = true;
        }
        for (final BlockWrapper block : this.toStamp) {
            this.setBlock(block);
        }
    }

    /**
     * Stamp the terrain. This will only replace a block, if the existing block is air.
     */
    protected final void stampFill(final SnipeData v) {
        if (!this.sorted) {
            this.toStamp.clear();
            this.toStamp.addAll(this.clone);
            this.sorted = true;
        }
        for (final BlockWrapper block : this.toStamp) {
            this.setBlockFill(block);
        }
    }

    /**
     * Stamps the terrain, if the replace block is air, it won't remove the old block
     */
    protected final void stampNoAir(final SnipeData v) {
        if (!this.sorted) {
            this.toStamp.clear();
            for (final BlockWrapper block : this.clone) {
                if (!block.getMaterial().isAir()) {
                    this.toStamp.add(block);
                }
            }
            this.sorted = true;
        }
        stamp(v);
    }

    @Override
    protected final void arrow(final SnipeData v) {
        switch (this.stamp) {
            case DEFAULT -> this.stamp(v);
            case NO_AIR -> this.stampNoAir(v);
            case FILL -> this.stampFill(v);
            default -> v.sendMessage(Messages.STAMP_ERROR);
        }
    }

    @Override
    protected void powder(final SnipeData v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void info(@NotNull final VoxelMessage vm) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @author Monofraps
     */
    protected enum StampType {
        NO_AIR, FILL, DEFAULT
    }
}
