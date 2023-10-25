package com.github.kevdadev.voxelsniperfabric

import com.github.kevindagame.command.VoxelCommand
import com.github.kevindagame.command.VoxelCommandManager
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.builder.RequiredArgumentBuilder.argument
import net.minecraft.server.command.ServerCommandSource

class FabricCommandManager: VoxelCommandManager() {
    override fun registerCommand(command: VoxelCommand?) {
        val identifiers: MutableList<String> = ArrayList(command!!.otherIdentifiers)
        identifiers.add(command.identifier)
        val handler = FabricCommandHandler(command)

        for (id in identifiers) {
            // TODO aliases?
            argumentsMap[id] = command.registerTabCompletion()
            dispatcher.register(
                literal<ServerCommandSource?>(id).requires(handler).then(
                    argument<ServerCommandSource, String>("args", StringArgumentType.greedyString())
                        .suggests(handler).executes(handler)
                )
            )
        }
    }

    companion object {
        private lateinit var dispatcher: CommandDispatcher<ServerCommandSource>
        fun initialize(dispatcher: CommandDispatcher<ServerCommandSource>) {
            this.dispatcher = dispatcher
            instance = FabricCommandManager()
        }
    }
}