package com.github.kevindagame.mocks

import com.github.kevindagame.VoxelBrushManager
import com.github.kevindagame.VoxelSniper
import com.github.kevindagame.voxelsniper.Environment
import com.github.kevindagame.voxelsniper.IVoxelsniper
import com.github.kevindagame.voxelsniper.biome.VoxelBiome
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType
import com.github.kevindagame.voxelsniper.entity.player.IPlayer
import com.github.kevindagame.voxelsniper.fileHandler.IFileHandler
import com.github.kevindagame.voxelsniper.fileHandler.IVoxelSniperConfiguration
import com.github.kevindagame.voxelsniper.material.VoxelMaterial
import com.github.kevindagame.voxelsniper.treeType.VoxelTreeType
import java.util.*
import java.util.logging.Logger

class MockVoxelSniper: IVoxelsniper {
    init {
        VoxelSniper.voxelsniper = this
        VoxelBrushManager.initialize()
    }
    override fun getPlayer(uuid: UUID?): IPlayer? {
        TODO("Not yet implemented")
    }

    override fun getPlayer(name: String?): IPlayer? {
        TODO("Not yet implemented")
    }

    override fun getEnvironment(): Environment {
        TODO("Not yet implemented")
    }

    override fun getVoxelSniperConfiguration(): IVoxelSniperConfiguration? {
        return VoxelSniperConfigurationMock()
    }

    override fun getFileHandler(): IFileHandler {
        TODO("Not yet implemented")
    }

    override fun getLogger(): Logger {
        TODO("Not yet implemented")
    }

    override fun getOnlinePlayerNames(): MutableList<String> {
        TODO("Not yet implemented")
    }

    private val vMaterials = listOf(
        VoxelMaterialMock("DIRT"),
        VoxelMaterialMock("GRASS_BLOCK")
    )
    override fun getMaterial(namespace: String?, key: String?): VoxelMaterial? {
        return vMaterials.firstOrNull { it.getKey() == key }
    }

    override fun getMaterials(): MutableList<VoxelMaterial> {
        return vMaterials.toMutableList()
    }

    override fun getBiome(namespace: String?, key: String?): VoxelBiome? {
        TODO("Not yet implemented")
    }

    override fun getBiomes(): MutableList<VoxelBiome> {
        TODO("Not yet implemented")
    }

    override fun getEntityType(namespace: String?, key: String?): VoxelEntityType? {
        TODO("Not yet implemented")
    }

    override fun getEntityTypes(): MutableList<VoxelEntityType> {
        TODO("Not yet implemented")
    }

    override fun getTreeType(namespace: String?, key: String?): VoxelTreeType? {
        TODO("Not yet implemented")
    }

    override fun getDefaultTreeType(): VoxelTreeType {
        TODO("Not yet implemented")
    }

    override fun getTreeTypes(): MutableList<VoxelTreeType> {
        TODO("Not yet implemented")
    }

}