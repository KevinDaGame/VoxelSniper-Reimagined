package com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.material

import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.blockdata.FabricBlockData
import com.github.kevindagame.voxelsniper.blockdata.IBlockData
import com.github.kevindagame.voxelsniper.material.VoxelMaterial
import net.minecraft.block.Block
import net.minecraft.registry.Registries

class FabricBlockMaterial(val block: Block, namespace: String, key: String): FabricMaterial(namespace,key) {
    override fun createBlockData(): IBlockData {
        return FabricBlockData(block.defaultState)
    }

    override fun isAir(): Boolean {
        return block.defaultState.isAir
    }

    override fun isTransparent(): Boolean {
        return !block.defaultState.isOpaque
    }

    override fun isBlock(): Boolean {
        return true
    }

    override fun createBlockData(s: String?): IBlockData {
        return FabricBlockData(block.defaultState)
    }

    companion object {
        fun fromFabricBlock(block: Block): VoxelMaterial {
            val tag = Registries.BLOCK.getKey(block)
            if (tag.isEmpty || block.defaultState.isAir) return AIR()
            val requiredTag = tag.get()
            return FabricBlockMaterial(block, requiredTag.value.namespace, requiredTag.value.path)

        }
    }
}