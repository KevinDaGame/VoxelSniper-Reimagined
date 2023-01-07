package com.github.kevindagame.voxelsniper.events.player.materialChange;

import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.EventPriority;
import com.github.kevindagame.voxelsniper.events.HandlerList;

import java.util.function.Consumer;

public class PlayerMaterialChangedEvent extends AbstractMaterialChangedEvent<PlayerMaterialChangedEvent> {

    private static final HandlerList<PlayerMaterialChangedEvent> handlers = new HandlerList<>();

    public PlayerMaterialChangedEvent(IPlayer p, IBlockData oldMaterial, IBlockData newMaterial) {
        super(p, oldMaterial, newMaterial);
    }

    @Override
    protected HandlerList<PlayerMaterialChangedEvent> getHandlers() {
        return handlers;
    }

    public static void registerListener(Consumer<PlayerMaterialChangedEvent> handler) {
        handlers.registerListener(handler);
    }

    public static void registerListener(EventPriority priority, Consumer<PlayerMaterialChangedEvent> handler) {
        handlers.registerListener(priority, handler);
    }
}
