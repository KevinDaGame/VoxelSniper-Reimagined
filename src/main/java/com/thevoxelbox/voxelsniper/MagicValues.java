package com.thevoxelbox.voxelsniper;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import java.util.HashMap;
import java.util.Map;

public class MagicValues {
    private static final Map<String, BlockData> toBlockData = new HashMap<>();
    private static final Map<Material, Integer> toId = new HashMap<>();
    private static final Map<Material, Byte> toData = new HashMap<>();

    public static BlockData getBlockDataFor(int material) {
        BlockData blockData = toBlockData.get("" + material);
        if (blockData != null) return blockData;
        else return toBlockData.get("0");
    }

    public static BlockData getBlockDataFor(int material, byte data) {
        if (data > 0) {
            BlockData blockData = toBlockData.get(material + ":" + data);
            if (blockData == null) return getBlockDataFor(material);
            else return blockData;
        } else return getBlockDataFor(material);
    }

    public static BlockData getBlockDataFor(int material, int data) {
        return getBlockDataFor(material, (byte) data);
    }

    public static int getIdFor(Material material) {
        return toId.getOrDefault(material, 0);
    }

    public static int getIdFor(BlockData data) {
        return toId.getOrDefault(data.getMaterial(), 0);
    }

    public static byte getDataFor(BlockData data) {
        return toData.getOrDefault(data.getMaterial(), (byte) 0);
    }

