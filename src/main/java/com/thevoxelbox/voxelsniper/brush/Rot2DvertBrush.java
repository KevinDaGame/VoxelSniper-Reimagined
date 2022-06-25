package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.BlockWrapper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.ArrayList;
import java.util.List;

// TODO: Dissect this
/**
 * @author Gavjenks, hack job from the other 2d rotation brush blockPositionY piotr
 */
// The X Y and Z variable names in this file do NOT MAKE ANY SENSE. Do not attempt to actually figure out what on earth is going on here. Just go to the
// original 2d horizontal brush if you wish to make anything similar to this, and start there. I didn't bother renaming everything.
public class Rot2DvertBrush extends Brush {

    private final int mode = 0;
    private int bSize;
    private int brushSize;
    private BlockWrapper[][][] snap;
    private double se;

    /**
     *
     */
    public Rot2DvertBrush() {
        this.setName("2D Rotation");
    }

    private void getMatrix() {
        this.brushSize = (this.bSize * 2) + 1;

        this.snap = new BlockWrapper[this.brushSize][this.brushSize][this.brushSize];

        int sx = this.getTargetBlock().getX() - this.bSize;
        int sy = this.getTargetBlock().getY() - this.bSize;
        int sz = this.getTargetBlock().getZ() - this.bSize;

        for (int x = 0; x < this.snap.length; x++) {
            sz = this.getTargetBlock().getZ() - this.bSize;

            for (int z = 0; z < this.snap.length; z++) {
                sy = this.getTargetBlock().getY() - this.bSize;

                for (int y = 0; y < this.snap.length; y++) {
                    final Block block = this.clampY(sx, sy, sz); // why is this not sx + x, sy + y sz + z?
                    this.snap[x][y][z] = new BlockWrapper(block);
                    block.setType(Material.AIR);
                    sy++;
                }

                sz++;
            }
            sx++;
        }
    }

    private void rotate(final SnipeData v) {
        final double brushSizeSquared = Math.pow(this.bSize + 0.5, 2);
        final double cos = Math.cos(this.se);
        final double sin = Math.sin(this.se);
        final boolean[][] doNotFill = new boolean[this.snap.length][this.snap.length];
        // I put y in the inside loop, since it doesn't have any power functions, should be much faster.
        // Also, new array keeps track of which x and z coords are being assigned in the rotated space so that we can
        // do a targeted filling of only those columns later that were left out.

        for (int x = 0; x < this.snap.length; x++) {
            final int xx = x - this.bSize;
            final double xSquared = Math.pow(xx, 2);

            for (int z = 0; z < this.snap.length; z++) {
                final int zz = z - this.bSize;

                if (xSquared + Math.pow(zz, 2) <= brushSizeSquared) {
                    final double newX = (xx * cos) - (zz * sin);
                    final double newZ = (xx * sin) + (zz * cos);

                    doNotFill[(int) newX + this.bSize][(int) newZ + this.bSize] = true;

                    for (int y = 0; y < this.snap.length; y++) {
                        final int yy = y - this.bSize;

                        final BlockWrapper block = this.snap[y][x][z];
                        if (block.getMaterial() == Material.AIR) {
                            continue;
                        }
                        this.setBlockMaterialAndDataAt(this.getTargetBlock().getX() + yy, this.getTargetBlock().getY() + (int) newX, this.getTargetBlock().getZ() + (int) newZ, block.getBlockData());
                    }
                }
            }
        }

        for (int x = 0; x < this.snap.length; x++) {
            final double xSquared = Math.pow(x - this.bSize, 2);
            final int fx = x + this.getTargetBlock().getX() - this.bSize;

            for (int z = 0; z < this.snap.length; z++) {
                if (xSquared + Math.pow(z - this.bSize, 2) <= brushSizeSquared) {
                    final int fz = z + this.getTargetBlock().getZ() - this.bSize;

                    if (!doNotFill[x][z]) {
                        // smart fill stuff
                        for (int y = 0; y < this.snap.length; y++) {
                            final int fy = y + this.getTargetBlock().getY() - this.bSize;

                            final BlockData a = this.getBlockDataAt(fy, fx + 1, fz);
                            final BlockData d = this.getBlockDataAt(fy, fx - 1, fz);
                            final BlockData c = this.getBlockDataAt(fy, fx, fz + 1);
                            final BlockData b = this.getBlockDataAt(fy, fx, fz - 1);

                            BlockData winner;

                            if (a.getMaterial() == b.getMaterial() || a.getMaterial() == c.getMaterial() || a.getMaterial() == d.getMaterial()) { // I figure that since we are already narrowing it down to ONLY the holes left behind, it
                                // should
                                // be fine to do all 5 checks needed to be legit about it.
                                winner = a;
                            } else if (b == d || c == d) {
                                winner = d;
                            } else {
                                winner = b; // blockPositionY making this default, it will also automatically cover situations where B = C;
                            }

                            this.setBlockMaterialAndDataAt(fy, fx, fz, winner);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.bSize = v.getBrushSize();

        switch (this.mode) {
            case 0:
                this.getMatrix();
                this.rotate(v);
                break;

            default:
                v.owner().getPlayer().sendMessage(ChatColor.RED + "Something went wrong.");
                break;
        }
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.bSize = v.getBrushSize();

        switch (this.mode) {
            case 0:
                this.getMatrix();
                this.rotate(v);
                break;

            default:
                v.owner().getPlayer().sendMessage(ChatColor.RED + "Something went wrong.");
                break;
        }
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "2D Rotation Brush Parameters:");
            v.sendMessage((ChatColor.AQUA + "/b " + "%triggerHandle%" + " [number]  -- Set angle in degrees").replace("%triggerHandle%",triggerHandle));
            return;
        }

        try {
            this.se = Math.toRadians(Double.parseDouble(params[0]));
            v.sendMessage(ChatColor.GREEN + "Angle set to " + this.se);
        } catch (NumberFormatException ignored) {
        }

        v.sendMessage((ChatColor.RED + "Invalid parameter! Use " + ChatColor.LIGHT_PURPLE + "'/b " + "%triggerHandle%" + " info'" + ChatColor.RED + " to display valid parameters.").replace("%triggerHandle%",triggerHandle));
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("[number]"));
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.rot2dvert";
    }
}
