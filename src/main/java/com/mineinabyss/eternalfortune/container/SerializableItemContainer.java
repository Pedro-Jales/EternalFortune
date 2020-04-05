package com.mineinabyss.eternalfortune.container;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class SerializableItemContainer implements ItemContainer, Serializable {

    private Long protection;
    private UUID owner;
    private Map<String, Object> location;
    private List<UUID> managedEntities = new ArrayList<>();
    private List<Map<String, Object>> content = new ArrayList<>();

    /**
     * Serializable ItemContainer to persist items on player death.
     *
     * @param protection protection
     * @param owner owner
     * @param location location
     * @param content content
     * @param managedEntities managedEntities
     */
    public SerializableItemContainer(Long protection, Player owner, Location location, ItemStack[] content, AreaEffectCloud... managedEntities) {
        this.protection = protection;
        this.owner = owner.getUniqueId();
        this.location = location.serialize();
        Arrays.stream(content).forEach(item -> this.content.add(item.serialize()));
        Arrays.stream(managedEntities).forEach(entity -> this.managedEntities.add(entity.getUniqueId()));
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
     * Get entities by their UUIDs and return them.
     *
     * @return entities
     */
    @Override
    public List<Entity> getManagedEntities() {
        return managedEntities.stream().map(Bukkit::getEntity).collect(Collectors.toList());
    }

}
