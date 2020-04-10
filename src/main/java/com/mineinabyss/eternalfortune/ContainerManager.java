package com.mineinabyss.eternalfortune;

import com.mineinabyss.eternalfortune.container.ActiveItemContainer;
import com.mineinabyss.eternalfortune.container.SerializableItemContainer;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ContainerManager implements Listener {

    public static final Long PROTECTION = TimeUnit.HOURS.toMillis(24L);

    public static final String PERMISSION_BASE = "mineinabyss.eternalfortune";
    public static final String PERMISSION_USE = PERMISSION_BASE + ".use";

    private List<ActiveItemContainer> containers;
    private Logger logger;

    public ContainerManager() {
        containers = new ArrayList<>();
        logger = EternalFortunePlugin.getInstance().getLogger();
    }

    /**
     * Returns the items from a player inventory.
     *
     * @param player player
     * @return items
     */
    private List<ItemStack> getInventoryContents(Player player) {
        return Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Returns an info about the container used as customname for
     * the armorstand.
     *
     * @param owner owner
     * @param time time
     * @param state state
     * @return info
     */
    private String getInfo(String owner, String time, String state) {
        return ChatColor.BLUE + owner + ChatColor.WHITE + " " + time + ChatColor.GREEN + " " + state;
    }

    /**
     * Spawn a container.
     */
    private void spawnContainer(World world, Player player, List<ItemStack> contents) {
        Location location = player.getLocation();
        Location center = new Location(world, location.getBlockX() + 0.5D, location.getBlockY() + 1.5D, location.getBlockZ() + 0.5D);
        ArmorStand managed = (ArmorStand) world.spawnEntity(center, EntityType.ARMOR_STAND);

        managed.setVisible(false);
        managed.setCollidable(false);
        managed.setSilent(true);
        managed.setCustomName(getInfo(player.getName(), "XX:YY:ZZ", "PROTECTED"));
        managed.setCustomNameVisible(true);

        ActiveItemContainer container = new ActiveItemContainer(
                System.currentTimeMillis() + PROTECTION,
                player,
                player.getLocation().clone().add(0D, 1D, 0D),
                contents,
                managed);

        containers.add(container);
        player.sendMessage(
                ChatColor.BLUE + "X: " + ChatColor.WHITE + location.getBlockX()
                + ChatColor.BLUE + " Y: " + ChatColor.WHITE + location.getBlockY()
                + ChatColor.BLUE + " Z: " + ChatColor.WHITE + location.getBlockZ());
    }

    public List<ActiveItemContainer> getContainers() {
        return containers;
    }

    /**
     * Load all containers.
     */
    protected void load() {
        for (File file : EternalFortunePlugin.getInstance().getContainerFolder().listFiles()) {
            ObjectInputStream ois = null;

            try {
                FileInputStream fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);

                containers.add(ActiveItemContainer.from((SerializableItemContainer) ois.readObject()));
            } catch (IOException e) {
                logger.log(Level.WARNING, "Error while reading container object.", e);
            } catch (ClassNotFoundException e) {
                logger.log(Level.WARNING, "Unable to find class for container.", e);
            } finally {
                try {
                    ois.close();
                } catch (IOException e) {
                    logger.log(Level.WARNING, "Could not close inputstream for container.", e);
                }
            }
        }


    }

    /**
     * Save all containers.
     */
    protected void save() {
        for (File file : EternalFortunePlugin.getInstance().getContainerFolder().listFiles()) {
            if (!file.isDirectory()) {
                if (!file.delete()) {
                    logger.log(Level.SEVERE, "Could not delete old container file.");
                }
            }
        }

        for (ActiveItemContainer active : containers) {
            SerializableItemContainer serializable = new SerializableItemContainer(
                    active.getProtection(),
                    active.getOwner(),
                    active.getLocation(),
                    active.getContent(),
                    active.getManagedEntity());
            ObjectOutputStream oos = null;

            try {
                FileOutputStream fos = new FileOutputStream(EternalFortunePlugin.getInstance().getContainerFolder().getPath() + "/" + UUID.randomUUID().toString());
                oos = new ObjectOutputStream(fos);

                oos.writeObject(serializable);
            } catch (IOException e) {
                logger.log(Level.WARNING, "Error while saving a container.");
            } finally {
                try {
                    oos.close();
                } catch (IOException e) {
                    logger.log(Level.WARNING, "Could not close outputstream for container.", e);
                }
            }
        }
    }

    /**
     * Spawn a container on player death.
     *
     * @param event event
     */
    @EventHandler
    public void onSpawnContainer(PlayerDeathEvent event) {
        Player player = event.getEntity();
        World world = player.getWorld();

        if (!world.getGameRuleValue(GameRule.KEEP_INVENTORY) && player.hasPermission(PERMISSION_USE)) {
            List<ItemStack> contents = getInventoryContents(player);

            if (contents.size() > 0) {
                spawnContainer(world, player, contents);
            }
        }
    }

    /**
     * Let players access containers.
     *
     * @param event event
     */
    @EventHandler
    public void onOpenContainer(PlayerInteractEvent event) {

    }

}
