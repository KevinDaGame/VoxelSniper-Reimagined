package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.location.BukkitLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.world.BukkitWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;

// Proposal: Use /v and /vr for leave and wood material // or two more parameters -- Monofraps

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#VoxelTrees_Brush
 *
 * @author Ghost8700 @ Voxel
 */
public class GenerateTreeBrush extends Brush {

    // Tree Variables.
    private final Random random = new Random();
    private final ArrayList<IBlock> branchBlocks = new ArrayList<>();
    private Undo undo;
    // If these default values are edited. Remember to change default values in the default preset.
    private VoxelMaterial leavesMaterial = VoxelMaterial.OAK_LEAVES;
    private VoxelMaterial woodMaterial = VoxelMaterial.OAK_WOOD;
    private boolean rootFloat = false;
    private int startHeight = 0;
    private int rootLength = 9;
    private int maxRoots = 2;
    private int minRoots = 1;
    private int thickness = 1;
    private int slopeChance = 40;
    private final int twistChance = 5; // This is a hidden value not available through Parameters. Otherwise messy.
    private int minimumHeight = 14;
    private int maximumHeight = 18;
    private int branchLength = 8;
    private int nodeMax = 4;
    private int nodeMin = 3;
    private ILocation location;

    /**
     *
     */
    public GenerateTreeBrush() {
        this.setName("Generate Tree");
    }

    // Branch Creation based on direction chosen from the parameters passed.
    private void branchCreate(final int xDirection, final int zDirection) {

        // Sets branch origin.
        final ILocation origin = new BukkitLocation(((BukkitLocation)location).getLocation());

        // Sets direction preference.
        final int xPreference = this.random.nextInt(60) + 20;
        final int zPreference = this.random.nextInt(60) + 20;

        // Iterates according to branch length.
        for (int r = 0; r < this.branchLength; r++) {

            // Alters direction according to preferences.
            if (this.chance(xPreference)) {
                location.addX(xDirection);
            }
            if (this.chance(zPreference)) {
                location.addZ(zDirection);
            }

            // 50% chance to increase elevation every second block.
            if (Math.abs(r % 2) == 1) {
                location.addY(this.random.nextInt(2));
            }

            // Add block to undo function.
            if (location.getBlock().getMaterial() != woodMaterial) {
                this.undo.put(location.getClampedBlock());
            }

            // Creates a branch block.
            location.getBlock().setBlockData(woodMaterial.createBlockData(), false);
            this.branchBlocks.add(location.getClampedBlock());
        }

        // Resets the origin
        location = new BukkitLocation(((BukkitLocation)origin).getLocation());
    }

