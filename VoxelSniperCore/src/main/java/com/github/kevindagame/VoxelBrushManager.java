package com.github.kevindagame;

import com.github.kevindagame.brush.*;
import com.github.kevindagame.brush.multiBlock.*;
import com.github.kevindagame.brush.polymorphic.PolyBrushBuilder;
import com.github.kevindagame.brush.polymorphic.PolyBrushShape;
import com.github.kevindagame.brush.polymorphic.PolyOperation;
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
        return new PolyBrushBuilder().name("Ball").alias("b").alias("ball").permission("voxelsniper.brush.ball").shape(PolyBrushShape.BALL).build();
    }

    private static BrushData biomeBrush() {
        return new BrushBuilder().name("Biome").alias("bio").alias("biome").setSupplier(BiomeBrush::new).setPermission("voxelsniper.brush.biome").build();
    }

    private static BrushData biomeBallBrush() {
        return new PolyBrushBuilder().name("Biomeball").alias("bioball").alias("biomeball").permission("voxelsniper.brush.biomeball").operation(PolyOperation.BIOME).shape(PolyBrushShape.BALL).build();
    }

    private static BrushData blendBallBrush() {
        return new BrushBuilder().name("Blendball").alias("bb").alias("blendball").setSupplier(BlendBallBrush::new).setPermission("voxelsniper.brush.blendball").build();
    }

    private static BrushData blendDiscBrush() {
        return new BrushBuilder().name("Blenddisc").alias("bd").alias("blenddisc").setSupplier(BlendDiscBrush::new).setPermission("voxelsniper.brush.blenddisc").build();
    }

    private static BrushData blendVoxelBrush() {
        return new BrushBuilder().name("Blendvoxel").alias("bv").alias("blendvoxel").setSupplier(BlendVoxelBrush::new).setPermission("voxelsniper.brush.blendvoxel").build();
    }

    private static BrushData blendVoxelDiscBrush() {
        return new BrushBuilder().name("Blendvoxeldisc").alias("bvd").alias("blendvoxeldisc").setSupplier(BlendVoxelDiscBrush::new).setPermission("voxelsniper.brush.blendvoxeldisc").build();
    }

    private static BrushData blobBrush() {
        return new BrushBuilder().name("Splatblob").alias("blob").alias("splatblob").setSupplier(BlobBrush::new).setPermission("voxelsniper.brush.splatblob").build();
    }

    private static BrushData blockResetBrush() {
        return new BrushBuilder().name("Blockresetbrush").alias("brb").alias("blockresetbrush").setSupplier(BlockResetBrush::new).setPermission("voxelsniper.brush.blockresetbrush").build();
    }

    private static BrushData blockResetSurfaceBrush() {
        return new BrushBuilder().name("Blockresetbrushsurface").alias("brbs").alias("blockresetbrushsurface").setSupplier(BlockResetSurfaceBrush::new).setPermission("voxelsniper.brush.blockresetbrushsurface").build();
    }

    private static BrushData canyonBrush() {
        return new BrushBuilder().name("Canyon").alias("ca").alias("canyon").setSupplier(CanyonBrush::new).setPermission("voxelsniper.brush.canyon").build();
    }

    private static BrushData canyonSelectionBrush() {
        return new BrushBuilder().name("Canyonselection").alias("cas").alias("canyonselection").setSupplier(CanyonSelectionBrush::new).setPermission("voxelsniper.brush.canyonselection").build();
    }

    private static BrushData checkerVoxelDiscBrush() {
        return new BrushBuilder().name("Checkervoxeldisc").alias("cvd").alias("checkervoxeldisc").setSupplier(CheckerVoxelDiscBrush::new).setPermission("voxelsniper.brush.checkervoxeldisc").build();
    }

    private static BrushData cleanSnowBrush() {
        return new BrushBuilder().name("Cleansnow").alias("cls").alias("cleansnow").setSupplier(CleanSnowBrush::new).setPermission("voxelsniper.brush.cleansnow").build();
    }

    private static BrushData cloneStampBrush() {
        return new BrushBuilder().name("Clonestamp").alias("cs").alias("clonestamp").setSupplier(CloneStampBrush::new).setPermission("voxelsniper.brush.clonestamp").build();
    }

    private static BrushData cometBrush() {
        return new BrushBuilder().name("Comet").alias("com").alias("comet").setSupplier(CometBrush::new).setPermission("voxelsniper.brush.comet").build();
    }

    private static BrushData copyPastaBrush() {
        return new BrushBuilder().name("Copypasta").alias("cp").alias("copypasta").setSupplier(CopyPastaBrush::new).setPermission("voxelsniper.brush.copypasta").build();
    }

    private static BrushData cylinderBrush() {
        return new PolyBrushBuilder().name("Cylinder").alias("c").alias("cylinder").permission("voxelsniper.brush.cylinder").shape(PolyBrushShape.CYLINDER).build();
    }

    private static BrushData discBrush() {
        return new PolyBrushBuilder().name("Disc").alias("d").alias("disc").shape(PolyBrushShape.DISC).permission("voxelsniper.brush.disc").build();
    }

    private static BrushData discFaceBrush() {
        return new BrushBuilder().name("Discface").alias("df").alias("discface").setSupplier(DiscFaceBrush::new).setPermission("voxelsniper.brush.discface").build();
    }

    private static BrushData domeBrush() {
        return new BrushBuilder().name("Domebrush").alias("dome").alias("domebrush").setSupplier(DomeBrush::new).setPermission("voxelsniper.brush.domebrush").build();
    }

    private static BrushData drainBrush() {
        return new BrushBuilder().name("drain").setSupplier(DrainBrush::new).setPermission("voxelsniper.brush.drain").build();
    }

    private static BrushData ellipseBrush() {
        return new BrushBuilder().name("Ellipse").alias("el").alias("ellipse").setSupplier(EllipseBrush::new).setPermission("voxelsniper.brush.ellipse").build();
    }

    private static BrushData ellipsoidBrush() {
        return new BrushBuilder().name("Ellipsoid").alias("elo").alias("ellipsoid").setSupplier(EllipsoidBrush::new).setPermission("voxelsniper.brush.ellipsoid").build();
    }

    private static BrushData entityBrush() {
        return new BrushBuilder().name("Entity").alias("en").alias("entity").setSupplier(EntityBrush::new).setPermission("voxelsniper.brush.entity").build();
    }

    private static BrushData entityRemovalBrush() {
        return new BrushBuilder().name("Entityremoval").alias("er").alias("entityremoval").setSupplier(EntityRemovalBrush::new).setPermission("voxelsniper.brush.entityremoval").build();
    }

    private static BrushData eraserBrush() {
        return new BrushBuilder().name("Eraser").alias("erase").alias("eraser").setSupplier(EraserBrush::new).setPermission("voxelsniper.brush.eraser").build();
    }

    private static BrushData erodeBrush() {
        return new BrushBuilder().name("Erode").alias("e").alias("erode").setSupplier(ErodeBrush::new).setPermission("voxelsniper.brush.erode").build();
    }

    private static BrushData extrudeBrush() {
        return new BrushBuilder().name("Extrude").alias("ex").alias("extrude").setSupplier(ExtrudeBrush::new).setPermission("voxelsniper.brush.extrude").build();
    }

    private static BrushData fillDownBrush() {
        return new BrushBuilder().name("Filldown").alias("fd").alias("filldown").setSupplier(FillDownBrush::new).setPermission("voxelsniper.brush.filldown").build();
    }

    private static BrushData flatOceanBrush() {
        return new BrushBuilder().name("Flatocean").alias("fo").alias("flatocean").setSupplier(FlatOceanBrush::new).setPermission("voxelsniper.brush.flatocean").build();
    }

    private static BrushData generateTreeBrush() {
        return new BrushBuilder().name("Generatetree").alias("gt").alias("generatetree").setSupplier(GenerateTreeBrush::new).setPermission("voxelsniper.brush.generatetree").build();
    }

    private static BrushData jaggedLineBrush() {
        return new BrushBuilder().name("Jagged").alias("j").alias("jagged").setSupplier(JaggedLineBrush::new).setPermission("voxelsniper.brush.jagged").build();
    }

    private static BrushData jockeyBrush() {
        return new BrushBuilder().name("jockey").setSupplier(JockeyBrush::new).setPermission("voxelsniper.brush.jockey").build();
    }

    private static BrushData lightningBrush() {
        return new BrushBuilder().name("Lightning").alias("light").alias("lightning").setSupplier(LightningBrush::new).setPermission("voxelsniper.brush.lightning").build();
    }

    private static BrushData lineBrush() {
        return new BrushBuilder().name("Line").alias("l").alias("line").setSupplier(LineBrush::new).setPermission("voxelsniper.brush.line").build();
    }

    private static BrushData moveBrush() {
        return new BrushBuilder().name("Move").alias("mv").alias("move").setSupplier(MoveBrush::new).setPermission("voxelsniper.brush.move").build();
    }

    private static BrushData oceanBrush() {
        return new BrushBuilder().name("Ocean").alias("o").alias("ocean").setSupplier(OceanBrush::new).setPermission("voxelsniper.brush.ocean").build();
    }

    private static BrushData overlayBrush() {
        return new BrushBuilder().name("Overlay").alias("over").alias("overlay").setSupplier(OverlayBrush::new).setPermission("voxelsniper.brush.overlay").build();
    }

    private static BrushData paintingBrush() {
        return new BrushBuilder().name("painting").setSupplier(PaintingBrush::new).setPermission("voxelsniper.brush.painting").build();
    }

    private static BrushData pullBrush() {
        return new BrushBuilder().name("pull").setSupplier(PullBrush::new).setPermission("voxelsniper.brush.pull").build();
    }

    private static BrushData regenerateChunkBrush() {
        return new BrushBuilder().name("Regeneratechunk").alias("rc").alias("regeneratechunk").setSupplier(RegenerateChunkBrush::new).setPermission("voxelsniper.brush.regeneratechunk").build();
    }

    private static BrushData ringBrush() {
        return new BrushBuilder().name("Ring").alias("ri").alias("ring").setSupplier(RingBrush::new).setPermission("voxelsniper.brush.ring").build();
    }

    private static BrushData rulerBrush() {
        return new BrushBuilder().name("Ruler").alias("r").alias("ruler").setSupplier(RulerBrush::new).setPermission("voxelsniper.brush.ruler").build();
    }

    private static BrushData scannerBrush() {
        return new BrushBuilder().name("Scanner").alias("sc").alias("scanner").setSupplier(ScannerBrush::new).setPermission("voxelsniper.brush.scanner").build();
    }

    private static BrushData setBrush() {
        return new BrushBuilder().name("set").setSupplier(SetBrush::new).setPermission("voxelsniper.brush.set").build();
    }

    private static BrushData shellBallBrush() {
        return new BrushBuilder().name("Shellball").alias("shb").alias("shellball").setSupplier(ShellBallBrush::new).setPermission("voxelsniper.brush.shellball").build();
    }

    private static BrushData shellSetBrush() {
        return new BrushBuilder().name("Shellset").alias("shs").alias("shellset").setSupplier(ShellSetBrush::new).setPermission("voxelsniper.brush.shellset").build();
    }

    private static BrushData shellVoxelBrush() {
        return new BrushBuilder().name("Shellvoxel").alias("shv").alias("shellvoxel").setSupplier(ShellVoxelBrush::new).setPermission("voxelsniper.brush.shellvoxel").build();
    }

    private static BrushData signOverwriteBrush() {
        return new BrushBuilder().name("Signoverwriter").alias("sio").alias("signoverwriter").setSupplier(SignOverwriteBrush::new).setPermission("voxelsniper.brush.signoverwriter").build();
    }

    private static BrushData snipeBrush() {
        return new BrushBuilder().name("Snipe").alias("s").alias("snipe").setSupplier(SnipeBrush::new).setPermission("voxelsniper.brush.snipe").build();
    }

    private static BrushData splatterBallBrush() {
        return new BrushBuilder().name("Splatball").alias("sb").alias("splatball").setSupplier(SplatterBallBrush::new).setPermission("voxelsniper.brush.splatball").build();
    }

    private static BrushData splatterDiscBrush() {
        return new BrushBuilder().name("Splatdisc").alias("sd").alias("splatdisc").setSupplier(SplatterDiscBrush::new).setPermission("voxelsniper.brush.splatdisc").build();
    }

    private static BrushData splatterOverlayBrush() {
        return new BrushBuilder().name("Splatteroverlay").alias("sover").alias("splatteroverlay").setSupplier(SplatterOverlayBrush::new).setPermission("voxelsniper.brush.splatteroverlay").build();
    }

    private static BrushData splineBrush() {
        return new BrushBuilder().name("Spline").alias("sp").alias("spline").setSupplier(SplineBrush::new).setPermission("voxelsniper.brush.spline").build();
    }

    private static BrushData splatterVoxelBrush() {
        return new BrushBuilder().name("Splattervoxel").alias("sv").alias("splattervoxel").setSupplier(SplatterVoxelBrush::new).setPermission("voxelsniper.brush.splattervoxel").build();
    }

    private static BrushData splatterVoxelDiscBrush() {
        return new BrushBuilder().name("Splatvoxeldisc").alias("svd").alias("splatvoxeldisc").setSupplier(SplatterVoxelDiscBrush::new).setPermission("voxelsniper.brush.splatvoxeldisc").build();
    }

    private static BrushData threePointCircleBrush() {
        return new BrushBuilder().name("Threepointcircle").alias("tpc").alias("threepointcircle").setSupplier(ThreePointCircleBrush::new).setPermission("voxelsniper.brush.threepointcircle").build();
    }

    private static BrushData treeSnipeBrush() {
        return new BrushBuilder().name("t").setSupplier(TreeSnipeBrush::new).setPermission("voxelsniper.brush.t").build();
    }

    private static BrushData triangleBrush() {
        return new BrushBuilder().name("Triangle").alias("tri").alias("triangle").setSupplier(TriangleBrush::new).setPermission("voxelsniper.brush.triangle").build();
    }

    private static BrushData underlayBrush() {
        return new BrushBuilder().name("Underlay").alias("under").alias("underlay").setSupplier(UnderlayBrush::new).setPermission("voxelsniper.brush.underlay").build();
    }

    private static BrushData voltMeterBrush() {
        return new BrushBuilder().name("Voltmeter").alias("volt").alias("voltmeter").setSupplier(VoltMeterBrush::new).setPermission("voxelsniper.brush.voltmeter").build();
    }

    private static BrushData voxelBrush() {
        return new PolyBrushBuilder().name("Voxel").alias("v").alias("voxel").shape(PolyBrushShape.VOXEL).permission("voxelsniper.brush.voxel").build();
    }

    private static BrushData voxelDiscBrush() {
        return new BrushBuilder().name("Voxeldisc").alias("vd").alias("voxeldisc").setSupplier(VoxelDiscBrush::new).setPermission("voxelsniper.brush.voxeldisc").build();
    }

    private static BrushData voxelDiscFaceBrush() {
        return new BrushBuilder().name("Voxeldiscface").alias("vdf").alias("voxeldiscface").setSupplier(VoxelDiscFaceBrush::new).setPermission("voxelsniper.brush.voxeldiscface").build();
    }

    private static BrushData warpBrush() {
        return new BrushBuilder().name("Warp").alias("w").alias("warp").setSupplier(WarpBrush::new).setPermission("voxelsniper.brush.warp").build();
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
     * @param brushData The brush data to check.
     * @return All Sniper registered handles for the brush.
     */
    public Set<String> getSniperBrushHandles(BrushData brushData) {
        Set<String> handles = new HashSet<>();
        for (String key : brushes.keySet()) {
            if (brushes.get(key).equals(brushData)) {
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
