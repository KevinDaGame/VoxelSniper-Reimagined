package com.github.kevindagame.util;

import com.github.kevindagame.voxelsniper.location.BaseLocation;

import java.util.List;
import java.util.stream.Collectors;

public class Actions {

    /**
     * Returns the locations that are on a checker grid, according to their sum
     *
     * @param locations A list of locations
     * @param even      whether the sum of x + y + z should be even or odd
     * @return The locations of which the sum of x + y + z is even or odd
     */
    public static List<BaseLocation> checker(List<BaseLocation> locations, boolean even) {
        return locations.stream().filter(location -> (location.getBlockX() + location.getBlockY() + location.getBlockZ()) % 2 != (even ? 0 : 1)).collect(Collectors.toList());

    }
}
