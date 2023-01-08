package com.github.kevindagame.util;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import net.kyori.adventure.text.ComponentLike;

import java.util.stream.Collectors;

/**
 * Messaging handler for various Voxel functions.
 * // TODO: Rewrite messaging to builder functions.
 * // TODO: Standardize message colors.
 */
public record VoxelMessage(SnipeData snipeData) {

    private static final int BRUSH_SIZE_WARNING_THRESHOLD = 20;

    /**
     * Send a brush message styled message to the player.
     *
     * @param brushMessage
     */
    public void brushMessage(ComponentLike brushMessage) {
        snipeData.sendMessage(Messages.BRUSH_MESSAGE_PREFIX.append(brushMessage));
    }

    /**
     * Display Brush Name.
     *
     * @param brushName
     */
    public void brushName(String brushName) {
        snipeData.sendMessage(Messages.BRUSH_NAME.replace("%name%", brushName));
    }

    /**
     * Display Center Parameter.
     */
    public void center() {
        snipeData.sendMessage(Messages.BRUSH_CENTER.replace("%center%", snipeData.getcCen()));
    }

    /**
     * Display custom message.
     *
     * @param message
     */
    public void custom(ComponentLike message) {
        snipeData.sendMessage(message);
    }

    /**
     * Display voxel type.
     */
    public void voxel() {
        snipeData.sendMessage(Messages.VOXEL_MAT.replace("%mat%", snipeData.getVoxelMaterial()));
    }

    /**
     * Display data value.
     */
    public void data() {
        snipeData.sendMessage(Messages.VOXEL_DATA.replace("%data%", snipeData.getVoxelSubstance().getAsString()));
    }

    /**
     * Display voxel height.
     */
    public void height() {
        snipeData.sendMessage(Messages.BRUSH_HEIGHT.replace("%height%", snipeData.getVoxelHeight()));
    }

    /**
     * Display performer.
     *
     * @param performerName
     */
    public void performerName(String performerName) {
        snipeData.sendMessage(Messages.PERFORMER.replace("%performer%", performerName));
    }

    /**
     * Display replace material.
     */
    public void replace() {
        snipeData.sendMessage(Messages.REPLACEMENT_MAT.replace("%mat%", snipeData.getReplaceMaterial()));
    }

    /**
     * Display replace data value.
     */
    public void replaceData() {
        snipeData.sendMessage(Messages.REPLACEMENT_DATA.replace("%data%", snipeData.getReplaceSubstance().getAsString()));
    }

    /**
     * Display brush size.
     */
    public void size() {
        snipeData.sendMessage(Messages.BRUSH_SIZE.replace("%size%", snipeData.getBrushSize()));
        if (snipeData.getBrushSize() >= BRUSH_SIZE_WARNING_THRESHOLD) {
            snipeData.sendMessage(Messages.BRUSH_SIZE_LARGE);
        }
    }

    /**
     * Display toggle lightning message.
     */
    public void toggleLightning() {
        String state = ((snipeData.owner().getSnipeData(snipeData.owner().getCurrentToolId()).isLightningEnabled()) ? "on" : "off");
        snipeData.sendMessage(Messages.TOGGLE_LIGHTNING.replace("%state%", state));
    }

    /**
     * Display toggle printout message.
     */
    public void togglePrintout() {
        // TODO seems to do the same as toggleLightning()
        String state = ((snipeData.owner().getSnipeData(snipeData.owner().getCurrentToolId()).isLightningEnabled()) ? "on" : "off");
        snipeData.sendMessage(Messages.TOGGLE_PRINTOUT.replace("%state%", state));
    }

    /**
     * Display toggle range message.
     */
    public void toggleRange() {
        String state = ((snipeData.owner().getSnipeData(snipeData.owner().getCurrentToolId()).isRanged()) ? "on" : "off");
        double range = snipeData.owner().getSnipeData(snipeData.owner().getCurrentToolId()).getRange();
        snipeData.sendMessage(Messages.TOGGLE_RANGE.replace("%state%", state).replace("%range%", range));
    }

    /**
     * Display voxel list.
     */
    public void voxelList() {
        if (snipeData.getVoxelList().isEmpty()) {
            snipeData.sendMessage(Messages.VOXEL_LIST_EMPTY);
        } else {

            String blocks = snipeData.getVoxelList().getList().stream().map(VoxelMaterial::getKey).collect(Collectors.joining(","));
            snipeData.sendMessage(Messages.VOXEL_LIST.replace("%blocks%", blocks));
        }
    }
}
