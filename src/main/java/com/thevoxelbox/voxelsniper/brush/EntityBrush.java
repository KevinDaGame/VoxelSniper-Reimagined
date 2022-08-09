package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.EntityType;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_Entity_Brush
 *
 * @author Piotr
 */
public class EntityBrush extends Brush {

    private EntityType entityType = EntityType.ZOMBIE;

    /**
     *
     */
    public EntityBrush() {
        this.setName("Entity");
    }

    private void spawn(final SnipeData v) {
        for (int x = 0; x < v.getBrushSize(); x++) {
            try {
                this.getWorld().spawn(this.getLastBlock().getLocation(), this.entityType.getEntityClass());
            } catch (final IllegalArgumentException exception) {
                v.sendMessage(Messages.ENTITYBRUSH_SPAWN_FAIL);
            }
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.spawn(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.spawn(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushMessage(Messages.ENTITY_BRUSH_MESSAGE.replace("%entity%", this.entityType.getName()));
        vm.size();
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.ENTITYBRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        try {
            this.entityType = EntityType.valueOf(params[0]);
            v.sendMessage(Messages.ENTITYBRUSH_ENTITY_TYPE.replace("%type%", this.entityType.name()));
        } catch (IllegalArgumentException e) {
            v.sendMessage(Messages.ENTITYBRUSH_UNKNOWN_ENTITY);
        }
    }

    @Override
    public List<String> registerArguments() {
        List<String> entities = new ArrayList<>();

        for (EntityType entity : EntityType.values()) {
            if (entity != EntityType.UNKNOWN)
                entities.add(entity.name());
        }

        return entities;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.entity";
    }
}
