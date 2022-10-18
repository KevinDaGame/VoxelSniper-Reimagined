package com.github.kevindagame.voxelsniper.bstats;

import com.github.kevindagame.voxelsniper.events.bukkit.SniperSnipeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class BrushUsageCounter implements Listener {
//    private static final Map<String, Integer> perBrushCounter = new HashMap<>();
    private static int counter = 0;

    @EventHandler
    private void onBrushUse(SniperSnipeEvent event) {
        if(!event.getSuccess()) return;
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
