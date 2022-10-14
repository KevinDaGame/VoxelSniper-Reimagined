package com.github.kevindagame.voxelsniperforge;

import com.github.kevindagame.command.VoxelCommand;
import com.github.kevindagame.command.VoxelCommandManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public final class ForgeCommandManager extends VoxelCommandManager {

    private static WeakReference<CommandDispatcher<CommandSourceStack>> dispatcher;

    public static void initialize(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Instantiate Command Manager if it's not yet instantiated.
        if (getInstance() == null) {
            // store dispatcher as a weak reference - don't know the lifetime of this and don't want to keep it around unnecessarily
            ForgeCommandManager.dispatcher = new WeakReference<>(dispatcher);
            instance = new ForgeCommandManager();
        }
    }

    @Override
    public void registerCommand(VoxelCommand command) {
        List<String> identifiers = new ArrayList<>(command.getOtherIdentifiers());
        identifiers.add(command.getIdentifier());
        ForgeCommandHandler handler = new ForgeCommandHandler(command);
        var dispatcher = ForgeCommandManager.dispatcher.get();
        assert dispatcher != null;

        for (String id : identifiers) {
            // TODO aliases?
            argumentsMap.put(id, command.registerTabCompletion());
            dispatcher.register(
                    Commands.literal(id).requires(handler).then(
                            Commands.argument("args", StringArgumentType.greedyString()).suggests(handler).executes(handler)
                    ).executes(handler)
            );
        }
    }
}
