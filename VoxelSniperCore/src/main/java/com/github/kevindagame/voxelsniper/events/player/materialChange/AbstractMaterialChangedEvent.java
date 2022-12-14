package com.github.kevindagame.voxelsniper.events.player.materialChange;

import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.player.PlayerEvent;

abstract class AbstractMaterialChangedEvent<T extends AbstractMaterialChangedEvent<?>> extends PlayerEvent<T> {

    private final IBlockData oldMaterial;
    private final IBlockData newMaterial;

    public AbstractMaterialChangedEvent(IPlayer p, IBlockData oldMaterial, IBlockData newMaterial) {
        super(p);
        this.oldMaterial = oldMaterial;
        this.newMaterial = newMaterial;
    }

    public IBlockData getOldMaterial() {
        return oldMaterial;
    }

    public IBlockData getNewMaterial() {
        return newMaterial;
    }

}
