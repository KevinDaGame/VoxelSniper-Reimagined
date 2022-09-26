package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;

import java.util.HashSet;
import java.util.List;

/**
 * @author Piotr
 */
// TODO: Figure out what this does
public class PullBrush extends Brush {

    private double pinch = 1;
    private double bubble = 0;

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
        vm.custom(Messages.PULLBRUSH_PINCH.replace("%val%", String.valueOf((-this.pinch + 1))));
        vm.custom(Messages.PULLBRUSH_BUBBLE.replace("%val%", String.valueOf(this.bubble)));
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.PULLBRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        try {
            if (params[0].startsWith("pinch")) {
                final double d = Double.parseDouble(params[1]);
                if (d < 0.0 || d > 1.0) {
                    v.sendMessage(Messages.NUMBER_OUT_OF_RANGE.replace("%arg%", params[0]).replace("%min%", "0.0").replace("%max%", "1.0"));
                } else {
                    this.pinch = 1 - d;
                    v.sendMessage(Messages.PULLBRUSH_PINCH.replace("%val%", String.valueOf((-this.pinch + 1))));
                }

                return;
            }

            if (params[0].startsWith("bubble")) {
                double d = Double.parseDouble(params[1]);
                if (d < 0.0 || d > 1.0) {
                    v.sendMessage(Messages.NUMBER_OUT_OF_RANGE.replace("%arg%", params[0]).replace("%min%", "0.0").replace("%max%", "1.0"));
                } else {
                    this.bubble = d;
                    v.sendMessage(Messages.PULLBRUSH_BUBBLE.replace("%val%", String.valueOf(this.bubble)));
                }
                return;
            }
        } catch (NumberFormatException ignored) {
        }

