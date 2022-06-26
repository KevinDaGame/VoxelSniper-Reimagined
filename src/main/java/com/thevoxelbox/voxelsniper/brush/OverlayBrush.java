package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.brush.perform.PerformerBrush;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.VoxelList;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_Overlay_.2F_Topsoil_Brush
 *
 * @author Gavjenks
 */
public class OverlayBrush extends PerformerBrush {

    private static final int DEFAULT_DEPTH = 3;
    private int depth = DEFAULT_DEPTH;

    private boolean allBlocks = false;
    private boolean useVoxelList = false;

    public OverlayBrush() {
        this.setName("Overlay (Topsoil Filling)");
    }

    private void overlay(final SnipeData v) {
        final int brushSize = v.getBrushSize();
        final double brushSizeSquared = Math.pow(brushSize + 0.5, 2);

        for (int z = brushSize; z >= -brushSize; z--) {
            for (int x = brushSize; x >= -brushSize; x--) {
                // check if column is valid
                // column is valid if it has no solid block right above the clicked layer
                final Material material = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + 1, this.getTargetBlock().getZ() + z);
                if (isIgnoredBlock(material)) {
                    if ((Math.pow(x, 2) + Math.pow(z, 2)) <= brushSizeSquared) {
                        for (int y = this.getTargetBlock().getY(); y > this.getMinHeight(); y--) {
                            // check for surface
                            final Material layerBlock = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y, this.getTargetBlock().getZ() + z);
                            if (!isIgnoredBlock(layerBlock)) {
                                for (int currentDepth = y; y - currentDepth < depth; currentDepth--) {
                                    final Material currentBlock = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, currentDepth, this.getTargetBlock().getZ() + z);
                                    if (isOverrideableMaterial(v.getVoxelList(), currentBlock)) {
                                        this.currentPerformer.perform(this.clampY(this.getTargetBlock().getX() + x, currentDepth, this.getTargetBlock().getZ() + z));
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }

        v.owner().storeUndo(this.currentPerformer.getUndo());
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
                            if (this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y - 1, this.getTargetBlock().getZ() + z) != Material.AIR) { // if not a floating block (like one of Notch'world pools)
                                if (this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y + 1, this.getTargetBlock().getZ() + z) == Material.AIR) { // must start at surface... this prevents it filling stuff in if
                                    // you click in a wall and it starts out below surface.
                                    final Material currentBlock = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y, this.getTargetBlock().getZ() + z);
                                    if (this.isOverrideableMaterial(v.getVoxelList(), currentBlock)) {
                                        for (int d = 1; (d < this.depth + 1); d++) {
                                            this.currentPerformer.perform(this.clampY(this.getTargetBlock().getX() + x, y + d, this.getTargetBlock().getZ() + z)); // fills down as many layers as you specify
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

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    private boolean isIgnoredBlock(Material material) {
        return material == Material.WATER || material.isTransparent() || material == Material.CACTUS;
    }

    private boolean isOverrideableMaterial(VoxelList list, Material material) {
        if (this.useVoxelList) {
            return list.contains(material);
        }

        if (allBlocks && !(material == Material.AIR)) {
            return true;
        }

        switch (material) {
            case STONE:
            case ANDESITE:
            case DIORITE:
            case GRANITE:
            case GRASS_BLOCK:
            case DIRT:
            case COARSE_DIRT:
            case PODZOL:
            case SAND:
            case RED_SAND:
            case GRAVEL:
            case SANDSTONE:
            case MOSSY_COBBLESTONE:
            case CLAY:
            case SNOW:
            case OBSIDIAN:
                return true;

            default:
                return false;
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        if (this.useVoxelList && v.getVoxelList().isEmpty()) {
            v.sendMessage(ChatColor.DARK_AQUA + "Overlay mode is set to custom defined blocks, but the VoxelList is empty. "
                    + ChatColor.GOLD + "Please use /vv list to see how to populate the list.");
            return;
        }
        this.overlay(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.overlayTwo(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        vm.custom(ChatColor.GOLD + "Overlaying on " + (this.allBlocks ? "all" : (this.useVoxelList ? "custom defined" : "natural")) + " blocks");
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "Overlay Brush Parameters:");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " depth [number]  -- Depth of blocks to overlay from surface");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " mode  -- Change target blocks to natural, custom defined or all blocks.");
            return;
        }

        if (params[0].startsWith("depth")) {
            try {
                this.depth = Integer.parseInt(params[1]);

                if (this.depth < 1) {
                    this.depth = 1;
                }

                v.sendMessage(ChatColor.AQUA + "Overlay depth set to " + this.depth);
                return;
            } catch (NumberFormatException ignored) {
            }
        }

        if (params[0].startsWith("mode")) {
            if (!this.allBlocks && !this.useVoxelList) {
                this.allBlocks = true;
                this.useVoxelList = false;
            } else if (this.allBlocks && !this.useVoxelList) {
                this.allBlocks = false;
                this.useVoxelList = true;
            } else if (!this.allBlocks && this.useVoxelList) {
                this.allBlocks = false;
                this.useVoxelList = false;
            }
            v.sendMessage(ChatColor.BLUE + "Will overlay on " + (this.allBlocks ? "all" : (this.useVoxelList ? "custom defined" : "natural")) + " blocks, " + this.depth + " blocks deep.");
            return;
        }

        v.sendMessage(ChatColor.RED + "Invalid parameter! Use " + ChatColor.LIGHT_PURPLE + "'/b " + triggerHandle + " info'" + ChatColor.RED + " to display valid parameters.");
        sendPerformerMessage(triggerHandle, v);
    }

    @Override
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.addAll(Lists.newArrayList("depth", "mode"));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();

        argumentValues.put("depth", Lists.newArrayList("[number]"));

        argumentValues.putAll(super.registerArgumentValues());
        return argumentValues;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.overlay";
    }
}
