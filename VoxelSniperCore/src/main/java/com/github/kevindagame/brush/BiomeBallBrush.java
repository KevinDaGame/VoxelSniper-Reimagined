package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.biome.VoxelBiome;
import com.github.kevindagame.voxelsniper.block.IBlock;

import java.util.List;
import java.util.stream.Collectors;

public class BiomeBallBrush extends AbstractBrush {

    private VoxelBiome selectedBiome = VoxelBiome.PLAINS;

    @Override
    public void info(VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        vm.custom(Messages.SELECTED_BIOME_TYPE.replace("%selectedBiome%", this.selectedBiome.key()));
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.biomeball";
    }

    @Override
    public void arrow(SnipeData v) {
        this.biome(v);
    }

    @Override
    protected void powder(SnipeData v) {
        this.biome(v);
    }

    private void biome(final SnipeData v) {
        final int brushSize = v.getBrushSize();
        final double brushSizeSquared = Math.pow(brushSize, 2);
        var targetBlock = this.getTargetBlock();
        int blockPositionX = targetBlock.getX();
        int blockPositionY = targetBlock.getY();
        int blockPositionZ = targetBlock.getZ();
        for (int z = 1; z <= brushSize; z++) {
            final double zSquared = Math.pow(z, 2);
            this.getWorld().setBiome(blockPositionX + z, blockPositionY, blockPositionZ, this.selectedBiome);
            this.getWorld().setBiome(blockPositionX + z, blockPositionY, blockPositionZ, this.selectedBiome);
            this.getWorld().setBiome(blockPositionX - z, blockPositionY, blockPositionZ, this.selectedBiome);
            this.getWorld().setBiome(blockPositionX, blockPositionY + z, blockPositionZ, this.selectedBiome);
            this.getWorld().setBiome(blockPositionX, blockPositionY - z, blockPositionZ, this.selectedBiome);
            this.getWorld().setBiome(blockPositionX, blockPositionY, blockPositionZ + z, this.selectedBiome);
            this.getWorld().setBiome(blockPositionX, blockPositionY, blockPositionZ - z, this.selectedBiome);

            for (int x = 1; x <= brushSize; x++) {
                final double xSquared = Math.pow(x, 2);

                if (zSquared + xSquared <= brushSizeSquared) {
                    this.getWorld().setBiome(blockPositionX + z, blockPositionY, blockPositionZ + x, this.selectedBiome);
                    this.getWorld().setBiome(blockPositionX + z, blockPositionY, blockPositionZ - x, this.selectedBiome);
                    this.getWorld().setBiome(blockPositionX - z, blockPositionY, blockPositionZ + x, this.selectedBiome);
                    this.getWorld().setBiome(blockPositionX - z, blockPositionY, blockPositionZ - x, this.selectedBiome);
                    this.getWorld().setBiome(blockPositionX + z, blockPositionY + x, blockPositionZ, this.selectedBiome);
                    this.getWorld().setBiome(blockPositionX + z, blockPositionY - x, blockPositionZ, this.selectedBiome);
                    this.getWorld().setBiome(blockPositionX - z, blockPositionY + x, blockPositionZ, this.selectedBiome);
                    this.getWorld().setBiome(blockPositionX - z, blockPositionY - x, blockPositionZ, this.selectedBiome);
                    this.getWorld().setBiome(blockPositionX, blockPositionY + z, blockPositionZ + x, this.selectedBiome);
                    this.getWorld().setBiome(blockPositionX, blockPositionY + z, blockPositionZ - x, this.selectedBiome);
                    this.getWorld().setBiome(blockPositionX, blockPositionY - z, blockPositionZ + x, this.selectedBiome);
                    this.getWorld().setBiome(blockPositionX, blockPositionY - z, blockPositionZ - x, this.selectedBiome);
                }

                for (int y = 1; y <= brushSize; y++) {
                    if ((xSquared + Math.pow(y, 2) + zSquared) <= brushSizeSquared) {
                        this.getWorld().setBiome(blockPositionX + x, blockPositionY + y, blockPositionZ + z, this.selectedBiome);
                        this.getWorld().setBiome(blockPositionX + x, blockPositionY + y, blockPositionZ - z, this.selectedBiome);
                        this.getWorld().setBiome(blockPositionX - x, blockPositionY + y, blockPositionZ + z, this.selectedBiome);
                        this.getWorld().setBiome(blockPositionX - x, blockPositionY + y, blockPositionZ - z, this.selectedBiome);
                        this.getWorld().setBiome(blockPositionX + x, blockPositionY - y, blockPositionZ + z, this.selectedBiome);
                        this.getWorld().setBiome(blockPositionX + x, blockPositionY - y, blockPositionZ - z, this.selectedBiome);
                        this.getWorld().setBiome(blockPositionX - x, blockPositionY - y, blockPositionZ + z, this.selectedBiome);
                        this.getWorld().setBiome(blockPositionX - x, blockPositionY - y, blockPositionZ - z, this.selectedBiome);
                    }
                }
            }
        }

        //refresh chunks
        final IBlock block1 = this.getWorld().getBlock(this.getTargetBlock().getX() - brushSize, 0, this.getTargetBlock().getZ() - brushSize);
        final IBlock block2 = this.getWorld().getBlock(this.getTargetBlock().getX() + brushSize, 0, this.getTargetBlock().getZ() + brushSize);

        final int lowChunkX = (block1.getX() <= block2.getX()) ? block1.getChunk().getX() : block2.getChunk().getX();
        final int lowChunkZ = (block1.getZ() <= block2.getZ()) ? block1.getChunk().getZ() : block2.getChunk().getZ();
        final int highChunkX = (block1.getX() >= block2.getX()) ? block1.getChunk().getX() : block2.getChunk().getX();
        final int highChunkZ = (block1.getZ() >= block2.getZ()) ? block1.getChunk().getZ() : block2.getChunk().getZ();

        for (int x = lowChunkX; x <= highChunkX; x++) {
            for (int z = lowChunkZ; z <= highChunkZ; z++) {
                this.getWorld().refreshChunk(x, z);
            }
        }
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.BIOME_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        try {
            this.selectedBiome = VoxelBiome.getBiome(params[0].toLowerCase());
            v.sendMessage(Messages.SELECTED_BIOME_TYPE.replace("%selectedBiome%", this.selectedBiome.key()));
        } catch (IllegalArgumentException e) {
            v.sendMessage(Messages.BIOME_DOES_NOT_EXIST);
        }
    }

    @Override
    public List<String> registerArguments() {

        return VoxelBiome.BIOMES.values().stream().map(VoxelBiome::getKey).collect(Collectors.toList());
    }
}
