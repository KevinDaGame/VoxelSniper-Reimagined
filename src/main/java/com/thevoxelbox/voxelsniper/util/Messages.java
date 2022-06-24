package com.thevoxelbox.voxelsniper.util;

import com.thevoxelbox.voxelsniper.VoxelSniper;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Locale;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;

public enum Messages implements ComponentLike {
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
    ;

    // TODO move this into YAML (probably with some kind of tool)
    private @NotNull String message = this.name().toLowerCase(Locale.ROOT);

    public static void load(VoxelSniper voxelSniper) {
        File langFile = new File(voxelSniper.getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            voxelSniper.saveResource("lang.yml", false);
        }
        FileConfiguration lang = YamlConfiguration.loadConfiguration(langFile);
        for (Messages message : values()) {
            String langMessage = lang.getString(message.name());
            if (langMessage == null) {
                voxelSniper.getLogger().warning("Unable to load message " + message.name() + ". Please make sure it exists in the lang file.");
            } else {
                message.message = langMessage;
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