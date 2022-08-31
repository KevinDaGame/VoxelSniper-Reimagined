package com.thevoxelbox.voxelsniper.voxelsniper.entity.Painting;

import com.thevoxelbox.voxelsniper.voxelsniper.entity.BukkitEntity;

import org.bukkit.Art;
import org.bukkit.entity.Painting;

public class BukkitPainting extends BukkitEntity implements IPainting {
    public BukkitPainting(Painting entity) {
        super(entity);
    }

    @Override
    public void setArt(Art art) {
        ((Painting)this.getEntity()).setArt(art);
    }

    @Override
    public Art getArt() {
        return ((Painting)this.getEntity()).getArt();
    }
}
