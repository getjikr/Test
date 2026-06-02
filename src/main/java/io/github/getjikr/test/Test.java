package io.github.getjikr.test;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

public final class Test extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("PO");
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands ->
                commands.registrar().register(this.giveDiamondCommand()));
    }

    private LiteralCommandNode<CommandSourceStack> giveDiamondCommand() {
        return Commands.literal("givediamond")
                .then(Commands.argument("amount", IntegerArgumentType.integer(1, 64))
                        .executes(this::giveDiamondLogic)
                ).build();
    }

    private int giveDiamondLogic(@NonNull CommandContext<CommandSourceStack> ctx) {
        if (ctx.getSource().getExecutor() instanceof Player player) {
            int amount = ctx.getArgument("amount", Integer.class);
            ItemStack diamonds = new ItemStack(Material.DIAMOND, amount);
            player.getInventory().addItem(diamonds);
            player.sendMessage(Component.text(amount + "개의 다이아몬드를 받았습니다").color(NamedTextColor.GREEN));
        }
        return Command.SINGLE_SUCCESS;
    }
}