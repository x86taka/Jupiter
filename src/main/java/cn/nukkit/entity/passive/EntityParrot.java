package cn.nukkit.entity.passive;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemFeather;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * @author Megapix96
 */
public class EntityParrot extends EntityAnimal {

    public static final int NETWORK_ID = 30;

    public EntityParrot(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public float getWidth() {
        return 0.5f;
    }

    @Override
    public float getHeight() {
        return 0.9f;
    }

    @Override
    public String getName() {
        return this.getNameTag();
    }

    @Override
    public Item[] getDrops() {
        return new Item[]{new ItemFeather(0, random.nextRange(1, 2))};
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    protected void initEntity() {
        this.setMaxHealth(6);
        super.initEntity();
    }
}
