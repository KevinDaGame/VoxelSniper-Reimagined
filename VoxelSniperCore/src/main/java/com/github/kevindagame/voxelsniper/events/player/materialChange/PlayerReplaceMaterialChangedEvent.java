package com.github.kevindagame.voxelsniper.events.player.materialChange;

import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.EventPriority;
import com.github.kevindagame.voxelsniper.events.HandlerList;

import java.util.function.Consumer;

public class PlayerReplaceMaterialChangedEvent extends AbstractMaterialChangedEvent<PlayerReplaceMaterialChangedEvent> {

    private static final HandlerList<PlayerReplaceMaterialChangedEvent> handlers = new HandlerList<>();

    public PlayerReplaceMaterialChangedEvent(IPlayer p, IBlockData oldMaterial, IBlockData newMaterial) {
        super(p, oldMaterial, newMaterial);
    }

    @Override
    protected HandlerList<PlayerReplaceMaterialChangedEvent> getHandlers() {
        return handlers;
    }

    public static void registerListener(Consumer<PlayerReplaceMaterialChangedEvent> handler) {
        handlers.registerListener(handler);
    }

    public static void registerListener(EventPriority priority, Consumer<PlayerReplaceMaterialChangedEvent> handler) {
        handlers.registerListener(priority, handler);
    }

}
