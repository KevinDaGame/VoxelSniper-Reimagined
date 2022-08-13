package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.location.LocationFactory;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.IVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.util.noise.PerlinNoiseGenerator;

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

    private static final ArrayList<VoxelMaterial> FLAMMABLE_BLOCKS = new ArrayList<>();

    private int octaves = 5;
    private double amplitude = 0.3;
    private double frequency = 1;

    static {
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.OAK_SAPLING);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.ACACIA_SAPLING);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.BIRCH_SAPLING);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.DARK_OAK_SAPLING);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.JUNGLE_SAPLING);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.SPRUCE_SAPLING);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.OAK_LEAVES);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.ACACIA_LEAVES);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.BIRCH_LEAVES);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.DARK_OAK_LEAVES);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.JUNGLE_LEAVES);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.SPRUCE_LEAVES);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.OAK_LOG);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.ACACIA_LOG);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.BIRCH_LOG);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.DARK_OAK_LOG);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.JUNGLE_LOG);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.SPRUCE_LOG);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.OAK_WOOD);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.ACACIA_WOOD);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.BIRCH_WOOD);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.DARK_OAK_WOOD);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.JUNGLE_WOOD);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.SPRUCE_WOOD);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.SPONGE);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.COBWEB);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.TALL_GRASS);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.DEAD_BUSH);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.WHITE_WOOL);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.DANDELION);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.POPPY);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.BLUE_ORCHID);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.ALLIUM);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.AZURE_BLUET);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.RED_TULIP);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.ORANGE_TULIP);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.WHITE_TULIP);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.PINK_TULIP);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.OXEYE_DAISY);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.TORCH);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.FIRE);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.OAK_STAIRS);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.WHEAT);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.ACACIA_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.BIRCH_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.DARK_OAK_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.JUNGLE_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.OAK_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.ACACIA_WALL_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.BIRCH_WALL_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.DARK_OAK_WALL_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.JUNGLE_WALL_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.OAK_WALL_SIGN);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.OAK_DOOR);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.LADDER);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.OAK_PRESSURE_PLATE);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.SNOW);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.ICE);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.SUGAR_CANE);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.OAK_FENCE);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.OAK_TRAPDOOR);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.VINE);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.OAK_FENCE_GATE);
        HeatRayBrush.FLAMMABLE_BLOCKS.add(VoxelMaterial.LILY_PAD);
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

        final IVector targetLocation = this.getTargetBlock().getLocation().toVector();
        final ILocation currentLocation = LocationFactory.getLocation(this.getTargetBlock().getWorld(), 0, 0, 0);
        final Undo undo = new Undo();
         IBlock currentBlock;

        for (int z = v.getBrushSize(); z >= -v.getBrushSize(); z--) {
            for (int x = v.getBrushSize(); x >= -v.getBrushSize(); x--) {
                for (int y = v.getBrushSize(); y >= -v.getBrushSize(); y--) {
                    currentLocation.setX(this.getTargetBlock().getX() + x);
                    currentLocation.setY(this.getTargetBlock().getY() + y);
                    currentLocation.setZ(this.getTargetBlock().getZ() + z);

                    if (currentLocation.toVector().isInSphere(targetLocation, v.getBrushSize())) {
                        currentBlock = currentLocation.getBlock();
                        if (currentBlock == null || currentBlock.getMaterial() == VoxelMaterial.CHEST) {
                            continue;
                        }

                        if (currentBlock.isLiquid()) {
                            undo.put(currentBlock);
                            currentBlock.setMaterial(VoxelMaterial.AIR);
                            continue;
                        }

                        if (HeatRayBrush.FLAMMABLE_BLOCKS.contains(currentBlock.getMaterial())) {
                            undo.put(currentBlock);
                            currentBlock.setMaterial(VoxelMaterial.FIRE);
                            continue;
                        }

                        if (!currentBlock.getMaterial().equals(VoxelMaterial.AIR)) {
                            final double airDensity = generator.noise(currentLocation.getX(), currentLocation.getY(), currentLocation.getZ(), this.octaves, this.frequency, this.amplitude);
                            final double fireDensity = generator.noise(currentLocation.getX(), currentLocation.getY(), currentLocation.getZ(), this.octaves, this.frequency, this.amplitude);
                            final double cobbleDensity = generator.noise(currentLocation.getX(), currentLocation.getY(), currentLocation.getZ(), this.octaves, this.frequency, this.amplitude);
                            final double obsidianDensity = generator.noise(currentLocation.getX(), currentLocation.getY(), currentLocation.getZ(), this.octaves, this.frequency, this.amplitude);

                            if (obsidianDensity >= HeatRayBrush.REQUIRED_OBSIDIAN_DENSITY) {
                                undo.put(currentBlock);
                                if (currentBlock.getMaterial() != VoxelMaterial.OBSIDIAN) {
                                    currentBlock.setMaterial(VoxelMaterial.OBSIDIAN);
                                }
                            } else if (cobbleDensity >= HeatRayBrush.REQUIRED_COBBLE_DENSITY) {
                                undo.put(currentBlock);
                                if (currentBlock.getMaterial() != VoxelMaterial.COBBLESTONE) {
                                    currentBlock.setMaterial(VoxelMaterial.COBBLESTONE);
                                }
                            } else if (fireDensity >= HeatRayBrush.REQUIRED_FIRE_DENSITY) {
                                undo.put(currentBlock);
                                if (currentBlock.getMaterial() != VoxelMaterial.FIRE) {
                                    currentBlock.setMaterial(VoxelMaterial.FIRE);
                                }
                            } else if (airDensity >= HeatRayBrush.REQUIRED_AIR_DENSITY) {
                                undo.put(currentBlock);
                                if (currentBlock.getMaterial() != VoxelMaterial.AIR) {
                                    currentBlock.setMaterial(VoxelMaterial.AIR);
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
        } catch (NumberFormatException temp) {
temp.printStackTrace();
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
