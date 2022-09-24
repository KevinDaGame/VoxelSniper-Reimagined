package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.brush.perform.PerformerBrush;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.util.VoxelList;
import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Splatter_Overlay_Brush
 *
 * @author Gavjenks Splatterized blockPositionY Giltwist
 */
public class SplatterOverlayBrush extends PerformerBrush {

    private static final int GROW_PERCENT_MIN = 1;
    private static final int GROW_PERCENT_DEFAULT = 1000;
    private static final int GROW_PERCENT_MAX = 9999;
    private static final int SEED_PERCENT_MIN = 1;
    private static final int SEED_PERCENT_DEFAULT = 1000;
    private static final int SEED_PERCENT_MAX = 9999;
    private static final int SPLATREC_PERCENT_MIN = 1;
    private static final int SPLATREC_PERCENT_DEFAULT = 3;
    private static final int SPLATREC_PERCENT_MAX = 10;
    private final int yOffset = 0;
    private final boolean randomizeHeight = false;
    private final Random generator = new Random();
    private int seedPercent; // Chance block on first pass is made active
    private int growPercent; // chance block on recursion pass is made active
    private int splatterRecursions; // How many times you grow the seeds
    private int depth = 3;

    private boolean allBlocks = false;
    private boolean useVoxelList = false;

    /**
     *
     */
    public SplatterOverlayBrush() {
        this.setName("Splatter Overlay");
    }

