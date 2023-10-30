package com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.chunk

import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.world.FabricWorld
import com.github.kevindagame.voxelsniper.chunk.IChunk
import com.github.kevindagame.voxelsniper.entity.IEntity
import com.github.kevindagame.voxelsniper.world.IWorld
import net.minecraft.world.chunk.Chunk

class FabricChunk(val chunk: Chunk, val world: FabricWorld) : IChunk {
    override fun getX(): Int {
        return chunk.pos.x
    }

    override fun getZ(): Int {
        return chunk.pos.z
    }

    override fun getWorld(): IWorld {
        return world
    }

    override fun getEntities(): MutableIterable<IEntity> {
        TODO("Not yet implemented")
    }
}