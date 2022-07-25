package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeAction;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;

import java.util.HashMap;
import java.util.List;

import org.bukkit.block.Block;

/**
 * Brush Interface.
 *
 */
public interface IBrush {

    /**
     * @param vm Message object
     */
    void info(VoxelMessage vm);

    /**
     * Handles parameters passed to brushes.
     *
     * @param triggerHandle the handle that triggered this brush
     * @param params Array of string containing parameters
     * @param v Snipe Data
     */
    void parseParameters(String triggerHandle, String[] params, SnipeData v);

    boolean perform(SnipeAction action, SnipeData data, Block targetBlock, Block lastBlock);

    /**
     * @return The name of the Brush
     */
    String getName();

    /**
     * @param name New name for the Brush
     */
    void setName(String name);

    /**
     * @return The name of the category the brush is in.
     */
    String getBrushCategory();

    /**
     * @return Permission node required to use this brush
     */
    String getPermissionNode();

    /**
     * Registers the additional arguments for the tab completion
     *
     * @return
     */
    List<String> registerArguments();

    /**
     * Registers the additional arguments for the tab completion
     *
     * @return
     */
    HashMap<String, List<String>> registerArgumentValues();
}
