package com.github.kevindagame.voxelsniper.entity.painting;

import com.github.kevindagame.voxelsniper.entity.IEntity;

public interface IPainting extends IEntity {
    boolean setArtId(int art);

    int getArtId();

    int nextPaintingId(boolean back);
}
