package com.github.kevindagame.brush.multiBlock;

import com.github.kevindagame.brush.AbstractBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Utils;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.BlockOperation;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.world.IWorld;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushe">...</a>s#ocean-brush
 *
 * @author Voxel
 */
public class OceanBrush extends AbstractBrush {

    private static final int WATER_LEVEL_DEFAULT = 62; // y=63 -- we are using array indices here
    private static final int WATER_LEVEL_MIN = 12;
    private static final int LOW_CUT_LEVEL = 12;
    @Deprecated()
    /**
     * This list is incomplete and will be removed in the future.
     */
    private static final List<VoxelMaterial> EXCLUDED_MATERIALS = List.of(
            VoxelMaterial.AIR(),
            VoxelMaterial.getMaterial("cave_air"),
            VoxelMaterial.getMaterial("void_air"),
            VoxelMaterial.getMaterial("oak_sapling"),
            VoxelMaterial.getMaterial("acacia_sapling"),
            VoxelMaterial.getMaterial("birch_sapling"),
            VoxelMaterial.getMaterial("dark_oak_sapling"),
            VoxelMaterial.getMaterial("jungle_sapling"),
            VoxelMaterial.getMaterial("spruce_sapling"),
            VoxelMaterial.getMaterial("oak_leaves"),
            VoxelMaterial.getMaterial("acacia_leaves"),
            VoxelMaterial.getMaterial("birch_leaves"),
            VoxelMaterial.getMaterial("dark_oak_leaves"),
            VoxelMaterial.getMaterial("jungle_leaves"),
            VoxelMaterial.getMaterial("spruce_leaves"),
            VoxelMaterial.getMaterial("oak_log"),
            VoxelMaterial.getMaterial("acacia_log"),
            VoxelMaterial.getMaterial("birch_log"),
            VoxelMaterial.getMaterial("dark_oak_log"),
            VoxelMaterial.getMaterial("jungle_log"),
            VoxelMaterial.getMaterial("spruce_log"),
            VoxelMaterial.getMaterial("oak_wood"),
            VoxelMaterial.getMaterial("acacia_wood"),
            VoxelMaterial.getMaterial("birch_wood"),
            VoxelMaterial.getMaterial("dark_oak_wood"),
            VoxelMaterial.getMaterial("jungle_wood"),
            VoxelMaterial.getMaterial("spruce_wood"),
            VoxelMaterial.getMaterial("water"),
            VoxelMaterial.getMaterial("lava"),
            VoxelMaterial.getMaterial("dandelion"),
            VoxelMaterial.getMaterial("poppy"),
            VoxelMaterial.getMaterial("blue_orchid"),
            VoxelMaterial.getMaterial("allium"),
            VoxelMaterial.getMaterial("azure_bluet"),
            VoxelMaterial.getMaterial("red_tulip"),
            VoxelMaterial.getMaterial("orange_tulip"),
            VoxelMaterial.getMaterial("white_tulip"),
            VoxelMaterial.getMaterial("pink_tulip"),
            VoxelMaterial.getMaterial("oxeye_daisy"),
            VoxelMaterial.getMaterial("cornflower"),
            VoxelMaterial.getMaterial("lily_of_the_valley"),
            VoxelMaterial.getMaterial("wither_rose"),
            VoxelMaterial.getMaterial("red_mushroom"),
            VoxelMaterial.getMaterial("brown_mushroom"),
            VoxelMaterial.getMaterial("melon"),
            VoxelMaterial.getMaterial("melon_stem"),
            VoxelMaterial.getMaterial("pumpkin"),
            VoxelMaterial.getMaterial("pumpkin_stem"),
            VoxelMaterial.getMaterial("cocoa"),
            VoxelMaterial.getMaterial("snow"),
            VoxelMaterial.getMaterial("ice"),
            VoxelMaterial.getMaterial("sugar_cane"),
            VoxelMaterial.getMaterial("tall_grass"),
            VoxelMaterial.getMaterial("snow")
    );

    private int waterLevel = WATER_LEVEL_DEFAULT;
    private boolean coverFloor = false;


