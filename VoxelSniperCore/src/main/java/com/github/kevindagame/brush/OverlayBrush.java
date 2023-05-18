package com.github.kevindagame.brush;

import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Utils;
import com.github.kevindagame.util.VoxelList;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jetbrains.annotations.NotNull;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#overlay-topsoil-filling-brush">...</a>
 *
 * @author Gavjenks
 */
public class OverlayBrush extends PerformerBrush {

    private static final int DEFAULT_DEPTH = 3;
    private int depth = DEFAULT_DEPTH;

    private boolean allBlocks = false;
    private boolean useVoxelList = false;



    private void overlay(final SnipeData v) {
        final int brushSize = v.getBrushSize();
        final double brushSizeSquared = Math.pow(brushSize + 0.5, 2);

        for (int z = brushSize; z >= -brushSize; z--) {
            for (int x = brushSize; x >= -brushSize; x--) {
                // check if column is valid
                // column is valid if it has no solid block right above the clicked layer
                final VoxelMaterial material = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + 1, this.getTargetBlock().getZ() + z);
                if (isIgnoredBlock(material)) {
                    if ((Math.pow(x, 2) + Math.pow(z, 2)) <= brushSizeSquared) {
                        for (int y = this.getTargetBlock().getY(); y > this.getMinHeight(); y--) {
                            // check for surface
                            final VoxelMaterial layerBlock = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y, this.getTargetBlock().getZ() + z);
                            if (!isIgnoredBlock(layerBlock)) {
                                for (int currentDepth = y; y - currentDepth < depth; currentDepth--) {
                                    final VoxelMaterial currentBlock = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, currentDepth, this.getTargetBlock().getZ() + z);
                                    if (isOverrideableMaterial(v.getVoxelList(), currentBlock)) {
                                        positions.add(new BaseLocation(getWorld(), this.getTargetBlock().getX() + x, currentDepth, this.getTargetBlock().getZ() + z));
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void overlayTwo(final SnipeData v) {
        final int brushSize = v.getBrushSize();
        final double brushSizeSquared = Math.pow(brushSize + 0.5, 2);
        final int[][] memory = new int[brushSize * 2 + 1][brushSize * 2 + 1];

        for (int z = brushSize; z >= -brushSize; z--) {
            for (int x = brushSize; x >= -brushSize; x--) {
                boolean surfaceFound = false;
                for (int y = this.getTargetBlock().getY(); y > this.getMinHeight() && !surfaceFound; y--) { // start scanning from the height you clicked at
                    if (memory[x + brushSize][z + brushSize] != 1) { // if haven't already found the surface in this column
                        if ((Math.pow(x, 2) + Math.pow(z, 2)) <= brushSizeSquared) { // if inside of the column...
                            if (!this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y - 1, this.getTargetBlock().getZ() + z).isAir()) { // if not a floating block (like one of Notch'world pools)
                                if (this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y + 1, this.getTargetBlock().getZ() + z).isAir()) { // must start at surface... this prevents it filling stuff in if
                                    // you click in a wall and it starts out below surface.
                                    final VoxelMaterial currentBlock = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y, this.getTargetBlock().getZ() + z);
                                    if (this.isOverrideableMaterial(v.getVoxelList(), currentBlock)) {
                                        for (int d = 1; (d < this.depth + 1); d++) {
                                            positions.add(new BaseLocation(getWorld(), this.getTargetBlock().getX() + x, y + d, this.getTargetBlock().getZ() + z)); // fills down as many layers as you specify
                                            // in parameters
                                            memory[x + brushSize][z + brushSize] = 1; // stop it from checking any other blocks in this vertical 1x1 column.
                                        }
                                        surfaceFound = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isIgnoredBlock(VoxelMaterial material) {
        return material.equals(VoxelMaterial.WATER) || material.isTransparent() || material == VoxelMaterial.CACTUS;
    }

    private boolean isOverrideableMaterial(VoxelList list, VoxelMaterial material) {
        if (this.useVoxelList) {
            return list.contains(material);
        }

        if (allBlocks && !(material.isAir())) {
            return true;
        }
        return VoxelMaterial.OVERRIDABLE_MATERIALS.contains(material);
    }

    @Override
    protected final void doArrow(final SnipeData v) {
        if (this.useVoxelList && v.getVoxelList().isEmpty()) {
            v.sendMessage(Messages.OVERLAY_BRUSH_VOXELLIST_EMPTY);
            return;
        }
        this.overlay(v);
    }

    @Override
    protected final void doPowder(final SnipeData v) {
        this.overlayTwo(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        String mode = this.allBlocks ? "all" : (this.useVoxelList ? "custom defined" : "natural");
        vm.custom(Messages.OVERLAY_MODE.replace("%mode%", mode));
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.OVERLAY_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        if (params[0].startsWith("depth")) {
            try {
                this.depth = Integer.parseInt(params[1]);

                if (this.depth < 1) {
                    this.depth = 1;
                }

                v.sendMessage(Messages.OVERLAY_DEPTH_SET.replace("%depth%", String.valueOf(this.depth)));
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
            v.sendMessage(Messages.OVERLAY_ON_MODE_DEPTH.replace("%depth%", String.valueOf(this.depth)).replace("%mode%", mode));
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        sendPerformerMessage(triggerHandle, v);
    }

    @NotNull
    @Override
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.addAll(Utils.newArrayList("depth", "mode"));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    @NotNull
    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();

        argumentValues.put("depth", Utils.newArrayList("[number]"));

        argumentValues.putAll(super.registerArgumentValues());
        return argumentValues;
    }
}
