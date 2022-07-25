package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.util.Messages;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

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
    private static final Set<Material> BREAKABLE_MATERIALS = new TreeSet<>();

    static {
        MoveBrush.BREAKABLE_MATERIALS.add(Material.OAK_SAPLING);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.ACACIA_SAPLING);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.BIRCH_SAPLING);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.DARK_OAK_SAPLING);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.JUNGLE_SAPLING);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.SPRUCE_SAPLING);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.BLACK_BED);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.BLUE_BED);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.LIGHT_BLUE_BED);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.BROWN_BED);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.CYAN_BED);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.GRAY_BED);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.GREEN_BED);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.LIGHT_GRAY_BED);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.LIME_BED);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.MAGENTA_BED);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.ORANGE_BED);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.PINK_BED);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.PURPLE_BED);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.RED_BED);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.WHITE_BED);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.YELLOW_BED);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.TALL_GRASS);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.DEAD_BUSH);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.PISTON_HEAD);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.DANDELION);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.POPPY);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.BLUE_ORCHID);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.ALLIUM);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.AZURE_BLUET);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.RED_TULIP);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.ORANGE_TULIP);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.WHITE_TULIP);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.PINK_TULIP);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.OXEYE_DAISY);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.BROWN_MUSHROOM);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.RED_MUSHROOM);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.TORCH);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.FIRE);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.WHEAT);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.ACACIA_SIGN);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.BIRCH_SIGN);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.DARK_OAK_SIGN);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.JUNGLE_SIGN);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.OAK_SIGN);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.ACACIA_WALL_SIGN);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.BIRCH_WALL_SIGN);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.DARK_OAK_WALL_SIGN);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.JUNGLE_WALL_SIGN);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.OAK_WALL_SIGN);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.OAK_DOOR);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.ACACIA_DOOR);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.BIRCH_DOOR);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.DARK_OAK_DOOR);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.JUNGLE_DOOR);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.SPRUCE_DOOR);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.LADDER);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.RAIL);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.ACTIVATOR_RAIL);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.DETECTOR_RAIL);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.POWERED_RAIL);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.LEVER);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.STONE_PRESSURE_PLATE);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.IRON_DOOR);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.OAK_PRESSURE_PLATE);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.ACACIA_PRESSURE_PLATE);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.BIRCH_PRESSURE_PLATE);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.DARK_OAK_PRESSURE_PLATE);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.JUNGLE_PRESSURE_PLATE);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.SPRUCE_PRESSURE_PLATE);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.REDSTONE_TORCH);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.REDSTONE_WALL_TORCH);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.STONE_BUTTON);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.SNOW);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.CACTUS);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.SUGAR_CANE);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.CAKE);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.REPEATER);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.OAK_TRAPDOOR);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.ACACIA_TRAPDOOR);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.BIRCH_TRAPDOOR);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.DARK_OAK_TRAPDOOR);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.JUNGLE_TRAPDOOR);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.SPRUCE_TRAPDOOR);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.PUMPKIN_STEM);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.MELON_STEM);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.VINE);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.LILY_PAD);
        MoveBrush.BREAKABLE_MATERIALS.add(Material.NETHER_WART);
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
            final World world = selection.getBlockStates().get(0).getWorld();

            final Undo undo = new Undo();
            final HashSet<Block> undoSet = new HashSet<>();

            final Selection newSelection = new Selection();
            final Location movedLocation1 = selection.getLocation1();
            movedLocation1.add(direction[0], direction[1], direction[2]);
            final Location movedLocation2 = selection.getLocation2();
            movedLocation2.add(direction[0], direction[1], direction[2]);
            newSelection.setLocation1(movedLocation1);
            newSelection.setLocation2(movedLocation2);
            try {
                newSelection.calculateRegion();
            } catch (final ComponentException exception) {
                v.sendMessage(exception);
            }

            for (final BlockState blockState : selection.getBlockStates()) {
                undoSet.add(blockState.getBlock());
            }
            for (final BlockState blockState : newSelection.getBlockStates()) {
                undoSet.add(blockState.getBlock());
            }

            for (final Block block : undoSet) {
                undo.put(block);
            }
            v.owner().storeUndo(undo);

            for (final BlockState blockState : selection.getBlockStates()) {
                blockState.getBlock().setType(Material.AIR);
            }
            for (final BlockState blockState : selection.getBlockStates()) {
                final Block affectedBlock = world.getBlockAt(blockState.getX() + direction[0], blockState.getY() + direction[1], blockState.getZ() + direction[2]);
                affectedBlock.setBlockData(blockState.getBlockData(), !MoveBrush.BREAKABLE_MATERIALS.contains(blockState.getType()));
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
        } catch (NumberFormatException ignored) {
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
        private final ArrayList<BlockState> blockStates = new ArrayList<>();
        /**
         *
         */
        private Location location1 = null;
        /**
         *
         */
        private Location location2 = null;

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
                    final World world = this.location1.getWorld();
                    for (int y = lowY; y <= highY; y++) {
                        for (int x = lowX; x <= highX; x++) {
                            for (int z = lowZ; z <= highZ; z++) {
                                this.blockStates.add(world.getBlockAt(x, y, z).getState());
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
        public ArrayList<BlockState> getBlockStates() {
            return this.blockStates;
        }

        /**
         * @return Location
         */
        public Location getLocation1() {
            return this.location1;
        }

        /**
         * @param location1
         */
        public void setLocation1(final Location location1) {
            this.location1 = location1;
        }

        /**
         * @return Location
         */
        public Location getLocation2() {
            return this.location2;
        }

        /**
         * @param location2
         */
        public void setLocation2(final Location location2) {
            this.location2 = location2;
        }
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.move";
    }
}
