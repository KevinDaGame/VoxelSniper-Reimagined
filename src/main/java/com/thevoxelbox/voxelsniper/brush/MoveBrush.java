package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockstate.IBlockState;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.BukkitMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;

import java.util.*;

/**
 * Moves a selection blockPositionY a certain amount. http://www.voxelwiki.com/minecraft/Voxelsniper#Move_Brush
 *
 * @author MikeMatrix
 */
public class MoveBrush extends Brush {

    /**
     * Breakable Blocks to determine if no-physics should be used.
     */
    private static final Set<IMaterial> BREAKABLE_MATERIALS = new TreeSet<>();

    static {
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.OAK_SAPLING));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.ACACIA_SAPLING));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.BIRCH_SAPLING));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.DARK_OAK_SAPLING));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.JUNGLE_SAPLING));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.SPRUCE_SAPLING));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.BLACK_BED));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.BLUE_BED));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.LIGHT_BLUE_BED));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.BROWN_BED));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.CYAN_BED));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.GRAY_BED));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.GREEN_BED));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.LIGHT_GRAY_BED));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.LIME_BED));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.MAGENTA_BED));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.ORANGE_BED));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.PINK_BED));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.PURPLE_BED));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.RED_BED));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.WHITE_BED));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.YELLOW_BED));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.TALL_GRASS));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.DEAD_BUSH));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.PISTON_HEAD));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.DANDELION));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.POPPY));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.BLUE_ORCHID));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.ALLIUM));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.AZURE_BLUET));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.RED_TULIP));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.ORANGE_TULIP));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.WHITE_TULIP));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.PINK_TULIP));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.OXEYE_DAISY));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.BROWN_MUSHROOM));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.RED_MUSHROOM));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.TORCH));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.FIRE));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.WHEAT));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.ACACIA_SIGN));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.BIRCH_SIGN));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.DARK_OAK_SIGN));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.JUNGLE_SIGN));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.OAK_SIGN));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.ACACIA_WALL_SIGN));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.BIRCH_WALL_SIGN));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.DARK_OAK_WALL_SIGN));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.JUNGLE_WALL_SIGN));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.OAK_WALL_SIGN));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.OAK_DOOR));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.ACACIA_DOOR));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.BIRCH_DOOR));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.DARK_OAK_DOOR));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.JUNGLE_DOOR));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.SPRUCE_DOOR));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.LADDER));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.RAIL));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.ACTIVATOR_RAIL));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.DETECTOR_RAIL));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.POWERED_RAIL));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.LEVER));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.STONE_PRESSURE_PLATE));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.IRON_DOOR));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.OAK_PRESSURE_PLATE));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.ACACIA_PRESSURE_PLATE));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.BIRCH_PRESSURE_PLATE));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.DARK_OAK_PRESSURE_PLATE));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.JUNGLE_PRESSURE_PLATE));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.SPRUCE_PRESSURE_PLATE));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.REDSTONE_TORCH));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.REDSTONE_WALL_TORCH));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.STONE_BUTTON));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.SNOW));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.CACTUS));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.SUGAR_CANE));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.CAKE));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.REPEATER));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.OAK_TRAPDOOR));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.ACACIA_TRAPDOOR));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.BIRCH_TRAPDOOR));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.DARK_OAK_TRAPDOOR));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.JUNGLE_TRAPDOOR));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.SPRUCE_TRAPDOOR));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.PUMPKIN_STEM));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.MELON_STEM));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.VINE));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.LILY_PAD));
        MoveBrush.BREAKABLE_MATERIALS.add(new BukkitMaterial(Material.NETHER_WART));
    }

    /**
     * Saved direction.
     */
    private final int[] moveDirections = {0, 0, 0};
    /**
     * Saved selection.
     */
    private Selection selection = null;

    /**
     *
     */
    public MoveBrush() {
        this.setName("Move");
    }

    /**
     * Moves the given selection blockPositionY the amount given in direction and saves an undo for the player.
     *
     * @param v
     * @param selection
     * @param direction
     */
    @SuppressWarnings("deprecation")
    private void moveSelection(final SnipeData v, final Selection selection, final int[] direction) {
        if (selection.getBlockStates().size() > 0) {
            final IWorld world = selection.getBlockStates().get(0).getWorld();

            final Undo undo = new Undo();
            final HashSet<IBlock> undoSet = new HashSet<>();

            final Selection newSelection = new Selection();
            final ILocation movedLocation1 = selection.getLocation1();
            movedLocation1.add(direction[0], direction[1], direction[2]);
            final ILocation movedLocation2 = selection.getLocation2();
            movedLocation2.add(direction[0], direction[1], direction[2]);
            newSelection.setLocation1(movedLocation1);
            newSelection.setLocation2(movedLocation2);
            try {
                newSelection.calculateRegion();
            } catch (final Exception exception) {
                v.getVoxelMessage().brushMessage("The new Selection has more blocks than the original selection. This should never happen!");
            }

            for (final IBlockState blockState : selection.getBlockStates()) {
                undoSet.add(blockState.getBlock());
            }
            for (final IBlockState blockState : newSelection.getBlockStates()) {
                undoSet.add(blockState.getBlock());
            }

            for (final IBlock block : undoSet) {
                undo.put(block);
            }
            v.owner().storeUndo(undo);

            for (final IBlockState blockState : selection.getBlockStates()) {
                blockState.getBlock().setMaterial(new BukkitMaterial(Material.AIR));
            }
            for (final IBlockState blockState : selection.getBlockStates()) {
                final  IBlock  affectedBlock = world.getBlock(blockState.getX() + direction[0], blockState.getY() + direction[1], blockState.getZ() + direction[2]);
                affectedBlock.setBlockData(blockState.getBlockData(), !MoveBrush.BREAKABLE_MATERIALS.contains(blockState.getMaterial()));
            }
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        if (this.selection == null) {
            this.selection = new Selection();
        }
        this.selection.setLocation1(this.getTargetBlock().getLocation());
        v.getVoxelMessage().brushMessage("Point 1 set.");

        try {
            if (this.selection.calculateRegion()) {
                this.moveSelection(v, this.selection, this.moveDirections);
                this.selection = null;
            }
        } catch (final Exception exception) {
            v.sendMessage(exception.getMessage());
        }
    }

    @Override
    protected final void powder(final SnipeData v) {
        if (this.selection == null) {
            this.selection = new Selection();
        }
        this.selection.setLocation2(this.getTargetBlock().getLocation());
        v.getVoxelMessage().brushMessage("Point 2 set.");

        try {
            if (this.selection.calculateRegion()) {
                this.moveSelection(v, this.selection, this.moveDirections);
                this.selection = null;
            }
        } catch (final Exception exception) {
            v.sendMessage(exception.getMessage());
        }
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(ChatColor.BLUE + "Move selection blockPositionY " + ChatColor.GOLD + "x:" + this.moveDirections[0] + " y:" + this.moveDirections[1] + " z:" + this.moveDirections[2]);
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.getVoxelMessage().custom(ChatColor.GOLD + "Move Brush Parameters:");
            v.getVoxelMessage().custom(ChatColor.AQUA + "/b " + triggerHandle + " x [number]  -- Set the x direction (positive => east)");
            v.getVoxelMessage().custom(ChatColor.AQUA + "/b " + triggerHandle + " y [number]  -- Set the y direction (positive => up)");
            v.getVoxelMessage().custom(ChatColor.AQUA + "/b " + triggerHandle + " z [number]  -- Set the z direction (positive => south)");
            v.getVoxelMessage().custom(ChatColor.AQUA + "/b " + triggerHandle + " reset  -- Reset brush to default values");
            v.getVoxelMessage().custom(ChatColor.BLUE + "Instructions: Use arrow and gunpowder to define two points.");
            return;
        }

        if (params[0].equalsIgnoreCase("reset")) {
            this.moveDirections[0] = 0;
            this.moveDirections[1] = 0;
            this.moveDirections[2] = 0;
            v.getVoxelMessage().custom(ChatColor.AQUA + "X, Y, Z direction set to 0. No movement will occur.");
            return;
        }

        try {
            if (params[0].equalsIgnoreCase("x")) {
                this.moveDirections[0] = Integer.parseInt(params[1]);
                v.getVoxelMessage().custom(ChatColor.AQUA + "X direction set to: " + this.moveDirections[0]);
                return;
            }

            if (params[0].equalsIgnoreCase("y")) {
                this.moveDirections[1] = Integer.parseInt(params[1]);
                v.getVoxelMessage().custom(ChatColor.AQUA + "Y direction set to: " + this.moveDirections[1]);
                return;
            }

            if (params[0].equalsIgnoreCase("z")) {
                this.moveDirections[2] = Integer.parseInt(params[1]);
                v.getVoxelMessage().custom(ChatColor.AQUA + "Z direction set to: " + this.moveDirections[2]);
                return;
            }
        } catch (NumberFormatException ignored) {
        }

        v.sendMessage(ChatColor.RED + "Invalid parameter! Use " + ChatColor.LIGHT_PURPLE + "'/b " + triggerHandle + " info'" + ChatColor.RED + " to display valid parameters.");
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("reset", "x", "y", "z"));
    }

    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();

        argumentValues.put("x", Lists.newArrayList("[number]"));
        argumentValues.put("y", Lists.newArrayList("[number]"));
        argumentValues.put("z", Lists.newArrayList("[number]"));
        
        return argumentValues;
    }

    /**
     * Selection Helper class.
     *
     * @author MikeMatrix
     */
    private class Selection {

        /**
         * Maximum amount of Blocks allowed blockPositionY the Selection.
         */
        private static final int MAX_BLOCK_COUNT = 5000000;
        /**
         * Calculated BlockStates of the selection.
         */
        private final ArrayList<IBlockState> blockStates = new ArrayList<>();
        /**
         *
         */
        private ILocation location1 = null;
        /**
         *
         */
        private ILocation location2 = null;

        /**
         * Calculates region, then saves all Blocks as BlockState.
         *
         * @return boolean success.
         * @throws Exception Message to be sent to the player.
         */
        public boolean calculateRegion() throws Exception {
            if (this.location1 != null && this.location2 != null) {
                if (this.location1.getWorld().equals(this.location2.getWorld())) {
                    final int lowX = (Math.min(this.location1.getBlockX(), this.location2.getBlockX()));
                    final int lowY = Math.min(this.location1.getBlockY(), this.location2.getBlockY());
                    final int lowZ = Math.min(this.location1.getBlockZ(), this.location2.getBlockZ());
                    final int highX = Math.max(this.location1.getBlockX(), this.location2.getBlockX());
                    final int highY = Math.max(this.location1.getBlockY(), this.location2.getBlockY());
                    final int highZ = Math.max(this.location1.getBlockZ(), this.location2.getBlockZ());
                    if (Math.abs(highX - lowX) * Math.abs(highZ - lowZ) * Math.abs(highY - lowY) > Selection.MAX_BLOCK_COUNT) {
                        throw new Exception(ChatColor.RED + "Selection size above hardcoded limit, please use a smaller selection.");
                    }
                    final IWorld world = this.location1.getWorld();
                    for (int y = lowY; y <= highY; y++) {
                        for (int x = lowX; x <= highX; x++) {
                            for (int z = lowZ; z <= highZ; z++) {
                                this.blockStates.add(world.getBlock(x, y, z).getState());
                            }
                        }
                    }
                    return true;
                }
            }
            return false;
        }

        /**
         * @return ArrayList<BlockState> calculated BlockStates of defined region.
         */
        public ArrayList<IBlockState> getBlockStates() {
            return this.blockStates;
        }

        /**
         * @return Location
         */
        public ILocation getLocation1() {
            return this.location1;
        }

        /**
         * @param location1
         */
        public void setLocation1(final ILocation location1) {
            this.location1 = location1;
        }

        /**
         * @return Location
         */
        public ILocation getLocation2() {
            return this.location2;
        }

        /**
         * @param location2
         */
        public void setLocation2(final ILocation location2) {
            this.location2 = location2;
        }
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.move";
    }
}
