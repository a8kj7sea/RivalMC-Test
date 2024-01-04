package me.a8kj7sea.entity;

import java.util.Collection;
import static net.kyori.adventure.text.Component.text;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

import lombok.Getter;
import lombok.NonNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public abstract class AbstractCommand implements SimpleCommand {

    @Getter
    private final String command;

    public AbstractCommand(@NonNull String command) {
        this.command = command;
    }

    public abstract void onCommand(@NonNull CommandSource commandSource, @NonNull String args[]);

    @Override
    public void execute(@NonNull Invocation invocation) {
        if (invocation.source() == null)
            return;
        onCommand(invocation.source(), invocation.arguments());
    }

    @Deprecated
    protected void sendSubsHelpMessage(@NonNull CommandSource sender, @NonNull Collection<SubCommand> subCommands) {

        Collection<SubCommand> subs = subCommands;
        sender.sendMessage(Component.text("                                                   "));
        for (SubCommand subCommand : subs) {
            sender.sendMessage(getSubCommandMessageComponent(subCommand));
            subCommands.remove(subCommand);
        }
        sender.sendMessage(Component.text("                                                   "));

    }

    @Deprecated
    private Component getSubCommandMessageComponent(SubCommand subCommand) {
        return Component.text()
                .append(text("» ").color(NamedTextColor.DARK_GRAY))
                .append(text(subCommand.getUsage()).color(NamedTextColor.GOLD))
                .append(text(" - ").color(NamedTextColor.GRAY))
                .append(text(subCommand.getDescription()).color(NamedTextColor.AQUA)).asComponent();
    }

    protected void handleSubs(@NonNull Collection<SubCommand> subCommands, @NonNull Player player,
            @NonNull String[] args) {

        for (SubCommand subCommand : subCommands) {

            if (!args[0].equalsIgnoreCase(subCommand.getLabel())) {
                return;
            }

            String permission = subCommand.getPermission();

            if (permission == null) {

                subCommand.handle(args, player);
                return;
            } else {

                if (!player.hasPermission(subCommand.getPermission())) {
                    player.sendMessage(
                            Component.text("You don't have enough permissions to execute this sub-command !"));
                    return;
                } else {

                    subCommand.handle(args, player);
                    return;
                }
            }

        }

    }

}
