package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

/**
 * @author Piotr
 */
// TODO: Figure out what this does
public class PullBrush extends Brush {

    private final HashSet<BlockWrapper> surface = new HashSet<>();
    private int vh;
    private double c1 = 1;
    private double c2 = 0;

    /**
     * Default Constructor.
     */
    public PullBrush() {
        this.setName("Soft Selection");
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        vm.height();
        vm.custom(Messages.PULLBRUSH_PINCH.replace("%val%",String.valueOf((-this.c1 + 1))));
        vm.custom(Messages.PULLBRUSH_BUBBLE.replace("%val%",String.valueOf(this.c2)));
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        try {
            final double pinch = Double.parseDouble(params[1]);
            final double bubble = Double.parseDouble(params[2]);
            this.c1 = 1 - pinch;
            this.c2 = bubble;
        } catch (final Exception exception) {
            v.sendMessage(Messages.INVALID_BRUSH_PARAM);
        }
    }

    /**
     * @param t
     * @return double
     */
    private double getStr(final double t) {
        final double lt = 1 - t;
        return (lt * lt * lt) + 3 * (lt * lt) * t * this.c1 + 3 * lt * (t * t) * this.c2; // My + (t * ((By + (t * ((c2 + (t * (0 - c2))) - By))) - My));
    }

    /**
     * @param v
     */
    private void getSurface(final SnipeData v) {
        this.surface.clear();

        final double bSquared = Math.pow(v.getBrushSize() + 0.5, 2);
        for (int z = -v.getBrushSize(); z <= v.getBrushSize(); z++) {
            final double zSquared = Math.pow(z, 2);
            final int actualZ = this.getTargetBlock().getZ() + z;
            for (int x = -v.getBrushSize(); x <= v.getBrushSize(); x++) {
                final double xSquared = Math.pow(x, 2);
                final int actualX = this.getTargetBlock().getX() + x;
                for (int y = -v.getBrushSize(); y <= v.getBrushSize(); y++) {
                    final double volume = (xSquared + Math.pow(y, 2) + zSquared);
                    if (volume <= bSquared) {
                        if (this.isSurface(actualX, this.getTargetBlock().getY() + y, actualZ)) {
                            this.surface.add(new BlockWrapper(this.clampY(actualX, this.getTargetBlock().getY() + y, actualZ), this.getStr(((volume / bSquared)))));
                        }
                    }
                }
            }
        }
    }

    /**
     * @param x
     * @param y
     * @param z
     * @return boolean
     */
    private boolean isSurface(final int x, final int y, final int z) {
        return this.getBlockMaterialAt(x, y, z) != Material.AIR
                && ((this.getBlockMaterialAt(x, y - 1, z) == Material.AIR)
                || (this.getBlockMaterialAt(x, y + 1, z) == Material.AIR)
                || (this.getBlockMaterialAt(x + 1, y, z) == Material.AIR)
                || (this.getBlockMaterialAt(x - 1, y, z) == Material.AIR)
                || (this.getBlockMaterialAt(x, y, z + 1) == Material.AIR)
                || (this.getBlockMaterialAt(x, y, z - 1) == Material.AIR));

    }

    private void setBlock(final BlockWrapper block) {
        final Block currentBlock = this.clampY(block.getX(), block.getY() + (int) (this.vh * block.getStr()), block.getZ());
        if (this.getBlockMaterialAt(block.getX(), block.getY() - 1, block.getZ()) == Material.AIR) {
            currentBlock.setBlockData(block.getBlockData());
            for (int y = block.getY(); y < currentBlock.getY(); y++) {
                this.setBlockMaterialAt(block.getX(), y, block.getZ(), Material.AIR);
            }
        } else {
            currentBlock.setBlockData(block.getBlockData());
            for (int y = block.getY() - 1; y < currentBlock.getY(); y++) {
                final Block current = this.clampY(block.getX(), y, block.getZ());
                current.setBlockData(block.getBlockData());
            }
        }
    }