        v.sendMessage(Messages.PULLBRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public List<String> registerArguments() {
        return Lists.newArrayList("pinch", "bubble");
    }

    /**
     * @param t
     * @return double
     */
    private double getStr(final double t) {
        final double lt = 1 - t;
        return (lt * lt * lt) + 3 * (lt * lt) * t * this.pinch + 3 * lt * (t * t) * this.bubble; // My + (t * ((By + (t * ((c2 + (t * (0 - c2))) - By))) - My));
    }

    /**
     * @param v
     */
    private HashSet<BlockWrapper> getSurface(final SnipeData v) {
        HashSet<BlockWrapper> surface = new HashSet<>();

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
                            surface.add(new BlockWrapper(this.clampY(actualX, this.getTargetBlock().getY() + y, actualZ), this.getStr(((volume / bSquared)))));
                        }
                    }
                }
            }
        }

        return surface;
    }

    /**
     * @param x
     * @param y
     * @param z
     * @return boolean
     */
    private boolean isSurface(final int x, final int y, final int z) {
        return (!this.getBlockMaterialAt(x, y, z).isAir())
                && ((this.getBlockMaterialAt(x, y - 1, z).isAir())
                || (this.getBlockMaterialAt(x, y + 1, z).isAir())
                || (this.getBlockMaterialAt(x + 1, y, z).isAir())
                || (this.getBlockMaterialAt(x - 1, y, z).isAir())
                || (this.getBlockMaterialAt(x, y, z + 1).isAir())
                || (this.getBlockMaterialAt(x, y, z - 1).isAir()));

    }

    private void setBlock(final BlockWrapper block, Undo undo, int vh) {
        final IBlock currentBlock = this.clampY(block.getX(), block.getY() + (int) (vh * block.getStr()), block.getZ());
        undo.put(currentBlock);
        currentBlock.setBlockData(block.getBlockData());

        if (this.getBlockMaterialAt(block.getX(), block.getY() - 1, block.getZ()).isAir()) {
            for (int y = block.getY(); y < currentBlock.getY(); y++) {
                IBlock b = this.clampY(block.getX(), y, block.getZ());
                undo.put(b);
                b.setBlockData(VoxelMaterial.AIR.createBlockData());
            }
        } else {
            for (int y = block.getY() - 1; y < currentBlock.getY(); y++) {
                final IBlock current = this.clampY(block.getX(), y, block.getZ());
                undo.put(current);
                current.setBlockData(block.getBlockData());
            }
        }
    }

    private void setBlockDown(final BlockWrapper block, Undo undo, int vh) {
        final IBlock currentBlock = this.clampY(block.getX(), block.getY() + (int) (vh * block.getStr()), block.getZ());
        undo.put(currentBlock);
        currentBlock.setBlockData(block.getBlockData());
        for (int y = block.getY(); y > currentBlock.getY(); y--) {
            IBlock b = this.clampY(block.getX(), y, block.getZ());
            undo.put(b);
            b.setBlockData(VoxelMaterial.AIR.createBlockData());
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        int vh = v.getVoxelHeight();
        HashSet<BlockWrapper> surface = this.getSurface(v);
        Undo undo = new Undo();

        if (vh > 0) {
            for (final BlockWrapper block : surface) {
                this.setBlock(block, undo, vh);
            }
        } else if (vh < 0) {
            for (final BlockWrapper block : surface) {
                this.setBlockDown(block, undo, vh);
            }
        }

        if (undo.getSize() > 0)
            v.owner().storeUndo(undo);
    }

    @Override
    protected final void powder(final SnipeData v) {
        Undo undo = new Undo();

        int vh = v.getVoxelHeight();

        int lastY;
        int lastStr;
        double str;
        final double brushSizeSquared = Math.pow(v.getBrushSize() + 0.5, 2);

        // Are we pulling up ?
        if (vh > 0) {

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
                        if (volume <= brushSizeSquared && !this.getWorld().getBlock(actualX, this.getTargetBlock().getY() + y, actualZ).getMaterial().isAir()) {

                            int actualY = this.getTargetBlock().getY() + y;

                            // Starting strength and new Position
                            str = this.getStr(volume / brushSizeSquared);
                            lastStr = (int) (vh * str);
                            lastY = actualY + lastStr;

                            IBlock b = this.clampY(actualX, lastY, actualZ);
                            undo.put(b);
                            b.setMaterial(this.getWorld().getBlock(actualX, actualY, actualZ).getMaterial());

                            if (str == 1) {
                                str = 0.8;
                            }

                            while (lastStr > 0) {
                                if (actualY < this.getTargetBlock().getY()) {
                                    str = str * str;
                                }
                                lastStr = (int) (vh * str);
                                int newY = actualY + lastStr;
                                VoxelMaterial material = this.getWorld().getBlock(actualX, actualY, actualZ).getMaterial();
                                for (int i = newY; i < lastY; i++) {
                                    b = this.clampY(actualX, i, actualZ);
                                    undo.put(b);
                                    b.setBlockData(material.createBlockData());
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
                        if (volume <= brushSizeSquared && !this.getWorld().getBlock(actualX, this.getTargetBlock().getY() + y, actualZ).getMaterial().isAir()) {
                            final int actualY = this.getTargetBlock().getY() + y;
                            lastY = actualY + (int) (vh * this.getStr(volume / brushSizeSquared));
                            IBlock b = this.clampY(actualX, lastY, actualZ);
                            undo.put(b);
                            b.setMaterial(this.getWorld().getBlock(actualX, actualY, actualZ).getMaterial());
                            y++;
                            volume = (xSquared + Math.pow(y, 2) + zSquared);
                            while (volume <= brushSizeSquared) {
                                final int blockY = this.getTargetBlock().getY() + y + (int) (vh * this.getStr(volume / brushSizeSquared));
                                final VoxelMaterial blockMaterial = this.getWorld().getBlock(actualX, this.getTargetBlock().getY() + y, actualZ).getMaterial();
                                for (int i = blockY; i < lastY; i++) {
                                    b = this.clampY(actualX, i, actualZ);
                                    undo.put(b);
                                    b.setBlockData(blockMaterial.createBlockData());
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

        if (undo.getSize() > 0)
            v.owner().storeUndo(undo);
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.pull";
    }

    /**
     * @author Piotr
     */
    private static final class BlockWrapper {

        private final IBlockData blockData;
        private final double str;
        private final int x;
        private final int y;
        private final int z;

        /**
         * @param block
         * @param st
         */
        public BlockWrapper(final IBlock block, final double st) {
            this.blockData = block.getBlockData();
            this.x = block.getX();
            this.y = block.getY();
            this.z = block.getZ();
            this.str = st;
        }

        /**
         * @return the d
         */
        public IBlockData getBlockData() {
            return this.blockData;
        }

        /**
         * @return the id
         */
        public VoxelMaterial getMaterial() {
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
}