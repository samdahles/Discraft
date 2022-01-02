package dev.samdahles.discraft;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import javax.security.auth.login.LoginException;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.managers.Presence;
import net.dv8tion.jda.api.entities.MessageChannel;

@SuppressWarnings("unused")
public class Core extends JavaPlugin implements Listener {
	public static Config config;
	public static JDA jda;
	public static Logger logger;
	public static boolean isRunning = false;
	public static ServerEventHandler serverEventHandler;
	public static CommandWatcher commandWatcher;
	public static MessageChannel linkingChannel;
	
	private static int currentActivityIndex = 0;
	
	private static void displayActivity() {
		Presence presence = Core.jda.getPresence();
		switch(Core.currentActivityIndex) {
		case 0:
			presence.setActivity(Activity.playing(Core.config.get("serverAddress")));
			break;
		case 1:
			int overallPlayerCount = Bukkit.getServer().getOfflinePlayers().length;
			presence.setActivity(Activity.playing(overallPlayerCount + " players have wandered this server!"));
			break;
		case 2:
			int onlinePlayerCount = Bukkit.getServer().getOnlinePlayers().size();
			int maxPlayerCount = Bukkit.getServer().getMaxPlayers();
			presence.setActivity(Activity.playing(onlinePlayerCount + " of " + maxPlayerCount + " players are online!"));
			break;
		case 3:
			if(Core.config.getBoolean("isWWWMapEnabled")) {
				presence.setActivity(Activity.watching("the map at " + Core.config.get("WWWMapAddress")));
			} else {
				Core.currentActivityIndex++;
				Core.displayActivity();
			}			
		default:
			Core.currentActivityIndex = 0;
			Core.displayActivity();
		}
	}
	
	
	@Override
	public void onEnable() {
		Core.logger = getLogger();
		Core.logger.info("Building config. . .");
		Core.buildConfig();
		Core.logger.info("Built config.");
		Core.serverEventHandler = new ServerEventHandler();
		Core.serverEventHandler.onEnable();
		Core.commandWatcher = new CommandWatcher();
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getServer().getPluginManager().registerEvents(Core.serverEventHandler, this);

		Bukkit.getPluginCommand("sendcoords").setExecutor(commandWatcher);
		Bukkit.getPluginCommand("reloadtoken").setExecutor(commandWatcher);		
		
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
	
	@Override
	public void onDisable() {
		Core.serverEventHandler.onDisable();
	}
	
	private static void updateLinkingChannel() throws IOException {
		String attemptedMessageChannelId = Core.config.get("minecraftChannel");
		MessageChannel attemptedMessageChannel;
		try {
			attemptedMessageChannel = Core.jda.getTextChannelById(attemptedMessageChannelId);
			if(attemptedMessageChannel == null) {
				Core.isRunning = false;
				throw new IOException("There is no channel with ID " + attemptedMessageChannelId + ".");
			}
		} catch(NumberFormatException e) {
			Core.isRunning = false;
			throw new IOException("There is no channel with ID " + attemptedMessageChannelId + ". You probably haven't filled it in.");
		}

		Core.linkingChannel = attemptedMessageChannel;
		Core.logger.info("Linking #" + Core.linkingChannel.getName());
	}
	
	public static void buildConfig() {
		try {
			Core.config = new Config("Discraft/config.yml");
			Core.jda = JDABuilder.createDefault(Core.config.get("botToken")).build();
			Core.jda.awaitReady();
			Core.updateLinkingChannel();
			jda.addEventListener(new DiscordEventHandler());
			Core.isRunning = true;
		} catch (LoginException | IllegalStateException e) {
			Core.logger.severe("The entered Discord token is incorrect. Be sure to set it in Discraft/config.yml and type /reloadtoken afterwards!");
			Core.isRunning = false;
		} catch (IOException e) {
			Core.logger.severe(e.getMessage().strip() + " Be sure to set it in Discraft/config.yml and type /reloadtoken afterwards!");
			Core.isRunning = false;
		} catch (InterruptedException e) {
			Core.isRunning = false;
			e.printStackTrace();
		}
	}
	
	public static void update() {
		if(!Core.isRunning) return;
		if(Core.config.getBoolean("showPresence") == false) {
			Core.jda.getPresence().setActivity(null);
		} else {
			Core.displayActivity();
			Core.currentActivityIndex++;
		}
	}
}