    private void setBlockDown(final BlockWrapper block) {
        final Block currentBlock = this.clampY(block.getX(), block.getY() + (int) (this.vh * block.getStr()), block.getZ());
        currentBlock.setBlockData(block.getBlockData());
        for (int y = block.getY(); y > currentBlock.getY(); y--) {
            this.setBlockMaterialAt(block.getX(), y, block.getZ(), Material.AIR);
        }
        // }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.vh = v.getVoxelHeight();
        this.getSurface(v);

        if (this.vh > 0) {
            for (final BlockWrapper block : this.surface) {
                this.setBlock(block);
            }
        } else if (this.vh < 0) {
            for (final BlockWrapper block : this.surface) {
                this.setBlockDown(block);
            }
        }
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.vh = v.getVoxelHeight();

        this.surface.clear();

        int lastY;
        int newY;
        int lastStr;
        double str;
        final double brushSizeSquared = Math.pow(v.getBrushSize() + 0.5, 2);

        Material material;

        // Are we pulling up ?
        if (this.vh > 0) {

            // Z - Axis
            for (int z = -v.getBrushSize(); z <= v.getBrushSize(); z++) {

                final int zSquared = z * z;
                final int actualZ = this.getTargetBlock().getZ() + z;

                // X - Axis
                for (int x = -v.getBrushSize(); x <= v.getBrushSize(); x++) {

                    final int xSquared = x * x;
                    final int actualX = this.getTargetBlock().getX() + x;

                    // Down the Y - Axis
                    for (int y = v.getBrushSize(); y >= -v.getBrushSize(); y--) {

                        final double volume = zSquared + xSquared + (y * y);

                        // Is this in the range of the brush?
                        if (volume <= brushSizeSquared && this.getWorld().getBlockAt(actualX, this.getTargetBlock().getY() + y, actualZ).getType() != Material.AIR) {

                            int actualY = this.getTargetBlock().getY() + y;

                            // Starting strength and new Position
                            str = this.getStr(volume / brushSizeSquared);
                            lastStr = (int) (this.vh * str);
                            lastY = actualY + lastStr;

                            this.clampY(actualX, lastY, actualZ).setType(this.getWorld().getBlockAt(actualX, actualY, actualZ).getType());

                            if (str == 1) {
                                str = 0.8;
                            }

                            while (lastStr > 0) {
                                if (actualY < this.getTargetBlock().getY()) {
                                    str = str * str;
                                }
                                lastStr = (int) (this.vh * str);
                                newY = actualY + lastStr;
                                material = this.getWorld().getBlockAt(actualX, actualY, actualZ).getType();
                                for (int i = newY; i < lastY; i++) {
                                    this.clampY(actualX, i, actualZ).setBlockData(material.createBlockData());
                                }
                                lastY = newY;
                                actualY--;
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            for (int z = -v.getBrushSize(); z <= v.getBrushSize(); z++) {
                final double zSquared = Math.pow(z, 2);
                final int actualZ = this.getTargetBlock().getZ() + z;
                for (int x = -v.getBrushSize(); x <= v.getBrushSize(); x++) {
                    final double xSquared = Math.pow(x, 2);
                    final int actualX = this.getTargetBlock().getX() + x;
                    for (int y = -v.getBrushSize(); y <= v.getBrushSize(); y++) {
                        double volume = (xSquared + Math.pow(y, 2) + zSquared);
                        if (volume <= brushSizeSquared && this.getWorld().getBlockAt(actualX, this.getTargetBlock().getY() + y, actualZ).getType() != Material.AIR) {
                            final int actualY = this.getTargetBlock().getY() + y;
                            lastY = actualY + (int) (this.vh * this.getStr(volume / brushSizeSquared));
                            this.clampY(actualX, lastY, actualZ).setType(this.getWorld().getBlockAt(actualX, actualY, actualZ).getType());
                            y++;
                            volume = (xSquared + Math.pow(y, 2) + zSquared);
                            while (volume <= brushSizeSquared) {
                                final int blockY = this.getTargetBlock().getY() + y + (int) (this.vh * this.getStr(volume / brushSizeSquared));
                                final Material blockMaterial = this.getWorld().getBlockAt(actualX, this.getTargetBlock().getY() + y, actualZ).getType();
                                for (int i = blockY; i < lastY; i++) {
                                    this.clampY(actualX, i, actualZ).setBlockData(blockMaterial.createBlockData());
                                }
                                lastY = blockY;
                                y++;
                                volume = (xSquared + Math.pow(y, 2) + zSquared);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * @author Piotr
     */
    private final class BlockWrapper {

        private final BlockData blockData;
        private final double str;
        private final int x;
        private final int y;
        private final int z;

        /**
         * @param block
         * @param st
         */
        public BlockWrapper(final Block block, final double st) {
            this.blockData = block.getBlockData();
            this.x = block.getX();
            this.y = block.getY();
            this.z = block.getZ();
            this.str = st;
        }

        /**
         * @return the d
         */
        public BlockData getBlockData() {
            return this.blockData;
        }

        /**
         * @return the id
         */
        public Material getMaterial() {
            return this.blockData.getMaterial();
        }

        /**
         * @return the str
         */
        public double getStr() {
            return this.str;
        }

        /**
         * @return the x
         */
        public int getX() {
            return this.x;
        }

        /**
         * @return the y
         */
        public int getY() {
            return this.y;
        }

        /**
         * @return the z
         */
        public int getZ() {
            return this.z;
        }
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.pull";
    }
}
