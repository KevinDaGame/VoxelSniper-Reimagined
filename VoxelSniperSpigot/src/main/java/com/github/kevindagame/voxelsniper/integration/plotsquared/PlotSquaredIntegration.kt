package com.github.kevindagame.voxelsniper.integration.plotsquared

import com.github.kevindagame.voxelsniper.events.player.PlayerSnipeEvent
import com.github.kevindagame.voxelsniper.world.SpigotWorld
import com.plotsquared.core.database.DBFunc
import com.plotsquared.core.location.Location
import com.plotsquared.core.plot.Plot

class PlotSquaredIntegration {

    init {
        PlayerSnipeEvent.registerListener { event: PlayerSnipeEvent ->
            handleEvent(event)
        }
    }

    private fun handleEvent(event: PlayerSnipeEvent) {
        if (event.isCancelled) return
        val world = (event.player.world as SpigotWorld).world

        for (operation in event.operations) {
            if (operation.isCancelled) continue
            val loc = operation.location

            //get plot for location
            val plot = Plot.getPlot(Location.at(world.name, loc.blockX, loc.blockY, loc.blockZ))
            if (plot != null) {
                if (plot.isOwner(event.player.uniqueId) || plot.trusted.contains(event.player.uniqueId) || plot.trusted.contains(
                        DBFunc.EVERYONE
                    )
                ) {
                    continue;
                }
            }
            operation.isCancelled = true
        }
    }
}