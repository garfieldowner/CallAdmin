package com.hayesdev.listeners;

import com.hayesdev.MyPlugin;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Message;
import github.scarsz.discordsrv.dependencies.jda.api.entities.MessageReaction;
import github.scarsz.discordsrv.dependencies.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
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
        Message message = event.getChannel().getHistory().getMessageById(event.getMessageId());
        if (event.getChannel().getId().equals("" + MyPlugin.getInstance().getConfigFile().getInt("admin-channel"))) {
            for (MessageReaction currentReaction : message.getReactions()) {
                if (currentReaction.getReactionEmote().getAsCodepoints().equalsIgnoreCase("U+2705") && currentReaction.getCount() > 1) {
                    String description = message.getEmbeds().get(0).getDescription();
                    if (description != null) {
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
