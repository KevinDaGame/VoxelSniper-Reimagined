package com.github.kevindagame;

import com.github.kevindagame.brush.*;
import com.github.kevindagame.brush.multiBlock.*;
import com.github.kevindagame.brush.shell.ShellBallBrush;
import com.github.kevindagame.brush.shell.ShellSetBrush;
import com.github.kevindagame.brush.shell.ShellVoxelBrush;
import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Brush registration manager.
 */
public class VoxelBrushManager {

    private static VoxelBrushManager instance = null;

    private final Map<String, BrushData> brushes = new HashMap<>();

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

        brushManager.registerSniperBrush(ballBrush());
        brushManager.registerSniperBrush(biomeBrush());
        brushManager.registerSniperBrush(biomeBallBrush());
        brushManager.registerSniperBrush(blendBallBrush());
        brushManager.registerSniperBrush(blendDiscBrush());
        brushManager.registerSniperBrush(blendVoxelBrush());
        brushManager.registerSniperBrush(blendVoxelDiscBrush());
        brushManager.registerSniperBrush(blobBrush());
        brushManager.registerSniperBrush(blockResetBrush());
        brushManager.registerSniperBrush(blockResetSurfaceBrush());
        brushManager.registerSniperBrush(canyonBrush());
        brushManager.registerSniperBrush(canyonSelectionBrush());
        brushManager.registerSniperBrush(checkerVoxelDiscBrush());
        brushManager.registerSniperBrush(cleanSnowBrush());
        brushManager.registerSniperBrush(cloneStampBrush());
        brushManager.registerSniperBrush(cometBrush());
        brushManager.registerSniperBrush(copyPastaBrush());
        brushManager.registerSniperBrush(cylinderBrush());
        brushManager.registerSniperBrush(discBrush());
        brushManager.registerSniperBrush(discFaceBrush());
        brushManager.registerSniperBrush(domeBrush());
        brushManager.registerSniperBrush(drainBrush());
        brushManager.registerSniperBrush(ellipseBrush());
        brushManager.registerSniperBrush(ellipsoidBrush());
        brushManager.registerSniperBrush(entityBrush());
        brushManager.registerSniperBrush(entityRemovalBrush());
        brushManager.registerSniperBrush(eraserBrush());
        brushManager.registerSniperBrush(erodeBrush());
        brushManager.registerSniperBrush(extrudeBrush());
        brushManager.registerSniperBrush(fillDownBrush());
        brushManager.registerSniperBrush(flatOceanBrush());
        brushManager.registerSniperBrush(generateTreeBrush());
        brushManager.registerSniperBrush(jaggedLineBrush());
        brushManager.registerSniperBrush(jockeyBrush());
        brushManager.registerSniperBrush(lightningBrush());
        brushManager.registerSniperBrush(lineBrush());
        brushManager.registerSniperBrush(moveBrush());
        brushManager.registerSniperBrush(oceanBrush());
        brushManager.registerSniperBrush(overlayBrush());
        brushManager.registerSniperBrush(paintingBrush());
        brushManager.registerSniperBrush(pullBrush());
        brushManager.registerSniperBrush(regenerateChunkBrush());
        brushManager.registerSniperBrush(ringBrush());
        brushManager.registerSniperBrush(rulerBrush());
        brushManager.registerSniperBrush(scannerBrush());
        brushManager.registerSniperBrush(setBrush());
        brushManager.registerSniperBrush(shellBallBrush());
        brushManager.registerSniperBrush(shellSetBrush());
        brushManager.registerSniperBrush(shellVoxelBrush());
        brushManager.registerSniperBrush(signOverwriteBrush());
        brushManager.registerSniperBrush(snipeBrush());
        brushManager.registerSniperBrush(splatterBallBrush());
        brushManager.registerSniperBrush(splatterDiscBrush());
        brushManager.registerSniperBrush(splatterOverlayBrush());
        brushManager.registerSniperBrush(splineBrush());
        brushManager.registerSniperBrush(splatterVoxelBrush());
        brushManager.registerSniperBrush(splatterVoxelDiscBrush());
        brushManager.registerSniperBrush(threePointCircleBrush());
        brushManager.registerSniperBrush(treeSnipeBrush());
        brushManager.registerSniperBrush(triangleBrush());
        brushManager.registerSniperBrush(underlayBrush());
        brushManager.registerSniperBrush(voltMeterBrush());
        brushManager.registerSniperBrush(voxelBrush());
        brushManager.registerSniperBrush(voxelDiscBrush());
        brushManager.registerSniperBrush(voxelDiscFaceBrush());
        brushManager.registerSniperBrush(warpBrush());


