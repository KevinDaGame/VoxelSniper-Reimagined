package com.thevoxelbox.voxelsniper.bukkit;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.thevoxelbox.voxelsniper.brush.*;

import java.util.*;

/**
 * Brush registration manager.
 */
public class VoxelBrushManager {

    private static VoxelBrushManager instance = null;

    private final Multimap<Class<? extends IBrush>, String> brushes = HashMultimap.create();
    private final List<String> brushHandles = new ArrayList<>();

    public static VoxelBrushManager getInstance() {
        return instance;
    }

    public static VoxelBrushManager initialize() {
        VoxelBrushManager brushManager = getInstance();

        // Instantiate Brush Manager if it's not yet instantiated.
        if (brushManager == null) {
            instance = new VoxelBrushManager();
            brushManager = getInstance();
        }

        brushManager.registerSniperBrush(BallBrush.class, "b", "ball");
        brushManager.registerSniperBrush(BiomeBrush.class, "bio", "biome");
        brushManager.registerSniperBrush(BlendBallBrush.class, "bb", "blendball");
        brushManager.registerSniperBrush(BlendDiscBrush.class, "bd", "blenddisc");
        brushManager.registerSniperBrush(BlendVoxelBrush.class, "bv", "blendvoxel");
        brushManager.registerSniperBrush(BlendVoxelDiscBrush.class, "bvd", "blendvoxeldisc");
        brushManager.registerSniperBrush(BlobBrush.class, "blob", "splatblob");
        brushManager.registerSniperBrush(BlockResetBrush.class, "brb", "blockresetbrush");
        brushManager.registerSniperBrush(BlockResetSurfaceBrush.class, "brbs", "blockresetbrushsurface");
        brushManager.registerSniperBrush(CanyonBrush.class, "ca", "canyon");
        brushManager.registerSniperBrush(CanyonSelectionBrush.class, "cas", "canyonselection");
        brushManager.registerSniperBrush(CheckerVoxelDiscBrush.class, "cvd", "checkervoxeldisc");
        brushManager.registerSniperBrush(CleanSnowBrush.class, "cls", "cleansnow");
        brushManager.registerSniperBrush(CloneStampBrush.class, "cs", "clonestamp");
        brushManager.registerSniperBrush(CometBrush.class, "com", "comet");
        brushManager.registerSniperBrush(CopyPastaBrush.class, "cp", "copypasta");
        brushManager.registerSniperBrush(CylinderBrush.class, "c", "cylinder");
        brushManager.registerSniperBrush(DiscBrush.class, "d", "disc");
        brushManager.registerSniperBrush(DiscFaceBrush.class, "df", "discface");
        brushManager.registerSniperBrush(DomeBrush.class, "dome", "domebrush");
        brushManager.registerSniperBrush(DrainBrush.class, "drain");
        brushManager.registerSniperBrush(EllipseBrush.class, "el", "ellipse");
        brushManager.registerSniperBrush(EllipsoidBrush.class, "elo", "ellipsoid");
        brushManager.registerSniperBrush(EntityBrush.class, "en", "entity");
        brushManager.registerSniperBrush(EntityRemovalBrush.class, "er", "entityremoval");
        brushManager.registerSniperBrush(EraserBrush.class, "erase", "eraser");
        brushManager.registerSniperBrush(ErodeBrush.class, "e", "erode");
        brushManager.registerSniperBrush(ExtrudeBrush.class, "ex", "extrude");
        brushManager.registerSniperBrush(FillDownBrush.class, "fd", "filldown");
        brushManager.registerSniperBrush(FlatOceanBrush.class, "fo", "flatocean");
        brushManager.registerSniperBrush(GenerateTreeBrush.class, "gt", "generatetree");
        brushManager.registerSniperBrush(HeatRayBrush.class, "hr", "heatray");
        brushManager.registerSniperBrush(JaggedLineBrush.class, "j", "jagged");
        brushManager.registerSniperBrush(JockeyBrush.class, "jockey");
        brushManager.registerSniperBrush(LightningBrush.class, "light", "lightning");
        brushManager.registerSniperBrush(LineBrush.class, "l", "line");
        brushManager.registerSniperBrush(MoveBrush.class, "mv", "move");
        brushManager.registerSniperBrush(OceanBrush.class, "o", "ocean");
        brushManager.registerSniperBrush(OverlayBrush.class, "over", "overlay");
        brushManager.registerSniperBrush(PaintingBrush.class, "painting");
        brushManager.registerSniperBrush(PullBrush.class, "pull");
        brushManager.registerSniperBrush(PunishBrush.class, "p", "punish");
        //brushManager.registerSniperBrush(RandomErodeBrush.class, "re", "randomerode");
        brushManager.registerSniperBrush(RegenerateChunkBrush.class, "gc", "generatechunk");
        brushManager.registerSniperBrush(RingBrush.class, "ri", "ring");
        brushManager.registerSniperBrush(Rot2DBrush.class, "rot2", "rotation2d");
        brushManager.registerSniperBrush(Rot2DvertBrush.class, "rot2v", "rotation2dvertical");
        brushManager.registerSniperBrush(Rot3DBrush.class, "rot3", "rotation3d");
        brushManager.registerSniperBrush(RulerBrush.class, "r", "ruler");
        brushManager.registerSniperBrush(ScannerBrush.class, "sc", "scanner");
        brushManager.registerSniperBrush(SetBrush.class, "set");
        //brushManager.registerSniperBrush(SetRedstoneFlipBrush.class, "setrf", "setredstoneflip");
        brushManager.registerSniperBrush(ShellBallBrush.class, "shb", "shellball");
        brushManager.registerSniperBrush(ShellSetBrush.class, "shs", "shellset");
        brushManager.registerSniperBrush(ShellVoxelBrush.class, "shv", "shellvoxel");
        brushManager.registerSniperBrush(SignOverwriteBrush.class, "sio", "signoverwriter");
        brushManager.registerSniperBrush(SnipeBrush.class, "s", "snipe");
        //brushManager.registerSniperBrush(SnowConeBrush.class, "snow", "snowcone");
        //brushManager.registerSniperBrush(SpiralStaircaseBrush.class, "sstair", "spiralstaircase");
        brushManager.registerSniperBrush(SplatterBallBrush.class, "sb", "splatball");
        brushManager.registerSniperBrush(SplatterDiscBrush.class, "sd", "splatdisc");
        brushManager.registerSniperBrush(SplatterOverlayBrush.class, "sover", "splatteroverlay");
        brushManager.registerSniperBrush(SplatterVoxelBrush.class, "sv", "splattervoxel");
        brushManager.registerSniperBrush(SplatterDiscBrush.class, "svd", "splatvoxeldisc");
        brushManager.registerSniperBrush(SplineBrush.class, "sp", "spline");
        //brushManager.registerSniperBrush(StencilBrush.class, "st", "stencil");
        //brushManager.registerSniperBrush(StencilListBrush.class, "sl", "stencillist");
        brushManager.registerSniperBrush(ThreePointCircleBrush.class, "tpc", "threepointcircle");
        brushManager.registerSniperBrush(TreeSnipeBrush.class, "t", "tree", "treesnipe");
        brushManager.registerSniperBrush(TriangleBrush.class, "tri", "triangle");
        brushManager.registerSniperBrush(UnderlayBrush.class, "under", "underlay");
        brushManager.registerSniperBrush(VoltMeterBrush.class, "volt", "voltmeter");
        brushManager.registerSniperBrush(VoxelBrush.class, "v", "voxel");
        brushManager.registerSniperBrush(VoxelDiscBrush.class, "vd", "voxeldisc");
        brushManager.registerSniperBrush(VoxelDiscFaceBrush.class, "vdf", "voxeldiscface");
        brushManager.registerSniperBrush(WarpBrush.class, "w", "warp");
        
        return getInstance();
    }

