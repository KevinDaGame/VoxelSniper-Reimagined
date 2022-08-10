package com.thevoxelbox.voxelsniper.snipe;

import com.google.common.collect.Sets;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockstate.IBlockState;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.IVector;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Furnace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.NoteBlock;

/**
 * Holds {@link BlockState}s that can be later on used to reset those block locations back to the recorded states.
 */
public class Undo {

    private static final List<VoxelMaterial> FALLING_MATERIALS = Arrays.asList(
            VoxelMaterial.WATER,
            VoxelMaterial.LAVA);
    private static final List<VoxelMaterial> FALLOFF_MATERIALS = Arrays.asList(
            VoxelMaterial.ACACIA_SAPLING,
            VoxelMaterial.BIRCH_SAPLING,
            VoxelMaterial.DARK_OAK_SAPLING,
            VoxelMaterial.JUNGLE_SAPLING,
            VoxelMaterial.OAK_SAPLING,
            VoxelMaterial.WHITE_BED,
            VoxelMaterial.ORANGE_BED,
            VoxelMaterial.MAGENTA_BED,
            VoxelMaterial.LIGHT_BLUE_BED,
            VoxelMaterial.YELLOW_BED,
            VoxelMaterial.LIME_BED,
            VoxelMaterial.PINK_BED,
            VoxelMaterial.GRAY_BED,
            VoxelMaterial.LIGHT_GRAY_BED,
            VoxelMaterial.CYAN_BED,
            VoxelMaterial.PURPLE_BED,
            VoxelMaterial.BLUE_BED,
            VoxelMaterial.BROWN_BED,
            VoxelMaterial.GREEN_BED,
            VoxelMaterial.RED_BED,
            VoxelMaterial.BLACK_BED,
            VoxelMaterial.POWERED_RAIL,
            VoxelMaterial.DETECTOR_RAIL,
            VoxelMaterial.TALL_GRASS,
            VoxelMaterial.DEAD_BUSH,
            // TODO Material.PISTON_EXTENSION,
            VoxelMaterial.DANDELION,
            VoxelMaterial.POPPY,
            VoxelMaterial.BLUE_ORCHID,
            VoxelMaterial.ALLIUM,
            VoxelMaterial.AZURE_BLUET,
            VoxelMaterial.RED_TULIP,
            VoxelMaterial.ORANGE_TULIP,
            VoxelMaterial.WHITE_TULIP,
            VoxelMaterial.PINK_TULIP,
            VoxelMaterial.OXEYE_DAISY,
            VoxelMaterial.BROWN_MUSHROOM,
            VoxelMaterial.RED_MUSHROOM,
            VoxelMaterial.TORCH,
            VoxelMaterial.FIRE,
            VoxelMaterial.WHEAT,
            VoxelMaterial.ACACIA_SIGN,
            VoxelMaterial.BIRCH_SIGN,
            VoxelMaterial.DARK_OAK_SIGN,
            VoxelMaterial.JUNGLE_SIGN,
            VoxelMaterial.OAK_SIGN,
            VoxelMaterial.ACACIA_WALL_SIGN,
            VoxelMaterial.BIRCH_WALL_SIGN,
            VoxelMaterial.DARK_OAK_WALL_SIGN,
            VoxelMaterial.JUNGLE_WALL_SIGN,
            VoxelMaterial.OAK_WALL_SIGN,
            VoxelMaterial.ACACIA_DOOR,
            VoxelMaterial.BIRCH_DOOR,
            VoxelMaterial.DARK_OAK_DOOR,
            VoxelMaterial.JUNGLE_DOOR,
            VoxelMaterial.OAK_DOOR,
            VoxelMaterial.LADDER,
            VoxelMaterial.RAIL,
            VoxelMaterial.LEVER,
            VoxelMaterial.STONE_PRESSURE_PLATE,
            VoxelMaterial.IRON_DOOR,
            VoxelMaterial.ACACIA_PRESSURE_PLATE,
            VoxelMaterial.BIRCH_PRESSURE_PLATE,
            VoxelMaterial.DARK_OAK_PRESSURE_PLATE,
            VoxelMaterial.JUNGLE_PRESSURE_PLATE,
            VoxelMaterial.OAK_PRESSURE_PLATE,
            VoxelMaterial.REDSTONE_TORCH,
            VoxelMaterial.REDSTONE_WALL_TORCH,
            VoxelMaterial.REDSTONE_WIRE,
            VoxelMaterial.STONE_BUTTON,
            VoxelMaterial.SNOW,
            VoxelMaterial.CACTUS,
            VoxelMaterial.SUGAR_CANE,
            VoxelMaterial.CAKE,
            VoxelMaterial.REPEATER,
            VoxelMaterial.ACACIA_TRAPDOOR,
            VoxelMaterial.BIRCH_TRAPDOOR,
            VoxelMaterial.DARK_OAK_TRAPDOOR,
            VoxelMaterial.JUNGLE_TRAPDOOR,
            VoxelMaterial.OAK_TRAPDOOR,
            VoxelMaterial.IRON_TRAPDOOR,
            VoxelMaterial.PUMPKIN_STEM,
            VoxelMaterial.MELON_STEM,
            VoxelMaterial.VINE,
            VoxelMaterial.LILY_PAD,
            VoxelMaterial.NETHER_WART);
    private final Set<IVector> containing = Sets.newHashSet();
    private final List<IBlockState> all;
    private final List<IBlockState> falloff;
    private final List<IBlockState> dropdown;

