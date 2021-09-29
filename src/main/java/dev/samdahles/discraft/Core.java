package dev.samdahles.discraft;

import javax.security.auth.login.LoginException;

import org.bukkit.event.Listener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Core implements Listener {
	public static Config config;
	public static JDA jda;
	
	public static void main(String[] args) throws LoginException {
		config = new Config("Discraft/config.yml");
		JDA jda = JDABuilder.createDefault(config.get("botToken")).build();
	}
	
	public static void update() {
		config.refresh();
		
		if(config.getBoolean("showPresence") == false) {
			
		} else {

		}
	}
	
}
