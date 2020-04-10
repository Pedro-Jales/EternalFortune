package com.mineinabyss.eternalfortune.container;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class SerializableItemContainer implements ItemContainer, Serializable {

    private Long protection;
    private UUID owner;
    private Map<String, Object> location;
    private UUID managedEntity;
    private List<Map<String, Object>> content = new ArrayList<>();

    /**
     * Serializable ItemContainer to persist items on player death.
     *
     * @param protection protection
     * @param owner owner
     * @param location location
     * @param content content
     * @param managedEntity managedEntity
     */
    public SerializableItemContainer(Long protection, Player owner, Location location, List<ItemStack> content, Entity managedEntity) {
        this.protection = protection;
        this.owner = owner.getUniqueId();
        this.location = location.serialize();
        content.forEach(item -> this.content.add(item.serialize()));
        this.managedEntity = managedEntity.getUniqueId();
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
     * Get the container owner by his uuid and return it.
     *
     * @return owner
     */
    @Override
    public Player getOwner() {
        return Bukkit.getPlayer(owner);
    }

    /**
     * Deserialize the location of the container and return it.
     *
     * @return location
     */
    @Override
    public Location getLocation() {
        return Location.deserialize(location);
    }

    /**
     * Deserialize ItemStacks and return them.
     *
     * @return items
     */
    @Override
    public List<ItemStack> getContent() {
        return content.stream().map(ItemStack::deserialize).collect(Collectors.toList());
    }

    /**
     * Gets the managed entity by her uuid and returns it.
     *
     * @return entity
     */
    @Override
    public Entity getManagedEntity() {
        return Bukkit.getEntity(managedEntity);
    }

}
