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

    private static BrushData biomeBrush() {
        return new BrushBuilder().name("biome").alias("bio").setSupplier(BiomeBrush::new).build();
    }

    private static BrushData ballBrush() {
        return new BrushBuilder().name("ball").alias("b").setSupplier(BallBrush::new).build();
    }

    private static BrushData biomeBallBrush() {
        return new BrushBuilder().name("biomeball"
        ).alias("bioball").setSupplier(BiomeBallBrush::new).build();
    }

    private static BrushData blendBallBrush() {
        return new BrushBuilder().name("blendball"
        ).alias("bb").setSupplier(BlendBallBrush::new).build();
    }

    private static BrushData blendDiscBrush() {
        return new BrushBuilder().name("blenddisc"
        ).alias("bd").setSupplier(BlendDiscBrush::new).build();
    }

    private static BrushData blendVoxelBrush() {
        return new BrushBuilder().name("blendvoxel"
        ).alias("bv").setSupplier(BlendVoxelBrush::new).build();
    }

    private static BrushData blendVoxelDiscBrush() {
        return new BrushBuilder().name("blendvoxeldisc"
        ).alias("bvd").setSupplier(BlendVoxelDiscBrush::new).build();
    }

    private static BrushData blobBrush() {
        return new BrushBuilder().name("splatblob"
        ).alias("blob").setSupplier(BlobBrush::new).build();
    }

    private static BrushData blockResetBrush() {
        return new BrushBuilder().name("blockresetbrush"
        ).alias("brb").setSupplier(BlockResetBrush::new).build();
    }

    private static BrushData blockResetSurfaceBrush() {
        return new BrushBuilder().name("blockresetbrushsurface"
        ).alias("brbs").setSupplier(BlockResetSurfaceBrush::new).build();
    }

    private static BrushData canyonBrush() {
        return new BrushBuilder().name("canyon"
        ).alias("ca").setSupplier(CanyonBrush::new).build();
    }

    private static BrushData canyonSelectionBrush() {
        return new BrushBuilder().name("canyonselection"
        ).alias("cas").setSupplier(CanyonSelectionBrush::new).build();
    }

    private static BrushData checkerVoxelDiscBrush() {
        return new BrushBuilder().name("checkervoxeldisc"
        ).alias("cvd").setSupplier(CheckerVoxelDiscBrush::new).build();
    }

    private static BrushData cleanSnowBrush() {
        return new BrushBuilder().name("cleansnow"
        ).alias("cls").setSupplier(CleanSnowBrush::new).build();
    }

    private static BrushData cloneStampBrush() {
        return new BrushBuilder().name("clonestamp"
        ).alias("cs").setSupplier(CloneStampBrush::new).build();
    }

    private static BrushData cometBrush() {
        return new BrushBuilder().name("comet"
        ).alias("com").setSupplier(CometBrush::new).build();
    }

    private static BrushData copyPastaBrush() {
        return new BrushBuilder().name("copypasta"
        ).alias("cp").setSupplier(CopyPastaBrush::new).build();
    }

    private static BrushData cylinderBrush() {
        return new BrushBuilder().name("cylinder"
        ).alias("c").setSupplier(CylinderBrush::new).build();
    }

    private static BrushData discBrush() {
        return new BrushBuilder().name("disc"
        ).alias("d").setSupplier(DiscBrush::new).build();
    }

    private static BrushData discFaceBrush() {
        return new BrushBuilder().name("discface"
        ).alias("df").setSupplier(DiscFaceBrush::new).build();
    }

    private static BrushData domeBrush() {
        return new BrushBuilder().name("domebrush"
        ).alias("dome").setSupplier(DomeBrush::new).build();
    }

    private static BrushData drainBrush() {
        return new BrushBuilder().name("drain"
        ).setSupplier(DrainBrush::new).build();
    }

    private static BrushData ellipseBrush() {
        return new BrushBuilder().name("ellipse"
        ).alias("el").setSupplier(EllipseBrush::new).build();
    }

    private static BrushData ellipsoidBrush() {
        return new BrushBuilder().name("ellipsoid"
        ).alias("elo").setSupplier(EllipsoidBrush::new).build();
    }

    private static BrushData entityBrush() {
        return new BrushBuilder().name("entity"
        ).alias("en").setSupplier(EntityBrush::new).build();
    }

    private static BrushData entityRemovalBrush() {
        return new BrushBuilder().name("entityremoval"
        ).alias("er").setSupplier(EntityRemovalBrush::new).build();
    }

    private static BrushData eraserBrush() {
        return new BrushBuilder().name("eraser"
        ).alias("erase").setSupplier(EraserBrush::new).build();
    }

    private static BrushData erodeBrush() {
        return new BrushBuilder().name("erode"
        ).alias("e").setSupplier(ErodeBrush::new).build();
    }

    private static BrushData extrudeBrush() {
        return new BrushBuilder().name("extrude"
        ).alias("ex").setSupplier(ExtrudeBrush::new).build();
    }

    private static BrushData fillDownBrush() {
        return new BrushBuilder().name("filldown"
        ).alias("fd").setSupplier(FillDownBrush::new).build();
    }

    private static BrushData flatOceanBrush() {
        return new BrushBuilder().name("flatocean"
        ).alias("fo").setSupplier(FlatOceanBrush::new).build();
    }

    private static BrushData generateTreeBrush() {
        return new BrushBuilder().name("generatetree"
        ).alias("gt").setSupplier(GenerateTreeBrush::new).build();
    }

    private static BrushData jaggedLineBrush() {
        return new BrushBuilder().name("jagged"
        ).alias("j").setSupplier(JaggedLineBrush::new).build();
    }

    private static BrushData jockeyBrush() {
        return new BrushBuilder().name("jockey"
        ).setSupplier(JockeyBrush::new).build();
    }

    private static BrushData lightningBrush() {
        return new BrushBuilder().name("lightning"
        ).alias("light").setSupplier(LightningBrush::new).build();
    }

    private static BrushData lineBrush() {
        return new BrushBuilder().name("line"
        ).alias("l").setSupplier(LineBrush::new).build();
    }

    private static BrushData moveBrush() {
        return new BrushBuilder().name("move"
        ).alias("mv").setSupplier(MoveBrush::new).build();
    }

    private static BrushData oceanBrush() {
        return new BrushBuilder().name("ocean"
        ).alias("o").setSupplier(OceanBrush::new).build();
    }

    private static BrushData overlayBrush() {
        return new BrushBuilder().name("overlay"
        ).alias("over").setSupplier(OverlayBrush::new).build();
    }

    private static BrushData paintingBrush() {
        return new BrushBuilder().name("painting"
        ).setSupplier(PaintingBrush::new).build();
    }

    private static BrushData pullBrush() {
        return new BrushBuilder().name("pull"
        ).setSupplier(PullBrush::new).build();
    }

    private static BrushData regenerateChunkBrush() {
        return new BrushBuilder().name("regeneratechunk"
        ).alias("rc").setSupplier(RegenerateChunkBrush::new).build();
    }

    private static BrushData ringBrush() {
        return new BrushBuilder().name("ring"
        ).alias("ri").setSupplier(RingBrush::new).build();
    }

    private static BrushData rulerBrush() {
        return new BrushBuilder().name("ruler"
        ).alias("r").setSupplier(RulerBrush::new).build();
    }

    private static BrushData scannerBrush() {
        return new BrushBuilder().name("scanner"
        ).alias("sc").setSupplier(ScannerBrush::new).build();
    }

    private static BrushData setBrush() {
        return new BrushBuilder().name("set"
        ).setSupplier(SetBrush::new).build();
    }

    private static BrushData shellBallBrush() {
        return new BrushBuilder().name("shellball"
        ).alias("shb").setSupplier(ShellBallBrush::new).build();
    }

    private static BrushData shellSetBrush() {
        return new BrushBuilder().name("shellset"
        ).alias("shs").setSupplier(ShellSetBrush::new).build();
    }

    private static BrushData shellVoxelBrush() {
        return new BrushBuilder().name("shellvoxel"
        ).alias("shv").setSupplier(ShellVoxelBrush::new).build();
    }

    private static BrushData signOverwriteBrush() {
        return new BrushBuilder().name("signoverwriter"
        ).alias("sio").setSupplier(SignOverwriteBrush::new).build();
    }

    private static BrushData snipeBrush() {
        return new BrushBuilder().name("snipe"
        ).alias("s").setSupplier(SnipeBrush::new).build();
    }

    private static BrushData splatterBallBrush() {
        return new BrushBuilder().name("splatball"
        ).alias("sb").setSupplier(SplatterBallBrush::new).build();
    }

    private static BrushData splatterDiscBrush() {
        return new BrushBuilder().name("splatdisc"
        ).alias("sd").setSupplier(SplatterDiscBrush::new).build();
    }

    private static BrushData splatterOverlayBrush() {
        return new BrushBuilder().name("splatteroverlay"
        ).alias("sover").setSupplier(SplatterOverlayBrush::new).build();
    }

    private static BrushData splineBrush() {
        return new BrushBuilder().name("spline"
        ).alias("sp").setSupplier(SplineBrush::new).build();
    }

    private static BrushData splatterVoxelBrush() {
        return new BrushBuilder().name("splattervoxel"
        ).alias("sv").setSupplier(SplatterVoxelBrush::new).build();
    }

    private static BrushData splatterVoxelDiscBrush() {
        return new BrushBuilder().name("splatvoxeldisc"
        ).alias("svd").setSupplier(SplatterVoxelDiscBrush::new).build();
    }

    private static BrushData threePointCircleBrush() {
        return new BrushBuilder().name("threepointcircle"
        ).alias("tpc").setSupplier(ThreePointCircleBrush::new).build();
    }

    private static BrushData treeSnipeBrush() {
        return new BrushBuilder().name("t").setSupplier(TreeSnipeBrush::new).build();
    }

    private static BrushData triangleBrush() {
        return new BrushBuilder().name("triangle"
        ).alias("tri").setSupplier(TriangleBrush::new).build();
    }

    private static BrushData underlayBrush() {
        return new BrushBuilder().name("underlay"
        ).alias("under").setSupplier(UnderlayBrush::new).build();
    }

    private static BrushData voltMeterBrush() {
        return new BrushBuilder().name("voltmeter"
        ).alias("volt").setSupplier(VoltMeterBrush::new).build();
    }

    private static BrushData voxelBrush() {
        return new BrushBuilder().name("voxel"
        ).alias("v").setSupplier(VoxelBrush::new).build();
    }

    private static BrushData voxelDiscBrush() {
        return new BrushBuilder().name("voxeldisc"
        ).alias("vd").setSupplier(VoxelDiscBrush::new).build();
    }

    private static BrushData voxelDiscFaceBrush() {
        return new BrushBuilder().name("voxeldiscface"
        ).alias("vdf").setSupplier(VoxelDiscFaceBrush::new).build();
    }

    private static BrushData warpBrush() {
        return new BrushBuilder().name("warp").alias("w").setSupplier(WarpBrush::new).build();
    }

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
