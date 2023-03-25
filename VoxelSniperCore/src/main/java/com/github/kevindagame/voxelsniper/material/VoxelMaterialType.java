package com.github.kevindagame.voxelsniper.material;

import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.voxelsniper.Version;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;

import java.util.*;

import static com.github.kevindagame.voxelsniper.Version.*;

@SuppressWarnings("unused")
public class VoxelMaterialType implements IMaterial {
    public static final Map<String, VoxelMaterialType> BLOCKS = new HashMap<>();
    //<editor-fold defaultstate="collapsed" desc="Blocks">
    public static final VoxelMaterialType AIR = register("minecraft", "air", Tags.AIR);
    public static final VoxelMaterialType STONE = register("minecraft", "stone");
    public static final VoxelMaterialType GRANITE = register("minecraft", "granite");
    public static final VoxelMaterialType POLISHED_GRANITE = register("minecraft", "polished_granite");
    public static final VoxelMaterialType DIORITE = register("minecraft", "diorite");
    public static final VoxelMaterialType POLISHED_DIORITE = register("minecraft", "polished_diorite");
    public static final VoxelMaterialType ANDESITE = register("minecraft", "andesite");
    public static final VoxelMaterialType POLISHED_ANDESITE = register("minecraft", "polished_andesite");
    public static final VoxelMaterialType GRASS_BLOCK = register("minecraft", "grass_block");
    public static final VoxelMaterialType DIRT = register("minecraft", "dirt");
    public static final VoxelMaterialType COARSE_DIRT = register("minecraft", "coarse_dirt");
    public static final VoxelMaterialType PODZOL = register("minecraft", "podzol");
    public static final VoxelMaterialType COBBLESTONE = register("minecraft", "cobblestone");
    public static final VoxelMaterialType OAK_PLANKS = register("minecraft", "oak_planks");
    public static final VoxelMaterialType SPRUCE_PLANKS = register("minecraft", "spruce_planks");
    public static final VoxelMaterialType BIRCH_PLANKS = register("minecraft", "birch_planks");
    public static final VoxelMaterialType JUNGLE_PLANKS = register("minecraft", "jungle_planks");
    public static final VoxelMaterialType ACACIA_PLANKS = register("minecraft", "acacia_planks");
    public static final VoxelMaterialType DARK_OAK_PLANKS = register("minecraft", "dark_oak_planks");
    public static final VoxelMaterialType OAK_SAPLING = register("minecraft", "oak_sapling", Tags.FALLSOFF);
    public static final VoxelMaterialType SPRUCE_SAPLING = register("minecraft", "spruce_sapling", Tags.FALLSOFF);
    public static final VoxelMaterialType BIRCH_SAPLING = register("minecraft", "birch_sapling", Tags.FALLSOFF);
    public static final VoxelMaterialType JUNGLE_SAPLING = register("minecraft", "jungle_sapling", Tags.FALLSOFF);
    public static final VoxelMaterialType ACACIA_SAPLING = register("minecraft", "acacia_sapling", Tags.FALLSOFF);
    public static final VoxelMaterialType DARK_OAK_SAPLING = register("minecraft", "dark_oak_sapling", Tags.FALLSOFF);
    public static final VoxelMaterialType BEDROCK = register("minecraft", "bedrock");
    public static final VoxelMaterialType WATER = register("minecraft", "water", Tags.FLUIDS);
    public static final VoxelMaterialType LAVA = register("minecraft", "lava", Tags.FLUIDS);
    public static final VoxelMaterialType SAND = register("minecraft", "sand");
    public static final VoxelMaterialType RED_SAND = register("minecraft", "red_sand");
    public static final VoxelMaterialType GRAVEL = register("minecraft", "gravel");
    public static final VoxelMaterialType GOLD_ORE = register("minecraft", "gold_ore");
    public static final VoxelMaterialType DEEPSLATE_GOLD_ORE = register("minecraft", "deepslate_gold_ore", V1_17);
    public static final VoxelMaterialType IRON_ORE = register("minecraft", "iron_ore");
    public static final VoxelMaterialType DEEPSLATE_IRON_ORE = register("minecraft", "deepslate_iron_ore", V1_17);
    public static final VoxelMaterialType COAL_ORE = register("minecraft", "coal_ore");
    public static final VoxelMaterialType DEEPSLATE_COAL_ORE = register("minecraft", "deepslate_coal_ore", V1_17);
    public static final VoxelMaterialType NETHER_GOLD_ORE = register("minecraft", "nether_gold_ore");
    public static final VoxelMaterialType OAK_LOG = register("minecraft", "oak_log");
    public static final VoxelMaterialType SPRUCE_LOG = register("minecraft", "spruce_log");
    public static final VoxelMaterialType BIRCH_LOG = register("minecraft", "birch_log");
    public static final VoxelMaterialType JUNGLE_LOG = register("minecraft", "jungle_log");
    public static final VoxelMaterialType ACACIA_LOG = register("minecraft", "acacia_log");
    public static final VoxelMaterialType DARK_OAK_LOG = register("minecraft", "dark_oak_log");
    public static final VoxelMaterialType STRIPPED_SPRUCE_LOG = register("minecraft", "stripped_spruce_log");
    public static final VoxelMaterialType STRIPPED_BIRCH_LOG = register("minecraft", "stripped_birch_log");
    public static final VoxelMaterialType STRIPPED_JUNGLE_LOG = register("minecraft", "stripped_jungle_log");
    public static final VoxelMaterialType STRIPPED_ACACIA_LOG = register("minecraft", "stripped_acacia_log");
    public static final VoxelMaterialType STRIPPED_DARK_OAK_LOG = register("minecraft", "stripped_dark_oak_log");
    public static final VoxelMaterialType STRIPPED_OAK_LOG = register("minecraft", "stripped_oak_log");
    public static final VoxelMaterialType OAK_WOOD = register("minecraft", "oak_wood");
    public static final VoxelMaterialType SPRUCE_WOOD = register("minecraft", "spruce_wood");
    public static final VoxelMaterialType BIRCH_WOOD = register("minecraft", "birch_wood");
    public static final VoxelMaterialType JUNGLE_WOOD = register("minecraft", "jungle_wood");
    public static final VoxelMaterialType ACACIA_WOOD = register("minecraft", "acacia_wood");
    public static final VoxelMaterialType DARK_OAK_WOOD = register("minecraft", "dark_oak_wood");
    public static final VoxelMaterialType STRIPPED_OAK_WOOD = register("minecraft", "stripped_oak_wood");
    public static final VoxelMaterialType STRIPPED_SPRUCE_WOOD = register("minecraft", "stripped_spruce_wood");
    public static final VoxelMaterialType STRIPPED_BIRCH_WOOD = register("minecraft", "stripped_birch_wood");
    public static final VoxelMaterialType STRIPPED_JUNGLE_WOOD = register("minecraft", "stripped_jungle_wood");
    public static final VoxelMaterialType STRIPPED_ACACIA_WOOD = register("minecraft", "stripped_acacia_wood");
    public static final VoxelMaterialType STRIPPED_DARK_OAK_WOOD = register("minecraft", "stripped_dark_oak_wood");
    public static final VoxelMaterialType OAK_LEAVES = register("minecraft", "oak_leaves");
    public static final VoxelMaterialType SPRUCE_LEAVES = register("minecraft", "spruce_leaves");
    public static final VoxelMaterialType BIRCH_LEAVES = register("minecraft", "birch_leaves");
    public static final VoxelMaterialType JUNGLE_LEAVES = register("minecraft", "jungle_leaves");
    public static final VoxelMaterialType ACACIA_LEAVES = register("minecraft", "acacia_leaves");
    public static final VoxelMaterialType DARK_OAK_LEAVES = register("minecraft", "dark_oak_leaves");
    public static final VoxelMaterialType AZALEA_LEAVES = register("minecraft", "azalea_leaves", V1_17);
    public static final VoxelMaterialType FLOWERING_AZALEA_LEAVES = register("minecraft", "flowering_azalea_leaves", V1_17);
    public static final VoxelMaterialType SPONGE = register("minecraft", "sponge");
    public static final VoxelMaterialType WET_SPONGE = register("minecraft", "wet_sponge");
    public static final VoxelMaterialType GLASS = register("minecraft", "glass");
    public static final VoxelMaterialType LAPIS_ORE = register("minecraft", "lapis_ore");
    public static final VoxelMaterialType DEEPSLATE_LAPIS_ORE = register("minecraft", "deepslate_lapis_ore", V1_17);
    public static final VoxelMaterialType LAPIS_BLOCK = register("minecraft", "lapis_block");
    public static final VoxelMaterialType DISPENSER = register("minecraft", "dispenser");
    public static final VoxelMaterialType SANDSTONE = register("minecraft", "sandstone");
    public static final VoxelMaterialType CHISELED_SANDSTONE = register("minecraft", "chiseled_sandstone");
    public static final VoxelMaterialType CUT_SANDSTONE = register("minecraft", "cut_sandstone");
    public static final VoxelMaterialType NOTE_BLOCK = register("minecraft", "note_block");
    public static final VoxelMaterialType WHITE_BED = register("minecraft", "white_bed", Tags.FALLSOFF);
    public static final VoxelMaterialType ORANGE_BED = register("minecraft", "orange_bed", Tags.FALLSOFF);
    public static final VoxelMaterialType MAGENTA_BED = register("minecraft", "magenta_bed", Tags.FALLSOFF);
    public static final VoxelMaterialType LIGHT_BLUE_BED = register("minecraft", "light_blue_bed", Tags.FALLSOFF);
    public static final VoxelMaterialType YELLOW_BED = register("minecraft", "yellow_bed", Tags.FALLSOFF);
    public static final VoxelMaterialType LIME_BED = register("minecraft", "lime_bed", Tags.FALLSOFF);
    public static final VoxelMaterialType PINK_BED = register("minecraft", "pink_bed", Tags.FALLSOFF);
    public static final VoxelMaterialType GRAY_BED = register("minecraft", "gray_bed", Tags.FALLSOFF);
    public static final VoxelMaterialType LIGHT_GRAY_BED = register("minecraft", "light_gray_bed", Tags.FALLSOFF);
    public static final VoxelMaterialType CYAN_BED = register("minecraft", "cyan_bed", Tags.FALLSOFF);
    public static final VoxelMaterialType PURPLE_BED = register("minecraft", "purple_bed", Tags.FALLSOFF);
    public static final VoxelMaterialType BLUE_BED = register("minecraft", "blue_bed", Tags.FALLSOFF);
    public static final VoxelMaterialType BROWN_BED = register("minecraft", "brown_bed", Tags.FALLSOFF);
    public static final VoxelMaterialType GREEN_BED = register("minecraft", "green_bed", Tags.FALLSOFF);
    public static final VoxelMaterialType RED_BED = register("minecraft", "red_bed", Tags.FALLSOFF);
    public static final VoxelMaterialType BLACK_BED = register("minecraft", "black_bed", Tags.FALLSOFF);
    public static final VoxelMaterialType POWERED_RAIL = register("minecraft", "powered_rail", Tags.FALLSOFF);
    public static final VoxelMaterialType DETECTOR_RAIL = register("minecraft", "detector_rail", Tags.FALLSOFF);
    public static final VoxelMaterialType STICKY_PISTON = register("minecraft", "sticky_piston");
    public static final VoxelMaterialType COBWEB = register("minecraft", "cobweb");
    public static final VoxelMaterialType GRASS = register("minecraft", "grass", Tags.FALLSOFF);
    public static final VoxelMaterialType FERN = register("minecraft", "fern", Tags.FALLSOFF);
    public static final VoxelMaterialType DEAD_BUSH = register("minecraft", "dead_bush", Tags.FALLSOFF);
    public static final VoxelMaterialType SEAGRASS = register("minecraft", "seagrass", Tags.FALLSOFF);
    public static final VoxelMaterialType TALL_SEAGRASS = register("minecraft", "tall_seagrass", Tags.FALLSOFF);
    public static final VoxelMaterialType PISTON = register("minecraft", "piston");
    public static final VoxelMaterialType PISTON_HEAD = register("minecraft", "piston_head");
    public static final VoxelMaterialType WHITE_WOOL = register("minecraft", "white_wool");
    public static final VoxelMaterialType ORANGE_WOOL = register("minecraft", "orange_wool");
    public static final VoxelMaterialType MAGENTA_WOOL = register("minecraft", "magenta_wool");
    public static final VoxelMaterialType LIGHT_BLUE_WOOL = register("minecraft", "light_blue_wool");
    public static final VoxelMaterialType YELLOW_WOOL = register("minecraft", "yellow_wool");
    public static final VoxelMaterialType LIME_WOOL = register("minecraft", "lime_wool");
    public static final VoxelMaterialType PINK_WOOL = register("minecraft", "pink_wool");
    public static final VoxelMaterialType GRAY_WOOL = register("minecraft", "gray_wool");
    public static final VoxelMaterialType LIGHT_GRAY_WOOL = register("minecraft", "light_gray_wool");
    public static final VoxelMaterialType CYAN_WOOL = register("minecraft", "cyan_wool");
    public static final VoxelMaterialType PURPLE_WOOL = register("minecraft", "purple_wool");
    public static final VoxelMaterialType BLUE_WOOL = register("minecraft", "blue_wool");
    public static final VoxelMaterialType BROWN_WOOL = register("minecraft", "brown_wool");
    public static final VoxelMaterialType GREEN_WOOL = register("minecraft", "green_wool");
    public static final VoxelMaterialType RED_WOOL = register("minecraft", "red_wool");
    public static final VoxelMaterialType BLACK_WOOL = register("minecraft", "black_wool");
    public static final VoxelMaterialType MOVING_PISTON = register("minecraft", "moving_piston");
    public static final VoxelMaterialType DANDELION = register("minecraft", "dandelion", Tags.FALLSOFF);
    public static final VoxelMaterialType POPPY = register("minecraft", "poppy", Tags.FALLSOFF);
    public static final VoxelMaterialType BLUE_ORCHID = register("minecraft", "blue_orchid", Tags.FALLSOFF);
    public static final VoxelMaterialType ALLIUM = register("minecraft", "allium", Tags.FALLSOFF);
    public static final VoxelMaterialType AZURE_BLUET = register("minecraft", "azure_bluet", Tags.FALLSOFF);
    public static final VoxelMaterialType RED_TULIP = register("minecraft", "red_tulip", Tags.FALLSOFF);
    public static final VoxelMaterialType ORANGE_TULIP = register("minecraft", "orange_tulip", Tags.FALLSOFF);
    public static final VoxelMaterialType WHITE_TULIP = register("minecraft", "white_tulip", Tags.FALLSOFF);
    public static final VoxelMaterialType PINK_TULIP = register("minecraft", "pink_tulip", Tags.FALLSOFF);
    public static final VoxelMaterialType OXEYE_DAISY = register("minecraft", "oxeye_daisy", Tags.FALLSOFF);
    public static final VoxelMaterialType CORNFLOWER = register("minecraft", "cornflower", Tags.FALLSOFF);
    public static final VoxelMaterialType WITHER_ROSE = register("minecraft", "wither_rose", Tags.FALLSOFF);
    public static final VoxelMaterialType LILY_OF_THE_VALLEY = register("minecraft", "lily_of_the_valley", Tags.FALLSOFF);
    public static final VoxelMaterialType BROWN_MUSHROOM = register("minecraft", "brown_mushroom", Tags.FALLSOFF);
    public static final VoxelMaterialType RED_MUSHROOM = register("minecraft", "red_mushroom", Tags.FALLSOFF);
    public static final VoxelMaterialType GOLD_BLOCK = register("minecraft", "gold_block");
    public static final VoxelMaterialType IRON_BLOCK = register("minecraft", "iron_block");
    public static final VoxelMaterialType BRICKS = register("minecraft", "bricks");
    public static final VoxelMaterialType TNT = register("minecraft", "tnt");
    public static final VoxelMaterialType BOOKSHELF = register("minecraft", "bookshelf");
    public static final VoxelMaterialType MOSSY_COBBLESTONE = register("minecraft", "mossy_cobblestone");
    public static final VoxelMaterialType OBSIDIAN = register("minecraft", "obsidian");
    public static final VoxelMaterialType TORCH = register("minecraft", "torch", Tags.FALLSOFF);
    public static final VoxelMaterialType WALL_TORCH = register("minecraft", "wall_torch", Tags.FALLSOFF);
    public static final VoxelMaterialType FIRE = register("minecraft", "fire", Tags.FALLSOFF);
    public static final VoxelMaterialType SOUL_FIRE = register("minecraft", "soul_fire", Tags.FALLSOFF);
    public static final VoxelMaterialType SPAWNER = register("minecraft", "spawner");
    public static final VoxelMaterialType OAK_STAIRS = register("minecraft", "oak_stairs");
    public static final VoxelMaterialType CHEST = register("minecraft", "chest");
    public static final VoxelMaterialType REDSTONE_WIRE = register("minecraft", "redstone_wire", Tags.FALLSOFF);
    public static final VoxelMaterialType DIAMOND_ORE = register("minecraft", "diamond_ore");
    public static final VoxelMaterialType DEEPSLATE_DIAMOND_ORE = register("minecraft", "deepslate_diamond_ore", V1_17);
    public static final VoxelMaterialType DIAMOND_BLOCK = register("minecraft", "diamond_block");
    public static final VoxelMaterialType CRAFTING_TABLE = register("minecraft", "crafting_table");
    public static final VoxelMaterialType WHEAT = register("minecraft", "wheat", Tags.FALLSOFF);
    public static final VoxelMaterialType FARMLAND = register("minecraft", "farmland");
    public static final VoxelMaterialType FURNACE = register("minecraft", "furnace");
    public static final VoxelMaterialType OAK_SIGN = register("minecraft", "oak_sign", Tags.FALLSOFF);
    public static final VoxelMaterialType SPRUCE_SIGN = register("minecraft", "spruce_sign", Tags.FALLSOFF);
    public static final VoxelMaterialType BIRCH_SIGN = register("minecraft", "birch_sign", Tags.FALLSOFF);
    public static final VoxelMaterialType ACACIA_SIGN = register("minecraft", "acacia_sign", Tags.FALLSOFF);
    public static final VoxelMaterialType JUNGLE_SIGN = register("minecraft", "jungle_sign", Tags.FALLSOFF);
    public static final VoxelMaterialType DARK_OAK_SIGN = register("minecraft", "dark_oak_sign", Tags.FALLSOFF);
    public static final VoxelMaterialType OAK_DOOR = register("minecraft", "oak_door", Tags.FALLSOFF);
    public static final VoxelMaterialType LADDER = register("minecraft", "ladder", Tags.FALLSOFF);
    public static final VoxelMaterialType RAIL = register("minecraft", "rail", Tags.FALLSOFF);
    public static final VoxelMaterialType COBBLESTONE_STAIRS = register("minecraft", "cobblestone_stairs");
    public static final VoxelMaterialType OAK_WALL_SIGN = register("minecraft", "oak_wall_sign", Tags.FALLSOFF);
    public static final VoxelMaterialType SPRUCE_WALL_SIGN = register("minecraft", "spruce_wall_sign", Tags.FALLSOFF);
    public static final VoxelMaterialType BIRCH_WALL_SIGN = register("minecraft", "birch_wall_sign", Tags.FALLSOFF);
    public static final VoxelMaterialType ACACIA_WALL_SIGN = register("minecraft", "acacia_wall_sign", Tags.FALLSOFF);
    public static final VoxelMaterialType JUNGLE_WALL_SIGN = register("minecraft", "jungle_wall_sign", Tags.FALLSOFF);
    public static final VoxelMaterialType DARK_OAK_WALL_SIGN = register("minecraft", "dark_oak_wall_sign", Tags.FALLSOFF);
    public static final VoxelMaterialType LEVER = register("minecraft", "lever", Tags.FALLSOFF);
    public static final VoxelMaterialType STONE_PRESSURE_PLATE = register("minecraft", "stone_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterialType IRON_DOOR = register("minecraft", "iron_door", Tags.FALLSOFF);
    public static final VoxelMaterialType OAK_PRESSURE_PLATE = register("minecraft", "oak_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterialType SPRUCE_PRESSURE_PLATE = register("minecraft", "spruce_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterialType BIRCH_PRESSURE_PLATE = register("minecraft", "birch_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterialType JUNGLE_PRESSURE_PLATE = register("minecraft", "jungle_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterialType ACACIA_PRESSURE_PLATE = register("minecraft", "acacia_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterialType DARK_OAK_PRESSURE_PLATE = register("minecraft", "dark_oak_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterialType REDSTONE_ORE = register("minecraft", "redstone_ore", Tags.FALLSOFF);
    public static final VoxelMaterialType DEEPSLATE_REDSTONE_ORE = register("minecraft", "deepslate_redstone_ore", V1_17);
    public static final VoxelMaterialType REDSTONE_TORCH = register("minecraft", "redstone_torch", Tags.FALLSOFF);
    public static final VoxelMaterialType REDSTONE_WALL_TORCH = register("minecraft", "redstone_wall_torch", Tags.FALLSOFF);
    public static final VoxelMaterialType STONE_BUTTON = register("minecraft", "stone_button", Tags.FALLSOFF);
    public static final VoxelMaterialType SNOW = register("minecraft", "snow", Tags.FALLSOFF);
    public static final VoxelMaterialType ICE = register("minecraft", "ice");
    public static final VoxelMaterialType SNOW_BLOCK = register("minecraft", "snow_block");
    public static final VoxelMaterialType CACTUS = register("minecraft", "cactus", Tags.FALLSOFF);
    public static final VoxelMaterialType CLAY = register("minecraft", "clay");
    public static List<VoxelMaterialType> OVERRIDABLE_MATERIALS = Arrays.asList(VoxelMaterialType.STONE,
            VoxelMaterialType.ANDESITE,
            VoxelMaterialType.DIORITE,
            VoxelMaterialType.GRANITE,
            VoxelMaterialType.GRASS_BLOCK,
            VoxelMaterialType.DIRT,
            VoxelMaterialType.COARSE_DIRT,
            VoxelMaterialType.PODZOL,
            VoxelMaterialType.SAND,
            VoxelMaterialType.RED_SAND,
            VoxelMaterialType.GRAVEL,
            VoxelMaterialType.SANDSTONE,
            VoxelMaterialType.MOSSY_COBBLESTONE,
            VoxelMaterialType.CLAY,
            VoxelMaterialType.SNOW,
            VoxelMaterialType.OBSIDIAN
    );
    public static final VoxelMaterialType SUGAR_CANE = register("minecraft", "sugar_cane", Tags.FALLSOFF);
    public static final VoxelMaterialType JUKEBOX = register("minecraft", "jukebox");
    public static final VoxelMaterialType OAK_FENCE = register("minecraft", "oak_fence");
    public static final VoxelMaterialType PUMPKIN = register("minecraft", "pumpkin");
    public static final VoxelMaterialType NETHERRACK = register("minecraft", "netherrack");
    public static final VoxelMaterialType SOUL_SAND = register("minecraft", "soul_sand");
    public static final VoxelMaterialType SOUL_SOIL = register("minecraft", "soul_soil");
    public static final VoxelMaterialType BASALT = register("minecraft", "basalt");
    public static final VoxelMaterialType POLISHED_BASALT = register("minecraft", "polished_basalt");
    public static final VoxelMaterialType SOUL_TORCH = register("minecraft", "soul_torch", Tags.FALLSOFF);
    public static final VoxelMaterialType SOUL_WALL_TORCH = register("minecraft", "soul_wall_torch", Tags.FALLSOFF);
    public static final VoxelMaterialType GLOWSTONE = register("minecraft", "glowstone");
    public static final VoxelMaterialType NETHER_PORTAL = register("minecraft", "nether_portal");
    public static final VoxelMaterialType CARVED_PUMPKIN = register("minecraft", "carved_pumpkin");
    public static final VoxelMaterialType JACK_O_LANTERN = register("minecraft", "jack_o_lantern");
    public static final VoxelMaterialType CAKE = register("minecraft", "cake", Tags.FALLSOFF);
    public static final VoxelMaterialType REPEATER = register("minecraft", "repeater", Tags.FALLSOFF);
    public static final VoxelMaterialType WHITE_STAINED_GLASS = register("minecraft", "white_stained_glass");
    public static final VoxelMaterialType ORANGE_STAINED_GLASS = register("minecraft", "orange_stained_glass");
    public static final VoxelMaterialType MAGENTA_STAINED_GLASS = register("minecraft", "magenta_stained_glass");
    public static final VoxelMaterialType LIGHT_BLUE_STAINED_GLASS = register("minecraft", "light_blue_stained_glass");
    public static final VoxelMaterialType YELLOW_STAINED_GLASS = register("minecraft", "yellow_stained_glass");
    public static final VoxelMaterialType LIME_STAINED_GLASS = register("minecraft", "lime_stained_glass");
    public static final VoxelMaterialType PINK_STAINED_GLASS = register("minecraft", "pink_stained_glass");
    public static final VoxelMaterialType GRAY_STAINED_GLASS = register("minecraft", "gray_stained_glass");
    public static final VoxelMaterialType LIGHT_GRAY_STAINED_GLASS = register("minecraft", "light_gray_stained_glass");
    public static final VoxelMaterialType CYAN_STAINED_GLASS = register("minecraft", "cyan_stained_glass");
    public static final VoxelMaterialType PURPLE_STAINED_GLASS = register("minecraft", "purple_stained_glass");
    public static final VoxelMaterialType BLUE_STAINED_GLASS = register("minecraft", "blue_stained_glass");
    public static final VoxelMaterialType BROWN_STAINED_GLASS = register("minecraft", "brown_stained_glass");
    public static final VoxelMaterialType GREEN_STAINED_GLASS = register("minecraft", "green_stained_glass");
    public static final VoxelMaterialType RED_STAINED_GLASS = register("minecraft", "red_stained_glass");
    public static final VoxelMaterialType BLACK_STAINED_GLASS = register("minecraft", "black_stained_glass");
    public static final VoxelMaterialType OAK_TRAPDOOR = register("minecraft", "oak_trapdoor");
    public static final VoxelMaterialType SPRUCE_TRAPDOOR = register("minecraft", "spruce_trapdoor");
    public static final VoxelMaterialType BIRCH_TRAPDOOR = register("minecraft", "birch_trapdoor");
    public static final VoxelMaterialType JUNGLE_TRAPDOOR = register("minecraft", "jungle_trapdoor");
    public static final VoxelMaterialType ACACIA_TRAPDOOR = register("minecraft", "acacia_trapdoor");
    public static final VoxelMaterialType DARK_OAK_TRAPDOOR = register("minecraft", "dark_oak_trapdoor");
    public static final VoxelMaterialType STONE_BRICKS = register("minecraft", "stone_bricks");
    public static final VoxelMaterialType MOSSY_STONE_BRICKS = register("minecraft", "mossy_stone_bricks");
    public static final VoxelMaterialType CRACKED_STONE_BRICKS = register("minecraft", "cracked_stone_bricks");
    public static final VoxelMaterialType CHISELED_STONE_BRICKS = register("minecraft", "chiseled_stone_bricks");
    public static final VoxelMaterialType INFESTED_STONE = register("minecraft", "infested_stone");
    public static final VoxelMaterialType INFESTED_COBBLESTONE = register("minecraft", "infested_cobblestone");
    public static final VoxelMaterialType INFESTED_STONE_BRICKS = register("minecraft", "infested_stone_bricks");
    public static final VoxelMaterialType INFESTED_MOSSY_STONE_BRICKS = register("minecraft", "infested_mossy_stone_bricks");
    public static final VoxelMaterialType INFESTED_CRACKED_STONE_BRICKS = register("minecraft", "infested_cracked_stone_bricks");
    public static final VoxelMaterialType INFESTED_CHISELED_STONE_BRICKS = register("minecraft", "infested_chiseled_stone_bricks");
    public static final VoxelMaterialType BROWN_MUSHROOM_BLOCK = register("minecraft", "brown_mushroom_block");
    public static final VoxelMaterialType RED_MUSHROOM_BLOCK = register("minecraft", "red_mushroom_block");
    public static final VoxelMaterialType MUSHROOM_STEM = register("minecraft", "mushroom_stem");
    public static final VoxelMaterialType IRON_BARS = register("minecraft", "iron_bars");
    public static final VoxelMaterialType CHAIN = register("minecraft", "chain");
    public static final VoxelMaterialType GLASS_PANE = register("minecraft", "glass_pane");
    public static final VoxelMaterialType MELON = register("minecraft", "melon");
    public static final VoxelMaterialType ATTACHED_PUMPKIN_STEM = register("minecraft", "attached_pumpkin_stem", Tags.FALLSOFF);
    public static final VoxelMaterialType ATTACHED_MELON_STEM = register("minecraft", "attached_melon_stem", Tags.FALLSOFF);
    public static final VoxelMaterialType PUMPKIN_STEM = register("minecraft", "pumpkin_stem", Tags.FALLSOFF);
    public static final VoxelMaterialType MELON_STEM = register("minecraft", "melon_stem", Tags.FALLSOFF);
    public static final VoxelMaterialType VINE = register("minecraft", "vine", Tags.FALLSOFF);
    public static final VoxelMaterialType GLOW_LICHEN = register("minecraft", "glow_lichen", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType OAK_FENCE_GATE = register("minecraft", "oak_fence_gate");
    public static final VoxelMaterialType BRICK_STAIRS = register("minecraft", "brick_stairs");
    public static final VoxelMaterialType STONE_BRICK_STAIRS = register("minecraft", "stone_brick_stairs");
    public static final VoxelMaterialType MYCELIUM = register("minecraft", "mycelium");
    public static final VoxelMaterialType LILY_PAD = register("minecraft", "lily_pad", Tags.FALLSOFF);
    public static final VoxelMaterialType NETHER_BRICKS = register("minecraft", "nether_bricks");
    public static final VoxelMaterialType NETHER_BRICK_FENCE = register("minecraft", "nether_brick_fence");
    public static final VoxelMaterialType NETHER_BRICK_STAIRS = register("minecraft", "nether_brick_stairs");
    public static final VoxelMaterialType NETHER_WART = register("minecraft", "nether_wart", Tags.FALLSOFF);
    public static final VoxelMaterialType ENCHANTING_TABLE = register("minecraft", "enchanting_table");
    public static final VoxelMaterialType BREWING_STAND = register("minecraft", "brewing_stand");
    public static final VoxelMaterialType CAULDRON = register("minecraft", "cauldron");
    public static final VoxelMaterialType WATER_CAULDRON = register("minecraft", "water_cauldron", V1_17);
    public static final VoxelMaterialType LAVA_CAULDRON = register("minecraft", "lava_cauldron", V1_17);
    public static final VoxelMaterialType POWDER_SNOW_CAULDRON = register("minecraft", "powder_snow_cauldron", V1_17);
    public static final VoxelMaterialType END_PORTAL = register("minecraft", "end_portal");
    public static final VoxelMaterialType END_PORTAL_FRAME = register("minecraft", "end_portal_frame");
    public static final VoxelMaterialType END_STONE = register("minecraft", "end_stone");
    public static final VoxelMaterialType DRAGON_EGG = register("minecraft", "dragon_egg");
    public static final VoxelMaterialType REDSTONE_LAMP = register("minecraft", "redstone_lamp");
    public static final VoxelMaterialType COCOA = register("minecraft", "cocoa", Tags.FALLSOFF);
    public static final VoxelMaterialType SANDSTONE_STAIRS = register("minecraft", "sandstone_stairs");
    public static final VoxelMaterialType EMERALD_ORE = register("minecraft", "emerald_ore");
    public static final VoxelMaterialType DEEPSLATE_EMERALD_ORE = register("minecraft", "deepslate_emerald_ore", V1_17);
    public static final VoxelMaterialType ENDER_CHEST = register("minecraft", "ender_chest");
    public static final VoxelMaterialType TRIPWIRE_HOOK = register("minecraft", "tripwire_hook", Tags.FALLSOFF);
    public static final VoxelMaterialType TRIPWIRE = register("minecraft", "tripwire");
    public static final VoxelMaterialType EMERALD_BLOCK = register("minecraft", "emerald_block");
    public static final VoxelMaterialType SPRUCE_STAIRS = register("minecraft", "spruce_stairs");
    public static final VoxelMaterialType BIRCH_STAIRS = register("minecraft", "birch_stairs");
    public static final VoxelMaterialType JUNGLE_STAIRS = register("minecraft", "jungle_stairs");
    public static final VoxelMaterialType COMMAND_BLOCK = register("minecraft", "command_block");
    public static final VoxelMaterialType BEACON = register("minecraft", "beacon");
    public static final VoxelMaterialType COBBLESTONE_WALL = register("minecraft", "cobblestone_wall");
    public static final VoxelMaterialType MOSSY_COBBLESTONE_WALL = register("minecraft", "mossy_cobblestone_wall");
    public static final VoxelMaterialType FLOWER_POT = register("minecraft", "flower_pot");
    public static final VoxelMaterialType POTTED_OAK_SAPLING = register("minecraft", "potted_oak_sapling");
    public static final VoxelMaterialType POTTED_SPRUCE_SAPLING = register("minecraft", "potted_spruce_sapling");
    public static final VoxelMaterialType POTTED_BIRCH_SAPLING = register("minecraft", "potted_birch_sapling");
    public static final VoxelMaterialType POTTED_JUNGLE_SAPLING = register("minecraft", "potted_jungle_sapling");
    public static final VoxelMaterialType POTTED_ACACIA_SAPLING = register("minecraft", "potted_acacia_sapling");
    public static final VoxelMaterialType POTTED_DARK_OAK_SAPLING = register("minecraft", "potted_dark_oak_sapling");
    public static final VoxelMaterialType POTTED_FERN = register("minecraft", "potted_fern");
    public static final VoxelMaterialType POTTED_DANDELION = register("minecraft", "potted_dandelion");
    public static final VoxelMaterialType POTTED_POPPY = register("minecraft", "potted_poppy");
    public static final VoxelMaterialType POTTED_BLUE_ORCHID = register("minecraft", "potted_blue_orchid");
    public static final VoxelMaterialType POTTED_ALLIUM = register("minecraft", "potted_allium");
    public static final VoxelMaterialType POTTED_AZURE_BLUET = register("minecraft", "potted_azure_bluet");
    public static final VoxelMaterialType POTTED_RED_TULIP = register("minecraft", "potted_red_tulip");
    public static final VoxelMaterialType POTTED_ORANGE_TULIP = register("minecraft", "potted_orange_tulip");
    public static final VoxelMaterialType POTTED_WHITE_TULIP = register("minecraft", "potted_white_tulip");
    public static final VoxelMaterialType POTTED_PINK_TULIP = register("minecraft", "potted_pink_tulip");
    public static final VoxelMaterialType POTTED_OXEYE_DAISY = register("minecraft", "potted_oxeye_daisy");
    public static final VoxelMaterialType POTTED_CORNFLOWER = register("minecraft", "potted_cornflower");
    public static final VoxelMaterialType POTTED_LILY_OF_THE_VALLEY = register("minecraft", "potted_lily_of_the_valley");
    public static final VoxelMaterialType POTTED_WITHER_ROSE = register("minecraft", "potted_wither_rose");
    public static final VoxelMaterialType POTTED_RED_MUSHROOM = register("minecraft", "potted_red_mushroom");
    public static final VoxelMaterialType POTTED_BROWN_MUSHROOM = register("minecraft", "potted_brown_mushroom");
    public static final VoxelMaterialType POTTED_DEAD_BUSH = register("minecraft", "potted_dead_bush");
    public static final VoxelMaterialType POTTED_CACTUS = register("minecraft", "potted_cactus");
    public static final VoxelMaterialType CARROTS = register("minecraft", "carrots", Tags.FALLSOFF);
    public static final VoxelMaterialType POTATOES = register("minecraft", "potatoes", Tags.FALLSOFF);
    public static final VoxelMaterialType OAK_BUTTON = register("minecraft", "oak_button", Tags.FALLSOFF);
    public static final VoxelMaterialType SPRUCE_BUTTON = register("minecraft", "spruce_button", Tags.FALLSOFF);
    public static final VoxelMaterialType BIRCH_BUTTON = register("minecraft", "birch_button", Tags.FALLSOFF);
    public static final VoxelMaterialType JUNGLE_BUTTON = register("minecraft", "jungle_button", Tags.FALLSOFF);
    public static final VoxelMaterialType ACACIA_BUTTON = register("minecraft", "acacia_button", Tags.FALLSOFF);
    public static final VoxelMaterialType DARK_OAK_BUTTON = register("minecraft", "dark_oak_button", Tags.FALLSOFF);
    public static final VoxelMaterialType SKELETON_SKULL = register("minecraft", "skeleton_skull");
    public static final VoxelMaterialType SKELETON_WALL_SKULL = register("minecraft", "skeleton_wall_skull");
    public static final VoxelMaterialType WITHER_SKELETON_SKULL = register("minecraft", "wither_skeleton_skull");
    public static final VoxelMaterialType WITHER_SKELETON_WALL_SKULL = register("minecraft", "wither_skeleton_wall_skull");
    public static final VoxelMaterialType ZOMBIE_HEAD = register("minecraft", "zombie_head");
    public static final VoxelMaterialType ZOMBIE_WALL_HEAD = register("minecraft", "zombie_wall_head");
    public static final VoxelMaterialType PLAYER_HEAD = register("minecraft", "player_head");
    public static final VoxelMaterialType PLAYER_WALL_HEAD = register("minecraft", "player_wall_head");
    public static final VoxelMaterialType CREEPER_HEAD = register("minecraft", "creeper_head");
    public static final VoxelMaterialType CREEPER_WALL_HEAD = register("minecraft", "creeper_wall_head");
    public static final VoxelMaterialType DRAGON_HEAD = register("minecraft", "dragon_head");
    public static final VoxelMaterialType DRAGON_WALL_HEAD = register("minecraft", "dragon_wall_head");
    public static final VoxelMaterialType ANVIL = register("minecraft", "anvil");
    public static final VoxelMaterialType CHIPPED_ANVIL = register("minecraft", "chipped_anvil");
    public static final VoxelMaterialType DAMAGED_ANVIL = register("minecraft", "damaged_anvil");
    public static final VoxelMaterialType TRAPPED_CHEST = register("minecraft", "trapped_chest");
    public static final VoxelMaterialType LIGHT_WEIGHTED_PRESSURE_PLATE = register("minecraft", "light_weighted_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterialType HEAVY_WEIGHTED_PRESSURE_PLATE = register("minecraft", "heavy_weighted_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterialType COMPARATOR = register("minecraft", "comparator", Tags.FALLSOFF);
    public static final VoxelMaterialType DAYLIGHT_DETECTOR = register("minecraft", "daylight_detector");
    public static final VoxelMaterialType REDSTONE_BLOCK = register("minecraft", "redstone_block");
    public static final VoxelMaterialType NETHER_QUARTZ_ORE = register("minecraft", "nether_quartz_ore");
    public static final VoxelMaterialType HOPPER = register("minecraft", "hopper");
    public static final VoxelMaterialType QUARTZ_BLOCK = register("minecraft", "quartz_block");
    public static final VoxelMaterialType CHISELED_QUARTZ_BLOCK = register("minecraft", "chiseled_quartz_block");
    public static final VoxelMaterialType QUARTZ_PILLAR = register("minecraft", "quartz_pillar");
    public static final VoxelMaterialType QUARTZ_STAIRS = register("minecraft", "quartz_stairs");
    public static final VoxelMaterialType ACTIVATOR_RAIL = register("minecraft", "activator_rail", Tags.FALLSOFF);
    public static final VoxelMaterialType DROPPER = register("minecraft", "dropper");
    public static final VoxelMaterialType WHITE_TERRACOTTA = register("minecraft", "white_terracotta");
    public static final VoxelMaterialType ORANGE_TERRACOTTA = register("minecraft", "orange_terracotta");
    public static final VoxelMaterialType MAGENTA_TERRACOTTA = register("minecraft", "magenta_terracotta");
    public static final VoxelMaterialType LIGHT_BLUE_TERRACOTTA = register("minecraft", "light_blue_terracotta");
    public static final VoxelMaterialType YELLOW_TERRACOTTA = register("minecraft", "yellow_terracotta");
    public static final VoxelMaterialType LIME_TERRACOTTA = register("minecraft", "lime_terracotta");
    public static final VoxelMaterialType PINK_TERRACOTTA = register("minecraft", "pink_terracotta");
    public static final VoxelMaterialType GRAY_TERRACOTTA = register("minecraft", "gray_terracotta");
    public static final VoxelMaterialType LIGHT_GRAY_TERRACOTTA = register("minecraft", "light_gray_terracotta");
    public static final VoxelMaterialType CYAN_TERRACOTTA = register("minecraft", "cyan_terracotta");
    public static final VoxelMaterialType PURPLE_TERRACOTTA = register("minecraft", "purple_terracotta");
    public static final VoxelMaterialType BLUE_TERRACOTTA = register("minecraft", "blue_terracotta");
    public static final VoxelMaterialType BROWN_TERRACOTTA = register("minecraft", "brown_terracotta");
    public static final VoxelMaterialType GREEN_TERRACOTTA = register("minecraft", "green_terracotta");
    public static final VoxelMaterialType RED_TERRACOTTA = register("minecraft", "red_terracotta");
    public static final VoxelMaterialType BLACK_TERRACOTTA = register("minecraft", "black_terracotta");
    public static final VoxelMaterialType WHITE_STAINED_GLASS_PANE = register("minecraft", "white_stained_glass_pane");
    public static final VoxelMaterialType ORANGE_STAINED_GLASS_PANE = register("minecraft", "orange_stained_glass_pane");
    public static final VoxelMaterialType MAGENTA_STAINED_GLASS_PANE = register("minecraft", "magenta_stained_glass_pane");
    public static final VoxelMaterialType LIGHT_BLUE_STAINED_GLASS_PANE = register("minecraft", "light_blue_stained_glass_pane");
    public static final VoxelMaterialType YELLOW_STAINED_GLASS_PANE = register("minecraft", "yellow_stained_glass_pane");
    public static final VoxelMaterialType LIME_STAINED_GLASS_PANE = register("minecraft", "lime_stained_glass_pane");
    public static final VoxelMaterialType PINK_STAINED_GLASS_PANE = register("minecraft", "pink_stained_glass_pane");
    public static final VoxelMaterialType GRAY_STAINED_GLASS_PANE = register("minecraft", "gray_stained_glass_pane");
    public static final VoxelMaterialType LIGHT_GRAY_STAINED_GLASS_PANE = register("minecraft", "light_gray_stained_glass_pane");
    public static final VoxelMaterialType CYAN_STAINED_GLASS_PANE = register("minecraft", "cyan_stained_glass_pane");
    public static final VoxelMaterialType PURPLE_STAINED_GLASS_PANE = register("minecraft", "purple_stained_glass_pane");
    public static final VoxelMaterialType BLUE_STAINED_GLASS_PANE = register("minecraft", "blue_stained_glass_pane");
    public static final VoxelMaterialType BROWN_STAINED_GLASS_PANE = register("minecraft", "brown_stained_glass_pane");
    public static final VoxelMaterialType GREEN_STAINED_GLASS_PANE = register("minecraft", "green_stained_glass_pane");
    public static final VoxelMaterialType RED_STAINED_GLASS_PANE = register("minecraft", "red_stained_glass_pane");
    public static final VoxelMaterialType BLACK_STAINED_GLASS_PANE = register("minecraft", "black_stained_glass_pane");
    public static final VoxelMaterialType ACACIA_STAIRS = register("minecraft", "acacia_stairs");
    public static final VoxelMaterialType DARK_OAK_STAIRS = register("minecraft", "dark_oak_stairs");
    public static final VoxelMaterialType SLIME_BLOCK = register("minecraft", "slime_block");
    public static final VoxelMaterialType BARRIER = register("minecraft", "barrier");
    public static final VoxelMaterialType LIGHT = register("minecraft", "light", V1_17);
    public static final VoxelMaterialType IRON_TRAPDOOR = register("minecraft", "iron_trapdoor");
    public static final VoxelMaterialType PRISMARINE = register("minecraft", "prismarine");
    public static final VoxelMaterialType PRISMARINE_BRICKS = register("minecraft", "prismarine_bricks");
    public static final VoxelMaterialType DARK_PRISMARINE = register("minecraft", "dark_prismarine");
    public static final VoxelMaterialType PRISMARINE_STAIRS = register("minecraft", "prismarine_stairs");
    public static final VoxelMaterialType PRISMARINE_BRICK_STAIRS = register("minecraft", "prismarine_brick_stairs");
    public static final VoxelMaterialType DARK_PRISMARINE_STAIRS = register("minecraft", "dark_prismarine_stairs");
    public static final VoxelMaterialType PRISMARINE_SLAB = register("minecraft", "prismarine_slab");
    public static final VoxelMaterialType PRISMARINE_BRICK_SLAB = register("minecraft", "prismarine_brick_slab");
    public static final VoxelMaterialType DARK_PRISMARINE_SLAB = register("minecraft", "dark_prismarine_slab");
    public static final VoxelMaterialType SEA_LANTERN = register("minecraft", "sea_lantern");
    public static final VoxelMaterialType HAY_BLOCK = register("minecraft", "hay_block");
    public static final VoxelMaterialType WHITE_CARPET = register("minecraft", "white_carpet", Tags.FALLSOFF);
    public static final VoxelMaterialType ORANGE_CARPET = register("minecraft", "orange_carpet", Tags.FALLSOFF);
    public static final VoxelMaterialType MAGENTA_CARPET = register("minecraft", "magenta_carpet", Tags.FALLSOFF);
    public static final VoxelMaterialType LIGHT_BLUE_CARPET = register("minecraft", "light_blue_carpet", Tags.FALLSOFF);
    public static final VoxelMaterialType YELLOW_CARPET = register("minecraft", "yellow_carpet", Tags.FALLSOFF);
    public static final VoxelMaterialType LIME_CARPET = register("minecraft", "lime_carpet", Tags.FALLSOFF);
    public static final VoxelMaterialType PINK_CARPET = register("minecraft", "pink_carpet", Tags.FALLSOFF);
    public static final VoxelMaterialType GRAY_CARPET = register("minecraft", "gray_carpet", Tags.FALLSOFF);
    public static final VoxelMaterialType LIGHT_GRAY_CARPET = register("minecraft", "light_gray_carpet", Tags.FALLSOFF);
    public static final VoxelMaterialType CYAN_CARPET = register("minecraft", "cyan_carpet", Tags.FALLSOFF);
    public static final VoxelMaterialType PURPLE_CARPET = register("minecraft", "purple_carpet", Tags.FALLSOFF);
    public static final VoxelMaterialType BLUE_CARPET = register("minecraft", "blue_carpet", Tags.FALLSOFF);
    public static final VoxelMaterialType BROWN_CARPET = register("minecraft", "brown_carpet", Tags.FALLSOFF);
    public static final VoxelMaterialType GREEN_CARPET = register("minecraft", "green_carpet", Tags.FALLSOFF);
    public static final VoxelMaterialType RED_CARPET = register("minecraft", "red_carpet", Tags.FALLSOFF);
    public static final VoxelMaterialType BLACK_CARPET = register("minecraft", "black_carpet", Tags.FALLSOFF);
    public static final VoxelMaterialType TERRACOTTA = register("minecraft", "terracotta");
    public static final VoxelMaterialType COAL_BLOCK = register("minecraft", "coal_block");
    public static final VoxelMaterialType PACKED_ICE = register("minecraft", "packed_ice");
    public static final VoxelMaterialType SUNFLOWER = register("minecraft", "sunflower", Tags.FALLSOFF);
    public static final VoxelMaterialType LILAC = register("minecraft", "lilac", Tags.FALLSOFF);
    public static final VoxelMaterialType ROSE_BUSH = register("minecraft", "rose_bush", Tags.FALLSOFF);
    public static final VoxelMaterialType PEONY = register("minecraft", "peony", Tags.FALLSOFF);
    public static final VoxelMaterialType TALL_GRASS = register("minecraft", "tall_grass", Tags.FALLSOFF);
    public static final VoxelMaterialType LARGE_FERN = register("minecraft", "large_fern", Tags.FALLSOFF);
    public static final VoxelMaterialType WHITE_BANNER = register("minecraft", "white_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType ORANGE_BANNER = register("minecraft", "orange_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType MAGENTA_BANNER = register("minecraft", "magenta_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType LIGHT_BLUE_BANNER = register("minecraft", "light_blue_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType YELLOW_BANNER = register("minecraft", "yellow_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType LIME_BANNER = register("minecraft", "lime_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType PINK_BANNER = register("minecraft", "pink_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType GRAY_BANNER = register("minecraft", "gray_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType LIGHT_GRAY_BANNER = register("minecraft", "light_gray_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType CYAN_BANNER = register("minecraft", "cyan_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType PURPLE_BANNER = register("minecraft", "purple_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType BLUE_BANNER = register("minecraft", "blue_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType BROWN_BANNER = register("minecraft", "brown_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType GREEN_BANNER = register("minecraft", "green_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType RED_BANNER = register("minecraft", "red_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType BLACK_BANNER = register("minecraft", "black_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType WHITE_WALL_BANNER = register("minecraft", "white_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType ORANGE_WALL_BANNER = register("minecraft", "orange_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType MAGENTA_WALL_BANNER = register("minecraft", "magenta_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType LIGHT_BLUE_WALL_BANNER = register("minecraft", "light_blue_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType YELLOW_WALL_BANNER = register("minecraft", "yellow_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType LIME_WALL_BANNER = register("minecraft", "lime_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType PINK_WALL_BANNER = register("minecraft", "pink_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType GRAY_WALL_BANNER = register("minecraft", "gray_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType LIGHT_GRAY_WALL_BANNER = register("minecraft", "light_gray_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType CYAN_WALL_BANNER = register("minecraft", "cyan_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType PURPLE_WALL_BANNER = register("minecraft", "purple_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType BLUE_WALL_BANNER = register("minecraft", "blue_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType BROWN_WALL_BANNER = register("minecraft", "brown_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType GREEN_WALL_BANNER = register("minecraft", "green_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType RED_WALL_BANNER = register("minecraft", "red_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType BLACK_WALL_BANNER = register("minecraft", "black_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterialType RED_SANDSTONE = register("minecraft", "red_sandstone");
    public static final VoxelMaterialType CHISELED_RED_SANDSTONE = register("minecraft", "chiseled_red_sandstone");
    public static final VoxelMaterialType CUT_RED_SANDSTONE = register("minecraft", "cut_red_sandstone");
    public static final VoxelMaterialType RED_SANDSTONE_STAIRS = register("minecraft", "red_sandstone_stairs");
    public static final VoxelMaterialType OAK_SLAB = register("minecraft", "oak_slab");
    public static final VoxelMaterialType SPRUCE_SLAB = register("minecraft", "spruce_slab");
    public static final VoxelMaterialType BIRCH_SLAB = register("minecraft", "birch_slab");
    public static final VoxelMaterialType JUNGLE_SLAB = register("minecraft", "jungle_slab");
    public static final VoxelMaterialType ACACIA_SLAB = register("minecraft", "acacia_slab");
    public static final VoxelMaterialType DARK_OAK_SLAB = register("minecraft", "dark_oak_slab");
    public static final VoxelMaterialType STONE_SLAB = register("minecraft", "stone_slab");
    public static final VoxelMaterialType SMOOTH_STONE_SLAB = register("minecraft", "smooth_stone_slab");
    public static final VoxelMaterialType SANDSTONE_SLAB = register("minecraft", "sandstone_slab");
    public static final VoxelMaterialType CUT_SANDSTONE_SLAB = register("minecraft", "cut_sandstone_slab");
    public static final VoxelMaterialType PETRIFIED_OAK_SLAB = register("minecraft", "petrified_oak_slab");
    public static final VoxelMaterialType COBBLESTONE_SLAB = register("minecraft", "cobblestone_slab");
    public static final VoxelMaterialType BRICK_SLAB = register("minecraft", "brick_slab");
    public static final VoxelMaterialType STONE_BRICK_SLAB = register("minecraft", "stone_brick_slab");
    public static final VoxelMaterialType NETHER_BRICK_SLAB = register("minecraft", "nether_brick_slab");
    public static final VoxelMaterialType QUARTZ_SLAB = register("minecraft", "quartz_slab");
    public static final VoxelMaterialType RED_SANDSTONE_SLAB = register("minecraft", "red_sandstone_slab");
    public static final VoxelMaterialType CUT_RED_SANDSTONE_SLAB = register("minecraft", "cut_red_sandstone_slab");
    public static final VoxelMaterialType PURPUR_SLAB = register("minecraft", "purpur_slab");
    public static final VoxelMaterialType SMOOTH_STONE = register("minecraft", "smooth_stone");
    public static final VoxelMaterialType SMOOTH_SANDSTONE = register("minecraft", "smooth_sandstone");
    public static final VoxelMaterialType SMOOTH_QUARTZ = register("minecraft", "smooth_quartz");
    public static final VoxelMaterialType SMOOTH_RED_SANDSTONE = register("minecraft", "smooth_red_sandstone");
    public static final VoxelMaterialType SPRUCE_FENCE_GATE = register("minecraft", "spruce_fence_gate");
    public static final VoxelMaterialType BIRCH_FENCE_GATE = register("minecraft", "birch_fence_gate");
    public static final VoxelMaterialType JUNGLE_FENCE_GATE = register("minecraft", "jungle_fence_gate");
    public static final VoxelMaterialType ACACIA_FENCE_GATE = register("minecraft", "acacia_fence_gate");
    public static final VoxelMaterialType DARK_OAK_FENCE_GATE = register("minecraft", "dark_oak_fence_gate");
    public static final VoxelMaterialType SPRUCE_FENCE = register("minecraft", "spruce_fence");
    public static final VoxelMaterialType BIRCH_FENCE = register("minecraft", "birch_fence");
    public static final VoxelMaterialType JUNGLE_FENCE = register("minecraft", "jungle_fence");
    public static final VoxelMaterialType ACACIA_FENCE = register("minecraft", "acacia_fence");
    public static final VoxelMaterialType DARK_OAK_FENCE = register("minecraft", "dark_oak_fence");
    public static final VoxelMaterialType SPRUCE_DOOR = register("minecraft", "spruce_door", Tags.FALLSOFF);
    public static final VoxelMaterialType BIRCH_DOOR = register("minecraft", "birch_door", Tags.FALLSOFF);
    public static final VoxelMaterialType JUNGLE_DOOR = register("minecraft", "jungle_door", Tags.FALLSOFF);
    public static final VoxelMaterialType ACACIA_DOOR = register("minecraft", "acacia_door", Tags.FALLSOFF);
    public static final VoxelMaterialType DARK_OAK_DOOR = register("minecraft", "dark_oak_door", Tags.FALLSOFF);
    public static final VoxelMaterialType END_ROD = register("minecraft", "end_rod");
    public static final VoxelMaterialType CHORUS_PLANT = register("minecraft", "chorus_plant", Tags.FALLSOFF);
    public static final VoxelMaterialType CHORUS_FLOWER = register("minecraft", "chorus_flower", Tags.FALLSOFF);
    public static final VoxelMaterialType PURPUR_BLOCK = register("minecraft", "purpur_block");
    public static final VoxelMaterialType PURPUR_PILLAR = register("minecraft", "purpur_pillar");
    public static final VoxelMaterialType PURPUR_STAIRS = register("minecraft", "purpur_stairs");
    public static final VoxelMaterialType END_STONE_BRICKS = register("minecraft", "end_stone_bricks");
    public static final VoxelMaterialType BEETROOTS = register("minecraft", "beetroots", Tags.FALLSOFF);
    public static final VoxelMaterialType DIRT_PATH = register("minecraft", "dirt_path", V1_17);
    public static final VoxelMaterialType END_GATEWAY = register("minecraft", "end_gateway");
    public static final VoxelMaterialType REPEATING_COMMAND_BLOCK = register("minecraft", "repeating_command_block");
    public static final VoxelMaterialType CHAIN_COMMAND_BLOCK = register("minecraft", "chain_command_block");
    public static final VoxelMaterialType FROSTED_ICE = register("minecraft", "frosted_ice");
    public static final VoxelMaterialType MAGMA_BLOCK = register("minecraft", "magma_block");
    public static final VoxelMaterialType NETHER_WART_BLOCK = register("minecraft", "nether_wart_block");
    public static final VoxelMaterialType RED_NETHER_BRICKS = register("minecraft", "red_nether_bricks");
    public static final VoxelMaterialType BONE_BLOCK = register("minecraft", "bone_block");
    public static final VoxelMaterialType STRUCTURE_VOID = register("minecraft", "structure_void");
    public static final VoxelMaterialType OBSERVER = register("minecraft", "observer");
    public static final VoxelMaterialType SHULKER_BOX = register("minecraft", "shulker_box");
    public static final VoxelMaterialType WHITE_SHULKER_BOX = register("minecraft", "white_shulker_box");
    public static final VoxelMaterialType ORANGE_SHULKER_BOX = register("minecraft", "orange_shulker_box");
    public static final VoxelMaterialType MAGENTA_SHULKER_BOX = register("minecraft", "magenta_shulker_box");
    public static final VoxelMaterialType LIGHT_BLUE_SHULKER_BOX = register("minecraft", "light_blue_shulker_box");
    public static final VoxelMaterialType YELLOW_SHULKER_BOX = register("minecraft", "yellow_shulker_box");
    public static final VoxelMaterialType LIME_SHULKER_BOX = register("minecraft", "lime_shulker_box");
    public static final VoxelMaterialType PINK_SHULKER_BOX = register("minecraft", "pink_shulker_box");
    public static final VoxelMaterialType GRAY_SHULKER_BOX = register("minecraft", "gray_shulker_box");
    public static final VoxelMaterialType LIGHT_GRAY_SHULKER_BOX = register("minecraft", "light_gray_shulker_box");
    public static final VoxelMaterialType CYAN_SHULKER_BOX = register("minecraft", "cyan_shulker_box");
    public static final VoxelMaterialType PURPLE_SHULKER_BOX = register("minecraft", "purple_shulker_box");
    public static final VoxelMaterialType BLUE_SHULKER_BOX = register("minecraft", "blue_shulker_box");
    public static final VoxelMaterialType BROWN_SHULKER_BOX = register("minecraft", "brown_shulker_box");
    public static final VoxelMaterialType GREEN_SHULKER_BOX = register("minecraft", "green_shulker_box");
    public static final VoxelMaterialType RED_SHULKER_BOX = register("minecraft", "red_shulker_box");
    public static final VoxelMaterialType BLACK_SHULKER_BOX = register("minecraft", "black_shulker_box");
    public static final VoxelMaterialType WHITE_GLAZED_TERRACOTTA = register("minecraft", "white_glazed_terracotta");
    public static final VoxelMaterialType ORANGE_GLAZED_TERRACOTTA = register("minecraft", "orange_glazed_terracotta");
    public static final VoxelMaterialType MAGENTA_GLAZED_TERRACOTTA = register("minecraft", "magenta_glazed_terracotta");
    public static final VoxelMaterialType LIGHT_BLUE_GLAZED_TERRACOTTA = register("minecraft", "light_blue_glazed_terracotta");
    public static final VoxelMaterialType YELLOW_GLAZED_TERRACOTTA = register("minecraft", "yellow_glazed_terracotta");
    public static final VoxelMaterialType LIME_GLAZED_TERRACOTTA = register("minecraft", "lime_glazed_terracotta");
    public static final VoxelMaterialType PINK_GLAZED_TERRACOTTA = register("minecraft", "pink_glazed_terracotta");
    public static final VoxelMaterialType GRAY_GLAZED_TERRACOTTA = register("minecraft", "gray_glazed_terracotta");
    public static final VoxelMaterialType LIGHT_GRAY_GLAZED_TERRACOTTA = register("minecraft", "light_gray_glazed_terracotta");
    public static final VoxelMaterialType CYAN_GLAZED_TERRACOTTA = register("minecraft", "cyan_glazed_terracotta");
    public static final VoxelMaterialType PURPLE_GLAZED_TERRACOTTA = register("minecraft", "purple_glazed_terracotta");
    public static final VoxelMaterialType BLUE_GLAZED_TERRACOTTA = register("minecraft", "blue_glazed_terracotta");
    public static final VoxelMaterialType BROWN_GLAZED_TERRACOTTA = register("minecraft", "brown_glazed_terracotta");
    public static final VoxelMaterialType GREEN_GLAZED_TERRACOTTA = register("minecraft", "green_glazed_terracotta");
    public static final VoxelMaterialType RED_GLAZED_TERRACOTTA = register("minecraft", "red_glazed_terracotta");
    public static final VoxelMaterialType BLACK_GLAZED_TERRACOTTA = register("minecraft", "black_glazed_terracotta");
    public static final VoxelMaterialType WHITE_CONCRETE = register("minecraft", "white_concrete");
    public static final VoxelMaterialType ORANGE_CONCRETE = register("minecraft", "orange_concrete");
    public static final VoxelMaterialType MAGENTA_CONCRETE = register("minecraft", "magenta_concrete");
    public static final VoxelMaterialType LIGHT_BLUE_CONCRETE = register("minecraft", "light_blue_concrete");
    public static final VoxelMaterialType YELLOW_CONCRETE = register("minecraft", "yellow_concrete");
    public static final VoxelMaterialType LIME_CONCRETE = register("minecraft", "lime_concrete");
    public static final VoxelMaterialType PINK_CONCRETE = register("minecraft", "pink_concrete");
    public static final VoxelMaterialType GRAY_CONCRETE = register("minecraft", "gray_concrete");
    public static final VoxelMaterialType LIGHT_GRAY_CONCRETE = register("minecraft", "light_gray_concrete");
    public static final VoxelMaterialType CYAN_CONCRETE = register("minecraft", "cyan_concrete");
    public static final VoxelMaterialType PURPLE_CONCRETE = register("minecraft", "purple_concrete");
    public static final VoxelMaterialType BLUE_CONCRETE = register("minecraft", "blue_concrete");
    public static final VoxelMaterialType BROWN_CONCRETE = register("minecraft", "brown_concrete");
    public static final VoxelMaterialType GREEN_CONCRETE = register("minecraft", "green_concrete");
    public static final VoxelMaterialType RED_CONCRETE = register("minecraft", "red_concrete");
    public static final VoxelMaterialType BLACK_CONCRETE = register("minecraft", "black_concrete");
    public static final VoxelMaterialType WHITE_CONCRETE_POWDER = register("minecraft", "white_concrete_powder");
    public static final VoxelMaterialType ORANGE_CONCRETE_POWDER = register("minecraft", "orange_concrete_powder");
    public static final VoxelMaterialType MAGENTA_CONCRETE_POWDER = register("minecraft", "magenta_concrete_powder");
    public static final VoxelMaterialType LIGHT_BLUE_CONCRETE_POWDER = register("minecraft", "light_blue_concrete_powder");
    public static final VoxelMaterialType YELLOW_CONCRETE_POWDER = register("minecraft", "yellow_concrete_powder");
    public static final VoxelMaterialType LIME_CONCRETE_POWDER = register("minecraft", "lime_concrete_powder");
    public static final VoxelMaterialType PINK_CONCRETE_POWDER = register("minecraft", "pink_concrete_powder");
    public static final VoxelMaterialType GRAY_CONCRETE_POWDER = register("minecraft", "gray_concrete_powder");
    public static final VoxelMaterialType LIGHT_GRAY_CONCRETE_POWDER = register("minecraft", "light_gray_concrete_powder");
    public static final VoxelMaterialType CYAN_CONCRETE_POWDER = register("minecraft", "cyan_concrete_powder");
    public static final VoxelMaterialType PURPLE_CONCRETE_POWDER = register("minecraft", "purple_concrete_powder");
    public static final VoxelMaterialType BLUE_CONCRETE_POWDER = register("minecraft", "blue_concrete_powder");
    public static final VoxelMaterialType BROWN_CONCRETE_POWDER = register("minecraft", "brown_concrete_powder");
    public static final VoxelMaterialType GREEN_CONCRETE_POWDER = register("minecraft", "green_concrete_powder");
    public static final VoxelMaterialType RED_CONCRETE_POWDER = register("minecraft", "red_concrete_powder");
    public static final VoxelMaterialType BLACK_CONCRETE_POWDER = register("minecraft", "black_concrete_powder");
    public static final VoxelMaterialType KELP = register("minecraft", "kelp", Tags.FALLSOFF);
    public static final VoxelMaterialType KELP_PLANT = register("minecraft", "kelp_plant", Tags.FALLSOFF);
    public static final VoxelMaterialType DRIED_KELP_BLOCK = register("minecraft", "dried_kelp_block");
    public static final VoxelMaterialType TURTLE_EGG = register("minecraft", "turtle_egg", Tags.FALLSOFF);
    public static final VoxelMaterialType DEAD_TUBE_CORAL_BLOCK = register("minecraft", "dead_tube_coral_block");
    public static final VoxelMaterialType DEAD_BRAIN_CORAL_BLOCK = register("minecraft", "dead_brain_coral_block");
    public static final VoxelMaterialType DEAD_BUBBLE_CORAL_BLOCK = register("minecraft", "dead_bubble_coral_block");
    public static final VoxelMaterialType DEAD_FIRE_CORAL_BLOCK = register("minecraft", "dead_fire_coral_block");
    public static final VoxelMaterialType DEAD_HORN_CORAL_BLOCK = register("minecraft", "dead_horn_coral_block");
    public static final VoxelMaterialType TUBE_CORAL_BLOCK = register("minecraft", "tube_coral_block");
    public static final VoxelMaterialType BRAIN_CORAL_BLOCK = register("minecraft", "brain_coral_block");
    public static final VoxelMaterialType BUBBLE_CORAL_BLOCK = register("minecraft", "bubble_coral_block");
    public static final VoxelMaterialType FIRE_CORAL_BLOCK = register("minecraft", "fire_coral_block");
    public static final VoxelMaterialType HORN_CORAL_BLOCK = register("minecraft", "horn_coral_block");
    public static final VoxelMaterialType DEAD_TUBE_CORAL = register("minecraft", "dead_tube_coral", Tags.FALLSOFF);
    public static final VoxelMaterialType DEAD_BRAIN_CORAL = register("minecraft", "dead_brain_coral", Tags.FALLSOFF);
    public static final VoxelMaterialType DEAD_BUBBLE_CORAL = register("minecraft", "dead_bubble_coral", Tags.FALLSOFF);
    public static final VoxelMaterialType DEAD_FIRE_CORAL = register("minecraft", "dead_fire_coral", Tags.FALLSOFF);
    public static final VoxelMaterialType DEAD_HORN_CORAL = register("minecraft", "dead_horn_coral", Tags.FALLSOFF);
    public static final VoxelMaterialType TUBE_CORAL = register("minecraft", "tube_coral", Tags.FALLSOFF);
    public static final VoxelMaterialType BRAIN_CORAL = register("minecraft", "brain_coral", Tags.FALLSOFF);
    public static final VoxelMaterialType BUBBLE_CORAL = register("minecraft", "bubble_coral", Tags.FALLSOFF);
    public static final VoxelMaterialType FIRE_CORAL = register("minecraft", "fire_coral", Tags.FALLSOFF);
    public static final VoxelMaterialType HORN_CORAL = register("minecraft", "horn_coral", Tags.FALLSOFF);
    public static final VoxelMaterialType DEAD_TUBE_CORAL_FAN = register("minecraft", "dead_tube_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType DEAD_BRAIN_CORAL_FAN = register("minecraft", "dead_brain_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType DEAD_BUBBLE_CORAL_FAN = register("minecraft", "dead_bubble_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType DEAD_FIRE_CORAL_FAN = register("minecraft", "dead_fire_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType DEAD_HORN_CORAL_FAN = register("minecraft", "dead_horn_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType TUBE_CORAL_FAN = register("minecraft", "tube_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType BRAIN_CORAL_FAN = register("minecraft", "brain_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType BUBBLE_CORAL_FAN = register("minecraft", "bubble_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType FIRE_CORAL_FAN = register("minecraft", "fire_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType HORN_CORAL_FAN = register("minecraft", "horn_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType DEAD_TUBE_CORAL_WALL_FAN = register("minecraft", "dead_tube_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType DEAD_BRAIN_CORAL_WALL_FAN = register("minecraft", "dead_brain_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType DEAD_BUBBLE_CORAL_WALL_FAN = register("minecraft", "dead_bubble_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType DEAD_FIRE_CORAL_WALL_FAN = register("minecraft", "dead_fire_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType DEAD_HORN_CORAL_WALL_FAN = register("minecraft", "dead_horn_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType TUBE_CORAL_WALL_FAN = register("minecraft", "tube_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType BRAIN_CORAL_WALL_FAN = register("minecraft", "brain_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType BUBBLE_CORAL_WALL_FAN = register("minecraft", "bubble_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType FIRE_CORAL_WALL_FAN = register("minecraft", "fire_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType HORN_CORAL_WALL_FAN = register("minecraft", "horn_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterialType SEA_PICKLE = register("minecraft", "sea_pickle", Tags.FALLSOFF);
    public static final VoxelMaterialType BLUE_ICE = register("minecraft", "blue_ice");
    public static final VoxelMaterialType CONDUIT = register("minecraft", "conduit");
    public static final VoxelMaterialType BAMBOO_SAPLING = register("minecraft", "bamboo_sapling", Tags.FALLSOFF);
    public static final VoxelMaterialType BAMBOO = register("minecraft", "bamboo", Tags.FALLSOFF);
    public static final VoxelMaterialType POTTED_BAMBOO = register("minecraft", "potted_bamboo");
    public static final VoxelMaterialType VOID_AIR = register("minecraft", "void_air", Tags.AIR);
    public static final VoxelMaterialType CAVE_AIR = register("minecraft", "cave_air", Tags.AIR);
    public static final VoxelMaterialType BUBBLE_COLUMN = register("minecraft", "bubble_column", Tags.FLUIDS);
    public static final VoxelMaterialType POLISHED_GRANITE_STAIRS = register("minecraft", "polished_granite_stairs");
    public static final VoxelMaterialType SMOOTH_RED_SANDSTONE_STAIRS = register("minecraft", "smooth_red_sandstone_stairs");
    public static final VoxelMaterialType MOSSY_STONE_BRICK_STAIRS = register("minecraft", "mossy_stone_brick_stairs");
    public static final VoxelMaterialType POLISHED_DIORITE_STAIRS = register("minecraft", "polished_diorite_stairs");
    public static final VoxelMaterialType MOSSY_COBBLESTONE_STAIRS = register("minecraft", "mossy_cobblestone_stairs");
    public static final VoxelMaterialType END_STONE_BRICK_STAIRS = register("minecraft", "end_stone_brick_stairs");
    public static final VoxelMaterialType STONE_STAIRS = register("minecraft", "stone_stairs");
    public static final VoxelMaterialType SMOOTH_SANDSTONE_STAIRS = register("minecraft", "smooth_sandstone_stairs");
    public static final VoxelMaterialType SMOOTH_QUARTZ_STAIRS = register("minecraft", "smooth_quartz_stairs");
    public static final VoxelMaterialType GRANITE_STAIRS = register("minecraft", "granite_stairs");
    public static final VoxelMaterialType ANDESITE_STAIRS = register("minecraft", "andesite_stairs");
    public static final VoxelMaterialType RED_NETHER_BRICK_STAIRS = register("minecraft", "red_nether_brick_stairs");
    public static final VoxelMaterialType POLISHED_ANDESITE_STAIRS = register("minecraft", "polished_andesite_stairs");
    public static final VoxelMaterialType DIORITE_STAIRS = register("minecraft", "diorite_stairs");
    public static final VoxelMaterialType POLISHED_GRANITE_SLAB = register("minecraft", "polished_granite_slab");
    public static final VoxelMaterialType SMOOTH_RED_SANDSTONE_SLAB = register("minecraft", "smooth_red_sandstone_slab");
    public static final VoxelMaterialType MOSSY_STONE_BRICK_SLAB = register("minecraft", "mossy_stone_brick_slab");
    public static final VoxelMaterialType POLISHED_DIORITE_SLAB = register("minecraft", "polished_diorite_slab");
    public static final VoxelMaterialType MOSSY_COBBLESTONE_SLAB = register("minecraft", "mossy_cobblestone_slab");
    public static final VoxelMaterialType END_STONE_BRICK_SLAB = register("minecraft", "end_stone_brick_slab");
    public static final VoxelMaterialType SMOOTH_SANDSTONE_SLAB = register("minecraft", "smooth_sandstone_slab");
    public static final VoxelMaterialType SMOOTH_QUARTZ_SLAB = register("minecraft", "smooth_quartz_slab");
    public static final VoxelMaterialType GRANITE_SLAB = register("minecraft", "granite_slab");
    public static final VoxelMaterialType ANDESITE_SLAB = register("minecraft", "andesite_slab");
    public static final VoxelMaterialType RED_NETHER_BRICK_SLAB = register("minecraft", "red_nether_brick_slab");
    public static final VoxelMaterialType POLISHED_ANDESITE_SLAB = register("minecraft", "polished_andesite_slab");
    public static final VoxelMaterialType DIORITE_SLAB = register("minecraft", "diorite_slab");
    public static final VoxelMaterialType BRICK_WALL = register("minecraft", "brick_wall");
    public static final VoxelMaterialType PRISMARINE_WALL = register("minecraft", "prismarine_wall");
    public static final VoxelMaterialType RED_SANDSTONE_WALL = register("minecraft", "red_sandstone_wall");
    public static final VoxelMaterialType MOSSY_STONE_BRICK_WALL = register("minecraft", "mossy_stone_brick_wall");
    public static final VoxelMaterialType GRANITE_WALL = register("minecraft", "granite_wall");
    public static final VoxelMaterialType STONE_BRICK_WALL = register("minecraft", "stone_brick_wall");
    public static final VoxelMaterialType NETHER_BRICK_WALL = register("minecraft", "nether_brick_wall");
    public static final VoxelMaterialType ANDESITE_WALL = register("minecraft", "andesite_wall");
    public static final VoxelMaterialType RED_NETHER_BRICK_WALL = register("minecraft", "red_nether_brick_wall");
    public static final VoxelMaterialType SANDSTONE_WALL = register("minecraft", "sandstone_wall");
    public static final VoxelMaterialType END_STONE_BRICK_WALL = register("minecraft", "end_stone_brick_wall");
    public static final VoxelMaterialType DIORITE_WALL = register("minecraft", "diorite_wall");
    public static final VoxelMaterialType SCAFFOLDING = register("minecraft", "scaffolding");
    public static final VoxelMaterialType LOOM = register("minecraft", "loom");
    public static final VoxelMaterialType BARREL = register("minecraft", "barrel");
    public static final VoxelMaterialType SMOKER = register("minecraft", "smoker");
    public static final VoxelMaterialType BLAST_FURNACE = register("minecraft", "blast_furnace");
    public static final VoxelMaterialType CARTOGRAPHY_TABLE = register("minecraft", "cartography_table");
    public static final VoxelMaterialType FLETCHING_TABLE = register("minecraft", "fletching_table");
    public static final VoxelMaterialType GRINDSTONE = register("minecraft", "grindstone");
    public static final VoxelMaterialType LECTERN = register("minecraft", "lectern");
    public static final VoxelMaterialType SMITHING_TABLE = register("minecraft", "smithing_table");
    public static final VoxelMaterialType STONECUTTER = register("minecraft", "stonecutter");
    public static final VoxelMaterialType BELL = register("minecraft", "bell");
    public static final VoxelMaterialType LANTERN = register("minecraft", "lantern");
    public static final VoxelMaterialType SOUL_LANTERN = register("minecraft", "soul_lantern");
    public static final VoxelMaterialType CAMPFIRE = register("minecraft", "campfire");
    public static final VoxelMaterialType SOUL_CAMPFIRE = register("minecraft", "soul_campfire");
    public static final VoxelMaterialType SWEET_BERRY_BUSH = register("minecraft", "sweet_berry_bush", Tags.FALLSOFF);
    public static final VoxelMaterialType WARPED_STEM = register("minecraft", "warped_stem");
    public static final VoxelMaterialType STRIPPED_WARPED_STEM = register("minecraft", "stripped_warped_stem");
    public static final VoxelMaterialType WARPED_HYPHAE = register("minecraft", "warped_hyphae");
    public static final VoxelMaterialType STRIPPED_WARPED_HYPHAE = register("minecraft", "stripped_warped_hyphae");
    public static final VoxelMaterialType WARPED_NYLIUM = register("minecraft", "warped_nylium");
    public static final VoxelMaterialType WARPED_FUNGUS = register("minecraft", "warped_fungus", Tags.FALLSOFF);
    public static final VoxelMaterialType WARPED_WART_BLOCK = register("minecraft", "warped_wart_block");
    public static final VoxelMaterialType WARPED_ROOTS = register("minecraft", "warped_roots", Tags.FALLSOFF);
    public static final VoxelMaterialType NETHER_SPROUTS = register("minecraft", "nether_sprouts", Tags.FALLSOFF);
    public static final VoxelMaterialType CRIMSON_STEM = register("minecraft", "crimson_stem");
    public static final VoxelMaterialType STRIPPED_CRIMSON_STEM = register("minecraft", "stripped_crimson_stem");
    public static final VoxelMaterialType CRIMSON_HYPHAE = register("minecraft", "crimson_hyphae");
    public static final VoxelMaterialType STRIPPED_CRIMSON_HYPHAE = register("minecraft", "stripped_crimson_hyphae");
    public static final VoxelMaterialType CRIMSON_NYLIUM = register("minecraft", "crimson_nylium");
    public static final VoxelMaterialType CRIMSON_FUNGUS = register("minecraft", "crimson_fungus", Tags.FALLSOFF);
    public static final VoxelMaterialType SHROOMLIGHT = register("minecraft", "shroomlight");
    public static final VoxelMaterialType WEEPING_VINES = register("minecraft", "weeping_vines", Tags.FALLSOFF);
    public static final VoxelMaterialType WEEPING_VINES_PLANT = register("minecraft", "weeping_vines_plant", Tags.FALLSOFF);
    public static final VoxelMaterialType TWISTING_VINES = register("minecraft", "twisting_vines", Tags.FALLSOFF);
    public static final VoxelMaterialType TWISTING_VINES_PLANT = register("minecraft", "twisting_vines_plant", Tags.FALLSOFF);
    public static final VoxelMaterialType CRIMSON_ROOTS = register("minecraft", "crimson_roots", Tags.FALLSOFF);
    public static final VoxelMaterialType CRIMSON_PLANKS = register("minecraft", "crimson_planks");
    public static final VoxelMaterialType WARPED_PLANKS = register("minecraft", "warped_planks");
    public static final VoxelMaterialType CRIMSON_SLAB = register("minecraft", "crimson_slab");
    public static final VoxelMaterialType WARPED_SLAB = register("minecraft", "warped_slab");
    public static final VoxelMaterialType CRIMSON_PRESSURE_PLATE = register("minecraft", "crimson_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterialType WARPED_PRESSURE_PLATE = register("minecraft", "warped_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterialType CRIMSON_FENCE = register("minecraft", "crimson_fence");
    public static final VoxelMaterialType WARPED_FENCE = register("minecraft", "warped_fence");
    public static final VoxelMaterialType CRIMSON_TRAPDOOR = register("minecraft", "crimson_trapdoor");
    public static final VoxelMaterialType WARPED_TRAPDOOR = register("minecraft", "warped_trapdoor");
    public static final VoxelMaterialType CRIMSON_FENCE_GATE = register("minecraft", "crimson_fence_gate");
    public static final VoxelMaterialType WARPED_FENCE_GATE = register("minecraft", "warped_fence_gate");
    public static final VoxelMaterialType CRIMSON_STAIRS = register("minecraft", "crimson_stairs");
    public static final VoxelMaterialType WARPED_STAIRS = register("minecraft", "warped_stairs");
    public static final VoxelMaterialType CRIMSON_BUTTON = register("minecraft", "crimson_button", Tags.FALLSOFF);
    public static final VoxelMaterialType WARPED_BUTTON = register("minecraft", "warped_button", Tags.FALLSOFF);
    public static final VoxelMaterialType CRIMSON_DOOR = register("minecraft", "crimson_door", Tags.FALLSOFF);
    public static final VoxelMaterialType WARPED_DOOR = register("minecraft", "warped_door", Tags.FALLSOFF);
    public static final VoxelMaterialType CRIMSON_SIGN = register("minecraft", "crimson_sign", Tags.FALLSOFF);
    public static final VoxelMaterialType WARPED_SIGN = register("minecraft", "warped_sign", Tags.FALLSOFF);
    public static final VoxelMaterialType CRIMSON_WALL_SIGN = register("minecraft", "crimson_wall_sign", Tags.FALLSOFF);
    public static final VoxelMaterialType WARPED_WALL_SIGN = register("minecraft", "warped_wall_sign", Tags.FALLSOFF);
    public static final VoxelMaterialType STRUCTURE_BLOCK = register("minecraft", "structure_block");
    public static final VoxelMaterialType JIGSAW = register("minecraft", "jigsaw");
    public static final VoxelMaterialType COMPOSTER = register("minecraft", "composter");
    public static final VoxelMaterialType TARGET = register("minecraft", "target");
    public static final VoxelMaterialType BEE_NEST = register("minecraft", "bee_nest");
    public static final VoxelMaterialType BEEHIVE = register("minecraft", "beehive");
    public static final VoxelMaterialType HONEY_BLOCK = register("minecraft", "honey_block");
    public static final VoxelMaterialType HONEYCOMB_BLOCK = register("minecraft", "honeycomb_block");
    public static final VoxelMaterialType NETHERITE_BLOCK = register("minecraft", "netherite_block");
    public static final VoxelMaterialType ANCIENT_DEBRIS = register("minecraft", "ancient_debris");
    public static final VoxelMaterialType CRYING_OBSIDIAN = register("minecraft", "crying_obsidian");
    public static final VoxelMaterialType RESPAWN_ANCHOR = register("minecraft", "respawn_anchor");
    public static final VoxelMaterialType POTTED_CRIMSON_FUNGUS = register("minecraft", "potted_crimson_fungus");
    public static final VoxelMaterialType POTTED_WARPED_FUNGUS = register("minecraft", "potted_warped_fungus");
    public static final VoxelMaterialType POTTED_CRIMSON_ROOTS = register("minecraft", "potted_crimson_roots");
    public static final VoxelMaterialType POTTED_WARPED_ROOTS = register("minecraft", "potted_warped_roots");
    public static final VoxelMaterialType LODESTONE = register("minecraft", "lodestone");
    public static final VoxelMaterialType BLACKSTONE = register("minecraft", "blackstone");
    public static final VoxelMaterialType BLACKSTONE_STAIRS = register("minecraft", "blackstone_stairs");
    public static final VoxelMaterialType BLACKSTONE_WALL = register("minecraft", "blackstone_wall");
    public static final VoxelMaterialType BLACKSTONE_SLAB = register("minecraft", "blackstone_slab");
    public static final VoxelMaterialType POLISHED_BLACKSTONE = register("minecraft", "polished_blackstone");
    public static final VoxelMaterialType POLISHED_BLACKSTONE_BRICKS = register("minecraft", "polished_blackstone_bricks");
    public static final VoxelMaterialType CRACKED_POLISHED_BLACKSTONE_BRICKS = register("minecraft", "cracked_polished_blackstone_bricks");
    public static final VoxelMaterialType CHISELED_POLISHED_BLACKSTONE = register("minecraft", "chiseled_polished_blackstone");
    public static final VoxelMaterialType POLISHED_BLACKSTONE_BRICK_SLAB = register("minecraft", "polished_blackstone_brick_slab");
    public static final VoxelMaterialType POLISHED_BLACKSTONE_BRICK_STAIRS = register("minecraft", "polished_blackstone_brick_stairs");
    public static final VoxelMaterialType POLISHED_BLACKSTONE_BRICK_WALL = register("minecraft", "polished_blackstone_brick_wall");
    public static final VoxelMaterialType GILDED_BLACKSTONE = register("minecraft", "gilded_blackstone");
    public static final VoxelMaterialType POLISHED_BLACKSTONE_STAIRS = register("minecraft", "polished_blackstone_stairs");
    public static final VoxelMaterialType POLISHED_BLACKSTONE_SLAB = register("minecraft", "polished_blackstone_slab");
    public static final VoxelMaterialType POLISHED_BLACKSTONE_PRESSURE_PLATE = register("minecraft", "polished_blackstone_pressure_plate");
    public static final VoxelMaterialType POLISHED_BLACKSTONE_BUTTON = register("minecraft", "polished_blackstone_button");
    public static final VoxelMaterialType POLISHED_BLACKSTONE_WALL = register("minecraft", "polished_blackstone_wall");
    public static final VoxelMaterialType CHISELED_NETHER_BRICKS = register("minecraft", "chiseled_nether_bricks");
    public static final VoxelMaterialType CRACKED_NETHER_BRICKS = register("minecraft", "cracked_nether_bricks");
    public static final VoxelMaterialType QUARTZ_BRICKS = register("minecraft", "quartz_bricks");
    public static final VoxelMaterialType CANDLE = register("minecraft", "candle", V1_17);
    public static final VoxelMaterialType WHITE_CANDLE = register("minecraft", "white_candle", V1_17);
    public static final VoxelMaterialType ORANGE_CANDLE = register("minecraft", "orange_candle", V1_17);
    public static final VoxelMaterialType MAGENTA_CANDLE = register("minecraft", "magenta_candle", V1_17);
    public static final VoxelMaterialType LIGHT_BLUE_CANDLE = register("minecraft", "light_blue_candle", V1_17);
    public static final VoxelMaterialType YELLOW_CANDLE = register("minecraft", "yellow_candle", V1_17);
    public static final VoxelMaterialType LIME_CANDLE = register("minecraft", "lime_candle", V1_17);
    public static final VoxelMaterialType PINK_CANDLE = register("minecraft", "pink_candle", V1_17);
    public static final VoxelMaterialType GRAY_CANDLE = register("minecraft", "gray_candle", V1_17);
    public static final VoxelMaterialType LIGHT_GRAY_CANDLE = register("minecraft", "light_gray_candle", V1_17);
    public static final VoxelMaterialType CYAN_CANDLE = register("minecraft", "cyan_candle", V1_17);
    public static final VoxelMaterialType PURPLE_CANDLE = register("minecraft", "purple_candle", V1_17);
    public static final VoxelMaterialType BLUE_CANDLE = register("minecraft", "blue_candle", V1_17);
    public static final VoxelMaterialType BROWN_CANDLE = register("minecraft", "brown_candle", V1_17);
    public static final VoxelMaterialType GREEN_CANDLE = register("minecraft", "green_candle", V1_17);
    public static final VoxelMaterialType RED_CANDLE = register("minecraft", "red_candle", V1_17);
    public static final VoxelMaterialType BLACK_CANDLE = register("minecraft", "black_candle", V1_17);
    public static final VoxelMaterialType CANDLE_CAKE = register("minecraft", "candle_cake", V1_17);
    public static final VoxelMaterialType WHITE_CANDLE_CAKE = register("minecraft", "white_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType ORANGE_CANDLE_CAKE = register("minecraft", "orange_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType MAGENTA_CANDLE_CAKE = register("minecraft", "magenta_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType LIGHT_BLUE_CANDLE_CAKE = register("minecraft", "light_blue_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType YELLOW_CANDLE_CAKE = register("minecraft", "yellow_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType LIME_CANDLE_CAKE = register("minecraft", "lime_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType PINK_CANDLE_CAKE = register("minecraft", "pink_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType GRAY_CANDLE_CAKE = register("minecraft", "gray_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType LIGHT_GRAY_CANDLE_CAKE = register("minecraft", "light_gray_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType CYAN_CANDLE_CAKE = register("minecraft", "cyan_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType PURPLE_CANDLE_CAKE = register("minecraft", "purple_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType BLUE_CANDLE_CAKE = register("minecraft", "blue_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType BROWN_CANDLE_CAKE = register("minecraft", "brown_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType GREEN_CANDLE_CAKE = register("minecraft", "green_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType RED_CANDLE_CAKE = register("minecraft", "red_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType BLACK_CANDLE_CAKE = register("minecraft", "black_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType AMETHYST_BLOCK = register("minecraft", "amethyst_block", V1_17);
    public static final VoxelMaterialType BUDDING_AMETHYST = register("minecraft", "budding_amethyst", V1_17);
    public static final VoxelMaterialType AMETHYST_CLUSTER = register("minecraft", "amethyst_cluster", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType LARGE_AMETHYST_BUD = register("minecraft", "large_amethyst_bud", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType MEDIUM_AMETHYST_BUD = register("minecraft", "medium_amethyst_bud", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType SMALL_AMETHYST_BUD = register("minecraft", "small_amethyst_bud", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType TUFF = register("minecraft", "tuff", V1_17);
    public static final VoxelMaterialType CALCITE = register("minecraft", "calcite", V1_17);
    public static final VoxelMaterialType TINTED_GLASS = register("minecraft", "tinted_glass", V1_17);
    public static final VoxelMaterialType POWDER_SNOW = register("minecraft", "powder_snow", V1_17);
    public static final VoxelMaterialType SCULK_SENSOR = register("minecraft", "sculk_sensor", V1_17);
    public static final VoxelMaterialType OXIDIZED_COPPER = register("minecraft", "oxidized_copper", V1_17);
    public static final VoxelMaterialType WEATHERED_COPPER = register("minecraft", "weathered_copper", V1_17);
    public static final VoxelMaterialType EXPOSED_COPPER = register("minecraft", "exposed_copper", V1_17);
    public static final VoxelMaterialType COPPER_BLOCK = register("minecraft", "copper_block", V1_17);
    public static final VoxelMaterialType COPPER_ORE = register("minecraft", "copper_ore", V1_17);
    public static final VoxelMaterialType DEEPSLATE_COPPER_ORE = register("minecraft", "deepslate_copper_ore", V1_17);
    public static final VoxelMaterialType OXIDIZED_CUT_COPPER = register("minecraft", "oxidized_cut_copper", V1_17);
    public static final VoxelMaterialType WEATHERED_CUT_COPPER = register("minecraft", "weathered_cut_copper", V1_17);
    public static final VoxelMaterialType EXPOSED_CUT_COPPER = register("minecraft", "exposed_cut_copper", V1_17);
    public static final VoxelMaterialType CUT_COPPER = register("minecraft", "cut_copper", V1_17);
    public static final VoxelMaterialType OXIDIZED_CUT_COPPER_STAIRS = register("minecraft", "oxidized_cut_copper_stairs", V1_17);
    public static final VoxelMaterialType WEATHERED_CUT_COPPER_STAIRS = register("minecraft", "weathered_cut_copper_stairs", V1_17);
    public static final VoxelMaterialType EXPOSED_CUT_COPPER_STAIRS = register("minecraft", "exposed_cut_copper_stairs", V1_17);
    public static final VoxelMaterialType CUT_COPPER_STAIRS = register("minecraft", "cut_copper_stairs", V1_17);
    public static final VoxelMaterialType OXIDIZED_CUT_COPPER_SLAB = register("minecraft", "oxidized_cut_copper_slab", V1_17);
    public static final VoxelMaterialType WEATHERED_CUT_COPPER_SLAB = register("minecraft", "weathered_cut_copper_slab", V1_17);
    public static final VoxelMaterialType EXPOSED_CUT_COPPER_SLAB = register("minecraft", "exposed_cut_copper_slab", V1_17);
    public static final VoxelMaterialType CUT_COPPER_SLAB = register("minecraft", "cut_copper_slab", V1_17);
    public static final VoxelMaterialType WAXED_COPPER_BLOCK = register("minecraft", "waxed_copper_block", V1_17);
    public static final VoxelMaterialType WAXED_WEATHERED_COPPER = register("minecraft", "waxed_weathered_copper", V1_17);
    public static final VoxelMaterialType WAXED_EXPOSED_COPPER = register("minecraft", "waxed_exposed_copper", V1_17);
    public static final VoxelMaterialType WAXED_OXIDIZED_COPPER = register("minecraft", "waxed_oxidized_copper", V1_17);
    public static final VoxelMaterialType WAXED_OXIDIZED_CUT_COPPER = register("minecraft", "waxed_oxidized_cut_copper", V1_17);
    public static final VoxelMaterialType WAXED_WEATHERED_CUT_COPPER = register("minecraft", "waxed_weathered_cut_copper", V1_17);
    public static final VoxelMaterialType WAXED_EXPOSED_CUT_COPPER = register("minecraft", "waxed_exposed_cut_copper", V1_17);
    public static final VoxelMaterialType WAXED_CUT_COPPER = register("minecraft", "waxed_cut_copper", V1_17);
    public static final VoxelMaterialType WAXED_OXIDIZED_CUT_COPPER_STAIRS = register("minecraft", "waxed_oxidized_cut_copper_stairs", V1_17);
    public static final VoxelMaterialType WAXED_WEATHERED_CUT_COPPER_STAIRS = register("minecraft", "waxed_weathered_cut_copper_stairs", V1_17);
    public static final VoxelMaterialType WAXED_EXPOSED_CUT_COPPER_STAIRS = register("minecraft", "waxed_exposed_cut_copper_stairs", V1_17);
    public static final VoxelMaterialType WAXED_CUT_COPPER_STAIRS = register("minecraft", "waxed_cut_copper_stairs", V1_17);
    public static final VoxelMaterialType WAXED_OXIDIZED_CUT_COPPER_SLAB = register("minecraft", "waxed_oxidized_cut_copper_slab", V1_17);
    public static final VoxelMaterialType WAXED_WEATHERED_CUT_COPPER_SLAB = register("minecraft", "waxed_weathered_cut_copper_slab", V1_17);
    public static final VoxelMaterialType WAXED_EXPOSED_CUT_COPPER_SLAB = register("minecraft", "waxed_exposed_cut_copper_slab", V1_17);
    public static final VoxelMaterialType WAXED_CUT_COPPER_SLAB = register("minecraft", "waxed_cut_copper_slab", V1_17);
    public static final VoxelMaterialType LIGHTNING_ROD = register("minecraft", "lightning_rod", V1_17);
    public static final VoxelMaterialType POINTED_DRIPSTONE = register("minecraft", "pointed_dripstone", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType DRIPSTONE_BLOCK = register("minecraft", "dripstone_block", V1_17);
    public static final VoxelMaterialType CAVE_VINES = register("minecraft", "cave_vines", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType CAVE_VINES_PLANT = register("minecraft", "cave_vines_plant", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType SPORE_BLOSSOM = register("minecraft", "spore_blossom", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType AZALEA = register("minecraft", "azalea", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType FLOWERING_AZALEA = register("minecraft", "flowering_azalea", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType MOSS_CARPET = register("minecraft", "moss_carpet", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType MOSS_BLOCK = register("minecraft", "moss_block", V1_17);
    public static final VoxelMaterialType BIG_DRIPLEAF = register("minecraft", "big_dripleaf", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType BIG_DRIPLEAF_STEM = register("minecraft", "big_dripleaf_stem", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType SMALL_DRIPLEAF = register("minecraft", "small_dripleaf", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType HANGING_ROOTS = register("minecraft", "hanging_roots", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterialType ROOTED_DIRT = register("minecraft", "rooted_dirt", V1_17);
    public static final VoxelMaterialType DEEPSLATE = register("minecraft", "deepslate", V1_17);
    public static final VoxelMaterialType COBBLED_DEEPSLATE = register("minecraft", "cobbled_deepslate", V1_17);
    public static final VoxelMaterialType COBBLED_DEEPSLATE_STAIRS = register("minecraft", "cobbled_deepslate_stairs", V1_17);
    public static final VoxelMaterialType COBBLED_DEEPSLATE_SLAB = register("minecraft", "cobbled_deepslate_slab", V1_17);
    public static final VoxelMaterialType COBBLED_DEEPSLATE_WALL = register("minecraft", "cobbled_deepslate_wall", V1_17);
    public static final VoxelMaterialType POLISHED_DEEPSLATE = register("minecraft", "polished_deepslate", V1_17);
    public static final VoxelMaterialType POLISHED_DEEPSLATE_STAIRS = register("minecraft", "polished_deepslate_stairs", V1_17);
    public static final VoxelMaterialType POLISHED_DEEPSLATE_SLAB = register("minecraft", "polished_deepslate_slab", V1_17);
    public static final VoxelMaterialType POLISHED_DEEPSLATE_WALL = register("minecraft", "polished_deepslate_wall", V1_17);
    public static final VoxelMaterialType DEEPSLATE_TILES = register("minecraft", "deepslate_tiles", V1_17);
    public static final VoxelMaterialType DEEPSLATE_TILE_STAIRS = register("minecraft", "deepslate_tile_stairs", V1_17);
    public static final VoxelMaterialType DEEPSLATE_TILE_SLAB = register("minecraft", "deepslate_tile_slab", V1_17);
    public static final VoxelMaterialType DEEPSLATE_TILE_WALL = register("minecraft", "deepslate_tile_wall", V1_17);
    public static final VoxelMaterialType DEEPSLATE_BRICKS = register("minecraft", "deepslate_bricks", V1_17);
    public static final VoxelMaterialType DEEPSLATE_BRICK_STAIRS = register("minecraft", "deepslate_brick_stairs", V1_17);
    public static final VoxelMaterialType DEEPSLATE_BRICK_SLAB = register("minecraft", "deepslate_brick_slab", V1_17);
    public static final VoxelMaterialType DEEPSLATE_BRICK_WALL = register("minecraft", "deepslate_brick_wall", V1_17);
    public static final VoxelMaterialType CHISELED_DEEPSLATE = register("minecraft", "chiseled_deepslate", V1_17);
    public static final VoxelMaterialType CRACKED_DEEPSLATE_BRICKS = register("minecraft", "cracked_deepslate_bricks", V1_17);
    public static final VoxelMaterialType CRACKED_DEEPSLATE_TILES = register("minecraft", "cracked_deepslate_tiles", V1_17);
    public static final VoxelMaterialType INFESTED_DEEPSLATE = register("minecraft", "infested_deepslate", V1_17);
    public static final VoxelMaterialType SMOOTH_BASALT = register("minecraft", "smooth_basalt", V1_17);
    public static final VoxelMaterialType RAW_IRON_BLOCK = register("minecraft", "raw_iron_block", V1_17);
    public static final VoxelMaterialType RAW_COPPER_BLOCK = register("minecraft", "raw_copper_block", V1_17);
    public static final VoxelMaterialType RAW_GOLD_BLOCK = register("minecraft", "raw_gold_block", V1_17);
    public static final VoxelMaterialType POTTED_AZALEA = register("minecraft", "potted_azalea_bush", V1_17);
    public static final VoxelMaterialType POTTED_FLOWERING_AZALEA = register("minecraft", "potted_flowering_azalea_bush", V1_17);
    public static final VoxelMaterialType FROGSPAWN = register("minecraft", "frogspawn", V1_19, Tags.FALLSOFF);
    public static final VoxelMaterialType MANGROVE_BUTTON = register("minecraft", "mangrove_button", V1_19, Tags.FALLSOFF);
    public static final VoxelMaterialType MANGROVE_DOOR = register("minecraft", "mangrove_door", V1_19, Tags.FALLSOFF);
    public static final VoxelMaterialType MANGROVE_FENCE = register("minecraft", "mangrove_fence", V1_19);
    public static final VoxelMaterialType MANGROVE_FENCE_GATE = register("minecraft", "mangrove_fence_gate", V1_19);
    public static final VoxelMaterialType MANGROVE_LEAVES = register("minecraft", "mangrove_leaves", V1_19);
    public static final VoxelMaterialType MANGROVE_LOG = register("minecraft", "mangrove_log", V1_19);
    public static final VoxelMaterialType MANGROVE_PLANKS = register("minecraft", "mangrove_planks", V1_19);
    public static final VoxelMaterialType MANGROVE_PRESSURE_PLATE = register("minecraft", "mangrove_pressure_plate", V1_19, Tags.FALLSOFF);
    public static final VoxelMaterialType MANGROVE_PROPAGULE = register("minecraft", "mangrove_propagule", V1_19, Tags.FALLSOFF);
    public static final VoxelMaterialType MANGROVE_ROOTS = register("minecraft", "mangrove_roots", V1_19);
    public static final VoxelMaterialType MANGROVE_SIGN = register("minecraft", "mangrove_sign", V1_19, Tags.FALLSOFF, Tags.FALLSOFF);
    public static final VoxelMaterialType MANGROVE_SLAB = register("minecraft", "mangrove_slab", V1_19);
    public static final VoxelMaterialType MANGROVE_STAIRS = register("minecraft", "mangrove_stairs", V1_19);
    public static final VoxelMaterialType MANGROVE_TRAPDOOR = register("minecraft", "mangrove_trapdoor", V1_19);
    public static final VoxelMaterialType MANGROVE_WALL_SIGN = register("minecraft", "mangrove_wall_sign", V1_19, Tags.FALLSOFF);
    public static final VoxelMaterialType MANGROVE_WOOD = register("minecraft", "mangrove_wood", V1_19);
    public static final VoxelMaterialType MUD = register("minecraft", "mud", V1_19);
    public static final VoxelMaterialType MUD_BRICK_SLAB = register("minecraft", "mud_brick_slab", V1_19);
    public static final VoxelMaterialType MUD_BRICK_STAIRS = register("minecraft", "mud_brick_stairs", V1_19);
    public static final VoxelMaterialType MUD_BRICK_WALL = register("minecraft", "mud_brick_wall", V1_19);
    public static final VoxelMaterialType MUD_BRICKS = register("minecraft", "mud_bricks", V1_19);
    public static final VoxelMaterialType MUDDY_MANGROVE_ROOTS = register("minecraft", "muddy_mangrove_roots", V1_19);
    public static final VoxelMaterialType OCHRE_FROGLIGHT = register("minecraft", "ochre_froglight", V1_19);
    public static final VoxelMaterialType PACKED_MUD = register("minecraft", "packed_mud", V1_19);
    public static final VoxelMaterialType PEARLESCENT_FROGLIGHT = register("minecraft", "pearlescent_froglight", V1_19);
    public static final VoxelMaterialType POTTED_MANGROVE_PROPAGULE = register("minecraft", "potted_mangrove_propagule", V1_19);
    public static final VoxelMaterialType REINFORCED_DEEPSLATE = register("minecraft", "reinforced_deepslate", V1_19);
    public static final VoxelMaterialType SCULK = register("minecraft", "sculk", V1_19);
    public static final VoxelMaterialType SCULK_CATALYST = register("minecraft", "sculk_catalyst", V1_19);
    public static final VoxelMaterialType SCULK_SHRIEKER = register("minecraft", "sculk_shrieker", V1_19);
    public static final VoxelMaterialType SCULK_VEIN = register("minecraft", "sculk_vein", V1_19, Tags.FALLSOFF);
    public static final VoxelMaterialType STRIPPED_MANGROVE_LOG = register("minecraft", "stripped_mangrove_log", V1_19);
    public static final VoxelMaterialType STRIPPED_MANGROVE_WOOD = register("minecraft", "stripped_mangrove_wood", V1_19);
    public static final VoxelMaterialType VERDANT_FROGLIGHT = register("minecraft", "verdant_froglight", V1_19);
    //</editor-fold>
    private final Version version;
    private final String key;
    private final String namespace;
    private final List<Tags> tags;
    private IMaterial material = null;

    public VoxelMaterialType(String namespace, String key, Version version, Tags... tags) {
        this.namespace = namespace;
        this.key = key;
        this.version = version;
        this.tags = Arrays.stream(tags).toList();
    }

    public VoxelMaterialType(String namespace, String key) {
        this(namespace, key, V1_16);
    }

    public VoxelMaterialType(String key) {
        this("minecraft", key);
    }

    private static VoxelMaterialType register(String namespace, String key, Version version, Tags... tags) {

        var material = new VoxelMaterialType(namespace, key, version, tags);
        var mapKey = namespace + ":" + key;
        BLOCKS.put(mapKey, material);
        return material;
    }

    private static VoxelMaterialType register(String namespace, String key, Tags... tags) {
        return register(namespace, key, V1_16, tags);
    }

    public static VoxelMaterialType getMaterial(String key) {
        if (key.contains(":")) {
            String[] components = key.split(":");
            return getMaterial(components[0], components[1]);
        }
        return getMaterial("minecraft", key);
    }

    public static VoxelMaterialType getMaterial(String namespace, String key) {
        if (namespace.isEmpty() || key.isEmpty()) return null;
        var block = BLOCKS.get(namespace + ":" + key);
        if (block == null) {
            block = new VoxelMaterialType(namespace, key);
            if (block.getIMaterial() != null) {  // block exists
                String mapKey = namespace + ":" + key;
                BLOCKS.put(mapKey, block);
                return block;
            } else {
                return null;
            }
        }
        if (VoxelSniper.voxelsniper.getVersion().isSupported(block.getVersion())) {
            return block;
        }
        return null;
    }

    public static Collection<VoxelMaterialType> getMaterials() {
        return BLOCKS.values().stream().filter((m) -> VoxelSniper.voxelsniper.getVersion().isSupported(m.getVersion())).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoxelMaterialType that = (VoxelMaterialType) o;
        return version == that.version && key.equals(that.key) && namespace.equals(that.namespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, key, namespace);
    }

    @Override
    public String toString() {
        return namespace + ":" + key;
    }

    public boolean isBlock() {
        return this.getIMaterial().isBlock();
    }

    @Override
    public boolean hasGravity() {
        return this.getIMaterial().hasGravity();
    }

    @Override
    public IBlockData createBlockData(String s) {
        return this.getIMaterial().createBlockData(s);
    }

    @Override
    public IBlockData createBlockData() {
        return this.getIMaterial().createBlockData();
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public boolean isSolid() {
        return this.getIMaterial().isSolid();
    }

    public boolean fallsOff() {
        return this.tags.contains(Tags.FALLSOFF);
    }

    public boolean isFluid() {
        return this.tags.contains(Tags.FLUIDS);
    }

    public boolean isAir() {
        return this.tags.contains(Tags.AIR);
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(String key) {
        return this.getIMaterial().equals(key);
    }

    @Override
    public String getName() {
        return this.getKey();
    }

    @Override
    public boolean equals(VoxelMaterialType material) {
        return this.getIMaterial().equals(material);
    }

    @Override
    public boolean isTransparent() {
        return this.getIMaterial().isTransparent();
    }

    public String getNamespace() {
        return namespace;
    }

    public IMaterial getIMaterial() {
        if (material == null) {
            this.material = VoxelSniper.voxelsniper.getMaterial(this);
        }
        return material;
    }

    private enum Tags {
        FALLSOFF,
        FLUIDS,
        AIR
    }
}
