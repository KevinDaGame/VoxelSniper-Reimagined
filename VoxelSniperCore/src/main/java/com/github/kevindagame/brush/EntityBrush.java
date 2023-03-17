package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.EntitySpawnOperation;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#entity-brush">...</a>
 *
 * @author Piotr
 */
public class EntityBrush extends AbstractBrush {

    private VoxelEntityType entityType = VoxelEntityType.ZOMBIE;


    private void spawn(final SnipeData v) {
        for (int x = 0; x < v.getBrushSize(); x++) {
            try {
                addOperation(new EntitySpawnOperation(this.getLastBlock().getLocation(), entityType));
            } catch (final IllegalArgumentException exception) {
                v.sendMessage(Messages.ENTITYBRUSH_SPAWN_FAIL);
            }
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        spawn(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        spawn(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushMessage(Messages.ENTITY_BRUSH_MESSAGE.replace("%entity%", this.entityType.getKey()));
        vm.size();
    }

    @Override
    public final void parseParameters(@NotNull final String triggerHandle, final String[] params, @NotNull final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.ENTITYBRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        try {
            if (params[0].contains(":")) {
                String[] split = params[0].split(":");
                this.entityType = VoxelEntityType.getEntityType(split[0], split[1]);
            } else {
                this.entityType = VoxelEntityType.getEntityType(params[0]);
            }
            if (entityType == null)
                v.sendMessage(Messages.ENTITYBRUSH_UNKNOWN_ENTITY);
            else
                v.sendMessage(Messages.ENTITYBRUSH_ENTITY_TYPE.replace("%type%", this.entityType.getKey()));
        } catch (IllegalArgumentException e) {
            v.sendMessage(Messages.ENTITYBRUSH_UNKNOWN_ENTITY);
        }
    }

    @NotNull
    @Override
    public List<String> registerArguments() {
        List<String> entities = new ArrayList<>();

        for (VoxelEntityType entity : VoxelEntityType.ENTITYTYPES.values()) {
            if (entity != null)
                entities.add(entity.getKey());
        }

        return entities;
    }
}
