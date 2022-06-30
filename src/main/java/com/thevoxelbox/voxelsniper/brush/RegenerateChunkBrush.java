package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.util.Messages;

import org.bukkit.Chunk;

/**
 * Regenerates the target chunk.
 *
 * @author Mick
 */
public class RegenerateChunkBrush extends Brush {

    /**
     *
     */
    public RegenerateChunkBrush() {
        this.setName("Chunk Generator 40k");
    }

    private void generateChunk(final SnipeData v) {
        final Chunk chunk = this.getTargetBlock().getChunk();
        final Undo undo = new Undo();

        for (int z = CHUNK_SIZE; z >= 0; z--) {
            for (int x = CHUNK_SIZE; x >= 0; x--) {
                for (int y = this.getMaxHeight(); y >= this.getMinHeight(); y--) {
                    undo.put(chunk.getBlock(x, y, z));
                }
            }
        }
        v.owner().storeUndo(undo);

        v.sendMessage(("Generate that chunk! " + "%chunk.getX%" + " " + "%chunk.getZ%").replace("%chunk.getX%", Integer.toString(chunk.getX())).replace("%chunk.getZ%", Integer.toString(chunk.getZ())));
        this.getWorld().regenerateChunk(chunk.getX(), chunk.getZ());
        this.getWorld().refreshChunk(chunk.getX(), chunk.getZ());
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
