package cn.nukkit.item;

/**
 * @author Megapix96
 */
public class ItemMelonGlistering extends ItemEdible {

    public ItemMelonGlistering() {
        this(0, 1);
    }

    public ItemMelonGlistering(Integer meta) {
        this(meta, 1);
    }

    public ItemMelonGlistering(Integer meta, int count) {
        super(GLISTERING_MELON, meta, count, "Glistering Melon");
    }
}
