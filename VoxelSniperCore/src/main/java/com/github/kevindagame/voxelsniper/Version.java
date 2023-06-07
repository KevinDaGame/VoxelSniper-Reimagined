package com.github.kevindagame.voxelsniper;

@Deprecated(since = "9.2.0", forRemoval = true)
//This doesn't allow forward compatibility 🤮
public enum Version {
    V1_16,
    V1_17,
    V1_18,
    V1_19,
    V1_20;

    public boolean isSupported(Version v) {
        return Integer.parseInt(this.name().substring(3)) >= Integer.parseInt(v.name().substring(3));
    }
}
