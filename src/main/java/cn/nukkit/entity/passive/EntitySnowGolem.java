package cn.nukkit.entity.passive;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemSnowball;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public class EntitySnowGolem extends EntityAnimal {

    public static final int NETWORK_ID = 21;

    public EntitySnowGolem(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public Item[] getDrops() {
        return new Item[]{
                new ItemSnowball(0, random.nextRange(0, 15))
        };
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public void initEntity() {
        this.setMaxHealth(4);
        super.initEntity();
    }

    @Override
    public float getWidth() {
        return 0.7f;
    }

    @Override
    public float getHeight() {
        return 1.9f;
    }

    @Override
    public String getName() {
        return this.getNameTag();
    }
}
