package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import org.bukkit.ChatColor;
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

    private static final ArrayList<IMaterial> FLAMMABLE_BLOCKS = new ArrayList<>();

    private int octaves = 5;
    private double amplitude = 0.3;
    private double frequency = 1;

    static {
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.OAK_SAPLING));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.ACACIA_SAPLING));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.BIRCH_SAPLING));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.DARK_OAK_SAPLING));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.JUNGLE_SAPLING));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.SPRUCE_SAPLING));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.OAK_LEAVES));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.ACACIA_LEAVES));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.BIRCH_LEAVES));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.DARK_OAK_LEAVES));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.JUNGLE_LEAVES));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.SPRUCE_LEAVES));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.OAK_LOG));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.ACACIA_LOG));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.BIRCH_LOG));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.DARK_OAK_LOG));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.JUNGLE_LOG));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.SPRUCE_LOG));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.OAK_WOOD));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.ACACIA_WOOD));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.BIRCH_WOOD));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.DARK_OAK_WOOD));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.JUNGLE_WOOD));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.SPRUCE_WOOD));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.SPONGE));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.COBWEB));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.TALL_GRASS));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.DEAD_BUSH));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.WHITE_WOOL));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.DANDELION));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.POPPY));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.BLUE_ORCHID));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.ALLIUM));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.AZURE_BLUET));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.RED_TULIP));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.ORANGE_TULIP));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.WHITE_TULIP));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.PINK_TULIP));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.OXEYE_DAISY));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.TORCH));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.FIRE));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.OAK_STAIRS));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.WHEAT));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.ACACIA_SIGN));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.BIRCH_SIGN));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.DARK_OAK_SIGN));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.JUNGLE_SIGN));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.OAK_SIGN));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.ACACIA_WALL_SIGN));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.BIRCH_WALL_SIGN));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.DARK_OAK_WALL_SIGN));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.JUNGLE_WALL_SIGN));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.OAK_WALL_SIGN));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.OAK_DOOR));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.LADDER));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.OAK_PRESSURE_PLATE));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.SNOW));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.ICE));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.SUGAR_CANE));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.OAK_FENCE));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.OAK_TRAPDOOR));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.VINE));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.OAK_FENCE_GATE));
        HeatRayBrush.FLAMMABLE_BLOCKS.add(new BukkitMaterial(Material.LILY_PAD));
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
                        if (currentBlock == null || currentBlock.getMaterial() == new BukkitMaterial( Material.CHEST)) {
                            continue;
                        }

                        if (currentBlock.isLiquid()) {
                            undo.put(currentBlock);
                            currentBlock.setType(new BukkitMaterial(Material.AIR));
                            continue;
                        }

                        if (HeatRayBrush.FLAMMABLE_BLOCKS.contains(currentBlock.getMaterial())) {
                            undo.put(currentBlock);
                            currentBlock.setType(new BukkitMaterial(Material.FIRE));
                            continue;
                        }

                        if (!currentBlock.getMaterial().equals(new BukkitMaterial(Material.AIR))) {
                            final double airDensity = generator.noise(currentLocation.getX(), currentLocation.getY(), currentLocation.getZ(), this.octaves, this.frequency, this.amplitude);
                            final double fireDensity = generator.noise(currentLocation.getX(), currentLocation.getY(), currentLocation.getZ(), this.octaves, this.frequency, this.amplitude);
                            final double cobbleDensity = generator.noise(currentLocation.getX(), currentLocation.getY(), currentLocation.getZ(), this.octaves, this.frequency, this.amplitude);
                            final double obsidianDensity = generator.noise(currentLocation.getX(), currentLocation.getY(), currentLocation.getZ(), this.octaves, this.frequency, this.amplitude);

                            if (obsidianDensity >= HeatRayBrush.REQUIRED_OBSIDIAN_DENSITY) {
                                undo.put(currentBlock);
                                if (currentBlock.getMaterial() != new BukkitMaterial( Material.OBSIDIAN)) {
                                    currentBlock.setType(new BukkitMaterial(Material.OBSIDIAN));
                                }
                            } else if (cobbleDensity >= HeatRayBrush.REQUIRED_COBBLE_DENSITY) {
                                undo.put(currentBlock);
                                if (currentBlock.getMaterial() != new BukkitMaterial( Material.COBBLESTONE)) {
                                    currentBlock.setType(new BukkitMaterial(Material.COBBLESTONE));
                                }
                            } else if (fireDensity >= HeatRayBrush.REQUIRED_FIRE_DENSITY) {
                                undo.put(currentBlock);
                                if (currentBlock.getMaterial() != new BukkitMaterial( Material.FIRE)) {
                                    currentBlock.setType(new BukkitMaterial(Material.FIRE));
                                }
                            } else if (airDensity >= HeatRayBrush.REQUIRED_AIR_DENSITY) {
                                undo.put(currentBlock);
                                if (currentBlock.getMaterial() != new BukkitMaterial( Material.AIR)) {
                                    currentBlock.setType(new BukkitMaterial(Material.AIR));
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
        vm.custom(ChatColor.GREEN + "Octaves: " + this.octaves);
        vm.custom(ChatColor.GREEN + "Amplitude: " + this.amplitude);
        vm.custom(ChatColor.GREEN + "Frequency: " + this.frequency);
        vm.size();
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {

        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "Heat Ray brush Parameters:");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " octave [number]  -- Octaves for the noise generator.");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " amplitude [number]  -- Amplitude for the noise generator.");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " frequency [number]  -- Frequency for the noise generator.");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " default  -- Reset to default values.");
            return;
        }

        if (params[0].equalsIgnoreCase("default")) {
            this.octaves = 5;
            this.amplitude = 0.3;
            this.frequency = 1;
            v.sendMessage(ChatColor.GOLD + "Values were set to default values.");
            return;
        }

        try {
            if (params[0].equalsIgnoreCase("octave")) {
                this.octaves = Integer.parseInt(params[1]);
                v.getVoxelMessage().custom(ChatColor.GREEN + "Octave: " + this.octaves);
                return;
            }
            if (params[0].equalsIgnoreCase("amplitude")) {
                this.amplitude = Double.parseDouble(params[1]);
                v.getVoxelMessage().custom(ChatColor.GREEN + "Amplitude: " + this.amplitude);
                return;
            }

            if (params[0].equalsIgnoreCase("frequency")) {
                this.frequency = Double.parseDouble(params[1]);
                v.getVoxelMessage().custom(ChatColor.GREEN + "Frequency: " + this.frequency);
                return;
            }
        } catch (NumberFormatException ignored) {
        }

        v.sendMessage(ChatColor.RED + "Invalid parameter! Use " + ChatColor.LIGHT_PURPLE + "'/b " + triggerHandle + " info'" + ChatColor.RED + " to display valid parameters.");
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
