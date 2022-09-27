package com.thevoxelbox.voxelsniper.voxelsniper.entity.entitytype;

import com.thevoxelbox.voxelsniper.VoxelSniper;
import com.thevoxelbox.voxelsniper.voxelsniper.Version;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public record VoxelEntityType(String namespace, String key, Version version) {
    public static final String DEFAULT_NAMESPACE = "minecraft";
    public static Map<String, VoxelEntityType> ENTITYTYPES;
    public static VoxelEntityType DROPPED_ITEM = register("item");
    public static VoxelEntityType EXPERIENCE_ORB = register("experience_orb");
    public static VoxelEntityType AREA_EFFECT_CLOUD = register("area_effect_cloud");
    public static VoxelEntityType ELDER_GUARDIAN = register("elder_guardian");
    public static VoxelEntityType WITHER_SKELETON = register("wither_skeleton");
    public static VoxelEntityType STRAY = register("stray");
    public static VoxelEntityType EGG = register("egg");
    public static VoxelEntityType LEASH_HITCH = register("leash_knot");
    public static VoxelEntityType PAINTING = register("painting");
    public static VoxelEntityType ARROW = register("arrow");
    public static VoxelEntityType SNOWBALL = register("snowball");
    public static VoxelEntityType FIREBALL = register("fireball");
    public static VoxelEntityType SMALL_FIREBALL = register("small_fireball");
    public static VoxelEntityType ENDER_PEARL = register("ender_pearl");
    public static VoxelEntityType ENDER_SIGNAL = register("eye_of_ender");
    public static VoxelEntityType SPLASH_POTION = register("potion");
    public static VoxelEntityType THROWN_EXP_BOTTLE = register("experience_bottle");
    public static VoxelEntityType ITEM_FRAME = register("item_frame");
    public static VoxelEntityType WITHER_SKULL = register("wither_skull");
    public static VoxelEntityType PRIMED_TNT = register("tnt");
    public static VoxelEntityType FALLING_BLOCK = register("falling_block");
    public static VoxelEntityType FIREWORK = register("firework_rocket");
    public static VoxelEntityType HUSK = register("husk");
    public static VoxelEntityType SPECTRAL_ARROW = register("spectral_arrow");
    public static VoxelEntityType SHULKER_BULLET = register("shulker_bullet");
    public static VoxelEntityType DRAGON_FIREBALL = register("dragon_fireball");
    public static VoxelEntityType ZOMBIE_VILLAGER = register("zombie_villager");
    public static VoxelEntityType SKELETON_HORSE = register("skeleton_horse");
    public static VoxelEntityType ZOMBIE_HORSE = register("zombie_horse");
    public static VoxelEntityType ARMOR_STAND = register("armor_stand");
    public static VoxelEntityType DONKEY = register("donkey");
    public static VoxelEntityType MULE = register("mule");
    public static VoxelEntityType EVOKER_FANGS = register("evoker_fangs");
    public static VoxelEntityType EVOKER = register("evoker");
    public static VoxelEntityType VEX = register("vex");
    public static VoxelEntityType VINDICATOR = register("vindicator");
    public static VoxelEntityType ILLUSIONER = register("illusioner");
    public static VoxelEntityType MINECART_COMMAND = register("command_block_minecart");
    public static VoxelEntityType BOAT = register("boat");
    public static VoxelEntityType MINECART = register("minecart");
    public static VoxelEntityType MINECART_CHEST = register("chest_minecart");
    public static VoxelEntityType MINECART_FURNACE = register("furnace_minecart");
    public static VoxelEntityType MINECART_TNT = register("tnt_minecart");
    public static VoxelEntityType MINECART_HOPPER = register("hopper_minecart");
    public static VoxelEntityType MINECART_MOB_SPAWNER = register("spawner_minecart");
    public static VoxelEntityType CREEPER = register("creeper");
    public static VoxelEntityType SKELETON = register("skeleton");
    public static VoxelEntityType SPIDER = register("spider");
    public static VoxelEntityType GIANT = register("giant");
    public static VoxelEntityType ZOMBIE = register("zombie");
    public static VoxelEntityType SLIME = register("slime");
    public static VoxelEntityType GHAST = register("ghast");
    public static VoxelEntityType ZOMBIFIED_PIGLIN = register("zombified_piglin");
    public static VoxelEntityType ENDERMAN = register("enderman");
    public static VoxelEntityType CAVE_SPIDER = register("cave_spider");
    public static VoxelEntityType SILVERFISH = register("silverfish");
    public static VoxelEntityType BLAZE = register("blaze");
    public static VoxelEntityType MAGMA_CUBE = register("magma_cube");
    public static VoxelEntityType ENDER_DRAGON = register("ender_dragon");
    public static VoxelEntityType WITHER = register("wither");
    public static VoxelEntityType BAT = register("bat");
    public static VoxelEntityType WITCH = register("witch");
    public static VoxelEntityType ENDERMITE = register("endermite");
    public static VoxelEntityType GUARDIAN = register("guardian");
    public static VoxelEntityType SHULKER = register("shulker");
    public static VoxelEntityType PIG = register("pig");
    public static VoxelEntityType SHEEP = register("sheep");
    public static VoxelEntityType COW = register("cow");
    public static VoxelEntityType CHICKEN = register("chicken");
    public static VoxelEntityType SQUID = register("squid");
    public static VoxelEntityType WOLF = register("wolf");
    public static VoxelEntityType MUSHROOM_COW = register("mooshroom");
    public static VoxelEntityType SNOWMAN = register("snow_golem");
    public static VoxelEntityType OCELOT = register("ocelot");
    public static VoxelEntityType IRON_GOLEM = register("iron_golem");
    public static VoxelEntityType HORSE = register("horse");
    public static VoxelEntityType RABBIT = register("rabbit");
    public static VoxelEntityType POLAR_BEAR = register("polar_bear");
    public static VoxelEntityType LLAMA = register("llama");
    public static VoxelEntityType LLAMA_SPIT = register("llama_spit");
    public static VoxelEntityType PARROT = register("parrot");
    public static VoxelEntityType VILLAGER = register("villager");
    public static VoxelEntityType ENDER_CRYSTAL = register("end_crystal");
    public static VoxelEntityType TURTLE = register("turtle");
    public static VoxelEntityType PHANTOM = register("phantom");
    public static VoxelEntityType TRIDENT = register("trident");
    public static VoxelEntityType COD = register("cod");
    public static VoxelEntityType SALMON = register("salmon");
    public static VoxelEntityType PUFFERFISH = register("pufferfish");
    public static VoxelEntityType TROPICAL_FISH = register("tropical_fish");
    public static VoxelEntityType DROWNED = register("drowned");
    public static VoxelEntityType DOLPHIN = register("dolphin");
    public static VoxelEntityType CAT = register("cat");
    public static VoxelEntityType PANDA = register("panda");
    public static VoxelEntityType PILLAGER = register("pillager");
    public static VoxelEntityType RAVAGER = register("ravager");
    public static VoxelEntityType TRADER_LLAMA = register("trader_llama");
    public static VoxelEntityType WANDERING_TRADER = register("wandering_trader");
    public static VoxelEntityType FOX = register("fox");
    public static VoxelEntityType BEE = register("bee");
    public static VoxelEntityType HOGLIN = register("hoglin");
    public static VoxelEntityType PIGLIN = register("piglin");
    public static VoxelEntityType STRIDER = register("strider");
    public static VoxelEntityType ZOGLIN = register("zoglin");
    public static VoxelEntityType PIGLIN_BRUTE = register("piglin_brute");
    public static VoxelEntityType AXOLOTL = register("axolotl");
    public static VoxelEntityType GLOW_ITEM_FRAME = register("glow_item_frame");
    public static VoxelEntityType GLOW_SQUID = register("glow_squid");
    public static VoxelEntityType GOAT = register("goat");
    public static VoxelEntityType MARKER = register("marker");
    public static VoxelEntityType ALLAY = register("allay");
    public static VoxelEntityType CHEST_BOAT = register("chest_boat");
    public static VoxelEntityType FROG = register("frog");
    public static VoxelEntityType TADPOLE = register("tadpole");
    public static VoxelEntityType WARDEN = register("warden");
    public static VoxelEntityType FISHING_HOOK = register("fishing_bobber");
    public static VoxelEntityType LIGHTNING = register("lightning_bolt");
    public static VoxelEntityType PLAYER = register("player");

    public VoxelEntityType(String key) {
        this(DEFAULT_NAMESPACE, key, Version.V1_16);
    }

    private static VoxelEntityType register(String key) {
        return register(key, Version.V1_16);
    }

    private static VoxelEntityType register(String key, Version version) {
        var entityType = new VoxelEntityType("minecraft", key, version);
        if (ENTITYTYPES == null) {
            ENTITYTYPES = new HashMap<>();
        }
        ENTITYTYPES.put(entityType.getName(), entityType);
        return entityType;
    }

    public static VoxelEntityType getEntityType(String key) {
        return getEntityType("minecraft", key.toLowerCase(Locale.ROOT));
    }

    public static VoxelEntityType getEntityType(String namespace, String key) {
        var entityType = ENTITYTYPES.get(namespace + ":" + key);
        if (VoxelSniper.voxelsniper.getVersion().isSupported(entityType.getVersion())) {
            return entityType;
        }
        return null;
    }

    String getNamespace() {
        return namespace;
    }

    public String getKey() {
        return key;
    }

    String getName() {
        return namespace + ":" + key;
    }

    private Version getVersion() {
        return version;
    }
}
