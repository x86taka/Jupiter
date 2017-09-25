package cn.nukkit.entity.monster;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBone;
import cn.nukkit.item.ItemCoal;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AddEntityPacket;

public class EntityWitherSkeleton extends EntityMonster {
    public static final int NETWORK_ID = 48;

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    public EntityWitherSkeleton(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
    @Override
    protected void initEntity() {
    	this.setMaxHealth(24);
        super.initEntity();
    }
    
    @Override
    public float getWidth() {
        return 0.7f;
    }

    @Override
    public float getLength() {
        return 0.7f;
    }

    @Override
    public float getHeight() {
        return 2.4f;
    }

    @Override
    public String getName() {
        return "WitherSkeleton";
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
        return new Item[]{new ItemCoal(0, random.nextRange(0, 1)), new ItemBone(0, random.nextRange(0, 2))};
    }
}
