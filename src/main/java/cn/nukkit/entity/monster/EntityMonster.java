package cn.nukkit.entity.monster;

import cn.nukkit.entity.EntityCreature;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public abstract class EntityMonster extends EntityCreature {
    public EntityMonster(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
}
