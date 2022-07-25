package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.util.Messages;

import java.util.ArrayList;

import org.bukkit.block.Block;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Shell_Brushes
 *
 * @author Piotr
 */
public class ShellSetBrush extends Brush {

    private static final int MAX_SIZE = 5000000;
    private Block block = null;

    /**
     *
     */
    public ShellSetBrush() {
        this.setName("Shell Set");
    }

    private boolean set(final Block bl, final SnipeData v) {
        if (this.block == null) {
            this.block = bl;
            return true;
        } else {
            if (!this.block.getWorld().getName().equals(bl.getWorld().getName())) {
                v.sendMessage(Messages.SELECTED_POINTS_DIFFERENT_WORLD);
                this.block = null;
                return true;
            }

            final int lowX = Math.min(this.block.getX(), bl.getX());
            final int lowY = Math.min(this.block.getY(), bl.getY());
            final int lowZ = Math.min(this.block.getZ(), bl.getZ());
            final int highX = Math.max(this.block.getX(), bl.getX());
            final int highY = Math.max(this.block.getY(), bl.getY());
            final int highZ = Math.max(this.block.getZ(), bl.getZ());

            int size = Math.abs(highX - lowX) * Math.abs(highZ - lowZ) * Math.abs(highY - lowY);
            if (size > MAX_SIZE) {
                v.sendMessage(Messages.SELECTION_SIZE_LIMIT);
            } else {
                final ArrayList<Block> blocks = new ArrayList<>((size / 2));
                for (int y = lowY; y <= highY; y++) {
                    for (int x = lowX; x <= highX; x++) {
                        for (int z = lowZ; z <= highZ; z++) {
                            if (this.getWorld().getBlockAt(x, y, z).getType() == v.getReplaceMaterial()) {
                            } else if (this.getWorld().getBlockAt(x + 1, y, z).getType() == v.getReplaceMaterial()) {
                            } else if (this.getWorld().getBlockAt(x - 1, y, z).getType() == v.getReplaceMaterial()) {
                            } else if (this.getWorld().getBlockAt(x, y, z + 1).getType() == v.getReplaceMaterial()) {
                            } else if (this.getWorld().getBlockAt(x, y, z - 1).getType() == v.getReplaceMaterial()) {
                            } else if (this.getWorld().getBlockAt(x, y + 1, z).getType() == v.getReplaceMaterial()) {
                            } else if (this.getWorld().getBlockAt(x, y - 1, z).getType() == v.getReplaceMaterial()) {
                            } else {
                                blocks.add(this.getWorld().getBlockAt(x, y, z));
                            }
                        }
                    }
                }

                final Undo undo = new Undo();
                for (final Block currentBlock : blocks) {
                    if (currentBlock.getType() != v.getVoxelMaterial()) {
                        undo.put(currentBlock);
                        currentBlock.setBlockData(v.getVoxelMaterial().createBlockData());
                    }
                }
                v.owner().storeUndo(undo);
                v.sendMessage(Messages.SHELL_BRUSH_COMPLETE);
            }

            this.block = null;
            return false;
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        if (this.set(this.getTargetBlock(), v)) {
            v.sendMessage(Messages.FIRST_POINT_SELECTED);
        }
    }

    @Override
    protected final void powder(final SnipeData v) {
        if (this.set(this.getLastBlock(), v)) {
            v.sendMessage(Messages.FIRST_POINT_SELECTED);
        }
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        vm.voxel();
        vm.replace();
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.shellset";
    }
}
