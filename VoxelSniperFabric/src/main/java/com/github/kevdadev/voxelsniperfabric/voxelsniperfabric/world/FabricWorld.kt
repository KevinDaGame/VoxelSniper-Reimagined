package com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.world

import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.block.FabricBlock
import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.chunk.FabricChunk
import com.github.kevindagame.util.brushOperation.BrushOperation
import com.github.kevindagame.voxelsniper.biome.VoxelBiome
import com.github.kevindagame.voxelsniper.block.IBlock
import com.github.kevindagame.voxelsniper.chunk.IChunk
import com.github.kevindagame.voxelsniper.entity.IEntity
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType
import com.github.kevindagame.voxelsniper.location.BaseLocation
import com.github.kevindagame.voxelsniper.treeType.VoxelTreeType
import com.github.kevindagame.voxelsniper.world.IWorld
import net.minecraft.world.World

class FabricWorld(val world: World): IWorld {
    override fun getBlock(location: BaseLocation): IBlock {
        if(location.world != this) {
            throw IllegalArgumentException("Location is not in this world")
        }
        return FabricBlock(location)
    }

    override fun getBlock(x: Int, y: Int, z: Int): IBlock {
        return getBlock(BaseLocation(this, x.toDouble(), y.toDouble(), z.toDouble()))
    }

    override fun getMinWorldHeight(): Int {
        return world.bottomY
    }

    override fun getMaxWorldHeight(): Int {
        return world.topY
    }

    override fun getChunkAtLocation(x: Int, z: Int): IChunk {
        return FabricChunk(world.getChunk(x, z), this)
    }

    override fun getNearbyEntities(location: BaseLocation?, x: Double, y: Double, z: Double): MutableList<IEntity> {
        TODO("Not yet implemented")
    }

    override fun refreshChunk(x: Int, z: Int) {
        TODO("Not yet implemented")
    }

    override fun strikeLightning(location: BaseLocation?) {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        return world.registryKey.value.toString()
    }

    override fun spawn(location: BaseLocation?, entity: VoxelEntityType?) {
        TODO("Not yet implemented")
    }

    override fun setBiome(x: Int, y: Int, z: Int, selectedBiome: VoxelBiome?) {
        TODO("Not yet implemented")
    }

    override fun getHighestBlockYAt(x: Int, z: Int): Int {
        TODO("Not yet implemented")
    }

    override fun regenerateChunk(x: Int, z: Int) {
        TODO("Not yet implemented")
    }

    override fun generateTree(
        location: BaseLocation?,
        treeType: VoxelTreeType?,
        updateBlocks: Boolean
    ): MutableList<BrushOperation>? {
        TODO("Not yet implemented")
    }

    override fun getBiome(location: BaseLocation?): VoxelBiome {
        TODO("Not yet implemented")
    }
}