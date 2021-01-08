package com.hayesdev.commands;

import com.hayesdev.MyPlugin;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;

public class CallAdmin implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player && cmd.getName().equalsIgnoreCase("calladmin")) {
            if (args.length != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("Message: " + String.join(" ", args));
                sb.append("\nSender: " + sender.getName());
                sb.append("\nStatus: Open");
                final EmbedBuilder builder = new EmbedBuilder()
                        .setTitle("Admin Call")
                        .setDescription(sb.toString())
                        .setColor(new Color(5154580));
                TextChannel tc = DiscordSRV.getPlugin().getJda().getTextChannelById(MyPlugin.getInstance().getConfig().getString("admin-channel"));
                if (tc != null) {
                    tc.sendMessage(builder.build()).queue(message -> message.addReaction("U+2705").queue());
                    return true;
                } else {
                    System.out.println("[CallAdmin] You did not set the admin-channel in the config!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You did not stipulate any arguments!");
                return false;
            }
        } else {
            System.out.println("You must be a Player to use this command!");
            return false;
        }

    return false;

    }
}