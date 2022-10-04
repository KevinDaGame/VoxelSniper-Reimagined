package com.github.kevindagame.voxelsniperforge.location;

import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import net.minecraft.core.BlockPos;

public final class ForgeLocation {
    public static BlockPos toForgeBlockPos(VoxelLocation location) {
        return new BlockPos(location.getX(), location.getY(), location.getZ());
    }
}
