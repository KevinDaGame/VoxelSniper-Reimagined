package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.world.IWorld;

import java.util.HashSet;

/**
 *
 */
public class StampBrush extends AbstractBrush {

    protected HashSet<BlockWrapper> clone = new HashSet<>();
    protected HashSet<BlockWrapper> fall = new HashSet<>();
    protected HashSet<BlockWrapper> drop = new HashSet<>();
    protected HashSet<BlockWrapper> solid = new HashSet<>();
    protected Undo undo;
    protected boolean sorted = false;
    protected StampType stamp = StampType.DEFAULT;
    /**
     *
     */
    public StampBrush() {
        this.setName("Stamp");
    }

    /**
     *
     */
    public final void reSort() {
        this.sorted = false;
    }

    protected final boolean falling(final VoxelMaterial material) {
        return material.hasGravity() || material.isFluid();
    }

    /**
     * @param cb
     */
    protected final void setBlock(final BlockWrapper cb) {
        final IBlock block = this.clampY(this.getTargetBlock().getX() + cb.x, this.getTargetBlock().getY() + cb.y, this.getTargetBlock().getZ() + cb.z);
        this.undo.put(block);
        block.setBlockData(cb.blockData);
    }

    /**
     * @param cb
     */
    protected final void setBlockFill(final BlockWrapper cb) {
        final IBlock block = this.clampY(this.getTargetBlock().getX() + cb.x, this.getTargetBlock().getY() + cb.y, this.getTargetBlock().getZ() + cb.z);
        if (block.getMaterial().isAir()) {
            this.undo.put(block);
            block.setBlockData(cb.blockData);
        }
    }

    /**
     * @param type
     */
    protected final void setStamp(final StampType type) {
        this.stamp = type;
    }

    /**
     * @param v
     */
    protected final void stamp(final SnipeData v) {
        this.undo = new Undo();

        if (this.sorted) {
            for (final BlockWrapper block : this.solid) {
                this.setBlock(block);
            }
            for (final BlockWrapper block : this.drop) {
                this.setBlock(block);
            }
            for (final BlockWrapper block : this.fall) {
                this.setBlock(block);
            }
        } else {
            this.fall.clear();
            this.drop.clear();
            this.solid.clear();
            for (final BlockWrapper block : this.clone) {
                if (block.blockData.getMaterial().fallsOff()) {
                    this.fall.add(block);
                } else if (this.falling(block.blockData.getMaterial())) {
                    this.drop.add(block);
                } else {
                    this.solid.add(block);
                    this.setBlock(block);
                }
            }
            for (final BlockWrapper block : this.drop) {
                this.setBlock(block);
            }
            for (final BlockWrapper block : this.fall) {
                this.setBlock(block);
            }
            this.sorted = true;
        }

        v.owner().storeUndo(this.undo);
    }

    /**
     * @param v
     */
    protected final void stampFill(final SnipeData v) {

        this.undo = new Undo();

        if (this.sorted) {
            for (final BlockWrapper block : this.solid) {
                this.setBlockFill(block);
            }
            for (final BlockWrapper block : this.drop) {
                this.setBlockFill(block);
            }
            for (final BlockWrapper block : this.fall) {
                this.setBlockFill(block);
            }
        } else {
            this.fall.clear();
            this.drop.clear();
            this.solid.clear();
            for (final BlockWrapper block : this.clone) {
                if (block.blockData.getMaterial().fallsOff()) {
                    this.fall.add(block);
                } else if (this.falling(block.blockData.getMaterial())) {
                    this.drop.add(block);
                } else {
                    this.solid.add(block);
                    this.setBlockFill(block);
                }
            }
            for (final BlockWrapper block : this.drop) {
                this.setBlockFill(block);
            }
            for (final BlockWrapper block : this.fall) {
                this.setBlockFill(block);
            }
            this.sorted = true;
        }

        v.owner().storeUndo(this.undo);
    }

    /**
     * @param v
     */
    protected final void stampNoAir(final SnipeData v) {

        this.undo = new Undo();

        if (this.sorted) {
            for (final BlockWrapper block : this.solid) {
                this.setBlock(block);
            }
            for (final BlockWrapper block : this.drop) {
                this.setBlock(block);
            }
            for (final BlockWrapper block : this.fall) {
                this.setBlock(block);
            }
        } else {
            this.fall.clear();
            this.drop.clear();
            this.solid.clear();
            for (final BlockWrapper block : this.clone) {
                if (block.blockData.getMaterial().fallsOff()) {
                    this.fall.add(block);
                } else if (this.falling(block.blockData.getMaterial())) {
                    this.drop.add(block);
                } else if (!block.blockData.getMaterial().isAir()) {
                    this.solid.add(block);
                    this.setBlock(block);
                }
            }
            for (final BlockWrapper block : this.drop) {
                this.setBlock(block);
            }
            for (final BlockWrapper block : this.fall) {
                this.setBlock(block);
            }
            this.sorted = true;
        }

        v.owner().storeUndo(this.undo);
    }

    @Override
    protected final void arrow(final SnipeData v) {
        switch (this.stamp) {
            case DEFAULT:
                this.stamp(v);
                break;

            case NO_AIR:
                this.stampNoAir(v);
                break;

            case FILL:
                this.stampFill(v);
                break;

            default:
                v.sendMessage(Messages.STAMP_ERROR);
                break;
        }
    }

    @Override
    protected void powder(final SnipeData v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void info(final VoxelMessage vm) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.stamp";
    }

    /**
     * @author Monofraps
     */
    protected enum StampType {
        NO_AIR, FILL, DEFAULT
    }

    /**
     * @author Voxel
     */
    protected class BlockWrapper {

        private final IWorld world;
        public IBlockData blockData;
        public int x;
        public int y;
        public int z;

        /**
         * @param b
         * @param blx
         * @param bly
         * @param blz
         */
        public BlockWrapper(final IBlock b, final int blx, final int bly, final int blz, final IWorld world) {
            this.blockData = b.getBlockData();
            this.x = blx;
            this.y = bly;
            this.z = blz;
            this.world = world;
        }
    }
}
