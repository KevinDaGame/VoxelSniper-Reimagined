package com.thevoxelbox.voxelsniper.util;

import com.thevoxelbox.voxelsniper.VoxelSniper;

import javax.annotation.Nonnull;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Locale;

public enum Messages {
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
    TOGGLE_RANGE;

    private Message message;

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
                langMessage = message.name().toLowerCase(Locale.ROOT);
            }

            message.message = new Message(langMessage);
        }
    }

    public final Message replace(String pattern, Object replacement) {
        return this.message.replace(pattern, replacement);
    }

    public final Message append(String message) {
        return this.message.append(message);
    }

    public final Message getMessage() {
        return this.message;
    }

    public static final class Message {
        private final String message;

        private Message(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public Message replace(String pattern, Object replacement) {
            if (pattern == null || replacement == null) return this;

            return new Message(this.message.replace(pattern, String.valueOf(replacement)));
        }

        public Message append(String message) {
            if (message == null) return this;

            return new Message(this.message + message);
        }
    }
}