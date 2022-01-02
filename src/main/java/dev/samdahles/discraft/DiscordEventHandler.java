package dev.samdahles.discraft;

import org.bukkit.Bukkit;

import dev.samdahles.discraft.embeds.DiscordChatEmbed;
import dev.samdahles.discraft.Core;

import java.awt.Color;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.md_5.bungee.api.ChatColor;

public class DiscordEventHandler extends ListenerAdapter {
	DiscordEventHandler() {}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		// Check if correct channel
		if(!event.getChannel().equals(Core.linkingChannel) || event.getAuthor().getAsTag() == Core.jda.getSelfUser().getAsTag()) return;
		
		// Delete bot message
		if(event.getAuthor().isBot()) {
			event.getMessage().delete();
			return;
		}
		
		Message msg = event.getMessage();
		// Replace message with embed
		event.getMessage().delete().queue();;
		new DiscordChatEmbed(event.getMember(), event).send();
		
		// Send it in minecraft
		User user = event.getAuthor();
		Member author = event.getGuild().getMember(user);
		ChatColor usernameColor = ChatColor.of(new Color(author.getColorRaw()));
		String chatContent = ChatColor.GRAY + "[ " + usernameColor + user.getAsTag() + ChatColor.GRAY + " ] " + ChatColor.WHITE + msg.getContentStripped();
		Core.logger.info(chatContent);
		Bukkit.getServer().broadcastMessage(chatContent);
	}
}
