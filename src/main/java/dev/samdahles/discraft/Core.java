package dev.samdahles.discraft;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.security.auth.login.LoginException;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

@SuppressWarnings("unused")
public class Core extends JavaPlugin implements Listener {
	public static Config config;
	public static JDA jda;
	
	@Override
	public void onEnable() {
		try {
			config = new Config("Discraft/config.yml");
			JDA jda = JDABuilder.createDefault(config.get("botToken")).build();
		} catch (LoginException | URISyntaxException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		Thread updateThread = new Thread(() -> {
			while(true) {
				Core.update();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
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
