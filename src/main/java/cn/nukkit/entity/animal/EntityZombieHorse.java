package cn.nukkit.entity.animal;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemRottenFlesh;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public class EntityZombieHorse extends EntityHorse {

    public static final int NETWORK_ID = 27;

    public EntityZombieHorse(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public Item[] getDrops() {
        return new Item[]{new ItemRottenFlesh(0, random.nextRange(0, 2))};
    }
}
