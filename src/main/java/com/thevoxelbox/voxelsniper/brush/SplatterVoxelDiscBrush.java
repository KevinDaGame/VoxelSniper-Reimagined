package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.Message;
import com.thevoxelbox.voxelsniper.SnipeData;
import com.thevoxelbox.voxelsniper.brush.perform.PerformBrush;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;

import java.util.Random;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Splatter_Brushes
 *
 * @author Voxel
 */
public class SplatterVoxelDiscBrush extends PerformBrush {

    private static final int GROW_PERCENT_MIN = 1;
    private static final int GROW_PERCENT_DEFAULT = 1000;
    private static final int GROW_PERCENT_MAX = 9999;
    private static final int SEED_PERCENT_MIN = 1;
    private static final int SEED_PERCENT_DEFAULT = 1000;
    private static final int SEED_PERCENT_MAX = 9999;
    private static final int SPLATREC_PERCENT_MIN = 1;
    private static final int SPLATREC_PERCENT_DEFAULT = 3;
    private static final int SPLATREC_PERCENT_MAX = 10;
    private int seedPercent; // Chance block on first pass is made active
    private int growPercent; // chance block on recursion pass is made active
    private int splatterRecursions; // How many times you grow the seeds
    private Random generator = new Random();

    /**
     *
     */
    public SplatterVoxelDiscBrush() {
        this.setName("Splatter Voxel Disc");
    }

