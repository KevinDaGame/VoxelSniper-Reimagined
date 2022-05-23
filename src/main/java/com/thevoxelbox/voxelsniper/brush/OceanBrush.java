package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_OCEANATOR_5000
 *
 * @author Voxel
 */
// TODO: FIX (not working?)
public class OceanBrush extends Brush {

    private static final int WATER_LEVEL_DEFAULT = 62; // y=63 -- we are using array indices here
    private static final int WATER_LEVEL_MIN = 12;
    private static final int LOW_CUT_LEVEL = 12;
    private static final List<Material> EXCLUDED_MATERIALS = new LinkedList<>();

    static {
        EXCLUDED_MATERIALS.add(Material.AIR);
        EXCLUDED_MATERIALS.add(Material.OAK_SAPLING);
        EXCLUDED_MATERIALS.add(Material.ACACIA_SAPLING);
        EXCLUDED_MATERIALS.add(Material.BIRCH_SAPLING);
        EXCLUDED_MATERIALS.add(Material.DARK_OAK_SAPLING);
        EXCLUDED_MATERIALS.add(Material.JUNGLE_SAPLING);
        EXCLUDED_MATERIALS.add(Material.SPRUCE_SAPLING);
        EXCLUDED_MATERIALS.add(Material.OAK_LEAVES);
        EXCLUDED_MATERIALS.add(Material.ACACIA_LEAVES);
        EXCLUDED_MATERIALS.add(Material.BIRCH_LEAVES);
        EXCLUDED_MATERIALS.add(Material.DARK_OAK_LEAVES);
        EXCLUDED_MATERIALS.add(Material.JUNGLE_LEAVES);
        EXCLUDED_MATERIALS.add(Material.SPRUCE_LEAVES);
        EXCLUDED_MATERIALS.add(Material.OAK_LOG);
        EXCLUDED_MATERIALS.add(Material.ACACIA_LOG);
        EXCLUDED_MATERIALS.add(Material.BIRCH_LOG);
        EXCLUDED_MATERIALS.add(Material.DARK_OAK_LOG);
        EXCLUDED_MATERIALS.add(Material.JUNGLE_LOG);
        EXCLUDED_MATERIALS.add(Material.SPRUCE_LOG);
        EXCLUDED_MATERIALS.add(Material.OAK_WOOD);
        EXCLUDED_MATERIALS.add(Material.ACACIA_WOOD);
        EXCLUDED_MATERIALS.add(Material.BIRCH_WOOD);
        EXCLUDED_MATERIALS.add(Material.DARK_OAK_WOOD);
        EXCLUDED_MATERIALS.add(Material.JUNGLE_WOOD);
        EXCLUDED_MATERIALS.add(Material.SPRUCE_WOOD);
        EXCLUDED_MATERIALS.add(Material.WATER);
        EXCLUDED_MATERIALS.add(Material.LAVA);
        EXCLUDED_MATERIALS.add(Material.DANDELION);
        EXCLUDED_MATERIALS.add(Material.POPPY);
        EXCLUDED_MATERIALS.add(Material.BLUE_ORCHID);
        EXCLUDED_MATERIALS.add(Material.ALLIUM);
        EXCLUDED_MATERIALS.add(Material.AZURE_BLUET);
        EXCLUDED_MATERIALS.add(Material.RED_TULIP);
        EXCLUDED_MATERIALS.add(Material.ORANGE_TULIP);
        EXCLUDED_MATERIALS.add(Material.WHITE_TULIP);
        EXCLUDED_MATERIALS.add(Material.PINK_TULIP);
        EXCLUDED_MATERIALS.add(Material.OXEYE_DAISY);
        EXCLUDED_MATERIALS.add(Material.RED_MUSHROOM);
        EXCLUDED_MATERIALS.add(Material.BROWN_MUSHROOM);
        EXCLUDED_MATERIALS.add(Material.MELON);
        EXCLUDED_MATERIALS.add(Material.MELON_STEM);
        EXCLUDED_MATERIALS.add(Material.PUMPKIN);
        EXCLUDED_MATERIALS.add(Material.PUMPKIN_STEM);
        EXCLUDED_MATERIALS.add(Material.COCOA);
        EXCLUDED_MATERIALS.add(Material.SNOW);
        EXCLUDED_MATERIALS.add(Material.SNOW_BLOCK);
        EXCLUDED_MATERIALS.add(Material.ICE);
        EXCLUDED_MATERIALS.add(Material.SUGAR_CANE);
        EXCLUDED_MATERIALS.add(Material.TALL_GRASS);
        EXCLUDED_MATERIALS.add(Material.SNOW);
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
            final Material material = this.clampY(bx, y, bz).getType();
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
        final World world = this.getWorld();

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
                    final Block block = world.getBlockAt(x, y, z);
                    if (!block.getType().equals(Material.AIR)) {
                        undo.put(block);
                        block.setType(Material.AIR);
                    }
                }

                // go down from water level to new sea level
                for (int y = this.waterLevel; y > newSeaFloorLevel; y--) {
                    final Block block = world.getBlockAt(x, y, z);
                    if (!block.getType().equals(Material.WATER)) {
                        // do not put blocks into the undo we already put into
                        if (!block.getType().equals(Material.AIR)) {
                            undo.put(block);
                        }
                        block.setType(Material.WATER);
                    }
                }

                // cover the sea floor of required
                if (this.coverFloor && (newSeaFloorLevel < this.waterLevel)) {
                    Block block = world.getBlockAt(x, newSeaFloorLevel, z);
                    if (block.getType() != v.getVoxelMaterial()) {
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
            v.sendMessage(ChatColor.GOLD + "Parameters:");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " water [number]  -- Sets the water level");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " floor [true/false]  -- Toggle sea floor cover (Cover material will be your voxel material)");
            return;
        }
        try {
            if (params[0].equalsIgnoreCase("water")) {
                int temp = Integer.parseInt(params[1]);

                if (temp <= WATER_LEVEL_MIN) {
                    v.sendMessage(ChatColor.RED + "The water level must be at least 12.");
                    return;
                }

                this.waterLevel = temp - 1;
                v.sendMessage(ChatColor.BLUE + "Water level set to " + ChatColor.GREEN + (waterLevel + 1));
                return;
            }

            if (params[0].equalsIgnoreCase("floor")) {
                this.coverFloor = Boolean.parseBoolean(params[1]);
                v.sendMessage(ChatColor.BLUE + "Floor cover " + ChatColor.GREEN + (this.coverFloor ? "enabled" : "disabled"));
                return;
            }
        } catch (NumberFormatException ignored) {
        }

        v.sendMessage(ChatColor.RED + "Invalid parameter! Use " + ChatColor.LIGHT_PURPLE + "'/b " + triggerHandle + " info'" + ChatColor.RED + " to display valid parameters.");
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
        vm.custom(ChatColor.BLUE + "Water level set to " + ChatColor.GREEN + (waterLevel + 1)); // +1 since we are working with 0-based array indices
        vm.custom(ChatColor.BLUE + "Floor cover " + ChatColor.GREEN + (this.coverFloor ? "enabled" : "disabled"));
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.ocean";
    }
}
