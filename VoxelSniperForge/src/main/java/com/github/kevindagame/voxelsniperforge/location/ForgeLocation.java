package com.github.kevindagame.voxelsniperforge.location;

import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniperforge.world.ForgeWorld;
import net.minecraft.core.BlockPos;

public final class ForgeLocation {
    public static BlockPos toForgeBlockPos(BaseLocation location) {
        return new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
    public static BaseLocation fromForgeBlockPos(ForgeWorld world, BlockPos pos) {
        return new BaseLocation(world, pos.getX(), pos.getY(), pos.getZ());
    }
}
