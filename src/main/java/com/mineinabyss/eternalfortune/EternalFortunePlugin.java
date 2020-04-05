package com.mineinabyss.eternalfortune;

import org.bukkit.*;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class EternalFortunePlugin extends JavaPlugin {

    private static EternalFortunePlugin instance;

    /**
     * Set plugin instance.
     */
    @Override
    public void onEnable() {
        instance = this;

        for (Player player : Bukkit.getOnlinePlayers()) {
            World world = player.getWorld();
            Location location = player.getLocation();
            Location entity = new Location(world, location.getBlockX() + 0.5D, location.getBlockY() + 1, location.getBlockZ() + 0.5D);
            AreaEffectCloud cloud = (AreaEffectCloud) world.spawnEntity(entity, EntityType.AREA_EFFECT_CLOUD);
            cloud.setCustomName(ChatColor.BLUE + "ETERNAL FORTUNE");
            cloud.setCustomNameVisible(true);
            cloud.setDuration(500);
            cloud.setRadius(10F);

            AreaEffectCloud cloud2 = (AreaEffectCloud) world.spawnEntity(entity.subtract(0D, 0.3D, 0D), EntityType.AREA_EFFECT_CLOUD);
            cloud2.setCustomName(player.getName());
            cloud2.setCustomNameVisible(true);
            cloud2.setDuration(500);
            cloud2.setRadius(10F);

            AreaEffectCloud cloud3 = (AreaEffectCloud) world.spawnEntity(entity.subtract(0D, 0.4D, 0D), EntityType.AREA_EFFECT_CLOUD);
            cloud3.setCustomName(ChatColor.YELLOW + "23H 59M 59S");
            cloud3.setCustomNameVisible(true);
            cloud3.setDuration(500);
            cloud3.setRadius(10F);

            player.sendBlockChange(entity.subtract(0.5D, 0D, 0.5D), Material.WHITE_TULIP, (byte) 6);
        }
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