    private void vSplatterDisc(final SnipeData v, Block targetBlock) {
        if (this.seedPercent < SEED_PERCENT_MIN || this.seedPercent > SEED_PERCENT_MAX) {
            v.sendMessage(ChatColor.BLUE + "Seed percent set to: 10%");
            this.seedPercent = SEED_PERCENT_DEFAULT;
        }
        if (this.growPercent < GROW_PERCENT_MIN || this.growPercent > GROW_PERCENT_MAX) {
            v.sendMessage(ChatColor.BLUE + "Growth percent set to: 10%");
            this.growPercent = GROW_PERCENT_DEFAULT;
        }
        if (this.splatterRecursions < SPLATREC_PERCENT_MIN || this.splatterRecursions > SPLATREC_PERCENT_MAX) {
            v.sendMessage(ChatColor.BLUE + "Recursions set to: 3");
            this.splatterRecursions = SPLATREC_PERCENT_DEFAULT;
        }
        final int[][] splat = new int[2 * v.getBrushSize() + 1][2 * v.getBrushSize() + 1];

        // Seed the array
        for (int x = 2 * v.getBrushSize(); x >= 0; x--) {
            for (int y = 2 * v.getBrushSize(); y >= 0; y--) {
                if (this.generator.nextInt(SEED_PERCENT_MAX + 1) <= this.seedPercent) {
                    splat[x][y] = 1;
                }
            }
        }
        // Grow the seeds
        final int gref = this.growPercent;
        final int[][] tempSplat = new int[2 * v.getBrushSize() + 1][2 * v.getBrushSize() + 1];
        int growcheck;

        for (int r = 0; r < this.splatterRecursions; r++) {
            this.growPercent = gref - ((gref / this.splatterRecursions) * (r));
            for (int x = 2 * v.getBrushSize(); x >= 0; x--) {
                for (int y = 2 * v.getBrushSize(); y >= 0; y--) {
                    tempSplat[x][y] = splat[x][y]; // prime tempsplat

                    growcheck = 0;
                    if (splat[x][y] == 0) {
                        if (x != 0 && splat[x - 1][y] == 1) {
                            growcheck++;
                        }
                        if (y != 0 && splat[x][y - 1] == 1) {
                            growcheck++;
                        }
                        if (x != 2 * v.getBrushSize() && splat[x + 1][y] == 1) {
                            growcheck++;
                        }
                        if (y != 2 * v.getBrushSize() && splat[x][y + 1] == 1) {
                            growcheck++;
                        }
                    }

                    if (growcheck >= 1 && this.generator.nextInt(GROW_PERCENT_MAX + 1) <= this.growPercent) {
                        tempSplat[x][y] = 1; // prevent bleed into splat
                    }
                }
            }
            // integrate tempsplat back into splat at end of iteration
            for (int x = 2 * v.getBrushSize(); x >= 0; x--) {
                for (int y = 2 * v.getBrushSize(); y >= 0; y--) {
                    splat[x][y] = tempSplat[x][y];
                }
            }
        }
        this.growPercent = gref;
        // Fill 1x1 holes
        for (int x = 2 * v.getBrushSize(); x >= 0; x--) {
            for (int y = 2 * v.getBrushSize(); y >= 0; y--) {
                if (splat[Math.max(x - 1, 0)][y] == 1 && splat[Math.min(x + 1, 2 * v.getBrushSize())][y] == 1 && splat[x][Math.max(0, y - 1)] == 1 && splat[x][Math.min(2 * v.getBrushSize(), y + 1)] == 1) {
                    splat[x][y] = 1;
                }
            }
        }

        // Make the changes
        for (int x = 2 * v.getBrushSize(); x >= 0; x--) {
            for (int y = 2 * v.getBrushSize(); y >= 0; y--) {
                if (splat[x][y] == 1) {
                    this.currentPerformer.perform(this.clampY(targetBlock.getX() - v.getBrushSize() + x, targetBlock.getY(), targetBlock.getZ() - v.getBrushSize() + y));
                }
            }
        }
        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.vSplatterDisc(v, this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.vSplatterDisc(v, this.getLastBlock());
    }

    @Override
    public final void info(final Message vm) {
        if (this.seedPercent < SEED_PERCENT_MIN || this.seedPercent > SEED_PERCENT_MAX) {
            this.seedPercent = SEED_PERCENT_DEFAULT;
        }
        if (this.growPercent < GROW_PERCENT_MIN || this.growPercent > GROW_PERCENT_MAX) {
            this.growPercent = GROW_PERCENT_DEFAULT;
        }
        if (this.splatterRecursions < SPLATREC_PERCENT_MIN || this.splatterRecursions > SPLATREC_PERCENT_MAX) {
            this.splatterRecursions = SPLATREC_PERCENT_DEFAULT;
        }
        vm.brushName("Splatter Voxel Disc");
        vm.size();
        vm.custom(ChatColor.BLUE + "Seed percent set to: " + this.seedPercent / 100 + "%");
        vm.custom(ChatColor.BLUE + "Growth percent set to: " + this.growPercent / 100 + "%");
        vm.custom(ChatColor.BLUE + "Recursions set to: " + this.splatterRecursions);

    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "Splatter Voxel Disc Brush Parameters:");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " seed [decimal]  -- Set a seed percentage");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " growth [decimal]  -- Set a growth percentage");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " recursion [number]  -- Set a recursion value");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " reset  -- Resets to default values");
            return;
        }

        if (params[0].equalsIgnoreCase("reset")) {
            this.seedPercent = SEED_PERCENT_DEFAULT;
            this.growPercent = GROW_PERCENT_DEFAULT;
            this.splatterRecursions = SPLATREC_PERCENT_DEFAULT;
            v.sendMessage(ChatColor.GOLD + "Values resetted to default values.");
            return;
        }

        try {
            if (params[0].startsWith("seed")) {
                final int temp = ((int) Double.parseDouble(params[1]) * 100);

                if (temp >= SEED_PERCENT_MIN && temp <= SEED_PERCENT_MAX) {
                    v.sendMessage(ChatColor.AQUA + "Seed percent set to: " + String.format("%.2f", (double) temp / 100) + "%");
                    this.seedPercent = temp;
                } else {
                    v.sendMessage(ChatColor.RED + "Seed percent must be a decimal between 0.01 - 99.99!");
                }
                return;
            }

            if (params[0].startsWith("growth")) {
                final int temp = ((int) Double.parseDouble(params[1]) * 100);

                if (temp >= GROW_PERCENT_MIN && temp <= GROW_PERCENT_MAX) {
                    v.sendMessage(ChatColor.AQUA + "Growth percent set to: " + String.format("%.2f", (double) temp / 100) + "%");
                    this.growPercent = (int) temp;
                } else {
                    v.sendMessage(ChatColor.RED + "Growth percent must be a decimal between 0.01 - 99.99!");
                }
                return;
            }

            if (params[0].startsWith("recursion")) {
                final int temp = Integer.parseInt(params[1]);

                if (temp >= SPLATREC_PERCENT_MIN && temp <= SPLATREC_PERCENT_MAX) {
                    v.sendMessage(ChatColor.AQUA + "Recursions set to: " + temp);
                    this.splatterRecursions = temp;
                } else {
                    v.sendMessage(ChatColor.RED + "Recursions must be an number between 1 - 10!");
                }
                return;
            }
        } catch (NumberFormatException e) {
        }

        v.sendMessage(ChatColor.RED + "Invalid parameter! Use " + ChatColor.LIGHT_PURPLE + "'/b " + triggerHandle + " info'" + ChatColor.RED + " to display valid parameters.");
        sendPerformerMessage(triggerHandle, v);
    }

    @Override
    public void registerSubcommandArguments(HashMap<Integer, List<String>> subcommandArguments) {
        subcommandArguments.put(1, Lists.newArrayList("recursion", "growth", "seed", "reset"));

        super.registerSubcommandArguments(subcommandArguments); // super must always execute last!
    }

    @Override
    public void registerArgumentValues(String prefix, HashMap<String, HashMap<Integer, List<String>>> argumentValues) {
        // Number variables
        HashMap<Integer, List<String>> arguments = new HashMap<>();
        arguments.put(1, Lists.newArrayList("[number]"));

        argumentValues.put(prefix + "recursion", arguments);

        // Decimal variables
        arguments = new HashMap<>();
        arguments.put(1, Lists.newArrayList("[decimal]"));

        argumentValues.put(prefix + "seed", arguments);
        argumentValues.put(prefix + "growth", arguments);
        
        super.registerArgumentValues(prefix, argumentValues);
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.splattervoxeldisc";
    }
}
