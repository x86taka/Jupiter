package cn.nukkit.entity.boss;

import cn.nukkit.entity.monster.EntityMonster;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public abstract class EntityBoss extends EntityMonster{

	public EntityBoss(FullChunk chunk, CompoundTag nbt) {
		super(chunk, nbt);
	}

}
