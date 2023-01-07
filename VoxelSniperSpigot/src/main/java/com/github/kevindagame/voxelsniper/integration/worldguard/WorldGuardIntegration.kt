package com.github.kevindagame.voxelsniper.integration.worldguard

import com.github.kevindagame.voxelsniper.entity.player.SpigotPlayer
import com.github.kevindagame.voxelsniper.events.player.PlayerSnipeEvent
import com.sk89q.worldedit.util.Location
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import com.sk89q.worldguard.protection.flags.Flags


class WorldGuardIntegration {
    init {
        enabled = true
        PlayerSnipeEvent.registerListener(this::handleEvent)
    }

    private fun handleEvent(event: PlayerSnipeEvent) {
        if (event.isCancelled) return
        if (event.player.hasPermission("voxelsniper.bypass.plotsquared")) return
        //Check if each operation is within the worldguard area
        val instance = WorldGuard.getInstance()
        val localPlayer = WorldGuardPlugin.inst().wrapPlayer((event.player as SpigotPlayer).player)
        val world = localPlayer.world
        val container = instance.platform.regionContainer
        val query = container.createQuery()

        if (instance.platform.sessionManager.hasBypass(localPlayer, world)) return

        for (operation in event.operations) {
            if (operation.isCancelled) continue
            val loc = operation.location
            val location = Location(world, loc.x, loc.y, loc.z)

            if (!query.testState(location, localPlayer, Flags.BUILD)) {
                operation.isCancelled = true
            }
        }
    }

    companion object {
        var enabled: Boolean = false
    }
}