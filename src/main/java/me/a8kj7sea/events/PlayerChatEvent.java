package me.a8kj7sea.events;

import com.google.common.eventbus.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent.ChatResult;

import lombok.Getter;
import lombok.NonNull;
import me.a8kj7sea.manager.StaffChatManager;
@Deprecated
public class PlayerChatEvent {

    private @Getter final StaffChatManager manager;

    public PlayerChatEvent(@NonNull final StaffChatManager manager) {
        this.manager = manager;
    }

    @Subscribe
    public void onPlayerChatEvent(@NonNull com.velocitypowered.api.event.player.PlayerChatEvent event) {
        this.manager.handleToggleMessage(event);
        event.setResult(ChatResult.denied());
    }
}
