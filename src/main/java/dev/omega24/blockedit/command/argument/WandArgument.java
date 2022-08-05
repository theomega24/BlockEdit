package dev.omega24.blockedit.command.argument;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.exceptions.parsing.NoInputProvidedException;
import dev.omega24.blockedit.BlockEdit;
import dev.omega24.blockedit.config.Lang;
import dev.omega24.blockedit.manager.WandManager;
import dev.omega24.blockedit.wand.Wand;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Queue;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class WandArgument<C> extends CommandArgument<C, Wand> {

    WandArgument(boolean required, String name, String defaultValue, BiFunction<CommandContext<C>, String, List<String>> suggestionsProvider, ArgumentDescription defaultDescription) {
        super(required, name, new WandParser<>(), defaultValue, Wand.class, suggestionsProvider, defaultDescription);
    }


    public static <C> CommandArgument.Builder<C, Wand> newBuilder(String name) {
        return new WandArgument.Builder<>(name);
    }

    public static <C> CommandArgument<C, Wand> of(String name) {
        return WandArgument.<C>newBuilder(name).asRequired().build();
    }

    public static <C> CommandArgument<C, Wand> optional(String name) {
        return WandArgument.<C>newBuilder(name).asOptional().build();
    }

    public static <C> CommandArgument<C, Wand> optional(String name, String defaultValue) {
        return WandArgument.<C>newBuilder(name).asOptionalWithDefault(defaultValue).build();
    }

    public static class Builder<C> extends CommandArgument.Builder<C, Wand> {
        private Builder(String name) {
            super(Wand.class, name);
        }

        @Override
        public @NotNull CommandArgument<C, Wand> build() {
            return new WandArgument<>(isRequired(), getName(), getDefaultValue(), getSuggestionsProvider(), getDefaultDescription());
        }
    }

    public static class WandParser<C> implements ArgumentParser<C, Wand> {
        @Override
        public @NonNull ArgumentParseResult<Wand> parse(@NotNull CommandContext<C> commandContext, @NotNull Queue<String> inputQueue) {
            String input = inputQueue.peek();
            if (input == null) {
                return ArgumentParseResult.failure(new NoInputProvidedException(WandParser.class, commandContext));
            }

            Wand wand = WandManager.getById(input);
            if (wand == null) {
                return ArgumentParseResult.failure(new WandParseException(input));
            }

            inputQueue.remove();
            return ArgumentParseResult.success(wand);
        }

        @Override
        public @NonNull List<String> suggestions(@NonNull CommandContext<C> commandContext, @NonNull String input) {
            return WandManager.getAll().stream().map((wand) -> wand.getKey().getKey()).collect(Collectors.toList());
        }
    }

    public static class WandParseException extends Exception {
        private final String name;

        public WandParseException(String name) {
            this.name = name;
        }

        @Override
        public String getMessage() {
            return MiniMessage.miniMessage().stripTags(Lang.WAND_COMMAND_NOT_FOUND, Placeholder.unparsed("wand", name));
        }
    }
}
