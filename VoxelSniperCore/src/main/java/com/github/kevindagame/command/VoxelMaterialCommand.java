package com.github.kevindagame.command;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.util.BlockHelper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.voxelmaterial.BasicVoxelMaterial;
import com.github.kevindagame.voxelmaterial.GradientVoxelMaterial;
import com.github.kevindagame.voxelmaterial.GradientVoxelMaterialDirection;
import com.github.kevindagame.voxelmaterial.GradientVoxelMaterialMaterial;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.player.materialChange.PlayerMaterialChangedEvent;
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class VoxelMaterialCommand extends MaterialCommand {

    public VoxelMaterialCommand() {
        super("VoxelVoxel");
        setIdentifier("v");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean doCommand(IPlayer player, String[] args) {
        Sniper sniper = player.getSniper();
        SnipeData snipeData = sniper.getSnipeData(sniper.getCurrentToolId());

        // Command: /v          <- Sets the block user is looking at as voxel substance.
        if (args.length == 0) {
            IBlock selectedBlock = new BlockHelper(sniper.getPlayer()).getTargetBlock();
            if (selectedBlock != null) {
                //todo: update for new materials
                if (!new PlayerMaterialChangedEvent(sniper.getPlayer(), sniper.getSnipeData(sniper.getCurrentToolId()).getVoxelSubstance().getMaterial(), selectedBlock.getBlockData()).callEvent().isCancelled()) {
                    snipeData.setVoxelSubstance(new BasicVoxelMaterial(selectedBlock.getBlockData()));
                    snipeData.getVoxelMessage().voxel();
                }
            } else {
                sniper.sendMessage(Messages.NOTHING_TO_SET_SUBSTANCE);
            }
            return true;
        }

        switch (args[0].toLowerCase(Locale.ROOT)) {
            // Default command
            // Command: /v info, /v help
            case "help", "info" -> {
                sniper.sendMessage(Messages.VOXEL_COMMAND_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
                return true;
            }
            case "gradient" -> {
                if (args.length != 3) {
                    sniper.sendMessage(Messages.VOXEL_COMMAND_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
                    return true;
                }
                var direction = GradientVoxelMaterialDirection.valueOf(args[1].toUpperCase(Locale.ROOT));
                var materials = parseGradientMaterialList(args[2]);
                if (materials == null) {
                    sniper.sendMessage(Messages.INVALID_TYPE_ID);
                    return true;
                }
                snipeData.setVoxelSubstance(new GradientVoxelMaterial(materials, direction));
                return true;

            }
            case "random" -> {

            }
        }
        // Command: /v [material]       <- Sets the defined material as voxel substance.
        VoxelMaterialType material = VoxelMaterialType.getMaterial(args[0]);

        if (material != null && material.isBlock()) {
            if (!new PlayerMaterialChangedEvent(sniper.getPlayer(), sniper.getSnipeData(sniper.getCurrentToolId()).getVoxelSubstance().getMaterial(), material.createBlockData()).callEvent().isCancelled()) {
                snipeData.setVoxelSubstance(new BasicVoxelMaterial(material.createBlockData()));
                snipeData.getVoxelMessage().voxel();
            }
        } else {
            sniper.sendMessage(Messages.INVALID_TYPE_ID);
        }
        return true;
    }

    @Override
    public List<String> doSuggestion(IPlayer player, String[] args) {
        System.out.println(Arrays.toString(args));
        var suggestions = new ArrayList<String>();
        if (args.length == 1) {
            suggestions.addAll(super.doSuggestion(player, args));
            suggestions.add("gradient");
            suggestions.add("random");
        }
        if (args.length == 2) {
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "g", "gradient" ->
                        suggestions.addAll(Arrays.stream(GradientVoxelMaterialDirection.values()).map(Enum::name).map(String::toLowerCase).toList());
                case "r", "random" -> suggestions.add("random");
            }
        }

        if (args.length == 3) {
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "g", "gradient" -> {
                    var prefix = getExceptLastElement(args[2]);
                    var parts = args[2].split(",");
                    var lastPart = parts[parts.length - 1];

                    if (args[2].endsWith(",")) {
                        suggestions.addAll(VoxelMaterialType.getMaterials().stream().filter(VoxelMaterialType::isBlock).map(VoxelMaterialType::getName).map(String::toLowerCase).map(s -> args[2] + s).toList());
                        break;
                    }

                    if (!lastPart.contains(":")) {
                        var material = VoxelMaterialType.getMaterial(lastPart);
                        if (material == null) {
                            suggestions.addAll(VoxelMaterialType.getMaterials().stream().filter(VoxelMaterialType::isBlock).map(VoxelMaterialType::getName).map(String::toLowerCase).map(s -> prefix + s).toList());
                        } else {
                            suggestions.add(args[2] + ",");
                        }
                    } else {
                        suggestions.add(args[2] + ",");
                    }
                }
            }
        }
        return suggestions;
    }

    private String getExceptLastElement(String input) {
        String[] parts = input.split(",");
        if (parts.length > 1 || input.endsWith(",")) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < parts.length - 1; i++) {
                sb.append(parts[i]);
                sb.append(",");
            }
            return sb.toString();
        } else {
            return "";
        }
    }


    private List<GradientVoxelMaterialMaterial> parseGradientMaterialList(String list) {
        var materials = new ArrayList<GradientVoxelMaterialMaterial>();
        for (String material : list.split(",")) {
            var mat = parseGradientMaterial(material);
            if (mat == null) {
                return null;
            }
            materials.add(mat);
        }
        return materials;
    }

    private GradientVoxelMaterialMaterial parseGradientMaterial(String material) {
        var parts = material.split(":");
        VoxelMaterialType mat = VoxelMaterialType.getMaterial(parts[0]);
        if (mat == null) {
            return null;
        }
        int weight = parts.length == 2 ? Integer.parseInt(parts[1]) : 1;
        return new GradientVoxelMaterialMaterial(mat.createBlockData(), weight);
    }

    @Override
    public List<String> registerTabCompletion() {
        var tc = new ArrayList<String>();
        tc.add("gradient");
        tc.add("random");
        tc.addAll(super.registerTabCompletion());
        return tc;
    }
}
