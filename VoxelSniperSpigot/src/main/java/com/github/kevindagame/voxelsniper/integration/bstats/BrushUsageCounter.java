package com.github.kevindagame.voxelsniper.integration.bstats;

import com.github.kevindagame.voxelsniper.events.player.PlayerSnipeEvent;

public class BrushUsageCounter {
    private static int counter = 0;

    public void registerListeners() {
        PlayerSnipeEvent.registerListener(this::onBrushUse);
    }

    private void onBrushUse(PlayerSnipeEvent event) {
        if(event.isCancelled()) return;
        counter++;
    }

    public static int getTotalBrushUses() {
        var val = counter;
        counter = 0;
        return val;
    }
}
