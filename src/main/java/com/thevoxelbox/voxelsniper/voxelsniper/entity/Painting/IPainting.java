package com.thevoxelbox.voxelsniper.voxelsniper.entity.Painting;

import com.thevoxelbox.voxelsniper.voxelsniper.entity.IEntity;

import org.bukkit.Art;

public interface IPainting extends IEntity {
    void setArt(Art art);
    Art getArt();
}
