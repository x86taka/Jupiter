package cn.nukkit.entity.mob;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemMagmaCream;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AddEntityPacket;

public class EntityMagmaCube extends EntityMob {
    public static final int NETWORK_ID = 42;

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    public EntityMagmaCube(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
    @Override
    protected void initEntity() {
        super.initEntity();
    }
    
    @Override
    public float getWidth() {
        return 0.3f;
    }

    @Override
    public float getLength() {
        return 0.9f;
    }

    @Override
    public float getHeight() {
        //TODO: ageable
        return 5f;
    }

    @Override
    public String getName() {
        return "Magma Cube";
    }


    @Override
    public void spawnTo(Player player) {
        AddEntityPacket pk = new AddEntityPacket();
        pk.type = this.getNetworkId();
        pk.entityUniqueId = this.getId();
        pk.entityRuntimeId = this.getId();
        pk.x = (float) this.x;
        pk.y = (float) this.y;
        pk.z = (float) this.z;
        pk.speedX = (float) this.motionX;
        pk.speedY = (float) this.motionY;
        pk.speedZ = (float) this.motionZ;
        pk.metadata = this.dataProperties;
        player.dataPacket(pk);

        super.spawnTo(player);
    }

    @Override
    public Item[] getDrops() {
        return new Item[]{new ItemMagmaCream(0, random.nextRange(0, 1))};
    }
}
