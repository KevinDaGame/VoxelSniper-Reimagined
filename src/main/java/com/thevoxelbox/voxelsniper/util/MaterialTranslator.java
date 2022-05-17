package com.thevoxelbox.voxelsniper.util;

import java.util.HashMap;
import org.bukkit.Material;

/**
 * Resolves legacy numerical item IDs to Materials.
 * 
 * This class SHOULD NOT be updated. It is meant for backwards compatibility and familiarity.
 * This will allow usage of the VoxelMaterial command such: /v 0 will set the Voxel to AIR.
 *
 * 
 * @author ervinnnc
 */

// TODO: Resolve data values for certain blocks 
// e.g. 59:7 will resolve to Wheat and data value of age=7.
public class MaterialTranslator {
    public static final HashMap<String, Material> legacyMaterialIds = new HashMap<>();
    
    public static Material resolveMaterial(String id) {
        if (legacyMaterialIds.containsKey(id)) {
            return legacyMaterialIds.get(id);
        }
        
        return null;
    } 
    
    static { 
        legacyMaterialIds.put("0", Material.AIR);
        legacyMaterialIds.put("1", Material.STONE);
        legacyMaterialIds.put("1:1", Material.GRANITE);
        legacyMaterialIds.put("1:2", Material.POLISHED_GRANITE);
        legacyMaterialIds.put("1:3", Material.DIORITE);
        legacyMaterialIds.put("1:4", Material.POLISHED_DIORITE);
        legacyMaterialIds.put("1:5", Material.ANDESITE);
        legacyMaterialIds.put("1:6", Material.POLISHED_ANDESITE);
        legacyMaterialIds.put("2", Material.GRASS_BLOCK);
        legacyMaterialIds.put("3", Material.DIRT);
        legacyMaterialIds.put("3:1", Material.COARSE_DIRT);
        legacyMaterialIds.put("3:2", Material.PODZOL);
        legacyMaterialIds.put("4", Material.COBBLESTONE);
        legacyMaterialIds.put("5", Material.OAK_PLANKS);
        legacyMaterialIds.put("5:1", Material.SPRUCE_PLANKS);
        legacyMaterialIds.put("5:2", Material.BIRCH_PLANKS);
        legacyMaterialIds.put("5:3", Material.JUNGLE_PLANKS);
        legacyMaterialIds.put("5:4", Material.ACACIA_PLANKS);
        legacyMaterialIds.put("5:5", Material.DARK_OAK_PLANKS);
        legacyMaterialIds.put("6", Material.OAK_SAPLING);
        legacyMaterialIds.put("6:1", Material.SPRUCE_SAPLING);
        legacyMaterialIds.put("6:2", Material.BIRCH_SAPLING);
        legacyMaterialIds.put("6:3", Material.JUNGLE_SAPLING);
        legacyMaterialIds.put("6:4", Material.ACACIA_SAPLING);
        legacyMaterialIds.put("6:5", Material.DARK_OAK_SAPLING);
        legacyMaterialIds.put("7", Material.BEDROCK);
        legacyMaterialIds.put("8", Material.WATER); // TODO add flowing
        legacyMaterialIds.put("9", Material.WATER); // TODO add still
        legacyMaterialIds.put("10", Material.LAVA); // TODO add flowing
        legacyMaterialIds.put("11", Material.LAVA); // TODO add still
        legacyMaterialIds.put("12", Material.SAND);
        legacyMaterialIds.put("12:1", Material.RED_SAND);
        legacyMaterialIds.put("13", Material.GRAVEL);
        legacyMaterialIds.put("14", Material.GOLD_ORE);
        legacyMaterialIds.put("15", Material.IRON_ORE);
        legacyMaterialIds.put("16", Material.COAL_ORE);
        legacyMaterialIds.put("17", Material.OAK_LOG);
        legacyMaterialIds.put("17:1", Material.SPRUCE_LOG);
        legacyMaterialIds.put("17:2", Material.BIRCH_LOG);
        legacyMaterialIds.put("17:3", Material.JUNGLE_LOG);
        legacyMaterialIds.put("18", Material.OAK_LEAVES);
        legacyMaterialIds.put("18:1", Material.SPRUCE_LEAVES);
        legacyMaterialIds.put("18:2", Material.BIRCH_LEAVES);
        legacyMaterialIds.put("18:3", Material.JUNGLE_LEAVES);
        legacyMaterialIds.put("19", Material.SPONGE);
        legacyMaterialIds.put("19:1", Material.WET_SPONGE);
        legacyMaterialIds.put("20", Material.GLASS);
        legacyMaterialIds.put("21", Material.LAPIS_ORE);
        legacyMaterialIds.put("22", Material.LAPIS_BLOCK);
        legacyMaterialIds.put("23", Material.DISPENSER);
        legacyMaterialIds.put("24", Material.SANDSTONE);
        legacyMaterialIds.put("24:1", Material.CHISELED_SANDSTONE);
        legacyMaterialIds.put("24:2", Material.SMOOTH_SANDSTONE);
        legacyMaterialIds.put("25", Material.NOTE_BLOCK);
        legacyMaterialIds.put("26", Material.RED_BED);
        legacyMaterialIds.put("27", Material.POWERED_RAIL);
        legacyMaterialIds.put("28", Material.DETECTOR_RAIL);
        legacyMaterialIds.put("29", Material.STICKY_PISTON);
        legacyMaterialIds.put("30", Material.COBWEB);
        legacyMaterialIds.put("31", Material.DEAD_BUSH);
        legacyMaterialIds.put("31:1", Material.GRASS);
        legacyMaterialIds.put("31:2", Material.FERN);
        legacyMaterialIds.put("32", Material.DEAD_BUSH);
        legacyMaterialIds.put("33", Material.PISTON);
        legacyMaterialIds.put("34", Material.PISTON_HEAD);
        legacyMaterialIds.put("35", Material.WHITE_WOOL);
        legacyMaterialIds.put("35:1", Material.ORANGE_WOOL);
        legacyMaterialIds.put("35:2", Material.MAGENTA_WOOL);
        legacyMaterialIds.put("35:3", Material.LIGHT_BLUE_WOOL);
        legacyMaterialIds.put("35:4", Material.YELLOW_WOOL);
        legacyMaterialIds.put("35:5", Material.LIME_WOOL);
        legacyMaterialIds.put("35:6", Material.PINK_WOOL);
        legacyMaterialIds.put("35:7", Material.GRAY_WOOL);
        legacyMaterialIds.put("35:8", Material.LIGHT_GRAY_WOOL);
        legacyMaterialIds.put("35:9", Material.CYAN_WOOL);
        legacyMaterialIds.put("35:10", Material.PURPLE_WOOL);
        legacyMaterialIds.put("35:11", Material.BLUE_WOOL);
        legacyMaterialIds.put("35:12", Material.BROWN_WOOL);
        legacyMaterialIds.put("35:13", Material.GREEN_WOOL);
        legacyMaterialIds.put("35:14", Material.RED_WOOL);
        legacyMaterialIds.put("35:15", Material.BLACK_WOOL);
        legacyMaterialIds.put("36", Material.AIR); // nothing
        legacyMaterialIds.put("37", Material.DANDELION);
        legacyMaterialIds.put("38", Material.POPPY);
        legacyMaterialIds.put("38:1", Material.BLUE_ORCHID);
        legacyMaterialIds.put("38:2", Material.ALLIUM);
        legacyMaterialIds.put("38:3", Material.AZURE_BLUET);
        legacyMaterialIds.put("38:4", Material.RED_TULIP);
        legacyMaterialIds.put("38:5", Material.ORANGE_TULIP);
        legacyMaterialIds.put("38:6", Material.WHITE_TULIP);
        legacyMaterialIds.put("38:7", Material.PINK_TULIP);
        legacyMaterialIds.put("38:8", Material.OXEYE_DAISY);
        legacyMaterialIds.put("39", Material.BROWN_MUSHROOM);
        legacyMaterialIds.put("40", Material.RED_MUSHROOM);
        legacyMaterialIds.put("41", Material.GOLD_BLOCK);
        legacyMaterialIds.put("42", Material.IRON_BLOCK);
        legacyMaterialIds.put("43", Material.STONE_SLAB); // TODO set double slab on block data
        legacyMaterialIds.put("43:1", Material.SANDSTONE_SLAB); // TODO set double slab on block data
        legacyMaterialIds.put("43:2", Material.OAK_SLAB); // TODO set double slab on block data
        legacyMaterialIds.put("43:3", Material.COBBLESTONE_SLAB); // TODO set double slab on block data
        legacyMaterialIds.put("43:4", Material.BRICK_SLAB); // TODO set double slab on block data
        legacyMaterialIds.put("43:5", Material.STONE_BRICK_SLAB); // TODO set double slab on block data
        legacyMaterialIds.put("43:6", Material.NETHER_BRICK_SLAB); // TODO set double slab on block data
        legacyMaterialIds.put("43:7", Material.QUARTZ_SLAB); // TODO set double slab on block data
        legacyMaterialIds.put("44", Material.STONE_SLAB);
        legacyMaterialIds.put("44:1", Material.SANDSTONE_SLAB);
        legacyMaterialIds.put("44:2", Material.OAK_SLAB); // TODO add other wood variants as extra data values
        legacyMaterialIds.put("44:3", Material.COBBLESTONE_SLAB);
        legacyMaterialIds.put("44:4", Material.BRICK_SLAB);
        legacyMaterialIds.put("44:5", Material.STONE_BRICK_SLAB);
        legacyMaterialIds.put("44:6", Material.NETHER_BRICK_SLAB);
        legacyMaterialIds.put("44:7", Material.QUARTZ_SLAB);
        legacyMaterialIds.put("45", Material.BRICKS);
        legacyMaterialIds.put("46", Material.TNT);
        legacyMaterialIds.put("47", Material.BOOKSHELF);
        legacyMaterialIds.put("48", Material.MOSSY_COBBLESTONE);
        legacyMaterialIds.put("49", Material.OBSIDIAN);
        legacyMaterialIds.put("50", Material.TORCH);
        legacyMaterialIds.put("51", Material.FIRE);
        legacyMaterialIds.put("52", Material.SPAWNER);
        legacyMaterialIds.put("53", Material.OAK_STAIRS); // TODO add other wood variants as extra data values
        legacyMaterialIds.put("54", Material.CHEST);
        legacyMaterialIds.put("55", Material.REDSTONE_WIRE);
        legacyMaterialIds.put("56", Material.DIAMOND_ORE);
        legacyMaterialIds.put("57", Material.DIAMOND_BLOCK);
        legacyMaterialIds.put("58", Material.CRAFTING_TABLE);
        legacyMaterialIds.put("59", Material.WHEAT);
        legacyMaterialIds.put("60", Material.FARMLAND);
        legacyMaterialIds.put("61", Material.FURNACE);
        legacyMaterialIds.put("62", Material.FURNACE); // TODO set burning
        legacyMaterialIds.put("63", Material.OAK_SIGN); // TODO add other wood data values
        legacyMaterialIds.put("64", Material.OAK_DOOR); // TODO add other wood data values
        legacyMaterialIds.put("65", Material.LADDER);
        legacyMaterialIds.put("66", Material.RAIL);
        legacyMaterialIds.put("67", Material.COBBLESTONE_STAIRS);
        legacyMaterialIds.put("68", Material.OAK_WALL_SIGN); // TODO add other wood data values
        legacyMaterialIds.put("69", Material.LEVER);
        legacyMaterialIds.put("70", Material.STONE_PRESSURE_PLATE);
        legacyMaterialIds.put("71", Material.IRON_DOOR);
        legacyMaterialIds.put("72", Material.OAK_PRESSURE_PLATE); // TODO add other wood data values
        legacyMaterialIds.put("73", Material.REDSTONE_ORE);
        legacyMaterialIds.put("74", Material.REDSTONE_ORE); // TODO set glowing
        legacyMaterialIds.put("75", Material.REDSTONE_TORCH);
        legacyMaterialIds.put("76", Material.REDSTONE_TORCH); // TODO set on
        legacyMaterialIds.put("77", Material.STONE_BUTTON);
        legacyMaterialIds.put("78", Material.SNOW);
        legacyMaterialIds.put("79", Material.ICE);
        legacyMaterialIds.put("80", Material.SNOW_BLOCK);
        legacyMaterialIds.put("81", Material.CACTUS);
        legacyMaterialIds.put("82", Material.CLAY);
        legacyMaterialIds.put("83", Material.SUGAR_CANE);
        legacyMaterialIds.put("84", Material.JUKEBOX);
        legacyMaterialIds.put("85", Material.OAK_FENCE); // TODO add other wood data values
        legacyMaterialIds.put("86", Material.PUMPKIN);
        legacyMaterialIds.put("87", Material.NETHERRACK);
        legacyMaterialIds.put("88", Material.SOUL_SAND);
        legacyMaterialIds.put("89", Material.GLOWSTONE);
        legacyMaterialIds.put("90", Material.NETHER_PORTAL);
        legacyMaterialIds.put("91", Material.JACK_O_LANTERN);
        legacyMaterialIds.put("92", Material.CAKE);
        legacyMaterialIds.put("93", Material.REPEATER);
        legacyMaterialIds.put("94", Material.REPEATER); // TODO set on
        legacyMaterialIds.put("95", Material.WHITE_STAINED_GLASS);
        legacyMaterialIds.put("95:1", Material.ORANGE_STAINED_GLASS);
        legacyMaterialIds.put("95:2", Material.MAGENTA_STAINED_GLASS);
        legacyMaterialIds.put("95:3", Material.LIGHT_BLUE_STAINED_GLASS);
        legacyMaterialIds.put("95:4", Material.YELLOW_STAINED_GLASS);
        legacyMaterialIds.put("95:5", Material.LIME_STAINED_GLASS);
        legacyMaterialIds.put("95:6", Material.PINK_STAINED_GLASS);
        legacyMaterialIds.put("95:7", Material.GRAY_STAINED_GLASS);
        legacyMaterialIds.put("95:8", Material.LIGHT_GRAY_STAINED_GLASS);
        legacyMaterialIds.put("95:9", Material.CYAN_STAINED_GLASS);
        legacyMaterialIds.put("95:10", Material.PURPLE_STAINED_GLASS);
        legacyMaterialIds.put("95:11", Material.BLUE_STAINED_GLASS);
        legacyMaterialIds.put("95:12", Material.BROWN_STAINED_GLASS);
        legacyMaterialIds.put("95:13", Material.GREEN_STAINED_GLASS);
        legacyMaterialIds.put("95:14", Material.RED_STAINED_GLASS);
        legacyMaterialIds.put("95:15", Material.BLACK_STAINED_GLASS);
        legacyMaterialIds.put("96", Material.OAK_TRAPDOOR); // TODO add other woods
        legacyMaterialIds.put("97", Material.INFESTED_STONE);
        legacyMaterialIds.put("97:1", Material.INFESTED_COBBLESTONE);
        legacyMaterialIds.put("97:2", Material.INFESTED_STONE_BRICKS);
        legacyMaterialIds.put("97:3", Material.INFESTED_MOSSY_STONE_BRICKS);
        legacyMaterialIds.put("97:4", Material.INFESTED_CRACKED_STONE_BRICKS);
        legacyMaterialIds.put("97:5", Material.INFESTED_CHISELED_STONE_BRICKS);
        legacyMaterialIds.put("98", Material.STONE_BRICKS);
        legacyMaterialIds.put("98:1", Material.MOSSY_STONE_BRICKS);
        legacyMaterialIds.put("98:2", Material.CRACKED_STONE_BRICKS);
        legacyMaterialIds.put("98:3", Material.CHISELED_STONE_BRICKS);
        legacyMaterialIds.put("99", Material.BROWN_MUSHROOM_BLOCK);
        legacyMaterialIds.put("100", Material.RED_MUSHROOM_BLOCK);
        legacyMaterialIds.put("101", Material.IRON_BARS);
        legacyMaterialIds.put("102", Material.GLASS_PANE); // TODO add other color data values
        legacyMaterialIds.put("103", Material.MELON);
        legacyMaterialIds.put("104", Material.PUMPKIN_STEM);
        legacyMaterialIds.put("105", Material.MELON_STEM);
        legacyMaterialIds.put("106", Material.VINE);
        legacyMaterialIds.put("107", Material.OAK_FENCE_GATE); // TODO add other woods
        legacyMaterialIds.put("108", Material.BRICK_STAIRS);
        legacyMaterialIds.put("109", Material.STONE_BRICK_STAIRS);
        legacyMaterialIds.put("110", Material.MYCELIUM);
        legacyMaterialIds.put("111", Material.LILY_PAD);
        legacyMaterialIds.put("112", Material.NETHER_BRICKS);
        legacyMaterialIds.put("113", Material.NETHER_BRICK_FENCE);
        legacyMaterialIds.put("114", Material.NETHER_BRICK_STAIRS);
        legacyMaterialIds.put("115", Material.NETHER_WART);
        legacyMaterialIds.put("116", Material.ENCHANTING_TABLE);
        legacyMaterialIds.put("117", Material.BREWING_STAND);
        legacyMaterialIds.put("118", Material.CAULDRON);
        legacyMaterialIds.put("119", Material.END_PORTAL);
        legacyMaterialIds.put("120", Material.END_PORTAL_FRAME);
        legacyMaterialIds.put("121", Material.END_STONE);
        legacyMaterialIds.put("122", Material.DRAGON_EGG);
        legacyMaterialIds.put("123", Material.REDSTONE_LAMP);
        legacyMaterialIds.put("124", Material.REDSTONE_LAMP); // TODO set active
        legacyMaterialIds.put("125", Material.OAK_PLANKS); // TODO set double slab
        legacyMaterialIds.put("125:1", Material.SPRUCE_PLANKS); // TODO set double slab
        legacyMaterialIds.put("125:2", Material.BIRCH_PLANKS); // TODO set double slab
        legacyMaterialIds.put("125:3", Material.JUNGLE_PLANKS); // TODO set double slab
        legacyMaterialIds.put("125:4", Material.ACACIA_PLANKS); // TODO set double slab
        legacyMaterialIds.put("125:5", Material.DARK_OAK_PLANKS); // TODO set double slab
        legacyMaterialIds.put("126", Material.OAK_SLAB);
        legacyMaterialIds.put("126:1", Material.SPRUCE_SLAB);
        legacyMaterialIds.put("126:2", Material.BIRCH_SLAB);
        legacyMaterialIds.put("126:3", Material.JUNGLE_SLAB);
        legacyMaterialIds.put("126:4", Material.ACACIA_SLAB);
        legacyMaterialIds.put("126:5", Material.DARK_OAK_SLAB);
        legacyMaterialIds.put("127", Material.COCOA);
        legacyMaterialIds.put("128", Material.SANDSTONE_STAIRS);
        legacyMaterialIds.put("129", Material.EMERALD_ORE);
        legacyMaterialIds.put("130", Material.ENDER_CHEST);
        legacyMaterialIds.put("131", Material.TRIPWIRE_HOOK);
        legacyMaterialIds.put("132", Material.TRIPWIRE);
        legacyMaterialIds.put("133", Material.EMERALD_BLOCK);
        legacyMaterialIds.put("134", Material.SPRUCE_STAIRS);
        legacyMaterialIds.put("135", Material.BIRCH_STAIRS);
        legacyMaterialIds.put("136", Material.JUNGLE_STAIRS);
        legacyMaterialIds.put("137", Material.COMMAND_BLOCK);
        legacyMaterialIds.put("138", Material.BEACON);
        legacyMaterialIds.put("139", Material.COBBLESTONE_WALL);
        legacyMaterialIds.put("139:1", Material.MOSSY_COBBLESTONE_WALL);
        legacyMaterialIds.put("140", Material.FLOWER_POT);
        legacyMaterialIds.put("141", Material.CARROTS);
        legacyMaterialIds.put("142", Material.POTATOES);
        legacyMaterialIds.put("143", Material.OAK_BUTTON); // TODO add other wood data values
        legacyMaterialIds.put("144", Material.SKELETON_SKULL);
        legacyMaterialIds.put("145", Material.ANVIL);
        legacyMaterialIds.put("146", Material.TRAPPED_CHEST);
        legacyMaterialIds.put("147", Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
        legacyMaterialIds.put("148", Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
        legacyMaterialIds.put("149", Material.COMPARATOR);
        legacyMaterialIds.put("150", Material.COMPARATOR); // TODO set active
        legacyMaterialIds.put("151", Material.DAYLIGHT_DETECTOR);
        legacyMaterialIds.put("152", Material.REDSTONE_BLOCK);
        legacyMaterialIds.put("153", Material.NETHER_QUARTZ_ORE);
        legacyMaterialIds.put("154", Material.HOPPER);
        legacyMaterialIds.put("155", Material.QUARTZ_BLOCK);
        legacyMaterialIds.put("155:1", Material.CHISELED_QUARTZ_BLOCK);
        legacyMaterialIds.put("155:2", Material.QUARTZ_PILLAR);
        legacyMaterialIds.put("156", Material.QUARTZ_STAIRS);
        legacyMaterialIds.put("157", Material.ACTIVATOR_RAIL);
        legacyMaterialIds.put("158", Material.DROPPER);
        legacyMaterialIds.put("159", Material.WHITE_TERRACOTTA);
        legacyMaterialIds.put("159:1", Material.ORANGE_TERRACOTTA);
        legacyMaterialIds.put("159:2", Material.MAGENTA_TERRACOTTA);
        legacyMaterialIds.put("159:3", Material.LIGHT_BLUE_TERRACOTTA);
        legacyMaterialIds.put("159:4", Material.YELLOW_TERRACOTTA);
        legacyMaterialIds.put("159:5", Material.LIME_TERRACOTTA);
        legacyMaterialIds.put("159:6", Material.PINK_TERRACOTTA);
        legacyMaterialIds.put("159:7", Material.GRAY_TERRACOTTA);
        legacyMaterialIds.put("159:8", Material.LIGHT_GRAY_TERRACOTTA);
        legacyMaterialIds.put("159:9", Material.CYAN_TERRACOTTA);
        legacyMaterialIds.put("159:10", Material.PURPLE_TERRACOTTA);
        legacyMaterialIds.put("159:11", Material.BLUE_TERRACOTTA);
        legacyMaterialIds.put("159:12", Material.BROWN_TERRACOTTA);
        legacyMaterialIds.put("159:13", Material.GREEN_TERRACOTTA);
        legacyMaterialIds.put("159:14", Material.RED_TERRACOTTA);
        legacyMaterialIds.put("159:15", Material.BLACK_TERRACOTTA);
        legacyMaterialIds.put("160", Material.WHITE_STAINED_GLASS_PANE);
        legacyMaterialIds.put("160:1", Material.ORANGE_STAINED_GLASS_PANE);
        legacyMaterialIds.put("160:2", Material.MAGENTA_STAINED_GLASS_PANE);
        legacyMaterialIds.put("160:3", Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        legacyMaterialIds.put("160:4", Material.YELLOW_STAINED_GLASS_PANE);
        legacyMaterialIds.put("160:5", Material.LIME_STAINED_GLASS_PANE);
        legacyMaterialIds.put("160:6", Material.PINK_STAINED_GLASS_PANE);
        legacyMaterialIds.put("160:7", Material.GRAY_STAINED_GLASS_PANE);
        legacyMaterialIds.put("160:8", Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        legacyMaterialIds.put("160:9", Material.CYAN_STAINED_GLASS_PANE);
        legacyMaterialIds.put("160:10", Material.PURPLE_STAINED_GLASS_PANE);
        legacyMaterialIds.put("160:11", Material.BLUE_STAINED_GLASS_PANE);
        legacyMaterialIds.put("160:12", Material.BROWN_STAINED_GLASS_PANE);
        legacyMaterialIds.put("160:13", Material.GREEN_STAINED_GLASS_PANE);
        legacyMaterialIds.put("160:14", Material.RED_STAINED_GLASS_PANE);
        legacyMaterialIds.put("160:15", Material.BLACK_STAINED_GLASS_PANE);
        legacyMaterialIds.put("161", Material.ACACIA_LEAVES);
        legacyMaterialIds.put("161:1", Material.DARK_OAK_LEAVES);
        legacyMaterialIds.put("162", Material.ACACIA_WOOD);
        legacyMaterialIds.put("162:1", Material.DARK_OAK_WOOD);
        legacyMaterialIds.put("163", Material.ACACIA_STAIRS);
        legacyMaterialIds.put("164", Material.DARK_OAK_STAIRS);
        legacyMaterialIds.put("165", Material.SLIME_BLOCK);
        legacyMaterialIds.put("166", Material.BARRIER);
        legacyMaterialIds.put("167", Material.IRON_TRAPDOOR);
        legacyMaterialIds.put("168", Material.PRISMARINE);
        legacyMaterialIds.put("168:1", Material.PRISMARINE_BRICKS);
        legacyMaterialIds.put("168:2", Material.DARK_PRISMARINE);
        legacyMaterialIds.put("169", Material.SEA_LANTERN);
        legacyMaterialIds.put("170", Material.HAY_BLOCK);
        legacyMaterialIds.put("171", Material.WHITE_CARPET);
        legacyMaterialIds.put("171:1", Material.ORANGE_CARPET);
        legacyMaterialIds.put("171:2", Material.MAGENTA_CARPET);
        legacyMaterialIds.put("171:3", Material.LIGHT_BLUE_CARPET);
        legacyMaterialIds.put("171:4", Material.YELLOW_CARPET);
        legacyMaterialIds.put("171:5", Material.LIME_CARPET);
        legacyMaterialIds.put("171:6", Material.PINK_CARPET);
        legacyMaterialIds.put("171:7", Material.GRAY_CARPET);
        legacyMaterialIds.put("171:8", Material.LIGHT_GRAY_CARPET);
        legacyMaterialIds.put("171:9", Material.CYAN_CARPET);
        legacyMaterialIds.put("171:10", Material.PURPLE_CARPET);
        legacyMaterialIds.put("171:11", Material.BLUE_CARPET);
        legacyMaterialIds.put("171:12", Material.BROWN_CARPET);
        legacyMaterialIds.put("171:13", Material.GREEN_CARPET);
        legacyMaterialIds.put("171:14", Material.RED_CARPET);
        legacyMaterialIds.put("171:15", Material.BLACK_CARPET);
        legacyMaterialIds.put("172", Material.TERRACOTTA);
        legacyMaterialIds.put("173", Material.COAL_BLOCK);
        legacyMaterialIds.put("174", Material.PACKED_ICE);
        legacyMaterialIds.put("175", Material.SUNFLOWER);
        legacyMaterialIds.put("175:1", Material.LILAC);
        legacyMaterialIds.put("175:2", Material.TALL_GRASS);
        legacyMaterialIds.put("175:3", Material.LARGE_FERN);
        legacyMaterialIds.put("175:4", Material.ROSE_BUSH);
        legacyMaterialIds.put("175:5", Material.PEONY);
        legacyMaterialIds.put("176", Material.WHITE_BANNER);
        legacyMaterialIds.put("177", Material.WHITE_WALL_BANNER); // TODO add other colors
        legacyMaterialIds.put("178", Material.DAYLIGHT_DETECTOR); // TODO set inverted
        legacyMaterialIds.put("179", Material.RED_SANDSTONE);
        legacyMaterialIds.put("179:1", Material.CHISELED_RED_SANDSTONE);
        legacyMaterialIds.put("179:2", Material.SMOOTH_RED_SANDSTONE);
        legacyMaterialIds.put("180", Material.RED_SANDSTONE_STAIRS);
        legacyMaterialIds.put("181", Material.RED_SANDSTONE); // TODO double slab
        legacyMaterialIds.put("182", Material.RED_SANDSTONE_SLAB);
        legacyMaterialIds.put("183", Material.SPRUCE_FENCE_GATE);
        legacyMaterialIds.put("184", Material.BIRCH_FENCE_GATE);
        legacyMaterialIds.put("185", Material.JUNGLE_FENCE_GATE);
        legacyMaterialIds.put("186", Material.DARK_OAK_FENCE_GATE);
        legacyMaterialIds.put("187", Material.ACACIA_FENCE_GATE);
        legacyMaterialIds.put("188", Material.SPRUCE_FENCE);
        legacyMaterialIds.put("189", Material.BIRCH_FENCE);
        legacyMaterialIds.put("190", Material.JUNGLE_FENCE);
        legacyMaterialIds.put("191", Material.DARK_OAK_FENCE);
        legacyMaterialIds.put("192", Material.ACACIA_FENCE);
        legacyMaterialIds.put("193", Material.SPRUCE_DOOR);
        legacyMaterialIds.put("194", Material.BIRCH_DOOR);
        legacyMaterialIds.put("195", Material.JUNGLE_DOOR);
        legacyMaterialIds.put("196", Material.ACACIA_DOOR);
        legacyMaterialIds.put("197", Material.DARK_OAK_DOOR);
        legacyMaterialIds.put("198", Material.END_ROD);
        legacyMaterialIds.put("199", Material.CHORUS_PLANT);
        legacyMaterialIds.put("200", Material.CHORUS_FLOWER);
        legacyMaterialIds.put("201", Material.PURPUR_BLOCK);
        legacyMaterialIds.put("202", Material.PURPUR_PILLAR);
        legacyMaterialIds.put("203", Material.PURPUR_STAIRS);
        legacyMaterialIds.put("204", Material.PURPUR_BLOCK); // TODO double slab
        legacyMaterialIds.put("205", Material.PURPUR_SLAB);
        legacyMaterialIds.put("206", Material.END_STONE_BRICKS);
        legacyMaterialIds.put("207", Material.BEETROOTS);
        try {
            // 1.17+
            legacyMaterialIds.put("208", Material.DIRT_PATH);
        } catch(Throwable t) {
            // older versions
            legacyMaterialIds.put("208", Material.valueOf("GRASS_PATH"));
        }
        legacyMaterialIds.put("209", Material.END_GATEWAY);
        legacyMaterialIds.put("210", Material.COMMAND_BLOCK); // TODO set repeating
        legacyMaterialIds.put("211", Material.COMMAND_BLOCK); // TODO set chain
        legacyMaterialIds.put("212", Material.FROSTED_ICE);
        legacyMaterialIds.put("213", Material.MAGMA_BLOCK);
        legacyMaterialIds.put("214", Material.NETHER_WART_BLOCK);
        legacyMaterialIds.put("215", Material.RED_NETHER_BRICKS);
        legacyMaterialIds.put("216", Material.BONE_BLOCK);
        legacyMaterialIds.put("217", Material.STRUCTURE_VOID);
        legacyMaterialIds.put("218", Material.OBSERVER);
        legacyMaterialIds.put("219", Material.WHITE_SHULKER_BOX);
        legacyMaterialIds.put("220", Material.ORANGE_SHULKER_BOX);
        legacyMaterialIds.put("221", Material.MAGENTA_SHULKER_BOX);
        legacyMaterialIds.put("222", Material.LIGHT_BLUE_SHULKER_BOX);
        legacyMaterialIds.put("223", Material.YELLOW_SHULKER_BOX);
        legacyMaterialIds.put("224", Material.LIME_SHULKER_BOX);
        legacyMaterialIds.put("225", Material.PINK_SHULKER_BOX);
        legacyMaterialIds.put("226", Material.GRAY_SHULKER_BOX);
        legacyMaterialIds.put("227", Material.LIGHT_GRAY_SHULKER_BOX);
        legacyMaterialIds.put("228", Material.CYAN_SHULKER_BOX);
        legacyMaterialIds.put("229", Material.PURPLE_SHULKER_BOX);
        legacyMaterialIds.put("230", Material.BLUE_SHULKER_BOX);
        legacyMaterialIds.put("231", Material.BROWN_SHULKER_BOX);
        legacyMaterialIds.put("232", Material.GREEN_SHULKER_BOX);
        legacyMaterialIds.put("233", Material.RED_SHULKER_BOX);
        legacyMaterialIds.put("234", Material.BLACK_SHULKER_BOX);
        legacyMaterialIds.put("235", Material.WHITE_GLAZED_TERRACOTTA);
        legacyMaterialIds.put("236", Material.ORANGE_GLAZED_TERRACOTTA);
        legacyMaterialIds.put("237", Material.MAGENTA_GLAZED_TERRACOTTA);
        legacyMaterialIds.put("238", Material.LIGHT_BLUE_GLAZED_TERRACOTTA);
        legacyMaterialIds.put("239", Material.YELLOW_GLAZED_TERRACOTTA);
        legacyMaterialIds.put("240", Material.LIME_GLAZED_TERRACOTTA);
        legacyMaterialIds.put("241", Material.PINK_GLAZED_TERRACOTTA);
        legacyMaterialIds.put("242", Material.GRAY_GLAZED_TERRACOTTA);
        legacyMaterialIds.put("243", Material.LIGHT_GRAY_GLAZED_TERRACOTTA);
        legacyMaterialIds.put("244", Material.CYAN_GLAZED_TERRACOTTA);
        legacyMaterialIds.put("245", Material.PURPLE_GLAZED_TERRACOTTA);
        legacyMaterialIds.put("246", Material.BLUE_GLAZED_TERRACOTTA);
        legacyMaterialIds.put("247", Material.BROWN_GLAZED_TERRACOTTA);
        legacyMaterialIds.put("248", Material.GREEN_GLAZED_TERRACOTTA);
        legacyMaterialIds.put("249", Material.RED_GLAZED_TERRACOTTA);
        legacyMaterialIds.put("250", Material.BLACK_GLAZED_TERRACOTTA);
        legacyMaterialIds.put("251", Material.WHITE_CONCRETE);
        legacyMaterialIds.put("251:1", Material.ORANGE_CONCRETE);
        legacyMaterialIds.put("251:2", Material.MAGENTA_CONCRETE);
        legacyMaterialIds.put("251:3", Material.LIGHT_BLUE_CONCRETE);
        legacyMaterialIds.put("251:4", Material.YELLOW_CONCRETE);
        legacyMaterialIds.put("251:5", Material.LIME_CONCRETE);
        legacyMaterialIds.put("251:6", Material.PINK_CONCRETE);
        legacyMaterialIds.put("251:7", Material.GRAY_CONCRETE);
        legacyMaterialIds.put("251:8", Material.LIGHT_GRAY_CONCRETE);
        legacyMaterialIds.put("251:9", Material.CYAN_CONCRETE);
        legacyMaterialIds.put("251:10", Material.PURPLE_CONCRETE);
        legacyMaterialIds.put("251:11", Material.BLUE_CONCRETE);
        legacyMaterialIds.put("251:12", Material.BROWN_CONCRETE);
        legacyMaterialIds.put("251:13", Material.GREEN_CONCRETE);
        legacyMaterialIds.put("251:14", Material.RED_CONCRETE);
        legacyMaterialIds.put("251:15", Material.BLACK_CONCRETE);
        legacyMaterialIds.put("252", Material.WHITE_CONCRETE_POWDER);
        legacyMaterialIds.put("252:1", Material.ORANGE_CONCRETE_POWDER);
        legacyMaterialIds.put("252:2", Material.MAGENTA_CONCRETE_POWDER);
        legacyMaterialIds.put("252:3", Material.LIGHT_BLUE_CONCRETE_POWDER);
        legacyMaterialIds.put("252:4", Material.YELLOW_CONCRETE_POWDER);
        legacyMaterialIds.put("252:5", Material.LIME_CONCRETE_POWDER);
        legacyMaterialIds.put("252:6", Material.PINK_CONCRETE_POWDER);
        legacyMaterialIds.put("252:7", Material.GRAY_CONCRETE_POWDER);
        legacyMaterialIds.put("252:8", Material.LIGHT_GRAY_CONCRETE_POWDER);
        legacyMaterialIds.put("252:9", Material.CYAN_CONCRETE_POWDER);
        legacyMaterialIds.put("252:10", Material.PURPLE_CONCRETE_POWDER);
        legacyMaterialIds.put("252:11", Material.BLUE_CONCRETE_POWDER);
        legacyMaterialIds.put("252:12", Material.BROWN_CONCRETE_POWDER);
        legacyMaterialIds.put("252:13", Material.GREEN_CONCRETE_POWDER);
        legacyMaterialIds.put("252:14", Material.RED_CONCRETE_POWDER);
        legacyMaterialIds.put("252:15", Material.BLACK_CONCRETE_POWDER);
        legacyMaterialIds.put("253", Material.AIR); // nothing
        legacyMaterialIds.put("254", Material.AIR); // nothing
        legacyMaterialIds.put("255", Material.STRUCTURE_BLOCK);
    }
}