    /**
     * Default constructor of a Undo container.
     */
    public Undo() {
        all = new LinkedList<>();
        falloff = new LinkedList<>();
        dropdown = new LinkedList<>();
    }

    /**
     * Get the number of blocks in the collection.
     *
     * @return size of the Undo collection
     */
    public int getSize() {
        return containing.size();
    }

    /**
     * Adds a Block to the collection.
     *
     * @param block Block to be added
     */
    public void put(IBlock block) {
        IVector pos = block.getLocation().toVector();
        if (this.containing.contains(pos)) {
            return;
        }
        this.containing.add(pos);
        if (Undo.FALLING_MATERIALS.contains(block.getMaterial().getVoxelMaterial())) {
            dropdown.add(block.getState());
        } else if (Undo.FALLOFF_MATERIALS.contains(block.getMaterial().getVoxelMaterial())) {
            falloff.add(block.getState());
        } else {
            all.add(block.getState());
        }
    }

    /**
     * Set the blockstates of all recorded blocks back to the state when they were inserted.
     */
    public void undo() {

        for (IBlockState blockState : all) {
            blockState.update(true, false);
            updateSpecialBlocks(blockState);
        }

        for (IBlockState blockState : falloff) {
            blockState.update(true, false);
            updateSpecialBlocks(blockState);
        }

        for (IBlockState blockState : dropdown) {
            blockState.update(true, false);
            updateSpecialBlocks(blockState);
        }
    }

    /**
     * @param blockState
     */
    private void updateSpecialBlocks(IBlockState blockState) {
        IBlockState currentState = blockState.getBlock().getState();
        if (blockState instanceof BrewingStand && currentState instanceof BrewingStand) {
            ((BrewingStand) currentState).getInventory().setContents(((BrewingStand) blockState).getInventory().getContents());
        } else if (blockState instanceof Chest && currentState instanceof Chest) {
            ((Chest) currentState).getInventory().setContents(((Chest) blockState).getInventory().getContents());
            ((Chest) currentState).getBlockInventory().setContents(((Chest) blockState).getBlockInventory().getContents());
            currentState.update();
        } else if (blockState instanceof CreatureSpawner && currentState instanceof CreatureSpawner) {
            ((CreatureSpawner) currentState).setSpawnedType(((CreatureSpawner) currentState).getSpawnedType());
            currentState.update();
        } else if (blockState instanceof Dispenser && currentState instanceof Dispenser) {
            ((Dispenser) currentState).getInventory().setContents(((Dispenser) blockState).getInventory().getContents());
            currentState.update();
        } else if (blockState instanceof Furnace && currentState instanceof Furnace) {
            ((Furnace) currentState).getInventory().setContents(((Furnace) blockState).getInventory().getContents());
            ((Furnace) currentState).setBurnTime(((Furnace) blockState).getBurnTime());
            ((Furnace) currentState).setCookTime(((Furnace) blockState).getCookTime());
            currentState.update();
        } else if (blockState instanceof NoteBlock && currentState instanceof NoteBlock) {
            ((NoteBlock) currentState).setNote(((NoteBlock) blockState).getNote());
            currentState.update();
        } else if (blockState instanceof Sign && currentState instanceof Sign) {
            int i = 0;
            for (String text : ((Sign) blockState).getLines()) {
                ((Sign) currentState).setLine(i++, text);
            }
            currentState.update();
        }
    }
}
