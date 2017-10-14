package cn.nukkit.blockentity;

import cn.nukkit.block.Block;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;

public class BlockEntityBanner extends BlockEntitySpawnable{

	public BlockEntityBanner(FullChunk chunk, CompoundTag nbt) {
		super(chunk, nbt);

		if (!nbt.contains("Base")) {
			nbt.putInt("Base", 0);
		}
		if (!nbt.contains("Patterns")) {
			nbt.putList(new ListTag<>("Patterns"));
		}
	}

	@Override
	public CompoundTag getSpawnCompound() {
        return new CompoundTag()
                .putString("id", BlockEntity.BANNER)
                .putInt("x", (int) this.x)
                .putInt("y", (int) this.y)
                .putInt("z", (int) this.z)
                .putInt("Base", this.namedTag.getInt("Base"))
                .putList(this.namedTag.getList("Patterns", CompoundTag.class));
	}

	@Override
	public boolean isBlockEntityValid() {
        int id = this.level.getBlockIdAt(this.getFloorX(), this.getFloorY(), this.getFloorZ());
        return id == Block.STANDING_BANNER || id == Block.WALL_BANNER;
	}

	public int getBase() {
		return this.namedTag.getInt("Base");
	}

	public ListTag<CompoundTag> getPatterns() {
		return this.namedTag.getList("Patterns", CompoundTag.class);
	}
}
