package com.thevoxelbox.voxelsniper.util;

import java.util.Locale;

import org.bukkit.ChatColor;

public interface Messages0 {
    // TODO move this into YAML (probably with some kind of tool)
    public static final String EXTRUDEBRUSH_SMOOTH_CIRCLE_MODE = ChatColor.GOLD + "Using smooth circles mode";
    public static final String ELLIPSEBRUSH_FILL_MODE = (ChatColor.AQUA + "Fill mode is %state%");
    public static final String BRUSH_NO_PARAMS_ACCEPTED = ChatColor.RED + "This brush does not accept additional parameters.";
    public static final String TARGET_MUST_BE_VISIBLE = ChatColor.RED + "Snipe target block must be visible.";
    public static final String ENTITYBRUSH_USAGE = (ChatColor.GOLD + "Entity Brush Parameters:\n" + ChatColor.AQUA + "/b " + "%triggerHandle%" + " [entityType] -- Change brush to the specified entity type");
    public static final String ENTITYBRUSH_ENTITY_TYPE = (ChatColor.GOLD + "Entity type: " + ChatColor.DARK_GREEN + "%type%");
    public static final String ENTITYBRUSH_UNKNOWN_ENTITY = ChatColor.RED + "That entity type does not exist.";
    public static final String ENTITYBRUSH_SPAWN_FAIL = ChatColor.RED + "Cannot spawn entity!";
    public static final String BALLBRUSH_USAGE = (ChatColor.GOLD + "Ball Brush Parameters:\n" + ChatColor.AQUA + "/b " + "%triggerHandle%" + " smooth -- Toggle using smooth sphere algorithm (default: false)");
    public static final String BALLBRUSH_SMOOTHSPHERE = (ChatColor.AQUA + "Smooth sphere algorithm: " + "%smoothSphere%");
    public static final String BRUSH_INVALID_PARAM = (ChatColor.RED + "Invalid parameter! Use " + ChatColor.LIGHT_PURPLE + "'/b " + "%triggerHandle%" + " info'" + ChatColor.RED + " to display valid parameters.");
    public static final String BIOME_DOES_NOT_EXIST = ChatColor.RED + "That biome does not exist.";
    public static final String BRUSH_NO_UNDO = ChatColor.RED + "THIS BRUSH DOES NOT UNDO.";
}
