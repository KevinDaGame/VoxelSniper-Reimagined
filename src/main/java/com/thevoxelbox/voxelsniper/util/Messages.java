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
    TOGGLE_RANGE;

    // TODO move this into YAML (probably with some kind of tool)
    public static final String PERFORMER_MESSAGE = ChatColor.DARK_AQUA + "You can use " + ChatColor.YELLOW + "'/b " + "%triggerHandle%" + " p [performer]'" + ChatColor.DARK_AQUA + " to change performers now.";
    public static final String BLOCKS_UNTIL_REPEATER_MESSAGE = ChatColor.AQUA + "Blocks until repeater needed: %blocks%";
    public static final String BLOCK_POWER_MESSAGE = ChatColor.AQUA + "Direct Power? %direct% Indirect Power? %indirect%";
    public static final String FACE_POWER_MESSAGE = (ChatColor.BLUE + "%face% Direct? %direct% %face% Indirect? %indirect%");
    public static final String TREESNIPE_USAGE = (ChatColor.GOLD + "Tree Snipe Brush Parameters:\n" + ChatColor.AQUA + "/b %triggerHandle% [treeType]  -- Change tree type");
    public static final String TREESNIPE_BRUSH_MESSAGE = (ChatColor.RED + "That tree type does not exist. Use " + ChatColor.LIGHT_PURPLE + " /b " + "%triggerHandle%" + " info " + ChatColor.GOLD + " to see brush parameters.");
    public static final String VOLTMETER_BRUSH_MESSAGE = "Right click with arrow to see if blocks/faces are powered. Powder measures wire current.";
    public static final String DEFAULT_STAMP = "Default Stamp";
    public static final String NO_AIR_STAMP = "No-Air Stamp";
    public static final String FILL_STAMP = "Fill Stamp";
    public static final String CLONE_STAMP_ERROR = ChatColor.DARK_RED + "Error while stamping! Report";
    public static final String OFF_WORLD_END_POS = ChatColor.DARK_PURPLE + "Warning: off-world end position.";
    public static final String OFF_WORLD_START_POS = ChatColor.DARK_PURPLE + "Warning: off-world start position.";
    public static final String ENTITY_BRUSH_MESSAGE = (ChatColor.LIGHT_PURPLE + "Entity Brush (%entity%)");
    public static final String LIGHTNING_BRUSH_MESSAGE = "Lightning Brush!  Please use in moderation.";
    public static final String SELECTION_SIZE_ABOVE_LIMIT = ChatColor.RED + "Selection size above hardcoded limit, please use a smaller selection.";
    public static final String POINT_1_SET = "Point 1 set.";
    public static final String POINT_2_SET = "Point 2 set.";
    public static final String REGENERATE_CHUNK_MESSAGE = "Tread lightly, This brush will melt your spleen and sell your kidneys.";
    public static final String ROT3D_BRUSH_MESSAGE = "Rotates Yaw (XZ), then Pitch(XY), then Roll(ZY), in order.";
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