package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author DivineRage
 */
public class ScannerBrush extends Brush {

    private static final int DEPTH_MIN = 1;
    private static final int DEPTH_DEFAULT = 24;
    private static final int DEPTH_MAX = 64;

    private int depth = DEPTH_DEFAULT;
    private Material checkFor = Material.AIR;

    /**
     *
     */
    public ScannerBrush() {
        this.setName("Scanner");
    }

    private int clamp(final int value, final int min, final int max) {
        if (value < min) {
            return min;
        } else return Math.min(value, max);
    }

    private void scan(final SnipeData v, final BlockFace bf) {
        if (bf == null) {
            return;
        }

        switch (bf) {
            case NORTH:
                // Scan south
                for (int i = 1; i < this.depth + 1; i++) {
                    if (this.clampY(this.getTargetBlock().getX() + i, this.getTargetBlock().getY(), this.getTargetBlock().getZ()).getType() == this.checkFor) {
                        v.sendMessage(Messages.SCANNER_FOUND_BLOCKS.replace("%checkFor%", this.checkFor.toString()).replace("%i%", Integer.toString(i)));
                        return;
                    }
                }
                v.sendMessage(Messages.SCANNER_FOUND_NO_BLOCKS);
                break;

            case SOUTH:
                // Scan north
                for (int i = 1; i < this.depth + 1; i++) {
                    if (this.clampY(this.getTargetBlock().getX() - i, this.getTargetBlock().getY(), this.getTargetBlock().getZ()).getType() == this.checkFor) {
                        v.sendMessage(Messages.SCANNER_FOUND_BLOCKS.replace("%checkFor%", this.checkFor.toString()).replace("%i%", Integer.toString(i)));
                        return;
                    }
                }
                v.sendMessage(Messages.SCANNER_FOUND_NO_BLOCKS);
                break;

            case EAST:
                // Scan west
                for (int i = 1; i < this.depth + 1; i++) {
                    if (this.clampY(this.getTargetBlock().getX(), this.getTargetBlock().getY(), this.getTargetBlock().getZ() + i).getType() == this.checkFor) {
                        v.sendMessage(Messages.SCANNER_FOUND_BLOCKS.replace("%checkFor%", this.checkFor.toString()).replace("%i%", Integer.toString(i)));
                        return;
                    }
                }
                v.sendMessage(Messages.SCANNER_FOUND_NO_BLOCKS);
                break;

            case WEST:
                // Scan east
                for (int i = 1; i < this.depth + 1; i++) {
                    if (this.clampY(this.getTargetBlock().getX(), this.getTargetBlock().getY(), this.getTargetBlock().getZ() - i).getType() == this.checkFor) {
                        v.sendMessage(Messages.SCANNER_FOUND_BLOCKS.replace("%checkFor%", this.checkFor.toString()).replace("%i%", Integer.toString(i)));
                        return;
                    }
                }
                v.sendMessage(Messages.SCANNER_FOUND_NO_BLOCKS);
                break;

            case UP:
                // Scan down
                for (int i = 1; i < this.depth + 1; i++) {
                    if ((this.getTargetBlock().getY() - i) <= 0) {
                        break;
                    }
                    if (this.clampY(this.getTargetBlock().getX(), this.getTargetBlock().getY() - i, this.getTargetBlock().getZ()).getType() == this.checkFor) {
                        v.sendMessage(Messages.SCANNER_FOUND_BLOCKS.replace("%checkFor%", this.checkFor.toString()).replace("%i%", Integer.toString(i)));
                        return;
                    }
                }
                v.sendMessage(Messages.SCANNER_FOUND_NO_BLOCKS);
                break;

            case DOWN:
                // Scan up
                for (int i = 1; i < this.depth + 1; i++) {
                    if ((this.getTargetBlock().getY() + i) >= this.getMaxHeight()) {
                        break;
                    }
                    if (this.clampY(this.getTargetBlock().getX(), this.getTargetBlock().getY() + i, this.getTargetBlock().getZ()).getType() == this.checkFor) {
                        v.sendMessage(Messages.SCANNER_FOUND_BLOCKS.replace("%checkFor%", this.checkFor.toString()).replace("%i%", Integer.toString(i)));
                        return;
                    }
                }
                v.sendMessage(Messages.SCANNER_FOUND_NO_BLOCKS);
                break;

            default:
                break;
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.checkFor = v.getVoxelMaterial();
        this.scan(v, this.getTargetBlock().getFace(this.getLastBlock()));
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(Messages.SCANNER_BRUSH_INFO.replace("%depth%",String.valueOf(this.depth)).replace("%checkFor%",String.valueOf(this.checkFor)));
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.SCANNER_BRUSH_USAGE.replace("%triggerHandle%",triggerHandle));
            return;
        }

        try {
            if (params[0].startsWith("depth")) {
                this.depth = this.clamp(Integer.parseInt(params[1]), DEPTH_MIN, DEPTH_MAX);
                v.sendMessage(Messages.SCANNER_DEPTH_SET.replace("%depth%",String.valueOf(this.depth)));
            }
        } catch (NumberFormatException ignored) {
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("depth"));
    }

    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        // Number variables
        HashMap<String, List<String>> argumentValues = new HashMap<>();
        
        argumentValues.put("depth", Lists.newArrayList("[number]"));
        
        return argumentValues;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.scanner";
    }
}
