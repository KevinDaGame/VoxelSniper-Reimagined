package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockstate.IBlockState;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.BukkitMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

/**
 * Moves a selection blockPositionY a certain amount. http://www.voxelwiki.com/minecraft/Voxelsniper#Move_Brush
 *
 * @author MikeMatrix
 */
public class MoveBrush extends Brush {

    private static class ComponentException extends Exception implements ComponentLike {
        private final ComponentLike component;

        ComponentException(ComponentLike component) {
            this.component = component;
        }

        @Override
        public String getMessage() {
            return LegacyComponentSerializer.legacySection().serialize(this.asComponent());
        }

        @Override
        public @NotNull Component asComponent() {
            return component.asComponent();
        }
    }

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
            } catch (final ComponentException exception) {
                v.sendMessage(exception);
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
        v.getVoxelMessage().brushMessage(Messages.POINT_1_SET);

        try {
            if (this.selection.calculateRegion()) {
                this.moveSelection(v, this.selection, this.moveDirections);
                this.selection = null;
            }
        } catch (final ComponentException exception) {
            v.sendMessage(exception);
        }
    }

    @Override
    protected final void powder(final SnipeData v) {
        if (this.selection == null) {
            this.selection = new Selection();
        }
        this.selection.setLocation2(this.getTargetBlock().getLocation());
        v.getVoxelMessage().brushMessage(Messages.POINT_2_SET);

        try {
            if (this.selection.calculateRegion()) {
                this.moveSelection(v, this.selection, this.moveDirections);
                this.selection = null;
            }
        } catch (final ComponentException exception) {
            v.sendMessage(exception);
        }
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(Messages.MOVE_BRUSH_SELECTION.replace("%x%", String.valueOf(this.moveDirections[0])).replace("%y%", String.valueOf(this.moveDirections[1])).replace("%z%", String.valueOf(this.moveDirections[2])));
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.MOVE_BRUSH_USAGE.replace("%triggerHandle%",triggerHandle));
            return;
        }

        if (params[0].equalsIgnoreCase("reset")) {
            this.moveDirections[0] = 0;
            this.moveDirections[1] = 0;
            this.moveDirections[2] = 0;
            v.getVoxelMessage().custom(Messages.MOVE_BRUSH_RESET);
            return;
        }

        try {
            if (params[0].equalsIgnoreCase("x")) {
                this.moveDirections[0] = Integer.parseInt(params[1]);
                v.getVoxelMessage().custom(Messages.MOVE_DIRECTION_SET.replace("%dir%", "X").replace("%val%", String.valueOf(this.moveDirections[0])));
                return;
            }

            if (params[0].equalsIgnoreCase("y")) {
                this.moveDirections[1] = Integer.parseInt(params[1]);
                v.getVoxelMessage().custom(Messages.MOVE_DIRECTION_SET.replace("%dir%", "Y").replace("%val%", String.valueOf(this.moveDirections[1])));
                return;
            }

            if (params[0].equalsIgnoreCase("z")) {
                this.moveDirections[2] = Integer.parseInt(params[1]);
                v.getVoxelMessage().custom(Messages.MOVE_DIRECTION_SET.replace("%dir%", "Z").replace("%val%", String.valueOf(this.moveDirections[2])));
                return;
            }
        } catch (NumberFormatException temp) {
temp.printStackTrace();
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
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
        public boolean calculateRegion() throws ComponentException {
            if (this.location1 != null && this.location2 != null) {
                if (this.location1.getWorld().equals(this.location2.getWorld())) {
                    final int lowX = (Math.min(this.location1.getBlockX(), this.location2.getBlockX()));
                    final int lowY = Math.min(this.location1.getBlockY(), this.location2.getBlockY());
                    final int lowZ = Math.min(this.location1.getBlockZ(), this.location2.getBlockZ());
                    final int highX = Math.max(this.location1.getBlockX(), this.location2.getBlockX());
                    final int highY = Math.max(this.location1.getBlockY(), this.location2.getBlockY());
                    final int highZ = Math.max(this.location1.getBlockZ(), this.location2.getBlockZ());
                    if (Math.abs(highX - lowX) * Math.abs(highZ - lowZ) * Math.abs(highY - lowY) > Selection.MAX_BLOCK_COUNT) {
                        throw new ComponentException(Messages.SELECTION_SIZE_ABOVE_LIMIT);
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
