package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;

/**
 * @author GavJenks
 */
public class FlatOceanBrush extends Brush {

    private static final int DEFAULT_WATER_LEVEL = 29;
    private static final int DEFAULT_FLOOR_LEVEL = 8;
    private int waterLevel = DEFAULT_WATER_LEVEL;
    private int floorLevel = DEFAULT_FLOOR_LEVEL;

    /**
     *
     */
    public FlatOceanBrush() {
        this.setName("FlatOcean");
    }

    @SuppressWarnings("deprecation")
    private void flatOcean(final Chunk chunk) {
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                // chunk.getWorld() == getWorld()
                for (int y = this.getMinHeight(); y < this.getMaxHeight(); y++) {
                    if (y <= this.floorLevel) {
                        chunk.getBlock(x, y, z).setType(Material.DIRT, false);
                    } else if (y <= this.waterLevel) {
                        chunk.getBlock(x, y, z).setType(Material.WATER, false);
                    } else {
                        chunk.getBlock(x, y, z).setType(Material.AIR, false);
                    }
                }
            }
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.flatOcean(this.getWorld().getChunkAt(this.getTargetBlock()));
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.flatOcean(this.getWorld().getChunkAt(this.getTargetBlock()));
        this.flatOcean(this.getWorld().getChunkAt(this.clampY(this.getTargetBlock().getX() + CHUNK_SIZE, 1, this.getTargetBlock().getZ())));
        this.flatOcean(this.getWorld().getChunkAt(this.clampY(this.getTargetBlock().getX() + CHUNK_SIZE, 1, this.getTargetBlock().getZ() + CHUNK_SIZE)));
        this.flatOcean(this.getWorld().getChunkAt(this.clampY(this.getTargetBlock().getX(), 1, this.getTargetBlock().getZ() + CHUNK_SIZE)));
        this.flatOcean(this.getWorld().getChunkAt(this.clampY(this.getTargetBlock().getX() - CHUNK_SIZE, 1, this.getTargetBlock().getZ() + CHUNK_SIZE)));
        this.flatOcean(this.getWorld().getChunkAt(this.clampY(this.getTargetBlock().getX() - CHUNK_SIZE, 1, this.getTargetBlock().getZ())));
        this.flatOcean(this.getWorld().getChunkAt(this.clampY(this.getTargetBlock().getX() - CHUNK_SIZE, 1, this.getTargetBlock().getZ() - CHUNK_SIZE)));
        this.flatOcean(this.getWorld().getChunkAt(this.clampY(this.getTargetBlock().getX(), 1, this.getTargetBlock().getZ() - CHUNK_SIZE)));
        this.flatOcean(this.getWorld().getChunkAt(this.clampY(this.getTargetBlock().getX() + CHUNK_SIZE, 1, this.getTargetBlock().getZ() - CHUNK_SIZE)));
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(ChatColor.RED + "THIS BRUSH DOES NOT UNDO.");
        vm.custom(ChatColor.GREEN + "Water level set to " + this.waterLevel);
        vm.custom(ChatColor.GREEN + "Ocean floor level set to " + this.floorLevel);
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "Entity Brush Parameters:");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " water [number]  -- Set the y-level the water will rise to. (default: 29)");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " floor [number]  -- Set the y-level the ocean floor will rise to. (default: 8)");
            v.sendMessage(ChatColor.RED + "BEWARE! THIS BRUSH DOES NOT UNDO.");
            return;
        }

        if (params[0].equalsIgnoreCase("water")) {
            int newWaterLevel = Integer.parseInt(params[1]);

            if (newWaterLevel < this.floorLevel) {
                newWaterLevel = this.floorLevel + 1;
            }

            this.waterLevel = newWaterLevel;
            v.sendMessage(ChatColor.GREEN + "Water level set to " + this.waterLevel);
            return;
        }

        if (params[0].equalsIgnoreCase("floor")) {
            int newFloorLevel = Integer.parseInt(params[1]);

            if (newFloorLevel > this.waterLevel) {
                newFloorLevel = this.waterLevel - 1;
                if (newFloorLevel == 0) {
                    newFloorLevel = 1;
                    this.waterLevel = 2;
                }
            }

            this.floorLevel = newFloorLevel;
            v.sendMessage(ChatColor.GREEN + "Ocean floor level set to " + this.floorLevel);
            return;
        }

        v.sendMessage(ChatColor.RED + "Invalid parameter! Use " + ChatColor.LIGHT_PURPLE + "'/b " + triggerHandle + " info'" + ChatColor.RED + " to display valid parameters.");
    }

    @Override
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();
        
        arguments.addAll(Lists.newArrayList("water", "floor"));
        
        return arguments;
    }

    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();
        
        argumentValues.put("water", Lists.newArrayList("[number]"));
        argumentValues.put("floor", Lists.newArrayList("[number]"));
        
        argumentValues.putAll(super.registerArgumentValues());
        return argumentValues;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.flatocean";
    }
}
