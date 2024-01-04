package me.a8kj7sea.command.subs;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;

import lombok.Getter;
import lombok.NonNull;
import me.a8kj7sea.entity.SubCommand;
import me.a8kj7sea.staffchat.StaffChatPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class ToggleSubCommand implements SubCommand {

    @Getter
    private final StaffChatPlugin staffChatPlugin;

    public enum ToggleState {
        JOIN, LEAVE;
    }

    public ToggleSubCommand(final @NonNull StaffChatPlugin staffChatPlugin) {
        this.staffChatPlugin = staffChatPlugin;
    }

    @Override
    public String getDescription() {
        return "to join/left staff chat toggled mode .";
    }

    @Override
    public String getUsage() {
        return "/staff toggle";
    }

    @Override
    public String getLabel() {
        return "toggle";
    }

    @Override
    public void handle(@NonNull String[] args, @NonNull CommandSource commandSource) {

        if (!(commandSource instanceof Player))
            return;

        Player player = (Player) commandSource;

        if (args.length > 1 && args[0].equalsIgnoreCase(getLabel())) {
            player.sendMessage(
                    Component.text("Incorrect use , please rewrite command with this syntax " + getUsage())
                            .color(NamedTextColor.RED));
            return;
        } else {

            handleToggleLogic(getPlayerToggleState(player), player);
        }
    }

    private ToggleState getPlayerToggleState(@NonNull Player player) {
        return this.staffChatPlugin.getManager().toggledPlayers().contains(player) ? ToggleState.LEAVE
                : ToggleState.JOIN;
    }

    private void handleToggleLogic(@NonNull ToggleState toggleState, @NonNull Player player) {
        if (toggleState == ToggleState.JOIN) {
            this.staffChatPlugin.getManager().toggledPlayers().add(player);
            player.sendMessage(
                    Component.text("You have been join toggled mode successfully !").color(NamedTextColor.GREEN));
        } else if (toggleState == ToggleState.LEAVE) {
            this.staffChatPlugin.getManager().toggledPlayers().remove(player);
            player.sendMessage(
                    Component.text("You have been left toggled mode successfully !").color(NamedTextColor.GREEN));
        }
    }

}
