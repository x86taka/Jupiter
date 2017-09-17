package cn.nukkit.entity.animal;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemLeather;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public class EntityDonkey extends EntityHorse {

    public static final int NETWORK_ID = 24;

    public EntityDonkey(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public Item[] getDrops() {
        return new Item[]{new ItemLeather(0, random.nextRange(0, 2))};
    }
}
