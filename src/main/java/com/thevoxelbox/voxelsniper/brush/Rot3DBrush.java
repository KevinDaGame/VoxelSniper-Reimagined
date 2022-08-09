package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.util.BlockWrapper;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.material.BukkitMaterial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;

/**
 *
 */
public class Rot3DBrush extends Brush {

    private final int mode = 0;
    private int bSize;
    private int brushSize;
    private BlockWrapper[][][] snap;
    private double seYaw;
    private double sePitch;
    private double seRoll;

    /**
     *
     */
    public Rot3DBrush() {
        this.setName("3D Rotation");
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.brushMessage(Messages.ROT3D_BRUSH_MESSAGE);
    }

    // after all rotations, compare snapshot to new state of world, and store changed blocks to undo?
    // --> agreed. Do what erode does and store one snapshot with Block pointers and int id of what the block started with, afterwards simply go thru that
    // matrix and compare Block.getId with 'id' if different undo.add( new BlockWrapper ( Block, oldId ) )
    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        // which way is clockwise is less obvious for roll and pitch... should probably fix that / make it clear
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.ROTATION_3D_BRUSH_USAGE.replace("%triggerHandle%",triggerHandle));
            return;
        }
        try {
            if (params[0].equalsIgnoreCase("pitch")) {
                this.sePitch = Math.toRadians(Double.parseDouble(params[1]));
                v.sendMessage(Messages.ANGLE_AROUND_AXIS_SET.replace("%axis%", "Z").replace("%angle%",String.valueOf(this.sePitch)));
                if (this.sePitch < 0 || this.sePitch > 359) {
                    v.sendMessage(Messages.INVALID_BRUSH_PARAM_ANGLE);
                }
                return;
            }

            if (params[0].equalsIgnoreCase("roll")) {
                this.seRoll = Math.toRadians(Double.parseDouble(params[1]));
                v.sendMessage(Messages.ANGLE_AROUND_AXIS_SET.replace("%axis%", "X").replace("%angle%",String.valueOf(this.seRoll)));
                if (this.seRoll < 0 || this.seRoll > 359) {
                    v.sendMessage(Messages.INVALID_BRUSH_PARAM_ANGLE);
                }
                return;
            }

            if (params[0].equalsIgnoreCase("yaw")) {
                this.seYaw = Math.toRadians(Double.parseDouble(params[1]));
                v.sendMessage(Messages.ANGLE_AROUND_AXIS_SET.replace("%axis%", "Y").replace("%angle%",String.valueOf(this.seYaw)));
                if (this.seYaw < 0 || this.seYaw > 359) {
                    v.sendMessage(Messages.INVALID_BRUSH_PARAM_ANGLE);
                }
                return;
            }
        } catch (NumberFormatException temp) {
temp.printStackTrace();
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("pitch", "roll", "yaw"));
    }

    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        // Number variables
        HashMap<String, List<String>> argumentValues = new HashMap<>();
        
        argumentValues.put("pitch", Lists.newArrayList("[1-359]"));
        argumentValues.put("roll", Lists.newArrayList("[1-359]"));
        argumentValues.put("yaw", Lists.newArrayList("[1-359]"));

        return argumentValues;
    }

    private void getMatrix() { // only need to do once. But y needs to change + sphere
        final double brushSizeSquared = Math.pow(this.bSize + 0.5, 2);
        this.brushSize = (this.bSize * 2) + 1;

        this.snap = new BlockWrapper[this.brushSize][this.brushSize][this.brushSize];

        int sx = this.getTargetBlock().getX() - this.bSize;
        //int sy = this.getTargetBlock().getY() - this.bSize; Not used
        int sz = this.getTargetBlock().getZ() - this.bSize;

        for (int x = 0; x < this.snap.length; x++) {
            final double xSquared = Math.pow(x - this.bSize, 2);
            sz = this.getTargetBlock().getZ() - this.bSize;

            for (int z = 0; z < this.snap.length; z++) {
                final double zSquared = Math.pow(z - this.bSize, 2);
                sz = this.getTargetBlock().getY() - this.bSize;

                for (int y = 0; y < this.snap.length; y++) {
                    if (xSquared + zSquared + Math.pow(y - this.bSize, 2) <= brushSizeSquared) {
                        final IBlock block = this.clampY(sx, sz, sz);
                        this.snap[x][y][z] = new BlockWrapper(block);
                        block.setMaterial(new BukkitMaterial(Material.AIR));
                        sz++;
                    }
                }

                sz++;
            }
            sx++;
        }

    }

    private void rotate(final SnipeData v) {
        // basically 1) make it a sphere we are rotating in, not a cylinder
        // 2) do three rotations in a row, one in each dimension, unless some dimensions are set to zero or udnefined or whatever, then skip those.
        // --> Why not utilize Sniper'world new oportunities and have arrow rotate all 3, powder rotate x, goldsisc y, otherdisc z. Or something like that. Or
        // we
        // could just use arrow and powder and just differenciate between left and right click that gis 4 different situations
        // --> Well, there would be 7 different possibilities... X, Y, Z, XY, XZ, YZ, XYZ, and different numbers of parameters for each, so I think each having
        // and item is too confusing. How about this: arrow = rotate one dimension, based on the face you click, and takes 1 param... powder: rotates all three
        // at once, and takes 3 params.
        final double brushSizeSquared = Math.pow(this.bSize + 0.5, 2);
        final double cosYaw = Math.cos(this.seYaw);
        final double sinYaw = Math.sin(this.seYaw);
        final double cosPitch = Math.cos(this.sePitch);
        final double sinPitch = Math.sin(this.sePitch);
        final double cosRoll = Math.cos(this.seRoll);
        final double sinRoll = Math.sin(this.seRoll);
        final boolean[][][] doNotFill = new boolean[this.snap.length][this.snap.length][this.snap.length];
        final Undo undo = new Undo();

        for (int x = 0; x < this.snap.length; x++) {
            final int xx = x - this.bSize;
            final double xSquared = Math.pow(xx, 2);

            for (int z = 0; z < this.snap.length; z++) {
                final int zz = z - this.bSize;
                final double zSquared = Math.pow(zz, 2);
                final double newxzX = (xx * cosYaw) - (zz * sinYaw);
                final double newxzZ = (xx * sinYaw) + (zz * cosYaw);

                for (int y = 0; y < this.snap.length; y++) {
                    final int yy = y - this.bSize;
                    if (xSquared + zSquared + Math.pow(yy, 2) <= brushSizeSquared) {
                        undo.put(this.clampY(this.getTargetBlock().getX() + xx, this.getTargetBlock().getY() + yy, this.getTargetBlock().getZ() + zz)); // just store
                        // whole sphere in undo, too complicated otherwise, since this brush both adds and remos things unpredictably.

                        final double newxyX = (newxzX * cosPitch) - (yy * sinPitch);
                        final double newxyY = (newxzX * sinPitch) + (yy * cosPitch); // calculates all three in succession in precise math space
                        final double newyzY = (newxyY * cosRoll) - (newxzZ * sinRoll);
                        final double newyzZ = (newxyY * sinRoll) + (newxzZ * cosRoll);

                        doNotFill[(int) newxyX + this.bSize][(int) newyzY + this.bSize][(int) newyzZ + this.bSize] = true; // only rounds off to nearest
                        // block
                        // after all three, though.

                        final BlockWrapper block = this.snap[x][y][z];
                        if (block.getMaterial() == new BukkitMaterial( Material.AIR)) {
                            continue;
                        }
                        this.setBlockMaterialAndDataAt(this.getTargetBlock().getX() + (int) newxyX, this.getTargetBlock().getY() + (int) newyzY, this.getTargetBlock().getZ() + (int) newyzZ, block.getBlockData());
                    }
                }
            }
        }

        for (int x = 0; x < this.snap.length; x++) {
            final double xSquared = Math.pow(x - this.bSize, 2);
            final int fx = x + this.getTargetBlock().getX() - this.bSize;

            for (int z = 0; z < this.snap.length; z++) {
                final double zSquared = Math.pow(z - this.bSize, 2);
                final int fz = z + this.getTargetBlock().getZ() - this.bSize;

                for (int y = 0; y < this.snap.length; y++) {
                    if (xSquared + zSquared + Math.pow(y - this.bSize, 2) <= brushSizeSquared) {
                        if (!doNotFill[x][y][z]) {
                            // smart fill stuff
                            final int fy = y + this.getTargetBlock().getY() - this.bSize;
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

                            this.setBlockMaterialAndDataAt(fx, fy, fz, winner);
                        }
                    }
                }
            }
        }
        v.owner().storeUndo(undo);
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
                v.sendMessage(Messages.ERROR);
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
                v.sendMessage(Messages.ERROR);
                break;
        }
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.rot3d";
    }
}