    private void sOverlay(final SnipeData v) {

        // Splatter Time
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
                if (2 * v.getBrushSize() + 1 >= 0)
                    System.arraycopy(tempSplat[x], 0, splat[x], 0, 2 * v.getBrushSize() + 1);
            }
        }
        this.growPercent = gref;

        final int[][] memory = new int[2 * v.getBrushSize() + 1][2 * v.getBrushSize() + 1];
        final double brushSizeSquared = Math.pow(v.getBrushSize() + 0.5, 2);

        for (int z = v.getBrushSize(); z >= -v.getBrushSize(); z--) {
            for (int x = v.getBrushSize(); x >= -v.getBrushSize(); x--) {
                for (int y = this.getTargetBlock().getY(); y > this.getMinHeight(); y--) {
                    // start scanning from the height you clicked at
                    if (memory[x + v.getBrushSize()][z + v.getBrushSize()] != 1) {
                        // if haven't already found the surface in this column
                        if ((Math.pow(x, 2) + Math.pow(z, 2)) <= brushSizeSquared && splat[x + v.getBrushSize()][z + v.getBrushSize()] == 1) {
                            // if inside of the column && if to be splattered
                            final VoxelMaterial check = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y + 1, this.getTargetBlock().getZ() + z);
                            if (check.isAir() || check == VoxelMaterial.WATER) {
                                // must start at surface... this prevents it filling stuff in if you click in a wall
                                // and it starts out below surface.
                                final VoxelMaterial currentBlock = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y, this.getTargetBlock().getZ() + z);
                                if (this.isOverrideableMaterial(v.getVoxelList(), currentBlock)) {
                                    final int depth = this.randomizeHeight ? generator.nextInt(this.depth) : this.depth;

                                    for (int d = this.depth - 1; ((this.depth - d) <= depth); d--) {
                                        if (!this.clampY(this.getTargetBlock().getX() + x, y - d, this.getTargetBlock().getZ() + z).getMaterial().isAir()) {
                                            // fills down as many layers as you specify in parameters
                                            this.currentPerformer.perform(this.clampY(this.getTargetBlock().getX() + x, y - d + yOffset, this.getTargetBlock().getZ() + z));
                                            // stop it from checking any other blocks in this vertical 1x1 column.
                                            memory[x + v.getBrushSize()][z + v.getBrushSize()] = 1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    private void soverlayTwo(final SnipeData v) {
        // Splatter Time
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
        final int[][] tempsplat = new int[2 * v.getBrushSize() + 1][2 * v.getBrushSize() + 1];
        int growcheck;

        for (int r = 0; r < this.splatterRecursions; r++) {
            this.growPercent = gref - ((gref / this.splatterRecursions) * (r));

            for (int x = 2 * v.getBrushSize(); x >= 0; x--) {
                for (int y = 2 * v.getBrushSize(); y >= 0; y--) {
                    tempsplat[x][y] = splat[x][y]; // prime tempsplat

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
                        tempsplat[x][y] = 1; // prevent bleed into splat
                    }

                }

            }
            // integrate tempsplat back into splat at end of iteration
            for (int x = 2 * v.getBrushSize(); x >= 0; x--) {
                if (2 * v.getBrushSize() + 1 >= 0)
                    System.arraycopy(tempsplat[x], 0, splat[x], 0, 2 * v.getBrushSize() + 1);
            }
        }
        this.growPercent = gref;

        final int[][] memory = new int[v.getBrushSize() * 2 + 1][v.getBrushSize() * 2 + 1];
        final double brushSizeSquared = Math.pow(v.getBrushSize() + 0.5, 2);

        for (int z = v.getBrushSize(); z >= -v.getBrushSize(); z--) {
            for (int x = v.getBrushSize(); x >= -v.getBrushSize(); x--) {
                for (int y = this.getTargetBlock().getY(); y > this.getMinHeight(); y--) { // start scanning from the height you clicked at
                    if (memory[x + v.getBrushSize()][z + v.getBrushSize()] != 1) { // if haven't already found the surface in this column
                        if ((Math.pow(x, 2) + Math.pow(z, 2)) <= brushSizeSquared && splat[x + v.getBrushSize()][z + v.getBrushSize()] == 1) { // if inside of the column...&& if to be splattered
                            if (!this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y - 1, this.getTargetBlock().getZ() + z).isAir()) { // if not a floating block (like one of Notch'world pools)
                                if (this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y + 1, this.getTargetBlock().getZ() + z).isAir()) { // must start at surface... this prevents it filling stuff in if
                                    final VoxelMaterial currentBlock = this.getBlockMaterialAt(this.getTargetBlock().getX() + x, y, this.getTargetBlock().getZ() + z);
                                    if (this.isOverrideableMaterial(v.getVoxelList(), currentBlock)) {
                                        final int depth = this.randomizeHeight ? generator.nextInt(this.depth) : this.depth;
                                        for (int d = 1; (d < depth + 1); d++) {
                                            this.currentPerformer.perform(this.clampY(this.getTargetBlock().getX() + x, y + d + yOffset, this.getTargetBlock().getZ() + z)); // fills down as many layers as you specify
                                            // in parameters
                                            memory[x + v.getBrushSize()][z + v.getBrushSize()] = 1; // stop it from checking any other blocks in this vertical 1x1 column.
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    private boolean isIgnoredBlock(VoxelMaterial material) {
        return material.equals(VoxelMaterial.WATER) || material.isTransparent() || material.equals(VoxelMaterial.CACTUS);
    }

    private boolean isOverrideableMaterial(VoxelList list, VoxelMaterial material) {
        if (this.useVoxelList) {
            return list.contains(material);
        }

        if (allBlocks && !material.isAir()) {
            return true;
        }

        return VoxelMaterial.OVERRIDABLE_MATERIALS.contains(material);
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.sOverlay(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.soverlayTwo(v);
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
        vm.brushName(this.getName());
        vm.size();
        vm.custom(Messages.BRUSH_SEED_PERCENT_SET.replace("%val%", String.valueOf(this.seedPercent / 100)));
        vm.custom(Messages.GROWTH_PERCENT_SET.replace("%growPercent%", String.valueOf(this.growPercent / 100)));
        vm.custom(Messages.BRUSH_RECURSION_SET.replace("%val%", String.valueOf(this.splatterRecursions)));
        vm.custom(Messages.Y_OFFSET_SET.replace("%yOffset%", String.valueOf(this.yOffset)));
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.SPLATTER_OVERLAY_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        if (params[0].equalsIgnoreCase("reset")) {
            this.seedPercent = SEED_PERCENT_DEFAULT;
            this.growPercent = GROW_PERCENT_DEFAULT;
            this.splatterRecursions = SPLATREC_PERCENT_DEFAULT;
            this.depth = 3;
            this.allBlocks = false;
            v.sendMessage(Messages.BRUSH_RESET_DEFAULT);
            return;
        }

        if (params[0].startsWith("mode")) {
            if (!this.allBlocks && !this.useVoxelList) {
                this.allBlocks = true;
                this.useVoxelList = false;
            } else if (this.allBlocks && !this.useVoxelList) {
                this.allBlocks = false;
                this.useVoxelList = true;
            } else if (!this.allBlocks && this.useVoxelList) {
                this.allBlocks = false;
                this.useVoxelList = false;
            }
            String mode = this.allBlocks ? "all" : (this.useVoxelList ? "custom defined" : "natural");
            v.sendMessage(Messages.OVERLAY_ON_MODE_DEPTH.replace("%depth%", String.valueOf(this.depth)).replace("%mode%", mode));
            return;
        }

        try {
            if (params[0].startsWith("depth")) {
                this.depth = Integer.parseInt(params[1]);

                if (this.depth < 1) {
                    this.depth = 1;
                }

                v.sendMessage(Messages.OVERLAY_DEPTH_SET.replace("%depth%", String.valueOf(this.depth)));
                return;
            }

            if (params[0].startsWith("seed")) {
                final int temp = ((int) Double.parseDouble(params[1]) * 100);

                if (temp >= SEED_PERCENT_MIN && temp <= SEED_PERCENT_MAX) {
                    v.sendMessage(Messages.BRUSH_SEED_PERCENT_SET.replace("%val%", String.valueOf(temp / 100)));
                    this.seedPercent = temp;
                } else {
                    v.sendMessage(Messages.SEED_PERCENT_RANGE);
                }
                return;
            }

            if (params[0].startsWith("growth")) {
                final int temp = ((int) Double.parseDouble(params[1]) * 100);

                if (temp >= GROW_PERCENT_MIN && temp <= GROW_PERCENT_MAX) {
                    v.sendMessage(Messages.GROWTH_PERCENT_SET.replace("%growPercent%", String.valueOf(temp / 100)));
                    this.growPercent = temp;
                } else {
                    v.sendMessage(Messages.GROWTH_PERCENT_RANGE.replace("%min%", "0.01").replace("%max%", "99.99"));
                }
                return;
            }

            if (params[0].startsWith("recursion")) {
                final int temp = Integer.parseInt(params[1]);

                if (temp >= SPLATREC_PERCENT_MIN && temp <= SPLATREC_PERCENT_MAX) {
                    v.sendMessage(Messages.BRUSH_RECURSION_SET.replace("%val%", String.valueOf(temp)));
                    this.splatterRecursions = temp;
                } else {
                    v.sendMessage(Messages.SPLATTER_BALL_BRUSH_RECURSION_RANGE);
                }
                return;
            }
        } catch (NumberFormatException temp) {
            temp.printStackTrace();
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        sendPerformerMessage(triggerHandle, v);
    }

    @Override
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.addAll(Lists.newArrayList("recursion", "growth", "seed", "reset", "depth", "mode"));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();

        // Number variables
        argumentValues.put("recursion", Lists.newArrayList("[number]"));
        argumentValues.put("depth", Lists.newArrayList("[number]"));

        // Decimal variables
        argumentValues.put("seed", Lists.newArrayList("[decimal]"));
        argumentValues.put("growth", Lists.newArrayList("[decimal]"));

        argumentValues.putAll(super.registerArgumentValues());
        return argumentValues;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.splatteroverlay";
    }
}
