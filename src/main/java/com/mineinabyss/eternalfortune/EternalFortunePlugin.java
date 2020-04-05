package com.mineinabyss.eternalfortune;

import org.bukkit.plugin.java.JavaPlugin;

public class EternalFortunePlugin extends JavaPlugin {

    private static EternalFortunePlugin instance;

    /**
     * Set the plugin instance.
     */
    @Override
    public void onEnable() {
        instance = this;
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