    static {
        toBlockData.put("0", Material.AIR.createBlockData());
        toBlockData.put("1", Material.STONE.createBlockData());
        toBlockData.put("1:1", Material.GRANITE.createBlockData());
        toBlockData.put("1:2", Material.POLISHED_GRANITE.createBlockData());
        toBlockData.put("1:3", Material.DIORITE.createBlockData());
        toBlockData.put("1:4", Material.POLISHED_DIORITE.createBlockData());
        toBlockData.put("1:5", Material.ANDESITE.createBlockData());
        toBlockData.put("1:6", Material.POLISHED_ANDESITE.createBlockData());
        toBlockData.put("2", Material.GRASS_BLOCK.createBlockData());
        toBlockData.put("3", Material.DIRT.createBlockData());
        toBlockData.put("3:1", Material.COARSE_DIRT.createBlockData());
        toBlockData.put("3:2", Material.PODZOL.createBlockData());
        toBlockData.put("4", Material.COBBLESTONE.createBlockData());
        toBlockData.put("5", Material.OAK_PLANKS.createBlockData());
        toBlockData.put("5:1", Material.SPRUCE_PLANKS.createBlockData());
        toBlockData.put("5:2", Material.BIRCH_PLANKS.createBlockData());
        toBlockData.put("5:3", Material.JUNGLE_PLANKS.createBlockData());
        toBlockData.put("5:4", Material.ACACIA_PLANKS.createBlockData());
        toBlockData.put("5:5", Material.DARK_OAK_PLANKS.createBlockData());
        toBlockData.put("6", Material.OAK_SAPLING.createBlockData());
        toBlockData.put("6:1", Material.SPRUCE_SAPLING.createBlockData());
        toBlockData.put("6:2", Material.BIRCH_SAPLING.createBlockData());
        toBlockData.put("6:3", Material.JUNGLE_SAPLING.createBlockData());
        toBlockData.put("6:4", Material.ACACIA_SAPLING.createBlockData());
        toBlockData.put("6:5", Material.DARK_OAK_SAPLING.createBlockData());
        toBlockData.put("7", Material.BEDROCK.createBlockData());
        toBlockData.put("8", Material.WATER.createBlockData()); // TODO add flowing
        toBlockData.put("9", Material.WATER.createBlockData()); // TODO add still
        toBlockData.put("10", Material.LAVA.createBlockData()); // TODO add flowing
        toBlockData.put("11", Material.LAVA.createBlockData()); // TODO add still
        toBlockData.put("12", Material.SAND.createBlockData());
        toBlockData.put("12:1", Material.RED_SAND.createBlockData());
        toBlockData.put("13", Material.GRAVEL.createBlockData());
        toBlockData.put("14", Material.GOLD_ORE.createBlockData());
        toBlockData.put("15", Material.IRON_ORE.createBlockData());
        toBlockData.put("16", Material.COAL_ORE.createBlockData());
        toBlockData.put("17", Material.OAK_LOG.createBlockData());
        toBlockData.put("17:1", Material.SPRUCE_LOG.createBlockData());
        toBlockData.put("17:2", Material.BIRCH_LOG.createBlockData());
        toBlockData.put("17:3", Material.JUNGLE_LOG.createBlockData());
        toBlockData.put("18", Material.OAK_LEAVES.createBlockData());
        toBlockData.put("18:1", Material.SPRUCE_LEAVES.createBlockData());
        toBlockData.put("18:2", Material.BIRCH_LEAVES.createBlockData());
        toBlockData.put("18:3", Material.JUNGLE_LEAVES.createBlockData());
        toBlockData.put("19", Material.SPONGE.createBlockData());
        toBlockData.put("19:1", Material.WET_SPONGE.createBlockData());
        toBlockData.put("20", Material.GLASS.createBlockData());
        toBlockData.put("21", Material.LAPIS_ORE.createBlockData());
        toBlockData.put("22", Material.LAPIS_BLOCK.createBlockData());
        toBlockData.put("23", Material.DISPENSER.createBlockData());
        toBlockData.put("24", Material.SANDSTONE.createBlockData());
        toBlockData.put("24:1", Material.CHISELED_SANDSTONE.createBlockData());
        toBlockData.put("24:2", Material.SMOOTH_SANDSTONE.createBlockData());
        toBlockData.put("25", Material.NOTE_BLOCK.createBlockData());
        toBlockData.put("26", Material.RED_BED.createBlockData());
        toBlockData.put("27", Material.POWERED_RAIL.createBlockData());
        toBlockData.put("28", Material.DETECTOR_RAIL.createBlockData());
        toBlockData.put("29", Material.STICKY_PISTON.createBlockData());
        toBlockData.put("30", Material.COBWEB.createBlockData());
        toBlockData.put("31", Material.DEAD_BUSH.createBlockData());
        toBlockData.put("31:1", Material.GRASS.createBlockData());
        toBlockData.put("31:2", Material.FERN.createBlockData());
        toBlockData.put("32", Material.DEAD_BUSH.createBlockData());
        toBlockData.put("33", Material.PISTON.createBlockData());
        toBlockData.put("34", Material.PISTON_HEAD.createBlockData());
        toBlockData.put("35", Material.WHITE_WOOL.createBlockData());
        toBlockData.put("35:1", Material.ORANGE_WOOL.createBlockData());
        toBlockData.put("35:2", Material.MAGENTA_WOOL.createBlockData());
        toBlockData.put("35:3", Material.LIGHT_BLUE_WOOL.createBlockData());
        toBlockData.put("35:4", Material.YELLOW_WOOL.createBlockData());
        toBlockData.put("35:5", Material.LIME_WOOL.createBlockData());
        toBlockData.put("35:6", Material.PINK_WOOL.createBlockData());
        toBlockData.put("35:7", Material.GRAY_WOOL.createBlockData());
        toBlockData.put("35:8", Material.LIGHT_GRAY_WOOL.createBlockData());
        toBlockData.put("35:9", Material.CYAN_WOOL.createBlockData());
        toBlockData.put("35:10", Material.PURPLE_WOOL.createBlockData());
        toBlockData.put("35:11", Material.BLUE_WOOL.createBlockData());
        toBlockData.put("35:12", Material.BROWN_WOOL.createBlockData());
        toBlockData.put("35:13", Material.GREEN_WOOL.createBlockData());
        toBlockData.put("35:14", Material.RED_WOOL.createBlockData());
        toBlockData.put("35:15", Material.BLACK_WOOL.createBlockData());
        toBlockData.put("36", Material.AIR.createBlockData()); // nothing
        toBlockData.put("37", Material.DANDELION.createBlockData());
        toBlockData.put("38", Material.POPPY.createBlockData());
        toBlockData.put("38:1", Material.BLUE_ORCHID.createBlockData());
        toBlockData.put("38:2", Material.ALLIUM.createBlockData());
        toBlockData.put("38:3", Material.AZURE_BLUET.createBlockData());
        toBlockData.put("38:4", Material.RED_TULIP.createBlockData());
        toBlockData.put("38:5", Material.ORANGE_TULIP.createBlockData());
        toBlockData.put("38:6", Material.WHITE_TULIP.createBlockData());
        toBlockData.put("38:7", Material.PINK_TULIP.createBlockData());
        toBlockData.put("38:8", Material.OXEYE_DAISY.createBlockData());
        toBlockData.put("39", Material.BROWN_MUSHROOM.createBlockData());
        toBlockData.put("40", Material.RED_MUSHROOM.createBlockData());
        toBlockData.put("41", Material.GOLD_BLOCK.createBlockData());
        toBlockData.put("42", Material.IRON_BLOCK.createBlockData());
        toBlockData.put("43", Material.STONE_SLAB.createBlockData()); // TODO set double slab on block data
        toBlockData.put("43:1", Material.SANDSTONE_SLAB.createBlockData()); // TODO set double slab on block data
        toBlockData.put("43:2", Material.OAK_SLAB.createBlockData()); // TODO set double slab on block data
        toBlockData.put("43:3", Material.COBBLESTONE_SLAB.createBlockData()); // TODO set double slab on block data
        toBlockData.put("43:4", Material.BRICK_SLAB.createBlockData()); // TODO set double slab on block data
        toBlockData.put("43:5", Material.STONE_BRICK_SLAB.createBlockData()); // TODO set double slab on block data
        toBlockData.put("43:6", Material.NETHER_BRICK_SLAB.createBlockData()); // TODO set double slab on block data
        toBlockData.put("43:7", Material.QUARTZ_SLAB.createBlockData()); // TODO set double slab on block data
        toBlockData.put("44", Material.STONE_SLAB.createBlockData());
        toBlockData.put("44:1", Material.SANDSTONE_SLAB.createBlockData());
        toBlockData.put("44:2", Material.OAK_SLAB.createBlockData()); // TODO add other wood variants as extra data values
        toBlockData.put("44:3", Material.COBBLESTONE_SLAB.createBlockData());
        toBlockData.put("44:4", Material.BRICK_SLAB.createBlockData());
        toBlockData.put("44:5", Material.STONE_BRICK_SLAB.createBlockData());
        toBlockData.put("44:6", Material.NETHER_BRICK_SLAB.createBlockData());
        toBlockData.put("44:7", Material.QUARTZ_SLAB.createBlockData());
        toBlockData.put("45", Material.BRICKS.createBlockData());
        toBlockData.put("46", Material.TNT.createBlockData());
        toBlockData.put("47", Material.BOOKSHELF.createBlockData());
        toBlockData.put("48", Material.MOSSY_COBBLESTONE.createBlockData());
        toBlockData.put("49", Material.OBSIDIAN.createBlockData());
        toBlockData.put("50", Material.TORCH.createBlockData());
        toBlockData.put("51", Material.FIRE.createBlockData());
        toBlockData.put("52", Material.SPAWNER.createBlockData());
        toBlockData.put("53", Material.OAK_STAIRS.createBlockData()); // TODO add other wood variants as extra data values
        toBlockData.put("54", Material.CHEST.createBlockData());
        toBlockData.put("55", Material.REDSTONE_WIRE.createBlockData());
        toBlockData.put("56", Material.DIAMOND_ORE.createBlockData());
        toBlockData.put("57", Material.DIAMOND_BLOCK.createBlockData());
        toBlockData.put("58", Material.CRAFTING_TABLE.createBlockData());
        toBlockData.put("59", Material.WHEAT.createBlockData());
        toBlockData.put("60", Material.FARMLAND.createBlockData());
        toBlockData.put("61", Material.FURNACE.createBlockData());
        toBlockData.put("62", Material.FURNACE.createBlockData()); // TODO set burning
        toBlockData.put("63", Material.OAK_SIGN.createBlockData()); // TODO add other wood data values
        toBlockData.put("64", Material.OAK_DOOR.createBlockData()); // TODO add other wood data values
        toBlockData.put("65", Material.LADDER.createBlockData());
        toBlockData.put("66", Material.RAIL.createBlockData());
        toBlockData.put("67", Material.COBBLESTONE_STAIRS.createBlockData());
        toBlockData.put("68", Material.OAK_WALL_SIGN.createBlockData()); // TODO add other wood data values
        toBlockData.put("69", Material.LEVER.createBlockData());
        toBlockData.put("70", Material.STONE_PRESSURE_PLATE.createBlockData());
        toBlockData.put("71", Material.IRON_DOOR.createBlockData());
        toBlockData.put("72", Material.OAK_PRESSURE_PLATE.createBlockData()); // TODO add other wood data values
        toBlockData.put("73", Material.REDSTONE_ORE.createBlockData());
        toBlockData.put("74", Material.REDSTONE_ORE.createBlockData()); // TODO set glowing
        toBlockData.put("75", Material.REDSTONE_TORCH.createBlockData());
        toBlockData.put("76", Material.REDSTONE_TORCH.createBlockData()); // TODO set on
        toBlockData.put("77", Material.STONE_BUTTON.createBlockData());
        toBlockData.put("78", Material.SNOW.createBlockData());
        toBlockData.put("79", Material.ICE.createBlockData());
        toBlockData.put("80", Material.SNOW_BLOCK.createBlockData());
        toBlockData.put("81", Material.CACTUS.createBlockData());
        toBlockData.put("82", Material.CLAY.createBlockData());
        toBlockData.put("83", Material.SUGAR_CANE.createBlockData());
        toBlockData.put("84", Material.JUKEBOX.createBlockData());
        toBlockData.put("85", Material.OAK_FENCE.createBlockData()); // TODO add other wood data values
        toBlockData.put("86", Material.PUMPKIN.createBlockData());
        toBlockData.put("87", Material.NETHERRACK.createBlockData());
        toBlockData.put("88", Material.SOUL_SAND.createBlockData());
        toBlockData.put("89", Material.GLOWSTONE.createBlockData());
        toBlockData.put("90", Material.NETHER_PORTAL.createBlockData());
        toBlockData.put("91", Material.JACK_O_LANTERN.createBlockData());
        toBlockData.put("92", Material.CAKE.createBlockData());
        toBlockData.put("93", Material.REPEATER.createBlockData());
        toBlockData.put("94", Material.REPEATER.createBlockData()); // TODO set on
        toBlockData.put("95", Material.WHITE_STAINED_GLASS.createBlockData());
        toBlockData.put("95:1", Material.ORANGE_STAINED_GLASS.createBlockData());
        toBlockData.put("95:2", Material.MAGENTA_STAINED_GLASS.createBlockData());
        toBlockData.put("95:3", Material.LIGHT_BLUE_STAINED_GLASS.createBlockData());
        toBlockData.put("95:4", Material.YELLOW_STAINED_GLASS.createBlockData());
        toBlockData.put("95:5", Material.LIME_STAINED_GLASS.createBlockData());
        toBlockData.put("95:6", Material.PINK_STAINED_GLASS.createBlockData());
        toBlockData.put("95:7", Material.GRAY_STAINED_GLASS.createBlockData());
        toBlockData.put("95:8", Material.LIGHT_GRAY_STAINED_GLASS.createBlockData());
        toBlockData.put("95:9", Material.CYAN_STAINED_GLASS.createBlockData());
        toBlockData.put("95:10", Material.PURPLE_STAINED_GLASS.createBlockData());
        toBlockData.put("95:11", Material.BLUE_STAINED_GLASS.createBlockData());
        toBlockData.put("95:12", Material.BROWN_STAINED_GLASS.createBlockData());
        toBlockData.put("95:13", Material.GREEN_STAINED_GLASS.createBlockData());
        toBlockData.put("95:14", Material.RED_STAINED_GLASS.createBlockData());
        toBlockData.put("95:15", Material.BLACK_STAINED_GLASS.createBlockData());
        toBlockData.put("96", Material.OAK_TRAPDOOR.createBlockData()); // TODO add other woods
        toBlockData.put("97", Material.INFESTED_STONE.createBlockData());
        toBlockData.put("97:1", Material.INFESTED_COBBLESTONE.createBlockData());
        toBlockData.put("97:2", Material.INFESTED_STONE_BRICKS.createBlockData());
        toBlockData.put("97:3", Material.INFESTED_MOSSY_STONE_BRICKS.createBlockData());
        toBlockData.put("97:4", Material.INFESTED_CRACKED_STONE_BRICKS.createBlockData());
        toBlockData.put("97:5", Material.INFESTED_CHISELED_STONE_BRICKS.createBlockData());
        toBlockData.put("98", Material.STONE_BRICKS.createBlockData());
        toBlockData.put("98:1", Material.MOSSY_STONE_BRICKS.createBlockData());
        toBlockData.put("98:2", Material.CRACKED_STONE_BRICKS.createBlockData());
        toBlockData.put("98:3", Material.CHISELED_STONE_BRICKS.createBlockData());
        toBlockData.put("99", Material.BROWN_MUSHROOM_BLOCK.createBlockData());
        toBlockData.put("100", Material.RED_MUSHROOM_BLOCK.createBlockData());
        toBlockData.put("101", Material.IRON_BARS.createBlockData());
        toBlockData.put("102", Material.GLASS_PANE.createBlockData()); // TODO add other color data values
        toBlockData.put("103", Material.MELON.createBlockData());
        toBlockData.put("104", Material.PUMPKIN_STEM.createBlockData());
        toBlockData.put("105", Material.MELON_STEM.createBlockData());
        toBlockData.put("106", Material.VINE.createBlockData());
        toBlockData.put("107", Material.OAK_FENCE_GATE.createBlockData()); // TODO add other woods
        toBlockData.put("108", Material.BRICK_STAIRS.createBlockData());
        toBlockData.put("109", Material.STONE_BRICK_STAIRS.createBlockData());
        toBlockData.put("110", Material.MYCELIUM.createBlockData());
        toBlockData.put("111", Material.LILY_PAD.createBlockData());
        toBlockData.put("112", Material.NETHER_BRICKS.createBlockData());
        toBlockData.put("113", Material.NETHER_BRICK_FENCE.createBlockData());
        toBlockData.put("114", Material.NETHER_BRICK_STAIRS.createBlockData());
        toBlockData.put("115", Material.NETHER_WART.createBlockData());
        toBlockData.put("116", Material.ENCHANTING_TABLE.createBlockData());
        toBlockData.put("117", Material.BREWING_STAND.createBlockData());
        toBlockData.put("118", Material.CAULDRON.createBlockData());
        toBlockData.put("119", Material.END_PORTAL.createBlockData());
        toBlockData.put("120", Material.END_PORTAL_FRAME.createBlockData());
        toBlockData.put("121", Material.END_STONE.createBlockData());
        toBlockData.put("122", Material.DRAGON_EGG.createBlockData());
        toBlockData.put("123", Material.REDSTONE_LAMP.createBlockData());
        toBlockData.put("124", Material.REDSTONE_LAMP.createBlockData()); // TODO set active
        toBlockData.put("125", Material.OAK_PLANKS.createBlockData()); // TODO set double slab
        toBlockData.put("125:1", Material.SPRUCE_PLANKS.createBlockData()); // TODO set double slab
        toBlockData.put("125:2", Material.BIRCH_PLANKS.createBlockData()); // TODO set double slab
        toBlockData.put("125:3", Material.JUNGLE_PLANKS.createBlockData()); // TODO set double slab
        toBlockData.put("125:4", Material.ACACIA_PLANKS.createBlockData()); // TODO set double slab
        toBlockData.put("125:5", Material.DARK_OAK_PLANKS.createBlockData()); // TODO set double slab
        toBlockData.put("126", Material.OAK_SLAB.createBlockData());
        toBlockData.put("126:1", Material.SPRUCE_SLAB.createBlockData());
        toBlockData.put("126:2", Material.BIRCH_SLAB.createBlockData());
        toBlockData.put("126:3", Material.JUNGLE_SLAB.createBlockData());
        toBlockData.put("126:4", Material.ACACIA_SLAB.createBlockData());
        toBlockData.put("126:5", Material.DARK_OAK_SLAB.createBlockData());
        toBlockData.put("127", Material.COCOA.createBlockData());
        toBlockData.put("128", Material.SANDSTONE_STAIRS.createBlockData());
        toBlockData.put("129", Material.EMERALD_ORE.createBlockData());
        toBlockData.put("130", Material.ENDER_CHEST.createBlockData());
        toBlockData.put("131", Material.TRIPWIRE_HOOK.createBlockData());
        toBlockData.put("132", Material.TRIPWIRE.createBlockData());
        toBlockData.put("133", Material.EMERALD_BLOCK.createBlockData());
        toBlockData.put("134", Material.SPRUCE_STAIRS.createBlockData());
        toBlockData.put("135", Material.BIRCH_STAIRS.createBlockData());
        toBlockData.put("136", Material.JUNGLE_STAIRS.createBlockData());
        toBlockData.put("137", Material.COMMAND_BLOCK.createBlockData());
        toBlockData.put("138", Material.BEACON.createBlockData());
        toBlockData.put("139", Material.COBBLESTONE_WALL.createBlockData());
        toBlockData.put("139:1", Material.MOSSY_COBBLESTONE_WALL.createBlockData());
        toBlockData.put("140", Material.FLOWER_POT.createBlockData());
        toBlockData.put("141", Material.CARROTS.createBlockData());
        toBlockData.put("142", Material.POTATOES.createBlockData());
        toBlockData.put("143", Material.OAK_BUTTON.createBlockData()); // TODO add other wood data values
        toBlockData.put("144", Material.SKELETON_SKULL.createBlockData());
        toBlockData.put("145", Material.ANVIL.createBlockData());
        toBlockData.put("146", Material.TRAPPED_CHEST.createBlockData());
        toBlockData.put("147", Material.LIGHT_WEIGHTED_PRESSURE_PLATE.createBlockData());
        toBlockData.put("148", Material.HEAVY_WEIGHTED_PRESSURE_PLATE.createBlockData());
        toBlockData.put("149", Material.COMPARATOR.createBlockData());
        toBlockData.put("150", Material.COMPARATOR.createBlockData()); // TODO set active
        toBlockData.put("151", Material.DAYLIGHT_DETECTOR.createBlockData());
        toBlockData.put("152", Material.REDSTONE_BLOCK.createBlockData());
        toBlockData.put("153", Material.NETHER_QUARTZ_ORE.createBlockData());
        toBlockData.put("154", Material.HOPPER.createBlockData());
        toBlockData.put("155", Material.QUARTZ_BLOCK.createBlockData());
        toBlockData.put("155:1", Material.CHISELED_QUARTZ_BLOCK.createBlockData());
        toBlockData.put("155:2", Material.QUARTZ_PILLAR.createBlockData());
        toBlockData.put("156", Material.QUARTZ_STAIRS.createBlockData());
        toBlockData.put("157", Material.ACTIVATOR_RAIL.createBlockData());
        toBlockData.put("158", Material.DROPPER.createBlockData());
        toBlockData.put("159", Material.WHITE_TERRACOTTA.createBlockData());
        toBlockData.put("159:1", Material.ORANGE_TERRACOTTA.createBlockData());
        toBlockData.put("159:2", Material.MAGENTA_TERRACOTTA.createBlockData());
        toBlockData.put("159:3", Material.LIGHT_BLUE_TERRACOTTA.createBlockData());
        toBlockData.put("159:4", Material.YELLOW_TERRACOTTA.createBlockData());
        toBlockData.put("159:5", Material.LIME_TERRACOTTA.createBlockData());
        toBlockData.put("159:6", Material.PINK_TERRACOTTA.createBlockData());
        toBlockData.put("159:7", Material.GRAY_TERRACOTTA.createBlockData());
        toBlockData.put("159:8", Material.LIGHT_GRAY_TERRACOTTA.createBlockData());
        toBlockData.put("159:9", Material.CYAN_TERRACOTTA.createBlockData());
        toBlockData.put("159:10", Material.PURPLE_TERRACOTTA.createBlockData());
        toBlockData.put("159:11", Material.BLUE_TERRACOTTA.createBlockData());
        toBlockData.put("159:12", Material.BROWN_TERRACOTTA.createBlockData());
        toBlockData.put("159:13", Material.GREEN_TERRACOTTA.createBlockData());
        toBlockData.put("159:14", Material.RED_TERRACOTTA.createBlockData());
        toBlockData.put("159:15", Material.BLACK_TERRACOTTA.createBlockData());
        toBlockData.put("160", Material.WHITE_STAINED_GLASS_PANE.createBlockData());
        toBlockData.put("160:1", Material.ORANGE_STAINED_GLASS_PANE.createBlockData());
        toBlockData.put("160:2", Material.MAGENTA_STAINED_GLASS_PANE.createBlockData());
        toBlockData.put("160:3", Material.LIGHT_BLUE_STAINED_GLASS_PANE.createBlockData());
        toBlockData.put("160:4", Material.YELLOW_STAINED_GLASS_PANE.createBlockData());
        toBlockData.put("160:5", Material.LIME_STAINED_GLASS_PANE.createBlockData());
        toBlockData.put("160:6", Material.PINK_STAINED_GLASS_PANE.createBlockData());
        toBlockData.put("160:7", Material.GRAY_STAINED_GLASS_PANE.createBlockData());
        toBlockData.put("160:8", Material.LIGHT_GRAY_STAINED_GLASS_PANE.createBlockData());
        toBlockData.put("160:9", Material.CYAN_STAINED_GLASS_PANE.createBlockData());
        toBlockData.put("160:10", Material.PURPLE_STAINED_GLASS_PANE.createBlockData());
        toBlockData.put("160:11", Material.BLUE_STAINED_GLASS_PANE.createBlockData());
        toBlockData.put("160:12", Material.BROWN_STAINED_GLASS_PANE.createBlockData());
        toBlockData.put("160:13", Material.GREEN_STAINED_GLASS_PANE.createBlockData());
        toBlockData.put("160:14", Material.RED_STAINED_GLASS_PANE.createBlockData());
        toBlockData.put("160:15", Material.BLACK_STAINED_GLASS_PANE.createBlockData());
        toBlockData.put("161", Material.ACACIA_LEAVES.createBlockData());
        toBlockData.put("161:1", Material.DARK_OAK_LEAVES.createBlockData());
        toBlockData.put("162", Material.ACACIA_WOOD.createBlockData());
        toBlockData.put("162:1", Material.DARK_OAK_WOOD.createBlockData());
        toBlockData.put("163", Material.ACACIA_STAIRS.createBlockData());
        toBlockData.put("164", Material.DARK_OAK_STAIRS.createBlockData());
        toBlockData.put("165", Material.SLIME_BLOCK.createBlockData());
        toBlockData.put("166", Material.BARRIER.createBlockData());
        toBlockData.put("167", Material.IRON_TRAPDOOR.createBlockData());
        toBlockData.put("168", Material.PRISMARINE.createBlockData());
        toBlockData.put("168:1", Material.PRISMARINE_BRICKS.createBlockData());
        toBlockData.put("168:2", Material.DARK_PRISMARINE.createBlockData());
        toBlockData.put("169", Material.SEA_LANTERN.createBlockData());
        toBlockData.put("170", Material.HAY_BLOCK.createBlockData());
        toBlockData.put("171", Material.WHITE_CARPET.createBlockData());
        toBlockData.put("171:1", Material.ORANGE_CARPET.createBlockData());
        toBlockData.put("171:2", Material.MAGENTA_CARPET.createBlockData());
        toBlockData.put("171:3", Material.LIGHT_BLUE_CARPET.createBlockData());
        toBlockData.put("171:4", Material.YELLOW_CARPET.createBlockData());
        toBlockData.put("171:5", Material.LIME_CARPET.createBlockData());
        toBlockData.put("171:6", Material.PINK_CARPET.createBlockData());
        toBlockData.put("171:7", Material.GRAY_CARPET.createBlockData());
        toBlockData.put("171:8", Material.LIGHT_GRAY_CARPET.createBlockData());
        toBlockData.put("171:9", Material.CYAN_CARPET.createBlockData());
        toBlockData.put("171:10", Material.PURPLE_CARPET.createBlockData());
        toBlockData.put("171:11", Material.BLUE_CARPET.createBlockData());
        toBlockData.put("171:12", Material.BROWN_CARPET.createBlockData());
        toBlockData.put("171:13", Material.GREEN_CARPET.createBlockData());
        toBlockData.put("171:14", Material.RED_CARPET.createBlockData());
        toBlockData.put("171:15", Material.BLACK_CARPET.createBlockData());
        toBlockData.put("172", Material.TERRACOTTA.createBlockData());
        toBlockData.put("173", Material.COAL_BLOCK.createBlockData());
        toBlockData.put("174", Material.PACKED_ICE.createBlockData());
        toBlockData.put("175", Material.SUNFLOWER.createBlockData());
        toBlockData.put("175:1", Material.LILAC.createBlockData());
        toBlockData.put("175:2", Material.TALL_GRASS.createBlockData());
        toBlockData.put("175:3", Material.LARGE_FERN.createBlockData());
        toBlockData.put("175:4", Material.ROSE_BUSH.createBlockData());
        toBlockData.put("175:5", Material.PEONY.createBlockData());
        toBlockData.put("176", Material.WHITE_BANNER.createBlockData());
        toBlockData.put("177", Material.WHITE_WALL_BANNER.createBlockData()); // TODO add other colors
        toBlockData.put("178", Material.DAYLIGHT_DETECTOR.createBlockData()); // TODO set inverted
        toBlockData.put("179", Material.RED_SANDSTONE.createBlockData());
        toBlockData.put("179:1", Material.CHISELED_RED_SANDSTONE.createBlockData());
        toBlockData.put("179:2", Material.SMOOTH_RED_SANDSTONE.createBlockData());
        toBlockData.put("180", Material.RED_SANDSTONE_STAIRS.createBlockData());
        toBlockData.put("181", Material.RED_SANDSTONE.createBlockData()); // TODO double slab
        toBlockData.put("182", Material.RED_SANDSTONE_SLAB.createBlockData());
        toBlockData.put("183", Material.SPRUCE_FENCE_GATE.createBlockData());
        toBlockData.put("184", Material.BIRCH_FENCE_GATE.createBlockData());
        toBlockData.put("185", Material.JUNGLE_FENCE_GATE.createBlockData());
        toBlockData.put("186", Material.DARK_OAK_FENCE_GATE.createBlockData());
        toBlockData.put("187", Material.ACACIA_FENCE_GATE.createBlockData());
        toBlockData.put("188", Material.SPRUCE_FENCE.createBlockData());
        toBlockData.put("189", Material.BIRCH_FENCE.createBlockData());
        toBlockData.put("190", Material.JUNGLE_FENCE.createBlockData());
        toBlockData.put("191", Material.DARK_OAK_FENCE.createBlockData());
        toBlockData.put("192", Material.ACACIA_FENCE.createBlockData());
        toBlockData.put("193", Material.SPRUCE_DOOR.createBlockData());
        toBlockData.put("194", Material.BIRCH_DOOR.createBlockData());
        toBlockData.put("195", Material.JUNGLE_DOOR.createBlockData());
        toBlockData.put("196", Material.ACACIA_DOOR.createBlockData());
        toBlockData.put("197", Material.DARK_OAK_DOOR.createBlockData());
        toBlockData.put("198", Material.END_ROD.createBlockData());
        toBlockData.put("199", Material.CHORUS_PLANT.createBlockData());
        toBlockData.put("200", Material.CHORUS_FLOWER.createBlockData());
        toBlockData.put("201", Material.PURPUR_BLOCK.createBlockData());
        toBlockData.put("202", Material.PURPUR_PILLAR.createBlockData());
        toBlockData.put("203", Material.PURPUR_STAIRS.createBlockData());
        toBlockData.put("204", Material.PURPUR_BLOCK.createBlockData()); // TODO double slab
        toBlockData.put("205", Material.PURPUR_SLAB.createBlockData());
        toBlockData.put("206", Material.END_STONE_BRICKS.createBlockData());
        toBlockData.put("207", Material.BEETROOTS.createBlockData());
        toBlockData.put("208", Material.GRASS_PATH.createBlockData());
        toBlockData.put("209", Material.END_GATEWAY.createBlockData());
        toBlockData.put("210", Material.COMMAND_BLOCK.createBlockData()); // TODO set repeating
        toBlockData.put("211", Material.COMMAND_BLOCK.createBlockData()); // TODO set chain
        toBlockData.put("212", Material.FROSTED_ICE.createBlockData());
        toBlockData.put("213", Material.MAGMA_BLOCK.createBlockData());
        toBlockData.put("214", Material.NETHER_WART_BLOCK.createBlockData());
        toBlockData.put("215", Material.RED_NETHER_BRICKS.createBlockData());
        toBlockData.put("216", Material.BONE_BLOCK.createBlockData());
        toBlockData.put("217", Material.STRUCTURE_VOID.createBlockData());
        toBlockData.put("218", Material.OBSERVER.createBlockData());
        toBlockData.put("219", Material.WHITE_SHULKER_BOX.createBlockData());
        toBlockData.put("220", Material.ORANGE_SHULKER_BOX.createBlockData());
        toBlockData.put("221", Material.MAGENTA_SHULKER_BOX.createBlockData());
        toBlockData.put("222", Material.LIGHT_BLUE_SHULKER_BOX.createBlockData());
        toBlockData.put("223", Material.YELLOW_SHULKER_BOX.createBlockData());
        toBlockData.put("224", Material.LIME_SHULKER_BOX.createBlockData());
        toBlockData.put("225", Material.PINK_SHULKER_BOX.createBlockData());
        toBlockData.put("226", Material.GRAY_SHULKER_BOX.createBlockData());
        toBlockData.put("227", Material.LIGHT_GRAY_SHULKER_BOX.createBlockData());
        toBlockData.put("228", Material.CYAN_SHULKER_BOX.createBlockData());
        toBlockData.put("229", Material.PURPLE_SHULKER_BOX.createBlockData());
        toBlockData.put("230", Material.BLUE_SHULKER_BOX.createBlockData());
        toBlockData.put("231", Material.BROWN_SHULKER_BOX.createBlockData());
        toBlockData.put("232", Material.GREEN_SHULKER_BOX.createBlockData());
        toBlockData.put("233", Material.RED_SHULKER_BOX.createBlockData());
        toBlockData.put("234", Material.BLACK_SHULKER_BOX.createBlockData());
        toBlockData.put("235", Material.WHITE_GLAZED_TERRACOTTA.createBlockData());
        toBlockData.put("236", Material.ORANGE_GLAZED_TERRACOTTA.createBlockData());
        toBlockData.put("237", Material.MAGENTA_GLAZED_TERRACOTTA.createBlockData());
        toBlockData.put("238", Material.LIGHT_BLUE_GLAZED_TERRACOTTA.createBlockData());
        toBlockData.put("239", Material.YELLOW_GLAZED_TERRACOTTA.createBlockData());
        toBlockData.put("240", Material.LIME_GLAZED_TERRACOTTA.createBlockData());
        toBlockData.put("241", Material.PINK_GLAZED_TERRACOTTA.createBlockData());
        toBlockData.put("242", Material.GRAY_GLAZED_TERRACOTTA.createBlockData());
        toBlockData.put("243", Material.LIGHT_GRAY_GLAZED_TERRACOTTA.createBlockData());
        toBlockData.put("244", Material.CYAN_GLAZED_TERRACOTTA.createBlockData());
        toBlockData.put("245", Material.PURPLE_GLAZED_TERRACOTTA.createBlockData());
        toBlockData.put("246", Material.BLUE_GLAZED_TERRACOTTA.createBlockData());
        toBlockData.put("247", Material.BROWN_GLAZED_TERRACOTTA.createBlockData());
        toBlockData.put("248", Material.GREEN_GLAZED_TERRACOTTA.createBlockData());
        toBlockData.put("249", Material.RED_GLAZED_TERRACOTTA.createBlockData());
        toBlockData.put("250", Material.BLACK_GLAZED_TERRACOTTA.createBlockData());
        toBlockData.put("251", Material.WHITE_CONCRETE.createBlockData());
        toBlockData.put("251:1", Material.ORANGE_CONCRETE.createBlockData());
        toBlockData.put("251:2", Material.MAGENTA_CONCRETE.createBlockData());
        toBlockData.put("251:3", Material.LIGHT_BLUE_CONCRETE.createBlockData());
        toBlockData.put("251:4", Material.YELLOW_CONCRETE.createBlockData());
        toBlockData.put("251:5", Material.LIME_CONCRETE.createBlockData());
        toBlockData.put("251:6", Material.PINK_CONCRETE.createBlockData());
        toBlockData.put("251:7", Material.GRAY_CONCRETE.createBlockData());
        toBlockData.put("251:8", Material.LIGHT_GRAY_CONCRETE.createBlockData());
        toBlockData.put("251:9", Material.CYAN_CONCRETE.createBlockData());
        toBlockData.put("251:10", Material.PURPLE_CONCRETE.createBlockData());
        toBlockData.put("251:11", Material.BLUE_CONCRETE.createBlockData());
        toBlockData.put("251:12", Material.BROWN_CONCRETE.createBlockData());
        toBlockData.put("251:13", Material.GREEN_CONCRETE.createBlockData());
        toBlockData.put("251:14", Material.RED_CONCRETE.createBlockData());
        toBlockData.put("251:15", Material.BLACK_CONCRETE.createBlockData());
        toBlockData.put("252", Material.WHITE_CONCRETE_POWDER.createBlockData());
        toBlockData.put("252:1", Material.ORANGE_CONCRETE_POWDER.createBlockData());
        toBlockData.put("252:2", Material.MAGENTA_CONCRETE_POWDER.createBlockData());
        toBlockData.put("252:3", Material.LIGHT_BLUE_CONCRETE_POWDER.createBlockData());
        toBlockData.put("252:4", Material.YELLOW_CONCRETE_POWDER.createBlockData());
        toBlockData.put("252:5", Material.LIME_CONCRETE_POWDER.createBlockData());
        toBlockData.put("252:6", Material.PINK_CONCRETE_POWDER.createBlockData());
        toBlockData.put("252:7", Material.GRAY_CONCRETE_POWDER.createBlockData());
        toBlockData.put("252:8", Material.LIGHT_GRAY_CONCRETE_POWDER.createBlockData());
        toBlockData.put("252:9", Material.CYAN_CONCRETE_POWDER.createBlockData());
        toBlockData.put("252:10", Material.PURPLE_CONCRETE_POWDER.createBlockData());
        toBlockData.put("252:11", Material.BLUE_CONCRETE_POWDER.createBlockData());
        toBlockData.put("252:12", Material.BROWN_CONCRETE_POWDER.createBlockData());
        toBlockData.put("252:13", Material.GREEN_CONCRETE_POWDER.createBlockData());
        toBlockData.put("252:14", Material.RED_CONCRETE_POWDER.createBlockData());
        toBlockData.put("252:15", Material.BLACK_CONCRETE_POWDER.createBlockData());
        toBlockData.put("253", Material.AIR.createBlockData()); // nothing
        toBlockData.put("254", Material.AIR.createBlockData()); // nothing
        toBlockData.put("255", Material.STRUCTURE_BLOCK.createBlockData());

        toBlockData.forEach((key, value) -> {
            String[] splitKey = key.split(":");
            int id = Integer.parseInt(splitKey[0]);
            toId.put(value.getMaterial(), id);
            byte data = 0;
            if (splitKey.length > 1) data = Byte.parseByte(splitKey[1]);
            toData.put(value.getMaterial(), data);
        });
    }
}
