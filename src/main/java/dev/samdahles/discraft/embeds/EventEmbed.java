package dev.samdahles.discraft.embeds;

import java.awt.Color;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;

public class EventEmbed extends Embed {
	public EventEmbed(Player author, Color embedColor, String text) {
		super();
		UUID authorUUID = author.getUniqueId();
		eb.setColor(embedColor);
		eb.setDescription(text.strip().replaceAll("([_*`\n])+", ""));
		eb.setAuthor(author.getName(), "https://namemc.com/search?q=" + authorUUID.toString(), "https://crafatar.com/renders/head/" + authorUUID.toString().replaceAll("-", ""));
	}
	
	public EventEmbed(Tameable tameable, Color embedColor, String text) {
		super();
		Player author = (Player) tameable.getOwner();
		UUID authorUUID = author.getUniqueId();
		eb.setColor(embedColor);
		eb.setDescription(text.strip().replaceAll("([_*`\n])+", ""));
		eb.setAuthor(author.getName(), "https://namemc.com/search?q=" + authorUUID.toString(), "https://crafatar.com/renders/head/" + authorUUID.toString().replaceAll("-", ""));
	}
}
