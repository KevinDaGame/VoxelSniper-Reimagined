package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.brush.perform.PerformerBrush;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.VoxelList;
import com.thevoxelbox.voxelsniper.voxelsniper.material.BukkitMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Underlay_Brush
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
                            final IMaterial currentBlock = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y, this.getTargetBlock().getZ() + z);
                            if (this.isOverrideableMaterial(v.getVoxelList(), currentBlock)) {
                                for (int d = 0; (d < this.depth); d++) {
                                    if (this.clampY(this.getTargetBlock().getX() + x, y + d, this.getTargetBlock().getZ() + z).getType() != new BukkitMaterial( Material.AIR)) {
                                        this.currentPerformer.perform(this.clampY(this.getTargetBlock().getX() + x, y + d, this.getTargetBlock().getZ() + z)); // fills down as many layers as you specify in
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

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    private void underlay2(final SnipeData v) {
        final int[][] memory = new int[v.getBrushSize() * 2 + 1][v.getBrushSize() * 2 + 1];
        final double brushSizeSquared = Math.pow(v.getBrushSize() + 0.5, 2);

        for (int z = v.getBrushSize(); z >= -v.getBrushSize(); z--) {
            for (int x = v.getBrushSize(); x >= -v.getBrushSize(); x--) {
                for (int y = this.getTargetBlock().getY(); y < this.getTargetBlock().getY() + this.depth; y++) { // start scanning from the height you clicked at
                    if (memory[x + v.getBrushSize()][z + v.getBrushSize()] != 1) { // if haven't already found the surface in this column
                        if ((Math.pow(x, 2) + Math.pow(z, 2)) <= brushSizeSquared) { // if inside of the column...
                            final IMaterial currentBlock = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y, this.getTargetBlock().getZ() + z);
                            if (this.isOverrideableMaterial(v.getVoxelList(), currentBlock)) {
                                for (int d = -1; (d < this.depth - 1); d++) {
                                    this.currentPerformer.perform(this.clampY(this.getTargetBlock().getX() + x, y - d, this.getTargetBlock().getZ() + z)); // fills down as many layers as you specify in
                                    // parameters
                                    memory[x + v.getBrushSize()][z + v.getBrushSize()] = 1; // stop it from checking any other blocks in this vertical 1x1 column.
                                }
                            }
                        }
                    }
                }
            }
        }

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    private boolean isOverrideableMaterial(VoxelList list, IMaterial material) {
        if (this.useVoxelList) {
            return list.contains(material);
        }

        if (allBlocks && !(material == new BukkitMaterial( Material.AIR))) {
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
    public final void arrow(final SnipeData v) {
        this.underlay(v);
    }

    @Override
    public final void powder(final SnipeData v) {
        this.underlay2(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "Underlay (Reverse Overlay) Brush Parameters:");
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
        return "voxelsniper.brush.underlay";
    }
}
