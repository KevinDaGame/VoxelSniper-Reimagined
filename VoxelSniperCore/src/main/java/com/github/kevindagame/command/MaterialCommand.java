package com.github.kevindagame.command;

import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        // Returns all available materials. It will return the list of materials with the format "modid:material". for materials starting with "minecraft:", it will also append the material name without the "minecraft:" prefix. This way you can type "minecraft:stone" or "stone" and it will both work.
        var materials = VoxelSniper.voxelsniper.getMaterials().stream().filter(VoxelMaterial::isBlock).map(VoxelMaterial::toString).collect(Collectors.toList());
        materials.addAll(materials.stream().filter(material -> material.startsWith("minecraft:")).map(material -> material.replace("minecraft:", "")).toList());
        return materials;
    }

    @Override
    public List<String> doSuggestion(IPlayer player, String[] args) {
        if (args.length == 1) {
            args[0] = args[0].toLowerCase();
            return getTabCompletion(1);
        }

        return new ArrayList<>();
    }
}
