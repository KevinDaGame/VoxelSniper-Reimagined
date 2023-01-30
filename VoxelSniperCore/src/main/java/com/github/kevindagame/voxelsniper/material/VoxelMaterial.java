package com.github.kevindagame.voxelsniper.material;

import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.voxelsniper.Version;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;

import java.util.*;

import static com.github.kevindagame.voxelsniper.Version.*;

@SuppressWarnings("unused")
public class VoxelMaterial implements IMaterial {
    public static final Map<String, VoxelMaterial> BLOCKS = new HashMap<>();
    //<editor-fold defaultstate="collapsed" desc="Blocks">
    public static final VoxelMaterial AIR = register("minecraft", "air", Tags.AIR);
    public static final VoxelMaterial STONE = register("minecraft", "stone");
    public static final VoxelMaterial GRANITE = register("minecraft", "granite");
    public static final VoxelMaterial POLISHED_GRANITE = register("minecraft", "polished_granite");
    public static final VoxelMaterial DIORITE = register("minecraft", "diorite");
    public static final VoxelMaterial POLISHED_DIORITE = register("minecraft", "polished_diorite");
    public static final VoxelMaterial ANDESITE = register("minecraft", "andesite");
    public static final VoxelMaterial POLISHED_ANDESITE = register("minecraft", "polished_andesite");
    public static final VoxelMaterial GRASS_BLOCK = register("minecraft", "grass_block");
    public static final VoxelMaterial DIRT = register("minecraft", "dirt");
    public static final VoxelMaterial COARSE_DIRT = register("minecraft", "coarse_dirt");
    public static final VoxelMaterial PODZOL = register("minecraft", "podzol");
    public static final VoxelMaterial COBBLESTONE = register("minecraft", "cobblestone");
    public static final VoxelMaterial OAK_PLANKS = register("minecraft", "oak_planks");
    public static final VoxelMaterial SPRUCE_PLANKS = register("minecraft", "spruce_planks");
    public static final VoxelMaterial BIRCH_PLANKS = register("minecraft", "birch_planks");
    public static final VoxelMaterial JUNGLE_PLANKS = register("minecraft", "jungle_planks");
    public static final VoxelMaterial ACACIA_PLANKS = register("minecraft", "acacia_planks");
    public static final VoxelMaterial DARK_OAK_PLANKS = register("minecraft", "dark_oak_planks");
    public static final VoxelMaterial OAK_SAPLING = register("minecraft", "oak_sapling", Tags.FALLSOFF);
    public static final VoxelMaterial SPRUCE_SAPLING = register("minecraft", "spruce_sapling", Tags.FALLSOFF);
    public static final VoxelMaterial BIRCH_SAPLING = register("minecraft", "birch_sapling", Tags.FALLSOFF);
    public static final VoxelMaterial JUNGLE_SAPLING = register("minecraft", "jungle_sapling", Tags.FALLSOFF);
    public static final VoxelMaterial ACACIA_SAPLING = register("minecraft", "acacia_sapling", Tags.FALLSOFF);
    public static final VoxelMaterial DARK_OAK_SAPLING = register("minecraft", "dark_oak_sapling", Tags.FALLSOFF);
    public static final VoxelMaterial BEDROCK = register("minecraft", "bedrock");
    public static final VoxelMaterial WATER = register("minecraft", "water", Tags.FLUIDS);
    public static final VoxelMaterial LAVA = register("minecraft", "lava", Tags.FLUIDS);
    public static final VoxelMaterial SAND = register("minecraft", "sand");
    public static final VoxelMaterial RED_SAND = register("minecraft", "red_sand");
    public static final VoxelMaterial GRAVEL = register("minecraft", "gravel");
    public static final VoxelMaterial GOLD_ORE = register("minecraft", "gold_ore");
    public static final VoxelMaterial DEEPSLATE_GOLD_ORE = register("minecraft", "deepslate_gold_ore", V1_17);
    public static final VoxelMaterial IRON_ORE = register("minecraft", "iron_ore");
    public static final VoxelMaterial DEEPSLATE_IRON_ORE = register("minecraft", "deepslate_iron_ore", V1_17);
    public static final VoxelMaterial COAL_ORE = register("minecraft", "coal_ore");
    public static final VoxelMaterial DEEPSLATE_COAL_ORE = register("minecraft", "deepslate_coal_ore", V1_17);
    public static final VoxelMaterial NETHER_GOLD_ORE = register("minecraft", "nether_gold_ore");
    public static final VoxelMaterial OAK_LOG = register("minecraft", "oak_log");
    public static final VoxelMaterial SPRUCE_LOG = register("minecraft", "spruce_log");
    public static final VoxelMaterial BIRCH_LOG = register("minecraft", "birch_log");
    public static final VoxelMaterial JUNGLE_LOG = register("minecraft", "jungle_log");
    public static final VoxelMaterial ACACIA_LOG = register("minecraft", "acacia_log");
    public static final VoxelMaterial DARK_OAK_LOG = register("minecraft", "dark_oak_log");
    public static final VoxelMaterial STRIPPED_SPRUCE_LOG = register("minecraft", "stripped_spruce_log");
    public static final VoxelMaterial STRIPPED_BIRCH_LOG = register("minecraft", "stripped_birch_log");
    public static final VoxelMaterial STRIPPED_JUNGLE_LOG = register("minecraft", "stripped_jungle_log");
    public static final VoxelMaterial STRIPPED_ACACIA_LOG = register("minecraft", "stripped_acacia_log");
    public static final VoxelMaterial STRIPPED_DARK_OAK_LOG = register("minecraft", "stripped_dark_oak_log");
    public static final VoxelMaterial STRIPPED_OAK_LOG = register("minecraft", "stripped_oak_log");
    public static final VoxelMaterial OAK_WOOD = register("minecraft", "oak_wood");
    public static final VoxelMaterial SPRUCE_WOOD = register("minecraft", "spruce_wood");
    public static final VoxelMaterial BIRCH_WOOD = register("minecraft", "birch_wood");
    public static final VoxelMaterial JUNGLE_WOOD = register("minecraft", "jungle_wood");
    public static final VoxelMaterial ACACIA_WOOD = register("minecraft", "acacia_wood");
    public static final VoxelMaterial DARK_OAK_WOOD = register("minecraft", "dark_oak_wood");
    public static final VoxelMaterial STRIPPED_OAK_WOOD = register("minecraft", "stripped_oak_wood");
    public static final VoxelMaterial STRIPPED_SPRUCE_WOOD = register("minecraft", "stripped_spruce_wood");
    public static final VoxelMaterial STRIPPED_BIRCH_WOOD = register("minecraft", "stripped_birch_wood");
    public static final VoxelMaterial STRIPPED_JUNGLE_WOOD = register("minecraft", "stripped_jungle_wood");
    public static final VoxelMaterial STRIPPED_ACACIA_WOOD = register("minecraft", "stripped_acacia_wood");
    public static final VoxelMaterial STRIPPED_DARK_OAK_WOOD = register("minecraft", "stripped_dark_oak_wood");
    public static final VoxelMaterial OAK_LEAVES = register("minecraft", "oak_leaves");
    public static final VoxelMaterial SPRUCE_LEAVES = register("minecraft", "spruce_leaves");
    public static final VoxelMaterial BIRCH_LEAVES = register("minecraft", "birch_leaves");
    public static final VoxelMaterial JUNGLE_LEAVES = register("minecraft", "jungle_leaves");
    public static final VoxelMaterial ACACIA_LEAVES = register("minecraft", "acacia_leaves");
    public static final VoxelMaterial DARK_OAK_LEAVES = register("minecraft", "dark_oak_leaves");
    public static final VoxelMaterial AZALEA_LEAVES = register("minecraft", "azalea_leaves", V1_17);
    public static final VoxelMaterial FLOWERING_AZALEA_LEAVES = register("minecraft", "flowering_azalea_leaves", V1_17);
    public static final VoxelMaterial SPONGE = register("minecraft", "sponge");
    public static final VoxelMaterial WET_SPONGE = register("minecraft", "wet_sponge");
    public static final VoxelMaterial GLASS = register("minecraft", "glass");
    public static final VoxelMaterial LAPIS_ORE = register("minecraft", "lapis_ore");
    public static final VoxelMaterial DEEPSLATE_LAPIS_ORE = register("minecraft", "deepslate_lapis_ore", V1_17);
    public static final VoxelMaterial LAPIS_BLOCK = register("minecraft", "lapis_block");
    public static final VoxelMaterial DISPENSER = register("minecraft", "dispenser");
    public static final VoxelMaterial SANDSTONE = register("minecraft", "sandstone");
    public static final VoxelMaterial CHISELED_SANDSTONE = register("minecraft", "chiseled_sandstone");
    public static final VoxelMaterial CUT_SANDSTONE = register("minecraft", "cut_sandstone");
    public static final VoxelMaterial NOTE_BLOCK = register("minecraft", "note_block");
    public static final VoxelMaterial WHITE_BED = register("minecraft", "white_bed", Tags.FALLSOFF);
    public static final VoxelMaterial ORANGE_BED = register("minecraft", "orange_bed", Tags.FALLSOFF);
    public static final VoxelMaterial MAGENTA_BED = register("minecraft", "magenta_bed", Tags.FALLSOFF);
    public static final VoxelMaterial LIGHT_BLUE_BED = register("minecraft", "light_blue_bed", Tags.FALLSOFF);
    public static final VoxelMaterial YELLOW_BED = register("minecraft", "yellow_bed", Tags.FALLSOFF);
    public static final VoxelMaterial LIME_BED = register("minecraft", "lime_bed", Tags.FALLSOFF);
    public static final VoxelMaterial PINK_BED = register("minecraft", "pink_bed", Tags.FALLSOFF);
    public static final VoxelMaterial GRAY_BED = register("minecraft", "gray_bed", Tags.FALLSOFF);
    public static final VoxelMaterial LIGHT_GRAY_BED = register("minecraft", "light_gray_bed", Tags.FALLSOFF);
    public static final VoxelMaterial CYAN_BED = register("minecraft", "cyan_bed", Tags.FALLSOFF);
    public static final VoxelMaterial PURPLE_BED = register("minecraft", "purple_bed", Tags.FALLSOFF);
    public static final VoxelMaterial BLUE_BED = register("minecraft", "blue_bed", Tags.FALLSOFF);
    public static final VoxelMaterial BROWN_BED = register("minecraft", "brown_bed", Tags.FALLSOFF);
    public static final VoxelMaterial GREEN_BED = register("minecraft", "green_bed", Tags.FALLSOFF);
    public static final VoxelMaterial RED_BED = register("minecraft", "red_bed", Tags.FALLSOFF);
    public static final VoxelMaterial BLACK_BED = register("minecraft", "black_bed", Tags.FALLSOFF);
    public static final VoxelMaterial POWERED_RAIL = register("minecraft", "powered_rail", Tags.FALLSOFF);
    public static final VoxelMaterial DETECTOR_RAIL = register("minecraft", "detector_rail", Tags.FALLSOFF);
    public static final VoxelMaterial STICKY_PISTON = register("minecraft", "sticky_piston");
    public static final VoxelMaterial COBWEB = register("minecraft", "cobweb");
    public static final VoxelMaterial GRASS = register("minecraft", "grass", Tags.FALLSOFF);
    public static final VoxelMaterial FERN = register("minecraft", "fern", Tags.FALLSOFF);
    public static final VoxelMaterial DEAD_BUSH = register("minecraft", "dead_bush", Tags.FALLSOFF);
    public static final VoxelMaterial SEAGRASS = register("minecraft", "seagrass", Tags.FALLSOFF);
    public static final VoxelMaterial TALL_SEAGRASS = register("minecraft", "tall_seagrass", Tags.FALLSOFF);
    public static final VoxelMaterial PISTON = register("minecraft", "piston");
    public static final VoxelMaterial PISTON_HEAD = register("minecraft", "piston_head");
    public static final VoxelMaterial WHITE_WOOL = register("minecraft", "white_wool");
    public static final VoxelMaterial ORANGE_WOOL = register("minecraft", "orange_wool");
    public static final VoxelMaterial MAGENTA_WOOL = register("minecraft", "magenta_wool");
    public static final VoxelMaterial LIGHT_BLUE_WOOL = register("minecraft", "light_blue_wool");
    public static final VoxelMaterial YELLOW_WOOL = register("minecraft", "yellow_wool");
    public static final VoxelMaterial LIME_WOOL = register("minecraft", "lime_wool");
    public static final VoxelMaterial PINK_WOOL = register("minecraft", "pink_wool");
    public static final VoxelMaterial GRAY_WOOL = register("minecraft", "gray_wool");
    public static final VoxelMaterial LIGHT_GRAY_WOOL = register("minecraft", "light_gray_wool");
    public static final VoxelMaterial CYAN_WOOL = register("minecraft", "cyan_wool");
    public static final VoxelMaterial PURPLE_WOOL = register("minecraft", "purple_wool");
    public static final VoxelMaterial BLUE_WOOL = register("minecraft", "blue_wool");
    public static final VoxelMaterial BROWN_WOOL = register("minecraft", "brown_wool");
    public static final VoxelMaterial GREEN_WOOL = register("minecraft", "green_wool");
    public static final VoxelMaterial RED_WOOL = register("minecraft", "red_wool");
    public static final VoxelMaterial BLACK_WOOL = register("minecraft", "black_wool");
    public static final VoxelMaterial MOVING_PISTON = register("minecraft", "moving_piston");
    public static final VoxelMaterial DANDELION = register("minecraft", "dandelion", Tags.FALLSOFF);
    public static final VoxelMaterial POPPY = register("minecraft", "poppy", Tags.FALLSOFF);
    public static final VoxelMaterial BLUE_ORCHID = register("minecraft", "blue_orchid", Tags.FALLSOFF);
    public static final VoxelMaterial ALLIUM = register("minecraft", "allium", Tags.FALLSOFF);
    public static final VoxelMaterial AZURE_BLUET = register("minecraft", "azure_bluet", Tags.FALLSOFF);
    public static final VoxelMaterial RED_TULIP = register("minecraft", "red_tulip", Tags.FALLSOFF);
    public static final VoxelMaterial ORANGE_TULIP = register("minecraft", "orange_tulip", Tags.FALLSOFF);
    public static final VoxelMaterial WHITE_TULIP = register("minecraft", "white_tulip", Tags.FALLSOFF);
    public static final VoxelMaterial PINK_TULIP = register("minecraft", "pink_tulip", Tags.FALLSOFF);
    public static final VoxelMaterial OXEYE_DAISY = register("minecraft", "oxeye_daisy", Tags.FALLSOFF);
    public static final VoxelMaterial CORNFLOWER = register("minecraft", "cornflower", Tags.FALLSOFF);
    public static final VoxelMaterial WITHER_ROSE = register("minecraft", "wither_rose", Tags.FALLSOFF);
    public static final VoxelMaterial LILY_OF_THE_VALLEY = register("minecraft", "lily_of_the_valley", Tags.FALLSOFF);
    public static final VoxelMaterial BROWN_MUSHROOM = register("minecraft", "brown_mushroom", Tags.FALLSOFF);
    public static final VoxelMaterial RED_MUSHROOM = register("minecraft", "red_mushroom", Tags.FALLSOFF);
    public static final VoxelMaterial GOLD_BLOCK = register("minecraft", "gold_block");
    public static final VoxelMaterial IRON_BLOCK = register("minecraft", "iron_block");
    public static final VoxelMaterial BRICKS = register("minecraft", "bricks");
    public static final VoxelMaterial TNT = register("minecraft", "tnt");
    public static final VoxelMaterial BOOKSHELF = register("minecraft", "bookshelf");
    public static final VoxelMaterial MOSSY_COBBLESTONE = register("minecraft", "mossy_cobblestone");
    public static final VoxelMaterial OBSIDIAN = register("minecraft", "obsidian");
    public static final VoxelMaterial TORCH = register("minecraft", "torch", Tags.FALLSOFF);
    public static final VoxelMaterial WALL_TORCH = register("minecraft", "wall_torch", Tags.FALLSOFF);
    public static final VoxelMaterial FIRE = register("minecraft", "fire", Tags.FALLSOFF);
    public static final VoxelMaterial SOUL_FIRE = register("minecraft", "soul_fire", Tags.FALLSOFF);
    public static final VoxelMaterial SPAWNER = register("minecraft", "spawner");
    public static final VoxelMaterial OAK_STAIRS = register("minecraft", "oak_stairs");
    public static final VoxelMaterial CHEST = register("minecraft", "chest");
    public static final VoxelMaterial REDSTONE_WIRE = register("minecraft", "redstone_wire", Tags.FALLSOFF);
    public static final VoxelMaterial DIAMOND_ORE = register("minecraft", "diamond_ore");
    public static final VoxelMaterial DEEPSLATE_DIAMOND_ORE = register("minecraft", "deepslate_diamond_ore", V1_17);
    public static final VoxelMaterial DIAMOND_BLOCK = register("minecraft", "diamond_block");
    public static final VoxelMaterial CRAFTING_TABLE = register("minecraft", "crafting_table");
    public static final VoxelMaterial WHEAT = register("minecraft", "wheat", Tags.FALLSOFF);
    public static final VoxelMaterial FARMLAND = register("minecraft", "farmland");
    public static final VoxelMaterial FURNACE = register("minecraft", "furnace");
    public static final VoxelMaterial OAK_SIGN = register("minecraft", "oak_sign", Tags.FALLSOFF);
    public static final VoxelMaterial SPRUCE_SIGN = register("minecraft", "spruce_sign", Tags.FALLSOFF);
    public static final VoxelMaterial BIRCH_SIGN = register("minecraft", "birch_sign", Tags.FALLSOFF);
    public static final VoxelMaterial ACACIA_SIGN = register("minecraft", "acacia_sign", Tags.FALLSOFF);
    public static final VoxelMaterial JUNGLE_SIGN = register("minecraft", "jungle_sign", Tags.FALLSOFF);
    public static final VoxelMaterial DARK_OAK_SIGN = register("minecraft", "dark_oak_sign", Tags.FALLSOFF);
    public static final VoxelMaterial OAK_DOOR = register("minecraft", "oak_door", Tags.FALLSOFF);
    public static final VoxelMaterial LADDER = register("minecraft", "ladder", Tags.FALLSOFF);
    public static final VoxelMaterial RAIL = register("minecraft", "rail", Tags.FALLSOFF);
    public static final VoxelMaterial COBBLESTONE_STAIRS = register("minecraft", "cobblestone_stairs");
    public static final VoxelMaterial OAK_WALL_SIGN = register("minecraft", "oak_wall_sign", Tags.FALLSOFF);
    public static final VoxelMaterial SPRUCE_WALL_SIGN = register("minecraft", "spruce_wall_sign", Tags.FALLSOFF);
    public static final VoxelMaterial BIRCH_WALL_SIGN = register("minecraft", "birch_wall_sign", Tags.FALLSOFF);
    public static final VoxelMaterial ACACIA_WALL_SIGN = register("minecraft", "acacia_wall_sign", Tags.FALLSOFF);
    public static final VoxelMaterial JUNGLE_WALL_SIGN = register("minecraft", "jungle_wall_sign", Tags.FALLSOFF);
    public static final VoxelMaterial DARK_OAK_WALL_SIGN = register("minecraft", "dark_oak_wall_sign", Tags.FALLSOFF);
    public static final VoxelMaterial LEVER = register("minecraft", "lever", Tags.FALLSOFF);
    public static final VoxelMaterial STONE_PRESSURE_PLATE = register("minecraft", "stone_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterial IRON_DOOR = register("minecraft", "iron_door", Tags.FALLSOFF);
    public static final VoxelMaterial OAK_PRESSURE_PLATE = register("minecraft", "oak_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterial SPRUCE_PRESSURE_PLATE = register("minecraft", "spruce_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterial BIRCH_PRESSURE_PLATE = register("minecraft", "birch_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterial JUNGLE_PRESSURE_PLATE = register("minecraft", "jungle_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterial ACACIA_PRESSURE_PLATE = register("minecraft", "acacia_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterial DARK_OAK_PRESSURE_PLATE = register("minecraft", "dark_oak_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterial REDSTONE_ORE = register("minecraft", "redstone_ore", Tags.FALLSOFF);
    public static final VoxelMaterial DEEPSLATE_REDSTONE_ORE = register("minecraft", "deepslate_redstone_ore", V1_17);
    public static final VoxelMaterial REDSTONE_TORCH = register("minecraft", "redstone_torch", Tags.FALLSOFF);
    public static final VoxelMaterial REDSTONE_WALL_TORCH = register("minecraft", "redstone_wall_torch", Tags.FALLSOFF);
    public static final VoxelMaterial STONE_BUTTON = register("minecraft", "stone_button", Tags.FALLSOFF);
    public static final VoxelMaterial SNOW = register("minecraft", "snow", Tags.FALLSOFF);
    public static final VoxelMaterial ICE = register("minecraft", "ice");
    public static final VoxelMaterial SNOW_BLOCK = register("minecraft", "snow_block");
    public static final VoxelMaterial CACTUS = register("minecraft", "cactus", Tags.FALLSOFF);
    public static final VoxelMaterial CLAY = register("minecraft", "clay");
    public static List<VoxelMaterial> OVERRIDABLE_MATERIALS = Arrays.asList(VoxelMaterial.STONE,
            VoxelMaterial.ANDESITE,
            VoxelMaterial.DIORITE,
            VoxelMaterial.GRANITE,
            VoxelMaterial.GRASS_BLOCK,
            VoxelMaterial.DIRT,
            VoxelMaterial.COARSE_DIRT,
            VoxelMaterial.PODZOL,
            VoxelMaterial.SAND,
            VoxelMaterial.RED_SAND,
            VoxelMaterial.GRAVEL,
            VoxelMaterial.SANDSTONE,
            VoxelMaterial.MOSSY_COBBLESTONE,
            VoxelMaterial.CLAY,
            VoxelMaterial.SNOW,
            VoxelMaterial.OBSIDIAN
    );
    public static final VoxelMaterial SUGAR_CANE = register("minecraft", "sugar_cane", Tags.FALLSOFF);
    public static final VoxelMaterial JUKEBOX = register("minecraft", "jukebox");
    public static final VoxelMaterial OAK_FENCE = register("minecraft", "oak_fence");
    public static final VoxelMaterial PUMPKIN = register("minecraft", "pumpkin");
    public static final VoxelMaterial NETHERRACK = register("minecraft", "netherrack");
    public static final VoxelMaterial SOUL_SAND = register("minecraft", "soul_sand");
    public static final VoxelMaterial SOUL_SOIL = register("minecraft", "soul_soil");
    public static final VoxelMaterial BASALT = register("minecraft", "basalt");
    public static final VoxelMaterial POLISHED_BASALT = register("minecraft", "polished_basalt");
    public static final VoxelMaterial SOUL_TORCH = register("minecraft", "soul_torch", Tags.FALLSOFF);
    public static final VoxelMaterial SOUL_WALL_TORCH = register("minecraft", "soul_wall_torch", Tags.FALLSOFF);
    public static final VoxelMaterial GLOWSTONE = register("minecraft", "glowstone");
    public static final VoxelMaterial NETHER_PORTAL = register("minecraft", "nether_portal");
    public static final VoxelMaterial CARVED_PUMPKIN = register("minecraft", "carved_pumpkin");
    public static final VoxelMaterial JACK_O_LANTERN = register("minecraft", "jack_o_lantern");
    public static final VoxelMaterial CAKE = register("minecraft", "cake", Tags.FALLSOFF);
    public static final VoxelMaterial REPEATER = register("minecraft", "repeater", Tags.FALLSOFF);
    public static final VoxelMaterial WHITE_STAINED_GLASS = register("minecraft", "white_stained_glass");
    public static final VoxelMaterial ORANGE_STAINED_GLASS = register("minecraft", "orange_stained_glass");
    public static final VoxelMaterial MAGENTA_STAINED_GLASS = register("minecraft", "magenta_stained_glass");
    public static final VoxelMaterial LIGHT_BLUE_STAINED_GLASS = register("minecraft", "light_blue_stained_glass");
    public static final VoxelMaterial YELLOW_STAINED_GLASS = register("minecraft", "yellow_stained_glass");
    public static final VoxelMaterial LIME_STAINED_GLASS = register("minecraft", "lime_stained_glass");
    public static final VoxelMaterial PINK_STAINED_GLASS = register("minecraft", "pink_stained_glass");
    public static final VoxelMaterial GRAY_STAINED_GLASS = register("minecraft", "gray_stained_glass");
    public static final VoxelMaterial LIGHT_GRAY_STAINED_GLASS = register("minecraft", "light_gray_stained_glass");
    public static final VoxelMaterial CYAN_STAINED_GLASS = register("minecraft", "cyan_stained_glass");
    public static final VoxelMaterial PURPLE_STAINED_GLASS = register("minecraft", "purple_stained_glass");
    public static final VoxelMaterial BLUE_STAINED_GLASS = register("minecraft", "blue_stained_glass");
    public static final VoxelMaterial BROWN_STAINED_GLASS = register("minecraft", "brown_stained_glass");
    public static final VoxelMaterial GREEN_STAINED_GLASS = register("minecraft", "green_stained_glass");
    public static final VoxelMaterial RED_STAINED_GLASS = register("minecraft", "red_stained_glass");
    public static final VoxelMaterial BLACK_STAINED_GLASS = register("minecraft", "black_stained_glass");
    public static final VoxelMaterial OAK_TRAPDOOR = register("minecraft", "oak_trapdoor");
    public static final VoxelMaterial SPRUCE_TRAPDOOR = register("minecraft", "spruce_trapdoor");
    public static final VoxelMaterial BIRCH_TRAPDOOR = register("minecraft", "birch_trapdoor");
    public static final VoxelMaterial JUNGLE_TRAPDOOR = register("minecraft", "jungle_trapdoor");
    public static final VoxelMaterial ACACIA_TRAPDOOR = register("minecraft", "acacia_trapdoor");
    public static final VoxelMaterial DARK_OAK_TRAPDOOR = register("minecraft", "dark_oak_trapdoor");
    public static final VoxelMaterial STONE_BRICKS = register("minecraft", "stone_bricks");
    public static final VoxelMaterial MOSSY_STONE_BRICKS = register("minecraft", "mossy_stone_bricks");
    public static final VoxelMaterial CRACKED_STONE_BRICKS = register("minecraft", "cracked_stone_bricks");
    public static final VoxelMaterial CHISELED_STONE_BRICKS = register("minecraft", "chiseled_stone_bricks");
    public static final VoxelMaterial INFESTED_STONE = register("minecraft", "infested_stone");
    public static final VoxelMaterial INFESTED_COBBLESTONE = register("minecraft", "infested_cobblestone");
    public static final VoxelMaterial INFESTED_STONE_BRICKS = register("minecraft", "infested_stone_bricks");
    public static final VoxelMaterial INFESTED_MOSSY_STONE_BRICKS = register("minecraft", "infested_mossy_stone_bricks");
    public static final VoxelMaterial INFESTED_CRACKED_STONE_BRICKS = register("minecraft", "infested_cracked_stone_bricks");
    public static final VoxelMaterial INFESTED_CHISELED_STONE_BRICKS = register("minecraft", "infested_chiseled_stone_bricks");
    public static final VoxelMaterial BROWN_MUSHROOM_BLOCK = register("minecraft", "brown_mushroom_block");
    public static final VoxelMaterial RED_MUSHROOM_BLOCK = register("minecraft", "red_mushroom_block");
    public static final VoxelMaterial MUSHROOM_STEM = register("minecraft", "mushroom_stem");
    public static final VoxelMaterial IRON_BARS = register("minecraft", "iron_bars");
    public static final VoxelMaterial CHAIN = register("minecraft", "chain");
    public static final VoxelMaterial GLASS_PANE = register("minecraft", "glass_pane");
    public static final VoxelMaterial MELON = register("minecraft", "melon");
    public static final VoxelMaterial ATTACHED_PUMPKIN_STEM = register("minecraft", "attached_pumpkin_stem", Tags.FALLSOFF);
    public static final VoxelMaterial ATTACHED_MELON_STEM = register("minecraft", "attached_melon_stem", Tags.FALLSOFF);
    public static final VoxelMaterial PUMPKIN_STEM = register("minecraft", "pumpkin_stem", Tags.FALLSOFF);
    public static final VoxelMaterial MELON_STEM = register("minecraft", "melon_stem", Tags.FALLSOFF);
    public static final VoxelMaterial VINE = register("minecraft", "vine", Tags.FALLSOFF);
    public static final VoxelMaterial GLOW_LICHEN = register("minecraft", "glow_lichen", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial OAK_FENCE_GATE = register("minecraft", "oak_fence_gate");
    public static final VoxelMaterial BRICK_STAIRS = register("minecraft", "brick_stairs");
    public static final VoxelMaterial STONE_BRICK_STAIRS = register("minecraft", "stone_brick_stairs");
    public static final VoxelMaterial MYCELIUM = register("minecraft", "mycelium");
    public static final VoxelMaterial LILY_PAD = register("minecraft", "lily_pad", Tags.FALLSOFF);
    public static final VoxelMaterial NETHER_BRICKS = register("minecraft", "nether_bricks");
    public static final VoxelMaterial NETHER_BRICK_FENCE = register("minecraft", "nether_brick_fence");
    public static final VoxelMaterial NETHER_BRICK_STAIRS = register("minecraft", "nether_brick_stairs");
    public static final VoxelMaterial NETHER_WART = register("minecraft", "nether_wart", Tags.FALLSOFF);
    public static final VoxelMaterial ENCHANTING_TABLE = register("minecraft", "enchanting_table");
    public static final VoxelMaterial BREWING_STAND = register("minecraft", "brewing_stand");
    public static final VoxelMaterial CAULDRON = register("minecraft", "cauldron");
    public static final VoxelMaterial WATER_CAULDRON = register("minecraft", "water_cauldron", V1_17);
    public static final VoxelMaterial LAVA_CAULDRON = register("minecraft", "lava_cauldron", V1_17);
    public static final VoxelMaterial POWDER_SNOW_CAULDRON = register("minecraft", "powder_snow_cauldron", V1_17);
    public static final VoxelMaterial END_PORTAL = register("minecraft", "end_portal");
    public static final VoxelMaterial END_PORTAL_FRAME = register("minecraft", "end_portal_frame");
    public static final VoxelMaterial END_STONE = register("minecraft", "end_stone");
    public static final VoxelMaterial DRAGON_EGG = register("minecraft", "dragon_egg");
    public static final VoxelMaterial REDSTONE_LAMP = register("minecraft", "redstone_lamp");
    public static final VoxelMaterial COCOA = register("minecraft", "cocoa", Tags.FALLSOFF);
    public static final VoxelMaterial SANDSTONE_STAIRS = register("minecraft", "sandstone_stairs");
    public static final VoxelMaterial EMERALD_ORE = register("minecraft", "emerald_ore");
    public static final VoxelMaterial DEEPSLATE_EMERALD_ORE = register("minecraft", "deepslate_emerald_ore", V1_17);
    public static final VoxelMaterial ENDER_CHEST = register("minecraft", "ender_chest");
    public static final VoxelMaterial TRIPWIRE_HOOK = register("minecraft", "tripwire_hook", Tags.FALLSOFF);
    public static final VoxelMaterial TRIPWIRE = register("minecraft", "tripwire");
    public static final VoxelMaterial EMERALD_BLOCK = register("minecraft", "emerald_block");
    public static final VoxelMaterial SPRUCE_STAIRS = register("minecraft", "spruce_stairs");
    public static final VoxelMaterial BIRCH_STAIRS = register("minecraft", "birch_stairs");
    public static final VoxelMaterial JUNGLE_STAIRS = register("minecraft", "jungle_stairs");
    public static final VoxelMaterial COMMAND_BLOCK = register("minecraft", "command_block");
    public static final VoxelMaterial BEACON = register("minecraft", "beacon");
    public static final VoxelMaterial COBBLESTONE_WALL = register("minecraft", "cobblestone_wall");
    public static final VoxelMaterial MOSSY_COBBLESTONE_WALL = register("minecraft", "mossy_cobblestone_wall");
    public static final VoxelMaterial FLOWER_POT = register("minecraft", "flower_pot");
    public static final VoxelMaterial POTTED_OAK_SAPLING = register("minecraft", "potted_oak_sapling");
    public static final VoxelMaterial POTTED_SPRUCE_SAPLING = register("minecraft", "potted_spruce_sapling");
    public static final VoxelMaterial POTTED_BIRCH_SAPLING = register("minecraft", "potted_birch_sapling");
    public static final VoxelMaterial POTTED_JUNGLE_SAPLING = register("minecraft", "potted_jungle_sapling");
    public static final VoxelMaterial POTTED_ACACIA_SAPLING = register("minecraft", "potted_acacia_sapling");
    public static final VoxelMaterial POTTED_DARK_OAK_SAPLING = register("minecraft", "potted_dark_oak_sapling");
    public static final VoxelMaterial POTTED_FERN = register("minecraft", "potted_fern");
    public static final VoxelMaterial POTTED_DANDELION = register("minecraft", "potted_dandelion");
    public static final VoxelMaterial POTTED_POPPY = register("minecraft", "potted_poppy");
    public static final VoxelMaterial POTTED_BLUE_ORCHID = register("minecraft", "potted_blue_orchid");
    public static final VoxelMaterial POTTED_ALLIUM = register("minecraft", "potted_allium");
    public static final VoxelMaterial POTTED_AZURE_BLUET = register("minecraft", "potted_azure_bluet");
    public static final VoxelMaterial POTTED_RED_TULIP = register("minecraft", "potted_red_tulip");
    public static final VoxelMaterial POTTED_ORANGE_TULIP = register("minecraft", "potted_orange_tulip");
    public static final VoxelMaterial POTTED_WHITE_TULIP = register("minecraft", "potted_white_tulip");
    public static final VoxelMaterial POTTED_PINK_TULIP = register("minecraft", "potted_pink_tulip");
    public static final VoxelMaterial POTTED_OXEYE_DAISY = register("minecraft", "potted_oxeye_daisy");
    public static final VoxelMaterial POTTED_CORNFLOWER = register("minecraft", "potted_cornflower");
    public static final VoxelMaterial POTTED_LILY_OF_THE_VALLEY = register("minecraft", "potted_lily_of_the_valley");
    public static final VoxelMaterial POTTED_WITHER_ROSE = register("minecraft", "potted_wither_rose");
    public static final VoxelMaterial POTTED_RED_MUSHROOM = register("minecraft", "potted_red_mushroom");
    public static final VoxelMaterial POTTED_BROWN_MUSHROOM = register("minecraft", "potted_brown_mushroom");
    public static final VoxelMaterial POTTED_DEAD_BUSH = register("minecraft", "potted_dead_bush");
    public static final VoxelMaterial POTTED_CACTUS = register("minecraft", "potted_cactus");
    public static final VoxelMaterial CARROTS = register("minecraft", "carrots", Tags.FALLSOFF);
    public static final VoxelMaterial POTATOES = register("minecraft", "potatoes", Tags.FALLSOFF);
    public static final VoxelMaterial OAK_BUTTON = register("minecraft", "oak_button", Tags.FALLSOFF);
    public static final VoxelMaterial SPRUCE_BUTTON = register("minecraft", "spruce_button", Tags.FALLSOFF);
    public static final VoxelMaterial BIRCH_BUTTON = register("minecraft", "birch_button", Tags.FALLSOFF);
    public static final VoxelMaterial JUNGLE_BUTTON = register("minecraft", "jungle_button", Tags.FALLSOFF);
    public static final VoxelMaterial ACACIA_BUTTON = register("minecraft", "acacia_button", Tags.FALLSOFF);
    public static final VoxelMaterial DARK_OAK_BUTTON = register("minecraft", "dark_oak_button", Tags.FALLSOFF);
    public static final VoxelMaterial SKELETON_SKULL = register("minecraft", "skeleton_skull");
    public static final VoxelMaterial SKELETON_WALL_SKULL = register("minecraft", "skeleton_wall_skull");
    public static final VoxelMaterial WITHER_SKELETON_SKULL = register("minecraft", "wither_skeleton_skull");
    public static final VoxelMaterial WITHER_SKELETON_WALL_SKULL = register("minecraft", "wither_skeleton_wall_skull");
    public static final VoxelMaterial ZOMBIE_HEAD = register("minecraft", "zombie_head");
    public static final VoxelMaterial ZOMBIE_WALL_HEAD = register("minecraft", "zombie_wall_head");
    public static final VoxelMaterial PLAYER_HEAD = register("minecraft", "player_head");
    public static final VoxelMaterial PLAYER_WALL_HEAD = register("minecraft", "player_wall_head");
    public static final VoxelMaterial CREEPER_HEAD = register("minecraft", "creeper_head");
    public static final VoxelMaterial CREEPER_WALL_HEAD = register("minecraft", "creeper_wall_head");
    public static final VoxelMaterial DRAGON_HEAD = register("minecraft", "dragon_head");
    public static final VoxelMaterial DRAGON_WALL_HEAD = register("minecraft", "dragon_wall_head");
    public static final VoxelMaterial ANVIL = register("minecraft", "anvil");
    public static final VoxelMaterial CHIPPED_ANVIL = register("minecraft", "chipped_anvil");
    public static final VoxelMaterial DAMAGED_ANVIL = register("minecraft", "damaged_anvil");
    public static final VoxelMaterial TRAPPED_CHEST = register("minecraft", "trapped_chest");
    public static final VoxelMaterial LIGHT_WEIGHTED_PRESSURE_PLATE = register("minecraft", "light_weighted_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterial HEAVY_WEIGHTED_PRESSURE_PLATE = register("minecraft", "heavy_weighted_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterial COMPARATOR = register("minecraft", "comparator", Tags.FALLSOFF);
    public static final VoxelMaterial DAYLIGHT_DETECTOR = register("minecraft", "daylight_detector");
    public static final VoxelMaterial REDSTONE_BLOCK = register("minecraft", "redstone_block");
    public static final VoxelMaterial NETHER_QUARTZ_ORE = register("minecraft", "nether_quartz_ore");
    public static final VoxelMaterial HOPPER = register("minecraft", "hopper");
    public static final VoxelMaterial QUARTZ_BLOCK = register("minecraft", "quartz_block");
    public static final VoxelMaterial CHISELED_QUARTZ_BLOCK = register("minecraft", "chiseled_quartz_block");
    public static final VoxelMaterial QUARTZ_PILLAR = register("minecraft", "quartz_pillar");
    public static final VoxelMaterial QUARTZ_STAIRS = register("minecraft", "quartz_stairs");
    public static final VoxelMaterial ACTIVATOR_RAIL = register("minecraft", "activator_rail", Tags.FALLSOFF);
    public static final VoxelMaterial DROPPER = register("minecraft", "dropper");
    public static final VoxelMaterial WHITE_TERRACOTTA = register("minecraft", "white_terracotta");
    public static final VoxelMaterial ORANGE_TERRACOTTA = register("minecraft", "orange_terracotta");
    public static final VoxelMaterial MAGENTA_TERRACOTTA = register("minecraft", "magenta_terracotta");
    public static final VoxelMaterial LIGHT_BLUE_TERRACOTTA = register("minecraft", "light_blue_terracotta");
    public static final VoxelMaterial YELLOW_TERRACOTTA = register("minecraft", "yellow_terracotta");
    public static final VoxelMaterial LIME_TERRACOTTA = register("minecraft", "lime_terracotta");
    public static final VoxelMaterial PINK_TERRACOTTA = register("minecraft", "pink_terracotta");
    public static final VoxelMaterial GRAY_TERRACOTTA = register("minecraft", "gray_terracotta");
    public static final VoxelMaterial LIGHT_GRAY_TERRACOTTA = register("minecraft", "light_gray_terracotta");
    public static final VoxelMaterial CYAN_TERRACOTTA = register("minecraft", "cyan_terracotta");
    public static final VoxelMaterial PURPLE_TERRACOTTA = register("minecraft", "purple_terracotta");
    public static final VoxelMaterial BLUE_TERRACOTTA = register("minecraft", "blue_terracotta");
    public static final VoxelMaterial BROWN_TERRACOTTA = register("minecraft", "brown_terracotta");
    public static final VoxelMaterial GREEN_TERRACOTTA = register("minecraft", "green_terracotta");
    public static final VoxelMaterial RED_TERRACOTTA = register("minecraft", "red_terracotta");
    public static final VoxelMaterial BLACK_TERRACOTTA = register("minecraft", "black_terracotta");
    public static final VoxelMaterial WHITE_STAINED_GLASS_PANE = register("minecraft", "white_stained_glass_pane");
    public static final VoxelMaterial ORANGE_STAINED_GLASS_PANE = register("minecraft", "orange_stained_glass_pane");
    public static final VoxelMaterial MAGENTA_STAINED_GLASS_PANE = register("minecraft", "magenta_stained_glass_pane");
    public static final VoxelMaterial LIGHT_BLUE_STAINED_GLASS_PANE = register("minecraft", "light_blue_stained_glass_pane");
    public static final VoxelMaterial YELLOW_STAINED_GLASS_PANE = register("minecraft", "yellow_stained_glass_pane");
    public static final VoxelMaterial LIME_STAINED_GLASS_PANE = register("minecraft", "lime_stained_glass_pane");
    public static final VoxelMaterial PINK_STAINED_GLASS_PANE = register("minecraft", "pink_stained_glass_pane");
    public static final VoxelMaterial GRAY_STAINED_GLASS_PANE = register("minecraft", "gray_stained_glass_pane");
    public static final VoxelMaterial LIGHT_GRAY_STAINED_GLASS_PANE = register("minecraft", "light_gray_stained_glass_pane");
    public static final VoxelMaterial CYAN_STAINED_GLASS_PANE = register("minecraft", "cyan_stained_glass_pane");
    public static final VoxelMaterial PURPLE_STAINED_GLASS_PANE = register("minecraft", "purple_stained_glass_pane");
    public static final VoxelMaterial BLUE_STAINED_GLASS_PANE = register("minecraft", "blue_stained_glass_pane");
    public static final VoxelMaterial BROWN_STAINED_GLASS_PANE = register("minecraft", "brown_stained_glass_pane");
    public static final VoxelMaterial GREEN_STAINED_GLASS_PANE = register("minecraft", "green_stained_glass_pane");
    public static final VoxelMaterial RED_STAINED_GLASS_PANE = register("minecraft", "red_stained_glass_pane");
    public static final VoxelMaterial BLACK_STAINED_GLASS_PANE = register("minecraft", "black_stained_glass_pane");
    public static final VoxelMaterial ACACIA_STAIRS = register("minecraft", "acacia_stairs");
    public static final VoxelMaterial DARK_OAK_STAIRS = register("minecraft", "dark_oak_stairs");
    public static final VoxelMaterial SLIME_BLOCK = register("minecraft", "slime_block");
    public static final VoxelMaterial BARRIER = register("minecraft", "barrier");
    public static final VoxelMaterial LIGHT = register("minecraft", "light", V1_17);
    public static final VoxelMaterial IRON_TRAPDOOR = register("minecraft", "iron_trapdoor");
    public static final VoxelMaterial PRISMARINE = register("minecraft", "prismarine");
    public static final VoxelMaterial PRISMARINE_BRICKS = register("minecraft", "prismarine_bricks");
    public static final VoxelMaterial DARK_PRISMARINE = register("minecraft", "dark_prismarine");
    public static final VoxelMaterial PRISMARINE_STAIRS = register("minecraft", "prismarine_stairs");
    public static final VoxelMaterial PRISMARINE_BRICK_STAIRS = register("minecraft", "prismarine_brick_stairs");
    public static final VoxelMaterial DARK_PRISMARINE_STAIRS = register("minecraft", "dark_prismarine_stairs");
    public static final VoxelMaterial PRISMARINE_SLAB = register("minecraft", "prismarine_slab");
    public static final VoxelMaterial PRISMARINE_BRICK_SLAB = register("minecraft", "prismarine_brick_slab");
    public static final VoxelMaterial DARK_PRISMARINE_SLAB = register("minecraft", "dark_prismarine_slab");
    public static final VoxelMaterial SEA_LANTERN = register("minecraft", "sea_lantern");
    public static final VoxelMaterial HAY_BLOCK = register("minecraft", "hay_block");
    public static final VoxelMaterial WHITE_CARPET = register("minecraft", "white_carpet", Tags.FALLSOFF);
    public static final VoxelMaterial ORANGE_CARPET = register("minecraft", "orange_carpet", Tags.FALLSOFF);
    public static final VoxelMaterial MAGENTA_CARPET = register("minecraft", "magenta_carpet", Tags.FALLSOFF);
    public static final VoxelMaterial LIGHT_BLUE_CARPET = register("minecraft", "light_blue_carpet", Tags.FALLSOFF);
    public static final VoxelMaterial YELLOW_CARPET = register("minecraft", "yellow_carpet", Tags.FALLSOFF);
    public static final VoxelMaterial LIME_CARPET = register("minecraft", "lime_carpet", Tags.FALLSOFF);
    public static final VoxelMaterial PINK_CARPET = register("minecraft", "pink_carpet", Tags.FALLSOFF);
    public static final VoxelMaterial GRAY_CARPET = register("minecraft", "gray_carpet", Tags.FALLSOFF);
    public static final VoxelMaterial LIGHT_GRAY_CARPET = register("minecraft", "light_gray_carpet", Tags.FALLSOFF);
    public static final VoxelMaterial CYAN_CARPET = register("minecraft", "cyan_carpet", Tags.FALLSOFF);
    public static final VoxelMaterial PURPLE_CARPET = register("minecraft", "purple_carpet", Tags.FALLSOFF);
    public static final VoxelMaterial BLUE_CARPET = register("minecraft", "blue_carpet", Tags.FALLSOFF);
    public static final VoxelMaterial BROWN_CARPET = register("minecraft", "brown_carpet", Tags.FALLSOFF);
    public static final VoxelMaterial GREEN_CARPET = register("minecraft", "green_carpet", Tags.FALLSOFF);
    public static final VoxelMaterial RED_CARPET = register("minecraft", "red_carpet", Tags.FALLSOFF);
    public static final VoxelMaterial BLACK_CARPET = register("minecraft", "black_carpet", Tags.FALLSOFF);
    public static final VoxelMaterial TERRACOTTA = register("minecraft", "terracotta");
    public static final VoxelMaterial COAL_BLOCK = register("minecraft", "coal_block");
    public static final VoxelMaterial PACKED_ICE = register("minecraft", "packed_ice");
    public static final VoxelMaterial SUNFLOWER = register("minecraft", "sunflower", Tags.FALLSOFF);
    public static final VoxelMaterial LILAC = register("minecraft", "lilac", Tags.FALLSOFF);
    public static final VoxelMaterial ROSE_BUSH = register("minecraft", "rose_bush", Tags.FALLSOFF);
    public static final VoxelMaterial PEONY = register("minecraft", "peony", Tags.FALLSOFF);
    public static final VoxelMaterial TALL_GRASS = register("minecraft", "tall_grass", Tags.FALLSOFF);
    public static final VoxelMaterial LARGE_FERN = register("minecraft", "large_fern", Tags.FALLSOFF);
    public static final VoxelMaterial WHITE_BANNER = register("minecraft", "white_banner", Tags.FALLSOFF);
    public static final VoxelMaterial ORANGE_BANNER = register("minecraft", "orange_banner", Tags.FALLSOFF);
    public static final VoxelMaterial MAGENTA_BANNER = register("minecraft", "magenta_banner", Tags.FALLSOFF);
    public static final VoxelMaterial LIGHT_BLUE_BANNER = register("minecraft", "light_blue_banner", Tags.FALLSOFF);
    public static final VoxelMaterial YELLOW_BANNER = register("minecraft", "yellow_banner", Tags.FALLSOFF);
    public static final VoxelMaterial LIME_BANNER = register("minecraft", "lime_banner", Tags.FALLSOFF);
    public static final VoxelMaterial PINK_BANNER = register("minecraft", "pink_banner", Tags.FALLSOFF);
    public static final VoxelMaterial GRAY_BANNER = register("minecraft", "gray_banner", Tags.FALLSOFF);
    public static final VoxelMaterial LIGHT_GRAY_BANNER = register("minecraft", "light_gray_banner", Tags.FALLSOFF);
    public static final VoxelMaterial CYAN_BANNER = register("minecraft", "cyan_banner", Tags.FALLSOFF);
    public static final VoxelMaterial PURPLE_BANNER = register("minecraft", "purple_banner", Tags.FALLSOFF);
    public static final VoxelMaterial BLUE_BANNER = register("minecraft", "blue_banner", Tags.FALLSOFF);
    public static final VoxelMaterial BROWN_BANNER = register("minecraft", "brown_banner", Tags.FALLSOFF);
    public static final VoxelMaterial GREEN_BANNER = register("minecraft", "green_banner", Tags.FALLSOFF);
    public static final VoxelMaterial RED_BANNER = register("minecraft", "red_banner", Tags.FALLSOFF);
    public static final VoxelMaterial BLACK_BANNER = register("minecraft", "black_banner", Tags.FALLSOFF);
    public static final VoxelMaterial WHITE_WALL_BANNER = register("minecraft", "white_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterial ORANGE_WALL_BANNER = register("minecraft", "orange_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterial MAGENTA_WALL_BANNER = register("minecraft", "magenta_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterial LIGHT_BLUE_WALL_BANNER = register("minecraft", "light_blue_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterial YELLOW_WALL_BANNER = register("minecraft", "yellow_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterial LIME_WALL_BANNER = register("minecraft", "lime_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterial PINK_WALL_BANNER = register("minecraft", "pink_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterial GRAY_WALL_BANNER = register("minecraft", "gray_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterial LIGHT_GRAY_WALL_BANNER = register("minecraft", "light_gray_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterial CYAN_WALL_BANNER = register("minecraft", "cyan_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterial PURPLE_WALL_BANNER = register("minecraft", "purple_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterial BLUE_WALL_BANNER = register("minecraft", "blue_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterial BROWN_WALL_BANNER = register("minecraft", "brown_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterial GREEN_WALL_BANNER = register("minecraft", "green_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterial RED_WALL_BANNER = register("minecraft", "red_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterial BLACK_WALL_BANNER = register("minecraft", "black_wall_banner", Tags.FALLSOFF);
    public static final VoxelMaterial RED_SANDSTONE = register("minecraft", "red_sandstone");
    public static final VoxelMaterial CHISELED_RED_SANDSTONE = register("minecraft", "chiseled_red_sandstone");
    public static final VoxelMaterial CUT_RED_SANDSTONE = register("minecraft", "cut_red_sandstone");
    public static final VoxelMaterial RED_SANDSTONE_STAIRS = register("minecraft", "red_sandstone_stairs");
    public static final VoxelMaterial OAK_SLAB = register("minecraft", "oak_slab");
    public static final VoxelMaterial SPRUCE_SLAB = register("minecraft", "spruce_slab");
    public static final VoxelMaterial BIRCH_SLAB = register("minecraft", "birch_slab");
    public static final VoxelMaterial JUNGLE_SLAB = register("minecraft", "jungle_slab");
    public static final VoxelMaterial ACACIA_SLAB = register("minecraft", "acacia_slab");
    public static final VoxelMaterial DARK_OAK_SLAB = register("minecraft", "dark_oak_slab");
    public static final VoxelMaterial STONE_SLAB = register("minecraft", "stone_slab");
    public static final VoxelMaterial SMOOTH_STONE_SLAB = register("minecraft", "smooth_stone_slab");
    public static final VoxelMaterial SANDSTONE_SLAB = register("minecraft", "sandstone_slab");
    public static final VoxelMaterial CUT_SANDSTONE_SLAB = register("minecraft", "cut_sandstone_slab");
    public static final VoxelMaterial PETRIFIED_OAK_SLAB = register("minecraft", "petrified_oak_slab");
    public static final VoxelMaterial COBBLESTONE_SLAB = register("minecraft", "cobblestone_slab");
    public static final VoxelMaterial BRICK_SLAB = register("minecraft", "brick_slab");
    public static final VoxelMaterial STONE_BRICK_SLAB = register("minecraft", "stone_brick_slab");
    public static final VoxelMaterial NETHER_BRICK_SLAB = register("minecraft", "nether_brick_slab");
    public static final VoxelMaterial QUARTZ_SLAB = register("minecraft", "quartz_slab");
    public static final VoxelMaterial RED_SANDSTONE_SLAB = register("minecraft", "red_sandstone_slab");
    public static final VoxelMaterial CUT_RED_SANDSTONE_SLAB = register("minecraft", "cut_red_sandstone_slab");
    public static final VoxelMaterial PURPUR_SLAB = register("minecraft", "purpur_slab");
    public static final VoxelMaterial SMOOTH_STONE = register("minecraft", "smooth_stone");
    public static final VoxelMaterial SMOOTH_SANDSTONE = register("minecraft", "smooth_sandstone");
    public static final VoxelMaterial SMOOTH_QUARTZ = register("minecraft", "smooth_quartz");
    public static final VoxelMaterial SMOOTH_RED_SANDSTONE = register("minecraft", "smooth_red_sandstone");
    public static final VoxelMaterial SPRUCE_FENCE_GATE = register("minecraft", "spruce_fence_gate");
    public static final VoxelMaterial BIRCH_FENCE_GATE = register("minecraft", "birch_fence_gate");
    public static final VoxelMaterial JUNGLE_FENCE_GATE = register("minecraft", "jungle_fence_gate");
    public static final VoxelMaterial ACACIA_FENCE_GATE = register("minecraft", "acacia_fence_gate");
    public static final VoxelMaterial DARK_OAK_FENCE_GATE = register("minecraft", "dark_oak_fence_gate");
    public static final VoxelMaterial SPRUCE_FENCE = register("minecraft", "spruce_fence");
    public static final VoxelMaterial BIRCH_FENCE = register("minecraft", "birch_fence");
    public static final VoxelMaterial JUNGLE_FENCE = register("minecraft", "jungle_fence");
    public static final VoxelMaterial ACACIA_FENCE = register("minecraft", "acacia_fence");
    public static final VoxelMaterial DARK_OAK_FENCE = register("minecraft", "dark_oak_fence");
    public static final VoxelMaterial SPRUCE_DOOR = register("minecraft", "spruce_door", Tags.FALLSOFF);
    public static final VoxelMaterial BIRCH_DOOR = register("minecraft", "birch_door", Tags.FALLSOFF);
    public static final VoxelMaterial JUNGLE_DOOR = register("minecraft", "jungle_door", Tags.FALLSOFF);
    public static final VoxelMaterial ACACIA_DOOR = register("minecraft", "acacia_door", Tags.FALLSOFF);
    public static final VoxelMaterial DARK_OAK_DOOR = register("minecraft", "dark_oak_door", Tags.FALLSOFF);
    public static final VoxelMaterial END_ROD = register("minecraft", "end_rod");
    public static final VoxelMaterial CHORUS_PLANT = register("minecraft", "chorus_plant", Tags.FALLSOFF);
    public static final VoxelMaterial CHORUS_FLOWER = register("minecraft", "chorus_flower", Tags.FALLSOFF);
    public static final VoxelMaterial PURPUR_BLOCK = register("minecraft", "purpur_block");
    public static final VoxelMaterial PURPUR_PILLAR = register("minecraft", "purpur_pillar");
    public static final VoxelMaterial PURPUR_STAIRS = register("minecraft", "purpur_stairs");
    public static final VoxelMaterial END_STONE_BRICKS = register("minecraft", "end_stone_bricks");
    public static final VoxelMaterial BEETROOTS = register("minecraft", "beetroots", Tags.FALLSOFF);
    public static final VoxelMaterial DIRT_PATH = register("minecraft", "dirt_path", V1_17);
    public static final VoxelMaterial END_GATEWAY = register("minecraft", "end_gateway");
    public static final VoxelMaterial REPEATING_COMMAND_BLOCK = register("minecraft", "repeating_command_block");
    public static final VoxelMaterial CHAIN_COMMAND_BLOCK = register("minecraft", "chain_command_block");
    public static final VoxelMaterial FROSTED_ICE = register("minecraft", "frosted_ice");
    public static final VoxelMaterial MAGMA_BLOCK = register("minecraft", "magma_block");
    public static final VoxelMaterial NETHER_WART_BLOCK = register("minecraft", "nether_wart_block");
    public static final VoxelMaterial RED_NETHER_BRICKS = register("minecraft", "red_nether_bricks");
    public static final VoxelMaterial BONE_BLOCK = register("minecraft", "bone_block");
    public static final VoxelMaterial STRUCTURE_VOID = register("minecraft", "structure_void");
    public static final VoxelMaterial OBSERVER = register("minecraft", "observer");
    public static final VoxelMaterial SHULKER_BOX = register("minecraft", "shulker_box");
    public static final VoxelMaterial WHITE_SHULKER_BOX = register("minecraft", "white_shulker_box");
    public static final VoxelMaterial ORANGE_SHULKER_BOX = register("minecraft", "orange_shulker_box");
    public static final VoxelMaterial MAGENTA_SHULKER_BOX = register("minecraft", "magenta_shulker_box");
    public static final VoxelMaterial LIGHT_BLUE_SHULKER_BOX = register("minecraft", "light_blue_shulker_box");
    public static final VoxelMaterial YELLOW_SHULKER_BOX = register("minecraft", "yellow_shulker_box");
    public static final VoxelMaterial LIME_SHULKER_BOX = register("minecraft", "lime_shulker_box");
    public static final VoxelMaterial PINK_SHULKER_BOX = register("minecraft", "pink_shulker_box");
    public static final VoxelMaterial GRAY_SHULKER_BOX = register("minecraft", "gray_shulker_box");
    public static final VoxelMaterial LIGHT_GRAY_SHULKER_BOX = register("minecraft", "light_gray_shulker_box");
    public static final VoxelMaterial CYAN_SHULKER_BOX = register("minecraft", "cyan_shulker_box");
    public static final VoxelMaterial PURPLE_SHULKER_BOX = register("minecraft", "purple_shulker_box");
    public static final VoxelMaterial BLUE_SHULKER_BOX = register("minecraft", "blue_shulker_box");
    public static final VoxelMaterial BROWN_SHULKER_BOX = register("minecraft", "brown_shulker_box");
    public static final VoxelMaterial GREEN_SHULKER_BOX = register("minecraft", "green_shulker_box");
    public static final VoxelMaterial RED_SHULKER_BOX = register("minecraft", "red_shulker_box");
    public static final VoxelMaterial BLACK_SHULKER_BOX = register("minecraft", "black_shulker_box");
    public static final VoxelMaterial WHITE_GLAZED_TERRACOTTA = register("minecraft", "white_glazed_terracotta");
    public static final VoxelMaterial ORANGE_GLAZED_TERRACOTTA = register("minecraft", "orange_glazed_terracotta");
    public static final VoxelMaterial MAGENTA_GLAZED_TERRACOTTA = register("minecraft", "magenta_glazed_terracotta");
    public static final VoxelMaterial LIGHT_BLUE_GLAZED_TERRACOTTA = register("minecraft", "light_blue_glazed_terracotta");
    public static final VoxelMaterial YELLOW_GLAZED_TERRACOTTA = register("minecraft", "yellow_glazed_terracotta");
    public static final VoxelMaterial LIME_GLAZED_TERRACOTTA = register("minecraft", "lime_glazed_terracotta");
    public static final VoxelMaterial PINK_GLAZED_TERRACOTTA = register("minecraft", "pink_glazed_terracotta");
    public static final VoxelMaterial GRAY_GLAZED_TERRACOTTA = register("minecraft", "gray_glazed_terracotta");
    public static final VoxelMaterial LIGHT_GRAY_GLAZED_TERRACOTTA = register("minecraft", "light_gray_glazed_terracotta");
    public static final VoxelMaterial CYAN_GLAZED_TERRACOTTA = register("minecraft", "cyan_glazed_terracotta");
    public static final VoxelMaterial PURPLE_GLAZED_TERRACOTTA = register("minecraft", "purple_glazed_terracotta");
    public static final VoxelMaterial BLUE_GLAZED_TERRACOTTA = register("minecraft", "blue_glazed_terracotta");
    public static final VoxelMaterial BROWN_GLAZED_TERRACOTTA = register("minecraft", "brown_glazed_terracotta");
    public static final VoxelMaterial GREEN_GLAZED_TERRACOTTA = register("minecraft", "green_glazed_terracotta");
    public static final VoxelMaterial RED_GLAZED_TERRACOTTA = register("minecraft", "red_glazed_terracotta");
    public static final VoxelMaterial BLACK_GLAZED_TERRACOTTA = register("minecraft", "black_glazed_terracotta");
    public static final VoxelMaterial WHITE_CONCRETE = register("minecraft", "white_concrete");
    public static final VoxelMaterial ORANGE_CONCRETE = register("minecraft", "orange_concrete");
    public static final VoxelMaterial MAGENTA_CONCRETE = register("minecraft", "magenta_concrete");
    public static final VoxelMaterial LIGHT_BLUE_CONCRETE = register("minecraft", "light_blue_concrete");
    public static final VoxelMaterial YELLOW_CONCRETE = register("minecraft", "yellow_concrete");
    public static final VoxelMaterial LIME_CONCRETE = register("minecraft", "lime_concrete");
    public static final VoxelMaterial PINK_CONCRETE = register("minecraft", "pink_concrete");
    public static final VoxelMaterial GRAY_CONCRETE = register("minecraft", "gray_concrete");
    public static final VoxelMaterial LIGHT_GRAY_CONCRETE = register("minecraft", "light_gray_concrete");
    public static final VoxelMaterial CYAN_CONCRETE = register("minecraft", "cyan_concrete");
    public static final VoxelMaterial PURPLE_CONCRETE = register("minecraft", "purple_concrete");
    public static final VoxelMaterial BLUE_CONCRETE = register("minecraft", "blue_concrete");
    public static final VoxelMaterial BROWN_CONCRETE = register("minecraft", "brown_concrete");
    public static final VoxelMaterial GREEN_CONCRETE = register("minecraft", "green_concrete");
    public static final VoxelMaterial RED_CONCRETE = register("minecraft", "red_concrete");
    public static final VoxelMaterial BLACK_CONCRETE = register("minecraft", "black_concrete");
    public static final VoxelMaterial WHITE_CONCRETE_POWDER = register("minecraft", "white_concrete_powder");
    public static final VoxelMaterial ORANGE_CONCRETE_POWDER = register("minecraft", "orange_concrete_powder");
    public static final VoxelMaterial MAGENTA_CONCRETE_POWDER = register("minecraft", "magenta_concrete_powder");
    public static final VoxelMaterial LIGHT_BLUE_CONCRETE_POWDER = register("minecraft", "light_blue_concrete_powder");
    public static final VoxelMaterial YELLOW_CONCRETE_POWDER = register("minecraft", "yellow_concrete_powder");
    public static final VoxelMaterial LIME_CONCRETE_POWDER = register("minecraft", "lime_concrete_powder");
    public static final VoxelMaterial PINK_CONCRETE_POWDER = register("minecraft", "pink_concrete_powder");
    public static final VoxelMaterial GRAY_CONCRETE_POWDER = register("minecraft", "gray_concrete_powder");
    public static final VoxelMaterial LIGHT_GRAY_CONCRETE_POWDER = register("minecraft", "light_gray_concrete_powder");
    public static final VoxelMaterial CYAN_CONCRETE_POWDER = register("minecraft", "cyan_concrete_powder");
    public static final VoxelMaterial PURPLE_CONCRETE_POWDER = register("minecraft", "purple_concrete_powder");
    public static final VoxelMaterial BLUE_CONCRETE_POWDER = register("minecraft", "blue_concrete_powder");
    public static final VoxelMaterial BROWN_CONCRETE_POWDER = register("minecraft", "brown_concrete_powder");
    public static final VoxelMaterial GREEN_CONCRETE_POWDER = register("minecraft", "green_concrete_powder");
    public static final VoxelMaterial RED_CONCRETE_POWDER = register("minecraft", "red_concrete_powder");
    public static final VoxelMaterial BLACK_CONCRETE_POWDER = register("minecraft", "black_concrete_powder");
    public static final VoxelMaterial KELP = register("minecraft", "kelp", Tags.FALLSOFF);
    public static final VoxelMaterial KELP_PLANT = register("minecraft", "kelp_plant", Tags.FALLSOFF);
    public static final VoxelMaterial DRIED_KELP_BLOCK = register("minecraft", "dried_kelp_block");
    public static final VoxelMaterial TURTLE_EGG = register("minecraft", "turtle_egg", Tags.FALLSOFF);
    public static final VoxelMaterial DEAD_TUBE_CORAL_BLOCK = register("minecraft", "dead_tube_coral_block");
    public static final VoxelMaterial DEAD_BRAIN_CORAL_BLOCK = register("minecraft", "dead_brain_coral_block");
    public static final VoxelMaterial DEAD_BUBBLE_CORAL_BLOCK = register("minecraft", "dead_bubble_coral_block");
    public static final VoxelMaterial DEAD_FIRE_CORAL_BLOCK = register("minecraft", "dead_fire_coral_block");
    public static final VoxelMaterial DEAD_HORN_CORAL_BLOCK = register("minecraft", "dead_horn_coral_block");
    public static final VoxelMaterial TUBE_CORAL_BLOCK = register("minecraft", "tube_coral_block");
    public static final VoxelMaterial BRAIN_CORAL_BLOCK = register("minecraft", "brain_coral_block");
    public static final VoxelMaterial BUBBLE_CORAL_BLOCK = register("minecraft", "bubble_coral_block");
    public static final VoxelMaterial FIRE_CORAL_BLOCK = register("minecraft", "fire_coral_block");
    public static final VoxelMaterial HORN_CORAL_BLOCK = register("minecraft", "horn_coral_block");
    public static final VoxelMaterial DEAD_TUBE_CORAL = register("minecraft", "dead_tube_coral", Tags.FALLSOFF);
    public static final VoxelMaterial DEAD_BRAIN_CORAL = register("minecraft", "dead_brain_coral", Tags.FALLSOFF);
    public static final VoxelMaterial DEAD_BUBBLE_CORAL = register("minecraft", "dead_bubble_coral", Tags.FALLSOFF);
    public static final VoxelMaterial DEAD_FIRE_CORAL = register("minecraft", "dead_fire_coral", Tags.FALLSOFF);
    public static final VoxelMaterial DEAD_HORN_CORAL = register("minecraft", "dead_horn_coral", Tags.FALLSOFF);
    public static final VoxelMaterial TUBE_CORAL = register("minecraft", "tube_coral", Tags.FALLSOFF);
    public static final VoxelMaterial BRAIN_CORAL = register("minecraft", "brain_coral", Tags.FALLSOFF);
    public static final VoxelMaterial BUBBLE_CORAL = register("minecraft", "bubble_coral", Tags.FALLSOFF);
    public static final VoxelMaterial FIRE_CORAL = register("minecraft", "fire_coral", Tags.FALLSOFF);
    public static final VoxelMaterial HORN_CORAL = register("minecraft", "horn_coral", Tags.FALLSOFF);
    public static final VoxelMaterial DEAD_TUBE_CORAL_FAN = register("minecraft", "dead_tube_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterial DEAD_BRAIN_CORAL_FAN = register("minecraft", "dead_brain_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterial DEAD_BUBBLE_CORAL_FAN = register("minecraft", "dead_bubble_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterial DEAD_FIRE_CORAL_FAN = register("minecraft", "dead_fire_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterial DEAD_HORN_CORAL_FAN = register("minecraft", "dead_horn_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterial TUBE_CORAL_FAN = register("minecraft", "tube_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterial BRAIN_CORAL_FAN = register("minecraft", "brain_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterial BUBBLE_CORAL_FAN = register("minecraft", "bubble_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterial FIRE_CORAL_FAN = register("minecraft", "fire_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterial HORN_CORAL_FAN = register("minecraft", "horn_coral_fan", Tags.FALLSOFF);
    public static final VoxelMaterial DEAD_TUBE_CORAL_WALL_FAN = register("minecraft", "dead_tube_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterial DEAD_BRAIN_CORAL_WALL_FAN = register("minecraft", "dead_brain_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterial DEAD_BUBBLE_CORAL_WALL_FAN = register("minecraft", "dead_bubble_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterial DEAD_FIRE_CORAL_WALL_FAN = register("minecraft", "dead_fire_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterial DEAD_HORN_CORAL_WALL_FAN = register("minecraft", "dead_horn_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterial TUBE_CORAL_WALL_FAN = register("minecraft", "tube_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterial BRAIN_CORAL_WALL_FAN = register("minecraft", "brain_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterial BUBBLE_CORAL_WALL_FAN = register("minecraft", "bubble_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterial FIRE_CORAL_WALL_FAN = register("minecraft", "fire_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterial HORN_CORAL_WALL_FAN = register("minecraft", "horn_coral_wall_fan", Tags.FALLSOFF);
    public static final VoxelMaterial SEA_PICKLE = register("minecraft", "sea_pickle", Tags.FALLSOFF);
    public static final VoxelMaterial BLUE_ICE = register("minecraft", "blue_ice");
    public static final VoxelMaterial CONDUIT = register("minecraft", "conduit");
    public static final VoxelMaterial BAMBOO_SAPLING = register("minecraft", "bamboo_sapling", Tags.FALLSOFF);
    public static final VoxelMaterial BAMBOO = register("minecraft", "bamboo", Tags.FALLSOFF);
    public static final VoxelMaterial POTTED_BAMBOO = register("minecraft", "potted_bamboo");
    public static final VoxelMaterial VOID_AIR = register("minecraft", "void_air", Tags.AIR);
    public static final VoxelMaterial CAVE_AIR = register("minecraft", "cave_air", Tags.AIR);
    public static final VoxelMaterial BUBBLE_COLUMN = register("minecraft", "bubble_column", Tags.FLUIDS);
    public static final VoxelMaterial POLISHED_GRANITE_STAIRS = register("minecraft", "polished_granite_stairs");
    public static final VoxelMaterial SMOOTH_RED_SANDSTONE_STAIRS = register("minecraft", "smooth_red_sandstone_stairs");
    public static final VoxelMaterial MOSSY_STONE_BRICK_STAIRS = register("minecraft", "mossy_stone_brick_stairs");
    public static final VoxelMaterial POLISHED_DIORITE_STAIRS = register("minecraft", "polished_diorite_stairs");
    public static final VoxelMaterial MOSSY_COBBLESTONE_STAIRS = register("minecraft", "mossy_cobblestone_stairs");
    public static final VoxelMaterial END_STONE_BRICK_STAIRS = register("minecraft", "end_stone_brick_stairs");
    public static final VoxelMaterial STONE_STAIRS = register("minecraft", "stone_stairs");
    public static final VoxelMaterial SMOOTH_SANDSTONE_STAIRS = register("minecraft", "smooth_sandstone_stairs");
    public static final VoxelMaterial SMOOTH_QUARTZ_STAIRS = register("minecraft", "smooth_quartz_stairs");
    public static final VoxelMaterial GRANITE_STAIRS = register("minecraft", "granite_stairs");
    public static final VoxelMaterial ANDESITE_STAIRS = register("minecraft", "andesite_stairs");
    public static final VoxelMaterial RED_NETHER_BRICK_STAIRS = register("minecraft", "red_nether_brick_stairs");
    public static final VoxelMaterial POLISHED_ANDESITE_STAIRS = register("minecraft", "polished_andesite_stairs");
    public static final VoxelMaterial DIORITE_STAIRS = register("minecraft", "diorite_stairs");
    public static final VoxelMaterial POLISHED_GRANITE_SLAB = register("minecraft", "polished_granite_slab");
    public static final VoxelMaterial SMOOTH_RED_SANDSTONE_SLAB = register("minecraft", "smooth_red_sandstone_slab");
    public static final VoxelMaterial MOSSY_STONE_BRICK_SLAB = register("minecraft", "mossy_stone_brick_slab");
    public static final VoxelMaterial POLISHED_DIORITE_SLAB = register("minecraft", "polished_diorite_slab");
    public static final VoxelMaterial MOSSY_COBBLESTONE_SLAB = register("minecraft", "mossy_cobblestone_slab");
    public static final VoxelMaterial END_STONE_BRICK_SLAB = register("minecraft", "end_stone_brick_slab");
    public static final VoxelMaterial SMOOTH_SANDSTONE_SLAB = register("minecraft", "smooth_sandstone_slab");
    public static final VoxelMaterial SMOOTH_QUARTZ_SLAB = register("minecraft", "smooth_quartz_slab");
    public static final VoxelMaterial GRANITE_SLAB = register("minecraft", "granite_slab");
    public static final VoxelMaterial ANDESITE_SLAB = register("minecraft", "andesite_slab");
    public static final VoxelMaterial RED_NETHER_BRICK_SLAB = register("minecraft", "red_nether_brick_slab");
    public static final VoxelMaterial POLISHED_ANDESITE_SLAB = register("minecraft", "polished_andesite_slab");
    public static final VoxelMaterial DIORITE_SLAB = register("minecraft", "diorite_slab");
    public static final VoxelMaterial BRICK_WALL = register("minecraft", "brick_wall");
    public static final VoxelMaterial PRISMARINE_WALL = register("minecraft", "prismarine_wall");
    public static final VoxelMaterial RED_SANDSTONE_WALL = register("minecraft", "red_sandstone_wall");
    public static final VoxelMaterial MOSSY_STONE_BRICK_WALL = register("minecraft", "mossy_stone_brick_wall");
    public static final VoxelMaterial GRANITE_WALL = register("minecraft", "granite_wall");
    public static final VoxelMaterial STONE_BRICK_WALL = register("minecraft", "stone_brick_wall");
    public static final VoxelMaterial NETHER_BRICK_WALL = register("minecraft", "nether_brick_wall");
    public static final VoxelMaterial ANDESITE_WALL = register("minecraft", "andesite_wall");
    public static final VoxelMaterial RED_NETHER_BRICK_WALL = register("minecraft", "red_nether_brick_wall");
    public static final VoxelMaterial SANDSTONE_WALL = register("minecraft", "sandstone_wall");
    public static final VoxelMaterial END_STONE_BRICK_WALL = register("minecraft", "end_stone_brick_wall");
    public static final VoxelMaterial DIORITE_WALL = register("minecraft", "diorite_wall");
    public static final VoxelMaterial SCAFFOLDING = register("minecraft", "scaffolding");
    public static final VoxelMaterial LOOM = register("minecraft", "loom");
    public static final VoxelMaterial BARREL = register("minecraft", "barrel");
    public static final VoxelMaterial SMOKER = register("minecraft", "smoker");
    public static final VoxelMaterial BLAST_FURNACE = register("minecraft", "blast_furnace");
    public static final VoxelMaterial CARTOGRAPHY_TABLE = register("minecraft", "cartography_table");
    public static final VoxelMaterial FLETCHING_TABLE = register("minecraft", "fletching_table");
    public static final VoxelMaterial GRINDSTONE = register("minecraft", "grindstone");
    public static final VoxelMaterial LECTERN = register("minecraft", "lectern");
    public static final VoxelMaterial SMITHING_TABLE = register("minecraft", "smithing_table");
    public static final VoxelMaterial STONECUTTER = register("minecraft", "stonecutter");
    public static final VoxelMaterial BELL = register("minecraft", "bell");
    public static final VoxelMaterial LANTERN = register("minecraft", "lantern");
    public static final VoxelMaterial SOUL_LANTERN = register("minecraft", "soul_lantern");
    public static final VoxelMaterial CAMPFIRE = register("minecraft", "campfire");
    public static final VoxelMaterial SOUL_CAMPFIRE = register("minecraft", "soul_campfire");
    public static final VoxelMaterial SWEET_BERRY_BUSH = register("minecraft", "sweet_berry_bush", Tags.FALLSOFF);
    public static final VoxelMaterial WARPED_STEM = register("minecraft", "warped_stem");
    public static final VoxelMaterial STRIPPED_WARPED_STEM = register("minecraft", "stripped_warped_stem");
    public static final VoxelMaterial WARPED_HYPHAE = register("minecraft", "warped_hyphae");
    public static final VoxelMaterial STRIPPED_WARPED_HYPHAE = register("minecraft", "stripped_warped_hyphae");
    public static final VoxelMaterial WARPED_NYLIUM = register("minecraft", "warped_nylium");
    public static final VoxelMaterial WARPED_FUNGUS = register("minecraft", "warped_fungus", Tags.FALLSOFF);
    public static final VoxelMaterial WARPED_WART_BLOCK = register("minecraft", "warped_wart_block");
    public static final VoxelMaterial WARPED_ROOTS = register("minecraft", "warped_roots", Tags.FALLSOFF);
    public static final VoxelMaterial NETHER_SPROUTS = register("minecraft", "nether_sprouts", Tags.FALLSOFF);
    public static final VoxelMaterial CRIMSON_STEM = register("minecraft", "crimson_stem");
    public static final VoxelMaterial STRIPPED_CRIMSON_STEM = register("minecraft", "stripped_crimson_stem");
    public static final VoxelMaterial CRIMSON_HYPHAE = register("minecraft", "crimson_hyphae");
    public static final VoxelMaterial STRIPPED_CRIMSON_HYPHAE = register("minecraft", "stripped_crimson_hyphae");
    public static final VoxelMaterial CRIMSON_NYLIUM = register("minecraft", "crimson_nylium");
    public static final VoxelMaterial CRIMSON_FUNGUS = register("minecraft", "crimson_fungus", Tags.FALLSOFF);
    public static final VoxelMaterial SHROOMLIGHT = register("minecraft", "shroomlight");
    public static final VoxelMaterial WEEPING_VINES = register("minecraft", "weeping_vines", Tags.FALLSOFF);
    public static final VoxelMaterial WEEPING_VINES_PLANT = register("minecraft", "weeping_vines_plant", Tags.FALLSOFF);
    public static final VoxelMaterial TWISTING_VINES = register("minecraft", "twisting_vines", Tags.FALLSOFF);
    public static final VoxelMaterial TWISTING_VINES_PLANT = register("minecraft", "twisting_vines_plant", Tags.FALLSOFF);
    public static final VoxelMaterial CRIMSON_ROOTS = register("minecraft", "crimson_roots", Tags.FALLSOFF);
    public static final VoxelMaterial CRIMSON_PLANKS = register("minecraft", "crimson_planks");
    public static final VoxelMaterial WARPED_PLANKS = register("minecraft", "warped_planks");
    public static final VoxelMaterial CRIMSON_SLAB = register("minecraft", "crimson_slab");
    public static final VoxelMaterial WARPED_SLAB = register("minecraft", "warped_slab");
    public static final VoxelMaterial CRIMSON_PRESSURE_PLATE = register("minecraft", "crimson_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterial WARPED_PRESSURE_PLATE = register("minecraft", "warped_pressure_plate", Tags.FALLSOFF);
    public static final VoxelMaterial CRIMSON_FENCE = register("minecraft", "crimson_fence");
    public static final VoxelMaterial WARPED_FENCE = register("minecraft", "warped_fence");
    public static final VoxelMaterial CRIMSON_TRAPDOOR = register("minecraft", "crimson_trapdoor");
    public static final VoxelMaterial WARPED_TRAPDOOR = register("minecraft", "warped_trapdoor");
    public static final VoxelMaterial CRIMSON_FENCE_GATE = register("minecraft", "crimson_fence_gate");
    public static final VoxelMaterial WARPED_FENCE_GATE = register("minecraft", "warped_fence_gate");
    public static final VoxelMaterial CRIMSON_STAIRS = register("minecraft", "crimson_stairs");
    public static final VoxelMaterial WARPED_STAIRS = register("minecraft", "warped_stairs");
    public static final VoxelMaterial CRIMSON_BUTTON = register("minecraft", "crimson_button", Tags.FALLSOFF);
    public static final VoxelMaterial WARPED_BUTTON = register("minecraft", "warped_button", Tags.FALLSOFF);
    public static final VoxelMaterial CRIMSON_DOOR = register("minecraft", "crimson_door", Tags.FALLSOFF);
    public static final VoxelMaterial WARPED_DOOR = register("minecraft", "warped_door", Tags.FALLSOFF);
    public static final VoxelMaterial CRIMSON_SIGN = register("minecraft", "crimson_sign", Tags.FALLSOFF);
    public static final VoxelMaterial WARPED_SIGN = register("minecraft", "warped_sign", Tags.FALLSOFF);
    public static final VoxelMaterial CRIMSON_WALL_SIGN = register("minecraft", "crimson_wall_sign", Tags.FALLSOFF);
    public static final VoxelMaterial WARPED_WALL_SIGN = register("minecraft", "warped_wall_sign", Tags.FALLSOFF);
    public static final VoxelMaterial STRUCTURE_BLOCK = register("minecraft", "structure_block");
    public static final VoxelMaterial JIGSAW = register("minecraft", "jigsaw");
    public static final VoxelMaterial COMPOSTER = register("minecraft", "composter");
    public static final VoxelMaterial TARGET = register("minecraft", "target");
    public static final VoxelMaterial BEE_NEST = register("minecraft", "bee_nest");
    public static final VoxelMaterial BEEHIVE = register("minecraft", "beehive");
    public static final VoxelMaterial HONEY_BLOCK = register("minecraft", "honey_block");
    public static final VoxelMaterial HONEYCOMB_BLOCK = register("minecraft", "honeycomb_block");
    public static final VoxelMaterial NETHERITE_BLOCK = register("minecraft", "netherite_block");
    public static final VoxelMaterial ANCIENT_DEBRIS = register("minecraft", "ancient_debris");
    public static final VoxelMaterial CRYING_OBSIDIAN = register("minecraft", "crying_obsidian");
    public static final VoxelMaterial RESPAWN_ANCHOR = register("minecraft", "respawn_anchor");
    public static final VoxelMaterial POTTED_CRIMSON_FUNGUS = register("minecraft", "potted_crimson_fungus");
    public static final VoxelMaterial POTTED_WARPED_FUNGUS = register("minecraft", "potted_warped_fungus");
    public static final VoxelMaterial POTTED_CRIMSON_ROOTS = register("minecraft", "potted_crimson_roots");
    public static final VoxelMaterial POTTED_WARPED_ROOTS = register("minecraft", "potted_warped_roots");
    public static final VoxelMaterial LODESTONE = register("minecraft", "lodestone");
    public static final VoxelMaterial BLACKSTONE = register("minecraft", "blackstone");
    public static final VoxelMaterial BLACKSTONE_STAIRS = register("minecraft", "blackstone_stairs");
    public static final VoxelMaterial BLACKSTONE_WALL = register("minecraft", "blackstone_wall");
    public static final VoxelMaterial BLACKSTONE_SLAB = register("minecraft", "blackstone_slab");
    public static final VoxelMaterial POLISHED_BLACKSTONE = register("minecraft", "polished_blackstone");
    public static final VoxelMaterial POLISHED_BLACKSTONE_BRICKS = register("minecraft", "polished_blackstone_bricks");
    public static final VoxelMaterial CRACKED_POLISHED_BLACKSTONE_BRICKS = register("minecraft", "cracked_polished_blackstone_bricks");
    public static final VoxelMaterial CHISELED_POLISHED_BLACKSTONE = register("minecraft", "chiseled_polished_blackstone");
    public static final VoxelMaterial POLISHED_BLACKSTONE_BRICK_SLAB = register("minecraft", "polished_blackstone_brick_slab");
    public static final VoxelMaterial POLISHED_BLACKSTONE_BRICK_STAIRS = register("minecraft", "polished_blackstone_brick_stairs");
    public static final VoxelMaterial POLISHED_BLACKSTONE_BRICK_WALL = register("minecraft", "polished_blackstone_brick_wall");
    public static final VoxelMaterial GILDED_BLACKSTONE = register("minecraft", "gilded_blackstone");
    public static final VoxelMaterial POLISHED_BLACKSTONE_STAIRS = register("minecraft", "polished_blackstone_stairs");
    public static final VoxelMaterial POLISHED_BLACKSTONE_SLAB = register("minecraft", "polished_blackstone_slab");
    public static final VoxelMaterial POLISHED_BLACKSTONE_PRESSURE_PLATE = register("minecraft", "polished_blackstone_pressure_plate");
    public static final VoxelMaterial POLISHED_BLACKSTONE_BUTTON = register("minecraft", "polished_blackstone_button");
    public static final VoxelMaterial POLISHED_BLACKSTONE_WALL = register("minecraft", "polished_blackstone_wall");
    public static final VoxelMaterial CHISELED_NETHER_BRICKS = register("minecraft", "chiseled_nether_bricks");
    public static final VoxelMaterial CRACKED_NETHER_BRICKS = register("minecraft", "cracked_nether_bricks");
    public static final VoxelMaterial QUARTZ_BRICKS = register("minecraft", "quartz_bricks");
    public static final VoxelMaterial CANDLE = register("minecraft", "candle", V1_17);
    public static final VoxelMaterial WHITE_CANDLE = register("minecraft", "white_candle", V1_17);
    public static final VoxelMaterial ORANGE_CANDLE = register("minecraft", "orange_candle", V1_17);
    public static final VoxelMaterial MAGENTA_CANDLE = register("minecraft", "magenta_candle", V1_17);
    public static final VoxelMaterial LIGHT_BLUE_CANDLE = register("minecraft", "light_blue_candle", V1_17);
    public static final VoxelMaterial YELLOW_CANDLE = register("minecraft", "yellow_candle", V1_17);
    public static final VoxelMaterial LIME_CANDLE = register("minecraft", "lime_candle", V1_17);
    public static final VoxelMaterial PINK_CANDLE = register("minecraft", "pink_candle", V1_17);
    public static final VoxelMaterial GRAY_CANDLE = register("minecraft", "gray_candle", V1_17);
    public static final VoxelMaterial LIGHT_GRAY_CANDLE = register("minecraft", "light_gray_candle", V1_17);
    public static final VoxelMaterial CYAN_CANDLE = register("minecraft", "cyan_candle", V1_17);
    public static final VoxelMaterial PURPLE_CANDLE = register("minecraft", "purple_candle", V1_17);
    public static final VoxelMaterial BLUE_CANDLE = register("minecraft", "blue_candle", V1_17);
    public static final VoxelMaterial BROWN_CANDLE = register("minecraft", "brown_candle", V1_17);
    public static final VoxelMaterial GREEN_CANDLE = register("minecraft", "green_candle", V1_17);
    public static final VoxelMaterial RED_CANDLE = register("minecraft", "red_candle", V1_17);
    public static final VoxelMaterial BLACK_CANDLE = register("minecraft", "black_candle", V1_17);
    public static final VoxelMaterial CANDLE_CAKE = register("minecraft", "candle_cake", V1_17);
    public static final VoxelMaterial WHITE_CANDLE_CAKE = register("minecraft", "white_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial ORANGE_CANDLE_CAKE = register("minecraft", "orange_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial MAGENTA_CANDLE_CAKE = register("minecraft", "magenta_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial LIGHT_BLUE_CANDLE_CAKE = register("minecraft", "light_blue_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial YELLOW_CANDLE_CAKE = register("minecraft", "yellow_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial LIME_CANDLE_CAKE = register("minecraft", "lime_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial PINK_CANDLE_CAKE = register("minecraft", "pink_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial GRAY_CANDLE_CAKE = register("minecraft", "gray_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial LIGHT_GRAY_CANDLE_CAKE = register("minecraft", "light_gray_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial CYAN_CANDLE_CAKE = register("minecraft", "cyan_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial PURPLE_CANDLE_CAKE = register("minecraft", "purple_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial BLUE_CANDLE_CAKE = register("minecraft", "blue_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial BROWN_CANDLE_CAKE = register("minecraft", "brown_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial GREEN_CANDLE_CAKE = register("minecraft", "green_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial RED_CANDLE_CAKE = register("minecraft", "red_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial BLACK_CANDLE_CAKE = register("minecraft", "black_candle_cake", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial AMETHYST_BLOCK = register("minecraft", "amethyst_block", V1_17);
    public static final VoxelMaterial BUDDING_AMETHYST = register("minecraft", "budding_amethyst", V1_17);
    public static final VoxelMaterial AMETHYST_CLUSTER = register("minecraft", "amethyst_cluster", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial LARGE_AMETHYST_BUD = register("minecraft", "large_amethyst_bud", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial MEDIUM_AMETHYST_BUD = register("minecraft", "medium_amethyst_bud", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial SMALL_AMETHYST_BUD = register("minecraft", "small_amethyst_bud", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial TUFF = register("minecraft", "tuff", V1_17);
    public static final VoxelMaterial CALCITE = register("minecraft", "calcite", V1_17);
    public static final VoxelMaterial TINTED_GLASS = register("minecraft", "tinted_glass", V1_17);
    public static final VoxelMaterial POWDER_SNOW = register("minecraft", "powder_snow", V1_17);
    public static final VoxelMaterial SCULK_SENSOR = register("minecraft", "sculk_sensor", V1_17);
    public static final VoxelMaterial OXIDIZED_COPPER = register("minecraft", "oxidized_copper", V1_17);
    public static final VoxelMaterial WEATHERED_COPPER = register("minecraft", "weathered_copper", V1_17);
    public static final VoxelMaterial EXPOSED_COPPER = register("minecraft", "exposed_copper", V1_17);
    public static final VoxelMaterial COPPER_BLOCK = register("minecraft", "copper_block", V1_17);
    public static final VoxelMaterial COPPER_ORE = register("minecraft", "copper_ore", V1_17);
    public static final VoxelMaterial DEEPSLATE_COPPER_ORE = register("minecraft", "deepslate_copper_ore", V1_17);
    public static final VoxelMaterial OXIDIZED_CUT_COPPER = register("minecraft", "oxidized_cut_copper", V1_17);
    public static final VoxelMaterial WEATHERED_CUT_COPPER = register("minecraft", "weathered_cut_copper", V1_17);
    public static final VoxelMaterial EXPOSED_CUT_COPPER = register("minecraft", "exposed_cut_copper", V1_17);
    public static final VoxelMaterial CUT_COPPER = register("minecraft", "cut_copper", V1_17);
    public static final VoxelMaterial OXIDIZED_CUT_COPPER_STAIRS = register("minecraft", "oxidized_cut_copper_stairs", V1_17);
    public static final VoxelMaterial WEATHERED_CUT_COPPER_STAIRS = register("minecraft", "weathered_cut_copper_stairs", V1_17);
    public static final VoxelMaterial EXPOSED_CUT_COPPER_STAIRS = register("minecraft", "exposed_cut_copper_stairs", V1_17);
    public static final VoxelMaterial CUT_COPPER_STAIRS = register("minecraft", "cut_copper_stairs", V1_17);
    public static final VoxelMaterial OXIDIZED_CUT_COPPER_SLAB = register("minecraft", "oxidized_cut_copper_slab", V1_17);
    public static final VoxelMaterial WEATHERED_CUT_COPPER_SLAB = register("minecraft", "weathered_cut_copper_slab", V1_17);
    public static final VoxelMaterial EXPOSED_CUT_COPPER_SLAB = register("minecraft", "exposed_cut_copper_slab", V1_17);
    public static final VoxelMaterial CUT_COPPER_SLAB = register("minecraft", "cut_copper_slab", V1_17);
    public static final VoxelMaterial WAXED_COPPER_BLOCK = register("minecraft", "waxed_copper_block", V1_17);
    public static final VoxelMaterial WAXED_WEATHERED_COPPER = register("minecraft", "waxed_weathered_copper", V1_17);
    public static final VoxelMaterial WAXED_EXPOSED_COPPER = register("minecraft", "waxed_exposed_copper", V1_17);
    public static final VoxelMaterial WAXED_OXIDIZED_COPPER = register("minecraft", "waxed_oxidized_copper", V1_17);
    public static final VoxelMaterial WAXED_OXIDIZED_CUT_COPPER = register("minecraft", "waxed_oxidized_cut_copper", V1_17);
    public static final VoxelMaterial WAXED_WEATHERED_CUT_COPPER = register("minecraft", "waxed_weathered_cut_copper", V1_17);
    public static final VoxelMaterial WAXED_EXPOSED_CUT_COPPER = register("minecraft", "waxed_exposed_cut_copper", V1_17);
    public static final VoxelMaterial WAXED_CUT_COPPER = register("minecraft", "waxed_cut_copper", V1_17);
    public static final VoxelMaterial WAXED_OXIDIZED_CUT_COPPER_STAIRS = register("minecraft", "waxed_oxidized_cut_copper_stairs", V1_17);
    public static final VoxelMaterial WAXED_WEATHERED_CUT_COPPER_STAIRS = register("minecraft", "waxed_weathered_cut_copper_stairs", V1_17);
    public static final VoxelMaterial WAXED_EXPOSED_CUT_COPPER_STAIRS = register("minecraft", "waxed_exposed_cut_copper_stairs", V1_17);
    public static final VoxelMaterial WAXED_CUT_COPPER_STAIRS = register("minecraft", "waxed_cut_copper_stairs", V1_17);
    public static final VoxelMaterial WAXED_OXIDIZED_CUT_COPPER_SLAB = register("minecraft", "waxed_oxidized_cut_copper_slab", V1_17);
    public static final VoxelMaterial WAXED_WEATHERED_CUT_COPPER_SLAB = register("minecraft", "waxed_weathered_cut_copper_slab", V1_17);
    public static final VoxelMaterial WAXED_EXPOSED_CUT_COPPER_SLAB = register("minecraft", "waxed_exposed_cut_copper_slab", V1_17);
    public static final VoxelMaterial WAXED_CUT_COPPER_SLAB = register("minecraft", "waxed_cut_copper_slab", V1_17);
    public static final VoxelMaterial LIGHTNING_ROD = register("minecraft", "lightning_rod", V1_17);
    public static final VoxelMaterial POINTED_DRIPSTONE = register("minecraft", "pointed_dripstone", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial DRIPSTONE_BLOCK = register("minecraft", "dripstone_block", V1_17);
    public static final VoxelMaterial CAVE_VINES = register("minecraft", "cave_vines", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial CAVE_VINES_PLANT = register("minecraft", "cave_vines_plant", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial SPORE_BLOSSOM = register("minecraft", "spore_blossom", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial AZALEA = register("minecraft", "azalea", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial FLOWERING_AZALEA = register("minecraft", "flowering_azalea", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial MOSS_CARPET = register("minecraft", "moss_carpet", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial MOSS_BLOCK = register("minecraft", "moss_block", V1_17);
    public static final VoxelMaterial BIG_DRIPLEAF = register("minecraft", "big_dripleaf", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial BIG_DRIPLEAF_STEM = register("minecraft", "big_dripleaf_stem", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial SMALL_DRIPLEAF = register("minecraft", "small_dripleaf", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial HANGING_ROOTS = register("minecraft", "hanging_roots", V1_17, Tags.FALLSOFF);
    public static final VoxelMaterial ROOTED_DIRT = register("minecraft", "rooted_dirt", V1_17);
    public static final VoxelMaterial DEEPSLATE = register("minecraft", "deepslate", V1_17);
    public static final VoxelMaterial COBBLED_DEEPSLATE = register("minecraft", "cobbled_deepslate", V1_17);
    public static final VoxelMaterial COBBLED_DEEPSLATE_STAIRS = register("minecraft", "cobbled_deepslate_stairs", V1_17);
    public static final VoxelMaterial COBBLED_DEEPSLATE_SLAB = register("minecraft", "cobbled_deepslate_slab", V1_17);
    public static final VoxelMaterial COBBLED_DEEPSLATE_WALL = register("minecraft", "cobbled_deepslate_wall", V1_17);
    public static final VoxelMaterial POLISHED_DEEPSLATE = register("minecraft", "polished_deepslate", V1_17);
    public static final VoxelMaterial POLISHED_DEEPSLATE_STAIRS = register("minecraft", "polished_deepslate_stairs", V1_17);
    public static final VoxelMaterial POLISHED_DEEPSLATE_SLAB = register("minecraft", "polished_deepslate_slab", V1_17);
    public static final VoxelMaterial POLISHED_DEEPSLATE_WALL = register("minecraft", "polished_deepslate_wall", V1_17);
    public static final VoxelMaterial DEEPSLATE_TILES = register("minecraft", "deepslate_tiles", V1_17);
    public static final VoxelMaterial DEEPSLATE_TILE_STAIRS = register("minecraft", "deepslate_tile_stairs", V1_17);
    public static final VoxelMaterial DEEPSLATE_TILE_SLAB = register("minecraft", "deepslate_tile_slab", V1_17);
    public static final VoxelMaterial DEEPSLATE_TILE_WALL = register("minecraft", "deepslate_tile_wall", V1_17);
    public static final VoxelMaterial DEEPSLATE_BRICKS = register("minecraft", "deepslate_bricks", V1_17);
    public static final VoxelMaterial DEEPSLATE_BRICK_STAIRS = register("minecraft", "deepslate_brick_stairs", V1_17);
    public static final VoxelMaterial DEEPSLATE_BRICK_SLAB = register("minecraft", "deepslate_brick_slab", V1_17);
    public static final VoxelMaterial DEEPSLATE_BRICK_WALL = register("minecraft", "deepslate_brick_wall", V1_17);
    public static final VoxelMaterial CHISELED_DEEPSLATE = register("minecraft", "chiseled_deepslate", V1_17);
    public static final VoxelMaterial CRACKED_DEEPSLATE_BRICKS = register("minecraft", "cracked_deepslate_bricks", V1_17);
    public static final VoxelMaterial CRACKED_DEEPSLATE_TILES = register("minecraft", "cracked_deepslate_tiles", V1_17);
    public static final VoxelMaterial INFESTED_DEEPSLATE = register("minecraft", "infested_deepslate", V1_17);
    public static final VoxelMaterial SMOOTH_BASALT = register("minecraft", "smooth_basalt", V1_17);
    public static final VoxelMaterial RAW_IRON_BLOCK = register("minecraft", "raw_iron_block", V1_17);
    public static final VoxelMaterial RAW_COPPER_BLOCK = register("minecraft", "raw_copper_block", V1_17);
    public static final VoxelMaterial RAW_GOLD_BLOCK = register("minecraft", "raw_gold_block", V1_17);
    public static final VoxelMaterial POTTED_AZALEA = register("minecraft", "potted_azalea_bush", V1_17);
    public static final VoxelMaterial POTTED_FLOWERING_AZALEA = register("minecraft", "potted_flowering_azalea_bush", V1_17);
    public static final VoxelMaterial FROGSPAWN = register("minecraft", "frogspawn", V1_19, Tags.FALLSOFF);
    public static final VoxelMaterial MANGROVE_BUTTON = register("minecraft", "mangrove_button", V1_19, Tags.FALLSOFF);
    public static final VoxelMaterial MANGROVE_DOOR = register("minecraft", "mangrove_door", V1_19, Tags.FALLSOFF);
    public static final VoxelMaterial MANGROVE_FENCE = register("minecraft", "mangrove_fence", V1_19);
    public static final VoxelMaterial MANGROVE_FENCE_GATE = register("minecraft", "mangrove_fence_gate", V1_19);
    public static final VoxelMaterial MANGROVE_LEAVES = register("minecraft", "mangrove_leaves", V1_19);
    public static final VoxelMaterial MANGROVE_LOG = register("minecraft", "mangrove_log", V1_19);
    public static final VoxelMaterial MANGROVE_PLANKS = register("minecraft", "mangrove_planks", V1_19);
    public static final VoxelMaterial MANGROVE_PRESSURE_PLATE = register("minecraft", "mangrove_pressure_plate", V1_19, Tags.FALLSOFF);
    public static final VoxelMaterial MANGROVE_PROPAGULE = register("minecraft", "mangrove_propagule", V1_19, Tags.FALLSOFF);
    public static final VoxelMaterial MANGROVE_ROOTS = register("minecraft", "mangrove_roots", V1_19);
    public static final VoxelMaterial MANGROVE_SIGN = register("minecraft", "mangrove_sign", V1_19, Tags.FALLSOFF, Tags.FALLSOFF);
    public static final VoxelMaterial MANGROVE_SLAB = register("minecraft", "mangrove_slab", V1_19);
    public static final VoxelMaterial MANGROVE_STAIRS = register("minecraft", "mangrove_stairs", V1_19);
    public static final VoxelMaterial MANGROVE_TRAPDOOR = register("minecraft", "mangrove_trapdoor", V1_19);
    public static final VoxelMaterial MANGROVE_WALL_SIGN = register("minecraft", "mangrove_wall_sign", V1_19, Tags.FALLSOFF);
    public static final VoxelMaterial MANGROVE_WOOD = register("minecraft", "mangrove_wood", V1_19);
    public static final VoxelMaterial MUD = register("minecraft", "mud", V1_19);
    public static final VoxelMaterial MUD_BRICK_SLAB = register("minecraft", "mud_brick_slab", V1_19);
    public static final VoxelMaterial MUD_BRICK_STAIRS = register("minecraft", "mud_brick_stairs", V1_19);
    public static final VoxelMaterial MUD_BRICK_WALL = register("minecraft", "mud_brick_wall", V1_19);
    public static final VoxelMaterial MUD_BRICKS = register("minecraft", "mud_bricks", V1_19);
    public static final VoxelMaterial MUDDY_MANGROVE_ROOTS = register("minecraft", "muddy_mangrove_roots", V1_19);
    public static final VoxelMaterial OCHRE_FROGLIGHT = register("minecraft", "ochre_froglight", V1_19);
    public static final VoxelMaterial PACKED_MUD = register("minecraft", "packed_mud", V1_19);
    public static final VoxelMaterial PEARLESCENT_FROGLIGHT = register("minecraft", "pearlescent_froglight", V1_19);
    public static final VoxelMaterial POTTED_MANGROVE_PROPAGULE = register("minecraft", "potted_mangrove_propagule", V1_19);
    public static final VoxelMaterial REINFORCED_DEEPSLATE = register("minecraft", "reinforced_deepslate", V1_19);
    public static final VoxelMaterial SCULK = register("minecraft", "sculk", V1_19);
    public static final VoxelMaterial SCULK_CATALYST = register("minecraft", "sculk_catalyst", V1_19);
    public static final VoxelMaterial SCULK_SHRIEKER = register("minecraft", "sculk_shrieker", V1_19);
    public static final VoxelMaterial SCULK_VEIN = register("minecraft", "sculk_vein", V1_19, Tags.FALLSOFF);
    public static final VoxelMaterial STRIPPED_MANGROVE_LOG = register("minecraft", "stripped_mangrove_log", V1_19);
    public static final VoxelMaterial STRIPPED_MANGROVE_WOOD = register("minecraft", "stripped_mangrove_wood", V1_19);
    public static final VoxelMaterial VERDANT_FROGLIGHT = register("minecraft", "verdant_froglight", V1_19);
    //</editor-fold>
    private final Version version;
    private final String key;
    private final String namespace;
    private final List<Tags> tags;
    private IMaterial material = null;

    public VoxelMaterial(String namespace, String key, Version version, Tags... tags) {
        this.namespace = namespace;
        this.key = key;
        this.version = version;
        this.tags = Arrays.stream(tags).toList();
    }

    public VoxelMaterial(String namespace, String key) {
        this(namespace, key, V1_16);
    }

    public VoxelMaterial(String key) {
        this("minecraft", key);
    }

    private static VoxelMaterial register(String namespace, String key, Version version, Tags... tags) {

        var material = new VoxelMaterial(namespace, key, version, tags);
        var mapKey = namespace + ":" + key;
        BLOCKS.put(mapKey, material);
        return material;
    }

    private static VoxelMaterial register(String namespace, String key, Tags... tags) {
        return register(namespace, key, V1_16, tags);
    }

    public static VoxelMaterial getMaterial(String key) {
        if (key.contains(":")) {
            String[] components = key.split(":");
            return getMaterial(components[0], components[1]);
        }
        return getMaterial("minecraft", key);
    }

    public static VoxelMaterial getMaterial(String namespace, String key) {
        if (namespace.isEmpty() || key.isEmpty()) return null;
        var block = BLOCKS.get(namespace + ":" + key);
        if (block == null) {
            block = new VoxelMaterial(namespace, key);
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

    public static Collection<VoxelMaterial> getMaterials() {
        return BLOCKS.values().stream().filter((m) -> VoxelSniper.voxelsniper.getVersion().isSupported(m.getVersion())).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoxelMaterial that = (VoxelMaterial) o;
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
    public boolean equals(VoxelMaterial material) {
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
