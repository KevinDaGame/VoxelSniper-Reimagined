package com.github.kevindagame.brush;


import com.github.kevindagame.util.BrushOperation.BlockOperation;
import com.google.common.collect.Lists;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.BlockWrapper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Piotr
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#2d-rotation-brush">...</a>
 */
public class Rot2DBrush extends AbstractBrush {
    private BlockWrapper[][][] snap;
    private double se;

    /**
     *
     */
    public Rot2DBrush() {
        this.setName("2D Rotation");
    }

    private void getMatrix(final int bSize) {
        int brushSize = (bSize * 2) + 1;

        this.snap = new BlockWrapper[brushSize][brushSize][brushSize];

        final double brushSizeSquared = Math.pow(bSize + 0.5, 2);
        int sx = this.getTargetBlock().getX() - bSize;
        int sy = this.getTargetBlock().getY() - bSize;
        int sz = this.getTargetBlock().getZ() - bSize;

        for (int x = 0; x < this.snap.length; x++) {
            sz = this.getTargetBlock().getZ() - bSize;
            final double xSquared = Math.pow(x - bSize, 2);
            for (int y = 0; y < this.snap.length; y++) {
                sy = this.getTargetBlock().getY() - bSize;
                if (xSquared + Math.pow(y - bSize, 2) <= brushSizeSquared) {
                    for (int z = 0; z < this.snap.length; z++) {
                        final IBlock block = getWorld().getBlock(sx, sy, sz);
                        this.snap[x][z][y] = new BlockWrapper(block);
                        addOperation(new BlockOperation(block.getLocation(), block.getBlockData(), VoxelMaterial.AIR.createBlockData()));
                        sy++;
                    }
                }
                sz++;
            }
            sx++;
        }
    }

    private void rotate(final int bSize, final SnipeData v) {
        final double brushSiyeSquared = Math.pow(bSize + 0.5, 2);
        final double cos = Math.cos(this.se);
        final double sin = Math.sin(this.se);
        final boolean[][] doNotFill = new boolean[this.snap.length][this.snap.length];
        // I put y in the inside loop, since it doesn't have any power functions, should be much faster.
        // Also, new array keeps track of which x and z coords are being assigned in the rotated space so that we can
        // do a targeted filling of only those columns later that were left out.

        for (int x = 0; x < this.snap.length; x++) {
            final int xx = x - bSize;
            final double xSquared = Math.pow(xx, 2);

            for (int y = 0; y < this.snap.length; y++) {
                final int zz = y - bSize;

                if (xSquared + Math.pow(zz, 2) <= brushSiyeSquared) {
                    final double newX = (xx * cos) - (zz * sin);
                    final double newZ = (xx * sin) + (zz * cos);

                    doNotFill[(int) newX + bSize][(int) newZ + bSize] = true;

                    for (int currentY = 0; currentY < this.snap.length; currentY++) {
                        final int yy = currentY - bSize;
                        final BlockWrapper block = this.snap[x][currentY][y];

                        if (block.getMaterial().isAir()) {
                            continue;
                        }
                        var b = getWorld().getBlock(this.getTargetBlock().getX() + (int) newX, this.getTargetBlock().getY() + yy, this.getTargetBlock().getZ() + (int) newZ);
                        addOperation(new BlockOperation(b.getLocation(), b.getBlockData(), block.getBlockData()));

                    }
                }
            }
        }
        for (int x = 0; x < this.snap.length; x++) {
            final double xSquared = Math.pow(x - bSize, 2);
            final int fx = x + this.getTargetBlock().getX() - bSize;

            for (int z = 0; z < this.snap.length; z++) {
                if (xSquared + Math.pow(z - bSize, 2) <= brushSiyeSquared) {
                    final int fz = z + this.getTargetBlock().getZ() - bSize;

                    if (!doNotFill[x][z]) {
                        // smart fill stuff

                        for (int y = 0; y < this.snap.length; y++) {
                            final int fy = y + this.getTargetBlock().getY() - bSize;

                            final IBlockData a = this.getBlockDataAt(fx + 1, fy, fz);
                            final IBlockData d = this.getBlockDataAt(fx - 1, fy, fz);
                            final IBlockData c = this.getBlockDataAt(fx, fy, fz + 1);
                            final IBlockData b = this.getBlockDataAt(fx, fy, fz - 1);

                            IBlockData winner;

                            if (a.getMaterial() == b.getMaterial() || a.getMaterial() == c.getMaterial() || a.getMaterial() == d.getMaterial()) { // I figure that since we are already narrowing it down to ONLY the holes left behind, it
                                // should
                                // be fine to do all 5 checks needed to be legit about it.
                                winner = a;
                            } else if (b == d || c == d) {
                                winner = d;
                            } else {
                                winner = b; // blockPositionY making this default, it will also automatically cover situations where B = C;
                            }
                            var block = getWorld().getBlock(fx, fy, fz);
                            addOperation(new BlockOperation(block.getLocation(), block.getBlockData(), winner));
                        }
                    }
                }
            }
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        int bSize = v.getBrushSize();

        this.getMatrix(bSize);
        this.rotate(bSize, v);

    }

    @Override
    protected final void powder(final SnipeData v) {
        this.arrow(v);

    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.ROTATE_2D_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        try {
            double degrees = Double.parseDouble(params[0]);
            this.se = Math.toRadians(degrees);
            v.sendMessage(Messages.ANGLE_SET.replace("%se%", String.valueOf(degrees)));
        } catch (NumberFormatException temp) {
            v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        }
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("[number]"));
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.rot2d";
    }
}
