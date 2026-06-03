package io.github.getjikr.test;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.NullMarked;

public class GiveDiamondSwordCommand {
    @NullMarked
    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("givesword")
                .requires(sender -> sender.getSender().isOp())
                .executes(ctx -> {
                    if (ctx.getSource().getExecutor() instanceof Player player) {
                        if (!(player.getInventory().contains(Material.DIAMOND_SWORD))) {
                            player.getInventory().addItem(ItemStack.of(Material.DIAMOND_SWORD));
                            player.sendMessage(Component.text("지급 완료"));
                        } else {
                            player.sendMessage(Component.text("이미 검이 있습니다"));
                        }
                    } else {
                        ctx.getSource().getExecutor().sendMessage(Component.text("!"));
                    }
                    return Command.SINGLE_SUCCESS;
                }).build();
    }
}
