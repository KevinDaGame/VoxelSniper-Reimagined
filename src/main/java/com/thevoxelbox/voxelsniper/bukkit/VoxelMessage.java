package com.thevoxelbox.voxelsniper.bukkit;

import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import org.bukkit.ChatColor;

import java.util.stream.Collectors;

/**
 *  Messaging handler for various Voxel functions.
 *  // TODO: Rewrite messaging to builder functions.
 *  // TODO: Standardize message colors.
 */
public class VoxelMessage {

    private static final int BRUSH_SIZE_WARNING_THRESHOLD = 20;
    private final SnipeData snipeData;

    /**
     * @param snipeData
     */
    public VoxelMessage(SnipeData snipeData) {
        this.snipeData = snipeData;
    }

    /**
     * Send a brush message styled message to the player.
     *
     * @param brushMessage
     */
    public void brushMessage(String brushMessage) {
        snipeData.sendMessage(ChatColor.LIGHT_PURPLE + brushMessage);
    }

    /**
     * Display Brush Name.
     *
     * @param brushName
     */
    public void brushName(String brushName) {
        snipeData.sendMessage(ChatColor.AQUA + "Brush Type: " + ChatColor.LIGHT_PURPLE + brushName);
    }

    /**
     * Display Center Parameter.
     */
    public void center() {
        snipeData.sendMessage(ChatColor.DARK_BLUE + "Brush Center: " + ChatColor.DARK_RED + snipeData.getcCen());
    }

    /**
     * Display custom message.
     *
     * @param message
     */
    public void custom(String message) {
        snipeData.sendMessage(message);
    }

    /**
     * Display voxel type.
     */
    public void voxel() {
        snipeData.sendMessage(ChatColor.GOLD + "Voxel Material: " + ChatColor.RED + snipeData.getVoxelMaterial());
    }

    /**
     * Display data value.
     */
    public void data() {
        snipeData.sendMessage(ChatColor.BLUE + "Voxel Data Value: " + ChatColor.DARK_RED + snipeData.getVoxelSubstance().getAsString());
    }

    /**
     * Display voxel height.
     */
    public void height() {
        snipeData.sendMessage(ChatColor.DARK_AQUA + "Brush Height: " + ChatColor.DARK_RED + snipeData.getVoxelHeight());
    }

    /**
     * Display performer.
     *
     * @param performerName
     */
    public void performerName(String performerName) {
        this.snipeData.sendMessage(ChatColor.DARK_PURPLE + "Performer: " + ChatColor.DARK_GREEN + performerName);
    }

    /**
     * Display replace material.
     */
    public void replace() {
        snipeData.sendMessage(ChatColor.AQUA + "Replace Target Material: " + ChatColor.RED + snipeData.getReplaceMaterial());
    }

    /**
     * Display replace data value.
     */
    public void replaceData() {
        snipeData.sendMessage(ChatColor.DARK_GRAY + "Replace Target Data Value: " + ChatColor.DARK_RED + snipeData.getReplaceSubstance().getAsString());
    }

    /**
     * Display brush size.
     */
    public void size() {
        snipeData.sendMessage(ChatColor.GREEN + "Brush Size: " + ChatColor.DARK_RED + snipeData.getBrushSize());
        if (snipeData.getBrushSize() >= BRUSH_SIZE_WARNING_THRESHOLD) {
            snipeData.sendMessage(ChatColor.RED + "WARNING: Large brush size selected!");
        }
    }

    /**
     * Display toggle lightning message.
     */
    public void toggleLightning() {
        snipeData.sendMessage(ChatColor.GOLD + "Lightning mode has been toggled " + ChatColor.DARK_RED + ((snipeData.owner().getSnipeData(snipeData.owner().getCurrentToolId()).isLightningEnabled()) ? "on" : "off"));
    }

    /**
     * Display toggle printout message.
     */
    public final void togglePrintout() {
        snipeData.sendMessage(ChatColor.GOLD + "Brush info printout mode has been toggled " + ChatColor.DARK_RED + ((snipeData.owner().getSnipeData(snipeData.owner().getCurrentToolId()).isLightningEnabled()) ? "on" : "off"));
    }

    /**
     * Display toggle range message.
     */
    public void toggleRange() {
        snipeData.sendMessage(ChatColor.GOLD + "Distance Restriction toggled " + ChatColor.DARK_RED + ((snipeData.owner().getSnipeData(snipeData.owner().getCurrentToolId()).isRanged()) ? "on" : "off") + ChatColor.GOLD + ". Range is " + ChatColor.LIGHT_PURPLE + (double) snipeData.owner().getSnipeData(snipeData.owner().getCurrentToolId()).getRange());
    }

    /**
     * Display voxel list.
     */
    public void voxelList() {
        if (snipeData.getVoxelList().isEmpty()) {
            snipeData.sendMessage(ChatColor.DARK_GREEN + "No blocks selected!");
        } else {

            String returnValueBuilder = ChatColor.DARK_GREEN +
                    "Block Types Selected: " +
                    ChatColor.AQUA +
                    snipeData.getVoxelList().getList().stream().map(e -> e.getKey().toString()).collect(Collectors.joining(","));

            snipeData.sendMessage(returnValueBuilder);
        }
    }
}
