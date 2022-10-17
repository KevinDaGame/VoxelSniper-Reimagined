package com.github.kevindagame.voxelsniper.entity.Painting;

import com.github.kevindagame.voxelsniper.entity.SpigotEntity;
import org.bukkit.Art;
import org.bukkit.entity.Painting;

public class SpigotPainting extends SpigotEntity implements IPainting {
    public SpigotPainting(Painting entity) {
        super(entity);
    }

    @Override
    public boolean setArtId(int id) {
        Art art = Art.getById(id);
        if (art == null) {
            return false;
        }
        ((Painting) this.getEntity()).setArt(art);
        return true;
    }

    @Override
    public int getArtId() {
        return ((Painting) this.getEntity()).getArt().getId();
    }

    @Override
    public int nextPaintingId(boolean back) {
        return (this.getArtId() + (back ? -1 : 1) + Art.values().length) % Art.values().length;
    }
}
