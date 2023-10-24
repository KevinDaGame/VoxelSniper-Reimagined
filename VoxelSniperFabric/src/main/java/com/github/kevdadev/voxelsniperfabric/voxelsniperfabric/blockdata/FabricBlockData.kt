package com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.blockdata

import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.material.FabricMaterial
import com.github.kevindagame.voxelsniper.blockdata.IBlockData
import com.github.kevindagame.voxelsniper.material.VoxelMaterial
import com.google.common.base.Preconditions
import net.minecraft.block.BlockState

class FabricBlockData(val state: BlockState) : IBlockData, Cloneable {
    override fun getMaterial(): VoxelMaterial {
        return FabricMaterial.fromFabricBlock(state.block)
    }

    override fun matches(blockData: IBlockData?): Boolean {
        if(blockData == null) return false
        if(blockData !is FabricBlockData) return false
        if(this == blockData) return true

        if(state.block != blockData.state.block) return false

        return this.merge(blockData) == this
    }

    override fun getAsString(): String {
        TODO("Not yet implemented")
    }

    override fun merge(newData: IBlockData): IBlockData {
       TODO()
    }

    override fun getCopy(): IBlockData {
        TODO("Not yet implemented")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FabricBlockData

        return state == other.state
    }

    override fun hashCode(): Int {
        return state.hashCode()
    }

}