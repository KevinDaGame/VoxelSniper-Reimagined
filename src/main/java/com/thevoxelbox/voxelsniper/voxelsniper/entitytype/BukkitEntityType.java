package com.thevoxelbox.voxelsniper.voxelsniper.entitytype;

import org.bukkit.entity.EntityType;

public class BukkitEntityType implements IEntityType {
    private final EntityType type;

    public BukkitEntityType(EntityType type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return type.getName();
    }
    public EntityType getType() {
        return type;
    }
}
