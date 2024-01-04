package me.a8kj7sea.entity;

import com.velocitypowered.api.command.CommandSource;

import lombok.NonNull;

public interface SubCommand {

    public String getLabel();

    default public String getPermission() {
        return null;
    }

    default public String getDescription() {
        return null;
    }

    default public String getUsage() {
        return null;
    }

    public void handle(@NonNull String args[],@NonNull CommandSource commandSource);
}
