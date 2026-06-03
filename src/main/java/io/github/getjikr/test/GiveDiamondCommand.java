package io.github.getjikr.test;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.CompletableFuture;

public class GiveDiamondCommand {
    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("givediamond")
                .requires(sender -> sender.getSender().isOp())
                .then(Commands.argument("amount", IntegerArgumentType.integer(1,64))
                        .suggests(GiveDiamondCommand::getSuggestions)
                        .executes(GiveDiamondCommand::executeLogic)
                ).build();
    }
    private static int executeLogic(CommandContext<CommandSourceStack> ctx) {
        if (ctx.getSource().getExecutor() instanceof Player player) {
            int amount = ctx.getArgument("amount", Integer.class);
            ItemStack diamonds = new ItemStack(Material.DIAMOND, amount);
            player.getInventory().addItem(diamonds);
            player.sendMessage(Component.text(amount + "개의 다이아몬드를 받았습니다.").color(NamedTextColor.GREEN));
        }
        return Command.SINGLE_SUCCESS;
    }
    private static CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> ctx, SuggestionsBuilder builder) {
        builder.suggest(64);
        return builder.buildFuture();
    }
}
