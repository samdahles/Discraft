# Discraft
Highly configurable Spigot plugin linking Discord channel to Minecraft chat.

This plugin contains features that other plugins don't have:
- It's highly configurable
- It respects the Discord role colors
- Logs all events
- Good-looking embeds
- Simple

<p align=center>Scroll down, there's more!</p>
<img src="https://media.discordapp.net/attachments/804063233014497320/927358476982812702/unknown.png">


## How to install
1. Drop the latest Discraft release into your Spigot plugin folder
2. (re)Start the server
3. The folder 'Discraft' is now made containing 'config.yml', open it in an IDE
4. Enter the information
5. Done!

## FAQ
- How to get a Discord channel ID?
1. In Discord settings, click 'Advanced' and turn on 'Developer mode'
2. Right-click the channel you want to copy the ID from
3. Click 'Copy ID'
- What do all entries in config.yml mean?
1. `minecraftChannel`: the ID of the channel that you want to link
2. `isWWWMapEnabled`: turn this to `true` if you have Dynmap installed, `false` if not
3. `showServerStatusEmbeds`: turn this to `true` if you want to be notified on server status changes
4. `showPresence`: turn this to `true` if you want Discraft to handle the status
5. `WWWMapAddress`: the URI your Dynmap
6. `serverAddress`: the IP address or domain name of your server host
7. `botToken`: your Discord bot token
- How do I get a Discord bot token?
Please see [this link](https://www.writebots.com/discord-bot-token/).
