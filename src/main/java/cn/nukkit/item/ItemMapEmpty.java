package cn.nukkit.item;

/**
 * @author Megapix96
 */
public class ItemMapEmpty extends Item {

    public static int mapCount = 0;

    public ItemMapEmpty() {
        this(0, 1);
    }

    public ItemMapEmpty(Integer meta) {
        this(meta, 1);
    }

    public ItemMapEmpty(Integer meta, int count) {
        super(EMPTY_MAP, 0, count, "Empty Map");
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }
}
