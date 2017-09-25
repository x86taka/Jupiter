package cn.nukkit.item;

import cn.nukkit.block.BlockArmorStand;

public class ItemArmorStand extends Item {

    public ItemArmorStand() {
        this(0, 1);
    }

    public ItemArmorStand(Integer meta) {
        this(meta, 1);
    }

    public ItemArmorStand(Integer meta, int count) {
        super(ARMOR_STAND, meta, count, "ArmorStand");
        this.block = new BlockArmorStand();
    }

    @Override
    public int getMaxStackSize() {
        return 16;
    }

}
