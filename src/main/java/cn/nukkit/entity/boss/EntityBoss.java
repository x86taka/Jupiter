package cn.nukkit.entity.boss;

import cn.nukkit.entity.mob.EntityMob;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public abstract class EntityBoss extends EntityMob {

    public EntityBoss(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
}
