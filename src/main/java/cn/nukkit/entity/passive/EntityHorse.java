package cn.nukkit.entity.passive;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemLeather;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public class EntityHorse extends EntityAnimal {

    public static final int NETWORK_ID = 23;

    public EntityHorse(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        this.setMaxHealth(random.nextRange(15, 30));
        this.setHealth(this.getMaxHealth());
    }

    @Override
    public float getWidth() {
        if (isBaby()) {
            return 0.6982f;
        }
        return 1.3965f;
    }

    @Override
    public float getHeight() {
        if (isBaby()) {
            return 0.8f;
        }
        return 1.6f;
    }

    @Override
    public String getName() {
        return this.getNameTag();
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
        return new Item[]{new ItemLeather(0, random.nextRange(0, 2))};
    }
}
