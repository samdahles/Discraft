package dev.samdahles.discraft.embeds;

import dev.samdahles.discraft.Core;

public class ServerEmbed extends Embed {
	public ServerEmbed(String text) {
		super();
		eb.setColor(Embed.SERVER_EVENT);
		eb.setDescription(text);
		long linkingChannelID = Core.linkingChannel.getIdLong();
		String guildIconUrl = Core.jda.getTextChannelById(linkingChannelID).getGuild().getIconUrl();
		if(guildIconUrl == null) {
			guildIconUrl = Core.jda.getSelfUser().getEffectiveAvatarUrl();
		}

		if(Core.config.getBoolean("isWWWMapEnabled")) {
			eb.setAuthor("Server", Core.config.get("WWWMapAddress"), guildIconUrl);
		} else {
			eb.setAuthor("Server", null, guildIconUrl);
		}
	}
}
