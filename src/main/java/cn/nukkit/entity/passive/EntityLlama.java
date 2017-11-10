package cn.nukkit.entity.passive;

import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public class EntityLlama extends EntityAnimal {

    public static final int NETWORK_ID = 29;

    public EntityLlama(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        this.setMaxHealth(random.nextRange(15, 30));
        this.setHealth(this.getMaxHealth());
    }

    @Override
    public float getWidth() {
        if (isBaby()) {
            return 0.45f;
        }
        return 0.9f;
    }

    @Override
    public float getHeight() {
        if (isBaby()) {
            return 0.935f;
        }
        return 1.87f;
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
        super.initEntity();
    }

    @Override
    public Item[] getDrops() {
        return new Item[]{};
    }
}
