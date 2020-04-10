package com.mineinabyss.eternalfortune.container;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface ItemContainer {

    Long getProtection();

    Player getOwner();

    Location getLocation();

    List<ItemStack> getContent();

    Entity getManagedEntity();

}
