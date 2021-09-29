package dev.samdahles.discraft;

import javax.security.auth.login.LoginException;

import org.bukkit.event.Listener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

@SuppressWarnings("unused")
public class Core implements Listener {
	public static Config config;
	public static JDA jda;
	
	public static void main(String[] args) throws LoginException {
		config = new Config("Discraft/config.yml");
		JDA jda = JDABuilder.createDefault(config.get("botToken")).build();
		
		Thread updateThread = new Thread(() -> {
			while(true) {
				Core.update();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		updateThread.start();
	}
	
	public static void update() {
		config.refresh();
		
		if(config.getBoolean("showPresence") == false) {
			jda.getPresence().setActivity(null);
		} else {
			if(jda.getPresence().getActivity() != Activity.playing(config.get("serverAddress"))){
				jda.getPresence().setActivity(Activity.playing(config.get("serverAddress")));
			}
		}
	}
	
}
