package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.util.Messages;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;
import org.bukkit.util.noise.PerlinNoiseGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_Heat_Ray
 *
 * @author Gavjenks
 */
public class HeatRayBrush extends Brush {

    private static final double REQUIRED_OBSIDIAN_DENSITY = 0.6;
    private static final double REQUIRED_COBBLE_DENSITY = 0.5;
    private static final double REQUIRED_FIRE_DENSITY = -0.25;
    private static final double REQUIRED_AIR_DENSITY = 0;

    private static final ArrayList<Material> FLAMMABLE_BLOCKS = new ArrayList<>();

    private int octaves = 5;
    private double amplitude = 0.3;
    private double frequency = 1;

    static {
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.OAK_SAPLING);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.ACACIA_SAPLING);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.BIRCH_SAPLING);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.DARK_OAK_SAPLING);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.JUNGLE_SAPLING);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.SPRUCE_SAPLING);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.OAK_LEAVES);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.ACACIA_LEAVES);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.BIRCH_LEAVES);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.DARK_OAK_LEAVES);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.JUNGLE_LEAVES);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.SPRUCE_LEAVES);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.OAK_LOG);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.ACACIA_LOG);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.BIRCH_LOG);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.DARK_OAK_LOG);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.JUNGLE_LOG);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.SPRUCE_LOG);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.OAK_WOOD);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.ACACIA_WOOD);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.BIRCH_WOOD);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.DARK_OAK_WOOD);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.JUNGLE_WOOD);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.SPRUCE_WOOD);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.SPONGE);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.COBWEB);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.TALL_GRASS);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.DEAD_BUSH);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.WHITE_WOOL);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.DANDELION);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.POPPY);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.BLUE_ORCHID);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.ALLIUM);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.AZURE_BLUET);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.RED_TULIP);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.ORANGE_TULIP);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.WHITE_TULIP);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.PINK_TULIP);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.OXEYE_DAISY);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.TORCH);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.FIRE);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.OAK_STAIRS);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.WHEAT);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.ACACIA_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.BIRCH_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.DARK_OAK_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.JUNGLE_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.OAK_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.ACACIA_WALL_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.BIRCH_WALL_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.DARK_OAK_WALL_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.JUNGLE_WALL_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.OAK_WALL_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.OAK_DOOR);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.LADDER);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.OAK_PRESSURE_PLATE);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.SNOW);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.ICE);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.SUGAR_CANE);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.OAK_FENCE);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.OAK_TRAPDOOR);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.VINE);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.OAK_FENCE_GATE);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(Material.LILY_PAD);
    }

    /**
     * Default Constructor.
     */
    public HeatRayBrush() {
        this.setName("Heat Ray");
    }

    /**
     * Heat Ray executer.
     *
     * @param v
     */
    public final void heatRay(final SnipeData v) {
        final PerlinNoiseGenerator generator = new PerlinNoiseGenerator(new Random());

        final Vector targetLocation = this.getTargetBlock().getLocation().toVector();
        final Location currentLocation = new Location(this.getTargetBlock().getWorld(), 0, 0, 0);
        final Undo undo = new Undo();
        Block currentBlock;

        for (int z = v.getBrushSize(); z >= -v.getBrushSize(); z--) {
            for (int x = v.getBrushSize(); x >= -v.getBrushSize(); x--) {
                for (int y = v.getBrushSize(); y >= -v.getBrushSize(); y--) {
                    currentLocation.setX(this.getTargetBlock().getX() + x);
                    currentLocation.setY(this.getTargetBlock().getY() + y);
                    currentLocation.setZ(this.getTargetBlock().getZ() + z);

                    if (currentLocation.toVector().isInSphere(targetLocation, v.getBrushSize())) {
                        currentBlock = currentLocation.getBlock();
                        if (currentBlock == null || currentBlock.getType() == Material.CHEST) {
                            continue;
                        }

                        if (currentBlock.isLiquid()) {
                            undo.put(currentBlock);
                            currentBlock.setType(Material.AIR);
                            continue;
                        }

                        if (HeatRayBrush.FLAMMABLE_BLOCKS.contains(currentBlock.getType())) {
                            undo.put(currentBlock);
                            currentBlock.setType(Material.FIRE);
                            continue;
                        }

                        if (!currentBlock.getType().equals(Material.AIR)) {
                            final double airDensity = generator.noise(currentLocation.getX(), currentLocation.getY(), currentLocation.getZ(), this.octaves, this.frequency, this.amplitude);
                            final double fireDensity = generator.noise(currentLocation.getX(), currentLocation.getY(), currentLocation.getZ(), this.octaves, this.frequency, this.amplitude);
                            final double cobbleDensity = generator.noise(currentLocation.getX(), currentLocation.getY(), currentLocation.getZ(), this.octaves, this.frequency, this.amplitude);
                            final double obsidianDensity = generator.noise(currentLocation.getX(), currentLocation.getY(), currentLocation.getZ(), this.octaves, this.frequency, this.amplitude);

                            if (obsidianDensity >= HeatRayBrush.REQUIRED_OBSIDIAN_DENSITY) {
                                undo.put(currentBlock);
                                if (currentBlock.getType() != Material.OBSIDIAN) {
                                    currentBlock.setType(Material.OBSIDIAN);
                                }
                            } else if (cobbleDensity >= HeatRayBrush.REQUIRED_COBBLE_DENSITY) {
                                undo.put(currentBlock);
                                if (currentBlock.getType() != Material.COBBLESTONE) {
                                    currentBlock.setType(Material.COBBLESTONE);
                                }
                            } else if (fireDensity >= HeatRayBrush.REQUIRED_FIRE_DENSITY) {
                                undo.put(currentBlock);
                                if (currentBlock.getType() != Material.FIRE) {
                                    currentBlock.setType(Material.FIRE);
                                }
                            } else if (airDensity >= HeatRayBrush.REQUIRED_AIR_DENSITY) {
                                undo.put(currentBlock);
                                if (currentBlock.getType() != Material.AIR) {
                                    currentBlock.setType(Material.AIR);
                                }
                            }
                        }
                    }

                }
            }
        }

        v.owner().storeUndo(undo);
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.heatRay(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.heatRay(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(Messages.HEAT_RAY_BRUSH_INFO.replace("%octaves%",String.valueOf(this.octaves)).replace("%amplitude%",String.valueOf(this.amplitude)).replace("%frequency%",String.valueOf(this.frequency)));
        vm.size();
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {

        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.HEAT_RAY_BRUSH_USAGE.replace("%triggerHandle%",triggerHandle));
            return;
        }

        if (params[0].equalsIgnoreCase("default")) {
            this.octaves = 5;
            this.amplitude = 0.3;
            this.frequency = 1;
            v.sendMessage(Messages.BRUSH_RESET_DEFAULT);
            return;
        }

        try {
            if (params[0].equalsIgnoreCase("octave")) {
                this.octaves = Integer.parseInt(params[1]);
                v.sendMessage(Messages.HEATRAY_OCTAVE.replace("%this.octaves%",String.valueOf(this.octaves)));
                return;
            }
            if (params[0].equalsIgnoreCase("amplitude")) {
                this.amplitude = Double.parseDouble(params[1]);
                v.sendMessage(Messages.HEATRAY_AMPLITUDE.replace("%this.amplitude%",String.valueOf(this.amplitude)));
                return;
            }

            if (params[0].equalsIgnoreCase("frequency")) {
                this.frequency = Double.parseDouble(params[1]);
                v.sendMessage(Messages.HEATRAY_FREQ.replace("%this.frequency%",String.valueOf(this.frequency)));
                return;
            }
        } catch (NumberFormatException ignored) {
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("octave", "amplitude", "frequency", "default"));
    }

    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();

        argumentValues.put("octave", Lists.newArrayList("[number]"));
        argumentValues.put("amplitude", Lists.newArrayList("[number]"));
        argumentValues.put("frequency", Lists.newArrayList("[number]"));

        return argumentValues;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.heatray";
    }
}
