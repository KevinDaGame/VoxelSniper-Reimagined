package com.github.kevdadev.voxelsniperfabric

import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.filehandler.FabricFileHandler
import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.material.FabricMaterial
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
import com.mojang.serialization.Codec
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.player.UseItemCallback
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.block.Block
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.util.Identifier
import net.minecraft.world.biome.source.BiomeSource
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

class VoxelSniperFabric : ModInitializer, IVoxelsniper {

    private lateinit var voxelSniperConfiguration: VoxelSniperConfiguration
    private lateinit var fileHandler: IFileHandler

    private lateinit var biomeRegistries: Registry<Codec<out BiomeSource>>

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

        biomeRegistries = Registries.BIOME_SOURCE

        FabricCommandManager.initialize()
        VoxelCommandManager.getInstance().registerBrushSubcommands()

        UseItemCallback.EVENT.register(FabricVoxelSniperListener())

        ServerPlayConnectionEvents.JOIN.register(FabricPlayerManager)
        ServerPlayConnectionEvents.DISCONNECT.register(FabricPlayerManager)
    }

    override fun getPlayer(uuid: UUID): IPlayer? {
        return FabricPlayerManager.getPlayer(uuid)
    }

    override fun getPlayer(name: String): IPlayer? {
        return FabricPlayerManager.getPlayerByName(name)
    }

    override fun getEnvironment(): Environment {
        return Environment.FABRIC
    }

    override fun getVoxelSniperConfiguration(): VoxelSniperConfiguration {
        return voxelSniperConfiguration
    }

    override fun getFileHandler(): IFileHandler {
        return fileHandler
    }

    override fun getLogger(): Logger {
        return LOGGER
    }

    override fun getOnlinePlayerNames(): List<String> {
        return FabricPlayerManager.getPlayers().map { it.getName() }
    }

    override fun getMaterial(namespace: String, key: String): VoxelMaterial? {
        val result = Registries.BLOCK.getOrEmpty(Identifier(namespace, key))
        if (result.isPresent) {
            return FabricMaterial(result.get(), namespace, key)
        }
        return null
    }

    override fun getMaterials(): List<VoxelMaterial> {
        return listOf()
    }

    override fun getBiome(namespace: String, key: String): VoxelBiome? {
        TODO()
    }

    override fun getBiomes(): List<VoxelBiome> {
        return listOf()
    }

    override fun getEntityType(namespace: String, key: String): VoxelEntityType? {
        return VoxelEntityType("minecraft", "player")
    }

    override fun getEntityTypes(): List<VoxelEntityType> {
        return listOf()
    }

    override fun getTreeType(namespace: String, key: String): VoxelTreeType? {
        TODO()
    }

    override fun getDefaultTreeType(): VoxelTreeType {
        return VoxelTreeType("minecraft", "oak")
    }

    override fun getTreeTypes(): List<VoxelTreeType> {
        return listOf()
    }

    companion object {
        private val LOGGER = Logger.getLogger("VoxelSniperFabric")
    }
}
