package it.mikeslab.util.language;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;
import java.util.logging.Level;

/**
 * Provides utility methods for managing language files for Bukkit/Spigot plugins.
 */
@UtilityClass
public class Language {

    private final String
            subFolder = "languages",
            defaultLanguage = "en_US";

    @Getter
    private FileConfiguration languageFile;
    private File dataFolder;
    private JavaPlugin plugin;
    private Cache<LangKey, String> cache;

    /**
     * Initializes the language file management system.
     *
     * @param plugin   The Bukkit/Spigot plugin instance.
     * @param language The language to load.
     */
    public void initialize(JavaPlugin plugin, String language) {
        Language.dataFolder = plugin.getDataFolder();
        Language.plugin = plugin;

        // Initialize the cache with a maximum size of 1000 entries.
        cache = Caffeine.newBuilder()
                .maximumSize(1000)
                .build();

        // Generate language files for recognized languages if they don't exist, and load the specified language.
        generate();
        loadLanguage(language);
    }

    /**
     * Reloads the specified language file.
     *
     * @param language The language to reload.
     */
    public void reload(String language) {
        loadLanguage(language);
    }

    /**
     * Returns the language string for the given key, or the default value if the key is not found in the language file.
     *
     * @param langKey The language key to retrieve the value for.
     * @return The language string for the given key.
     */
    public String getString(LangKey langKey) {
        try {
            // Try to get the language string from the cache. If it's not there, load it from the language file and add it to the cache.
            return cache.get(langKey, key -> loadString(langKey));
        } catch (Exception e) {
            // If an exception occurs while loading the language string, log a warning and return the default value.
            plugin.getLogger().log(Level.WARNING, "Error while getting language string for key " + langKey, e);
            return langKey.getDefaultValue();
        }
    }

    /**
     * Returns the language string for the given key with replacements, or the default value if the key is not found in the language file.
     *
     * @param langKey      The language key to retrieve the value for.
     * @param replacements The map of replacements to be applied to the language string.
     * @return The language string for the given key with replacements applied.
     */
    public String getString(LangKey langKey, Map<String, String> replacements) {
        String cacheString = getString(langKey);

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            cacheString = cacheString.replace(entry.getKey(), entry.getValue());
        }

        return cacheString;
    }

    /**
     * Loads the language string for the given key from the language file.
     *
     * @param langKey The language key to load the value for.
     * @return The language string for the given key.
     */
    private String loadString(LangKey langKey) {
        String parsedKey = langKey.name().toLowerCase().replace("_", "-");

        // If the language file doesn't contain the specified key, log a warning.
        if (!languageFile.contains(parsedKey)) {
            Bukkit.getLogger().warning("Missing language key: " + parsedKey);
        }

        // Return the language string for the specified key, or the default value if it's not found.
        return Translator.parseColor(languageFile.getString(parsedKey, langKey.getDefaultValue()));
    }

    /**
     * Closes the access to the language file.
     */
    public void close() {
        languageFile = null;
    }

    /**
     * Returns true if a language file with the given name exists in the plugin's data folder.
     *
     * @param language The language to check for.
     * @return True if a language file with the given name exists in the plugin's data folder.
     */
    public boolean isLanguageFile(String language) {
        return new File(dataFolder, subFolder + File.separator + language + ".yml").exists();
    }

    /**
     * Generates all language files for recognized languages if they don't exist.
     */
    private void generate() {
        // Generate the subfolder for language files if it doesn't exist.
        generateSubFolder();

        // Generate language files for each recognized language if they don't exist.
        for (RecognizedLanguages language : RecognizedLanguages.values()) {
            generateLanguageFile(language);
        }
    }

    /**
     * Creates the language subfolder in the plugin's data folder if it doesn't exist.
     */
    private void generateSubFolder() {
        File file = new File(dataFolder, subFolder);

        // If the subfolder doesn't exist, create it.
        if (!file.exists()) {
            file.mkdir();
        }
    }

    /**
     * Generates the language file for the given recognized language if it doesn't exist.
     *
     * @param language The recognized language to generate the file for.
     */
    private void generateLanguageFile(RecognizedLanguages language) {
        String path = subFolder + File.separator + language.name() + ".yml";
        File file = new File(dataFolder + File.separator + path);

        // If the language file already exists and has a non-zero size, skip it.
        if (file.exists() && file.length() > 0) {
            return;
        }

        try {
            // Generate the language file from the plugin resources.
            plugin.saveResource(path, false);
        } catch (IllegalArgumentException e) {
            // If an exception occurs while generating the language file, log a warning.
            plugin.getLogger().log(Level.WARNING, "Error while generating language file for language " + language, e);
        }
    }

    /**
     * Loads the language file with the given name, or the default language file if the given name is not recognized.
     *
     * @param language The language to load.
     */
    private void loadLanguage(String language) {
        // If the specified language is not recognized, use the default language.
        if (!RecognizedLanguages.isRecognizedLanguage(language)) {
            language = defaultLanguage;
        }

        // Load the language file from the plugin's data folder.
        File file = new File(dataFolder, subFolder + File.separator + language + ".yml");
        try {
            Language.languageFile = YamlConfiguration.loadConfiguration(file);
        } catch (Exception e) {
            // If an exception occurs while loading the language file, log an error and use an empty configuration.
            plugin.getLogger().log(Level.SEVERE, "Failed to load language file: " + language + ".yml", e);
            Language.languageFile = new YamlConfiguration();
        }
    }
}
