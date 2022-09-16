package com.thevoxelbox.voxelsniper.util;

import com.thevoxelbox.voxelsniper.voxelsniper.IVoxelsniper;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Pattern;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;

import org.jetbrains.annotations.NotNull;

public enum Messages implements ComponentLike {
    //<editor-fold defaultstate="collapsed" desc="Messages">
    BRUSH_MESSAGE_PREFIX,
    BRUSH_NAME,
    BRUSH_CENTER,
    BRUSH_HEIGHT,
    BRUSH_SIZE,
    BRUSH_SIZE_LARGE,
    VOXEL_MAT,
    VOXEL_DATA,
    VOXEL_LIST,
    VOXEL_LIST_EMPTY,
    REPLACEMENT_MAT,
    REPLACEMENT_DATA,
    PERFORMER,
    TOGGLE_LIGHTNING,
    TOGGLE_PRINTOUT,
    TOGGLE_RANGE,
    PERFORMER_MESSAGE,
    BLOCKS_UNTIL_REPEATER_MESSAGE,
    BLOCK_POWER_MESSAGE,
    FACE_POWER_MESSAGE,
    TREESNIPE_USAGE,
    TREESNIPE_BRUSH_MESSAGE,
    VOLTMETER_BRUSH_MESSAGE,
    DEFAULT_STAMP,
    NO_AIR_STAMP,
    FILL_STAMP,
    STAMP_ERROR,
    OFF_WORLD_END_POS,
    OFF_WORLD_START_POS,
    ENTITY_BRUSH_MESSAGE,
    LIGHTNING_BRUSH_MESSAGE,
    SELECTION_SIZE_ABOVE_LIMIT,
    POINT_1_SET,
    POINT_2_SET,
    REGENERATE_CHUNK_MESSAGE,
    ROT3D_BRUSH_MESSAGE,
    BRUSH_SMOOTH_CIRCLE,
    ELLIPSEBRUSH_FILL_MODE,
    BRUSH_NO_PARAMS_ACCEPTED,
    TARGET_MUST_BE_VISIBLE,
    ENTITYBRUSH_USAGE,
    ENTITYBRUSH_ENTITY_TYPE,
    ENTITYBRUSH_UNKNOWN_ENTITY,
    ENTITYBRUSH_SPAWN_FAIL,
    BALLBRUSH_USAGE,
    SMOOTHSPHERE_ALGORITHM,
    BRUSH_INVALID_PARAM,
    BIOME_DOES_NOT_EXIST,
    BRUSH_NO_UNDO,
    REFRESHED_CHUNK,
    GOTO_INVALID_SYNTAX,
    GOTO_MSG,
    PAINTING_INVALID_SYNTAX,
    UNKNOWN_PAINTING,
    PAINTING_SET,
    INVALID_INPUT,
    ERROR,
    NOTHING_TO_UNDO,
    UNDO_SUCCESSFUL,
    SELECTED_BIOME_TYPE,
    BIOME_BRUSH_USAGE,
    BLEND_BALL_BRUSH_USAGE,
    BLEND_BRUSH_WATER_MODE,
    BLEND_DISC_BRUSH_USAGE,
    BLEND_VOXEL_BRUSH_USAGE,
    BALL_BLEND_BRUSH_USAGE,
    GROWTH_PERCENT_SET,
    CANYON_BRUSH_USAGE,
    CANYON_BRUSH_SHIFT_LEVEL,
    INPUT_NO_NUMBER,
    ELLIPSOID_BRUSH_USAGE,
    AXIS_SET_TO_VALUE,
    BLOB_BRUSH_USAGE,
    BLOB_BRUSH_SET_DEFAULT,
    GROWTH_PERCENT_RANGE,
    JAGGED_LINE_BRUSH_USAGE,
    JAGGED_LINE_BRUSH_RECURSION_RANGE,
    JAGGED_LINE_BRUSH_RECURSION_SET,
    JAGGED_LINE_BRUSH_SET_SPREAD,
    FIRST_COORDINATE_NOT_SET,
    FIRST_POINT_SELECTED,
    SECOND_POINT_SELECTED,
    THIRD_POINT_SELECTED,
    POINTS_CLEARED,
    CLEAN_SNOW_BURSH_USAGE,
    CHECKER_DISC_BRUSH_USAGE,
    CLONE_STAMP_BRUSH_USAGE,
    DISC_BRUSH_USAGE,
    DISC_FACE_BRUSH_USAGE,
    EXTRUDE_BRUSH_USAGE,
    CHECKER_USING_WORLD_CORDS,
    ROTATION_3D_BRUSH_USAGE,
    COMET_BRUSH_USAGE,
    COPYPASTA_BRUSH_USAGE,
    CYLINDER_BRUSH_USAGE,
    DRAIN_BRUSH_USAGE,
    ELLIPSE_BRUSH_USAGE,
    ENTITY_REMOVAL_BRUSH_USAGE,
    ERODE_BRUSH_USAGE,
    FILL_DOWN_BRUSH_USAGE,
    FLAT_OCEAN_BRUSH_USAGE,
    GEN_TREE_BRUSH_USAGE_1,
    GEN_TREE_BRUSH_USAGE_2,
    HEAT_RAY_BRUSH_USAGE,
    JOCKEY_BRUSH_USAGE,
    MOVE_BRUSH_USAGE,
    OCEAN_BRUSH_USAGE,
    OVERLAY_BRUSH_USAGE,
    RING_BRUSH_USAGE,
    ROTATE_2D_BRUSH_USAGE,
    SCANNER_BRUSH_USAGE,
    SPLATTER_BALL_BRUSH_USAGE,
    SPLATTER_DISC_BRUSH_USAGE,
    SPLATTER_OVERLAY_BRUSH_USAGE,
    SPLATTER_FOXEL_BRUSH_USAGE,
    SPLATTER_VOXEL_DISC_BRUSH_USAGE,
    SPLINE_BRUSH_USAGE,
    THREE_POINT_CIRCLE_BRUSH_USAGE,
    UNDERLAY_BRUSH_USAGE,
    SIGN_BRUSH_EMPTY_LINE,
    SIGN_BRUSH_USAGE,
    MISSING_PARAMETER,
    PARAMETER_PARSE_ERROR,
    ENTITY_REMOVE_COUNT,
    SIGN_OVERWRITE_BRUSH_RANGE_MODE,
    SIGN_OVERWRITE_ENABLED,
    SIGN_OVERWRITE_DISABLED,
    SIGN_OVERWRITE_BUFFER_TEXT,
    FILE_LOAD_SUCCESSFUL,
    FILE_LOAD_FAIL,
    THIS_FILE_DOES_NOT_EXIST,
    FILE_SAVE_SUCCESSFUL,
    FILE_SAVE_FAIL,
    SIGN_OVERWRITE_EXCEED_CHAR_LIMIT,
    SIGN_BUFFER_TEXT_SET,
    SIGN_LINE_ENABLED_DISABLED,
    TARGET_BLOCK_NO_SIGN,
    SIGN_BUFFER_CLEARED,
    SIGN_BUFFER_CLEARED_ENABLED,
    BRUSH_SIZE_SET,
    BRUSH_HEIGHT_SET,
    NO_SIGN_FOUND,
    BRUSH_RESET_DEFAULT,
    OVERLAY_DEPTH_SET,
    SHIFT_LEVEL_SET,
    LAND_WILL_BE_SHIFTED_TO_Y,
    BLOCKS_COPIED_SUCCESSFULLY,
    CLONE_STAMP_NO_AIR,
    CLONE_STAMP_FILL,
    CLONE_STAMP_DEFAULT_MODE,
    COMET_SIZE,
    BLOCKS_COPIED_COUNT,
    COPY_AREA_TOO_BIG,
    MUST_SELECT_2_POINTS,
    PASTE_AIR_STATE,
    PIVOT_ANGLE,
    BLOCKS_PASTED_COUNT,
    CYLINDER_SHIFT_Y,
    DRAIN_TRUE_CIRCLE_MODE,
    DISC_DRAIN_MODE,
    DRAIN_BRUSH_SHAPE,
    INVALID_TARGET,
    X_SIZE_SET,
    Y_SIZE_SET,
    ELLIPSE_RENDER_STEP_NUMBER,
    INVALID_X_SCALE,
    X_SCALE_MODIFIER_SET,
    INVALID_Y_SCALE,
    Y_SCALE_MODIFIER_SET,
    INVALID_STEP_AMOUNT,
    RENDER_STEP_NUMBER_SET,
    ENTITY_REMOVAL_REGEX_ERROR,
    ENTITY_REMOVAL_EXCLUSIONS,
    ENTITY_REMOVAL_EXCLUSION_LIST,
    ENTITY_REMOVAL_RESET,
    ENTITY_REMOVAL_CLEAR,
    ENTITY_REMOVAL_ADD,
    ENTITY_REMOVAL_REMOVE,
    ENTITY_REMOVAL_NOT_IN_LIST,
    PRESET_DOES_NOT_EXIST,
    ACTIVE_BRUSH_PRESET,
    BRUSH_PRESET_CHANGED,
    WATER_LEVEL_SET,
    OCEAN_FLOOR_LEVEL_SET,
    LEAVES_MAT_SET,
    WOOD_LOG_MAT_SET,
    INVALID_TYPE,
    THICKNESS_SET,
    START_HEIGHT_SET,
    TRUNK_SLOPE_SET,
    BRANCH_LENGTH_SET,
    ROOT_LENGTH_SET,
    FLOATING_ROOTS_SET,
    MIN_ROOTS_SET,
    MIN_ROOTS_ABOVE_MAX,
    MAX_ROOTS_BELOW_MIN,
    MAX_ROOTS_SET,
    MIN_HEIGHT_SET,
    MIN_HEIGHT_ABOVE_MAX,
    MAX_HEIGHT_BELOW_MIN,
    MAX_HEIGHT_SET,
    LEAF_THICKNESS_MAX_SET,
    LEAF_THICKNESS_MIN_SET,
    HEAT_RAY_BRUSH_INFO,
    SITTING_ON_CLOSEST_ENTITY,
    NO_PLAYERS_FOUND_TO_SIT_ON,
    NO_ENTITIES_FOUND_TO_SIT_ON,
    YOU_BROKE_THE_STACK,
    ENTITY_EJECTED,
    YOU_HAVE_BEEN_EJECTED,
    CURRENT_JOCKEY_MODE,
    JOCKEY_TARGETING_PLAYERS,
    JOCKEY_TARGETING_ENTITIES,
    LINE_BRUSH_INFO,
    MOVE_BRUSH_RESET,
    MOVE_DIRECTION_SET,
    OCEAN_LEVEL_AT_LEAST_12,
    OCEAN_FLOOR_COVER,
    OVERLAY_BRUSH_VOXELLIST_EMPTY,
    INVALID_BRUSH_PARAM,
    INVALID_BRUSH_PARAM_ANGLE,
    PUNISH_BRUSH_USAGE,
    NO_LONGER_TARGETING_A_SPECIFIC_PLAYER,
    NOW_TARGETING_A_SPECIFIC_PLAYER,
    PUNISHMENT_APPLIED_NUMBER,
    PUNISHMENT_NAME,
    PUNISHMENT_DOES_NOT_EXIST,
    INNER_RADIUS_INFO,
    INNER_RADIUS_SET,
    ANGLE_SET,
    RULER_BRUSH_USAGE,
    SCANNER_FOUND_BLOCKS,
    SCANNER_FOUND_NO_BLOCKS,
    SCANNER_BRUSH_INFO,
    SCANNER_DEPTH_SET,
    SELECTED_POINTS_DIFFERENT_WORLD,
    SELECTION_SIZE_LIMIT,
    ANGLE_AROUND_AXIS_SET,
    SHELL_BRUSH_COMPLETE,
    BRUSH_SEED_PERCENT_SET,
    BRUSH_RECURSION_SET,
    SPLATTER_BALL_BRUSH_RECURSION_RANGE,
    SEED_PERCENT_RANGE,
    Y_OFFSET_SET,
    BLOCK_NOT_IN_ENDPOINT_SELECTION,
    BLOCK_NOT_IN_CONTROL_POINT_SELECTION,
    BEZIER_CURVE_CLEARED,
    ENDPOINT_SELECTION_MODE_ENABLED,
    CONTROL_POINT_SELECTION_MODE_ENABLED,
    NO_SELECTION_MODE,
    CONTROL_POINT_SELECTION_MODE_DISABLED,
    ENDPOINT_SELECTION_MODE_DISABLED,
    ERROR_INVALID_POINTS,
    TOLERANCE_SETTING_DOES_NOT_EXIST,
    TRI_POINT_CIRCLE_MODE_ACCURATE,
    TRI_POINT_CIRCLE_MODE_DEFAULT,
    TRI_POINT_CIRCLE_MODE_SMOOTH,
    TRI_POINT_CIRCLE_MODE_UNKNOWN,
    TRI_POINT_CIRCLE_DONE,
    BRUSH_TOLERANCE_SET,
    TRIANGLE_BRUSH_INFO,
    HEATRAY_FREQ,
    HEATRAY_OCTAVE,
    HEATRAY_AMPLITUDE,
    PULLBRUSH_PINCH,
    PULLBRUSH_BUBBLE,
    RULER_BRUSH_POWDER,
    PUNISHMENT_SELECTED,
    VOXEL_BRUSH_COMMAND_USAGE,
    VOXEL_BRUSH_COMMAND_SETTINGS,
    BRUSH_HANDLE_NOT_FOUND,
    VOXEL_BRUSH_NO_PERMISSION,
    VOXEL_BRUSH_TOOL_COMMAND_USAGE,
    VOXEL_BRUSH_TOOL_COMMAND_HOLD_ITEM,
    VOXEL_BRUSH_TOOL_COMMAND_CANT_ASSIGN,
    VOXEL_BRUSH_TOOL_COMMAND_ASSIGN_FAIL,
    VOXEL_BRUSH_TOOL_COMMAND_ASSIGNED,
    VOXEL_BRUSH_TOOL_COMMAND_ASSIGN_OWN,
    VOXEL_BRUSH_TOOL_COMMAND_HOLD_UNASSIGN,
    VOXEL_BRUSH_TOOL_COMMAND_NOT_ALLOWED_UNASSIGN,
    VOXEL_BRUSH_TOOL_COMMAND_ASSIGNED_AS_TOOL,
    VOXEL_DEFAULT_COMMAND_USAGE,
    BRUSH_SETTINGS_RESET,
    VOXEL_INK_COMMAND_USAGE,
    VOXEL_INK_REPLACE_COMMAND_USAGE,
    VOXEL_PERFORMER_COMMAND_USAGE,
    VOXEL_REPLACE_COMMAND_USAGE,
    VOXELSNIPER_COMMAND_USAGE,
    VOXEL_UNDO_COMMAND_USAGE_START,
    VOXEL_UNDO_COMMAND_USAGE_UNDO,
    VOXEL_UNDO_COMMAND_USAGE_END,
    VOXEL_CENTER_USAGE,
    VOXEL_LIST_USAGE,
    VOXEL_HEIGHT_USAGE,
    VOXEL_LIST_CLEAR_USAGE,
    VOXEL_VARIABLE_USAGE,
    VOX_COMMAND_USAGE,
    VOX_PAINTING_USAGE,
    VOX_GOTO_USAGE,
    VOXEL_COMMAND_USAGE,
    VOXEL_INK_DIFFERENT_TYPE,
    VOXEL_INK_NO_BLOCK_TO_IMITATE_DATA,
    VOXEL_INK_CANT_IMITATE_DATA,
    VOXEL_INK_REPLACE_DIFFERENT_TYPE,
    VOXEL_INK_REPLACE_NO_BLOCK_TO_IMITATE_DATA,
    VOXEL_INK_REPLACE_CANT_IMITATE_DATA,
    NO_SUCH_PERFORMER,
    THE_ACTIVE_BRUSH_IS_NOT_A_PERFORMER_BRUSH,
    REPLACE_NOTHING_TO_IMITATE,
    INVALID_ITEM_ID,
    CURRENT_BRUSH_SETTINGS,
    UNDO_USER_PERMISSION,
    INVALID_UNDO_AMOUNT,
    CHANGES_UNDONE_BY_OTHER,
    PLAYER_NOT_FOUND,
    NOTHING_TO_SET_SUBSTANCE,
    INVALID_TYPE_ID,
    VOXEL_LIST_COULDNT_ADD,
    UNDID_PLAYER_CHANGES,
    SPLINE_BRUSH_NOT_ENOUGH_POINTS,
    ADDED_BLOCK_ENDPOINT,
    ADDED_BLOCK_CONTROL,
    REMOVED_BLOCK_CONTROL,
    REMOVED_BLOCK_ENDPOINT,
    MOVE_BRUSH_SELECTION,
    FILL_DOWN_MODE,
    FILL_DOWN_FROM,
    OVERLAY_MODE,
    OVERLAY_ON_MODE_DEPTH,
    ONLY_PLAYERS_CAN_EXECUTE_COMMANDS,
    NEGATIVE_VALUES_ARE_NOT_ALLOWED,
    CAN_T_PARSE_NUMBER,
    VOXEL_SNIPER_IS_NOW_ENABLED_FOR_YOU,
    VOXEL_SNIPER_IS_NOW_DISABLED_FOR_YOU,
    NO_BRUSH_SELECTED,
    CURRENT_TOOL,
    NO_PERMISSION_BRUSH,
    NO_PERMISSION_MESSAGE,
    VOXEL_HEIGHT_MUST_NOT_BE_0,
    GENERATED_CHUNK,
    FILE_ALREADY_EXISTS,
    BLOCK_ALREADY_ADDED_ENDPOINT,
    BLOCK_ALREADY_ADDED_CONTROL,
    ENDPOINT_SELECTION_FULL,
    CONTROL_SELECTION_FULL,
    PULLBRUSH_USAGE,
    NUMBER_OUT_OF_RANGE,
    ;
    //</editor-fold>