    /**
     * Register a brush for VoxelSniper to be able to use.
     *
     * @param clazz Brush implementing IBrush interface.
     * @param handles Handles under which the brush can be accessed ingame.
     */
    public void registerSniperBrush(Class<? extends IBrush> clazz, String... handles) {
        Preconditions.checkNotNull(clazz, "Cannot register null as a class.");
        for (String handle : handles) {
            brushes.put(clazz, handle.toLowerCase());
            brushHandles.add(handle.toLowerCase());
        }
    }

    /**
     * Retrieve Brush class via handle Lookup.
     *
     * @param handle Case insensitive brush handle
     * @return Brush class
     */
    public Class<? extends IBrush> getBrushForHandle(String handle) {
        Preconditions.checkNotNull(handle, "Brushhandle can not be null.");
        if (!brushes.containsValue(handle.toLowerCase())) {
            return null;
        }

        for (Map.Entry<Class<? extends IBrush>, String> entry : brushes.entries()) {
            if (entry.getValue().equalsIgnoreCase(handle)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * @return Amount of IBrush classes registered with the system under Sniper visibility.
     */
    public int registeredSniperBrushes() {
        return brushes.keySet().size();
    }

    /**
     * @return Amount of handles registered with the system under Sniper visibility.
     */
    public int registeredSniperBrushHandles() {
        return brushes.size();
    }

    /**
     *
     * @param clazz Brush class
     * @return All Sniper registered handles for the brush.
     */
    public Set<String> getSniperBrushHandles(Class<? extends IBrush> clazz) {
        return new HashSet<>(brushes.get(clazz));
    }

    /**
     * @return Immutable Multimap copy of all the registered brushes
     */
    public Multimap<Class<? extends IBrush>, String> getRegisteredBrushesMultimap() {
        return ImmutableMultimap.copyOf(brushes);
    }

    public List<String> getBrushHandles() {
        return brushHandles;
    }
}
