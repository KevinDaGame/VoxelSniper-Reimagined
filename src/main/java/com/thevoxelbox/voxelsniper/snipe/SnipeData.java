package com.thevoxelbox.voxelsniper.snipe;

import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.util.VoxelList;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;

/**
 * @author Piotr
 */
public class SnipeData {

    // Default values
    public static final int DEFAULT_CYLINDER_CENTER = 0;
    public static final int DEFAULT_VOXEL_HEIGHT = 1;
    public static final int DEFAULT_BRUSH_SIZE = 3;
    public static final BlockData DEFAULT_VOXEL_SUBSTANCE = Material.AIR.createBlockData();
    public static final BlockData DEFAULT_TARGET_SUBSTANCE = Material.AIR.createBlockData();

    // Sniper varaibles
    private final Sniper owner;
    private VoxelMessage voxelMessage;

    private int range = 0;
    private boolean ranged = false;
    private final boolean lightning = false;

    private int brushSize = SnipeData.DEFAULT_BRUSH_SIZE;

    // Voxel and ReplaceTarget Materials & BlockData
    private BlockData voxelSubstance = SnipeData.DEFAULT_VOXEL_SUBSTANCE;
    private BlockData replaceSubstance = SnipeData.DEFAULT_TARGET_SUBSTANCE;

    // Others
    private int cCen = SnipeData.DEFAULT_CYLINDER_CENTER;
    private VoxelList voxelList = new VoxelList();
    private int voxelHeight = SnipeData.DEFAULT_VOXEL_HEIGHT;

    public SnipeData(final Sniper vs) {
        this.owner = vs;
    }

    public VoxelMessage getVoxelMessage() {
        return voxelMessage;
    }

    public void setVoxelMessage(VoxelMessage voxelMessage) {
        this.voxelMessage = voxelMessage;
    }

    public int getBrushSize() {
        return brushSize;
    }

    public void setBrushSize(int brushSize) {
        this.brushSize = brushSize;
    }

    public VoxelList getVoxelList() {
        return voxelList;
    }

    public void setVoxelList(VoxelList voxelList) {
        this.voxelList = voxelList;
    }

    public int getVoxelHeight() {
        return voxelHeight;
    }

    public void setVoxelHeight(int voxelHeight) {
        this.voxelHeight = voxelHeight;
    }

    public BlockData getVoxelSubstance() {
        return voxelSubstance;
    }

    public Material getVoxelMaterial() {
        return voxelSubstance.getMaterial();
    }

    public void setVoxelSubstance(BlockData voxelSubstance) {
        this.voxelSubstance = voxelSubstance;
    }

    public BlockData getReplaceSubstance() {
        return replaceSubstance;
    }

    public Material getReplaceMaterial() {
        return replaceSubstance.getMaterial();
    }

    public void setReplaceSubstance(BlockData targetSubstance) {
        this.replaceSubstance = targetSubstance;
    }

    public int getcCen() {
        return cCen;
    }

    public void setcCen(int cCen) {
        this.cCen = cCen;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public boolean isRanged() {
        return ranged;
    }

    public void setRanged(boolean ranged) {
        this.ranged = ranged;
    }

    public boolean isLightningEnabled() {
        return lightning;
    }

    public final World getWorld() {
        return this.owner.getPlayer().getWorld();
    }

    public final Sniper owner() {
        return this.owner;
    }

    /**
     * Reset to default values.
     */
    public final void reset() {
        this.voxelSubstance = SnipeData.DEFAULT_VOXEL_SUBSTANCE;
        this.replaceSubstance = SnipeData.DEFAULT_TARGET_SUBSTANCE;

        this.brushSize = SnipeData.DEFAULT_BRUSH_SIZE;

        this.voxelHeight = SnipeData.DEFAULT_VOXEL_HEIGHT;
        this.cCen = SnipeData.DEFAULT_CYLINDER_CENTER;
        this.voxelList = new VoxelList();
    }

    public final void sendMessage(final String message) {
        this.owner.getPlayer().sendMessage(message);
    }

}
