package me.a8kj7sea.command;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import com.google.common.collect.Lists;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;

import lombok.NonNull;
import me.a8kj7sea.command.subs.ListSubCommand;
import me.a8kj7sea.command.subs.ToggleSubCommand;
import me.a8kj7sea.entity.AbstractCommand;
import me.a8kj7sea.entity.SubCommand;
import me.a8kj7sea.staffchat.StaffChatPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import static net.kyori.adventure.text.Component.text;

public class StaffCommand extends AbstractCommand {

    private final List<SubCommand> setOfSubCommands = Lists.newArrayList();
    private final StaffChatPlugin staffChatPlugin;

    public StaffCommand(@NonNull final StaffChatPlugin staffChatPlugin) {

        super("staff");
        this.staffChatPlugin = staffChatPlugin;
        makeSureIsRegistered();
    }

    public void init() {
        setOfSubCommands.add(new ListSubCommand(this.staffChatPlugin));
        makeSureIsRegistered();
    }

    private void makeSureIsRegistered() {
        setOfSubCommands.add(new ToggleSubCommand(this.staffChatPlugin));
    }

    @Override
    public void onCommand(@NonNull CommandSource commandSource, @NonNull String[] args) {

        if (!(commandSource instanceof Player)) {
            commandSource.sendMessage(Component.text("This command only for player usage !").color(NamedTextColor.RED));
            return;
        }

        Player sender = (Player) commandSource;

        if (!sender.hasPermission("staffchat.command-use")) {
            sender.sendMessage(Component.text("You don't have enough permissions to execute this command !")
                    .color(NamedTextColor.RED));
            return;
        }

        if (args.length != 1 || args[0].equalsIgnoreCase("help")) {
            sendHelpMessage(sender, setOfSubCommands);
            return;
        } else {
            handleSubs(setOfSubCommands, sender, args);
        }

    }

    private void sendHelpMessage(@NonNull CommandSource sender, @NonNull Collection<SubCommand> subCommands) {

        Collection<SubCommand> subs = Arrays.asList(new ListSubCommand(this.staffChatPlugin),
                new ToggleSubCommand(this.staffChatPlugin));
        sender.sendMessage(
                Component.text("                     HELP MENU                         \n").color(NamedTextColor.AQUA));
        for (SubCommand subCommand : subs) {
            sender.sendMessage(getSubCommandMessageComponent(subCommand));
            subCommands.remove(subCommand);
        }
        sender.sendMessage(getSubCommandMessageComponent(new SubCommand() {

            @Override
            public String getLabel() {
                return "<message>";
            }

            @Override
            public String getUsage() {
                return "/staffchat " + getLabel();
            }

            @Override
            public String getDescription() {
                return "To send message to online staff";
            }

            @Override
            public void handle(@NonNull String[] args, @NonNull CommandSource commandSource) {
                return;
            }

        }));
        sender.sendMessage(Component.text("                                                   "));

    }

    private Component getSubCommandMessageComponent(SubCommand subCommand) {
        return Component.text()
                .append(text("» ").color(NamedTextColor.DARK_GRAY))
                .append(text(subCommand.getUsage()).color(NamedTextColor.GOLD))
                .append(text(" - ").color(NamedTextColor.GRAY))
                .append(text(subCommand.getDescription()).color(NamedTextColor.AQUA)).asComponent();
    }
}
