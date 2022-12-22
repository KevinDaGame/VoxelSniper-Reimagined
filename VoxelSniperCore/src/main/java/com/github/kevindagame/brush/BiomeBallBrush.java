package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.BrushOperation.BiomeOperation;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.biome.VoxelBiome;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#biome-ball-brush">...</a>
 */
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
        this.operations = Shapes.ball(this.getTargetBlock().getLocation(), v.getBrushSize(), true).stream().map(location -> new BiomeOperation(location, getWorld().getBiome(location), this.selectedBiome)).collect(toList());

    }
    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.BIOMEBALL_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
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

        return VoxelBiome.BIOMES.values().stream().map(VoxelBiome::getKey).collect(toList());
    }
}
