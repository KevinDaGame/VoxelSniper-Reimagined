package com.github.kevindagame.voxelsniper.blockdata.persistent

import com.github.kevindagame.voxelsniper.blockdata.SpigotBlockData
import org.bukkit.block.data.type.Leaves

class SpigotPersistent(blockData: Leaves) : SpigotBlockData(blockData), IPersistent {
    override fun isPersistent(): Boolean {
        return (blockData as Leaves).isPersistent
    }

    override fun setPersistent(persistent: Boolean) {
        (blockData as Leaves).isPersistent = persistent
    }
}
