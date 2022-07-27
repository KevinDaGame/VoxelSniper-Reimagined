package com.thevoxelbox.voxelsniper.voxelsniper.location;

import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.chunk.IChunk;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.IVector;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

public interface ILocation {

    int getBlockX();

    int getBlockY();

    int getBlockZ();
    double getX();

    double getY();

    double getZ();

    void setX(double x);

    void setY(double y);

    void setZ(double z);

    default void addX(double x) {
        this.setX(this.getX() + x);
    }

    default void addY(double y) {
        this.setY(this.getY() + y);
    }

    default void addZ(double z) {
        this.setZ(this.getZ() + z);
    }
    default void add(int x, int y, int z) {
        this.addX(x);
        this.addY(y);
        this.addZ(z);
    }
    default void add(ILocation location) {
        this.addX(location.getX());
        this.addY(location.getY());
        this.addZ(location.getZ());
    }
    float getYaw();
    float getPitch();
    void setYaw(float yaw);
    void setPitch(float pitch);
    IWorld getWorld();

    IBlock getClampedBlock();

    IBlock getBlock();

    IVector toVector();

    double distanceSquared(ILocation targetLocation);

    IChunk getChunk();

    double distance(ILocation location);
}
