package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.brushOperation.CustomOperation;
import com.github.kevindagame.util.brushOperation.CustomOperationContext;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.chunk.IChunk;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

/**
 * Regenerates the target chunk.
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#regenerate-chunk-brush">...</a>
 *
 * @author Mick
 */
public class RegenerateChunkBrush extends CustomBrush {
    public RegenerateChunkBrush() {
        this.setName("Chunk Generator 40k");
    }

    private void generateChunk(final SnipeData v) {
        for (int z = CHUNK_SIZE; z >= 0; z--) {
            for (int x = CHUNK_SIZE; x >= 0; x--) {
                for (int y = this.getMaxHeight(); y >= this.getMinHeight(); y--) {
                    addOperation(new CustomOperation(new BaseLocation(this.getWorld(), x, y, z), this, v, CustomOperationContext.OTHER));
                }
            }
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.generateChunk(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.generateChunk(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.brushMessage(Messages.REGENERATE_CHUNK_MESSAGE);
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.regeneratechunk";
    }

    @Override
    public boolean perform(@NotNull ImmutableList<CustomOperation> operations, @NotNull SnipeData snipeData, @NotNull Undo undo) {
        final IChunk chunk = this.getTargetBlock().getChunk();
        //check if no operation has been cancelled by comparing the size to the amount of loops
        //TODO test this
        if (operations.size() != CHUNK_SIZE * CHUNK_SIZE * (this.getMaxHeight() - this.getMinHeight())) {
            return false;
        }
        for (int z = CHUNK_SIZE; z >= 0; z--) {
            for (int x = CHUNK_SIZE; x >= 0; x--) {
                for (int y = this.getMaxHeight(); y >= this.getMinHeight(); y--) {
                    undo.put(chunk.getBlock(x, y, z));
                }
            }
        }
        snipeData.sendMessage(Messages.GENERATED_CHUNK.replace("%chunk.getX%", Integer.toString(chunk.getX())).replace("%chunk.getZ%", Integer.toString(chunk.getZ())));
        this.getWorld().regenerateChunk(chunk.getX(), chunk.getZ());
        this.getWorld().refreshChunk(chunk.getX(), chunk.getZ());
        return true;
    }
}
