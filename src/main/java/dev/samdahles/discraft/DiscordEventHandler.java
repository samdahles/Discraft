package dev.samdahles.discraft;

import java.awt.Color;

import org.bukkit.Bukkit;

import dev.samdahles.discraft.embeds.DiscordChatEmbed;
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
		if(!event.getChannel().equals(Core.linkingChannel) || event.getAuthor().getAsTag() == Core.jda.getSelfUser().getAsTag()) return;
		
		if(event.getAuthor().isBot()) {
			event.getMessage().delete();
			return;
		}
		
		Message msg = event.getMessage();
		event.getMessage().delete().queue();;
		new DiscordChatEmbed(event.getMember(), event).send();
		
		User user = event.getAuthor();
		Member author = event.getGuild().getMember(user);
		ChatColor usernameColor = ChatColor.of(new Color(author.getColorRaw()));
		String chatContent = ChatColor.GRAY + "[ " + usernameColor + user.getAsTag() + ChatColor.GRAY + " ] " + ChatColor.WHITE + msg.getContentStripped();
		Core.logger.info(chatContent);
		Bukkit.getServer().broadcastMessage(chatContent);
	}
}
