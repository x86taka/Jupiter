package cn.nukkit.entity.boss;

import cn.nukkit.Player;
import cn.nukkit.block.BlockSponge;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemPrismarineCrystals;
import cn.nukkit.item.ItemPrismarineShard;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AddEntityPacket;

public class EntityElderGuardian extends EntityBoss {

	public static final int NETWORK_ID = 50;

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    public EntityElderGuardian(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
    @Override
    protected void initEntity() {
    	this.setMaxHealth(80);
        super.initEntity();
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
        Item drops[] = new Item[4];
        drops[0] = new ItemPrismarineCrystals(0, random.nextRange(0, 1));
        drops[1] = new ItemPrismarineShard(0, random.nextRange(0, 2));
        //TODO: 60%の確率で生魚、25%の確率で生鮭、2%の確率でクマノミ、13%の確率でフグ。また焼死時には焼き魚、焼き鮭をドロップ
        if (this.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            drops[2] = new ItemBlock(new BlockSponge());
        }
        return drops;
    }
}