    private @NotNull String message = this.name().toLowerCase(Locale.ROOT);

    public static void load(IVoxelsniper voxelSniper) {
        File langFile = new File(voxelSniper.getFileHandler().getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            voxelSniper.getFileHandler().saveResource("lang.yml", false);
        }
        YamlConfiguration lang = new YamlConfiguration(langFile);
        YamlConfiguration fallBackLang = new YamlConfiguration(Messages.class.getClassLoader(), "lang.yml");
        boolean changedFile = false;

        for (Messages message : values()) {
            String langMessage = lang.getString(message.name());
            if (langMessage == null) {
                // try to read from internal file
                langMessage = fallBackLang.getString(message.name());
                if (langMessage == null) {
                    voxelSniper.getLogger().warning("Unable to load message " + message.name() + ". This is an error and should be reported.");
                } else {
                    // add default to 'public' file
                    lang.set(message.name(), langMessage);
                    changedFile = true;
                    message.message = langMessage;
                }
            } else {
                message.message = langMessage;
            }
        }

        if (changedFile) {
            try {
                lang.save(langFile);
            } catch (IOException ignored) {
            }
        }
    }

    @NotNull public final Message replace(String pattern, Object replacement) {
        return new Message(this.message).replace(pattern, replacement);
    }

    @NotNull public final Component replace(String pattern, ComponentLike replacement) {
        return new Message(this.message).replace(pattern, replacement);
    }

    @NotNull public final Message append(String message) {
        return new Message(this.message).append(message);
    }

    public ComponentLike append(ComponentLike message) {
        return this.asComponent().append(message);
    }

    @Override
    public @NotNull Component asComponent() {
        return MiniMessage.miniMessage().deserialize(this.message);
    }

    @Override
    public String toString() {
        return this.message;
    }

    public static final class Message implements ComponentLike {
        @NotNull private String message;

        private Message(@NotNull String message) {
            this.message = message;
        }

        @NotNull public Message replace(String target, Object replacement) {
            if (target != null && replacement != null)
                this.message = this.message.replace(target, String.valueOf(replacement));

            return this;
        }

        @NotNull public Component replace(String target, ComponentLike replacement) {
            if (target != null && replacement != null)
                return this.asComponent().replaceText(TextReplacementConfig.builder().match(Pattern.compile(target, Pattern.LITERAL)).replacement(replacement).build());

            return this.asComponent();
        }

        @NotNull public Message append(String message) {
            if (message != null) this.message += message;
            return this;
        }

        @Override
        public @NotNull Component asComponent() {
            return MiniMessage.miniMessage().deserialize(this.message);
        }
    }
}