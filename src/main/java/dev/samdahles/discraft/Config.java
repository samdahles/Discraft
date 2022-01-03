package dev.samdahles.discraft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class Config {
	private String relativePath;
	private String absolutePath;
	private File configFile;
	private Map<String, Object> config;
	private Yaml yaml;

	
	public Map<String, Object> getDefaultValues() {
		Map<String, Object> map = new HashMap<>();
		map.put("showPresence", true);
		map.put("showServerStatusEmbeds", true);
		map.put("minecraftChannel", "Discord Channel ID");
		map.put("botToken", "Discord Bot Token");
		map.put("serverAddress", "Minecraft Server Address");
		map.put("isWWWMapEnabled", false);
		map.put("WWWMapAddress", "Dynmap URL");
		return map;
	}
	
	Config(String path) throws IOException {
		DumperOptions options = new DumperOptions();
		options.setIndent(2);
		options.setPrettyFlow(true);
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		this.yaml = new Yaml(options);
		
		this.relativePath = path.replace("/", File.separator);
		this.absolutePath = new File(".")
				.getCanonicalPath() 
				+ File.separator 
				+ "plugins"
				+ File.separator
				+ this.relativePath;
		
		this.configFile = new File(this.absolutePath);
		
		if(this.configFile.isDirectory()) {
			throw new IOException(this.configFile.getName() + " is a directory");
		}
		
		this.createConfigIfNotExists();
		this.file2map();
	}
	
	public boolean isValid() {
		if(this.config != null) {
			List<String> defaultKeys = new ArrayList<String>(this.getDefaultValues().keySet());
			List<String> givenKeys = new ArrayList<String>(this.config.keySet());
			if(new HashSet<>(defaultKeys).equals(new HashSet<>(givenKeys))) {
				return true;
			}
		}
		Core.logger.warning("Config file is corrupted.");
		return false;	
	}
	
	public void fillDefault() {
		Core.logger.info("Building new config file. . .");
		this.config = this.getDefaultValues();
		this.map2file();
		Core.logger.warning("The newly built config file is filled with sample data. Fill it in accordingly at Discraft/config.yml.");
	}
	
	public void set(String key, boolean value) {
		this.config.put(key, value);
		this.map2file();
	}
	
	public void set(String key, String value) {
		this.config.put(key, value);
		this.map2file();
	}
	
	public void set(String key, int value) {
		this.config.put(key, value);
		this.map2file();
	}
	
	public boolean getBoolean(String key) {
		if(!this.isValid()) this.fillDefault();
		return (boolean) this.config.get(key);
	}
	
	public String get(String key) {
		if(!this.isValid()) this.fillDefault();
		return String.valueOf(this.config.get(key));
	}
	
	
	private void map2file() {
		try {
			yaml.dump(this.config, new FileWriter(this.absolutePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void file2map() {
		try {
			this.config = yaml.load(new FileInputStream(this.absolutePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	
	private boolean createConfigIfNotExists() throws IOException {
		if(this.configFile.exists()) {
			if(this.configFile.isDirectory()) { 
				throw new IOException(this.configFile.getName() + " is a directory");
			} else {
				return false;
			}
		}
	
		String pathNameOnly = this.absolutePath.replace(configFile.getName(), "");
		new File(pathNameOnly).mkdirs();
		this.fillDefault();
		return true;
	}
}
