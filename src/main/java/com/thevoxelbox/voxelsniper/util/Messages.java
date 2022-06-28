package com.thevoxelbox.voxelsniper.util;

import com.thevoxelbox.voxelsniper.VoxelSniper;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;

public enum Messages implements ComponentLike, Messages0 {
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
    CLONE_STAMP_ERROR,
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
    FINAL_PAINTING,
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
    BLOB_BRUSH_RANGE,
    BLOB_BRUSH_SET_VALUE,
    JAGGED_LINE_BRUSH_USAGE,
    JAGGED_LINE_BRUSH_RECURSION_RANGE,
    JAGGED_LINE_BRUSH_RECURSION_SET,
    JAGGED_LINE_BRUSH_SET_SPREAD,
    FIRST_COORDINATE_NOT_SET,
    FIRST_POINT_SELECTED,
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
    ;

    private @NotNull String message = this.name().toLowerCase(Locale.ROOT);

    public static void load(VoxelSniper voxelSniper) {
        File langFile = new File(voxelSniper.getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            voxelSniper.saveResource("lang.yml", false);
        }
        YamlConfiguration lang = new YamlConfiguration(langFile);
        YamlConfiguration fallBackLang = new YamlConfiguration(Messages.class.getClassLoader(), "lang.yml", voxelSniper);
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