package dev.samdahles.discraft;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import dev.samdahles.discraft.embeds.Embed;
import dev.samdahles.discraft.embeds.EventEmbed;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class CommandWatcher implements CommandExecutor {
	CommandWatcher() {
		
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String cmd, @NotNull String[] args) {
		if(sender instanceof Player) {
			// Non-console commands
			Player player = (Player) sender;
			if(cmd.equalsIgnoreCase("sendcoords")) {
				Location playerLocation = player.getLocation();
				int X = (int) playerLocation.getX();
				int Y = (int) playerLocation.getY();
				int Z = (int) playerLocation.getZ();

				EventEmbed coordEmbed = new EventEmbed(player, Embed.COORDINATE, player.getName() + " is currently at " + X + " " + Y + " " + Z);
				if(Core.config.getBoolean("isWWWMapEnabled")) {
					coordEmbed = new EventEmbed(player, Embed.COORDINATE, player.getName() + " is currently at [" + X + " " + Y + " " + Z + "](" + Core.config.get("WWWMapAddress") + ")");
				}
				coordEmbed.send();
				
				TextComponent clickableCoords = new TextComponent(TextComponent.fromLegacyText(X + " " + Y + " " + Z, ChatColor.of(Embed.COORDINATE)));
				clickableCoords.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tpa " + player.getName()));
				clickableCoords.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click to teleport!")));
				BaseComponent[] component = new ComponentBuilder(player.getName()).color(ChatColor.of(Embed.PLAYER(player))).append(" is currently at ").color(ChatColor.WHITE).append(clickableCoords).color(ChatColor.of(Embed.COORDINATE)).create();
				Core.logger.info(ChatColor.of(Embed.PLAYER(player)) + player.getName() + ChatColor.WHITE + " is currently at " + ChatColor.of(Embed.COORDINATE) + X + " " + Y + " " + Z);
				for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
					onlinePlayer.spigot().sendMessage(component);
				}
				return true;
			}
		}
		
		// Console and player commands
		if(cmd.equalsIgnoreCase("reloadtoken")) {
			if(sender instanceof Player) {
				// OP Check
				if(!((Player) sender).isOp()) {
					return false;
				}
			}
			Core.buildConfig();
			// Send all online OPs a message
			String tosend = ChatColor.WHITE + "Discraft token has been refreshed by " + ChatColor.BLUE + "console";
			if(sender instanceof Player) {
				tosend = ChatColor.WHITE + "Discraft token has been refreshed by " + ChatColor.of(Embed.PLAYER((Player) sender) + ((Player) sender).getName());
			}
			for(Player player : Bukkit.getServer().getOnlinePlayers()) {
				if(player.isOp()) {
					player.sendMessage(tosend);
				}
			}
			Core.logger.info(tosend);
			return true;
		}
		return false;
	}
}
