package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.brushOperation.CustomOperation;
import com.github.kevindagame.util.brushOperation.CustomOperationContext;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.BlockFace;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author DivineRage
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#scanner-brush">...</a>
 */
public class ScannerBrush extends CustomBrush {

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
    protected final void arrow(final SnipeData v) {
        addOperation(new CustomOperation(getTargetBlock().getLocation(), this, v, CustomOperationContext.TARGETLOCATION));
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(Messages.SCANNER_BRUSH_INFO.replace("%depth%", String.valueOf(this.depth)).replace("%checkFor%", vm.snipeData().getVoxelMaterial()));
    }

    @Override
    public final void parseParameters(@NotNull final String triggerHandle, final String[] params, @NotNull final SnipeData v) {
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

    @NotNull
    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("depth"));
    }

    @NotNull
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

    @Override
    public boolean perform(ImmutableList<CustomOperation> operations, @NotNull SnipeData snipeData, @NotNull Undo undo) {
        if(operations.size() != 1) {
            return false;
        }
        this.scan(snipeData, this.getTargetBlock().getFace(this.getLastBlock()));
        return true;
    }
}
