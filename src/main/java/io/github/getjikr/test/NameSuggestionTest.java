package io.github.getjikr.test;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

import java.util.List;

public class NameSuggestionTest {
    public static LiteralCommandNode<CommandSourceStack> constructStringSuggestion() {
        final List<String> name = List.of("Alex", "Andrew", "Stephanie", "Shopie", "Elijah");
        return Commands.literal("selectname")
                .then(Commands.argument("name", StringArgumentType.word())
                        .suggests((ctx, builder) -> {
                            name.stream()
                                    .filter(entry -> entry.toLowerCase().startsWith(builder.getRemainingLowerCase()))
                                    .forEach(builder::suggest);
                            return builder.buildFuture();
                        })
                ).build();
    }
}
