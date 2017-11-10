package cn.nukkit.entity.mob;

import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public abstract class EntityBoss extends EntityMob {

    public EntityBoss(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    protected void sendBossBar() {

    }
}
