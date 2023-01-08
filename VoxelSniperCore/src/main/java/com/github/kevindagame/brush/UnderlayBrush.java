package com.github.kevindagame.brush;

import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.google.common.collect.Lists;
import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelList;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#underlay-reverse-overlay-brush">...</a>
 *
 * @author jmck95 Credit to GavJenks for framework and 95 of code. Big Thank you
 * to GavJenks
 */
public class UnderlayBrush extends PerformerBrush {

    private static final int DEFAULT_DEPTH = 3;
    private int depth = DEFAULT_DEPTH;

    private boolean allBlocks = false;
    private boolean useVoxelList = false;

    /**
     *
     */
    public UnderlayBrush() {
        this.setName("Underlay (Reverse Overlay)");
    }

    private void underlay(final SnipeData v) {
        final int[][] memory = new int[v.getBrushSize() * 2 + 1][v.getBrushSize() * 2 + 1];
        final double brushSizeSquared = Math.pow(v.getBrushSize() + 0.5, 2);

        for (int z = v.getBrushSize(); z >= -v.getBrushSize(); z--) {
            for (int x = v.getBrushSize(); x >= -v.getBrushSize(); x--) {
                for (int y = this.getTargetBlock().getY(); y < this.getTargetBlock().getY() + this.depth; y++) { // start scanning from the height you clicked at
                    if (memory[x + v.getBrushSize()][z + v.getBrushSize()] != 1) { // if haven't already found the surface in this column
                        if ((Math.pow(x, 2) + Math.pow(z, 2)) <= brushSizeSquared) { // if inside of the column...
                            final VoxelMaterial currentBlock = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y, this.getTargetBlock().getZ() + z);
                            if (this.isOverrideableMaterial(v.getVoxelList(), currentBlock)) {
                                for (int d = 0; (d < this.depth); d++) {
                                    var mat = getBlockMaterialAt(this.getTargetBlock().getX() + x, y + d, this.getTargetBlock().getZ() + z);
                                    if (!mat.isAir()) {
                                        positions.add(new BaseLocation(getWorld(), this.getTargetBlock().getX() + x, y + d, this.getTargetBlock().getZ() + z)); // fills down as many layers as you specify in
                                        // parameters
                                        memory[x + v.getBrushSize()][z + v.getBrushSize()] = 1; // stop it from checking any other blocks in this vertical 1x1 column.
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    private void underlay2(final SnipeData v) {
        final int[][] memory = new int[v.getBrushSize() * 2 + 1][v.getBrushSize() * 2 + 1];
        final double brushSizeSquared = Math.pow(v.getBrushSize() + 0.5, 2);

        for (int z = v.getBrushSize(); z >= -v.getBrushSize(); z--) {
            for (int x = v.getBrushSize(); x >= -v.getBrushSize(); x--) {
                for (int y = this.getTargetBlock().getY(); y < this.getTargetBlock().getY() + this.depth; y++) { // start scanning from the height you clicked at
                    if (memory[x + v.getBrushSize()][z + v.getBrushSize()] != 1) { // if haven't already found the surface in this column
                        if ((Math.pow(x, 2) + Math.pow(z, 2)) <= brushSizeSquared) { // if inside of the column...
                            final VoxelMaterial currentBlock = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y, this.getTargetBlock().getZ() + z);
                            if (this.isOverrideableMaterial(v.getVoxelList(), currentBlock)) {
                                for (int d = -1; (d < this.depth - 1); d++) {
                                    positions.add(new BaseLocation(getWorld(), this.getTargetBlock().getX() + x, y - d, this.getTargetBlock().getZ() + z)); // fills down as many layers as you specify in
                                    // parameters
                                    memory[x + v.getBrushSize()][z + v.getBrushSize()] = 1; // stop it from checking any other blocks in this vertical 1x1 column.
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isOverrideableMaterial(VoxelList list, VoxelMaterial material) {
        if (this.useVoxelList) {
            return list.contains(material);
        }

        if (allBlocks && !material.isAir()) {
            return true;
        }

        return VoxelMaterial.OVERRIDABLE_MATERIALS.contains(material);
    }

    @Override
    public final void doArrow(final SnipeData v) {
        this.underlay(v);
    }

    @Override
    public final void doPowder(final SnipeData v) {
        this.underlay2(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        String mode = this.allBlocks ? "all" : (this.useVoxelList ? "custom defined" : "natural");
        vm.custom(Messages.UNDERLAY_MODE.replace("%mode%", mode));
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.UNDERLAY_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        if (params[0].startsWith("depth")) {
            try {
                this.depth = Integer.parseInt(params[1]);

                if (this.depth < 1) {
                    this.depth = 1;
                }

                v.sendMessage(Messages.UNDERLAY_DEPTH_SET.replace("%depth%", String.valueOf(this.depth)));
                return;
            } catch (NumberFormatException temp) {
                temp.printStackTrace();
            }
        }

        if (params[0].startsWith("mode")) {
            if (!this.useVoxelList) {
                if (!this.allBlocks) {
                    this.allBlocks = true;
                } else {
                    this.allBlocks = false;
                    this.useVoxelList = true;
                }
            } else if (!this.allBlocks) {
                this.useVoxelList = false;
            }
            String mode = this.allBlocks ? "all" : (this.useVoxelList ? "custom defined" : "natural");
            v.sendMessage(Messages.UNDERLAY_ON_MODE_DEPTH.replace("%depth%", String.valueOf(this.depth)).replace("%mode%", mode));

            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        sendPerformerMessage(triggerHandle, v);
    }

    @NotNull
    @Override
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.addAll(Lists.newArrayList("depth", "mode"));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    @NotNull
    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();

        argumentValues.put("depth", Lists.newArrayList("[number]"));

        argumentValues.putAll(super.registerArgumentValues());
        return argumentValues;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.underlay";
    }
}
