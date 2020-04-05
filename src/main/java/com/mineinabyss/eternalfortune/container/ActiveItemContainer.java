package com.mineinabyss.eternalfortune.container;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ActiveItemContainer implements ItemContainer {

    private Long protection;
    private Player owner;
    private Location location;
    private List<ItemStack> content;
    private List<Entity> managedEntities;

    /**
     * ItemContainer to store values after deserialization of a SerializableItemContainer.
     *
     * @param protection protection
     * @param owner owner
     * @param location location
     * @param content content
     * @param managedEntities managedEntities
     */
    public ActiveItemContainer(Long protection, Player owner, Location location, List<ItemStack> content, List<Entity> managedEntities) {
        this.protection = protection;
        this.owner = owner;
        this.location = location;
        this.content = content;
        this.managedEntities = managedEntities;
    }

    /**
     * Returns the time till this container is protected and
     * only accessible to the owner.
     *
     * @return protection
     */
    @Override
    public Long getProtection() {
        return protection;
    }

    /**
     * Returns the owner of the container.
     *
     * @return owner
     */
    @Override
    public Player getOwner() {
        return owner;
    }

    /**
     * Returns the location of the container.
     *
     * @return location
     */
    @Override
    public Location getLocation() {
        return location;
    }

    /**
     * Returns the items from the container.
     *
     * @return items
     */
    @Override
    public List<ItemStack> getContent() {
        return content;
    }

    /**
     * Returns the entities displaying info about the container.
     *
     * @return entities
     */
    @Override
    public List<Entity> getManagedEntities() {
        return managedEntities;
    }

    /**
     * Returns an instance of an ActiveItemContainer from an instance
     * of a SerializableItemContainer.
     *
     * @param container container
     * @return container
     */
    public static ActiveItemContainer from(SerializableItemContainer container) {
        return new ActiveItemContainer(container.getProtection(), container.getOwner(), container.getLocation(), container.getContent(), container.getManagedEntities());
    }

}
