package com.github.kevindagame.voxelsniperforge.location;

import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.github.kevindagame.voxelsniperforge.world.ForgeWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public final class ForgeLocation {
    public static BlockPos toForgeBlockPos(VoxelLocation location) {
        return new BlockPos(location.getX(), location.getY(), location.getZ());
    }
    public static VoxelLocation fromForgeBlockPos(ForgeWorld world, BlockPos pos) {
        return new VoxelLocation(world, pos.getX(), pos.getY(), pos.getZ());
    }
}
