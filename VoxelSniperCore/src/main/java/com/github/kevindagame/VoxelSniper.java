package com.github.kevindagame;

import com.github.kevindagame.voxelsniper.IVoxelsniper;
import com.github.kevindagame.voxelsniper.events.Event;
import com.github.kevindagame.voxelsniper.events.HandlerList;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class VoxelSniper {
    public static IVoxelsniper voxelsniper;

    public static <T extends Event> void registerListener(Class<T> eventType, Consumer<T> handler) {
        try {
            Method method = eventType.getMethod("getHandlerList");
            @SuppressWarnings("unchecked")
            HandlerList<T> res = (HandlerList<T>) method.invoke(null);
            res.registerListener(handler);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
