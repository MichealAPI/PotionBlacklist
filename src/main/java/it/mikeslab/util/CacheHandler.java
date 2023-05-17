package it.mikeslab.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.base.Stopwatch;
import it.mikeslab.Main;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * This utility class handles cache operations for blacklisted UUIDs.
 */
@UtilityClass
public class CacheHandler {
    /**
     * Cache of blacklisted UUIDs.
     */
    @Getter
    private final Cache<UUID, Boolean> blacklistedUUIDsCache =
            Caffeine.newBuilder()
                    .maximumSize(1000)
                    .build();

    /**
     * Saves the current state of the UUID cache to the config file.
     */
    public void saveCacheToConfig() {
        Stopwatch stopwatch = Stopwatch.createStarted();

        FileConfiguration config = Main.getInstance().getConfig();
        List<String> list = config.getStringList("blacklisted-uuids");

        config.set("blacklisted-uuids", null);

        blacklistedUUIDsCache.asMap().keySet().forEach(uuid -> list.add(uuid.toString()));

        config.set("blacklisted-uuids", list);
        Main.getInstance().saveConfig();

        stopwatch.stop();

        Bukkit.getLogger().info(String.format("Saved %d blacklisted UUIDs to config in %d ms",
                blacklistedUUIDsCache.asMap().size(), stopwatch.elapsed(TimeUnit.MILLISECONDS)));
    }

    /**
     * Loads the UUID cache from the config file.
     */
    public void loadCacheFromConfig() {
        Stopwatch stopwatch = Stopwatch.createStarted();

        FileConfiguration config = Main.getInstance().getConfig();
        List<String> list = config.getStringList("blacklisted-uuids");

        if(list.isEmpty()) return;

        list.forEach(uuid -> blacklistedUUIDsCache.put(UUID.fromString(uuid), true));

        stopwatch.stop();

        Bukkit.getLogger().info(String.format("Loaded %d blacklisted UUIDs from config in %d ms",
                blacklistedUUIDsCache.asMap().size(), stopwatch.elapsed(TimeUnit.MILLISECONDS)));
    }
}
