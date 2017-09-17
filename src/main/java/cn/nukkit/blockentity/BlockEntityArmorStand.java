package cn.nukkit.blockentity;

import cn.nukkit.block.Block;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public class BlockEntityArmorStand extends BlockEntitySpawnable {

	public BlockEntityArmorStand(FullChunk chunk, CompoundTag nbt) {
		super(chunk, nbt);
        this.namedTag = nbt;
	}

	@Override
    public void saveNBT() {
        super.saveNBT();
        this.namedTag.remove("Creator");
    }

    @Override
    public boolean isBlockEntityValid() {
        int blockID = getBlock().getId();
        return blockID == Block.ARMOR_STAND;
    }
    
    @Override
    public CompoundTag getSpawnCompound() {
        return new CompoundTag()
                .putString("id", BlockEntity.ARMOR_STAND)
                .putInt("x", (int) this.x)
                .putInt("y", (int) this.y)
                .putInt("z", (int) this.z);
    }
    
}