        //these brushes are currently removed/broken
        //brushManager.registerSniperBrush(StencilBrush.class, "st", "stencil");
        //brushManager.registerSniperBrush(StencilListBrush.class, "sl", "stencillist");

        //brushManager.registerSniperBrush(Rot2DBrush.class, "rot2", "rotation2d");
        //brushManager.registerSniperBrush(Rot2DvertBrush.class, "rot2v", "rotation2dvertical");
        //brushManager.registerSniperBrush(Rot3DBrush.class, "rot3", "rotation3d");


        //these brushes have an unknown status
        //brushManager.registerSniperBrush(SnowConeBrush.class, "snow", "snowcone");
        //brushManager.registerSniperBrush(SpiralStaircaseBrush.class, "sstair", "spiralstaircase");

        return getInstance();
    }

    // region <Brush Registration methods>
    private static BrushData ballBrush() {
        return new BrushBuilder().name("Ball").alias("b").setSupplier(BallBrush::new).setPermission("voxelsniper.brush.ball").build();
    }

    private static BrushData biomeBrush() {
        return new BrushBuilder().name("Biome").alias("bio").setSupplier(BiomeBrush::new).setPermission("voxelsniper.brush.biome").build();
    }

    private static BrushData biomeBallBrush() {
        return new BrushBuilder().name("Biomeball").alias("bioball").setSupplier(BiomeBallBrush::new).setPermission("voxelsniper.brush.biomeball").build();
    }

    private static BrushData blendBallBrush() {
        return new BrushBuilder().name("Blendball").alias("bb").setSupplier(BlendBallBrush::new).setPermission("voxelsniper.brush.blendball").build();
    }

    private static BrushData blendDiscBrush() {
        return new BrushBuilder().name("Blenddisc").alias("bd").setSupplier(BlendDiscBrush::new).setPermission("voxelsniper.brush.blenddisc").build();
    }

    private static BrushData blendVoxelBrush() {
        return new BrushBuilder().name("Blendvoxel").alias("bv").setSupplier(BlendVoxelBrush::new).setPermission("voxelsniper.brush.blendvoxel").build();
    }

    private static BrushData blendVoxelDiscBrush() {
        return new BrushBuilder().name("Blendvoxeldisc").alias("bvd").setSupplier(BlendVoxelDiscBrush::new).setPermission("voxelsniper.brush.blendvoxeldisc").build();
    }

    private static BrushData blobBrush() {
        return new BrushBuilder().name("Splatblob").alias("blob").setSupplier(BlobBrush::new).setPermission("voxelsniper.brush.splatblob").build();
    }

    private static BrushData blockResetBrush() {
        return new BrushBuilder().name("Blockresetbrush").alias("brb").setSupplier(BlockResetBrush::new).setPermission("voxelsniper.brush.blockresetbrush").build();
    }

    private static BrushData blockResetSurfaceBrush() {
        return new BrushBuilder().name("Blockresetbrushsurface").alias("brbs").setSupplier(BlockResetSurfaceBrush::new).setPermission("voxelsniper.brush.blockresetbrushsurface").build();
    }

    private static BrushData canyonBrush() {
        return new BrushBuilder().name("Canyon").alias("ca").setSupplier(CanyonBrush::new).setPermission("voxelsniper.brush.canyon").build();
    }

    private static BrushData canyonSelectionBrush() {
        return new BrushBuilder().name("Canyonselection").alias("cas").setSupplier(CanyonSelectionBrush::new).setPermission("voxelsniper.brush.canyonselection").build();
    }

    private static BrushData checkerVoxelDiscBrush() {
        return new BrushBuilder().name("Checkervoxeldisc").alias("cvd").setSupplier(CheckerVoxelDiscBrush::new).setPermission("voxelsniper.brush.checkervoxeldisc").build();
    }

    private static BrushData cleanSnowBrush() {
        return new BrushBuilder().name("Cleansnow").alias("cls").setSupplier(CleanSnowBrush::new).setPermission("voxelsniper.brush.cleansnow").build();
    }

    private static BrushData cloneStampBrush() {
        return new BrushBuilder().name("Clonestamp").alias("cs").setSupplier(CloneStampBrush::new).setPermission("voxelsniper.brush.clonestamp").build();
    }

    private static BrushData cometBrush() {
        return new BrushBuilder().name("Comet").alias("com").setSupplier(CometBrush::new).setPermission("voxelsniper.brush.comet").build();
    }

    private static BrushData copyPastaBrush() {
        return new BrushBuilder().name("Copypasta").alias("cp").setSupplier(CopyPastaBrush::new).setPermission("voxelsniper.brush.copypasta").build();
    }

    private static BrushData cylinderBrush() {
        return new BrushBuilder().name("Cylinder").alias("c").setSupplier(CylinderBrush::new).setPermission("voxelsniper.brush.cylinder").build();
    }

    private static BrushData discBrush() {
        return new BrushBuilder().name("Disc").alias("d").setSupplier(DiscBrush::new).setPermission("voxelsniper.brush.disc").build();
    }

    private static BrushData discFaceBrush() {
        return new BrushBuilder().name("Discface").alias("df").setSupplier(DiscFaceBrush::new).setPermission("voxelsniper.brush.discface").build();
    }

    private static BrushData domeBrush() {
        return new BrushBuilder().name("Domebrush").alias("dome").setSupplier(DomeBrush::new).setPermission("voxelsniper.brush.domebrush").build();
    }

    private static BrushData drainBrush() {
        return new BrushBuilder().name("drain").setSupplier(DrainBrush::new).setPermission("voxelsniper.brush.drain").build();
    }

    private static BrushData ellipseBrush() {
        return new BrushBuilder().name("Ellipse").alias("el").setSupplier(EllipseBrush::new).setPermission("voxelsniper.brush.ellipse").build();
    }

    private static BrushData ellipsoidBrush() {
        return new BrushBuilder().name("Ellipsoid").alias("elo").setSupplier(EllipsoidBrush::new).setPermission("voxelsniper.brush.ellipsoid").build();
    }

    private static BrushData entityBrush() {
        return new BrushBuilder().name("Entity").alias("en").setSupplier(EntityBrush::new).setPermission("voxelsniper.brush.entity").build();
    }

    private static BrushData entityRemovalBrush() {
        return new BrushBuilder().name("Entityremoval").alias("er").setSupplier(EntityRemovalBrush::new).setPermission("voxelsniper.brush.entityremoval").build();
    }

    private static BrushData eraserBrush() {
        return new BrushBuilder().name("Eraser").alias("erase").setSupplier(EraserBrush::new).setPermission("voxelsniper.brush.eraser").build();
    }

    private static BrushData erodeBrush() {
        return new BrushBuilder().name("Erode").alias("e").setSupplier(ErodeBrush::new).setPermission("voxelsniper.brush.erode").build();
    }

    private static BrushData extrudeBrush() {
        return new BrushBuilder().name("Extrude").alias("ex").setSupplier(ExtrudeBrush::new).setPermission("voxelsniper.brush.extrude").build();
    }

    private static BrushData fillDownBrush() {
        return new BrushBuilder().name("Filldown").alias("fd").setSupplier(FillDownBrush::new).setPermission("voxelsniper.brush.filldown").build();
    }

    private static BrushData flatOceanBrush() {
        return new BrushBuilder().name("Flatocean").alias("fo").setSupplier(FlatOceanBrush::new).setPermission("voxelsniper.brush.flatocean").build();
    }

    private static BrushData generateTreeBrush() {
        return new BrushBuilder().name("Generatetree").alias("gt").setSupplier(GenerateTreeBrush::new).setPermission("voxelsniper.brush.generatetree").build();
    }

    private static BrushData jaggedLineBrush() {
        return new BrushBuilder().name("Jagged").alias("j").setSupplier(JaggedLineBrush::new).setPermission("voxelsniper.brush.jagged").build();
    }

    private static BrushData jockeyBrush() {
        return new BrushBuilder().name("jockey").setSupplier(JockeyBrush::new).setPermission("voxelsniper.brush.jockey").build();
    }

    private static BrushData lightningBrush() {
        return new BrushBuilder().name("Lightning").alias("light").setSupplier(LightningBrush::new).setPermission("voxelsniper.brush.lightning").build();
    }

    private static BrushData lineBrush() {
        return new BrushBuilder().name("Line").alias("l").setSupplier(LineBrush::new).setPermission("voxelsniper.brush.line").build();
    }

    private static BrushData moveBrush() {
        return new BrushBuilder().name("Move").alias("mv").setSupplier(MoveBrush::new).setPermission("voxelsniper.brush.move").build();
    }

    private static BrushData oceanBrush() {
        return new BrushBuilder().name("Ocean").alias("o").setSupplier(OceanBrush::new).setPermission("voxelsniper.brush.ocean").build();
    }

    private static BrushData overlayBrush() {
        return new BrushBuilder().name("Overlay").alias("over").setSupplier(OverlayBrush::new).setPermission("voxelsniper.brush.overlay").build();
    }

    private static BrushData paintingBrush() {
        return new BrushBuilder().name("painting").setSupplier(PaintingBrush::new).setPermission("voxelsniper.brush.painting").build();
    }

    private static BrushData pullBrush() {
        return new BrushBuilder().name("pull").setSupplier(PullBrush::new).setPermission("voxelsniper.brush.pull").build();
    }

    private static BrushData regenerateChunkBrush() {
        return new BrushBuilder().name("Regeneratechunk").alias("rc").setSupplier(RegenerateChunkBrush::new).setPermission("voxelsniper.brush.regeneratechunk").build();
    }

    private static BrushData ringBrush() {
        return new BrushBuilder().name("Ring").alias("ri").setSupplier(RingBrush::new).setPermission("voxelsniper.brush.ring").build();
    }

    private static BrushData rulerBrush() {
        return new BrushBuilder().name("Ruler").alias("r").setSupplier(RulerBrush::new).setPermission("voxelsniper.brush.ruler").build();
    }

    private static BrushData scannerBrush() {
        return new BrushBuilder().name("Scanner").alias("sc").setSupplier(ScannerBrush::new).setPermission("voxelsniper.brush.scanner").build();
    }

    private static BrushData setBrush() {
        return new BrushBuilder().name("set").setSupplier(SetBrush::new).setPermission("voxelsniper.brush.set").build();
    }

    private static BrushData shellBallBrush() {
        return new BrushBuilder().name("Shellball").alias("shb").setSupplier(ShellBallBrush::new).setPermission("voxelsniper.brush.shellball").build();
    }

    private static BrushData shellSetBrush() {
        return new BrushBuilder().name("Shellset").alias("shs").setSupplier(ShellSetBrush::new).setPermission("voxelsniper.brush.shellset").build();
    }

    private static BrushData shellVoxelBrush() {
        return new BrushBuilder().name("Shellvoxel").alias("shv").setSupplier(ShellVoxelBrush::new).setPermission("voxelsniper.brush.shellvoxel").build();
    }

    private static BrushData signOverwriteBrush() {
        return new BrushBuilder().name("Signoverwriter").alias("sio").setSupplier(SignOverwriteBrush::new).setPermission("voxelsniper.brush.signoverwriter").build();
    }

    private static BrushData snipeBrush() {
        return new BrushBuilder().name("Snipe").alias("s").setSupplier(SnipeBrush::new).setPermission("voxelsniper.brush.snipe").build();
    }

    private static BrushData splatterBallBrush() {
        return new BrushBuilder().name("Splatball").alias("sb").setSupplier(SplatterBallBrush::new).setPermission("voxelsniper.brush.splatball").build();
    }

    private static BrushData splatterDiscBrush() {
        return new BrushBuilder().name("Splatdisc").alias("sd").setSupplier(SplatterDiscBrush::new).setPermission("voxelsniper.brush.splatdisc").build();
    }

    private static BrushData splatterOverlayBrush() {
        return new BrushBuilder().name("Splatteroverlay").alias("sover").setSupplier(SplatterOverlayBrush::new).setPermission("voxelsniper.brush.splatteroverlay").build();
    }

    private static BrushData splineBrush() {
        return new BrushBuilder().name("Spline").alias("sp").setSupplier(SplineBrush::new).setPermission("voxelsniper.brush.spline").build();
    }

    private static BrushData splatterVoxelBrush() {
        return new BrushBuilder().name("Splattervoxel").alias("sv").setSupplier(SplatterVoxelBrush::new).setPermission("voxelsniper.brush.splattervoxel").build();
    }

    private static BrushData splatterVoxelDiscBrush() {
        return new BrushBuilder().name("Splatvoxeldisc").alias("svd").setSupplier(SplatterVoxelDiscBrush::new).setPermission("voxelsniper.brush.splatvoxeldisc").build();
    }

    private static BrushData threePointCircleBrush() {
        return new BrushBuilder().name("Threepointcircle").alias("tpc").setSupplier(ThreePointCircleBrush::new).setPermission("voxelsniper.brush.threepointcircle").build();
    }

    private static BrushData treeSnipeBrush() {
        return new BrushBuilder().name("t").setSupplier(TreeSnipeBrush::new).setPermission("voxelsniper.brush.t").build();
    }

    private static BrushData triangleBrush() {
        return new BrushBuilder().name("Triangle").alias("tri").setSupplier(TriangleBrush::new).setPermission("voxelsniper.brush.triangle").build();
    }

    private static BrushData underlayBrush() {
        return new BrushBuilder().name("Underlay").alias("under").setSupplier(UnderlayBrush::new).setPermission("voxelsniper.brush.underlay").build();
    }

    private static BrushData voltMeterBrush() {
        return new BrushBuilder().name("Voltmeter").alias("volt").setSupplier(VoltMeterBrush::new).setPermission("voxelsniper.brush.voltmeter").build();
    }

    private static BrushData voxelBrush() {
        return new BrushBuilder().name("Voxel").alias("v").setSupplier(VoxelBrush::new).setPermission("voxelsniper.brush.voxel").build();
    }

    private static BrushData voxelDiscBrush() {
        return new BrushBuilder().name("Voxeldisc").alias("vd").setSupplier(VoxelDiscBrush::new).setPermission("voxelsniper.brush.voxeldisc").build();
    }

    private static BrushData voxelDiscFaceBrush() {
        return new BrushBuilder().name("Voxeldiscface").alias("vdf").setSupplier(VoxelDiscFaceBrush::new).setPermission("voxelsniper.brush.voxeldiscface").build();
    }

    private static BrushData warpBrush() {
        return new BrushBuilder().name("Warp").alias("w").setSupplier(WarpBrush::new).setPermission("voxelsniper.brush.warp").build();
    }
    //endregion

    /**
     * Register a brush for VoxelSniper to be able to use.
     *
     * @param brushData The brush data to register.
     */
    public void registerSniperBrush(@NotNull BrushData brushData) {
        for (String handle : brushData.getAliases()) {
            brushes.put(handle.toLowerCase(), brushData);
        }
    }

    /**
     * Retrieve Brush class via handle Lookup.
     *
     * @param handle Case-insensitive brush handle
     * @return Brush class
     */
    public BrushData getBrushForHandle(String handle) {
        Preconditions.checkNotNull(handle, "Brushhandle can not be null.");

        return brushes.get(handle.toLowerCase());
    }

    /**
     * @return Amount of IBrush classes registered with the system under Sniper visibility.
     */
    public int registeredSniperBrushes() {
        return (int) brushes.values().stream().distinct().count();
    }

    /**
     * @return Amount of handles registered with the system under Sniper visibility.
     */
    public int registeredSniperBrushHandles() {
        return brushes.values().size();
    }

    /**
     * @param clazz Brush class
     * @return All Sniper registered handles for the brush.
     */
    public Set<String> getSniperBrushHandles(Class<? extends IBrush> clazz) {
        Set<String> handles = new HashSet<>();
        for (String key : brushes.keySet()) {
            if (brushes.get(key).equals(clazz)) {
                handles.add(key);
            }
        }
        return handles;
    }

    /**
     * @return Immutable Map copy of all the registered brushes
     */
    public Map<String, BrushData> getRegisteredBrushesMap() {
        return Map.copyOf(brushes);
    }

    /**
     * @return The brush data for the default brush.
     */
    public BrushData getDefaultBrush() {
        return brushes.get("snipe");
    }

    public List<String> getBrushHandles() {
        return new ArrayList<>(brushes.keySet());
    }
}
