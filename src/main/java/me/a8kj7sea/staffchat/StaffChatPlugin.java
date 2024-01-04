package me.a8kj7sea.staffchat;

import java.util.logging.Logger;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

import lombok.Getter;
import lombok.NonNull;
import me.a8kj7sea.command.StaffChatCommand;
import me.a8kj7sea.command.StaffCommand;
import me.a8kj7sea.manager.StaffChatManager;

@Plugin(name = "StaffChat-Plugin", id = "StaffChatPlugin", version = "0.0.1", authors = "a8kj", description = "A simple plugin that maked to rivalmc server as proof or developer test")
public class StaffChatPlugin {

    @Getter
    private final Logger logger;
    @Getter
    public final ProxyServer proxyServer;
    private @Getter final StaffChatManager manager;

    @Inject
    public StaffChatPlugin(@NonNull ProxyServer proxyServer, @NonNull Logger logger) {
        this.logger = logger;
        this.proxyServer = proxyServer;
        this.manager = new StaffChatManager(this);
    }

    @Subscribe
    public void onProxyInitialize(@NonNull ProxyInitializeEvent event) {
        CommandManager commandManager = this.proxyServer.getCommandManager();
        CommandMeta staffCommandMeta = commandManager.metaBuilder("staff")
                .plugin(this)
                .build();

        CommandMeta staffChatCommandMeta = commandManager.metaBuilder("staffchat")
                .aliases("sc", "chatstaff")
                .plugin(this)
                .build();

        StaffCommand staffCommand = new StaffCommand(this);
        staffCommand.init();
        commandManager.register(staffCommandMeta, staffCommand);
        commandManager.register(staffChatCommandMeta, new StaffChatCommand(this.getManager()));
        logger.info("Staffchat plugin enabled succesfully !");
    }

    @Subscribe
    public void onPlayerChatEvent(@NonNull com.velocitypowered.api.event.player.PlayerChatEvent event) {
        this.manager.handleToggleMessage(event);
    }

}
