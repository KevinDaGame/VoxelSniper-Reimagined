package com.github.kevdadev.voxelsniperfabric

import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.filehandler.FabricFileHandler
import com.github.kevindagame.VoxelBrushManager
import com.github.kevindagame.VoxelSniper
import com.github.kevindagame.command.VoxelCommandManager
import com.github.kevindagame.util.Messages
import com.github.kevindagame.util.schematic.SchematicReader
import com.github.kevindagame.voxelsniper.Environment
import com.github.kevindagame.voxelsniper.IVoxelsniper
import com.github.kevindagame.voxelsniper.biome.VoxelBiome
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType
import com.github.kevindagame.voxelsniper.entity.player.IPlayer
import com.github.kevindagame.voxelsniper.fileHandler.IFileHandler
import com.github.kevindagame.voxelsniper.fileHandler.VoxelSniperConfiguration
import com.github.kevindagame.voxelsniper.material.VoxelMaterial
import com.github.kevindagame.voxelsniper.treeType.VoxelTreeType
import net.fabricmc.api.ModInitializer
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

class VoxelSniperFabric : ModInitializer, IVoxelsniper {

    private lateinit var voxelSniperConfiguration: VoxelSniperConfiguration
    private lateinit var fileHandler: IFileHandler

    /**
     * Runs the mod initializer.
     */
    override fun onInitialize() {
        VoxelSniper.voxelsniper = this
        val brushManager = VoxelBrushManager.initialize()
        logger.log(
            Level.INFO,
            "Registered {0} Sniper Brushes with {1} handles.",
            arrayOf<Any>(brushManager.registeredSniperBrushes(), brushManager.registeredSniperBrushHandles())
        )


        fileHandler = FabricFileHandler()
        SchematicReader.initialize()
        Messages.load(this)

        voxelSniperConfiguration = VoxelSniperConfiguration(this)

        VoxelCommandManager.getInstance().registerBrushSubcommands();
    }

    override fun getPlayer(uuid: UUID): IPlayer? {
        TODO()
    }

    override fun getPlayer(name: String): IPlayer? {
        TODO()
    }

    override fun getEnvironment(): Environment {
        TODO()
    }

    override fun getVoxelSniperConfiguration(): VoxelSniperConfiguration {
        TODO()
    }

    override fun getFileHandler(): IFileHandler {
        return fileHandler
    }

    override fun getLogger(): Logger {
        return LOGGER
    }

    override fun getOnlinePlayerNames(): List<String> {
        TODO()
    }

    override fun getMaterial(namespace: String, key: String): VoxelMaterial? {
        TODO()
    }

    override fun getMaterials(): List<VoxelMaterial> {
        TODO()
    }

    override fun getBiome(namespace: String, key: String): VoxelBiome? {
        TODO()
    }

    override fun getBiomes(): List<VoxelBiome> {
        TODO()
    }

    override fun getEntityType(namespace: String, key: String): VoxelEntityType? {
        TODO()
    }

    override fun getEntityTypes(): List<VoxelEntityType> {
        TODO()
    }

    override fun getTreeType(namespace: String, key: String): VoxelTreeType? {
        TODO()
    }

    override fun getDefaultTreeType(): VoxelTreeType {
        TODO()
    }

    override fun getTreeTypes(): List<VoxelTreeType> {
        TODO()
    }

    companion object {
        private val LOGGER = Logger.getLogger("VoxelSniperFabric")
    }
}
