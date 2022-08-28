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

    @Override
    public String getKey() {
        return type.getKey().getKey();
    }

    @Override
    public String getNameSpace() {
        return type.getKey().getNamespace();
    }

    public EntityType getType() {
        return type;
    }
}
