package cn.nukkit.entity.passive;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBeefRaw;
import cn.nukkit.item.ItemLeather;
import cn.nukkit.item.ItemSteak;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * Author: BeYkeRYkt
 * Nukkit Project
 */
public class EntityMooshroom extends EntityAnimal {

    public static final int NETWORK_ID = 16;

    public EntityMooshroom(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    protected void initEntity() {
        setMaxHealth(10);
        super.initEntity();
    }

    @Override
    public float getWidth() {
        return 0.9f;
    }

    @Override
    public float getHeight() {
        if (isBaby()) {
            return 0.65f;
        }
        return 1.3f;
    }

    @Override
    public float getEyeHeight() {
        if (isBaby()) {
            return 0.65f;
        }
        return 1.2f;
    }

    @Override
    public String getName() {
        return this.getNameTag();
    }

    @Override
    public Item[] getDrops() {
        if (this.isOnFire()) {
            return new Item[]{new ItemLeather(0, random.nextRange(0, 2)), new ItemSteak(0, random.nextRange(1, 3))};
        } else {
            return new Item[]{new ItemLeather(0, random.nextRange(0, 2)), new ItemBeefRaw(0, random.nextRange(1, 3))};
        }
    }
}