package io.github.getjikr.test;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.FloatArgumentType;
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
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class Test extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("PO");
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(this.giveDiamondCommand());
            commands.registrar().register(this.setFlySpeedCommand());
        });

    }

    private LiteralCommandNode<CommandSourceStack> giveDiamondCommand() {
        return Commands.literal("givediamond")
                .then(Commands.argument("amount", IntegerArgumentType.integer(1, 64))
                        .executes(this::giveDiamondLogic)
                ).build();
    }

    private LiteralCommandNode<CommandSourceStack> setFlySpeedCommand() {
        return Commands.literal("flyspeed")
                .then(Commands.argument("speed", FloatArgumentType.floatArg(0,1.0f))
                        .executes(ctx -> {
                            if (!(ctx.getSource().getExecutor() instanceof Player player)) {

                                return Command.SINGLE_SUCCESS;
                            }
                            float speed = ctx.getArgument("speed", Float.class);
                            player.setFlySpeed(speed);
                            player.sendMessage(Component.text("플레이어의 플라이 속도가 " + speed + "로 설정되었습니다.")
                                    .color(NamedTextColor.GOLD));
                            return Command.SINGLE_SUCCESS;
                        })
                ).build();
    }

    private int giveDiamondLogic(CommandContext<CommandSourceStack> ctx) {
        if (ctx.getSource().getExecutor() instanceof Player player) {
            int amount = ctx.getArgument("amount", Integer.class);
            ItemStack diamonds = new ItemStack(Material.DIAMOND, amount);
            player.getInventory().addItem(diamonds);
            player.sendMessage(Component.text(amount + "개의 다이아몬드를 받았습니다").color(NamedTextColor.GREEN));
        }
        return Command.SINGLE_SUCCESS;
    }
}