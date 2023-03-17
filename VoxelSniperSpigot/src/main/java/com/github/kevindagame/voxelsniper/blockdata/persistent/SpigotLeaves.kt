package com.github.kevindagame.voxelsniper.blockdata.persistent

import com.github.kevindagame.voxelsniper.blockdata.SpigotBlockData
import org.bukkit.block.data.type.Leaves

class SpigotLeaves(blockData: Leaves) : SpigotBlockData(blockData), ILeaves {
    override fun isPersistent(): Boolean {
        return (blockData as Leaves).isPersistent
    }

    override fun setPersistent(persistent: Boolean) {
        (blockData as Leaves).isPersistent = persistent
    }
}