    private int getHeight(final int bx, final int bz) {
        for (int y = this.getWorld().getHighestBlockYAt(bx, bz); y > this.getMinHeight(); y--) {
            final VoxelMaterial material = getWorld().getBlock(bx, y, bz).getMaterial();
            if (!EXCLUDED_MATERIALS.contains(material)) {
                return y;
            }
        }
        return 0;
    }

    /**
     * @param v
     * @param undo
     */
    protected final void oceanator(final SnipeData v, final Undo undo) {
        final IWorld world = this.getWorld();

        final int minX = (int) Math.floor((this.getTargetBlock().getX() - v.getBrushSize()));
        final int minZ = (int) Math.floor((this.getTargetBlock().getZ() - v.getBrushSize()));
        final int maxX = (int) Math.floor((this.getTargetBlock().getX() + v.getBrushSize()));
        final int maxZ = (int) Math.floor((this.getTargetBlock().getZ() + v.getBrushSize()));

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                final int currentHeight = getHeight(x, z);
                final int wLevelDiff = currentHeight - (this.waterLevel - 1);
                final int newSeaFloorLevel = Math.max((this.waterLevel - wLevelDiff), LOW_CUT_LEVEL);

                final int highestY = this.getWorld().getHighestBlockYAt(x, z);

                // go down from highest Y block down to new sea floor
                for (int y = highestY; y > newSeaFloorLevel; y--) {
                    final IBlock block = world.getBlock(x, y, z);
                    if (!block.getMaterial().isAir()) {
                        addOperation(new BlockOperation(block.getLocation(), block.getBlockData(), VoxelMaterial.AIR().createBlockData()));
                    }
                }

                // go down from water level to new sea level
                for (int y = this.waterLevel; y > newSeaFloorLevel; y--) {
                    final IBlock block = world.getBlock(x, y, z);
                    if (!block.getMaterial().equals(VoxelMaterial.WATER())) {
                        addOperation(new BlockOperation(block.getLocation(), block.getBlockData(), VoxelMaterial.WATER().createBlockData()));
                    }
                }

                // cover the sea floor of required
                if (this.coverFloor && (newSeaFloorLevel < this.waterLevel)) {
                    IBlock block = world.getBlock(x, newSeaFloorLevel, z);
                    if (block.getMaterial() != v.getVoxelMaterial()) {
                        addOperation(new BlockOperation(block.getLocation(), block.getBlockData(), v.getVoxelMaterial().createBlockData()));
                    }
                }
            }
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        Undo undo = new Undo();
        this.oceanator(v, undo);
        v.owner().storeUndo(undo);
    }

    @Override
    protected final void powder(final SnipeData v) {
        arrow(v);
    }

    @Override
    public final void parseParameters(@NotNull final String triggerHandle, final String[] params, @NotNull final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.OCEAN_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }
        try {
            if (params[0].equalsIgnoreCase("water")) {
                int temp = Integer.parseInt(params[1]);

                if (temp <= WATER_LEVEL_MIN) {
                    v.sendMessage(Messages.OCEAN_LEVEL_AT_LEAST_12);
                    return;
                }

                this.waterLevel = temp - 1;
                v.sendMessage(Messages.WATER_LEVEL_SET.replace("%waterLevel%", String.valueOf(waterLevel + 1)));
                return;
            }

            if (params[0].equalsIgnoreCase("floor")) {
                this.coverFloor = Boolean.parseBoolean(params[1]);
                v.sendMessage(Messages.OCEAN_FLOOR_COVER.replace("%state%", this.coverFloor ? "enabled" : "disabled"));
                return;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @NotNull
    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Utils.newArrayList("water", "floor"));
    }

    @NotNull
    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();

        // Floor values
        argumentValues.put("floor", Utils.newArrayList("true", "false"));

        // Number values
        argumentValues.put("water", Utils.newArrayList("[number]"));

        return argumentValues;
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(Messages.WATER_LEVEL_SET.replace("%waterLevel%", String.valueOf(waterLevel + 1))); // +1 since we are working with 0-based array indices
        vm.custom(Messages.OCEAN_FLOOR_COVER.replace("%state%", this.coverFloor ? "enabled" : "disabled"));
    }
}
