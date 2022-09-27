package com.thevoxelbox.voxelsniper.voxelsniper.entity.Painting;

import com.thevoxelbox.voxelsniper.voxelsniper.entity.IEntity;

public interface IPainting extends IEntity {
    boolean setArtId(int art);

    int getArtId();

    int nextPaintingId(boolean back);
}
