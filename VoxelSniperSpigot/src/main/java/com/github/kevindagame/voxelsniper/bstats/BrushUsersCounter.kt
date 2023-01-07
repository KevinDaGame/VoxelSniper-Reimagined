package com.github.kevindagame.voxelsniper.bstats

import com.github.kevindagame.voxelsniper.events.player.PlayerSnipeEvent
import java.util.*

class BrushUsersCounter {
    fun registerListeners() {
        PlayerSnipeEvent.registerListener { event: PlayerSnipeEvent -> onBrushUse(event) }
    }

    private fun onBrushUse(event: PlayerSnipeEvent) {
        if (event.isCancelled) return
        users.add(event.player.uniqueId)
    }

    companion object {
        private val users: MutableSet<UUID> = HashSet()
        fun getTotalBrushUses(): Int {
                val brushUsers = users.size
                users.clear()
                return brushUsers
            }
    }
}