package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.util.Messages;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_CANYONATOR
 *
 * @author Voxel
 */
public class CanyonBrush extends Brush {

    private static final int SHIFT_LEVEL_MIN = 10;
    private static final int SHIFT_LEVEL_MAX = 60;
    private int yLevel = 10;

    /**
     *
     */
    public CanyonBrush() {
        this.setName("Canyon");
    }

    /**
     * @param chunk
     * @param undo
     */
    @SuppressWarnings("deprecation")
    protected final void canyon(final Chunk chunk, final Undo undo) {
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                int currentYLevel = this.yLevel;

                for (int y = 63; y < this.getMaxHeight(); y++) {
                    final Block block = chunk.getBlock(x, y, z);
                    final Block currentYLevelBlock = chunk.getBlock(x, currentYLevel, z);

                    undo.put(block);
                    undo.put(currentYLevelBlock);

                    currentYLevelBlock.setType(block.getType(), false);
                    block.setType(Material.AIR);

                    currentYLevel++;
                }

                final Block block = chunk.getBlock(x, this.getMinHeight(), z);
                undo.put(block);
                block.setType(Material.BEDROCK);

                for (int y = this.getMinHeight()+1; y < this.getMinHeight()+SHIFT_LEVEL_MIN; y++) {
                    final Block currentBlock = chunk.getBlock(x, y, z);
                    undo.put(currentBlock);
                    currentBlock.setType(Material.STONE);
                }
            }
        }
    }

    @Override
    protected void arrow(final SnipeData v) {
        final Undo undo = new Undo();

        canyon(getTargetBlock().getChunk(), undo);

        v.owner().storeUndo(undo);
    }

    @Override
    protected void powder(final SnipeData v) {
        final Undo undo = new Undo();

        Chunk targetChunk = getTargetBlock().getChunk();
        for (int x = targetChunk.getX() - 1; x <= targetChunk.getX() + 1; x++) {
            for (int z = targetChunk.getZ() - 1; z <= targetChunk.getZ() + 1; z++) {
                canyon(getWorld().getChunkAt(x, z), undo);
            }
        }

        v.owner().storeUndo(undo);
    }

    @Override
    public void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(Messages.CANYON_BRUSH_SHIFT_LEVEL.replace("%yLevel%", String.valueOf(this.yLevel)));
    }

    @Override
    public final void parseParameters(String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.CANYON_BRUSH_USAGE.replace("%triggerHandle%",triggerHandle));
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

                v.sendMessage(ChatColor.GREEN + "Land will be shifted to y-coordinate of " + this.yLevel);
            } catch (NumberFormatException e) {
                v.sendMessage(Messages.INPUT_NO_NUMBER);
            }
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public List<String> registerArguments() {
        return new ArrayList<>(Lists.newArrayList("y"));
    }

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

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.canyon";
    }
}
