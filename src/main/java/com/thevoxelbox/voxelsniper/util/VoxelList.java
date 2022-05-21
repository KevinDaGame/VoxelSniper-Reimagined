package com.thevoxelbox.voxelsniper.util;

import com.google.common.collect.ImmutableList;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * Container class for multiple ID/Datavalue pairs.
 */
public class VoxelList {

    private List<Material> materials = new ArrayList<>();

    public void add(Material material) {
        if (!materials.contains(material)) {
            this.materials.add(material);
        }
    }

    public void remove(Material material) {
        if (materials.contains(material)) {
            this.materials.remove(material);
        }
    }

    public boolean contains(Material material) {
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
    public List<Material> getList() {
        return ImmutableList.copyOf(materials);
    }

}
