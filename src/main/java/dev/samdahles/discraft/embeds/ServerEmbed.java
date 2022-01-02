package dev.samdahles.discraft.embeds;

import dev.samdahles.discraft.Core;

public class ServerEmbed extends Embed {
	public ServerEmbed(String text) {
		super();
		eb.setColor(Embed.SERVER_EVENT);
		eb.setDescription(text);
		if(Core.config.getBoolean("isWWWMapEnabled")) {
			eb.setAuthor("Server", Core.config.get("WWWMapAddress"), Core.jda.getSelfUser().getEffectiveAvatarUrl());
		} else {
			eb.setAuthor("Server", null, Core.jda.getSelfUser().getEffectiveAvatarUrl());
		}
	}
}
