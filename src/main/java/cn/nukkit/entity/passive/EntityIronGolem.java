package cn.nukkit.entity.passive;

import cn.nukkit.block.BlockFlower;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemIngotIron;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public class EntityIronGolem extends EntityAnimal {

    public static final int NETWORK_ID = 20;

    public EntityIronGolem(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public Item[] getDrops() {
        return new Item[]{
                new ItemIngotIron(0, random.nextRange(3, 5)),
                new BlockFlower(BlockFlower.TYPE_POPPY).toItem().setCount(random.nextRange(0, 2))
        };
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public void initEntity() {
        this.setMaxHealth(100);
        super.initEntity();
    }

    @Override
    public float getWidth() {
        return 1.4f;
    }

    @Override
    public float getHeight() {
        return 2.7f;
    }

    @Override
    public String getName() {
        return this.getNameTag();
    }
}
