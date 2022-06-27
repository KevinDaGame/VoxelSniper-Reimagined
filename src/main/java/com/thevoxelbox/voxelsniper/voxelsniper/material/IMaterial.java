package com.thevoxelbox.voxelsniper.voxelsniper.material;

public interface IMaterial {
    boolean isSolid();
    String getKey();
    boolean equals(String key);

}
