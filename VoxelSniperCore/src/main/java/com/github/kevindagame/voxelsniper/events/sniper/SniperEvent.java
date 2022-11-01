package com.github.kevindagame.voxelsniper.events.sniper;

import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.voxelsniper.events.Event;

public abstract class SniperEvent extends Event {
	private final Sniper sniper;
	
	public SniperEvent(Sniper sniper) {
		this.sniper = sniper;
	}
	
	public final Sniper getSniper() {
		return sniper;
	}
}
