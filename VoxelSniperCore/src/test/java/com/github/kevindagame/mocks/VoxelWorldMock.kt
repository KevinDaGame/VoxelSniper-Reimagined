package com.github.kevindagame.mocks

import com.github.kevindagame.util.brushOperation.BrushOperation
import com.github.kevindagame.voxelsniper.biome.VoxelBiome
import com.github.kevindagame.voxelsniper.block.IBlock
import com.github.kevindagame.voxelsniper.chunk.IChunk
import com.github.kevindagame.voxelsniper.entity.IEntity
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType
import com.github.kevindagame.voxelsniper.location.BaseLocation
import com.github.kevindagame.voxelsniper.material.VoxelMaterial
import com.github.kevindagame.voxelsniper.treeType.VoxelTreeType
import com.github.kevindagame.voxelsniper.world.IWorld

enum class WorldType {
    FLAT,
    NORMAL
}

class VoxelWorldMock(
    worldType: WorldType,
    val xWidth: Int = 50,
    val minY: Int = -64,
    val maxY: Int = 128,
    val zWidth: Int = 50
) : IWorld {
    var blocks: MutableList<MutableList<MutableList<VoxelBlockMock>>> = mutableListOf()

    init {
        when (worldType) {
            WorldType.FLAT -> {
                for (x in 0 until xWidth) {
                    blocks.add(mutableListOf())
                    for (z in 0 until zWidth) {
                        blocks[x].add(mutableListOf())
                        for (y in minY until maxY) {
                            var material = VoxelMaterial.AIR()
                            if (y < 60) {
                                material = VoxelMaterial.STONE()
                            }
                            else if (y < 64) {
                                material = VoxelMaterial.getMaterial("DIRT")
                            }
                            else if (y == 64) {
                                material = VoxelMaterial.getMaterial("GRASS_BLOCK")
                            }
                            blocks[x][z].add(VoxelBlockMock())
                        }
                    }
                }
            }

            WorldType.NORMAL -> {
                throw NotImplementedError("WorldType.NORMAL is not implemented yet")
            }
        }
    }

    override fun getBlock(location: BaseLocation): IBlock {
        return getBlock(location.blockX, location.blockY, location.blockZ)
    }

    override fun getBlock(x: Int, y: Int, z: Int): IBlock {
        return blocks[x][z][y - minY]
    }

    override fun getMinWorldHeight(): Int {
        return minY
    }

    override fun getMaxWorldHeight(): Int {
        return maxY
    }

    override fun getChunkAtLocation(x: Int, z: Int): IChunk {
        throw NotImplementedError("getChunkAtLocation is not implemented yet")
    }

    override fun getNearbyEntities(location: BaseLocation?, x: Double, y: Double, z: Double): MutableList<IEntity> {
        throw NotImplementedError("getNearbyEntities is not implemented yet")
    }

    override fun refreshChunk(x: Int, z: Int) {
        throw NotImplementedError("refreshChunk is not implemented yet")
    }

    override fun strikeLightning(location: BaseLocation?) {
        throw NotImplementedError("strikeLightning is not implemented yet")
    }

    override fun getName(): String {
        return "Mock world"
    }

    override fun spawn(location: BaseLocation?, entity: VoxelEntityType?) {
        throw NotImplementedError("spawn is not implemented yet")
    }

    override fun setBiome(x: Int, y: Int, z: Int, selectedBiome: VoxelBiome?) {
        throw NotImplementedError("setBiome is not implemented yet")
    }

    override fun getHighestBlockYAt(x: Int, z: Int): Int {
        throw NotImplementedError("getHighestBlockYAt is not implemented yet")
    }

    override fun regenerateChunk(x: Int, z: Int) {
        throw NotImplementedError("regenerateChunk is not implemented yet")
    }

    override fun generateTree(
        location: BaseLocation?,
        treeType: VoxelTreeType?,
        updateBlocks: Boolean
    ): MutableList<BrushOperation>? {
        throw NotImplementedError("generateTree is not implemented yet")
    }

    override fun getBiome(location: BaseLocation?): VoxelBiome {
        TODO("Not yet implemented")
    }
}