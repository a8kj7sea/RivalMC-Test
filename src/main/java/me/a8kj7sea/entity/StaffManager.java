package me.a8kj7sea.entity;

import java.util.List;
import java.util.Set;

import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;

import lombok.NonNull;

public interface StaffManager {

    @Deprecated
    public Set<Player> players();

    public void sendStaffMessage(@NonNull String message,@NonNull Player sender);

    public List<Player> toggledPlayers();

    public void handleToggleMessage(@NonNull PlayerChatEvent event);
}
