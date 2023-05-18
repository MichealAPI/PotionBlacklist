![logo](https://i.imgur.com/vUS2JQM.png)


[![CodeFactor](https://www.codefactor.io/repository/github/michealslab/potionblacklist/badge)](https://www.codefactor.io/repository/github/michealslab/potionblacklist) 


The **PotionBlacklist** plugin is designed for Minecraft versions **1.19 and above**. This plugin allows players with the appropriate permissions to **manage potion consumption** for other players on the server. With the permission nodes potion.unblacklist and ```potion.blacklist```, players can execute the commands ```/potion unblacklist [player]``` and ```/potion blacklist [player]``` respectively.

# Features

* **Potion Unblacklist**: Players with the ```potion.unblacklist``` permission can use the command ```/potion unblacklist [player]``` to grant the ability for a specified player to consume potions. Once unblacklisted, the player will be able to drink potions as usual.

* **Potion Blacklist**: Players with the ```potion.blacklist``` permission can use the command ```/potion blacklist [player]``` to prevent a specified player from consuming potions. When blacklisted, the player will be unable to drink potions until they are removed from the blacklist.

* **Caffeine Caching System**: The plugin utilizes a Caffeine caching system based on the FileConfiguration. This caching mechanism optimizes performance and enhances the overall efficiency of the plugin.

# Permissions

* **potion.unblacklist**: Grants the permission to use the ```/potion unblacklist [player]``` command and allow a player to consume potions.

* **potion.blacklist**: Grants the permission to use the ```/potion blacklist [player]``` command and prevent a player from consuming potions.

# Usage

* To use the plugin, make sure to place it inside the plugins folder of your Server.

* To grant a player the ability to drink potions, use the command ```/potion unblacklist [player]```. Replace **[player]** with the desired player's username.

* To prevent a player from consuming potions, use the command ```/potion blacklist [player]```. Again, replace **[player]** with the username of the player you wish to blacklist.

# Compatibility

This plugin is compatible with Minecraft versions **1.19** and newer. It leverages the latest features and enhancements available in the game to provide a seamless potion control experience.

**Note**: Make sure to follow the installation instructions provided with the plugin to ensure proper functionality.

Enjoy the Minecraft Potion Control plugin and have fun managing potion consumption on your server!
