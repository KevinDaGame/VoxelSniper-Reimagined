package com.github.kevdadev.voxelsniperfabric

import com.github.kevindagame.command.VoxelCommand
import com.github.kevindagame.command.VoxelCommandManager
import com.mojang.brigadier.CommandDispatcher
import java.lang.ref.WeakReference

class FabricCommandManager: VoxelCommandManager() {
    override fun registerCommand(command: VoxelCommand?) {

    }

    companion object {
        fun initialize() {
            instance = FabricCommandManager()
        }
    }
}