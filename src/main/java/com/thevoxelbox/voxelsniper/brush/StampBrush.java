package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.util.Messages;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

/**
 *
 */
public class StampBrush extends Brush {

    /**
     * @author Voxel
     */
    protected class BlockWrapper {

        private final World world;
        public BlockData blockData;
        public int x;
        public int y;
        public int z;

        /**
         * @param b
         * @param blx
         * @param bly
         * @param blz
         */
        public BlockWrapper(final Block b, final int blx, final int bly, final int blz, final World world) {
            this.blockData = b.getBlockData();
            this.x = blx;
            this.y = bly;
            this.z = blz;
            this.world = world;
        }
    }

    /**
     * @author Monofraps
     */
    protected enum StampType {
        NO_AIR, FILL, DEFAULT
    }

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


    protected final boolean falling(final Material material) {
        // TODO: Translate this
        // return (id > 7 && id < 14);
        return false;
    }


    protected final boolean fallsOff(final Material material) {
        switch (material) {
            // TODO: Translate this
            // 6, 37, 38, 39, 40, 50, 51, 55, 59, 63, 64, 65, 66, 69, 70, 71, 72, 75, 76, 77, 83
            /**
             * case (6): case (37): case (38): case (39): case (40): case (50): case (51): case (55): case (59): case (63): case (64): case (65): case (66):
             * case (68): case (69): case (70): case (71): case (72): case (75): case (76): case (77): case (78): case (83): case (93): case (94):
             */
            default:
                return false;
        }
    }

    /**
     * @param cb
     */
    protected final void setBlock(final BlockWrapper cb) {
        final Block block = this.clampY(this.getTargetBlock().getX() + cb.x, this.getTargetBlock().getY() + cb.y, this.getTargetBlock().getZ() + cb.z);
        this.undo.put(block);
        block.setBlockData(cb.blockData);
    }

    /**
     * @param cb
     */
    protected final void setBlockFill(final BlockWrapper cb) {
        final Block block = this.clampY(this.getTargetBlock().getX() + cb.x, this.getTargetBlock().getY() + cb.y, this.getTargetBlock().getZ() + cb.z);
        if (block.getType() == Material.AIR) {
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
                if (this.fallsOff(block.blockData.getMaterial())) {
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
                if (this.fallsOff(block.blockData.getMaterial())) {
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
                if (this.fallsOff(block.blockData.getMaterial())) {
                    this.fall.add(block);
                } else if (this.falling(block.blockData.getMaterial())) {
                    this.drop.add(block);
                } else if (block.blockData.getMaterial() != Material.AIR) {
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
}
