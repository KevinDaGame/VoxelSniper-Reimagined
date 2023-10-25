package com.github.kevdadev.voxelsniperfabric

import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.FabricPlayer
import com.github.kevindagame.voxelsniper.entity.player.IPlayer
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

object FabricPlayerManager : ServerPlayConnectionEvents.Join, ServerPlayConnectionEvents.Disconnect {

    private val players: MutableMap<String, IPlayer> = mutableMapOf()
    override fun onPlayReady(handler: ServerPlayNetworkHandler?, sender: PacketSender?, server: MinecraftServer?) {
        val player = FabricPlayer(handler!!.player)
        players[handler.player.uuid.toString()] = player
    }

    override fun onPlayDisconnect(handler: ServerPlayNetworkHandler?, server: MinecraftServer?) {
        players.remove(handler!!.player.uuid.toString())
    }

    fun getPlayer(uuid: UUID): IPlayer? {
        return players[uuid.toString()]
    }

    fun getPlayer(player: ServerPlayerEntity): IPlayer {
        return players[player.uuid.toString()]!!
    }

    fun getPlayerByName(name: String): IPlayer? {
        return players.values.firstOrNull { it.getName() == name }
    }

    fun getPlayers(): List<IPlayer> {
        return players.values.toList()
    }


}