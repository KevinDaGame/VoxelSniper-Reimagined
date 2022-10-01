package com.github.kevindagame.voxelsniperforge.location;

import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.github.kevindagame.voxelsniper.world.SpigotWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.bukkit.Location;

public final class ForgeLocation {
    public static BlockPos toSpigotLocation(VoxelLocation location) {
        return new BlockPos(((ForgeWorld) location.getWorld()).world(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }
}