    private void leafNodeCreate() {
        // Generates the node size.
        final int nodeRadius = this.random.nextInt(this.nodeMax - this.nodeMin + 1) + this.nodeMin;
        final double bSquared = Math.pow(nodeRadius + 0.5, 2);

        // Lowers the current block in order to start at the bottom of the node.
        location.addY(-2);

        for (int z = nodeRadius; z >= 0; z--) {
            final double zSquared = Math.pow(z, 2);

            for (int x = nodeRadius; x >= 0; x--) {
                final double xSquared = Math.pow(x, 2);

                for (int y = nodeRadius; y >= 0; y--) {
                    if ((xSquared + Math.pow(y, 2) + zSquared) <= bSquared) {
                        // Chance to skip creation of a block.
                        if (this.chance(70)) {
                            // If block is Air, create a leaf block.
                            if (location.getBlock().getRelative(x, y, z).getMaterial() == VoxelMaterial.AIR) {
                                // Adds block to undo function.
                                if (location.getBlock().getRelative(x, y, z).getBlockData().getMaterial() != leavesMaterial) {
                                    this.undo.put(location.getBlock().getRelative(x, y, z));
                                }
                                // Creates block.
                                location.getClampedBlock().getRelative(x, y, z).setBlockData(leavesMaterial.createBlockData(), false);
                            }
                        }
                        for (int dx : new int[]{-1, 1}) {
                            for (int dy : new int[]{-1, 1}) {
                                for (int dz : new int[]{-1, 1}) {
                                    this.createLeaf(x * dx, y * dy, z * dz);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void createLeaf(int x, int y, int z) {
        if (location.getBlock().getRelative(x, y, z).getMaterial() == VoxelMaterial.AIR) {
            this.undo.put(location.getClampedBlock().getRelative(x, y, z));
            location.getBlock().getRelative(x, y, z).setBlockData(leavesMaterial.createBlockData(), false);
        }
    }

    /**
     * Code Concerning Root Generation.
     *
     * @param xDirection
     * @param zDirection
     */
    private void rootCreate(final int xDirection, final int zDirection) {
        // Sets Origin.
        final ILocation origin = new BukkitLocation(((BukkitLocation)location).getLocation());

        // Generates the number of roots to create.
        final int roots = this.random.nextInt(this.maxRoots - this.minRoots + 1) + this.minRoots;

        // A roots preference to move along the X and Y axis.
        // Loops for each root to be created.
        for (int i = 0; i < roots; i++) {
            // Pushes the root'world starting point out from the center of the tree.
            for (int t = 0; t < this.thickness - 1; t++) {
                location.addX(xDirection);
                location.addZ(zDirection);
            }

            // Generate directional preference between 30% and 70%
            final int xPreference = this.random.nextInt(30) + 40;
            final int zPreference = this.random.nextInt(30) + 40;

            for (int j = 0; j < this.rootLength; j++) {
                // For the purposes of this algorithm, logs aren't considered solid.

                // If not solid then...
                // Save for undo function
                if (location.getBlock().getMaterial() != woodMaterial) {
                    this.undo.put(location.getClampedBlock());

                    // Place log block.
                    location.getClampedBlock().setBlockData(woodMaterial.createBlockData(), false);
                } else {
                    // If solid then...
                    // End loop
                    break;
                }
                List<VoxelMaterial> blocks = Arrays.asList(VoxelMaterial.WATER, VoxelMaterial.SNOW, VoxelMaterial.OAK_LOG, VoxelMaterial.BIRCH_LOG, VoxelMaterial.ACACIA_LOG, VoxelMaterial.DARK_OAK_LOG, VoxelMaterial.SPRUCE_LOG, VoxelMaterial.JUNGLE_LOG);
                // Checks is block below is solid
                if (blocks.contains(location.getClampedBlock().getRelative(0, -1, 0).getMaterial())) {
                    // Move down if solid.
                    location.addY(-1);
                    if (this.rootFloat) {
                        if (this.chance(xPreference)) {
                            location.addX(xDirection);
                        }
                        if (this.chance(zPreference)) {
                            location.addZ(zDirection);
                        }
                    }
                } else {
                    // If solid then move.
                    if (this.chance(xPreference)) {
                        location.addX(xDirection);
                    }
                    if (this.chance(zPreference)) {
                        location.addZ(zDirection);
                    }
                    // Checks if new location is solid, if not then move down.
                    if (blocks.contains(location.getClampedBlock().getRelative(0, -1, 0).getMaterial())) {
                        location.addY(-1);
                    }
                }
            }

            // Reset origin.
            location = new BukkitLocation(((BukkitLocation)origin).getLocation());

        }
    }

    private void rootGen() {
        // Quadrant 1
        this.rootCreate(1, 1);

        // Quadrant 2
        this.rootCreate(-1, 1);

        // Quadrant 3
        this.rootCreate(1, -1);

        // Quadrant 4
        this.rootCreate(-1, -1);
    }

    private void trunkCreate() {
        // Creates true circle discs of the set size using the wood type selected.
        final double bSquared = Math.pow(this.thickness + 0.5, 2);

        for (int x = this.thickness; x >= 0; x--) {
            final double xSquared = Math.pow(x, 2);

            for (int z = this.thickness; z >= 0; z--) {
                if ((xSquared + Math.pow(z, 2)) <= bSquared) {
                    for (int dx : new int[]{-1, 1}) {
                        for (int dz : new int[]{-1, 1}) {
                            this.createTrunk(x * dx, z * dz);
                        }

                    }
                }
            }
        }
    }

    private void createTrunk(int x, int z) {
        // If block is air, then create a block.
        if (location.getBlock().getRelative(x, 0, z).getMaterial() == VoxelMaterial.AIR) {
            // Adds block to undo function.
            this.undo.put(location.getClampedBlock().getRelative(x, 0, z));
            // Creates block.
            location.getClampedBlock().getRelative(x, 0, z).setBlockData(woodMaterial.createBlockData(), false);
        }
    }

    /*
     *
     * Code Concerning Trunk Generation
     */
    private void trunkGen() {
        // Sets Origin
        final ILocation origin = new BukkitLocation(((BukkitLocation)location).getLocation());

        // ----------
        // Main Trunk
        // ----------
        // Sets diretional preferences.
        int xPreference = this.random.nextInt(this.slopeChance);
        int zPreference = this.random.nextInt(this.slopeChance);

        // Sets direction.
        int xDirection = this.chance(50) ? -1 : 1;
        int zDirection = this.chance(50) ? -1 : 1;


        // Generates a height for trunk.
        int height = this.random.nextInt(this.maximumHeight - this.minimumHeight + 1) + this.minimumHeight;

        for (int p = 0; p < height; p++) {
            if (p > 3) {
                if (this.chance(this.twistChance)) {
                    xDirection *= -1;
                }
                if (this.chance(this.twistChance)) {
                    zDirection *= -1;
                }
                if (this.chance(xPreference)) {
                    location.addX(xDirection);
                }
                if (this.chance(zPreference)) {
                    location.addZ(zDirection);
                }
            }

            // Creates trunk section
            this.trunkCreate();

            // Mos up for next section
            location.addY(1);
        }

        // Generates branchs at top of trunk for each quadrant.
        this.branchCreate(1, 1);
        this.branchCreate(-1, 1);
        this.branchCreate(1, -1);
        this.branchCreate(-1, -1);

        // Reset Origin for next trunk.
        location = new BukkitLocation(((BukkitLocation)origin).getLocation());
        location.addY(4);

        // ---------------
        // Secondary Trunk
        // ---------------
        // Sets diretional preferences.
        xPreference = this.random.nextInt(this.slopeChance);
        zPreference = this.random.nextInt(this.slopeChance);

        // Sets direction.
        xDirection = 1;
        if (this.chance(50)) {
            xDirection = -1;
        }

        zDirection = 1;
        if (this.chance(50)) {
            zDirection = -1;
        }

        // Generates a height for trunk.
        height = this.random.nextInt(this.maximumHeight - this.minimumHeight + 1) + this.minimumHeight;

        if (height > 4) {
            for (int p = 0; p < height; p++) {
                if (this.chance(twistChance)) {
                    xDirection *= -1;
                }
                if (this.chance(twistChance)) {
                    zDirection *= -1;
                }
                if (this.chance(xPreference)) {
                    location.addX(xDirection);
                }
                if (this.chance(zPreference)) {
                    location.addZ(zDirection);
                }

                // Creates a trunk section
                this.trunkCreate();

                // Mos up for next section
                location.addY(1);
            }

            // Generates branchs at top of trunk for each quadrant.
            this.branchCreate(1, 1);
            this.branchCreate(-1, 1);
            this.branchCreate(1, -1);
            this.branchCreate(-1, -1);
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.undo = new Undo();

        this.branchBlocks.clear();

        // Sets the location variables.

        location = new BukkitLocation(new Location(((BukkitWorld)this.getTargetBlock().getWorld()).world(), this.getTargetBlock().getX(), this.getTargetBlock().getY() + this.startHeight, this.getTargetBlock().getZ()));

        // Generates the roots.
        this.rootGen();
        // Generates the trunk, which also generates branches.
        this.trunkGen();

        // Each branch block was saved in an array. This is now fed through an array.
        // This array takes each branch block and constructs a leaf node around it.
        for (final IBlock block : this.branchBlocks) {
            location = new BukkitLocation(new Location(((BukkitWorld)block.getWorld()).world(), block.getX(), block.getY(), block.getZ()));
            this.leafNodeCreate();
        }

        // Ends the undo function and mos on.
        v.owner().storeUndo(this.undo);
    }

    // The Powder currently does nothing extra.
    @Override
    protected final void powder(final SnipeData v) {
        this.arrow(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
    }

    /**
     * Method handling randomization. Returns true [chance] % of the time.
     *
     * @param chance The chance of returning true (%)
     * @return boolean
     */
    public boolean chance(int chance) {

        return random.nextInt(100) < chance;
    }


    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {

        if (params[0].equalsIgnoreCase("info")) {
            if (params.length == 1 || params[1].equals("1")) {
                v.sendMessage(Messages.GEN_TREE_BRUSH_USAGE_1.replace("%triggerHandle%",triggerHandle));
            } else if (params[1].equals("2")) {
                v.sendMessage(Messages.GEN_TREE_BRUSH_USAGE_2.replace("%triggerHandle%",triggerHandle));
            }
            return;
        }
        try {
            if (params[0].equalsIgnoreCase("leaves")) {
                VoxelMaterial material = VoxelMaterial.getMaterial(params[1]);
                if (material == null) throw new IllegalArgumentException();

                if (material == VoxelMaterial.OAK_LEAVES || material == VoxelMaterial.ACACIA_LEAVES || material == VoxelMaterial.SPRUCE_LEAVES
                        || material == VoxelMaterial.JUNGLE_LEAVES || material == VoxelMaterial.DARK_OAK_LEAVES || material == VoxelMaterial.BIRCH_LEAVES) {
                    this.leavesMaterial = material;
                    v.sendMessage(Messages.LEAVES_MAT_SET.replace("%leavesMaterial.name%",String.valueOf(this.leavesMaterial.getName())));
                } else {
                    throw new IllegalArgumentException();
                }
                return;
            }

            if (params[0].equalsIgnoreCase("wood")) {
                VoxelMaterial material = VoxelMaterial.getMaterial(params[1]);
                if (material == null) throw new IllegalArgumentException();

                if (material == VoxelMaterial.OAK_WOOD || material == VoxelMaterial.ACACIA_WOOD || material == VoxelMaterial.SPRUCE_WOOD
                        || material == VoxelMaterial.JUNGLE_WOOD || material == VoxelMaterial.DARK_OAK_WOOD || material == VoxelMaterial.BIRCH_WOOD) {
                    this.woodMaterial = material;
                    v.sendMessage(Messages.WOOD_LOG_MAT_SET.replace("%woodMaterial.name%",String.valueOf(this.woodMaterial.getName())));
                } else {
                    throw new IllegalArgumentException();
                }
                return;
            }
        } catch (IllegalArgumentException e) {
                v.sendMessage(Messages.INVALID_TYPE);
            return;
        }

        try {
            if (params[0].equalsIgnoreCase("thickness")) { // Tree Thickness
                this.thickness = Integer.parseInt(params[1]);
                v.sendMessage(Messages.THICKNESS_SET.replace("%thickness%",String.valueOf(this.thickness)));
                return;
            }

            if (params[0].equalsIgnoreCase("startHeight")) { // Starting Height
                this.startHeight = Integer.parseInt(params[1]);
                v.sendMessage(Messages.START_HEIGHT_SET.replace("%startHeight%",String.valueOf(this.startHeight)));
                return;
            }

            if (params[0].equalsIgnoreCase("slope")) { // Trunk Slope Chance
                this.slopeChance = Integer.parseInt(params[1]);
                v.sendMessage(Messages.TRUNK_SLOPE_SET.replace("%this.slopeChance%",String.valueOf(this.slopeChance)));
                return;
            }

            if (params[0].equalsIgnoreCase("branchLength")) { // Branch Length
                this.branchLength = Integer.parseInt(params[1]);
                v.sendMessage(Messages.BRANCH_LENGTH_SET.replace("%branchLength%",String.valueOf(this.branchLength)));
                return;
            }

            if (params[0].equalsIgnoreCase("rootLength")) { // Root Length
                this.rootLength = Integer.parseInt(params[1]);
                v.sendMessage(Messages.ROOT_LENGTH_SET.replace("%rootLength%",String.valueOf(this.rootLength)));
                return;
            }

            if (params[0].equalsIgnoreCase("rootFloat")) { // Root Float
                this.rootFloat = Boolean.parseBoolean(params[1]);
                v.sendMessage(Messages.FLOATING_ROOTS_SET.replace("%rootFloat%",String.valueOf(this.rootFloat)));
                return;
            }

            if (params[0].equalsIgnoreCase("rootMin")) { // Minimum Roots
                this.minRoots = Integer.parseInt(params[1]);
                if (this.minRoots > this.maxRoots) {
                    this.minRoots = this.maxRoots;
                    v.sendMessage(Messages.MIN_ROOTS_ABOVE_MAX.replace("%this.minRoots%",String.valueOf(this.minRoots)));
                } else {
                    v.sendMessage(Messages.MIN_ROOTS_SET.replace("%minRoots%",String.valueOf(this.minRoots)));
                }
                return;
            }

            if (params[0].equalsIgnoreCase("rootMax")) { // Maximum Roots
                this.maxRoots = Integer.parseInt(params[1]);
                if (this.minRoots > this.maxRoots) {
                    this.maxRoots = this.minRoots;
                    v.sendMessage(Messages.MAX_ROOTS_BELOW_MIN.replace("%this.minRoots%",String.valueOf(this.minRoots)));
                } else {
                    v.sendMessage(Messages.MAX_ROOTS_SET.replace("%maxRoots%",String.valueOf(this.maxRoots)));
                }
                return;
            }

            if (params[0].equalsIgnoreCase("minHeight")) { // Height Minimum
                this.minimumHeight = Integer.parseInt(params[1]);
                if (this.minimumHeight > this.maximumHeight) {
                    this.minimumHeight = this.maximumHeight;
                    v.sendMessage(Messages.MIN_HEIGHT_ABOVE_MAX.replace("%this.minimumHeight%",String.valueOf(this.minimumHeight)));
                } else {
                    v.sendMessage(Messages.MIN_HEIGHT_SET.replace("%minimumHeight%",String.valueOf(this.minimumHeight)));
                }
                return;
            }

            if (params[0].equalsIgnoreCase("maxHeight")) { // Height Maximum
                this.maximumHeight = Integer.parseInt(params[1]);
                if (this.minimumHeight > this.maximumHeight) {
                    this.maximumHeight = this.minimumHeight;
                    v.sendMessage(Messages.MAX_HEIGHT_BELOW_MIN.replace("%this.maximumHeight%",String.valueOf(this.maximumHeight)));
                } else {
                    v.sendMessage(Messages.MAX_HEIGHT_SET.replace("%maximumHeight%",String.valueOf(this.maximumHeight)));
                }
                return;
            }

            if (params[0].equalsIgnoreCase("leavesMax")) { // Leaf Node Max Size
                this.nodeMax = Integer.parseInt(params[1]);
                v.sendMessage(Messages.LEAF_THICKNESS_MAX_SET.replace("%nodeMax%",String.valueOf(this.nodeMax)));
                return;
            }

            if (params[0].equalsIgnoreCase("leavesMin")) { // Leaf Node Min Size
                this.nodeMin = Integer.parseInt(params[1]);
                v.sendMessage(Messages.LEAF_THICKNESS_MIN_SET.replace("%nodeMin%",String.valueOf(this.nodeMin)));
                return;
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException temp) {
temp.printStackTrace();

        }

        if (params[0].equalsIgnoreCase("default")) { // Default settings.
            this.leavesMaterial = VoxelMaterial.OAK_LEAVES;
            this.woodMaterial = VoxelMaterial.OAK_WOOD;
            this.rootFloat = false;
            this.startHeight = 0;
            this.rootLength = 9;
            this.maxRoots = 2;
            this.minRoots = 1;
            this.thickness = 1;
            this.slopeChance = 40;
            this.minimumHeight = 14;
            this.maximumHeight = 18;
            this.branchLength = 8;
            this.nodeMax = 4;
            this.nodeMin = 3;
            v.sendMessage(Messages.BRUSH_RESET_DEFAULT);
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("leaves", "wood", "thickness", "startHeight", "branchLength", "slope", "rootLength",
                "rootFloat", "info", "rootMin", "rootMax", "minHeight", "maxHeight", "leavesMin", "leavesMax", "default"));
    }

    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();

        // Number variables
        argumentValues.put("thickness", Lists.newArrayList("[number]"));
        argumentValues.put("startHeight", Lists.newArrayList("[number]"));
        argumentValues.put("slope", Lists.newArrayList("[number]"));
        argumentValues.put("branchLength", Lists.newArrayList("[number]"));
        argumentValues.put("rootLength", Lists.newArrayList("[number]"));
        argumentValues.put("rootMin", Lists.newArrayList("[number]"));
        argumentValues.put("rootMax", Lists.newArrayList("[number]"));
        argumentValues.put("minHeight", Lists.newArrayList("[number]"));
        argumentValues.put("maxHeight", Lists.newArrayList("[number]"));
        argumentValues.put("leavesMin", Lists.newArrayList("[number]"));
        argumentValues.put("leavesMax", Lists.newArrayList("[number]"));

        // Info variables
        argumentValues.put("info", Lists.newArrayList("1", "2"));

        // True/false variable
        argumentValues.put("rootFloat", Lists.newArrayList("true", "false"));

        // Wood material variables
        argumentValues.put("wood", Lists.newArrayList(VoxelMaterial.OAK_WOOD.getName(), Material.ACACIA_WOOD.name(), Material.SPRUCE_WOOD.name(), Material.JUNGLE_WOOD.name(),
                Material.DARK_OAK_WOOD.name(), Material.BIRCH_WOOD.name()));

        // Leaves material variables
        argumentValues.put("leaves", Lists.newArrayList(VoxelMaterial.OAK_LEAVES.getName(), Material.ACACIA_LEAVES.name(), Material.SPRUCE_LEAVES.name(), Material.JUNGLE_LEAVES.name(),
                Material.DARK_OAK_LEAVES.name(), Material.BIRCH_LEAVES.name()));

        return argumentValues;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.generatetree";
    }
}
