package cn.nukkit.inventory;

import java.util.UUID;

import cn.nukkit.item.Item;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public interface Recipe {

    Item getResult();

    void registerToCraftingManager();

    UUID getId();

    void setId(UUID id);

    default boolean requiresCraftingTable() {
        return false;
    }
}