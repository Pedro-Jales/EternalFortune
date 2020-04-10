package com.mineinabyss.eternalfortune;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public class EternalFortunePlugin extends JavaPlugin {

    private static EternalFortunePlugin instance;

    private File containerFolder;
    private ContainerManager containerManager;

    /**
     * Set the plugin instance and load containerFolder.
     */
    @Override
    public void onEnable() {
        instance = this;
        containerManager = new ContainerManager();
        containerFolder = new File(getDataFolder(), "containers");

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        if (!containerFolder.exists()) {
            if (containerFolder.mkdir()) {
                getLogger().log(Level.INFO, "Created folder for storing containers.");
            } else {
                getLogger().log(Level.SEVERE, "Unable to create folder for storing containers.");
            }
        }

        getServer().getPluginManager().registerEvents(containerManager, this);
        containerManager.load();

        System.out.println(containerManager.getContainers().size());
    }

    /**
     * Save container data.
     */
    @Override
    public void onDisable() {
        containerManager.save();
        HandlerList.unregisterAll(containerManager);
    }

    /**
     * Returns the folder used for storing containers.
     *
     * @return folder
     */
    public File getContainerFolder() {
        return containerFolder;
    }

    /**
     * Returns an instance of ContainerManager.
     *
     * @return manager
     */
    public ContainerManager getContainerManager() {
        return containerManager;
    }

    /**
     * Returns an instance of the plugin.
     *
     * @return instance
     */
    public static EternalFortunePlugin getInstance() {
        return instance;
    }

}
