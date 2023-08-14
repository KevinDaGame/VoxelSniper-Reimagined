package com.github.kevindagame.voxelsniperforge.blockdata.leaves

import com.github.kevindagame.voxelsniper.blockdata.leaves.ILeaves
import com.github.kevindagame.voxelsniperforge.blockdata.waterlogged.ForgeWaterlogged
import net.minecraft.world.level.block.LeavesBlock
import net.minecraft.world.level.block.state.BlockState

class ForgeLeaves(state: BlockState?) : ForgeWaterlogged(state), ILeaves {
    override fun isPersistent(): Boolean {
        return get(LeavesBlock.PERSISTENT)
    }

    override fun setPersistent(persistent: Boolean) {
        set(LeavesBlock.PERSISTENT, persistent)
    }
}
