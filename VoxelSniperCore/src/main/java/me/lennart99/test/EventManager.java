// temporary example class on how to listen for events

package me.lennart99.test;

import com.github.kevindagame.voxelsniper.events.player.PlayerMaterialChangedEvent;
import com.github.kevindagame.voxelsniper.events.player.PlayerReplaceMaterialChangedEvent;
import com.github.kevindagame.voxelsniper.events.player.PlayerSnipeEvent;

public final class EventManager {
	public EventManager() {
//		VoxelSniper.registerListener(SniperMaterialChangedEvent.class, (SniperMaterialChangedEvent ev) -> {
//			ev.getSniper().getPlayer().sendMessage("Test SniperReplaceMaterialChangedEvent with reflection");
//		});

		PlayerSnipeEvent.getHandlerList().registerListener((ev) -> {
			ev.getPlayer().sendMessage("Test SniperSnipeEvent without reflection");
		});

		PlayerMaterialChangedEvent.getHandlerList().registerListener((ev) -> {
			ev.getPlayer().sendMessage("Test SniperMaterialChangedEvent without reflection");
		});

		PlayerReplaceMaterialChangedEvent.getHandlerList().registerListener(this::handleEvent);
	}
	
	private void handleEvent(PlayerReplaceMaterialChangedEvent ev) {
		ev.getPlayer().sendMessage("Test SniperReplaceMaterialChangedEvent in function");
	}
	
}
