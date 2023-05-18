package com.github.kevindagame.command;

import com.github.kevindagame.util.Utils;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class with code to tab-complete the materials,
 * used by the various commands that take material types as argument
 */
public abstract class MaterialCommand extends VoxelCommand {
    public MaterialCommand(String name) {
        super(name);
    }

    @Override
    public final List<String> registerTabCompletion() {
        return VoxelMaterial.getMaterials().stream().filter(VoxelMaterial::isBlock).map(VoxelMaterial::getKey).toList();
    }

    @Override
    public List<String> doSuggestion(IPlayer player, String[] args) {
        if (args.length == 1) {
            // Preprocess the string for partial matching
            args[0] = args[0].toLowerCase();

            if (args[0].startsWith("minecraft:")) {
                return getTabCompletion(1).stream().map(e -> "minecraft:" + e).toList();
            } else if (args[0].startsWith("mi")) {
                return Utils.newArrayList("minecraft:");
            } else {
                return getTabCompletion(1);
            }
        }

        return new ArrayList<>();
    }
}
