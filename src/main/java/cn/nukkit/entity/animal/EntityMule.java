package cn.nukkit.entity.animal;

import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public class EntityMule extends EntityAnimal {

    public static final int NETWORK_ID = 25;

    public EntityMule(FullChunk chunk, CompoundTag nbt) {
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
    	this.setMaxHealth(20);
        super.initEntity();
    }
}
