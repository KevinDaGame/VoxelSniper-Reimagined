package com.github.kevdadev.voxelsniperfabric

import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.FabricPlayer
import com.github.kevindagame.command.VoxelCommand
import com.github.kevindagame.util.Messages
import com.github.kevindagame.voxelsniper.entity.player.IPlayer
import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.server.command.ServerCommandSource
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.function.Predicate

class FabricCommandHandler(private val voxelCommand: VoxelCommand):
    Predicate<ServerCommandSource>,
    SuggestionProvider<ServerCommandSource>,
    Command<ServerCommandSource> {
    override fun test(commandSource: ServerCommandSource): Boolean {
        if (commandSource.isExecutedByPlayer) {
            val p = commandSource.player ?: return false
            val player: IPlayer = VoxelSniperFabric.instance.getPlayer(p)
            return player.hasPermission(voxelCommand.permission)
        }
        return false
    }

    override fun getSuggestions(
        context: CommandContext<ServerCommandSource>?,
        builder: SuggestionsBuilder?
    ): CompletableFuture<Suggestions> {
        val inputString = context!!.input
        val input = inputString.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        this.voxelCommand.setActiveIdentifier(context.rootNode.name) // This is the root command.

        this.voxelCommand.setActiveAlias(input[0]) // This is the alias that was executed.

        if (context.source.isExecutedByPlayer) {
            val p = VoxelSniperFabric.instance.getPlayer(context.source.player!!)
            val args: Array<String> = getArguments(inputString)
            val lastArg = args[args.size - 1]
            // create string with all arguments, except the last one
            val argString = inputString.substring(input[0].length + 1, inputString.length - lastArg.length)
            val completions: List<String> = voxelCommand.doSuggestion(p, args)
            completions.stream().filter(Predicate<String> { s: String ->
                startsWithIgnoreCase(
                    lastArg,
                    s
                )
            }).forEach { s: String -> builder!!.suggest(argString + s) }
        }

        return builder!!.buildFuture()
    }

    private fun getArguments(inputString: String): Array<String> {
        val input = inputString.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return if (inputString.endsWith(" ")) {
            val args = Arrays.copyOfRange(input, 1, input.size + 1)
            args[args.size - 1] = ""
            args
        } else {
            Arrays.copyOfRange(input, 1, input.size)
        }
    }

    private fun startsWithIgnoreCase(prefix: String, s: String): Boolean {
        return s.lowercase().startsWith(prefix.lowercase())
    }

    override fun run(context: CommandContext<ServerCommandSource>?): Int {
        val inputString = context!!.input
        val input = inputString.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        voxelCommand.activeIdentifier = context.nodes[0].node.name // This is the root command.

        voxelCommand.activeAlias = input[0] // This is the alias that was executed.


        if (context.source.isExecutedByPlayer) {
            val p: IPlayer = VoxelSniperFabric.instance.getPlayer(context.source.player!!)
            val args = getArguments(inputString)
            voxelCommand.execute(p, args)
        } else {
            context.source.sendError(FabricPlayer.toNative(Messages.ONLY_PLAYERS_CAN_EXECUTE_COMMANDS.asComponent()))
        }
        return 0 // TODO figure out what we should return

    }
}