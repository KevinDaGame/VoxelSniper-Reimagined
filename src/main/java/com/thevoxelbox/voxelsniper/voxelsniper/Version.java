package com.thevoxelbox.voxelsniper.voxelsniper;

public enum Version {
    V1_16,
    V1_17,
    V1_18,
    V1_19;

    //todo: Test if this works
    public boolean isSupported(Version v) {
        return Integer.parseInt(this.toString().substring(3)) >= Integer.parseInt(v.toString().substring(3));
    }
}
