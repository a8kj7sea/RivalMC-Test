package me.a8kj7sea.command.subs;

import static net.kyori.adventure.text.Component.text;

import java.util.Collection;

import com.google.common.collect.Sets;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;

import lombok.Getter;
import lombok.NonNull;
import me.a8kj7sea.entity.SubCommand;
import me.a8kj7sea.staffchat.StaffChatPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent.Builder;
import net.kyori.adventure.text.format.NamedTextColor;

public class ListSubCommand implements SubCommand {

    @Getter
    private final StaffChatPlugin staffChatPlugin;

    public ListSubCommand(final @NonNull StaffChatPlugin staffChatPlugin) {
        this.staffChatPlugin = staffChatPlugin;
    }

    @Override
    public String getDescription() {
        return "to provide staff players list .";
    }

    @Override
    public String getUsage() {
        return "/staff list";
    }

    @Override
    public String getLabel() {
        return "list";
    }

    @Override
    public void handle(@NonNull String[] args, @NonNull CommandSource commandSource) {
        if (!(commandSource instanceof Player)) {
            return;
        }

        Player player = (Player) commandSource;

        if (args.length > 1 && args[0].equalsIgnoreCase(getLabel())) {
            player.sendMessage(
                    Component.text("Incorrect use , please rewrite command with this syntax " + getUsage())
                            .color(NamedTextColor.RED));
            return;
        } else {
            player.sendMessage(getStaffListAsComponent(staffPlayers()));
        }
    }

    private Collection<Player> staffPlayers() {
        Collection<Player> players = Sets.newHashSet();

        for (Player player : staffChatPlugin.getProxyServer().getAllPlayers()) {

            if (player.hasPermission("staffchat.chat-notify")) {
                players.add(player);
            }
        }
        return players;
    }

    private Component getStaffListAsComponent(Collection<Player> players) {
        int index = 1;
        Builder compentBuilder = text();
        compentBuilder.append(text("\n             ONLINE STAFF LIST                   \n").color(NamedTextColor.DARK_RED));
        for (Player player : players) {

            compentBuilder.append(text("\n#").color(NamedTextColor.DARK_GRAY))
                    .append(text(" " + index).color(NamedTextColor.GOLD)).append(text(" "))
                    .append(text(player.getUsername()).color(NamedTextColor.AQUA)).append(text("\n"));
            index++;

        }
        compentBuilder.append(text("                                 \n"));
        return compentBuilder.asComponent();

    }
}
