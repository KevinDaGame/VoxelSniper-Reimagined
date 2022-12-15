package com.github.kevindagame.voxelsniper.bstats;

import com.github.kevindagame.voxelsniper.events.player.PlayerSnipeEvent;

public class BrushUsageCounter {
//    private static final Map<String, Integer> perBrushCounter = new HashMap<>();
    private static int counter = 0;

    public void registerListeners() {
        PlayerSnipeEvent.registerListener(this::onBrushUse);
    }

    private void onBrushUse(PlayerSnipeEvent event) {
        if(!event.isSuccessful()) return;
//        String brushName = event.getBrush().getName();
//        perBrushCounter.put(brushName, perBrushCounter.getOrDefault(brushName, 0) + 1);
        counter++;
    }

    public static int getTotalBrushUses() {
        var val = counter;
        counter = 0;
        return val;
    }

//    public static Map<String, Integer> getUsagePerBrush() {
//        var map = new HashMap<>(perBrushCounter);
//        perBrushCounter.clear();
//        return map;
//    }
}
