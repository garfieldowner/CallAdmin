package com.hayesdev.listeners;

import com.hayesdev.MyPlugin;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.*;
import github.scarsz.discordsrv.util.DiscordUtil;

public class DiscordSRVListener {

    private final MyPlugin plugin;

    public DiscordSRVListener(MyPlugin plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void discordReadyEvent(DiscordReadyEvent event) {
        // Example of using JDA's events
        // We need to wait until DiscordSRV has initialized JDA, thus we're doing this inside DiscordReadyEvent
        DiscordUtil.getJda().addEventListener(new JDAListener(plugin));
        System.out.println("[CallAdmin] JDAListener loaded!");
    }

}
