package dev.samdahles.discraft.embeds;

import dev.samdahles.discraft.Core;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DiscordChatEmbed extends Embed {
	public DiscordChatEmbed(Member sender, MessageReceivedEvent originalMessageEvent) {
		super();
		eb.setColor(sender.getColor());
		eb.setDescription(originalMessageEvent.getMessage().getContentRaw());
		eb.setAuthor(sender.getEffectiveName(), null, sender.getEffectiveAvatarUrl());
	}
}
