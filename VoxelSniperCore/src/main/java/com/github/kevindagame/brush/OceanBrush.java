package com.github.kevindagame.brush;

import com.google.common.collect.Lists;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.world.IWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushe">...</a>s#ocean-brush
 *
 * @author Voxel
 */
// TODO: FIX (not working?)
public class OceanBrush extends AbstractBrush {

    private static final int WATER_LEVEL_DEFAULT = 62; // y=63 -- we are using array indices here
    private static final int WATER_LEVEL_MIN = 12;
    private static final int LOW_CUT_LEVEL = 12;
    private static final List<VoxelMaterial> EXCLUDED_MATERIALS = new LinkedList<>();

    static {
        EXCLUDED_MATERIALS.add(VoxelMaterial.AIR);
        EXCLUDED_MATERIALS.add(VoxelMaterial.CAVE_AIR);
        EXCLUDED_MATERIALS.add(VoxelMaterial.VOID_AIR);
        EXCLUDED_MATERIALS.add(VoxelMaterial.OAK_SAPLING);
        EXCLUDED_MATERIALS.add(VoxelMaterial.ACACIA_SAPLING);
        EXCLUDED_MATERIALS.add(VoxelMaterial.BIRCH_SAPLING);
        EXCLUDED_MATERIALS.add(VoxelMaterial.DARK_OAK_SAPLING);
        EXCLUDED_MATERIALS.add(VoxelMaterial.JUNGLE_SAPLING);
        EXCLUDED_MATERIALS.add(VoxelMaterial.SPRUCE_SAPLING);
        EXCLUDED_MATERIALS.add(VoxelMaterial.OAK_LEAVES);
        EXCLUDED_MATERIALS.add(VoxelMaterial.ACACIA_LEAVES);
        EXCLUDED_MATERIALS.add(VoxelMaterial.BIRCH_LEAVES);
        EXCLUDED_MATERIALS.add(VoxelMaterial.DARK_OAK_LEAVES);
        EXCLUDED_MATERIALS.add(VoxelMaterial.JUNGLE_LEAVES);
        EXCLUDED_MATERIALS.add(VoxelMaterial.SPRUCE_LEAVES);
        EXCLUDED_MATERIALS.add(VoxelMaterial.OAK_LOG);
        EXCLUDED_MATERIALS.add(VoxelMaterial.ACACIA_LOG);
        EXCLUDED_MATERIALS.add(VoxelMaterial.BIRCH_LOG);
        EXCLUDED_MATERIALS.add(VoxelMaterial.DARK_OAK_LOG);
        EXCLUDED_MATERIALS.add(VoxelMaterial.JUNGLE_LOG);
        EXCLUDED_MATERIALS.add(VoxelMaterial.SPRUCE_LOG);
        EXCLUDED_MATERIALS.add(VoxelMaterial.OAK_WOOD);
        EXCLUDED_MATERIALS.add(VoxelMaterial.ACACIA_WOOD);
        EXCLUDED_MATERIALS.add(VoxelMaterial.BIRCH_WOOD);
        EXCLUDED_MATERIALS.add(VoxelMaterial.DARK_OAK_WOOD);
        EXCLUDED_MATERIALS.add(VoxelMaterial.JUNGLE_WOOD);
        EXCLUDED_MATERIALS.add(VoxelMaterial.SPRUCE_WOOD);
        EXCLUDED_MATERIALS.add(VoxelMaterial.WATER);
        EXCLUDED_MATERIALS.add(VoxelMaterial.LAVA);
        EXCLUDED_MATERIALS.add(VoxelMaterial.DANDELION);
        EXCLUDED_MATERIALS.add(VoxelMaterial.POPPY);
        EXCLUDED_MATERIALS.add(VoxelMaterial.BLUE_ORCHID);
        EXCLUDED_MATERIALS.add(VoxelMaterial.ALLIUM);
        EXCLUDED_MATERIALS.add(VoxelMaterial.AZURE_BLUET);
        EXCLUDED_MATERIALS.add(VoxelMaterial.RED_TULIP);
        EXCLUDED_MATERIALS.add(VoxelMaterial.ORANGE_TULIP);
        EXCLUDED_MATERIALS.add(VoxelMaterial.WHITE_TULIP);
        EXCLUDED_MATERIALS.add(VoxelMaterial.PINK_TULIP);
        EXCLUDED_MATERIALS.add(VoxelMaterial.OXEYE_DAISY);
        EXCLUDED_MATERIALS.add(VoxelMaterial.RED_MUSHROOM);
        EXCLUDED_MATERIALS.add(VoxelMaterial.BROWN_MUSHROOM);
        EXCLUDED_MATERIALS.add(VoxelMaterial.MELON);
        EXCLUDED_MATERIALS.add(VoxelMaterial.MELON_STEM);
        EXCLUDED_MATERIALS.add(VoxelMaterial.PUMPKIN);
        EXCLUDED_MATERIALS.add(VoxelMaterial.PUMPKIN_STEM);
        EXCLUDED_MATERIALS.add(VoxelMaterial.COCOA);
        EXCLUDED_MATERIALS.add(VoxelMaterial.SNOW);
        EXCLUDED_MATERIALS.add(VoxelMaterial.SNOW_BLOCK);
        EXCLUDED_MATERIALS.add(VoxelMaterial.ICE);
        EXCLUDED_MATERIALS.add(VoxelMaterial.SUGAR_CANE);
        EXCLUDED_MATERIALS.add(VoxelMaterial.TALL_GRASS);
        EXCLUDED_MATERIALS.add(VoxelMaterial.SNOW);
    }

    private int waterLevel = WATER_LEVEL_DEFAULT;
    private boolean coverFloor = false;

    /**
     *
     */
    public OceanBrush() {
        this.setName("OCEANATOR 5000(tm)");
    }

    private int getHeight(final int bx, final int bz) {
        for (int y = this.getWorld().getHighestBlockYAt(bx, bz); y > this.getMinHeight(); y--) {
            final VoxelMaterial material = this.clampY(bx, y, bz).getMaterial();
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
    @SuppressWarnings("deprecation")
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
                        undo.put(block);
                        block.setMaterial(VoxelMaterial.AIR);
                    }
                }

                // go down from water level to new sea level
                for (int y = this.waterLevel; y > newSeaFloorLevel; y--) {
                    final IBlock block = world.getBlock(x, y, z);
                    if (!block.getMaterial().equals(VoxelMaterial.WATER)) {
                        // do not put blocks into the undo we already put into
                        if (!block.getMaterial().isAir()) {
                            undo.put(block);
                        }
                        block.setMaterial(VoxelMaterial.WATER);
                    }
                }

                // cover the sea floor of required
                if (this.coverFloor && (newSeaFloorLevel < this.waterLevel)) {
                    IBlock block = world.getBlock(x, newSeaFloorLevel, z);
                    if (block.getMaterial() != v.getVoxelMaterial()) {
                        undo.put(block);
                        block.setBlockData(v.getVoxelMaterial().createBlockData());
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
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
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

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("water", "floor"));
    }

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

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.ocean";
    }
}
