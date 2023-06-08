package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Utils;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.EntityRemoveOperation;
import com.github.kevindagame.voxelsniper.chunk.IChunk;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#entity-removal-brush">...</a>
 */
public class EntityRemovalBrush extends AbstractBrush {

    private final List<VoxelEntityType> exclusionList = new ArrayList<>();

    /**
     *
     */
    public EntityRemovalBrush() {
        defaultValues();
    }

    private void defaultValues() {
        exclusionList.clear();

        exclusionList.add(VoxelEntityType.getEntityType("minecraft", "armor_stand"));
        exclusionList.add(VoxelEntityType.getEntityType("minecraft", "boat"));
        exclusionList.add(VoxelEntityType.getEntityType("minecraft", "dropped_item"));
        exclusionList.add(VoxelEntityType.getEntityType("minecraft", "item_frame"));
        exclusionList.add(VoxelEntityType.getEntityType("minecraft", "leash_hitch"));
        exclusionList.add(VoxelEntityType.getEntityType("minecraft", "minecart"));
        exclusionList.add(VoxelEntityType.getEntityType("minecraft", "minecart_chest"));
        exclusionList.add(VoxelEntityType.getEntityType("minecraft", "minecart_command"));
        exclusionList.add(VoxelEntityType.getEntityType("minecraft", "minecart_furnace"));
        exclusionList.add(VoxelEntityType.getEntityType("minecraft", "minecart_hopper"));
        exclusionList.add(VoxelEntityType.getEntityType("minecraft", "minecart_mob_spawner"));
        exclusionList.add(VoxelEntityType.getEntityType("minecraft", "minecart_tnt"));
        exclusionList.add(VoxelEntityType.getEntityType("minecraft", "painting"));
        exclusionList.add(VoxelEntityType.getEntityType("minecraft", "player"));
        exclusionList.add(VoxelEntityType.getEntityType("minecraft", "villager"));
        exclusionList.add(VoxelEntityType.getEntityType("minecraft", "wandering_trader"));

    }

    private void radialRemoval(SnipeData v) {
        final IChunk targetChunk = getTargetBlock().getChunk();
        int entityCount = 0;
        int chunkCount = 0;

        try {
            entityCount += removeEntities(targetChunk);

            int radius = v.getBrushSize() / 16;

            for (int x = targetChunk.getX() - radius; x <= targetChunk.getX() + radius; x++) {
                for (int z = targetChunk.getZ() - radius; z <= targetChunk.getZ() + radius; z++) {
                    entityCount += removeEntities(getWorld().getChunkAtLocation(x, z));

                    chunkCount++;
                }
            }
        } catch (final PatternSyntaxException pse) {
            pse.printStackTrace();
            v.sendMessage(Messages.ENTITY_REMOVAL_REGEX_ERROR.replace("%pattern%", String.valueOf(pse.getPattern())).replace("%desc%", pse.getDescription()).replace("%idx%", String.valueOf(pse.getIndex())));
        }
        v.sendMessage(Messages.ENTITY_REMOVE_COUNT.replace("%chunkCount%", String.valueOf(chunkCount)).replace("%entityCount%", String.valueOf(entityCount)).replace("%multiple%", (chunkCount == 1 ? " chunk." : " chunks.")));
    }

    private int removeEntities(IChunk chunk) throws PatternSyntaxException {
        int entityCount = 0;

        for (IEntity entity : chunk.getEntities()) {
            if (exclusionList.contains(entity.getType())) {
                continue;
            }

            addOperation(new EntityRemoveOperation(entity.getLocation(), entity));
            entityCount++;
        }

        return entityCount;
    }

    @Override
    protected void arrow(SnipeData v) {
        radialRemoval(v);
    }

    @Override
    protected void powder(SnipeData v) {
        radialRemoval(v);
    }

    @Override
    public void info(VoxelMessage vm) {
        vm.brushName(getName());
        String exclusionList = this.exclusionList.stream().map(VoxelEntityType::key).collect(Collectors.joining(Messages.ENTITY_REMOVAL_EXCLUSION_LIST.toString()));
        vm.custom(Messages.ENTITY_REMOVAL_EXCLUSIONS.replace("%exclusionList%", exclusionList));
        vm.size();
    }

    @Override
    public void parseParameters(@NotNull final String triggerHandle, final String[] params, @NotNull final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.ENTITY_REMOVAL_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        if (params[0].equalsIgnoreCase("reset")) {
            defaultValues();
            v.sendMessage(Messages.ENTITY_REMOVAL_RESET);
            return;
        }

        if (params[0].equalsIgnoreCase("clear")) {
            exclusionList.clear();
            exclusionList.add(VoxelEntityType.getEntityType("minecraft", "player"));
            v.sendMessage(Messages.ENTITY_REMOVAL_CLEAR);
            return;
        }

        if (params[0].equalsIgnoreCase("list")) {
            String exclusionList = this.exclusionList.stream().map(VoxelEntityType::key).collect(Collectors.joining(Messages.ENTITY_REMOVAL_EXCLUSION_LIST.toString()));
            v.sendMessage(Messages.ENTITY_REMOVAL_EXCLUSIONS.replace("%exclusionList%", exclusionList));
            return;
        }

        if (params[0].equalsIgnoreCase("+") || params[0].equalsIgnoreCase("-")) {
            try {
                VoxelEntityType entity = VoxelEntityType.getEntityType(params[1]);

                if (params[0].equals("+")) {
                    exclusionList.add(entity);
                    v.sendMessage(Messages.ENTITY_REMOVAL_ADD.replace("%name%", entity.key()));
                } else {
                    if (exclusionList.contains(entity)) {
                        exclusionList.remove(entity);
                        v.sendMessage(Messages.ENTITY_REMOVAL_REMOVE.replace("%name%", String.valueOf(entity.key())));
                    } else {
                        v.sendMessage(Messages.ENTITY_REMOVAL_NOT_IN_LIST.replace("%name%", String.valueOf(entity.key())));
                    }
                }

                return;
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException temp) {
                temp.printStackTrace();
            }
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @NotNull
    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Utils.newArrayList("+", "-", "reset", "clear", "list"));
    }

    @NotNull
    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();

        List<String> entities = new ArrayList<>();

        for (VoxelEntityType entity : VoxelEntityType.getEntityTypes()) {
            entities.add(entity.toString());
        }


        argumentValues.put("+", entities);
        argumentValues.put("-", entities);
        return argumentValues;
    }
}
