package com.github.kevindagame.brush.MultiBlock;

import com.github.kevindagame.brush.MultiBlock.MultiBlockBrush;
import com.github.kevindagame.util.BlockWrapper;
import com.google.common.collect.Lists;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.chunk.IChunk;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author GavJenks
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#flatocean-brush">...</a>
 */
public class FlatOceanBrush extends MultiBlockBrush {

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

    private void flatOcean(final IChunk chunk) {
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                // chunk.getWorld() == getWorld()
                for (int y = this.getMinHeight(); y < this.getMaxHeight(); y++) {
                    var block = chunk.getBlock(x, y, z);
                    positions.add(block.getLocation());
                    var blockWrapper = new BlockWrapper(block);
                    if (y <= this.floorLevel) {
                        blockWrapper.setBlockData(VoxelMaterial.DIRT.getIMaterial().createBlockData());
                    } else if (y <= this.waterLevel) {
                        blockWrapper.setBlockData(VoxelMaterial.WATER.getIMaterial().createBlockData());
                    } else {
                        blockWrapper.setBlockData(VoxelMaterial.AIR.getIMaterial().createBlockData());
                    }
                    operations.add(blockWrapper);
                }
            }
        }
    }

    @Override
    protected final void doArrow(final SnipeData v) {
        this.flatOcean(this.getWorld().getChunkAtLocation(this.getTargetBlock().getLocation()));
    }

    @Override
    protected final void doPowder(final SnipeData v) {
        this.flatOcean(this.getWorld().getChunkAtLocation(this.getTargetBlock().getLocation()));
        this.flatOcean(this.getWorld().getChunkAtLocation(getWorld().getBlock(this.getTargetBlock().getX() + CHUNK_SIZE, 1, this.getTargetBlock().getZ()).getLocation()));
        this.flatOcean(this.getWorld().getChunkAtLocation(getWorld().getBlock(this.getTargetBlock().getX() + CHUNK_SIZE, 1, this.getTargetBlock().getZ() + CHUNK_SIZE).getLocation()));
        this.flatOcean(this.getWorld().getChunkAtLocation(getWorld().getBlock(this.getTargetBlock().getX(), 1, this.getTargetBlock().getZ() + CHUNK_SIZE).getLocation()));
        this.flatOcean(this.getWorld().getChunkAtLocation(getWorld().getBlock(this.getTargetBlock().getX() - CHUNK_SIZE, 1, this.getTargetBlock().getZ() + CHUNK_SIZE).getLocation()));
        this.flatOcean(this.getWorld().getChunkAtLocation(getWorld().getBlock(this.getTargetBlock().getX() - CHUNK_SIZE, 1, this.getTargetBlock().getZ()).getLocation()));
        this.flatOcean(this.getWorld().getChunkAtLocation(getWorld().getBlock(this.getTargetBlock().getX() - CHUNK_SIZE, 1, this.getTargetBlock().getZ() - CHUNK_SIZE).getLocation()));
        this.flatOcean(this.getWorld().getChunkAtLocation(getWorld().getBlock(this.getTargetBlock().getX(), 1, this.getTargetBlock().getZ() - CHUNK_SIZE).getLocation()));
        this.flatOcean(this.getWorld().getChunkAtLocation(getWorld().getBlock(this.getTargetBlock().getX() + CHUNK_SIZE, 1, this.getTargetBlock().getZ() - CHUNK_SIZE).getLocation()));
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
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
