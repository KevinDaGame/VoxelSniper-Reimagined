package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.brush.perform.PerformerBrush;
import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Splatter_Brushes
 *
 * @author Voxel
 */
public class SplatterBallBrush extends PerformerBrush {

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
    private final Random generator = new Random();

    /**
     *
     */
    public SplatterBallBrush() {
        this.setName("Splatter Ball");
    }

    private void splatterBall(final SnipeData v,  IBlock targetBlock) {
        if (this.seedPercent < SEED_PERCENT_MIN || this.seedPercent > SEED_PERCENT_MAX) {
            v.owner().getPlayer().sendMessage(ChatColor.BLUE + "Seed percent set to: 10%");
            this.seedPercent = SEED_PERCENT_DEFAULT;
        }
        if (this.growPercent < GROW_PERCENT_MIN || this.growPercent > GROW_PERCENT_MAX) {
            v.owner().getPlayer().sendMessage(ChatColor.BLUE + "Growth percent set to: 10%");
            this.growPercent = GROW_PERCENT_DEFAULT;
        }
        if (this.splatterRecursions < SPLATREC_PERCENT_MIN || this.splatterRecursions > SPLATREC_PERCENT_MAX) {
            v.owner().getPlayer().sendMessage(ChatColor.BLUE + "Recursions set to: 3");
            this.splatterRecursions = SPLATREC_PERCENT_DEFAULT;
        }

        final int[][][] splat = new int[2 * v.getBrushSize() + 1][2 * v.getBrushSize() + 1][2 * v.getBrushSize() + 1];

        // Seed the array
        for (int x = 2 * v.getBrushSize(); x >= 0; x--) {
            for (int y = 2 * v.getBrushSize(); y >= 0; y--) {
                for (int z = 2 * v.getBrushSize(); z >= 0; z--) {
                    if (this.generator.nextInt(SEED_PERCENT_MAX + 1) <= this.seedPercent) {
                        splat[x][y][z] = 1;
                    }
                }
            }
        }
        // Grow the seeds
        final int gref = this.growPercent;
        final int[][][] tempSplat = new int[2 * v.getBrushSize() + 1][2 * v.getBrushSize() + 1][2 * v.getBrushSize() + 1];
        int growcheck;

        for (int r = 0; r < this.splatterRecursions; r++) {
            this.growPercent = gref - ((gref / this.splatterRecursions) * (r));
            for (int x = 2 * v.getBrushSize(); x >= 0; x--) {
                for (int y = 2 * v.getBrushSize(); y >= 0; y--) {
                    for (int z = 2 * v.getBrushSize(); z >= 0; z--) {
                        tempSplat[x][y][z] = splat[x][y][z]; // prime tempsplat

                        growcheck = 0;
                        if (splat[x][y][z] == 0) {
                            if (x != 0 && splat[x - 1][y][z] == 1) {
                                growcheck++;
                            }
                            if (y != 0 && splat[x][y - 1][z] == 1) {
                                growcheck++;
                            }
                            if (z != 0 && splat[x][y][z - 1] == 1) {
                                growcheck++;
                            }
                            if (x != 2 * v.getBrushSize() && splat[x + 1][y][z] == 1) {
                                growcheck++;
                            }
                            if (y != 2 * v.getBrushSize() && splat[x][y + 1][z] == 1) {
                                growcheck++;
                            }
                            if (z != 2 * v.getBrushSize() && splat[x][y][z + 1] == 1) {
                                growcheck++;
                            }
                        }

                        if (growcheck >= GROW_PERCENT_MIN && this.generator.nextInt(GROW_PERCENT_MAX + 1) <= this.growPercent) {
                            tempSplat[x][y][z] = 1; // prevent bleed into splat
                        }

                    }
                }
            }
            // integrate tempsplat back into splat at end of iteration
            for (int x = 2 * v.getBrushSize(); x >= 0; x--) {
                for (int y = 2 * v.getBrushSize(); y >= 0; y--) {
                    if (2 * v.getBrushSize() + 1 >= 0)
                        System.arraycopy(tempSplat[x][y], 0, splat[x][y], 0, 2 * v.getBrushSize() + 1);
                }
            }
        }
        this.growPercent = gref;
        // Fill 1x1x1 holes
        for (int x = 2 * v.getBrushSize(); x >= 0; x--) {
            for (int y = 2 * v.getBrushSize(); y >= 0; y--) {
                for (int z = 2 * v.getBrushSize(); z >= 0; z--) {
                    if (splat[Math.max(x - 1, 0)][y][z] == 1 && splat[Math.min(x + 1, 2 * v.getBrushSize())][y][z] == 1 && splat[x][Math.max(0, y - 1)][z] == 1 && splat[x][Math.min(2 * v.getBrushSize(), y + 1)][z] == 1) {
                        splat[x][y][z] = 1;
                    }
                }
            }
        }

        // Make the changes
        final double rSquared = Math.pow(v.getBrushSize() + 1, 2);

        for (int x = 2 * v.getBrushSize(); x >= 0; x--) {
            final double xSquared = Math.pow(x - v.getBrushSize() - 1, 2);

            for (int y = 2 * v.getBrushSize(); y >= 0; y--) {
                final double ySquared = Math.pow(y - v.getBrushSize() - 1, 2);

                for (int z = 2 * v.getBrushSize(); z >= 0; z--) {
                    if (splat[x][y][z] == 1 && xSquared + ySquared + Math.pow(z - v.getBrushSize() - 1, 2) <= rSquared) {
                        currentPerformer.perform(targetBlock.getRelative(-v.getBrushSize() + x, -v.getBrushSize() + y, -v.getBrushSize() + z));
                    }
                }
            }
        }

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.splatterBall(v, this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.splatterBall(v, this.getLastBlock());
    }

    @Override
    public final void info(final VoxelMessage vm) {
        if (this.seedPercent < SEED_PERCENT_MIN || this.seedPercent > SEED_PERCENT_MAX) {
            this.seedPercent = SEED_PERCENT_DEFAULT;
        }
        if (this.growPercent < GROW_PERCENT_MIN || this.growPercent > GROW_PERCENT_MAX) {
            this.growPercent = GROW_PERCENT_DEFAULT;
        }
        if (this.splatterRecursions < SPLATREC_PERCENT_MIN || this.splatterRecursions > SPLATREC_PERCENT_MAX) {
            this.splatterRecursions = SPLATREC_PERCENT_DEFAULT;
        }
        vm.brushName("Splatter Ball");
        vm.size();
        vm.custom(ChatColor.BLUE + "Seed percent set to: " + this.seedPercent / 100 + "%");
        vm.custom(ChatColor.BLUE + "Growth percent set to: " + this.growPercent / 100 + "%");
        vm.custom(ChatColor.BLUE + "Recursions set to: " + this.splatterRecursions);

    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "Splatter Ball Brush Parameters:");
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
                final int seedPercentage = ((int) Double.parseDouble(params[1]) * 100);

                if (seedPercentage >= SEED_PERCENT_MIN && seedPercentage <= SEED_PERCENT_MAX) {
                    v.sendMessage(ChatColor.AQUA + "Seed percent set to: " + String.format("%.2f", (double) seedPercentage / 100) + "%");
                    this.seedPercent = seedPercentage;
                } else {
                    v.sendMessage(ChatColor.RED + "Seed percent must be a decimal between 0.01 - 99.99!");
                }
                return;
            }

