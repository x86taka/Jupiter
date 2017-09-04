package cn.nukkit.item;

/**
 * @author Megapix96
 */
public class ItemChorusFruit extends Item {

    public ItemChorusFruit() {
        this(0, 1);
    }

    public ItemChorusFruit(Integer meta) {
        this(meta, 1);
    }

    public ItemChorusFruit(Integer meta, int count) {
        super(CHORUS_FRUIT, meta, count, "Chorus Fruit");
    }
}
