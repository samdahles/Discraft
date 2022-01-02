package dev.samdahles.discraft.embeds;

import java.io.File;
import java.time.Instant;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import dev.samdahles.discraft.Core;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.Color;

public abstract class Embed {
	protected EmbedBuilder eb;
	private final File serverIcon = new File("." + File.separatorChar + "server-icon.png");

	public static final Color SERVER_EVENT = new Color(34, 38, 36);
	public static final Color OP = new Color(52, 235, 201);
	public static final Color NON_OP = new Color(52, 235, 110);
	public static final Color DEATH = new Color(199, 32, 65);
	public static final Color COORDINATE = new Color(212, 192, 106);
	public static final Color JOIN = new Color(38, 143, 181);
	public static final Color QUIT = new Color(33, 101, 191);
	public static final Color KICK = new Color(77, 8, 28);
	
	public static final Color PLAYER(Player player) {
		if(player.isOp()) return Embed.OP;
		return Embed.NON_OP;
	}
	
	Embed() {
		this.eb = new EmbedBuilder();
	}
	
	public boolean send() {
		if(!Core.isRunning) return false;
		try {
			this.eb.setTimestamp(Instant.now());
			if(serverIcon.exists()) {
				this.eb.setFooter(Core.config.get("serverAddress"), "attachment://serverIcon.png");
				Core.linkingChannel.sendMessageEmbeds(this.eb.build()).addFile(serverIcon, "serverIcon.png").queue();
			} else {
				this.eb.setFooter(Core.config.get("serverAddress"));
				Core.linkingChannel.sendMessageEmbeds(this.eb.build()).queue();
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}		
		return true;
	}
}
