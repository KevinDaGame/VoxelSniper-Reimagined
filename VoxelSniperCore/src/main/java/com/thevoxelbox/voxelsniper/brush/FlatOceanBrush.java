package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.chunk.IChunk;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private void flatOcean(final IChunk chunk) {
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                // chunk.getWorld() == getWorld()
                for (int y = this.getMinHeight(); y < this.getMaxHeight(); y++) {
                    if (y <= this.floorLevel) {
                        chunk.getBlock(x, y, z).setMaterial(VoxelMaterial.DIRT, false);
                    } else if (y <= this.waterLevel) {
                        chunk.getBlock(x, y, z).setMaterial(VoxelMaterial.WATER, false);
                    } else {
                        chunk.getBlock(x, y, z).setMaterial(VoxelMaterial.AIR, false);
                    }
                }
            }
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.flatOcean(this.getWorld().getChunkAtLocation(this.getTargetBlock().getLocation()));
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.flatOcean(this.getWorld().getChunkAtLocation(this.getTargetBlock().getLocation()));
        this.flatOcean(this.getWorld().getChunkAtLocation(this.clampY(this.getTargetBlock().getX() + CHUNK_SIZE, 1, this.getTargetBlock().getZ()).getLocation()));
        this.flatOcean(this.getWorld().getChunkAtLocation(this.clampY(this.getTargetBlock().getX() + CHUNK_SIZE, 1, this.getTargetBlock().getZ() + CHUNK_SIZE).getLocation()));
        this.flatOcean(this.getWorld().getChunkAtLocation(this.clampY(this.getTargetBlock().getX(), 1, this.getTargetBlock().getZ() + CHUNK_SIZE).getLocation()));
        this.flatOcean(this.getWorld().getChunkAtLocation(this.clampY(this.getTargetBlock().getX() - CHUNK_SIZE, 1, this.getTargetBlock().getZ() + CHUNK_SIZE).getLocation()));
        this.flatOcean(this.getWorld().getChunkAtLocation(this.clampY(this.getTargetBlock().getX() - CHUNK_SIZE, 1, this.getTargetBlock().getZ()).getLocation()));
        this.flatOcean(this.getWorld().getChunkAtLocation(this.clampY(this.getTargetBlock().getX() - CHUNK_SIZE, 1, this.getTargetBlock().getZ() - CHUNK_SIZE).getLocation()));
        this.flatOcean(this.getWorld().getChunkAtLocation(this.clampY(this.getTargetBlock().getX(), 1, this.getTargetBlock().getZ() - CHUNK_SIZE).getLocation()));
        this.flatOcean(this.getWorld().getChunkAtLocation(this.clampY(this.getTargetBlock().getX() + CHUNK_SIZE, 1, this.getTargetBlock().getZ() - CHUNK_SIZE).getLocation()));
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(Messages.BRUSH_NO_UNDO);
        vm.custom(Messages.WATER_LEVEL_SET.replace("%waterLevel%", String.valueOf(waterLevel)));
        vm.custom(Messages.OCEAN_FLOOR_LEVEL_SET.replace("%floorLevel%", String.valueOf(this.floorLevel)));
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.FLAT_OCEAN_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            v.sendMessage(Messages.BRUSH_NO_UNDO);
            return;
        }

        if (params[0].equalsIgnoreCase("water")) {
            int newWaterLevel = Integer.parseInt(params[1]);

            if (newWaterLevel < this.floorLevel) {
                newWaterLevel = this.floorLevel + 1;
            }

            this.waterLevel = newWaterLevel;
            v.sendMessage(Messages.WATER_LEVEL_SET.replace("%waterLevel%", String.valueOf(waterLevel)));
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
            v.sendMessage(Messages.OCEAN_FLOOR_LEVEL_SET.replace("%floorLevel%", String.valueOf(floorLevel)));
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("water", "floor"));
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
