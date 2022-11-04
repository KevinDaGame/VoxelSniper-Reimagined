package com.github.kevindagame.util;

import com.github.kevindagame.voxelsniper.location.VoxelLocation;

import java.util.Set;
import java.util.stream.Collectors;

public class Actions {

    /**
     * Returns the locations that are on a checker grid, according to their sum
     * @param locations
     * @param even
     * @return
     */
    public static Set<VoxelLocation> checker(Set<VoxelLocation> locations, boolean even) {
        return locations.stream().filter(location -> (location.getBlockX() + location.getBlockY() + location.getBlockZ()) % 2 != (even ? 0 : 1)).collect(Collectors.toSet());

    }
}
