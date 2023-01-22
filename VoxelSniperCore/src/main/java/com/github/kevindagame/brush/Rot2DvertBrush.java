package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.BlockWrapper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

// TODO: Dissect this

/**
 * @author Gavjenks, hack job from the other 2d rotation brush blockPositionY piotr
 */
// The X Y and Z variable names in this file do NOT MAKE ANY SENSE. Do not attempt to actually figure out what on earth is going on here. Just go to the
// original 2d horizontal brush if you wish to make anything similar to this, and start there. I didn't bother renaming everything.
public class Rot2DvertBrush extends AbstractBrush {

    private BlockWrapper[][][] snap;
    private double se;


    private void getMatrix(final int bSize) {
        int brushSize = (bSize * 2) + 1;

        this.snap = new BlockWrapper[brushSize][brushSize][brushSize];

        int sx = this.getTargetBlock().getX() - bSize;
        int sy = this.getTargetBlock().getY() - bSize;
        int sz = this.getTargetBlock().getZ() - bSize;

        for (int x = 0; x < this.snap.length; x++) {
            sz = this.getTargetBlock().getZ() - bSize;

            for (int z = 0; z < this.snap.length; z++) {
                sy = this.getTargetBlock().getY() - bSize;

                for (int y = 0; y < this.snap.length; y++) {
                    final IBlock block = this.getWorld().getBlock(sx, sy, sz); // why is this not sx + x, sy + y sz + z?
                    this.snap[x][y][z] = new BlockWrapper(block);
                    block.setMaterial(VoxelMaterial.AIR);
                    sy++;
                }

                sz++;
            }
            sx++;
        }
    }

    private void rotate(final int bSize, final SnipeData v) {
        final double brushSizeSquared = Math.pow(bSize + 0.5, 2);
        final double cos = Math.cos(this.se);
        final double sin = Math.sin(this.se);
        final boolean[][] doNotFill = new boolean[this.snap.length][this.snap.length];
        // I put y in the inside loop, since it doesn't have any power functions, should be much faster.
        // Also, new array keeps track of which x and z coords are being assigned in the rotated space so that we can
        // do a targeted filling of only those columns later that were left out.

        for (int x = 0; x < this.snap.length; x++) {
            final int xx = x - bSize;
            final double xSquared = Math.pow(xx, 2);

            for (int z = 0; z < this.snap.length; z++) {
                final int zz = z - bSize;

                if (xSquared + Math.pow(zz, 2) <= brushSizeSquared) {
                    final double newX = (xx * cos) - (zz * sin);
                    final double newZ = (xx * sin) + (zz * cos);

                    doNotFill[(int) newX + bSize][(int) newZ + bSize] = true;

                    for (int y = 0; y < this.snap.length; y++) {
                        final int yy = y - bSize;

                        final BlockWrapper block = this.snap[y][x][z];
                        if (block.getMaterial().isAir()) {
                            continue;
                        }
                        this.getWorld().getBlock(this.getTargetBlock().getX() + yy, this.getTargetBlock().getY() + (int) newX, this.getTargetBlock().getZ() + (int) newZ).setBlockData(block.getBlockData());
                    }
                }
            }
        }

        for (int x = 0; x < this.snap.length; x++) {
            final double xSquared = Math.pow(x - bSize, 2);
            final int fx = x + this.getTargetBlock().getX() - bSize;

            for (int z = 0; z < this.snap.length; z++) {
                if (xSquared + Math.pow(z - bSize, 2) <= brushSizeSquared) {
                    final int fz = z + this.getTargetBlock().getZ() - bSize;

                    if (!doNotFill[x][z]) {
                        // smart fill stuff
                        for (int y = 0; y < this.snap.length; y++) {
                            final int fy = y + this.getTargetBlock().getY() - bSize;

                            final IBlockData a = this.getBlockDataAt(fy, fx + 1, fz);
                            final IBlockData d = this.getBlockDataAt(fy, fx - 1, fz);
                            final IBlockData c = this.getBlockDataAt(fy, fx, fz + 1);
                            final IBlockData b = this.getBlockDataAt(fy, fx, fz - 1);

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

                            this.getWorld().getBlock(fy, fx, fz).setBlockData(winner);
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
    public final void parseParameters(@NotNull final String triggerHandle, final String[] params, @NotNull final SnipeData v) {
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

    @NotNull
    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("[number]"));
    }
}
