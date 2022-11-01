// temporary example class on how to listen for events

package me.lennart99.test;

import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.voxelsniper.events.sniper.SniperMaterialChangedEvent;
import com.github.kevindagame.voxelsniper.events.sniper.SniperReplaceMaterialChangedEvent;
import com.github.kevindagame.voxelsniper.events.sniper.SniperSnipeEvent;

public final class EventManager {
	public EventManager() {
//		VoxelSniper.registerListener(SniperMaterialChangedEvent.class, (SniperMaterialChangedEvent ev) -> {
//			ev.getSniper().getPlayer().sendMessage("Test SniperReplaceMaterialChangedEvent with reflection");
//		});

		SniperSnipeEvent.getHandlerList().registerListener((ev) -> {
			ev.getSniper().getPlayer().sendMessage("Test SniperSnipeEvent without reflection");
		});

		SniperMaterialChangedEvent.getHandlerList().registerListener((ev) -> {
			ev.getSniper().getPlayer().sendMessage("Test SniperMaterialChangedEvent without reflection");
		});

		SniperReplaceMaterialChangedEvent.getHandlerList().registerListener(this::handleEvent);
	}
	
	private void handleEvent(SniperReplaceMaterialChangedEvent ev) {
		ev.getSniper().getPlayer().sendMessage("Test SniperReplaceMaterialChangedEvent in function");
	}
	
}
