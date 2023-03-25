package com.github.kevindagame.brush.multiBlock;

import com.github.kevindagame.brush.AbstractBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.BlockOperation;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType;
import com.github.kevindagame.voxelsniper.world.IWorld;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
    private static final List<VoxelMaterialType> EXCLUDED_MATERIALS = new LinkedList<>();

    static {
        EXCLUDED_MATERIALS.add(VoxelMaterialType.AIR);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.CAVE_AIR);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.VOID_AIR);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.OAK_SAPLING);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.ACACIA_SAPLING);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.BIRCH_SAPLING);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.DARK_OAK_SAPLING);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.JUNGLE_SAPLING);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.SPRUCE_SAPLING);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.OAK_LEAVES);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.ACACIA_LEAVES);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.BIRCH_LEAVES);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.DARK_OAK_LEAVES);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.JUNGLE_LEAVES);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.SPRUCE_LEAVES);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.OAK_LOG);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.ACACIA_LOG);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.BIRCH_LOG);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.DARK_OAK_LOG);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.JUNGLE_LOG);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.SPRUCE_LOG);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.OAK_WOOD);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.ACACIA_WOOD);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.BIRCH_WOOD);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.DARK_OAK_WOOD);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.JUNGLE_WOOD);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.SPRUCE_WOOD);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.WATER);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.LAVA);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.DANDELION);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.POPPY);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.BLUE_ORCHID);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.ALLIUM);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.AZURE_BLUET);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.RED_TULIP);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.ORANGE_TULIP);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.WHITE_TULIP);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.PINK_TULIP);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.OXEYE_DAISY);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.RED_MUSHROOM);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.BROWN_MUSHROOM);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.MELON);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.MELON_STEM);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.PUMPKIN);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.PUMPKIN_STEM);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.COCOA);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.SNOW);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.SNOW_BLOCK);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.ICE);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.SUGAR_CANE);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.TALL_GRASS);
        EXCLUDED_MATERIALS.add(VoxelMaterialType.SNOW);
    }

    private int waterLevel = WATER_LEVEL_DEFAULT;
    private boolean coverFloor = false;


    private int getHeight(final int bx, final int bz) {
        for (int y = this.getWorld().getHighestBlockYAt(bx, bz); y > this.getMinHeight(); y--) {
            final VoxelMaterialType material = getWorld().getBlock(bx, y, bz).getMaterial();
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
                        addOperation(new BlockOperation(block.getLocation(), block.getBlockData(), VoxelMaterialType.AIR.createBlockData()));
                    }
                }

                // go down from water level to new sea level
                for (int y = this.waterLevel; y > newSeaFloorLevel; y--) {
                    final IBlock block = world.getBlock(x, y, z);
                    if (!block.getMaterial().equals(VoxelMaterialType.WATER)) {
                        addOperation(new BlockOperation(block.getLocation(), block.getBlockData(), VoxelMaterialType.WATER.createBlockData()));
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

        return new ArrayList<>(Lists.newArrayList("water", "floor"));
    }

    @NotNull
    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();

        // Floor values
        argumentValues.put("floor", Lists.newArrayList("true", "false"));

        // Number values
        argumentValues.put("water", Lists.newArrayList("[number]"));

        return argumentValues;
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(Messages.WATER_LEVEL_SET.replace("%waterLevel%", String.valueOf(waterLevel + 1))); // +1 since we are working with 0-based array indices
        vm.custom(Messages.OCEAN_FLOOR_COVER.replace("%state%", this.coverFloor ? "enabled" : "disabled"));
    }
}
