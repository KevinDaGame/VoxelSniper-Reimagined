package com.thevoxelbox.voxelsniper.voxelsniper.location;

import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

public interface ILocation {

    public int getX();

    public int getY();

    public int getZ();

    public void setX(int x);

    public void setY(int y);

    public void setZ(int z);

    default void addX(int x) {
        this.setX(this.getX() + x);
    }

    default void addY(int y) {
        this.setY(this.getY() + y);
    }

    default void addZ(int z) {
        this.setZ(this.getZ() + z);
    }
    float getYaw();
    float getPitch();
    void setYaw(float yaw);
    void setPitch(float pitch);
    IWorld getWorld();

}
