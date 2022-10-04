package com.github.kevindagame.voxelsniperforge.entity.Painting;

import com.github.kevindagame.voxelsniper.entity.Painting.IPainting;
import com.github.kevindagame.voxelsniperforge.entity.ForgeEntity;

import net.minecraft.world.entity.decoration.Painting;


public class ForgePainting extends ForgeEntity implements IPainting {
    public ForgePainting(Painting entity) {
        super(entity);
    }

    @Override
    public boolean setArtId(int id) {
        Art art = Art.getById(id);
        if (art == null) {
            return false;
        }
        ((Painting) this.getEntity()).
        return true;
    }

    @Override
    public int getArtId() {
        return ((Painting) this.getEntity()).get
    }

    @Override
    public int nextPaintingId(boolean back) {
        return (this.getArtId() + (back ? -1 : 1) + Art.values().length) % Art.values().length;
    }
}
