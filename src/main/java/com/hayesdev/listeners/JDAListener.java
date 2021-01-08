package com.hayesdev.listeners;

import com.hayesdev.MyPlugin;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Message;
import github.scarsz.discordsrv.dependencies.jda.api.entities.MessageReaction;
import github.scarsz.discordsrv.dependencies.jda.api.events.message.react.MessageReactionAddEvent;
import github.scarsz.discordsrv.dependencies.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class JDAListener extends ListenerAdapter {

    private final MyPlugin plugin;

    public JDAListener(MyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        String messageID = event.getMessageId();
        Message message = event.getChannel().retrieveMessageById(messageID).complete();
        if (event.getChannel().getId().equals("" + MyPlugin.getInstance().getConfigFile().getString("admin-channel")) && message != null) {
            for (MessageReaction currentReaction : message.getReactions()) {
                if (currentReaction.getReactionEmote().getAsCodepoints().equalsIgnoreCase("U+2705") && currentReaction.getCount() > 1) {
                    String description = message.getEmbeds().get(0).getDescription();
                    if (description != null) {
                        int startingPoint = description.indexOf("\nStatus: ");
                        String description2 = description.substring(0, startingPoint) + "\nStatus: Currently being investigated";
                        message.clearReactions().queue();
                        final EmbedBuilder builder = new EmbedBuilder()
                                .setTitle("Admin Call")
                                .setDescription(description2)
                                .setColor(new Color(5154580));
                        message.editMessage(builder.build()).queue();
                        break;
                    }
                }
            }
        }
    }
}
