package com.github.kevindagame.util;

import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Container class for multiple ID/Datavalue pairs.
 */
public class VoxelList {

    private final List<VoxelMaterial> materials = new ArrayList<>();

    public void add(VoxelMaterial material) {
        if (!materials.contains(material)) {
            this.materials.add(material);
        }
    }

    public void remove(VoxelMaterial material) {
        if (materials.contains(material)) {
            this.materials.remove(material);
        }
    }

    public boolean contains(VoxelMaterial material) {
        return this.materials.contains(material);
    }

    /**
     * Clears the VoxelList.
     */
    public void clear() {
        materials.clear();
    }

    /**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements
     */
    public boolean isEmpty() {
        return materials.isEmpty();
    }

    /**
     * Returns a defensive copy of the List with pairs.
     *
     * @return defensive copy of the List with pairs
     */
    public List<VoxelMaterial> getList() {
        return Collections.unmodifiableList(materials);
    }

}
