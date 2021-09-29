package dev.samdahles.discraft;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
	private FileConfiguration config;
	private String relativePath;
	
	Config(String relativePath) {
		this.relativePath = relativePath;
		try {
			config.load(relativePath);
		} catch (IOException | InvalidConfigurationException e) {
			this.createDefault();
		}
	}
	
	public Object get(String key) {
		return config.get(key);
	}
	
	public boolean getBoolean(String key) {
		return config.getBoolean(key);
	}
	
	public int getInt(String key) {
		return config.getInt(key);
	}
	
	public boolean set(String key, String value) throws IOException {
		// Sets config entry and refreshes the Config object. Returns true if key exists, false if not
		if(config.contains(key)) {
			config.set(key, value);
			// ? config.options().copyDefaults(true);
			config.save(this.relativePath);
			this.refresh();
			return true;
		}
		return false;
	}
	
	public void refresh() {
		try {
			config.load(relativePath);
		} catch (IOException | InvalidConfigurationException e) {
			this.createDefault();
		}
	}
	
	
	private void createDefault() {
		config.addDefault("showPresence", true);
		config.addDefault("showServerStatusEmbeds", true);
		config.addDefault("minecraftChannel", "PASTE_YOUR_DISCORD_CHANNEL_HERE");
		config.addDefault("botToken", "PASTE_YOUR_DISCORD_BOT_TOKEN_HERE");
	}
	

	
}
