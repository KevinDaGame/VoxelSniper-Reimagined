package com.github.kevindagame.brush.multiBlock;

import com.github.kevindagame.brush.AbstractBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.BlockOperation;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.chunk.IChunk;
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#canyon-brush">...</a>
 *
 * @author Voxel
 */
public class CanyonBrush extends AbstractBrush {

    private static final int SHIFT_LEVEL_MIN = -60;
    private static final int SHIFT_LEVEL_MAX = 60;
    private int yLevel = -53;

    /**
     * @param chunk
     */
    protected final void canyon(final IChunk chunk) {
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                int currentYLevel = this.yLevel;

                //TODO I don't think 63 is correct
                for (int y = 63; y < this.getMaxHeight(); y++) {
                    final IBlock block = chunk.getBlock(x, y, z);
                    final IBlock currentYLevelBlock = chunk.getBlock(x, currentYLevel, z);
                    addOperation(new BlockOperation(currentYLevelBlock.getLocation(), currentYLevelBlock.getBlockData(), block.getBlockData()));
                    currentYLevel++;
                }
                for (int y = currentYLevel; y < this.getMaxHeight(); y++) {
                    var block = chunk.getBlock(x, y, z);
                    addOperation(new BlockOperation(block.getLocation(), block.getBlockData(), VoxelMaterialType.AIR.createBlockData()));
                }
                var block = chunk.getBlock(x, this.getMinHeight(), z);
                addOperation(new BlockOperation(block.getLocation(), block.getBlockData(), VoxelMaterialType.BEDROCK.createBlockData()));
            }
        }
    }

    @Override
    protected void arrow(final SnipeData v) {
        canyon(getTargetBlock().getChunk());
    }

    @Override
    protected void powder(final SnipeData v) {
        IChunk targetChunk = getTargetBlock().getChunk();
        for (int x = targetChunk.getX() - 1; x <= targetChunk.getX() + 1; x++) {
            for (int z = targetChunk.getZ() - 1; z <= targetChunk.getZ() + 1; z++) {
                canyon(getWorld().getChunkAtLocation(x, z));
            }
        }
    }

    @Override
    public void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(Messages.CANYON_BRUSH_SHIFT_LEVEL.replace("%yLevel%", String.valueOf(this.yLevel)));
    }

    @Override
    public final void parseParameters(@NotNull String triggerHandle, final String[] params, @NotNull final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.CANYON_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        if (params[0].startsWith("y")) {
            try {
                int yLevel = Integer.parseInt(params[1]);

                if (yLevel < SHIFT_LEVEL_MIN) {
                    yLevel = SHIFT_LEVEL_MIN;
                } else if (yLevel > SHIFT_LEVEL_MAX) {
                    yLevel = SHIFT_LEVEL_MAX;
                }

                this.yLevel = yLevel;

                v.sendMessage(Messages.LAND_WILL_BE_SHIFTED_TO_Y.replace("%yLevel%", String.valueOf(this.yLevel)));
            } catch (NumberFormatException e) {
                v.sendMessage(Messages.INPUT_NO_NUMBER);
            }
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @NotNull
    @Override
    public List<String> registerArguments() {
        return new ArrayList<>(Lists.newArrayList("y"));
    }

    @NotNull
    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();

        argumentValues.put("y", Lists.newArrayList("[number]"));

        return argumentValues;
    }

    protected final int getYLevel() {
        return yLevel;
    }

    protected final void setYLevel(int yLevel) {
        this.yLevel = yLevel;
    }
}
