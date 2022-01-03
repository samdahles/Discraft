package dev.samdahles.discraft;

import java.awt.Color;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import dev.samdahles.discraft.embeds.Embed;
import dev.samdahles.discraft.embeds.EventEmbed;
import dev.samdahles.discraft.embeds.MinecraftChatEmbed;
import dev.samdahles.discraft.embeds.ServerEmbed;
import net.md_5.bungee.api.ChatColor;

public class ServerEventHandler implements Listener {
	ServerEventHandler() {}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		new EventEmbed(event.getPlayer(), Embed.JOIN, event.getPlayer().getName() + " has joined the party!").send();
		event.setJoinMessage(ChatColor.of(Embed.PLAYER(event.getPlayer())) + event.getPlayer().getName() + ChatColor.WHITE + " has joined the party!");
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		new EventEmbed(event.getPlayer(), Embed.QUIT, event.getPlayer().getName() + " has left the server!").send();
		event.setQuitMessage(ChatColor.of(Embed.PLAYER(event.getPlayer())) + event.getPlayer().getName() + ChatColor.WHITE + " has left the server!");
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		String kickReason = event.getReason();
		String action = "kicked";
		new EventEmbed(event.getPlayer(), Embed.KICK, event.getPlayer().getName() + " has been " + action + " for \"" + kickReason + "\"").send();
		event.setLeaveMessage(ChatColor.of(Embed.PLAYER(event.getPlayer())) + event.getPlayer().getName() + ChatColor.WHITE + " has been " + action + " from the server for \"" + kickReason + "\"");
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		String deathMessage = event.getDeathMessage();
		if(deathMessage != null) {
			new EventEmbed(event.getEntity(), Embed.DEATH, deathMessage).send();
		}
		deathMessage = ChatColor.WHITE + deathMessage;
		deathMessage = deathMessage.replaceAll(event.getEntity().getName(), ChatColor.of(Embed.PLAYER(event.getEntity())) + event.getEntity().getName() + ChatColor.WHITE);
		if(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent lastDamageEvent = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
			Entity damager = lastDamageEvent.getDamager();
			if(damager instanceof Player) {
				deathMessage = deathMessage.replaceAll(damager.getName(), ChatColor.of(Embed.PLAYER((Player) damager)) + damager.getName() + ChatColor.WHITE);
			}
		}
		event.setDeathMessage(deathMessage);	
	}
	
	// TODO: Check if pet was killed
	public void onEntityDamage(EntityDamageEvent event) {}
	
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		new MinecraftChatEmbed(event.getPlayer(), event.getMessage()).send();
		Color nameColor = Embed.PLAYER(event.getPlayer());
		Bukkit.getServer().broadcastMessage(ChatColor.GRAY + "[ " + ChatColor.of(nameColor) + event.getPlayer().getName() + ChatColor.GRAY + " ] " + ChatColor.WHITE + event.getMessage());
	}
	
	public void onEnable() {
		if(Core.config.getBoolean("showServerStatusEmbeds")) {
			new ServerEmbed("Server is online!").send();
		}
	}
	
	// TODO: Disable linkingChannel talking permissions
	public void onDisable() {
		if(Core.config.getBoolean("showServerStatusEmbeds")) {
			new ServerEmbed("Server shutting down!").send();
		}
	}
}
