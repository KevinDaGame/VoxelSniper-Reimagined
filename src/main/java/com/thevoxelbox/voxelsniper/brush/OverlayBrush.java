package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.brush.perform.PerformerBrush;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_Overlay_.2F_Topsoil_Brush
 *
 * @author Gavjenks
 */
public class OverlayBrush extends PerformerBrush {

    private static final int DEFAULT_DEPTH = 3;
    private int depth = DEFAULT_DEPTH;
    private boolean allBlocks = false;

    /**
     *
     */
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
                        for (int y = this.getTargetBlock().getY(); y > 0; y--) {
                            // check for surface
                            final Material layerBlock = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y, this.getTargetBlock().getZ() + z);
                            if (!isIgnoredBlock(layerBlock)) {
                                for (int currentDepth = y; y - currentDepth < depth; currentDepth--) {
                                    final Material currentBlock = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, currentDepth, this.getTargetBlock().getZ() + z);
                                    if (isOverrideableMaterial(currentBlock)) {
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

    @SuppressWarnings("deprecation")
    private boolean isIgnoredBlock(Material material) {
        return material == Material.WATER || material.isTransparent() || material == Material.CACTUS;
    }

    @SuppressWarnings("deprecation")
    private boolean isOverrideableMaterial(Material material) {
        if (allBlocks && !(material == Material.AIR)) {
            return true;
        }

        switch (material) {
            case STONE:
            case ANDESITE:
            case DIORITE:
            case GRANITE:
            case GRASS:
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

    private void overlayTwo(final SnipeData v) {
        final int brushSize = v.getBrushSize();
        final double brushSizeSquared = Math.pow(brushSize + 0.5, 2);
        final int[][] memory = new int[brushSize * 2 + 1][brushSize * 2 + 1];

        for (int z = brushSize; z >= -brushSize; z--) {
            for (int x = brushSize; x >= -brushSize; x--) {
                boolean surfaceFound = false;
                for (int y = this.getTargetBlock().getY(); y > 0 && !surfaceFound; y--) { // start scanning from the height you clicked at
                    if (memory[x + brushSize][z + brushSize] != 1) { // if haven't already found the surface in this column
                        if ((Math.pow(x, 2) + Math.pow(z, 2)) <= brushSizeSquared) { // if inside of the column...
                            if (this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y - 1, this.getTargetBlock().getZ() + z) != Material.AIR) { // if not a floating block (like one of Notch'world pools)
                                if (this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y + 1, this.getTargetBlock().getZ() + z) == Material.AIR) { // must start at surface... this prevents it filling stuff in if
                                    // you click in a wall and it starts out below surface.
                                    if (!this.allBlocks) { // if the override parameter has not been activated, go to the switch that filters out manmade stuff.

                                        switch (this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y, this.getTargetBlock().getZ() + z)) {
                                            case STONE:
                                            case ANDESITE:
                                            case DIORITE:
                                            case GRANITE:
                                            case GRASS:
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
                                                for (int d = 1; (d < this.depth + 1); d++) {
                                                    this.currentPerformer.perform(this.clampY(this.getTargetBlock().getX() + x, y + d, this.getTargetBlock().getZ() + z)); // fills down as many layers as you specify
                                                    // in parameters
                                                    memory[x + brushSize][z + brushSize] = 1; // stop it from checking any other blocks in this vertical 1x1 column.
                                                }
                                                surfaceFound = true;
                                                break;

                                            default:
                                                break;
                                        }
                                    } else {
                                        for (int d = 1; (d < this.depth + 1); d++) {
                                            this.currentPerformer.perform(this.clampY(this.getTargetBlock().getX() + x, y + d, this.getTargetBlock().getZ() + z)); // fills down as many layers as you specify in
                                            // parameters
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

    @Override
    protected final void arrow(final SnipeData v) {
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
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "Overlay Brush Parameters:");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " depth [number]  -- Depth of blocks to overlay from surface");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " mode  -- Toggle between overlaying natural blocks or all blocks.");
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
            } catch (NumberFormatException e) {
            }
        }

        if (params[0].startsWith("mode")) {
            this.allBlocks = !this.allBlocks;
            v.sendMessage(ChatColor.BLUE + "Will overlay on " + (this.allBlocks ? "all blocks" : "natural blocks") + ", " + this.depth + " blocks deep.");
            return;
        }

        v.sendMessage(ChatColor.RED + "Invalid parameter! Use " + ChatColor.LIGHT_PURPLE + "'/b " + triggerHandle + " info'" + ChatColor.RED + " to display valid parameters.");
        sendPerformerMessage(triggerHandle, v);
    }

    @Override
    public HashMap<String, List<String>> registerArguments(String brushHandle) {
        HashMap<String, List<String>> arguments = new HashMap<>();
        arguments.put(BRUSH_ARGUMENT_PREFIX + brushHandle, Lists.newArrayList("depth", "mode"));

        arguments.putAll(super.registerArguments(brushHandle));
        return arguments;
    }

    @Override
    public HashMap<String, List<String>> registerArgumentValues(String brushHandle) {
        HashMap<String, List<String>> argumentValues = new HashMap<>();
        
        argumentValues.put("depth", Lists.newArrayList("[number]"));

        argumentValues.putAll(super.registerArgumentValues(brushHandle));
        return argumentValues;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.overlay";
    }
}
