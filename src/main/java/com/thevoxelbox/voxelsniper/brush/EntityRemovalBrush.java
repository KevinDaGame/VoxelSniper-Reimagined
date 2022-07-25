package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 *
 */
public class EntityRemovalBrush extends Brush {

    private final List<EntityType> exclusionList = new ArrayList<>();

    /**
     *
     */
    public EntityRemovalBrush() {
        this.setName("Entity Removal Brush");
        defaultValues();
    }

    private void defaultValues() {
        exclusionList.clear();

        exclusionList.add(EntityType.ARMOR_STAND);
        exclusionList.add(EntityType.BOAT);
        exclusionList.add(EntityType.DROPPED_ITEM);
        exclusionList.add(EntityType.ITEM_FRAME);
        exclusionList.add(EntityType.LEASH_HITCH);
        exclusionList.add(EntityType.MINECART);
        exclusionList.add(EntityType.MINECART_CHEST);
        exclusionList.add(EntityType.MINECART_COMMAND);
        exclusionList.add(EntityType.MINECART_FURNACE);
        exclusionList.add(EntityType.MINECART_HOPPER);
        exclusionList.add(EntityType.MINECART_MOB_SPAWNER);
        exclusionList.add(EntityType.MINECART_TNT);
        exclusionList.add(EntityType.PAINTING);
        exclusionList.add(EntityType.PLAYER);
        exclusionList.add(EntityType.VILLAGER);
        exclusionList.add(EntityType.WANDERING_TRADER);

    }

    private void radialRemoval(SnipeData v) {
        final Chunk targetChunk = getTargetBlock().getChunk();
        int entityCount = 0;
        int chunkCount = 0;

        try {
            entityCount += removeEntities(targetChunk);

            int radius = Math.round(v.getBrushSize() / 16);

            for (int x = targetChunk.getX() - radius; x <= targetChunk.getX() + radius; x++) {
                for (int z = targetChunk.getZ() - radius; z <= targetChunk.getZ() + radius; z++) {
                    entityCount += removeEntities(getWorld().getChunkAt(x, z));

                    chunkCount++;
                }
            }
        } catch (final PatternSyntaxException pse) {
            pse.printStackTrace();
            v.sendMessage(Messages.ENTITY_REMOVAL_REGEX_ERROR.replace("%pattern%",String.valueOf(pse.getPattern())).replace("%desc%", pse.getDescription()).replace("%idx%", String.valueOf(pse.getIndex())));
        }
        v.sendMessage(Messages.ENTITY_REMOVE_COUNT.replace("%chunkCount%", String.valueOf(chunkCount)).replace("%entityCount%", String.valueOf(entityCount)).replace("%multiple%", (chunkCount == 1 ? " chunk." : " chunks.")));
    }

    private int removeEntities(Chunk chunk) throws PatternSyntaxException {
        int entityCount = 0;

        for (Entity entity : chunk.getEntities()) {
            if (exclusionList.contains(entity.getType())) {
                continue;
            }

            entity.remove();
            entityCount++;
        }

        return entityCount;
    }

    @Override
    protected void arrow(SnipeData v) {
        this.radialRemoval(v);
    }

    @Override
    protected void powder(SnipeData v) {
        this.radialRemoval(v);
    }

    @Override
    public void info(VoxelMessage vm) {
        vm.brushName(getName());
        String exclusionList = this.exclusionList.stream().map(e -> e.name()).collect(Collectors.joining(Messages.ENTITY_REMOVAL_EXCLUSION_LIST.toString()));
        vm.custom(Messages.ENTITY_REMOVAL_EXCLUSIONS.replace("%exclusionList%", exclusionList));
        vm.size();
    }

    @Override
    public void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.ENTITY_REMOVAL_BRUSH_USAGE.replace("%triggerHandle%",triggerHandle));
            return;
        }

        if (params[0].equalsIgnoreCase("reset")) {
            defaultValues();
            v.sendMessage(Messages.ENTITY_REMOVAL_RESET);
            return;
        }

        if (params[0].equalsIgnoreCase("clear")) {
            exclusionList.clear();
            v.sendMessage(Messages.ENTITY_REMOVAL_CLEAR);
            return;
        }

        if (params[0].equalsIgnoreCase("list")) {
            String exclusionList = this.exclusionList.stream().map(e -> e.name()).collect(Collectors.joining(Messages.ENTITY_REMOVAL_EXCLUSION_LIST.toString()));
            v.sendMessage(Messages.ENTITY_REMOVAL_EXCLUSIONS.replace("%exclusionList%", exclusionList));
            return;
        }

        if (params[0].equalsIgnoreCase("+") || params[0].equalsIgnoreCase("-")) {
            try {
                EntityType entity = EntityType.valueOf(params[1]);

                if (params[0].equals("+")) {
                    exclusionList.add(entity);
                    v.sendMessage(Messages.ENTITY_REMOVAL_ADD.replace("%name%", String.valueOf(entity.getName())));
                } else {
                    if (exclusionList.contains(entity)) {
                        exclusionList.remove(entity);
                        v.sendMessage(Messages.ENTITY_REMOVAL_REMOVE.replace("%name%", String.valueOf(entity.getName())));
                    } else {
                        v.sendMessage(Messages.ENTITY_REMOVAL_NOT_IN_LIST.replace("%name%", String.valueOf(entity.getName())));
                    }
                }

                return;
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ignored) {
            }
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("+", "-", "reset", "clear", "list"));
    }

    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();

        List<String> entities = new ArrayList<>();

        for (EntityType entity : EntityType.values()) {
            entities.add(entity.name());
        }


        argumentValues.put("+", entities);
        argumentValues.put("-", entities);
        return argumentValues;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.entityremoval";
    }
}
