package dev.samdahles.discraft.embeds;

import java.util.UUID;

import org.bukkit.entity.Player;

public class MinecraftChatEmbed extends Embed {
	public MinecraftChatEmbed(Player author, String text) {
		super();
		UUID authorUUID = author.getUniqueId();
		eb.setColor(Embed.PLAYER(author));
		eb.setDescription(text.strip().replaceAll("([_*`\n])+", ""));
		eb.setAuthor(author.getName(), "https://namemc.com/search?q=" + authorUUID.toString(), "https://crafatar.com/renders/head/" + authorUUID.toString().replaceAll("-", ""));
	}
}
