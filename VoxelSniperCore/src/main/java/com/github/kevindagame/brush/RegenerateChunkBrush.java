package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.chunk.IChunk;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;

/**
 * Regenerates the target chunk.
 *
 * @author Mick
 */
public class RegenerateChunkBrush extends AbstractBrush {
    private Undo tempUndo;
    /**
     *
     */
    public RegenerateChunkBrush() {
        this.setName("Chunk Generator 40k");
    }

    private void generateChunk(final SnipeData v) {
        for (int z = CHUNK_SIZE; z >= 0; z--) {
            for (int x = CHUNK_SIZE; x >= 0; x--) {
                for (int y = this.getMaxHeight(); y >= this.getMinHeight(); y--) {
                    this.positions.add(new VoxelLocation(this.getWorld(), x, y, z));
                }
            }
        }
    }

    @Override
    protected boolean actPerform(SnipeData v) {
        final IChunk chunk = this.getTargetBlock().getChunk();
        Undo undo = new Undo();
        for (int z = CHUNK_SIZE; z >= 0; z--) {
            for (int x = CHUNK_SIZE; x >= 0; x--) {
                for (int y = this.getMaxHeight(); y >= this.getMinHeight(); y--) {
                    undo.put(chunk.getBlock(x, y, z));
                }
            }
        }
        v.owner().storeUndo(undo);
        v.sendMessage(Messages.GENERATED_CHUNK.replace("%chunk.getX%", Integer.toString(chunk.getX())).replace("%chunk.getZ%", Integer.toString(chunk.getZ())));
        this.getWorld().regenerateChunk(chunk.getX(), chunk.getZ());
        this.getWorld().refreshChunk(chunk.getX(), chunk.getZ());
        return true;
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
}
