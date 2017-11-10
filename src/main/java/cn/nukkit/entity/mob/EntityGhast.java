package cn.nukkit.entity.mob;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemGhastTear;
import cn.nukkit.item.ItemGunpowder;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AddEntityPacket;

public class EntityGhast extends EntityMob {
    public static final int NETWORK_ID = 41;

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    public EntityGhast(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
    @Override
    protected void initEntity() {
    	this.setMaxHealth(10);
        super.initEntity();
    }
    
    @Override
    public float getWidth() {
        return 6f;
    }

    @Override
    public float getLength() {
        return 6f;
    }

    @Override
    public float getHeight() {
        return 6f;
    }

    @Override
    public String getName() {
        return "Ghast";
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
        return new Item[]{new ItemGhastTear(0, random.nextRange(0, 1)), new ItemGunpowder(0, random.nextRange(0, 2))};
    }
}
