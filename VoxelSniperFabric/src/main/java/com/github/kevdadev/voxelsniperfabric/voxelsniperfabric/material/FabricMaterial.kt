package com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.material

import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.blockdata.FabricBlockData
import com.github.kevindagame.voxelsniper.blockdata.IBlockData
import com.github.kevindagame.voxelsniper.material.VoxelMaterial
import net.minecraft.block.Block
import net.minecraft.registry.Registries

class FabricMaterial(val block: Block, namespace: String, key: String): VoxelMaterial(namespace,key) {
    override fun createBlockData(): IBlockData {
        return FabricBlockData(block.defaultState)
    }

    override fun isAir(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isTransparent(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isBlock(): Boolean {
        TODO("Not yet implemented")
    }

    override fun createBlockData(s: String?): IBlockData {
        TODO("Not yet implemented")
    }

    companion object {
        fun fromFabricBlock(block: Block): VoxelMaterial {
            val tag = Registries.BLOCK.getKey(block)
            if (tag.isEmpty || block.defaultState.isAir) return AIR()
            val requiredTag = tag.get()
            return FabricMaterial(block, requiredTag.value.namespace, requiredTag.value.path)

        }
    }
}