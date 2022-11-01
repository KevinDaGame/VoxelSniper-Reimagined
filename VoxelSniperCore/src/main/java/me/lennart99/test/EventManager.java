// temporary example class on how to listen for events

package me.lennart99.test;

import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.voxelsniper.events.sniper.SniperMaterialChangedEvent;
import com.github.kevindagame.voxelsniper.events.sniper.SniperReplaceMaterialChangedEvent;
import com.github.kevindagame.voxelsniper.events.sniper.SniperSnipeEvent;

public final class EventManager {
	public EventManager() {
		VoxelSniper.registerListener(SniperReplaceMaterialChangedEvent.class, (SniperReplaceMaterialChangedEvent ev) -> {
			ev.getSniper().getPlayer().sendMessage("Test SniperReplaceMaterialChangedEvent with reflection");
		});

		SniperSnipeEvent.getHandlerList().registerListener((ev) -> {
			ev.getSniper().getPlayer().sendMessage("Test SniperSnipeEvent without reflection");
		});

		SniperMaterialChangedEvent.getHandlerList().registerListener(this::handleEvent);
	}
	
	private void handleEvent(SniperMaterialChangedEvent ev) {
		ev.getSniper().getPlayer().sendMessage("Test SniperMaterialChangedEvent in function");
	}
	
}