            if (params[0].startsWith("growth")) {
                final int growthPercentage = ((int) Double.parseDouble(params[1]) * 100);

                if (growthPercentage >= GROW_PERCENT_MIN && growthPercentage <= GROW_PERCENT_MAX) {
                    v.sendMessage(ChatColor.AQUA + "Growth percent set to: " + String.format("%.2f", (double) growthPercentage / 100) + "%");
                    this.growPercent = growthPercentage;
                } else {
                    v.sendMessage(ChatColor.RED + "Growth percent must be a decimal between 0.01 - 99.99!");
                }
                return;
            }

            if (params[0].startsWith("recursion")) {
                final int recursionValue = Integer.parseInt(params[1]);

                if (recursionValue >= SPLATREC_PERCENT_MIN && recursionValue <= SPLATREC_PERCENT_MAX) {
                    v.sendMessage(ChatColor.AQUA + "Recursions set to: " + recursionValue);
                    this.splatterRecursions = recursionValue;
                } else {
                    v.sendMessage(ChatColor.RED + "Recursions must be an number between 1 - 10!");
                }
                return;
            }
        } catch (NumberFormatException temp) {
temp.printStackTrace();
        }

        v.sendMessage(ChatColor.RED + "Invalid parameter! Use " + ChatColor.LIGHT_PURPLE + "'/b " + triggerHandle + " info'" + ChatColor.RED + " to display valid parameters.");
        sendPerformerMessage(triggerHandle, v);
    }

    @Override
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.addAll(Lists.newArrayList("recursion", "growth", "seed", "reset"));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();
        
        // Number variables
        argumentValues.put("recursion", Lists.newArrayList("[number]"));

        // Decimal variables
        argumentValues.put("seed", Lists.newArrayList("[decimal]"));
        argumentValues.put("growth", Lists.newArrayList("[decimal]"));

        argumentValues.putAll(super.registerArgumentValues());
        return argumentValues;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.splatterball";
    }
}
