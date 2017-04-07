package cn.nukkit.item;

import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.BlockColor;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
abstract public class ItemArmor extends Item {

    public static final int TIER_LEATHER = 1;
    public static final int TIER_IRON = 2;
    public static final int TIER_CHAIN = 3;
    public static final int TIER_GOLD = 4;
    public static final int TIER_DIAMOND = 5;

    public ItemArmor(int id) {
        super(id);
    }

    public ItemArmor(int id, Integer meta) {
        super(id, meta);
    }

    public ItemArmor(int id, Integer meta, int count) {
        super(id, meta, count);
    }

    public ItemArmor(int id, Integer meta, int count, String name) {
        super(id, meta, count, name);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean isArmor() {
        return true;
    }

    @Override
    public int getEnchantAbility() {
        switch (this.getTier()) {
            case TIER_CHAIN:
                return 12;
            case TIER_LEATHER:
                return 15;
            case TIER_DIAMOND:
                return 10;
            case TIER_GOLD:
                return 25;
            case TIER_IRON:
                return 9;
        }

        return 0;
    }

	public void setCustomColor(BlockColor color){
		CompoundTag tag;
		if(this.hasCompoundTag()){
			tag = this.getNamedTag();
		}else{
			tag = new CompoundTag();
		}
		tag.putInt("customColor", color.getRGB());
		this.setCompoundTag(tag);
	}

	public CompoundTag getCustomColor(){
		if(!this.hasCompoundTag()) return null;
		CompoundTag tag = this.getNamedTag();
		if(tag.getCompound("customColor") != null){
			return tag.getCompound("customColor");
		}
		return null;
	}

	public void clearCustomColor(){
		if(!this.hasCompoundTag()) return;
		CompoundTag tag = this.getNamedTag();
		if(tag.getCompound("customColor") != null){
			tag.remove("customColor");
		}
		this.setCompoundTag(tag);
	}
}
