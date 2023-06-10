package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.BiomeOperation;
import com.github.kevindagame.voxelsniper.biome.VoxelBiome;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#biome-brush">...</a>
 */
public class BiomeBrush extends AbstractBrush {

    private VoxelBiome selectedBiome = VoxelBiome.PLAINS();

    private void biome(final SnipeData v) {
        final int brushSize = v.getBrushSize();
        final double brushSizeSquared = Math.pow(brushSize, 2);

        for (int x = -brushSize; x <= brushSize; x++) {
            final double xSquared = Math.pow(x, 2);
            for (int z = -brushSize; z <= brushSize; z++) {
                if ((xSquared + Math.pow(z, 2)) <= brushSizeSquared) {
                    for ( int y = getMinHeight(); y <= getMaxHeight(); y++) {
                    var location = new BaseLocation(this.getWorld(), this.getTargetBlock().getX() + x, y, this.getTargetBlock().getZ() + z);
                    this.addOperation(new BiomeOperation(location, getWorld().getBiome(location), this.selectedBiome));

                    }
                }
            }
        }


    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.biome(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.biome(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        vm.custom(Messages.SELECTED_BIOME_TYPE.replace("%selectedBiome%", this.selectedBiome.toString()));
    }

    @Override
    public final void parseParameters(@NotNull final String triggerHandle, final String[] params, @NotNull final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.BIOME_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        var biome = VoxelBiome.getBiome(params[0].toLowerCase());
        if (biome == null) {
            v.sendMessage(Messages.BIOME_DOES_NOT_EXIST);
            return;
        }
        this.selectedBiome = biome;
        v.sendMessage(Messages.SELECTED_BIOME_TYPE.replace("%selectedBiome%", this.selectedBiome.getKey()));
    }

    @NotNull
    @Override
    public List<String> registerArguments() {

        return VoxelBiome.getBiomes().stream().map(VoxelBiome::toString).collect(Collectors.toList());
    }
}
