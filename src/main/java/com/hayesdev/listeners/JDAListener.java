package com.hayesdev.listeners;

import com.hayesdev.MyPlugin;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Message;
import github.scarsz.discordsrv.dependencies.jda.api.entities.MessageReaction;
import github.scarsz.discordsrv.dependencies.jda.api.events.message.react.MessageReactionAddEvent;
import github.scarsz.discordsrv.dependencies.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.awt.*;

public class JDAListener extends ListenerAdapter {

    private final MyPlugin plugin;

    public JDAListener(MyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        System.out.println("LOLLLOL");
        String messageID = event.getMessageId();
        Message message = event.getChannel().getHistory().getMessageById(messageID);
        if (event.getChannel().getId().equals("" + MyPlugin.getInstance().getConfigFile().getString("admin-channel")) && message != null) {
            System.out.println("LOLLLOL2");
            for (MessageReaction currentReaction : message.getReactions()) {
                if (currentReaction.getReactionEmote().getAsCodepoints().equalsIgnoreCase("U+2705") && currentReaction.getCount() > 1) {
                    System.out.println("LOLLLOL3");
                    String description = message.getEmbeds().get(0).getDescription();
                    if (description != null) {
                        System.out.println("LOLLLOL4");
                        message.clearReactions().queue();
                        final EmbedBuilder builder = new EmbedBuilder()
                                .setTitle("Admin Call")
                                .setDescription("Currently being investigated")
                                .setColor(new Color(5154580));
                        message.editMessage(builder.build()).queue();
                        String sender = description.substring(description.indexOf("Sender: "));
                        Player player = Bukkit.getPlayer(sender);
                        if (player != null) {
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "CallAdmin" + ChatColor.GRAY + "]" + ChatColor.WHITE + " Your issue is being dealt with");
                        }
                        break;
                    }
                }
            }
        }
    }
}
