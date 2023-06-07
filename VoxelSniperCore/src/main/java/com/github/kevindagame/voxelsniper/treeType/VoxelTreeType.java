package com.github.kevindagame.voxelsniper.treeType;

import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.voxelsniper.Version;

public enum VoxelTreeType {

    /**
     * Regular tree, no branches
     */
    TREE,
    /**
     * Regular tree, extra tall with branches
     */
    BIG_TREE,
    /**
     * Redwood tree, shaped like a pine tree
     */
    REDWOOD,
    /**
     * Tall redwood tree with just a few leaves at the top
     */
    TALL_REDWOOD,
    /**
     * Birch tree
     */
    BIRCH,
    /**
     * Standard jungle tree; 4 blocks wide and tall
     */
    JUNGLE,
    /**
     * Smaller jungle tree; 1 block wide
     */
    SMALL_JUNGLE,
    /**
     * Jungle tree with cocoa plants; 1 block wide
     */
    COCOA_TREE,
    /**
     * Small bush that grows in the jungle
     */
    JUNGLE_BUSH,
    /**
     * Big red mushroom; short and fat
     */
    RED_MUSHROOM,
    /**
     * Big brown mushroom; tall and umbrella-like
     */
    BROWN_MUSHROOM,
    /**
     * Swamp tree (regular with vines on the side)
     */
    SWAMP,
    /**
     * Acacia tree.
     */
    ACACIA,
    /**
     * Dark Oak tree.
     */
    DARK_OAK,
    /**
     * Mega redwood tree; 4 blocks wide and tall
     */
    MEGA_REDWOOD,
    /**
     * Tall birch tree
     */
    TALL_BIRCH,
    /**
     * Large plant native to The End
     */
    CHORUS_PLANT,
    /**
     * Large crimson fungus native to the nether
     */
    CRIMSON_FUNGUS,
    /**
     * Large warped fungus native to the nether
     */
    WARPED_FUNGUS(), // last 1.16
    /**
     * Tree with large roots which grows above lush caves
     */
    AZALEA(Version.V1_17),
    /**
     * Mangrove tree
     */
    MANGROVE(Version.V1_19),
    /**
     * Tall mangrove tree
     */
    TALL_MANGROVE(Version.V1_19),
    CHERRY(Version.V1_20);

    private final Version version;

    VoxelTreeType() {
        this(Version.V1_16);
    }

    VoxelTreeType(Version ver) {
        this.version = ver;
    }

    public boolean isSupported() {
        return VoxelSniper.voxelsniper.getVersion().isSupported(this.version);
    }
}
