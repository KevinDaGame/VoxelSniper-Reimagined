// temporary example class on how to listen for events

package me.lennart99.test;

import com.github.kevindagame.voxelsniper.events.EventPriority;
import com.github.kevindagame.voxelsniper.events.player.PlayerMaterialChangedEvent;
import com.github.kevindagame.voxelsniper.events.player.PlayerReplaceMaterialChangedEvent;
import com.github.kevindagame.voxelsniper.events.player.PlayerSnipeEvent;

public final class EventManager {
	public EventManager() {
//		VoxelSniper.registerListener(SniperMaterialChangedEvent.class, (SniperMaterialChangedEvent ev) -> {
//			ev.getSniper().getPlayer().sendMessage("Test SniperReplaceMaterialChangedEvent with reflection");
//		});
		PlayerSnipeEvent.getHandlerList().registerListener(EventPriority.HIGH, (ev) -> {
			ev.getPlayer().sendMessage("Test SniperSnipeEvent high prio");
		});
		PlayerSnipeEvent.getHandlerList().registerListener((ev) -> {
			ev.getPlayer().sendMessage("Test SniperSnipeEvent no prio");
		});
		PlayerSnipeEvent.getHandlerList().registerListener(EventPriority.MEDIUM, (ev) -> {
			ev.getPlayer().sendMessage("Test SniperSnipeEvent med prio"); // skipped
		});
		PlayerSnipeEvent.getHandlerList().registerListener((ev) -> {
			ev.getPlayer().sendMessage("Test SniperSnipeEvent no prio");
		});
		PlayerSnipeEvent.getHandlerList().registerListener(EventPriority.LOW, (ev) -> {
			ev.getPlayer().sendMessage("Test SniperSnipeEvent low prio");
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
