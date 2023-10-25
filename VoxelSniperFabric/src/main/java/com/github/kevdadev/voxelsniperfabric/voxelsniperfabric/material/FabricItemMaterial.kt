package com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.material

import com.github.kevindagame.voxelsniper.blockdata.IBlockData
import net.minecraft.item.Item

class FabricItemMaterial(item: Item, namespace: String, key: String) : FabricMaterial(namespace, key) {
    override fun createBlockData(): IBlockData {
        TODO("Not yet implemented")
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
}