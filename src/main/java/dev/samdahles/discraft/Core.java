package dev.samdahles.discraft;

import org.bukkit.event.Listener;

import net.dv8tion.jda.api.JDA;

public class Core implements Listener {
	public static Config config;
	public static JDA jda;
	
	public static void main(String[] args) {
		config = new Config("Discraft/config.yml");
	}
	
	public static void update() {
		config.refresh();
		
		if(config.get("showPresence") == false) {
			
		}
	}
	
}
