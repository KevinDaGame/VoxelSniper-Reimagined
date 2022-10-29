package com.github.kevindagame.voxelsniperforge;

import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.command.VoxelCommand;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniperforge.entity.player.ForgePlayer;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import net.minecraft.commands.CommandSourceStack;

public class ForgeCommandHandler implements Predicate<CommandSourceStack>, Command<CommandSourceStack>, SuggestionProvider<CommandSourceStack> {
    private final VoxelCommand command;

    public ForgeCommandHandler(VoxelCommand command) {
        this.command = command;
    }

    @Override
    public boolean test(CommandSourceStack commandSource) {
        if (commandSource.isPlayer()) {
            IPlayer p = VoxelSniperForge.getInstance().getPlayer(commandSource.getPlayer());
            return p.hasPermission(this.command.getPermission());
        }
        return false;
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var inputString = context.getInput();
        var input = inputString.split(" ");
        this.command.setActiveIdentifier(context.getNodes().get(0).getNode().getName()); // This is the root command.
        this.command.setActiveAlias(input[0]);   // This is the alias that was executed.

        if (context.getSource().isPlayer()) {
            IPlayer p = VoxelSniperForge.getInstance().getPlayer(context.getSource().getPlayer());
            var args = getArguments(inputString);

            this.command.execute(p, args);
        } else {
            context.getSource().sendSystemMessage(ForgePlayer.toNative(Messages.ONLY_PLAYERS_CAN_EXECUTE_COMMANDS.asComponent()));
        }
        return 0;  // TODO figure out what we should return
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        var inputString = context.getInput();
        var input = inputString.split(" ");
        this.command.setActiveIdentifier(context.getRootNode().getName()); // This is the root command.
        this.command.setActiveAlias(input[0]);   // This is the alias that was executed.

        if (context.getSource().isPlayer()) {
            IPlayer p = VoxelSniperForge.getInstance().getPlayer(context.getSource().getPlayer());
            var args = getArguments(inputString);
            var lastArg = args[args.length - 1];
            // create string with all arguments, except the last one
            var argString = inputString.substring(input[0].length()+1, inputString.length() - lastArg.length());

            var completions = this.command.doSuggestion(p, args);
            completions.stream().filter(s -> startsWithIgnoreCase(lastArg, s)).forEach(s -> builder.suggest(argString + s));
        }

        return builder.buildFuture();
    }

    private String[] getArguments(String inputString) {
        var input = inputString.split(" ");
        if (inputString.endsWith(" ")) {
            String[] args = Arrays.copyOfRange(input, 1, input.length + 1);
            args[args.length - 1] = "";
            return args;
        } else {
            return Arrays.copyOfRange(input, 1, input.length);
        }
    }

    private boolean startsWithIgnoreCase(String prefix, String s) {
        return s.toLowerCase(Locale.ROOT).startsWith(prefix.toLowerCase(Locale.ROOT));
    }
}
