package com.hayesdev;

import com.hayesdev.commands.CallAdmin;
import com.hayesdev.listeners.DiscordSRVListener;
import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;

public class MyPlugin extends JavaPlugin {

    private static MyPlugin myPlugin;

    private DiscordSRVListener discordsrvListener;
    private File customConfigFile;
    private FileConfiguration configFile;

    @Override
    public void onEnable() {
        myPlugin = this;
        discordsrvListener = new DiscordSRVListener(myPlugin);
        DiscordSRV.api.subscribe(discordsrvListener);
        createCustomFile();
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();
        if (configFile != null) {
            if (!(configFile.contains("admin-channel"))) {
                configFile.createSection("admin-channel");
                configFile.set("admin-channel", 0);
            }
        }
        try {
            configFile.save(customConfigFile);
        } catch (IOException exception1) {
            exception1.printStackTrace();
        }
        this.getCommand("calladmin").setExecutor(new CallAdmin());
        System.out.println("[CallAdmin] Enabled!");
        if (configFile.getInt("admin-channel") == 0) {
            System.out.println("[CallAdmin] No channel path set in config!");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void createCustomFile() {
        customConfigFile = new File(getDataFolder(), "config.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }

        configFile = new YamlConfiguration();
        try {
            configFile.load(customConfigFile);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfigFile() {
        return this.configFile;
    }

    @Override
    public void onDisable() {
        DiscordSRV.api.unsubscribe(discordsrvListener);
        System.out.println("[CallAdmin] Disabled");
    }

    public static MyPlugin getInstance() { return myPlugin; }

}