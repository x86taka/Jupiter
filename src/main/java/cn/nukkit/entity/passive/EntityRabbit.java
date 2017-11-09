package cn.nukkit.entity.passive;

import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemRabbitCooked;
import cn.nukkit.item.ItemRabbitFoot;
import cn.nukkit.item.ItemRabbitHide;
import cn.nukkit.item.ItemRabbitRaw;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * Author: BeYkeRYkt
 * Nukkit Project
 */
public class EntityRabbit extends EntityAnimal {

    public static final int NETWORK_ID = 18;

    public EntityRabbit(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public float getWidth() {
        if (isBaby()) {
            return 0.2f;
        }
        return 0.4f;
    }

    @Override
    public float getHeight() {
        if (isBaby()) {
            return 0.25f;
        }
        return 0.5f;
    }

    @Override
    public float getEyeHeight() {
        if (isBaby()) {
            return 0.25f; //TODO: No have information
        }
        return 0.5f;
    }

    @Override
    public String getName() {
        return this.getNameTag();
    }

    @Override
    public Item[] getDrops() {
        Item drops[] = new Item[3];
        drops[0] = this.isOnFire() ? new ItemRabbitCooked(0, random.nextRange(0, 1)) : new ItemRabbitRaw(0, random.nextRange(0, 1));
        drops[1] = new ItemRabbitHide(0, random.nextRange(0, 1));

        if (this.getLastDamageCause() instanceof EntityDamageByEntityEvent && random.nextRange(1, 10) == 1) {
            drops[2] = new ItemRabbitFoot(random.nextRange(0, 1));
        }

        return drops;
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    protected void initEntity() {
        this.setMaxHealth(3);
        super.initEntity();
    }
}
