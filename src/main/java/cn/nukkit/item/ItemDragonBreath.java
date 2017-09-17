package cn.nukkit.item;

/**
 * @author Megapix96
 */
public class ItemDragonBreath extends Item {

    public ItemDragonBreath() {
        this(0, 1);
    }

    public ItemDragonBreath(Integer meta) {
        this(meta, 1);
    }

    public ItemDragonBreath(Integer meta, int count) {
        super(DRAGON_BREATH, 0, count, "Dragon Breath");
    }
}
