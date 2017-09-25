package cn.nukkit.entity.animal;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemFish;
import cn.nukkit.item.ItemSalmon;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public class EntityPolarBear extends EntityAnimal {

    public static final int NETWORK_ID = 28;

    public EntityPolarBear(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public float getWidth() {
        if (isBaby()) {
            return 0.65f;
        }
        return 1.3f;
    }

    @Override
    public float getHeight() {
        if (isBaby()) {
            return 0.7f;
        }
        return 1.4f;
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public void initEntity() {
        this.setMaxHealth(30);
        super.initEntity();
    }

    @Override
    public Item[] getDrops() {
        if (random.nextRange(0, 3) != 0) {
            return new Item[]{new ItemFish(0, random.nextRange(0, 2))};
        } else {
            return new Item[]{new ItemSalmon(0, random.nextRange(0, 2))};
        }
    }
}
