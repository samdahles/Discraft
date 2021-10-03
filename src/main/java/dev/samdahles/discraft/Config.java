package dev.samdahles.discraft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

@SuppressWarnings("unused")
public class Config {
	private Yaml yaml = new Yaml();
	private String relativePath;
	private String absolutePath;
	private FileWriter yamlFileWriter;
	private FileInputStream yamlFileInputStream;
	private Map<String, Object> config;
	
	Config(String relativePath) {
		this.relativePath = relativePath.replace("/", File.separator);
		try {
			this.absolutePath = new File(".").getCanonicalPath() + File.separator + this.relativePath;
			yamlFileWriter = new FileWriter(this.absolutePath);
			yamlFileInputStream = new FileInputStream(this.absolutePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		config = yaml.load(yamlFileInputStream);
	}
		
	public String get(String key) {
		return (String) config.get(key);
	}
	
	public boolean getBoolean(String key) {
		return (boolean) config.get(key);
	}
	
	public int getInt(String key) {
		return (int) config.get(key);
	}
	
	public boolean set(String key, String value) throws IOException {
		// Sets config entry and refreshes the YAML file. Returns true if key exists, false if not
		if(config.containsKey(key)) {
			config.put(key, value);
			yaml.dump(config, yamlFileWriter);
			return true;
		}
		return false;
	}
	
	public void refresh() {
		try {
			yamlFileInputStream = new FileInputStream(this.absolutePath);
			yamlFileWriter = new FileWriter(this.absolutePath);
		} catch (IOException e) {
			
		}
		yaml.load(yamlFileInputStream);
	}
	
	public void createDefault() {
		config.putIfAbsent("showPresence", true);
		config.putIfAbsent("showServerStatusEmbeds", true);
		config.putIfAbsent("minecraftChannel", "PASTE_YOUR_DISCORD_CHANNEL_HERE");
		config.putIfAbsent("botToken", "PASTE_YOUR_DISCORD_BOT_TOKEN_HERE");
		config.putIfAbsent("serverAddress", "PASTE_YOUR_SERVER_ADDRESS_HERE");
	}
	
}
