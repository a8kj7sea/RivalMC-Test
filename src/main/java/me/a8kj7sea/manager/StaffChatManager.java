package me.a8kj7sea.manager;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent.ChatResult;
import com.velocitypowered.api.proxy.Player;

import lombok.Getter;
import lombok.NonNull;
import me.a8kj7sea.entity.StaffManager;
import me.a8kj7sea.staffchat.StaffChatPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class StaffChatManager implements StaffManager {

    @Getter
    private final StaffChatPlugin staffChatPlugin;
    private final List<Player> toggledPlayers = Lists.newArrayList();

    public StaffChatManager(@NonNull final StaffChatPlugin staffChatPlugin) {
        this.staffChatPlugin = staffChatPlugin;
    }

    @Deprecated
    @Override
    public Set<Player> players() {
        Set<Player> staff = Sets.newHashSet();

        for (Player player : staffChatPlugin.getProxyServer().getAllPlayers()) {

            if (player.hasPermission("staffchat.chat-notify")) {
                staff.add(player);
            }
        }

        return staff;
    }

    @Override
    public void sendStaffMessage(@NonNull String message, @NonNull Player sender) {
        players().forEach(player -> player.sendMessage(

                Component.text("[Staff-Chat] ").color(NamedTextColor.RED).append(
                        Component.text(sender.getUsername()).color(NamedTextColor.DARK_GREEN)).append(
                                Component.text(" : ").color(NamedTextColor.DARK_GRAY))
                        .append(
                                Component.text(message).color(NamedTextColor.WHITE))));
    }

    public List<Player> toggledPlayers() {
        return toggledPlayers;
    }

    @Override
    public void handleToggleMessage(@NonNull PlayerChatEvent event) {
        if (event.getPlayer() == null || event.getMessage() == null)
            return;

        Player sender = (Player) event.getPlayer();

        if (!toggledPlayers.contains(sender)) {
            return;
        }

        sendStaffMessage(event.getMessage(), sender);
        event.setResult(ChatResult.denied());
    }

}
