package it.mikeslab;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.MessageType;
import it.mikeslab.command.PotionCommand;
import it.mikeslab.event.PlayerListener;
import it.mikeslab.util.CacheHandler;
import it.mikeslab.util.language.Language;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter public static Main instance;
    private BukkitCommandManager commandManager;


    @Override
    public void onEnable() {
        saveDefaultConfig();

        instance = this;

        CacheHandler.loadCacheFromConfig();

        setupCommandFramework();
        setupListener();

        Language.initialize(this, getConfig().getString("language"));
    }


    @Override
    public void onDisable() {
        CacheHandler.saveCacheToConfig();
    }


    private void setupCommandFramework() {
        commandManager = new BukkitCommandManager(this);
        commandManager.registerCommand(new PotionCommand());
        commandManager.enableUnstableAPI("help");
        commandManager.setFormat(MessageType.HELP, ChatColor.GREEN, ChatColor.GOLD);
    }


    private void setupListener() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }




}
