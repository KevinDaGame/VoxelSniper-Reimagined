package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelList;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#splatter-overlay-brush">...</a>
 *
 * @author Gavjenks Splatterized blockPositionY Giltwist
 */
public class SplatterOverlayBrush extends SplatterBrushBase {

    private final boolean randomizeHeight = false;
    private final int yOffset = 0;
    private int depth = 3;
    private boolean allBlocks = false;
    private boolean useVoxelList = false;
    public SplatterOverlayBrush() {
        this.setName("Splatter Overlay");
    }

    private void sOverlay(final SnipeData v) {

        var splat = splatter2D(v);
        final double brushSizeSquared = Math.pow(v.getBrushSize() + 0.5, 2);
        final int[][] memory = new int[2 * v.getBrushSize() + 1][2 * v.getBrushSize() + 1];
        for (int z = v.getBrushSize(); z >= -v.getBrushSize(); z--) {
            for (int x = v.getBrushSize(); x >= -v.getBrushSize(); x--) {
                for (int y = this.getTargetBlock().getY(); y > this.getMinHeight(); y--) {
                    // start scanning from the height you clicked at
                    if (memory[x + v.getBrushSize()][z + v.getBrushSize()] != 1) {
                        // if haven't already found the surface in this column
                        if ((Math.pow(x, 2) + Math.pow(z, 2)) <= brushSizeSquared && splat[x + v.getBrushSize()][z + v.getBrushSize()] == 1) {
                            // if inside of the column && if to be splattered
                            final VoxelMaterial check = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y + 1, this.getTargetBlock().getZ() + z);
                            if (check.isAir() || check == VoxelMaterial.WATER) {
                                // must start at surface... this prevents it filling stuff in if you click in a wall
                                // and it starts out below surface.
                                final VoxelMaterial currentBlock = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y, this.getTargetBlock().getZ() + z);
                                if (this.isOverrideableMaterial(v.getVoxelList(), currentBlock)) {
                                    final int depth = this.randomizeHeight ? generator.nextInt(this.depth) : this.depth;

                                    for (int d = this.depth - 1; ((this.depth - d) <= depth); d--) {
                                        if (!this.getWorld().getBlock(this.getTargetBlock().getX() + x, y - d, this.getTargetBlock().getZ() + z).getMaterial().isAir()) {
                                            // fills down as many layers as you specify in parameters
                                            var block = v.getWorld().getBlock(getTargetBlock().getX() + x, y - d + yOffset, this.getTargetBlock().getZ() + z);
                                            this.positions.add(block.getLocation());
                                            // stop it from checking any other blocks in this vertical 1x1 column.
                                            memory[x + v.getBrushSize()][z + v.getBrushSize()] = 1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void soverlayTwo(final SnipeData v) {
        var splat = splatter2D(v);
        final double brushSizeSquared = Math.pow(v.getBrushSize() + 0.5, 2);

        for (int z = v.getBrushSize(); z >= -v.getBrushSize(); z--) {
            for (int x = v.getBrushSize(); x >= -v.getBrushSize(); x--) {
                var checked = false;
                for (int y = this.getTargetBlock().getY(); y > this.getMinHeight(); y--) { // start scanning from the height you clicked at
                    if (!checked) { // if haven't already found the surface in this column
                        if ((Math.pow(x, 2) + Math.pow(z, 2)) <= brushSizeSquared && splat[x + v.getBrushSize()][z + v.getBrushSize()] == 1) { // if inside of the column...&& if to be splattered
                            if (!this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y - 1, this.getTargetBlock().getZ() + z).isAir()) { // if not a floating block (like one of Notch'world pools)
                                if (this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y + 1, this.getTargetBlock().getZ() + z).isAir()) { // must start at surface... this prevents it filling stuff in if
                                    final VoxelMaterial currentBlock = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y, this.getTargetBlock().getZ() + z);
                                    if (this.isOverrideableMaterial(v.getVoxelList(), currentBlock)) {
                                        final int depth = this.randomizeHeight ? generator.nextInt(this.depth) : this.depth;
                                        for (int d = 1; (d < depth + 1); d++) {
                                            this.positions.add(new BaseLocation(this.getWorld(), this.getTargetBlock().getX() + x, y + d + yOffset, this.getTargetBlock().getZ() + z));
                                        }
                                        checked = true; // stop it from checking any other blocks in this vertical 1x1 column.
                                    }
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
    protected final void doArrow(final SnipeData v) {
        this.sOverlay(v);
    }

    @Override
    protected final void doPowder(final SnipeData v) {
        this.soverlayTwo(v);
    }

    @Override
    protected void resetBrush(SnipeData v) {
        super.resetBrush(v);
        this.depth = 3;
        this.allBlocks = false;
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (super.parseParams(triggerHandle, params, v)) return;
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.SPLATTER_OVERLAY_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
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

        try {
            if (params[0].startsWith("depth")) {
                this.depth = Integer.parseInt(params[1]);

                if (this.depth < 1) {
                    this.depth = 1;
                }

                v.sendMessage(Messages.OVERLAY_DEPTH_SET.replace("%depth%", String.valueOf(this.depth)));
                return;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        sendPerformerMessage(triggerHandle, v);
    }

    @NotNull
    @Override
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.addAll(Lists.newArrayList("recursion", "growth", "seed", "reset", "depth", "mode"));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    @NotNull
    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();

        // Number variables
        argumentValues.put("recursion", Lists.newArrayList("[number]"));
        argumentValues.put("depth", Lists.newArrayList("[number]"));

        // Decimal variables
        argumentValues.put("seed", Lists.newArrayList("[decimal]"));
        argumentValues.put("growth", Lists.newArrayList("[decimal]"));

        argumentValues.putAll(super.registerArgumentValues());
        return argumentValues;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.splatteroverlay";
    }
}
