package me.a8kj7sea.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;

import lombok.NonNull;
import me.a8kj7sea.entity.AbstractCommand;
import me.a8kj7sea.manager.StaffChatManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class StaffChatCommand extends AbstractCommand {

    private final StaffChatManager staffChatManager;

    public StaffChatCommand(@NonNull final StaffChatManager manager) {
        super("staffchat");
        this.staffChatManager = manager;
    }

    @Override
    public void onCommand(@NonNull CommandSource commandSource, @NonNull String[] args) {
        if (!(commandSource instanceof Player)) {
            commandSource.sendMessage(Component.text("This command only for player usage !").color(NamedTextColor.RED));
            return;
        }

        Player player = (Player) commandSource;

        if (!player.hasPermission("staffchat.chat-notify")) {
            player.sendMessage(Component.text("You don't have enough permissions to execute this command !")
                    .color(NamedTextColor.RED));
            return;
        }

        if (args.length < 1) {
            player.sendMessage(
                    Component.text("Incorrect use , please rewrite command with this syntax " + " /staffchat <message>")
                            .color(NamedTextColor.RED));
            return;
        }

        String message = String.join(" ", args);

        this.staffChatManager.sendStaffMessage(message, player);
    }

}
