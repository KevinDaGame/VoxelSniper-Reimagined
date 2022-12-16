package com.github.kevindagame.brush;

import com.google.common.collect.Lists;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.BlockFace;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author DivineRage
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#scanner-brush">...</a>
 */
public class ScannerBrush extends AbstractBrush {

    private static final int DEPTH_MIN = 1;
    private static final int DEPTH_DEFAULT = 24;
    private static final int DEPTH_MAX = 64;

    private int depth = DEPTH_DEFAULT;

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
        VoxelMaterial checkFor = v.getVoxelMaterial();

        for (int i = 1; i < this.depth + 1; i++) {
            int y = this.getTargetBlock().getY() + (bf.getModY() * i * -1);
            if (y >= this.getMaxHeight() || y < this.getMinHeight()) {
                break;
            }
            VoxelMaterial mat = getWorld().getBlock(this.getTargetBlock().getX() + (bf.getModX() * i * -1), y, this.getTargetBlock().getZ() + (bf.getModZ() * i * -1)).getMaterial();
            if (mat == checkFor) {
                v.sendMessage(Messages.SCANNER_FOUND_BLOCKS.replace("%checkFor%", checkFor.getName()).replace("%i%", Integer.toString(i)));
                return;
            }
        }
        v.sendMessage(Messages.SCANNER_FOUND_NO_BLOCKS);
    }

    @Override
    protected boolean actPerform(SnipeData v) {
        this.scan(v, this.getTargetBlock().getFace(this.getLastBlock()));
        return true;
    }

    @Override
    protected final void arrow(final SnipeData v) {
        positions.add(getTargetBlock().getLocation());
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(Messages.SCANNER_BRUSH_INFO.replace("%depth%", String.valueOf(this.depth)).replace("%checkFor%", vm.snipeData().getVoxelMaterial()));
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.SCANNER_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        try {
            if (params[0].startsWith("depth")) {
                this.depth = this.clamp(Integer.parseInt(params[1]), DEPTH_MIN, DEPTH_MAX);
                v.sendMessage(Messages.SCANNER_DEPTH_SET.replace("%depth%", String.valueOf(this.depth)));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
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
