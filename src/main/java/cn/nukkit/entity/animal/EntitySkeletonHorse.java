package cn.nukkit.entity.animal;

import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public class EntitySkeletonHorse extends EntityAnimal {

    public static final int NETWORK_ID = 26;

    public EntitySkeletonHorse(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public Item[] getDrops() {
        return new Item[]{};
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
    public float getWidth() {
        if (isBaby()) {
            return 0.6982f; // No have information
        }
        return 1.3965f;
    }

    @Override
    public float getHeight() {
        if (isBaby()) {
            return 0.8f; // No have information
        }
        return 1.6f;
    }
}
